package com.wynlink.park_platform.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wynlink.park_platform.entity.Gcjldata;
import com.wynlink.park_platform.entity.ParkingInfo;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.service.GcjldataService;
import com.wynlink.park_platform.service.ParkingInfoService;
import com.wynlink.park_platform.utils.Arith;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
	
	@Autowired
	private GcjldataService gcjldataService;
	@Autowired
	private ParkingInfoService parkingInfoService;
	
	/**
	 * 统计昨日收益
	 * @return
	 */
	@RequestMapping("/yesterday")
	public ResultData yesterday() {
		List<Map<String,Object>> record = gcjldataService.yesterdayRecord();
		
		
		Double totalFee = 0.0;
		if(record != null && record.size() > 0) {
			for (Map<String, Object> map : record) {
				if(!StringUtils.isEmpty(map.get("payment_toll_fee"))) {
					totalFee = Arith.add(totalFee, Double.parseDouble(map.get("payment_toll_fee").toString()));
				}
			}
		}
		
		System.out.println(totalFee);
		return ResultData.createSuccess(totalFee, "");
	}
	
	/**
	 * 实时统计当日收益
	 * @return
	 */
	@RequestMapping("/nowEarnings")
	public ResultData nowEarnings() {
		
		Map<String,Object> todayMoney = gcjldataService.todayRecord();
		
		Double data = 0.0;
		if(todayMoney != null && !StringUtils.isEmpty(todayMoney.get("totalTodayMoney"))) {
			data = Double.parseDouble(todayMoney.get("totalTodayMoney").toString());
		}
		return ResultData.createSuccess(data, "");
	}
	
	
	/**
	 * 按月分组统计收益
	 * @return
	 */
	@RequestMapping("/ayfztj")
	public ResultData ayfztj() {
		List<Map<String,Object>> fytj = gcjldataService.fytj();
		
		return ResultData.createSuccess(fytj, "");
	}
	
	
	/**
	 * 统计场内车辆
	 * @return
	 */
	@RequestMapping("/totalVehicle")
	public ResultData totalVehicle() {
		
		List<Map<String,Object>> countListMap = gcjldataService.totalVehicle();
		
		Integer count = 0;
		
		if(countListMap != null && countListMap.size() > 0) {
			count = countListMap.size();
		}
		
		
		return ResultData.createSuccess(count, "查询成功！");
	}
	
	/**
	 * 统计剩余车位
	 * @return
	 */
	@RequestMapping("/residueSpace")
	public ResultData residueSpace(Integer parkingId,Integer space) {
		
		ParkingInfo entity = new ParkingInfo();
		entity.setRemainCount(space);
		
		/**
		 * 更新车位信息
		 */
		try {
			parkingInfoService.update(entity, new EntityWrapper<ParkingInfo>()
					.eq("parking_id", parkingId));
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("更新失败！");
		}
		
		System.out.println(space);
		return ResultData.createSuccess(space, "更新成功！");
	}
	
}
