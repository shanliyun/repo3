package com.wynlink.park_platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wynlink.park_platform.entity.WhiteList;
import com.wynlink.park_platform.mapper.WhiteListMapper;
import com.wynlink.park_platform.service.WhiteListService;

/**
 * <p>
 * 白名单车辆 服务实现类
 * </p>
 *
 * @author Vincent
 * @since 2019-04-13
 */
@Service
public class WhiteListServiceImpl extends ServiceImpl<WhiteListMapper, WhiteList> implements WhiteListService {

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Override
	public List<Map<String, Object>> findAll(Integer parkingId) {
		List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT plate_no As plateNo,plate_color_name AS plateColorName FROM white_list WHERE parking_id = ?"
				, parkingId);
		return list;
	}
	@Override
	public WhiteList findById(Integer id) {
		return this.baseMapper.findById(id);
	}
	@Override
	public Page<WhiteList> findAll(Page<WhiteList> page, Map<String, Object> map) {
		return page.setRecords(this.baseMapper.findAll(page,map));
	}

}
