package com.wynlink.park_platform.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.SysRole;
import com.wynlink.park_platform.entity.SysUser;
import com.wynlink.park_platform.service.SysRoleService;
import com.wynlink.park_platform.service.SysUserService;

/**
 * <p>
 * 系统角色
 * </p>
 *
 * @author Vincent
 * @since 2019-03-19
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@RequestMapping("/add")
	public ResultData add(@RequestBody SysRole sysRole) {
		
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		Integer id = user.getId();
		
		String roleName = sysRole.getRoleName();
		if(StringUtils.isEmpty(roleName)) {
			return ResultData.createError("角色名不能为空！");
		}

		int count = sysRoleService.selectCount(new EntityWrapper<SysRole>()
				.eq("role_name", roleName)
				.eq("user_id", id));
		if (count > 0) {
			return ResultData.createError("角色名已存在！");
		}
		
		sysRole.setUserId(id);
		sysRole.setCreateTime(new Date());
		sysRole.setUpdateTime(new Date());
		try {
			sysRoleService.insert(sysRole);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("新增角色失败！");
		}
		return ResultData.createSuccess("新增角色成功！");
	}
	
	/**
	 * 角色列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public ResultData list(@RequestBody Map<String, Object> params) {
		Integer currentPage = 1;
		if (!StringUtils.isEmpty(params.get("currentPage"))) {
			currentPage = Integer.parseInt(params.get("currentPage").toString());
		}

		Integer pageSize = 10;
		if (!StringUtils.isEmpty(params.get("pageSize"))) {
			pageSize = Integer.parseInt(params.get("pageSize").toString());
		}

		Wrapper<SysRole> wrapper = new EntityWrapper<SysRole>();
		if (!StringUtils.isEmpty(params.get("roleName"))) {
			wrapper.like("role_name", params.get("roleName").toString());
		}

		wrapper.orderBy("create_time", false);

		Page<SysRole> page = sysRoleService.selectPage(new Page<SysRole>(currentPage, pageSize), wrapper);
		return ResultData.createSuccess(page, "查询成功！");
	}
	
	/**
	 * 角色更新
	 * @param sysUser
	 * @return
	 */
	@RequestMapping("/update")
	public ResultData update(@RequestBody SysRole sysRole) {
		String roleName = sysRole.getRoleName();
		if(StringUtils.isEmpty(roleName)) {
			return ResultData.createError("角色名不能为空！");
		}
		Integer id = sysRole.getId();
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		int count = sysRoleService.selectCount(new EntityWrapper<SysRole>()
				.eq("role_name", roleName)
				.eq("user_id", id)
				.ne("id", id));
		if (count > 0) {
			return ResultData.createError("角色名已存在！");
		}
		sysRole.setUpdateTime(new Date());
		try {
			sysRoleService.updateById(sysRole);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("更新失败，请重试！");
		}
		return ResultData.createSuccess("更新成功！");
	}
	

	
	/**
	 * 角色删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultData delete(Integer id) {
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		try {
			sysRoleService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("删除失败，请重试！");
		}
		
		return ResultData.createSuccess("删除成功！");
	}
}
