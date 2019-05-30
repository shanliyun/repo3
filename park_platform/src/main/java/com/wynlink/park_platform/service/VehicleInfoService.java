package com.wynlink.park_platform.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wynlink.park_platform.entity.VehicleInfo;
import com.wynlink.park_platform.entity.vo.VehicleInfoVo;
import com.wynlink.park_platform.entity.vo.VehicleInfoVoPlus;

/**
 * <p>
 * 车辆信息表 服务类
 * </p>
 *
 * @author Vincent
 * @since 2019-03-20
 */
public interface VehicleInfoService extends IService<VehicleInfo> {

	Page<VehicleInfoVo> selectVehicleVoPage(Page<VehicleInfo> page, Map map);

	VehicleInfoVoPlus findById(Integer id);

}
