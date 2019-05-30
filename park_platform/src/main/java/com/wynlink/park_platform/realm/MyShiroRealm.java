package com.wynlink.park_platform.realm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wynlink.park_platform.entity.SysPermission;
import com.wynlink.park_platform.entity.SysRole;
import com.wynlink.park_platform.entity.SysUser;
import com.wynlink.park_platform.service.SysPermissionService;
import com.wynlink.park_platform.service.SysUserService;

//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm {

	// 用于用户查询
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysPermissionService sysPermissionService;

	// 角色权限和对应权限添加
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 获取登录用户名
		SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
		if(user == null) {
			return null;
		}
		/**
		 * 获取用户权限信息
		 */
		List<SysPermission> permissions = sysPermissionService.queryPermissionByUser(user.getId());
		
		Map<Integer,SysPermission> permissionMap = new HashMap<>();
		
		List<SysPermission> roots = new ArrayList<SysPermission>();
		for (SysPermission sysPermission : permissions) {
			permissionMap.put(sysPermission.getId(),sysPermission);
		}
		
		for (SysPermission sysPermission : permissions) {
			SysPermission child = sysPermission;
			if(child.getPid() == 0) {
				roots.add(sysPermission);
			} else {
				SysPermission parent = permissionMap.get(child.getPid());
				parent.getChildPermissions().add(child);
			}
		}
		/*
		 * 获取权限名称
		 */
		List<String> permissionNames = new ArrayList<String>();
		if(permissions.size() > 0) {
			for(SysPermission permission: permissions) {
				permissionNames.add(permission.getPermName());
			}
		}
		
		/**
		 * 获取用户角色信息
		 */
		List<SysRole> roles = sysPermissionService.queryRoleByUser(user.getId());
		/*
		 * 获取角色名
		 */
		List<String> roleNames = new ArrayList<String>();
		if(roles.size() > 0) {
			for(SysRole role: roles) {
				roleNames.add(role.getRoleName());
			}
		}
		
		// 添加角色和权限
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		// 添加角色
		simpleAuthorizationInfo.addRoles(roleNames);
		// 添加权限
		simpleAuthorizationInfo.addStringPermissions(permissionNames);
		
		
		return simpleAuthorizationInfo;
	}

	// 用户认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException{
		// 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
		if (token.getPrincipal() == null) {
			return null;
		}
		
		UsernamePasswordToken upToken= (UsernamePasswordToken)token;
		// 获取登录用户名
		String username = upToken.getUsername();
		// 查询用户名称
		SysUser user = sysUserService.selectOne(new EntityWrapper<SysUser>()
				.eq("login_account", username));
		if (user == null) {
			// 这里返回后会报出对应异常
			return null;
		} 
		if (user.getUserStatus() == 0) { //账户冻结
            throw new LockedAccountException();
        }
		
		 SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				 	user, //用户信息
				 	user.getLoginPassword(), //密码
	                ByteSource.Util.bytes(user.getLoginAccount()),//salt=username
	                getName()  //realm name
	        );
		return authenticationInfo;
	}
}