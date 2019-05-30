package com.wynlink.park_platform.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.SysRole;
import com.wynlink.park_platform.entity.SysUser;
import com.wynlink.park_platform.entity.SysUserRole;
import com.wynlink.park_platform.service.SysRoleService;
import com.wynlink.park_platform.service.SysUserRoleService;
import com.wynlink.park_platform.service.SysUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-05-21
 */
@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleController {
	
	@Autowired
	private SysUserService sysUserService;
	
	
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 为用户分配角色
	 * @param params
	 * @return
	 */
	@RequestMapping("/addRoleForUser")
	public ResultData addRoleForUser(@RequestBody Map<String,Object> params) {
		
		Integer userId = null;
		if(!StringUtils.isEmpty(params.get("userId"))) {
			userId = Integer.parseInt(params.get("userId").toString());
		} else {
			return ResultData.createError("用户id不能为空！");
		}
		
		List<Integer> roleIds = null;
		if(!StringUtils.isEmpty(params.get("roleIds"))) {
			roleIds = (List<Integer>) params.get("roleIds");
		} else {
			return ResultData.createError("角色id不能为空！");
		}
		
		try {
			sysUserRoleService.delete(new EntityWrapper<SysUserRole>()
					.eq("user_id", userId));
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("分配角色失败！");
		}
		
		List<SysUserRole> entityList = new ArrayList<SysUserRole>();
		for(Integer roleId : roleIds) {
			SysUserRole entity = new SysUserRole();
			entity.setUserId(userId);
			entity.setRoleId(roleId);
			entityList.add(entity);
		}
		
		try {
			sysUserRoleService.insertBatch(entityList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("分配角色失败！");
		}
		
		return ResultData.createSuccess("分配角色成功！");
	}
	
	/**
	 * 回显已经分配的权限
	 * @param params
	 * @return
	 */
	@RequestMapping("/loadAssignRole")
	public ResultData loadAssignRole(Integer userId) {
		
		if(StringUtils.isEmpty(userId)) {
			return ResultData.createError("用户id不能为空！");
		}
		
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		Integer id = user.getId();//当前登录用户id
		/*
		 * 查询所有角色
		 */
		List<SysRole> allRole = sysRoleService.selectList(new EntityWrapper<SysRole>()
				.eq("user_id", id));
		
		List<SysRole> assignRoles = new ArrayList<SysRole>();
		List<SysRole> unassignRoles = new ArrayList<SysRole>();
		
		Map<String,Object> map = new HashMap<String, Object>();
		/**
		 * 查询当前用户下的角色
		 */
		List<SysUserRole> SysUserRoles = sysUserRoleService.selectList(new EntityWrapper<SysUserRole>()
				.eq("user_id", userId));
		
		List<Integer> roleIds = new ArrayList<Integer>();
		if(SysUserRoles.size() > 0) {
			for(SysUserRole sysUserRole: SysUserRoles) {
				roleIds.add(sysUserRole.getRoleId());
			}
		}
		for (SysRole sysRole : allRole) {
			if(roleIds.contains(sysRole.getId())) {
				assignRoles.add(sysRole);
			} else {
				unassignRoles.add(sysRole);
			}
		}
		
		map.put("assignRoles",assignRoles);
		
		map.put("unassignRoles",unassignRoles);
		
		return ResultData.createSuccess(map, "查询成功");
	}
}

