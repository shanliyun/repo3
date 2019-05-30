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
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.WhiteList;
import com.wynlink.park_platform.service.WhiteListService;

/**
 * <p>
 * 白名单车辆 前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-04-28
 */
@RestController
@RequestMapping("/whiteList")
public class WhiteListController { 
	
	@Autowired
	private WhiteListService whiteListService;
	
	@RequestMapping("/add")
	public ResultData add(@RequestBody WhiteList whiteList) {
		
		/**
		 * 检查非空参数
		 */
		ResultData checkWhiteParams = this.checkWhiteParams(whiteList);
		if(checkWhiteParams.getStatus() == 1) {
			return checkWhiteParams;
		}
		
		int count = whiteListService.selectCount(new EntityWrapper<WhiteList>()
				.eq("parking_id", whiteList.getParkingId())
				.eq("plate_no", whiteList.getPlateNo()));
		if(count > 0) {
			return ResultData.createError("勿重复添加白名单!");
		}
		
		whiteList.setCreateTime(new Date());
		whiteList.setUpdateTime(new Date());
		
		try {
			whiteListService.insert(whiteList);
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
		
		WhiteList vo = whiteListService.findById(id);
		
		return ResultData.createSuccess(vo, "查询成功！");
	}
	
	/**
	 * 修改黑名单
	 * @param blackList
	 * @return
	 */
	@RequestMapping("/update")
	public ResultData update(@RequestBody WhiteList whiteList) {
		
		Integer id = whiteList.getId();
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		/**
		 * 检查非空参数
		 */
		ResultData checkWhiteParams = this.checkWhiteParams(whiteList);
		if(checkWhiteParams.getStatus() == 1) {
			return checkWhiteParams;
		}
		/**
		 * 检查重复
		 */
		int count = whiteListService.selectCount(new EntityWrapper<WhiteList>()
				.eq("parking_id", whiteList.getParkingId())
				.eq("plate_no", whiteList.getPlateNo())
				.ne("id", id));
		if (count > 0) {
			return ResultData.createError("改车牌已设置过白名单！");
		}
		
		whiteList.setUpdateTime(new Date());
		
		try {
			whiteListService.updateById(whiteList);
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
	@RequestMapping("/delete")
	public ResultData delete(@RequestParam("id") Integer id) {
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		
		try {
			whiteListService.deleteById(id);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResultData.createError("删除白名单失败！");
		}
		
		return ResultData.createSuccess("删除成功！");
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

		Page<WhiteList> page = new Page<>(currentPage, pageSize);

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
		Page<WhiteList> pageList = whiteListService.findAll(page,map);
		
		return ResultData.createSuccess(pageList,"查询成功！");
	}
	
	private ResultData checkWhiteParams(WhiteList whiteList) {
		Integer parkingId = whiteList.getParkingId();
		if(StringUtils.isEmpty(parkingId)) {
			return ResultData.createError("请选择车场名称！");
		}
		
		String plateNo = whiteList.getPlateNo();
		if(StringUtils.isEmpty(plateNo)) {
			return ResultData.createError("请输入车牌号！");
		}
		
		String plateColorName = whiteList.getPlateColorName();
		if(StringUtils.isEmpty(plateColorName)) {
			return ResultData.createError("请选择车牌颜色！");
		}
		
		
		return ResultData.createSuccess("");
	}

}

