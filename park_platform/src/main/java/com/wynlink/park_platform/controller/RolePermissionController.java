package com.wynlink.park_platform.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.SysPermission;
import com.wynlink.park_platform.service.RolePermissionService;
import com.wynlink.park_platform.service.SysPermissionService;

@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController {

	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private SysPermissionService sysPermissionService;
	/**
	 * 为角色分配权限
	 * @param params
	 * @return
	 */
	@RequestMapping("/addPermisForRole")
	public ResultData addPermisForRole(@RequestBody Map<String,Object> params) {
		
		Integer roleId = 0;
		if(!StringUtils.isEmpty(params.get("roleId"))) {
			roleId = Integer.parseInt(params.get("roleId").toString());
		} else {
			return ResultData.createError("角色id不能为空！");
		}
		
		List<Integer> permisIds = null;
		if(!StringUtils.isEmpty(params.get("permissionIds"))) {
			permisIds = (List<Integer>) params.get("permissionIds");
		} else {
			return ResultData.createError("权限id不能为空！");
		}
		
		Boolean bol = rolePermissionService.deletePermisForRole(roleId);
		if(!bol) {
			return ResultData.createError("分配权限失败！");
		}
		
		Boolean flag = rolePermissionService.addPermisForRole(roleId, permisIds);
		if(!flag) {
			return ResultData.createError("分配权限失败！");
		}
		return ResultData.createError("分配权限成功！");
	}
	
	/**
	 * 回显已经分配的权限
	 * @param params
	 * @return
	 */
	@RequestMapping("/loadAssignPermis")
	public ResultData loadAssignPermis(Integer roleId) {
		if(StringUtils.isEmpty(roleId)) {
			return ResultData.createError("角色id不能为空！");
		}
		List<SysPermission> permissions = new ArrayList<SysPermission>();
		/**
		 * 查询所有权限
		 */
		List<SysPermission> allPermission = sysPermissionService.selectList(null);
		/**
		 * 获取当前角色已经分配的权限
		 */
		List<Integer> permisIds = rolePermissionService.findPermisIdsByRoleId(roleId);
		
		Map<Integer,SysPermission> permissionMap = new HashMap<Integer, SysPermission>();
		for (SysPermission p : allPermission) {
			
			if(permisIds.contains(p.getId())) {
				p.setChecked(true);
			} else {
				p.setChecked(false);
			}
			
			permissionMap.put(p.getId(), p);
		}
		
		for (SysPermission p : allPermission) {
			SysPermission child = p;
			if(child.getPid() == 0) {
				
				permissions.add(p);
			} else {
				SysPermission parent = permissionMap.get(child.getPid());
				parent.getChildPermissions().add(child);
			}
		}
		
		
		
		
		
		
		
		return ResultData.createSuccess(permissions, "查询成功！");
	}
	
	
}
