package com.wynlink.park_platform.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.SysPermission;
import com.wynlink.park_platform.entity.SysUser;
import com.wynlink.park_platform.service.SysPermissionService;

@RestController
@RequestMapping("/index")
public class IndexController {
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate JdbcTemplate;

	@Autowired
	private SysPermissionService sysPermissionService;

	@RequestMapping("/getMenu")
	public ResultData getMenu() {

		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();

		/**
		 * 获取用户权限信息
		 */
		/**
		 * List<SysPermission> data =
		 * sysPermissionService.queryPermissionByUser(user.getId());
		 * 
		 * Map<Integer,SysPermission> permissionMap = new HashMap<>();
		 * 
		 * List<SysPermission> roots = new ArrayList<SysPermission>(); for
		 * (SysPermission sysPermission : data) {
		 * permissionMap.put(sysPermission.getId(),sysPermission); }
		 * 
		 * for (SysPermission sysPermission : data) { SysPermission child =
		 * sysPermission; if(child.getPid() == 0) { roots.add(sysPermission); } else {
		 * SysPermission parent = permissionMap.get(child.getPid());
		 * parent.getChildPermissions().add(child); } }
		 **/

		List<List<Map<String, Object>>> data = new ArrayList<>();

		/*
		 * 根据用户查询权限
		 */
		String sql = "SELECT id,perm_name,url,pid,icon FROM sys_permission WHERE id IN ("
				+ "SELECT perm_id FROM sys_role_permission WHERE role_id IN ("
				+ "SELECT role_id FROM sys_user_role WHERE user_id = ?))";

		List<Map<String, Object>> permissions = JdbcTemplate.queryForList(sql, user.getId());
		List<Map<String, Object>> parentMennus = new ArrayList<Map<String, Object>>();
		if (permissions.size() > 0) {
			for (int i = 0; i < permissions.size(); i++) {
				Map<String, Object> map = permissions.get(i);
				if ("0".equals(map.get("pid").toString())) {// 父节点
					parentMennus.add(permissions.remove(i));
				}

			}
		}
		List<Map<String, Object>> childMennus = permissions;
		for (Map<String, Object> parent : parentMennus) {
			
			List<Map<String, Object>> result = new ArrayList<>();
			
			
			for(int i=0; i < childMennus.size(); i++) {
				Map<String, Object> child = childMennus.get(i);
				if(child.get("pid").equals(parent.get("id"))) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("sonMenu", child.get("perm_name"));
					map.put("url", child.get("url"));
					map.put("parentMenu", parent.get("perm_name"));
					map.put("icon", parent.get("icon"));
					result.add(map);
					//childMennus.remove(i);
				}
			}
			/*
			 * for (Map<String, Object> child : childMennus) { if
			 * (child.get("pid").equals(parent.get("id"))) { child.remove("perm_name");
			 * child.put("sonMenu", child.get("perm_name")); child.put("parentMenu",
			 * parent.get("perm_name")); child.put("icon", parent.get("icon")); } }
			 */

			data.add(result);
		}

		/*
		 * 查询父节点
		 */
		/*
		 * String sql =
		 * "SELECT id,perm_name AS menuName,icon FROM sys_permission WHERE pid=0";
		 * List<Map<String, Object>> parentMennus = JdbcTemplate.queryForList(sql);
		 * 
		 * 
		 * if (parentMennus != null && parentMennus.size() > 0) {
		 * 
		 *//**
			 * 根据父节点查询子节点
			 *//*
				 * for (Map<String, Object> parentMennu : parentMennus) { List<Map<String,
				 * Object>> result = new ArrayList<Map<String, Object>>(); Integer id =
				 * (Integer) parentMennu.get("id"); List<Map<String, Object>> sonMenus =
				 * JdbcTemplate
				 * .queryForList("SELECT perm_name AS sonMenu,url FROM sys_permission WHERE pid = ?"
				 * , id); if(sonMenus.size() > 0) { for (Map<String, Object> sonMenu : sonMenus)
				 * { sonMenu.put("parentMenu", parentMennu.get("menuName")); sonMenu.put("icon",
				 * parentMennu.get("icon")); result.add(sonMenu); } }
				 * 
				 * data.add(result); } }
				 */

		return ResultData.createSuccess(data, "查询成功！");
	}
}
