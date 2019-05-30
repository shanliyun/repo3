package com.wynlink.park_platform.controller;

import java.util.Calendar;
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
import com.wynlink.park_platform.entity.HolidayTime;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.vo.FeeRuleVo;
import com.wynlink.park_platform.service.HolidayTimeService;

@RestController
@RequestMapping("/holiday")
public class HolidayController {
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private HolidayTimeService holidayTimeService;
	
	/**
	 * 加载节假日
	 * @return
	 */
	@RequestMapping("/init")
	public ResultData init() {
		
		List<Map<String,Object>> list = 
				jdbcTemplate.queryForList("SELECT holiday_id AS holidayId,holiday FROM holiday_info");
		
		return ResultData.createSuccess(list,"");
	}
	
	/**
	 * 添加假期
	 * @return
	 */
	@RequestMapping("/add")
	public ResultData add(@RequestBody HolidayTime holidayTime) {
		
		ResultData checkHolidayParams = this.checkHolidayParams(holidayTime);
		if(checkHolidayParams.getStatus() == 1) {
			return checkHolidayParams;
		}
		
		Calendar date = Calendar.getInstance();
		holidayTime.setYear(date.get(Calendar.YEAR));
		int count = holidayTimeService.selectCount(new EntityWrapper<HolidayTime>()
				.eq("year", holidayTime.getYear())
				.eq("holiday_id", holidayTime.getHolidayId()));
		if(count > 0) {
			return ResultData.createError("勿添加重复假日！");
		}
		
		
		holidayTime.setCreateTime(new Date());
		holidayTime.setUpdateTime(new Date());
		
		try {
			holidayTimeService.insert(holidayTime);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("添加失败！");
		}
		
		return ResultData.createSuccess("添加成功！");
	}
	
	/**
	 * 假期列表
	 * @return
	 */
	@RequestMapping("/list")
	public ResultData list() {
		
		List<HolidayTime> list = holidayTimeService.selectList(null);
		if(list != null && list.size() > 0) {
			for (HolidayTime holidayTime : list) {
				Integer holidayId = holidayTime.getHolidayId();
				Map<String, Object> map = jdbcTemplate.queryForMap("SELECT holiday FROM holiday_info WHERE holiday_id=?",holidayId);
				holidayTime.setHoliday(map.get("holiday").toString());
			}
		}
		
		return ResultData.createSuccess(list,"查询成功！");
	}
	
	/**
	 * 回显假期
	 * @return
	 */
	@RequestMapping("/detail")
	public ResultData detail(Integer id) {
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("假期id不能为空！");
		}
		
		HolidayTime holidayTime = holidayTimeService.selectById(id);
		if(holidayTime == null) {
			return ResultData.createError("假期id有误！");
		}
		
		Integer holidayId = holidayTime.getHolidayId();
		Map<String, Object> map = jdbcTemplate.queryForMap("SELECT holiday FROM holiday_info WHERE holiday_id=?",holidayId);
		holidayTime.setHoliday(map.get("holiday").toString());
		
		return ResultData.createSuccess(holidayTime,"查询成功！");
	}
	
	/**
	 * 修改假期
	 * @return
	 */
	@RequestMapping("/update")
	public ResultData update(@RequestBody HolidayTime holidayTime) {
		ResultData checkHolidayParams = this.checkHolidayParams(holidayTime);
		if(checkHolidayParams.getStatus() == 1) {
			return checkHolidayParams;
		}
		
		Calendar date = Calendar.getInstance();
		holidayTime.setYear(date.get(Calendar.YEAR));
		int count = holidayTimeService.selectCount(new EntityWrapper<HolidayTime>()
				.eq("year", holidayTime.getYear())
				.eq("holiday_id", holidayTime.getHolidayId())
				.ne("id", holidayTime.getId()));
		if(count > 0) {
			return ResultData.createError("勿添加重复假日！");
		}
		
		holidayTime.setUpdateTime(new Date());
		
		try {
			holidayTimeService.updateById(holidayTime);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("修改失败！");
		}
		
		return ResultData.createSuccess("修改成功！");
	}
	

	/**
	 * 删除假期
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultData delete(Integer id) {
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("假期id不能为空！");
		}
		boolean f = holidayTimeService.deleteById(id);
		if(!f) {
			return ResultData.createError("删除失败！");
		}
		
		return ResultData.createSuccess("删除成功！");
	}
	
	private ResultData checkHolidayParams(HolidayTime holidayTime) {

		Integer holidayId = holidayTime.getHolidayId();
		if(StringUtils.isEmpty(holidayId)) {
			return ResultData.createError("请选择假期！");
		}
		
		Date startTime = holidayTime.getStartTime();
		Date endTime = holidayTime.getEndTime();
		if(StringUtils.isEmpty(startTime)||StringUtils.isEmpty(endTime)) {
			return ResultData.createError("请选择开始时间和结束时间！");
		}
		return ResultData.createSuccess("");
	}
}
