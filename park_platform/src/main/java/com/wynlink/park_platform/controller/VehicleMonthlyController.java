package com.wynlink.park_platform.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.VehicleMonthly;
import com.wynlink.park_platform.entity.vo.VehicleMonthlyVo;
import com.wynlink.park_platform.entity.vo.VehicleMonthlyVoPlus;
import com.wynlink.park_platform.service.VehicleMonthlyService;
import com.wynlink.park_platform.utils.DateFormatUtil;

/**
 * 月租车辆管理
 * @author vincent
 *
 */
@RestController
@RequestMapping("/vehicleMonthly")
public class VehicleMonthlyController {

	@Autowired
	private VehicleMonthlyService vehicleMonthlyService;
	
	@Autowired  
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	/**
	 * 初始化月租类型
	 */
	@RequestMapping("/initMonthly")
	public ResultData initMonthly() {
		
		
		List<Map<String, Object>> data = jdbcTemplate.queryForList("SELECT monthly_type_no AS monthlyTypeNo,"
				+ "monthly_type AS monthlyType FROM vehicle_monthly_type");
		
		return ResultData.createSuccess(data, "");
	}
	
	/**
	 * 注册月租车
	 * @return
	 */
	@RequestMapping("/add")
	public ResultData add(@RequestBody VehicleMonthly vehicleMonthly) {
		
		/**
		 * 字段非空验证
		 */
		ResultData checkMonthlyParams = this.checkMonthlyParams(vehicleMonthly);
		if(checkMonthlyParams.getStatus() == 1) {
			return checkMonthlyParams;
		}
		
		/**
		 * 车牌号是否已注册过
		 */
		int count = vehicleMonthlyService.selectCount(new EntityWrapper<VehicleMonthly>()
				.eq("parking_id", vehicleMonthly.getParkingId())
				.eq("plate_no", vehicleMonthly.getPlateNo()));
		
		if(count > 0) {
			return ResultData.createError("该车牌号已注册过！");
		}
		
		/**
		 * 计算到期时间和月数
		 */
		Integer monthlyTypeNo = vehicleMonthly.getMonthlyTypeNo();//月租类型编号
		Date startTime = vehicleMonthly.getStartTime();
		Date endTime = null;
		Integer months = 0;
		switch (monthlyTypeNo) {
		case 1:
			months = 1;
			endTime = DateFormatUtil.addMonths(startTime, months);
			break;
		case 2:
			months = 3;
			endTime = DateFormatUtil.addMonths(startTime, months);
			break;
		case 3:
			months = 6;
			endTime = DateFormatUtil.addMonths(startTime, months);
			break;
		case 4:
			months = 12;
			endTime = DateFormatUtil.addMonths(startTime, months);
			break;
		default:
			months = vehicleMonthly.getMonths();
			endTime = DateFormatUtil.addMonths(startTime, months);
			break;
		}
		
		vehicleMonthly.setEndTime(endTime);
		Integer monthlyRent = vehicleMonthly.getMonthlyRent();//每月租金
		Integer totalRent = monthlyRent * months;
		vehicleMonthly.setTotalRent(totalRent);
		vehicleMonthly.setMonths(months);
		vehicleMonthly.setCreateTime(new Date());
		vehicleMonthly.setUpdateTime(new Date());
		try {
			vehicleMonthlyService.insert(vehicleMonthly);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createSuccess("注册失败，请重试！");
		}
		
		return ResultData.createSuccess("注册成功！");
	}
	
