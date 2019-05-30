package com.wynlink.park_platform.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.MonthCard;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.SysUser;
import com.wynlink.park_platform.entity.vo.MonthCardVo;
import com.wynlink.park_platform.service.MonthCardService;
import com.wynlink.park_platform.utils.DateFormatUtil;

/**
 * <p>
 * 月卡 前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-05-29
 */
@RestController
@RequestMapping("/monthCard")
public class MonthCardController {
	
	@Autowired
	private MonthCardService monthCardService;
	
	/**
	 * 月卡办理
	 * @param monthCard
	 * @return
	 */
	@PostMapping("/add")
	public ResultData add(@RequestBody MonthCard monthCard) {
		
		Integer months = monthCard.getMonths();
		if(StringUtils.isEmpty(months)) {
			return ResultData.createError("请输入月数！");
		}
		
		String plateNo = monthCard.getPlateNo();
		String plateColor = monthCard.getPlateColor();
		//当前登录用户
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		
		/**
		 * 查询车牌号有无办理过月卡
		 */
		int count = monthCardService.selectCount(new EntityWrapper<MonthCard>()
				.eq("group_id", user.getGroupId())
				.eq("plate_no", plateNo)
				.eq("plate_color", plateColor));
		if(count > 0) {
			return ResultData.createError("勿重复办理月卡！");
		}
		
		Date endTime = DateFormatUtil.addMonths(new Date(), months);
		monthCard.setEndTime(endTime);
		monthCard.setTransactor(user.getUserName());
		monthCard.setGroupId(user.getGroupId());
		monthCard.setCreateTime(new Date());
		monthCard.setUpdateTime(new Date());
		
		try {
			monthCardService.insert(monthCard);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("开通失败！");
		}
		
		return ResultData.createSuccess("开通成功！");
	}
	
	/**
	 * 月卡车辆列表
	 */
	@PostMapping("/list")
	public ResultData list(@RequestBody Map<String,Object> params) {
		
		Integer currentPage = 1;
		if(!StringUtils.isEmpty(params.get("currentPage"))) {
			currentPage = Integer.parseInt(params.get("currentPage").toString());
		}
		
		Integer pageSize = 10;
		if(!StringUtils.isEmpty(params.get("pageSize"))) {
			pageSize = Integer.parseInt(params.get("pageSize").toString());
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		//车场Id
		Integer parkingId = null;
		if(!StringUtils.isEmpty(params.get("parkingId"))) {
			parkingId = Integer.parseInt(params.get("parkingId").toString());
		}
		map.put("parkingId", parkingId);
		//车牌号码
		String plateNo = null;
		if(!StringUtils.isEmpty(params.get("plateNo"))) {
			plateNo = "'" + params.get("plateNo")+ "'";
		}
		
		map.put("plateNo", plateNo);
		
		//车牌颜色
		String plateColor = null;
		if(!StringUtils.isEmpty(params.get("plateColor"))) {
			plateColor = params.get("plateColor").toString();
		}
		map.put("plateColor", plateColor);
		
		//车主姓名
		String owner = null;
		if(!StringUtils.isEmpty(params.get("owner"))) {
			owner = "'" + params.get("owner")+ "'";
		}
		map.put("owner", owner);
		
		//车位号
		String spaceNo = null;
		if(!StringUtils.isEmpty(params.get("spaceNo"))) {
			spaceNo = "'" + params.get("spaceNo")+ "'";
		}
		map.put("spaceNo", spaceNo);
		
		//到期时间区间
		String start = null;
		String end = null;
		if(!StringUtils.isEmpty(params.get("start")) && !StringUtils.isEmpty(params.get("end"))) {
			start = params.get("start").toString();
			end = params.get("end").toString();
		}
		map.put("start", start);
		map.put("end", end);
		
		Page<MonthCardVo> page = monthCardService.findAllPage(new Page<MonthCardVo>(currentPage, pageSize),map);
		
		return null;
	}
}

