package com.wynlink.park_platform.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wynlink.park_platform.entity.PassRuleDate;
import com.wynlink.park_platform.entity.PassRuleHoliday;
import com.wynlink.park_platform.entity.PassRuleTime;
import com.wynlink.park_platform.entity.PassRuleWeek;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.service.PassRuleDateService;
import com.wynlink.park_platform.service.PassRuleHolidayService;
import com.wynlink.park_platform.service.PassRuleTimeService;
import com.wynlink.park_platform.service.PassRuleWeekService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-04-17
 */
@RestController
@RequestMapping("/passRuleTime")
public class PassRuleTimeController {
	
	@Autowired
	private PassRuleTimeService passRuleTimeService;
	
	@Autowired
	private PassRuleDateService passRuleDateService;
	
	@Autowired
	private PassRuleWeekService passRuleWeekService;
	
	@Autowired
	private PassRuleHolidayService passRuleHolidayService;
	
	@Transactional
	@RequestMapping("/setTime")
	public ResultData setTime(@RequestBody PassRuleTime passRuleTime) {
		
		String timeName = passRuleTime.getTimeName();
		Integer parkingId = passRuleTime.getParkingId();
		int count = passRuleTimeService.selectCount(new EntityWrapper<PassRuleTime>()
				.eq("time_name", timeName)
				.eq("parking_id", parkingId));
		if(count > 0) {
			return ResultData.createError("时间名称重复!");
		}
		
		passRuleTime.setCreateTime(new Date());
		passRuleTime.setUpdateTime(new Date());
		try {
			passRuleTimeService.insert(passRuleTime);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("添加时间失败！");
		}
		
		Integer id = passRuleTime.getId();//时间id
		
		List<PassRuleDate> passRuleDates = passRuleTime.getPassRuleDate();
		if(passRuleDates != null && passRuleDates.size() > 0) {
			for (PassRuleDate passRuleDate : passRuleDates) {
				passRuleDate.setTimeId(id);
				passRuleDate.setParkingId(parkingId);
			}
		}
		
		try {
			passRuleDateService.insertBatch(passRuleDates);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("添加时间失败！");
		}
		
		List<PassRuleWeek> passRuleWeeks = passRuleTime.getPassRuleWeek();
		if(passRuleWeeks != null && passRuleWeeks.size() > 0) {
			for (PassRuleWeek passRuleWeek : passRuleWeeks) {
				passRuleWeek.setTimeId(id);
				passRuleWeek.setParkingId(parkingId);
			}
		}
		
		try {
			passRuleWeekService.insertBatch(passRuleWeeks);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("添加时间失败！");
		}
		
		
		List<PassRuleHoliday> passRuleHolidays = passRuleTime.getPassRuleHoliday();
		if(passRuleHolidays != null && passRuleHolidays.size() > 0) {
			for (PassRuleHoliday passRuleHoliday : passRuleHolidays) {
				passRuleHoliday.setTimeId(id);
				passRuleHoliday.setParkingId(parkingId);
			}
		}
		
		try {
			passRuleHolidayService.insertBatch(passRuleHolidays);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("添加时间失败！");
		}
		return ResultData.createSuccess("添加时间成功！");
	}
	
	@RequestMapping("/list")
	public ResultData list() {
		
		List<PassRuleTime> list = passRuleTimeService.selectList(null);
		
		return ResultData.createSuccess(list,"");
	}
	
	@RequestMapping("/detail")
	public ResultData detail(Integer id) {
		
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		PassRuleTime ruleTime = passRuleTimeService.selectById(id);
		if(ruleTime == null) {
			return ResultData.createError("该id有误！");
		}
		ruleTime.setPassRuleDate(passRuleDateService.selectList(new EntityWrapper<PassRuleDate>()
				.eq("time_id", id)));
		ruleTime.setPassRuleHoliday(passRuleHolidayService.selectList(new EntityWrapper<PassRuleHoliday>()
						.eq("time_id", id)));
		ruleTime.setPassRuleWeek(passRuleWeekService.selectList(new EntityWrapper<PassRuleWeek>()
						.eq("time_id", id)));
		
		return ResultData.createSuccess(ruleTime,"");
	}
	
	
	
	@Transactional
	@RequestMapping("/updateTime")
	public ResultData updateTime(@RequestBody PassRuleTime passRuleTime) {
		
		Integer id = passRuleTime.getId();
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		String timeName = passRuleTime.getTimeName();
		int count = passRuleTimeService.selectCount(new EntityWrapper<PassRuleTime>()
				.eq("time_name", timeName)
				.ne("id", id));
		if(count > 0) {
			return ResultData.createError("时间名称重复!");
		}
		passRuleTime.setUpdateTime(new Date());
		try {
			passRuleTimeService.updateById(passRuleTime);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("修改时间失败！");
		}
		
		List<PassRuleDate> passRuleDates = passRuleTime.getPassRuleDate();
		if(passRuleDates != null && passRuleDates.size() > 0) {
			
			for (PassRuleDate passRuleDate : passRuleDates) {
				passRuleDate.setTimeId(id);
			}
			
			/**
			 * 先删除此id下的日期表数据
			 */
			try {
				passRuleDateService.delete(new EntityWrapper<PassRuleDate>()
						.eq("time_id", id));
				passRuleDateService.insertBatch(passRuleDates);
			} catch (Exception e) {
				e.printStackTrace();
				return ResultData.createError("修改时间失败！");
			}
		}
		
		List<PassRuleWeek> passRuleWeeks = passRuleTime.getPassRuleWeek();
		if(passRuleWeeks != null && passRuleWeeks.size() > 0) {
			
			for (PassRuleWeek passRuleWeek : passRuleWeeks) {
				passRuleWeek.setTimeId(id);
			}
			try {
				passRuleWeekService.delete(new EntityWrapper<PassRuleWeek>()
						.eq("time_id", id));
				passRuleWeekService.insertBatch(passRuleWeeks);
			} catch (Exception e) {
				e.printStackTrace();
				return ResultData.createError("修改时间失败！");
			}
		}
		
		List<PassRuleHoliday> passRuleHolidays = passRuleTime.getPassRuleHoliday();
		if(passRuleHolidays != null && passRuleHolidays.size() > 0) {
			for (PassRuleHoliday passRuleHoliday : passRuleHolidays) {
				passRuleHoliday.setTimeId(id);
			}
			
			try {
				passRuleHolidayService.delete(new EntityWrapper<PassRuleHoliday>()
						.eq("time_id", id));
				passRuleHolidayService.insertBatch(passRuleHolidays);
			} catch (Exception e) {
				e.printStackTrace();
				return ResultData.createError("修改时间失败！");
			}
		}
		
		return ResultData.createSuccess("修改时间成功！");
	}
	
	
	
	@RequestMapping("/delete")
	public ResultData delete(Integer id) {
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		boolean flag = passRuleTimeService.deleteById(id);
		if(flag) {
			/**
			 * 同步删除字表
			 */
			try {
				passRuleDateService.delete(new EntityWrapper<PassRuleDate>()
						.eq("time_id", id));
				passRuleWeekService.delete(new EntityWrapper<PassRuleWeek>()
						.eq("time_id", id));
				passRuleHolidayService.delete(new EntityWrapper<PassRuleHoliday>()
						.eq("time_id", id));
			} catch (Exception e) {
				e.printStackTrace();
				return ResultData.createError("删除失败");
			}
		} else {
			return ResultData.createError("删除失败");
		}
		
		return ResultData.createSuccess("删除成功！");
	}
	
	

}

