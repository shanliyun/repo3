package com.wynlink.park_platform.service.impl;

import com.wynlink.park_platform.entity.SysPermission;
import com.wynlink.park_platform.entity.SysRole;
import com.wynlink.park_platform.mapper.SysPermissionMapper;
import com.wynlink.park_platform.service.SysPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Vincent
 * @since 2019-05-21
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {


	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Override
	public List<SysPermission> queryPermissionByUser(Integer userId) {
		
		List<SysPermission> pers = new ArrayList<SysPermission>();
		
		String sql = "SELECT id,perm_name,url,pid FROM sys_permission WHERE id IN ("
				+ "SELECT perm_id FROM sys_role_permission WHERE role_id IN ("
				+ "SELECT role_id FROM sys_user_role WHERE user_id = ?))";
		
		List<Map<String,Object>> list = null;
		try {
			list = jdbcTemplate.queryForList(sql, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				SysPermission p = new SysPermission();
				p.setId(Integer.parseInt(map.get("id").toString()));
				p.setPermName(map.get("perm_name").toString());
				p.setUrl(map.get("url")==null?null:map.get("url").toString());
				p.setPid(Integer.parseInt(map.get("pid").toString()));
				pers.add(p);
			}
		}
		
		
		return pers;
	}
	@Override
	public List<SysRole> queryRoleByUser(Integer userId) {
		List<SysRole> roles = new ArrayList<SysRole>();
		
		String sql = "SELECT role_name FROM sys_role WHERE id IN (SELECT role_id FROM sys_user_role WHERE user_id=?);";
		
		List<Map<String,Object>> list = null;
		try {
			list = jdbcTemplate.queryForList(sql, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				SysRole r = new SysRole();
				r.setRoleName(map.get("role_name").toString());
				roles.add(r);
			}
		}
		return roles;
	}

}
