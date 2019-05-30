package com.wynlink.park_platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.Gcjldata;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.service.GcjldataService;

@RestController
@RequestMapping("/AllVehicle")
public class AllVehicleManageController {

	@Autowired
	private GcjldataService gcjldataService;
	
	/**
	 * 查询所有过车数据
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public ResultData list(@RequestBody Map<String,Object> params) {
		
		Integer current = 1;
		Integer size = 10;
		
		if(!StringUtils.isEmpty(params.get("currentPage"))) {
			current = Integer.parseInt(params.get("currentPage").toString());
		}
		
		if(!StringUtils.isEmpty(params.get("pageSize"))) {
			size = Integer.parseInt(params.get("pageSize").toString());
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("parkingName", params.get("parkingName"));
		map.put("plateNo", params.get("plateNo"));
		Page page = new Page<>();
		
		List<Map<String,Object>> list = gcjldataService.allVehicle(new Page<>(current, size),map);
		page.setRecords(list);
		Long count = gcjldataService.allVehicleCount(map);
		page.setTotal(count);
		if(list == null) {
			return ResultData.createError("查询失败！");
		}
		return ResultData.createSuccess(page, "查询成功！");
	}
	
	/**
	 *根据车牌号查看历史详情
	 * @param plateNo
	 * @return
	 */
	@RequestMapping("/detail")
	public ResultData detail(String plateNo) {
		
		if(StringUtils.isEmpty(plateNo)) {
			return ResultData.createError("车牌号不能为空！");
		}
		
		List<Map<String,Object>> list = gcjldataService.findDetailByPlateNo(plateNo);
		if(list == null) {
			return ResultData.createError("查询失败！");
		}
		return ResultData.createSuccess(list, "查询成功！");
	}
}
