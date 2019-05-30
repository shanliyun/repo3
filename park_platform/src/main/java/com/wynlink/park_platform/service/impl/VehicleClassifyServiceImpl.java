package com.wynlink.park_platform.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wynlink.park_platform.entity.VehicleClassify;
import com.wynlink.park_platform.entity.vo.VehicleClassifyVo;
import com.wynlink.park_platform.mapper.VehicleClassifyMapper;
import com.wynlink.park_platform.service.VehicleClassifyService;

/**
 * <p>
 * 车辆分类表 服务实现类
 * </p>
 *
 * @author Vincent
 * @since 2019-04-16
 */
@Service
public class VehicleClassifyServiceImpl extends ServiceImpl<VehicleClassifyMapper, VehicleClassify> implements VehicleClassifyService {

	@Override
	public Page<VehicleClassifyVo> findAll(Page page, Map<String, Object> map) {
		return page.setRecords(this.baseMapper.findAll(page,map));
	}

	@Override
	public VehicleClassifyVo findById(Integer id) {
		return this.baseMapper.findById(id);
	}

}
