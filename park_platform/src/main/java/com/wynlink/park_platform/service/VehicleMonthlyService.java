package com.wynlink.park_platform.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wynlink.park_platform.entity.VehicleMonthly;
import com.wynlink.park_platform.entity.vo.VehicleMonthlyVo;
import com.wynlink.park_platform.entity.vo.VehicleMonthlyVoPlus;

/**
 * <p>
 * 月租车信息表 服务类
 * </p>
 *
 * @author Vincent
 * @since 2019-03-21
 */
public interface VehicleMonthlyService extends IService<VehicleMonthly> {

	Page<VehicleMonthlyVo> findAllVehicleMonthly(Page page, Map<String, Object> map);

	VehicleMonthlyVoPlus findVehicleMonthlyById(Integer id);

	List<Map<String, Object>> findAll(Integer parkingId);

}
