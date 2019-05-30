package com.wynlink.park_platform.service;

import com.wynlink.park_platform.entity.SysPermission;
import com.wynlink.park_platform.entity.SysRole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Vincent
 * @since 2019-05-21
 */
public interface SysPermissionService extends IService<SysPermission> {
	

	List<SysPermission> queryPermissionByUser(Integer id);

	List<SysRole> queryRoleByUser(Integer id);

}
