package com.wynlink.park_platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService {

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 给角色添加权限
	 * 
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	public Boolean addPermisForRole(Integer roleId, List<Integer> permissionIds) {

		Boolean flag = false;

		String sql = "INSERT sys_role_permission VALUES(?,?,?)";
		List<Object[]> params = new ArrayList<>();
		for (Integer permissionId : permissionIds) {
			params.add(new Object[] { null, roleId, permissionId });
		}
		try {
			jdbcTemplate.batchUpdate(sql, params);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 根据角色id删除角色下面的所有权限
	 */
	public Boolean deletePermisForRole(Integer roleId) {
		Boolean flag = false;

		String sql = "DELETE FROM sys_role_permission WHERE role_id=?";
		try {
			jdbcTemplate.update(sql, roleId);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 根据角色id查询所有权限id
	 */
	public List<Integer> findPermisIdsByRoleId(Integer roleId) {
		
		String sql = "SELECT perm_id AS permId FROM sys_role_permission WHERE role_id=?";
		List<Map<String, Object>> mapList = null;
		try {
			mapList = jdbcTemplate.queryForList(sql, roleId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Integer> data = new ArrayList<Integer>();
		if(mapList != null && mapList.size() > 0) {
			for (Map<String, Object> map : mapList) {
				data.add(Integer.parseInt(map.get("permId").toString()));
			}
		}
		return data;
	}
}
