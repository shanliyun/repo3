package com.wynlink.park_platform.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wynlink.park_platform.entity.FeeRule;
import com.wynlink.park_platform.entity.FeeRuleConfig;
import com.wynlink.park_platform.entity.FeeRuleConfig2;
import com.wynlink.park_platform.entity.ParkingInfo;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.vo.FeeRuleVo;
import com.wynlink.park_platform.service.FeeRuleConfig2Service;
import com.wynlink.park_platform.service.FeeRuleConfigService;
import com.wynlink.park_platform.service.FeeRuleService;
import com.wynlink.park_platform.service.ParkingInfoService;

/**
 * 收费规则配置
 * @author vincent
 *
 */
@RestController
@RequestMapping("/feeRule")
public class FeeRuleController {
	
	
	@Autowired
	private FeeRuleService feeRuleService;
	
	@Autowired
	private FeeRuleConfigService feeRuleConfigService;
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate JdbcTemplate;
	
	@Autowired
	private ParkingInfoService parkingInfoService;
	@Autowired
	private FeeRuleConfig2Service feeRuleConfig2Service;
	/**
	 * 初始化周期类型
	 */
	@RequestMapping("/initCycleType")
 	public ResultData initCycleType() {
		
		List<Map<String,Object>> list = JdbcTemplate.queryForList("SELECT cycle_type_id AS cycleTypeId,"
				+ "cycle_type_name AS cycleTypeName FROM fee_rule_cycle_type");
		return ResultData.createSuccess(list, "");
	}
	/**
	 * 添加收费规则
	 */
	@Transactional
	@RequestMapping("/addFeeRule")
 	public ResultData addFeeRule(@RequestBody FeeRule feeRule) {
		Integer parkingId = feeRule.getParkingId();
		String feeRuleName = feeRule.getFeeRuleName();
		
		int count = feeRuleService.selectCount(new EntityWrapper<FeeRule>()
				.eq("parking_id", parkingId)
				.eq("fee_rule_name", feeRuleName));
		if(count > 0) {
			return ResultData.createError("规则已存在！");
		}
		
		
		feeRule.setCreateTime(new Date());
		feeRule.setUpdateTime(new Date());
		
		try {
			feeRuleService.insert(feeRule);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("添加收费规则失败！");
		}
		
		Integer id = feeRule.getId();//收费规则id
		
		List<FeeRuleConfig> feeRuleConfigs = feeRule.getFeeRuleConfigs();
		
		if(feeRuleConfigs != null && feeRuleConfigs.size() > 0) {
			/**
			 * 插入配置表
			 */
			
			for (FeeRuleConfig feeRuleConfig : feeRuleConfigs) {
				feeRuleConfig.setParkingId(parkingId);
				feeRuleConfig.setFeeRuleId(id);
				Integer cycleTypeId = feeRuleConfig.getCycleTypeId();
				if(cycleTypeId == 1) {
					Integer startMin = feeRuleConfig.getStartMin();
					Integer endMin = feeRuleConfig.getEndMin();
					feeRuleConfig.setCycleMin(endMin-startMin);
				} else if(cycleTypeId == 2) {
					feeRuleConfig.setCycleMin(30);
				} else if(cycleTypeId == 3) {
					feeRuleConfig.setCycleMin(60);
				}
			}
			
			try {
				feeRuleConfigService.insertBatch(feeRuleConfigs);
			} catch (Exception e) {
				e.printStackTrace();
				return ResultData.createError("添加收费规则失败！");
			}
			
		} 
		
		List<FeeRuleConfig2> feeRuleConfig2s = feeRule.getFeeRuleConfig2s();
		if(feeRuleConfig2s != null && feeRuleConfig2s.size() > 0) {
			for (FeeRuleConfig2 feeRuleConfig2 : feeRuleConfig2s) {
				feeRuleConfig2.setParkingId(parkingId);
				feeRuleConfig2.setFeeRuleId(id);
			}
			
			try {
				feeRuleConfig2Service.insertBatch(feeRuleConfig2s);
			} catch (Exception e) {
				e.printStackTrace();
				return ResultData.createError("添加收费规则失败！");
			}
		}
		
		
		
		return ResultData.createSuccess("添加收费规则成功！");
	}
	
	
	/**
	 * 展示收费规则列表
	 */
	@RequestMapping("/feeRuleList")
 	public ResultData feeRuleList(@RequestBody Map<String,Object> params) {
		
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
		
		String feeRuleName = null;
		if(!StringUtils.isEmpty(params.get("feeRuleName"))) {
			feeRuleName = "%"+params.get("feeRuleName").toString()+"%";
		}
		
		Integer status = null;
		if(!StringUtils.isEmpty(params.get("status"))) {
			status = Integer.parseInt(params.get("status").toString());
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("parkingId", parkingId);
		map.put("feeRuleName", feeRuleName);
		map.put("status", status);
		Page<FeeRuleVo> page = feeRuleService.findAll(new Page<>(currentPage,pageSize), map);
		
		
		return ResultData.createSuccess(page, "查询成功");
	}
	
	/**
	 * 规则详情/回显
	 */
	@RequestMapping("/detail")
 	public ResultData detail(Integer id) {
		
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		FeeRule feeRule = feeRuleService.selectById(id);
		
		/*FeeRuleConfigOne feeRuleConfigOne = feeRuleConfigOneService.selectOne(
				new EntityWrapper<FeeRuleConfigOne>().eq("fee_rule_id", feeRule.getId()));
		
		FeeRuleConfigTwo feeRuleConfigTwo = feeRuleConfigTwoService.selectOne(
				new EntityWrapper<FeeRuleConfigTwo>().eq("fee_rule_id", feeRule.getId()));
		
		FeeRuleConfigThree feeRuleConfigThree = feeRuleConfigThreeService.selectOne(
				new EntityWrapper<FeeRuleConfigThree>().eq("fee_rule_id", feeRule.getId()));
		*/
		
		List<FeeRuleConfig> list = feeRuleConfigService.selectList(new EntityWrapper<FeeRuleConfig>().eq("fee_rule_id", feeRule.getId()));
		/*feeRule.setFeeRuleConfigOne(feeRuleConfigOne);
		feeRule.setFeeRuleConfigTwo(feeRuleConfigTwo);
		feeRule.setFeeRuleConfigThree(feeRuleConfigThree);*/
		feeRule.setFeeRuleConfigs(list);
		Integer parkingId = feeRule.getParkingId();
		ParkingInfo parkingInfo = parkingInfoService.selectById(parkingId);
		feeRule.setParkingName(parkingInfo.getParkingName());
		return ResultData.createSuccess(feeRule, "查询成功！");
	}
	
	
	/**
	 * 为车辆类型修改收费规则
	 */
	@Transactional
	@RequestMapping("/update")
 	public ResultData update(@RequestBody FeeRule feeRule) {
		
		if(StringUtils.isEmpty(feeRule.getId())) {
			return ResultData.createError("id不能为空！");
		}
		
		Integer parkingId = feeRule.getParkingId();
		String feeRuleName = feeRule.getFeeRuleName();
		
		int count = feeRuleService.selectCount(new EntityWrapper<FeeRule>()
				.eq("parking_id", parkingId)
				.eq("fee_rule_name", feeRuleName)
				.ne("id", feeRule.getId()));
		if(count > 0) {
			return ResultData.createError("规则已存在！");
		}
		
		feeRule.setUpdateTime(new Date());
		try {
			feeRuleService.updateById(feeRule);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("修改收费规则失败！");
		}
		
		/**
		 * 插入配置表
		 */
		List<FeeRuleConfig> feeRuleConfigs = feeRule.getFeeRuleConfigs();
		if(feeRuleConfigs != null && feeRuleConfigs.size() > 0) {
			for (FeeRuleConfig feeRuleConfig : feeRuleConfigs) {
				feeRuleConfig.setParkingId(parkingId);
				feeRuleConfig.setFeeRuleId(feeRule.getId());
				Integer cycleTypeId = feeRuleConfig.getCycleTypeId();
				if(cycleTypeId == 1) {
					Integer startMin = feeRuleConfig.getStartMin();
					Integer endMin = feeRuleConfig.getEndMin();
					feeRuleConfig.setCycleMin(endMin-startMin);
				} else if(cycleTypeId == 2) {
					feeRuleConfig.setCycleMin(30);
				} else if(cycleTypeId == 3) {
					feeRuleConfig.setCycleMin(60);
				}
			}
		}
		
		try {
			feeRuleConfigService.delete(new EntityWrapper<FeeRuleConfig>().eq("fee_rule_id", feeRule.getId()));
			feeRuleConfigService.insertBatch(feeRuleConfigs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*FeeRuleConfigOne feeRuleConfigOne = feeRule.getFeeRuleConfigOne();
		try {
			feeRuleConfigOneService.updateById(feeRuleConfigOne);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FeeRuleConfigTwo feeRuleConfigTwo = feeRule.getFeeRuleConfigTwo();
		
		try {
			feeRuleConfigTwoService.updateById(feeRuleConfigTwo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FeeRuleConfigThree feeRuleConfigThree = feeRule.getFeeRuleConfigThree();
		try {
			feeRuleConfigThreeService.updateById(feeRuleConfigThree);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return ResultData.createSuccess("修改收费规则成功！");
	}
	

	/**
	 * 收费规则删除
	 */
	@RequestMapping("/delete")
 	public ResultData delete(Integer id) {
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		FeeRule feeRule  = new FeeRule();
		feeRule.setId(id);
		feeRule.setStatus(0);
		
		try {
			feeRuleService.updateById(feeRule);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("删除失败！");
		}
		return ResultData.createSuccess("删除成功！");
	}

}
