package com.wynlink.park_platform.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.SysPermission;
import com.wynlink.park_platform.entity.SysRole;
import com.wynlink.park_platform.service.SysPermissionService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-05-21
 */
@RestController
@RequestMapping("/sysPermission")
public class SysPermissionController {

	@Autowired
	private SysPermissionService sysPermissionService;
	
	/**
	 * 新增节点
	 * @param sysPermission
	 * @return
	 */
	@RequestMapping("/add")
	public ResultData add(@RequestBody SysPermission sysPermission) {

		String permName = sysPermission.getPermName();
		if(StringUtils.isEmpty(permName)) {
			return ResultData.createError("权限名不能为空！");
		}
		
		Integer pid = sysPermission.getPid();

		int count = sysPermissionService.selectCount(new EntityWrapper<SysPermission>()
				.eq("perm_name", permName)
				.eq("pid", pid));
		if (count > 0) {
			return ResultData.createError("权限名已存在！");
		}
		sysPermission.setCreateTime(new Date());
		sysPermission.setUpdateTime(new Date());
		try {
			sysPermissionService.insert(sysPermission);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("新增权限失败！");
		}
		return ResultData.createSuccess("新增权限成功！");
	}
	
	/**
	 * 加载权限树形菜单
	 * @param params
	 * @return
	 */
	@RequestMapping("/loadPermissionTree")
	public ResultData loadPermissionTree() {
		
		/**
		 * 递归加载树形结构数据
		 */
		/*
		 * SysPermission parent = new SysPermission(); parent.setId(0);
		 * this.queryChildPermissions(parent);
		 */
		
		List<SysPermission> permissions = new ArrayList<SysPermission>();
		
		//查询所有权限
		List<SysPermission> ps = sysPermissionService.selectList(null);
		
		/*
		 * if(ps.size() > 0) { for (SysPermission p : ps) { //字节点 SysPermission child =
		 * p; if(p.getPid() == 0) { permissions.add(p); } else { for (SysPermission
		 * innerPermission : ps) { if(child.getPid()==innerPermission.getId()) { //父节点
		 * SysPermission parent = innerPermission; //组合父子节点关系
		 * parent.getChildPermissions().add(child); } } } } }
		 */
		
		Map<Integer,SysPermission> permissionMap = new HashMap<Integer, SysPermission>();
		for (SysPermission p : ps) {
			permissionMap.put(p.getId(), p);
		}
		
		for (SysPermission p : ps) {
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
	
	/**
	 * 递归查询权限信息
	 * @param parent
	 */
	/*
	 * private void queryChildPermissions(SysPermission parent) {
	 * List<SysPermission> childPermissions = sysPermissionService.selectList(new
	 * EntityWrapper<SysPermission>() .eq("pid", parent.getId()));
	 * 
	 * for (SysPermission sysPermission : childPermissions) {
	 * queryChildPermissions(sysPermission); }
	 * parent.setChildPermissions(childPermissions); }
	 */
	
	/**
	 * 权限回显
	 * @param sysUser
	 * @return
	 */
	@RequestMapping("/edit")
	public ResultData edit(Integer id) {
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		SysPermission sysPermission = sysPermissionService.selectById(id);
		
		
		return ResultData.createSuccess(sysPermission,"回显成功！");
	}
	
	/**
	 * 权限更新
	 * @param sysUser
	 * @return
	 */
	@RequestMapping("/update")
	public ResultData update(@RequestBody SysPermission sysPermission) {
		String permName = sysPermission.getPermName();
		if(StringUtils.isEmpty(permName)) {
			return ResultData.createError("权限名不能为空！");
		}
		Integer id = sysPermission.getId();
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		int count = sysPermissionService.selectCount(new EntityWrapper<SysPermission>()
				.eq("perm_name", permName)
				.eq("pid", sysPermission.getPid())
				.ne("id", id));
		if (count > 0) {
			return ResultData.createError("权限名已存在！");
		}
		sysPermission.setUpdateTime(new Date());
		try {
			sysPermissionService.updateById(sysPermission);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("更新失败，请重试！");
		}
		return ResultData.createSuccess("更新成功！");
	}
	

	
	/**
	 * 节点删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultData delete(Integer id) {
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		try {
			sysPermissionService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("删除失败，请重试！");
		}
		
		return ResultData.createSuccess("删除成功！");
	}
	
}

