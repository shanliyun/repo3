package com.wynlink.park_platform.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wynlink.park_platform.entity.VehicleClassify;
import com.wynlink.park_platform.entity.vo.VehicleClassifyVo;

/**
 * <p>
 * 车辆分类表 服务类
 * </p>
 *
 * @author Vincent
 * @since 2019-04-16
 */
public interface VehicleClassifyService extends IService<VehicleClassify> {

	Page<VehicleClassifyVo> findAll(Page page, Map<String, Object> map);

	VehicleClassifyVo findById(Integer id);

}