	/**
	 * 月租车列表展示
	 */
	@RequestMapping("/list")
	public ResultData list(@RequestBody Map<String,Object> params) {
		
		Integer currentPage = 1;
		if(!StringUtils.isEmpty(params.get("currentPage"))) {
			currentPage = Integer.parseInt(params.get("currentPage").toString());
		}
		
		Integer pageSize = 10;
		if(!StringUtils.isEmpty(params.get("pageSize"))) {
			pageSize = Integer.parseInt(params.get("pageSize").toString());
		}
		
		Integer parkingId = null;
		if(!StringUtils.isEmpty(params.get("parkingId"))) {
			parkingId = Integer.parseInt(params.get("parkingId").toString());
		}
		
		String plateNo = null;
		if(!StringUtils.isEmpty(params.get("plateNo"))) {
			plateNo = "%"+params.get("plateNo").toString()+"%";
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("parkingId", parkingId);
		map.put("plateNo", plateNo);
		
		Page<VehicleMonthlyVo> page = vehicleMonthlyService.findAllVehicleMonthly(new Page<>(currentPage,pageSize),map);
		
		
		return ResultData.createSuccess(page, "查询成功！");
	}
	
	/**
	 * 月租车详情
	 */
	@RequestMapping("/detail")
	public ResultData detail(Integer id) {
		
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		VehicleMonthlyVoPlus data = vehicleMonthlyService.findVehicleMonthlyById(id);
		
		return ResultData.createSuccess(data, "查询成功！");
	}
	
	/**
	 * 月租车修改
	 */
	@RequestMapping("/update")
	public ResultData update(@RequestBody VehicleMonthly vehicleMonthly) {
		
		Integer id = vehicleMonthly.getId();
		
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		/**
		 * 字段非空验证
		 */
		ResultData checkMonthlyParams = this.checkMonthlyParams(vehicleMonthly);
		if(checkMonthlyParams.getStatus() == 1) {
			return checkMonthlyParams;
		}
		
		/**
		 * 车牌号是否已注册过
		 */
		int count = vehicleMonthlyService.selectCount(new EntityWrapper<VehicleMonthly>()
				.eq("parking_id", vehicleMonthly.getParkingId())
				.eq("plate_no", vehicleMonthly.getPlateNo())
				.ne("id", id));
		
		if(count > 0) {
			return ResultData.createSuccess("该车牌号已注册过！");
		}
		
		
		
		Integer monthlyTypeNo = vehicleMonthly.getMonthlyTypeNo();
		
		Date endTime = null;
		Date startTime = vehicleMonthly.getStartTime();
		Integer months = 0;
		switch (monthlyTypeNo) {
		case 1:
			
			months = 1;
			endTime = DateFormatUtil.addMonths(startTime, months);
			break;
		case 2:
			months = 3;
			endTime = DateFormatUtil.addMonths(startTime, months);
			break;
		case 3:
			months = 6;
			endTime = DateFormatUtil.addMonths(startTime, months);
			break;
		case 4:
			months = 12;
			endTime = DateFormatUtil.addMonths(startTime, months);
			break;
		default:
			months = vehicleMonthly.getMonths();
			endTime = DateFormatUtil.addMonths(startTime, months);
			break;
		}
		
		vehicleMonthly.setEndTime(endTime);
		Integer monthlyRent = vehicleMonthly.getMonthlyRent();//每月租金
		Integer totalRent = monthlyRent * months;
		vehicleMonthly.setTotalRent(totalRent);
		vehicleMonthly.setMonths(months);
		vehicleMonthly.setUpdateTime(new Date());
		try {
			vehicleMonthlyService.updateById(vehicleMonthly);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("修改失败！");
		}
		
		
		return ResultData.createSuccess("修改成功！");
	}
	
	/**
	 * 月租车删除
	 */
	@RequestMapping("/delete")
	public ResultData delete(Integer id) {

		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		try {
			vehicleMonthlyService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("删除失败！");
		}
		
		
		return ResultData.createSuccess("删除成功！");
	}
	
	private ResultData checkMonthlyParams(VehicleMonthly vehicleMonthly) {
		Integer parkingId = vehicleMonthly.getParkingId();
		if(StringUtils.isEmpty(parkingId)) {
			return ResultData.createError("请选择车场！");
		}
		
		String plateNo = vehicleMonthly.getPlateNo();
		if(StringUtils.isEmpty(plateNo)) {
			return ResultData.createError("请输入车牌号！");
		}
		
		String plateColorName = vehicleMonthly.getPlateColorName();
		if(StringUtils.isEmpty(plateColorName)) {
			return ResultData.createError("请选择车牌颜色！");
		}
		
		Integer monthlyTypeNo = vehicleMonthly.getMonthlyTypeNo();
		if(StringUtils.isEmpty(monthlyTypeNo)) {
			return ResultData.createError("请选择月租类型！");
		}
		
		Date stime = vehicleMonthly.getStartTime();
		if(StringUtils.isEmpty(stime)) {
			return ResultData.createError("请选择开始时间！");
		}
		
		Integer monthlyRent = vehicleMonthly.getMonthlyRent();
		if(StringUtils.isEmpty(monthlyRent)) {
			return ResultData.createError("请输入每月租金！");
		}
		
		return ResultData.createSuccess("");
	}
}
