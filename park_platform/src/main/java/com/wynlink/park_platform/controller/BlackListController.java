package com.wynlink.park_platform.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.BlackList;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.service.BlackListService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-03-19
 */
@RestController
@RequestMapping("/blackList")
public class BlackListController {
	
	@Autowired
	private BlackListService blackListService;
	
	/**
	 * 添加黑名单
	 * @param blackList
	 * @return
	 */
	@RequestMapping("/add")
	public ResultData add(@RequestBody BlackList blackList) {
		/**
		 * 检查非空参数
		 */
		ResultData checkBlackParams = this.checkBlackParams(blackList);
		if(checkBlackParams.getStatus() == 1) {
			return checkBlackParams;
		}
		/**
		 * 检查重复
		 */
		int count = blackListService.selectCount(new EntityWrapper<BlackList>()
				.eq("parking_id", blackList.getParkingId())
				.eq("plate_no", blackList.getPlateNo()));
		if (count > 0) {
			return ResultData.createError("改车牌已设置过黑名单！");
		}
		blackList.setCreateTime(new Date());
		blackList.setUpdateTime(new Date());
		
		try {
			blackListService.insert(blackList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("添加失败！");
		}
		
		return ResultData.createSuccess("添加成功");
	}
	
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public ResultData detail(Integer id) {
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		BlackList vo = blackListService.findById(id);
		
		return ResultData.createSuccess(vo, "查询成功！");
	}
	
	/**
	 * 修改黑名单
	 * @param blackList
	 * @return
	 */
	@RequestMapping("/update")
	public ResultData update(@RequestBody BlackList blackList) {
		
		Integer id = blackList.getId();
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		/**
		 * 检查非空参数
		 */
		ResultData checkBlackParams = this.checkBlackParams(blackList);
		if(checkBlackParams.getStatus() == 1) {
			return checkBlackParams;
		}
		/**
		 * 检查重复
		 */
		int count = blackListService.selectCount(new EntityWrapper<BlackList>()
				.eq("parking_id", blackList.getParkingId())
				.eq("plate_no", blackList.getPlateNo())
				.ne("id", id));
		if (count > 0) {
			return ResultData.createError("改车牌已设置过黑名单！");
		}
		
		blackList.setUpdateTime(new Date());
		
		try {
			blackListService.updateById(blackList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("修改失败！");
		}
		
		return ResultData.createSuccess("修改成功");
	}
	
	/**
	 * 取消黑名单
	 * @param id
	 * @return
	 */
	@RequestMapping("/cancel")
	public ResultData cancel(@RequestParam("id") Integer id) {
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		BlackList blackList = new BlackList();
		blackList.setUpdateTime(new Date());
		blackList.setIsBlack(0);
		blackList.setId(id);
		
		try {
			blackListService.updateById(blackList);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResultData.createError("取消黑名单失败！");
		}
		
		return ResultData.createSuccess("取消成功！");
	}
	
	/**
	 * 黑名单列表展示
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public ResultData list(@RequestBody Map<String, Object> params) {
		Integer currentPage = 1;
		if (!StringUtils.isEmpty(params.get("currentPage"))) {
			currentPage = Integer.parseInt(params.get("currentPage").toString());
		}

		Integer pageSize = 10;
		if (!StringUtils.isEmpty(params.get("pageSize"))) {
			pageSize = Integer.parseInt(params.get("pageSize").toString());
		}

		Page<BlackList> page = new Page<>(currentPage, pageSize);

		Map<String,Object> map = new HashMap<>();
		
		Integer parkingId = null;
		if (!StringUtils.isEmpty(params.get("parkingId"))) {
			parkingId = Integer.parseInt(params.get("parkingId").toString());
		}
		
		String plateNo = null;
		if (!StringUtils.isEmpty(params.get("plateNo"))) {
			plateNo = "%"+params.get("plateNo").toString()+"%";
		}
		
		String plateColorNo = null;
		if (!StringUtils.isEmpty(params.get("plateColorNo"))) {
			plateColorNo = params.get("plateColorNo").toString();
		}
		
		map.put("plateNo", plateNo);
		map.put("parkingId", parkingId);
		map.put("plateColorNo", plateColorNo);
		Page<BlackList> pageList = blackListService.findAll(page,map);
		
		return ResultData.createSuccess(pageList,"查询成功！");
	}
	
	
	private ResultData checkBlackParams(BlackList blackList) {
		Integer parkingId = blackList.getParkingId();
		if(StringUtils.isEmpty(parkingId)) {
			return ResultData.createError("请选择车场名称！");
		}
		
		String plateNo = blackList.getPlateNo();
		if(StringUtils.isEmpty(plateNo)) {
			return ResultData.createError("请输入车牌号！");
		}
		
		String plateColorName = blackList.getPlateColorName();
		if(StringUtils.isEmpty(plateColorName)) {
			return ResultData.createError("请选择车牌颜色！");
		}
		
		Integer blackType = blackList.getBlackType();
		if(StringUtils.isEmpty(blackType)) {
			return ResultData.createError("请选择黑名单类型！");
		}
		
		return ResultData.createSuccess("");
	}
}

