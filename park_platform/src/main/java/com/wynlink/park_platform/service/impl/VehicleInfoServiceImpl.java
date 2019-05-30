package com.wynlink.park_platform.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wynlink.park_platform.entity.VehicleInfo;
import com.wynlink.park_platform.entity.vo.VehicleInfoVo;
import com.wynlink.park_platform.entity.vo.VehicleInfoVoPlus;
import com.wynlink.park_platform.mapper.VehicleInfoMapper;
import com.wynlink.park_platform.service.VehicleInfoService;

/**
 * <p>
 * 车辆信息表 服务实现类
 * </p>
 *
 * @author Vincent
 * @since 2019-03-20
 */
@Service
public class VehicleInfoServiceImpl extends ServiceImpl<VehicleInfoMapper, VehicleInfo> implements VehicleInfoService {


	@Override
	public Page<VehicleInfoVo> selectVehicleVoPage(Page page, Map map) {
		// TODO Auto-generated method stub
		return page.setRecords(this.baseMapper.selectVehiclePage(page, map));
	}

	@Override
	public VehicleInfoVoPlus findById(Integer id) {
		return this.baseMapper.findById(id);
	}
	
}
