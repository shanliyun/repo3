package com.wynlink.park_platform.service.impl;

import com.wynlink.park_platform.entity.BlackList;
import com.wynlink.park_platform.mapper.BlackListMapper;
import com.wynlink.park_platform.service.BlackListService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Vincent
 * @since 2019-03-19
 */
@Service
public class BlackListServiceImpl extends ServiceImpl<BlackListMapper, BlackList> implements BlackListService {

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Map<String, Object>> findAll(Integer parkingId) {
		List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT plate_no As plateNo,plate_color_name AS plateColorName FROM black_list WHERE parking_id = ?"
				, parkingId);
		return list;
	}

	@Override
	public Page<BlackList> findAll(Page<BlackList> page, Map<String, Object> map) {
		return page.setRecords(this.baseMapper.findAll(page,map));
	}

	@Override
	public BlackList findById(Integer id) {
		return this.baseMapper.findById(id);
	}

}
