package com.wynlink.park_platform.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.VehicleClassify;
import com.wynlink.park_platform.entity.VehicleInfo;
import com.wynlink.park_platform.entity.vo.VehicleClassifyVo;
import com.wynlink.park_platform.service.VehicleClassifyService;
import com.wynlink.park_platform.service.VehicleInfoService;

/**
 * <p>
 * 车辆分类表 前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-04-16
 */
@RestController
@RequestMapping("/vehicleClassify")
public class VehicleClassifyController {
	
	@Autowired
	private VehicleClassifyService vehicleClassifyService;

	@Autowired
	private VehicleInfoService vehicleInfoService;
	
	/**
	 * 车辆分类新增
	 */
	@RequestMapping("/add")
	public ResultData add(@RequestBody VehicleClassify vehicleClassify) {
		/**
		 * 字段非空校验
		 */
		Integer parkingId = vehicleClassify.getParkingId();//车场id
		if(StringUtils.isEmpty(parkingId)) {
			return ResultData.createError("请选择车场！");
		}
		
		String classifyName = vehicleClassify.getVehicleClassifyName();//分类名称
		if(StringUtils.isEmpty(classifyName)) {
			return ResultData.createError("请输入分类名称！");
		}
		/*
		 * 查询数据库中有无重复记录
		 */
		VehicleClassify vt = vehicleClassifyService
				.selectOne(new EntityWrapper<VehicleClassify>()
						.eq("parking_id", parkingId)
						.eq("vehicle_classify_name", classifyName));
		
		/*
		 * 如果数据库有记录并且状态为1，就提示用户类型已存在
		 */
		if(vt != null && vt.getStatus()==1) {
			return ResultData.createError("该分类已存在！");
		}
		/*
		 * 如果数据库有记录并且状态为0，就更改状态
		 */
		if (vt != null && vt.getStatus()==0) {
			vt.setStatus(1);
			vt.setUpdateTime(new Date());
			try {
				vehicleClassifyService.updateById(vt);
				return ResultData.createSuccess("新增分类成功！");
			} catch (Exception e) {
				e.printStackTrace();
				return ResultData.createError("新增分类失败！");
			}
		} 
		
		/*
		 * 如果数据库中没有就添加
		 */

		try {
			vehicleClassify.setCreateTime(new Date());
			vehicleClassify.setUpdateTime(new Date());
			vehicleClassifyService.insert(vehicleClassify);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResultData.createSuccess("新增分类成功！");
	}
	
	/**
	 * 车辆分类列表展示
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
		
		Map<String,Object> map = new HashMap<>();
		map.put("parkingId", parkingId);
		
		Page<VehicleClassifyVo> page = vehicleClassifyService.findAll(new Page<>(currentPage,pageSize),map);
		
		
		return ResultData.createSuccess(page, "查询成功！");
	}
	
	/**
	 * 车辆分类详情，回显
	 */
	@RequestMapping("/detail")
	public ResultData detail(Integer id) {
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		VehicleClassifyVo vo = vehicleClassifyService.findById(id);
		if(vo == null) {
			return ResultData.createError("id有误！");
		}
		
		
		return ResultData.createSuccess(vo, "查询成功！");
	}
	
	
	/**
	 * 车辆类型修改
	 */
	@RequestMapping("/update")
	public ResultData update(@RequestBody VehicleClassify vehicleClassify) {
		
		/*
		 * 先检查id参数是否完整
		 */
 		Integer id = vehicleClassify.getId();
		
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		/**
		 * 字段非空校验
		 */
		Integer parkingId = vehicleClassify.getParkingId();//车场id
		if(StringUtils.isEmpty(parkingId)) {
			return ResultData.createError("请选择车场！");
		}
		
		String classifyName = vehicleClassify.getVehicleClassifyName();//分类名称
		if(StringUtils.isEmpty(classifyName)) {
			return ResultData.createError("请输入分类名称！");
		}
		/*
		 * 查询数据库中除了自身有无其他重复记录
		 */
		int count = vehicleClassifyService.selectCount(new EntityWrapper<VehicleClassify>()
								.eq("parking_id", parkingId)
								.eq("vehicle_classify_name", classifyName)
								.ne("id", id));
		
		
		/*
		 * 如果数据库有记录，就提示用户类型已存在
		 */
		if(count > 0) {
			return ResultData.createError("类型已存在，请重新修改！");
		}
		/*
		 * 如果数据库有记录并且状态为0，就更改状态
		 */
		vehicleClassify.setUpdateTime(new Date());
		try {
			vehicleClassifyService.updateById(vehicleClassify);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("修改类型失败！");
		}
		


		return ResultData.createSuccess("修改类型成功！");
	}
	
	/**
	 * 车辆分类删除
	 */
	@RequestMapping("/delete")
	public ResultData delete(Integer id) {
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		/**
		 * 删除之前应先查询该分类下有无车辆信息
		 */
		int count = vehicleInfoService.selectCount(new EntityWrapper<VehicleInfo>()
				.eq("vehicle_classify_id", id));
		if(count > 0) {
			//如果有车辆信息，则提示不可删除
			return ResultData.createError("该车辆分类下面有所属车辆，禁止删除！");
		}

		/*VehicleClassify vehicleClassify = new VehicleClassify();
		vehicleClassify.setId(id);
		vehicleClassify.setStatus(0);*/
		try {
			//vehicleClassifyService.updateById(vehicleClassify);
			vehicleClassifyService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResultData.createSuccess("删除成功！");
	}
	
	/**
	 * 查询分类下车辆信息
	 */
	@RequestMapping("/vehicleForClassify")
	public ResultData vehicleForClassify(Integer vehicleClassifyId) {
		if(StringUtils.isEmpty(vehicleClassifyId)) {
			return ResultData.createError("车辆分类id不为空！");
		}
		
		Map<String,Object> map = new HashMap<>();
		List<VehicleInfo> list = vehicleInfoService.selectList(new EntityWrapper<VehicleInfo>()
				.eq("vehicle_classify_id", vehicleClassifyId));
		map.put("count", list.size());
		map.put("dataList", list);
		return ResultData.createSuccess(map, "查询成功!");
	}
}

