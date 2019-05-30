package com.wynlink.park_platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.service.PicUnusualService;
import com.wynlink.park_platform.utils.PageModel;

@RestController
@RequestMapping("/pic")
public class PicUnusualController {
	
	@Autowired
	private PicUnusualService picUnusualService;
	
	/**
	 * 问题车牌百分比
	 * @param type
	 * @return
	 */
	@RequestMapping("/percent")
	public ResultData percent(Integer type) {
		
		if(StringUtils.isEmpty(type)) {
			return ResultData.createError("类型不能为空！");
		}
		
		String percent = picUnusualService.percent(type);
			
		
		return ResultData.createSuccess(percent, "查询成功！");
	}
	
	@RequestMapping("/list")
	public ResultData list(@RequestBody Map<String,Object> params) {
		
		Integer type = null;
		
		if(StringUtils.isEmpty(params.get("type"))) {
			return ResultData.createError("类型不能为空！");
		}
		
		type = Integer.parseInt(params.get("type").toString());
		
		Integer currentPage = 1;
		if(!StringUtils.isEmpty(params.get("currentPage"))) {
			currentPage = Integer.parseInt(params.get("currentPage").toString());
		}
		
		Integer pageSize = 10;
		if(!StringUtils.isEmpty(params.get("pageSize"))) {
			pageSize = Integer.parseInt(params.get("pageSize").toString());
		}
		
		List<Map<String,Object>> list = picUnusualService.list(type);
		
		PageModel<String> pm = new PageModel(list, pageSize);

		List<String> sublist = pm.getObjects(currentPage);	
			
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("totalRows", pm.getTotalRows());
		map.put("records",sublist);
		return ResultData.createSuccess(map, "查询成功！");
	}
	

}
