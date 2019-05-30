package com.wynlink.park_platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wynlink.park_platform.entity.VehicleMonthly;
import com.wynlink.park_platform.entity.vo.VehicleMonthlyVo;
import com.wynlink.park_platform.entity.vo.VehicleMonthlyVoPlus;
import com.wynlink.park_platform.mapper.VehicleMonthlyMapper;
import com.wynlink.park_platform.service.VehicleMonthlyService;

/**
 * <p>
 * 月租车信息表 服务实现类
 * </p>
 *
 * @author Vincent
 * @since 2019-03-21
 */
@Service
public class VehicleMonthlyServiceImpl extends ServiceImpl<VehicleMonthlyMapper, VehicleMonthly> implements VehicleMonthlyService {

	@Override
	public Page<VehicleMonthlyVo> findAllVehicleMonthly(Page page, Map<String, Object> map) {
		return page.setRecords(this.baseMapper.findAllVehicleMonthly(page,map));
	}

	@Override
	public VehicleMonthlyVoPlus findVehicleMonthlyById(Integer id) {
		return this.baseMapper.findVehicleMonthlyById(id);
	}
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findAll(Integer parkingId) {
		List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT plate_no As plateNo,plate_color_name AS plateColorName,end_time AS endTime FROM vehicle_monthly WHERE parking_id = ?"
				, parkingId);
		return list;
	}

}
