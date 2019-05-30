package com.wynlink.park_platform.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.PassRule;
import com.wynlink.park_platform.entity.PlateFeature;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.vo.PassRuleVo;
import com.wynlink.park_platform.service.PassRuleService;
import com.wynlink.park_platform.service.PlateFeatureService;

/**
 * <p>
 * 车辆通行规则表 前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-04-08
 */
@RestController
@RequestMapping("/passRule")
public class PassRuleController {
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	

	@Autowired
	private PassRuleService passRuleService;
	
	@Autowired
	private PlateFeatureService plateFeatureService;
	/**
	 * 加载出入口信息
	 */
	@RequestMapping("/initEnterExit")
	public ResultData initEnterExit(Integer parkingId) {
		
		List<Map<String,Object>> list = jdbcTemplate.queryForList(
				"SELECT id,enter_exit_name AS enterExitName"
				+ " FROM enter_exit_info WHERE parking_id=?",parkingId);
		
		return ResultData.createSuccess(list,"");
	}
	
	/**
	 * 加载通行类型
	 */
	@RequestMapping("/initPassType")
	public ResultData initPassType() {
		List<Map<String,Object>> list = jdbcTemplate.queryForList(
				"SELECT pass_type_id AS passTypeId,pass_type AS passType FROM pass_type_info");
		
		return ResultData.createSuccess(list,"");
	}
	
	/**
	 * 加载收费类型
	 */
	@RequestMapping("/initFeeType")
	public ResultData initFeeType() {
		List<Map<String,Object>> list = jdbcTemplate.queryForList(
				"SELECT fee_type_id AS feeTypeId,fee_type AS feeType FROM fee_type_info");
		
		return ResultData.createSuccess(list,"");
	}
	
	/**
	 * 加载收费规则/费率表
	 */
	@RequestMapping("/initFeeRule")
	public ResultData initFeeRule(Integer parkingId) {
		
		List<Map<String,Object>> list = jdbcTemplate.queryForList(
				"SELECT id,fee_rule_name AS feeRuleName"
				+ " FROM fee_rule WHERE parking_id=?",parkingId);
		
		return ResultData.createSuccess(list,"");
	}
	
	
	/**
	 * 设置通行规则
	 */
	@RequestMapping("/add")
	public ResultData add (@RequestBody PassRule passRule) {
		
		/**
		 * 检查必填字段
		 */
		Integer parkingId = passRule.getParkingId();
		if(StringUtils.isEmpty(parkingId)) {
			return ResultData.createError("请选择停车场！");
		}
		
		String passRuleName = passRule.getPassRuleName();
		if(StringUtils.isEmpty(passRuleName)) {
			return ResultData.createError("请输入通行规则名称！");
		}
		
		/*Integer timeId = passRule.getTimeId();
		if(StringUtils.isEmpty(timeId)) {
			return ResultData.createError("请选择时间！");
		}*/
		
		String facility = passRule.getFacility();
		if(StringUtils.isEmpty(facility)) {
			return ResultData.createError("请选择设施！");
		}
		
		Integer vehicleClassifyId = passRule.getVehicleClassifyId();
		if(StringUtils.isEmpty(vehicleClassifyId)) {
			return ResultData.createError("请选择车辆分类！");
		}
		
		Integer feeRuleId = passRule.getFeeRuleId();
		if(StringUtils.isEmpty(feeRuleId)) {
			return ResultData.createError("请选择费率表！");
		}
		
		String allowType = passRule.getAllowType();
		if(StringUtils.isEmpty(allowType)) {
			return ResultData.createError("请选择许可类型！");
		}
		
		Integer passTypeId = passRule.getPassTypeId();
		if(StringUtils.isEmpty(passTypeId)) {
			return ResultData.createError("请选择通行类型！");
		}
		
		Integer feeTypeId = passRule.getFeeTypeId();
		if(StringUtils.isEmpty(feeTypeId)) {
			return ResultData.createError("请选择收费类型！");
		}
		
		/**
		 * 检查该车辆类型有无设置过
		 */
		int count = passRuleService.selectCount(new EntityWrapper<PassRule>()
				.eq("parking_id", parkingId)
				.eq("pass_rule_name", passRuleName));
		if(count > 0) {
			return ResultData.createError("规则已重复！");
		}
			
		passRule.setCreateTime(new Date());
		passRule.setUpdateTime(new Date());
		
		try {
			passRuleService.insert(passRule);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("设置通行规则失败！");
		}
		
		Integer id = passRule.getId();//通行规则id
		
		/**
		 * 插入车牌特征
		 */
		PlateFeature plateFeature = passRule.getPlateFeature();
		plateFeature.setParkingId(parkingId);
		plateFeature.setPassRuleId(id);
		try {
			plateFeatureService.insert(plateFeature);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("设置通行规则失败！");
		}
		
		
		return ResultData.createSuccess("设置规则成功！");
		
	}
	
	/**
	 * 车辆类型通行规则列表展示
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public ResultData list (@RequestBody Map<String,Object> params){
		
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
		Integer vehicleClassifyId = null;
		if(!StringUtils.isEmpty(params.get("vehicleClassifyId"))) {
			vehicleClassifyId = Integer.parseInt(params.get("vehicleClassifyId").toString());
		}
		
		
		Map<String,Object> map = new HashMap<>();
		map.put("parkingId", parkingId);
		map.put("vehicleClassifyId", vehicleClassifyId);
		
		Page<PassRuleVo> page = passRuleService.findAll(new Page<>(currentPage,pageSize),map);
		
		
		return ResultData.createSuccess(page, "查询成功！");
	}
	
	/**
	 * 车辆类型通行规则回显/查看详情
	 */
	@RequestMapping("/detail")
	public ResultData detail (Integer id) {
		
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		PassRule passRule = passRuleService.selectById(id);
		
		if(passRule == null) {
			return ResultData.createError("id不存在！");
		}
		
		Map<String,Object> data = new LinkedHashMap<>();
		
		/**
		 * 查询车场名称并添加到map
		 */
		Integer parkingId = passRule.getParkingId();//车场id
		Map<String, Object> parkingNameMap = jdbcTemplate.queryForMap(
				"SELECT parking_name AS parkingName FROM parking_info "
				+ "WHERE id=?",parkingId);
		String parkingName = parkingNameMap.get("parkingName").toString();
		data.put("parkingName", parkingName);
		/**
		 * 封装通行规则名称
		 */
		data.put("passRuleName", passRule.getPassRuleName());
		/**
		 * 查询时间
		 */
		Integer timeId = passRule.getTimeId();
		Map<String, Object> passRuleTimeMap = null;
		try {
			passRuleTimeMap = jdbcTemplate.queryForMap(
					"SELECT id,time_name AS timeName FROM pass_rule_time "
					+ "WHERE id=?",timeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		data.put("passRuleTime", passRuleTimeMap);
		/**
		 * 车场设施
		 */
		data.put("facility", passRule.getFacility());
		/**
		 * 车牌颜色
		 */
		Integer plateColorNo = passRule.getPlateColorNo();
		Map<String, Object> plateColorMap = new HashMap<>();
		if(!StringUtils.isEmpty(plateColorNo)) {
			Map<String, Object> colorMap = jdbcTemplate.queryForMap(
					"SELECT plate_color_no AS plateColorNo,plate_color AS plateColor FROM plate_color_info "
					+ "WHERE plate_color_no=?",plateColorNo);
			
			plateColorMap = colorMap;
		} else {
			plateColorMap.put("plateColorNo", "");
			plateColorMap.put("plateColor", "");
		}
		
		data.put("plateColor", plateColorMap);
		/**
		 * 车辆类型
		 */
		Integer vehicleTypeId = passRule.getVehicleTypeId();//车辆类型id
		Map<String, Object> vehicleTypeMap = new HashMap<>();
		if(!StringUtils.isEmpty(vehicleTypeId)) {
			Map<String, Object> typeMap = jdbcTemplate.queryForMap(
					"SELECT id,vehicle_type_name AS vehicleTypeName FROM vehicle_type_info "
					+ "WHERE id=?",vehicleTypeId);
			vehicleTypeMap = typeMap;
		} else {
			vehicleTypeMap.put("id", "");
			vehicleTypeMap.put("vehicleTypeName", "");
		}
		
		data.put("vehicleType", vehicleTypeMap);
		/**
		 * 车辆核载人数
		 */
		data.put("vehicleHzrs", passRule.getVehicleHzrs());
		
		/**
		 * 许可类型
		 */
		data.put("allowType", passRule.getAllowType());
		/**
		 * 通行类型
		 */
		Integer passTypeId = passRule.getPassTypeId();//通行类型id
		Map<String, Object> passTypeMap = jdbcTemplate.queryForMap(
				"SELECT pass_type_id AS passTypeId,pass_type AS passType FROM pass_type_info "
				+ "WHERE pass_type_id=?",passTypeId);
		data.put("passType", passTypeMap);
		/**
		 * 收费类型
		 */
		Integer feeTypeId = passRule.getFeeTypeId();
		Map<String, Object> feeTypeMap = jdbcTemplate.queryForMap(
				"SELECT fee_type_id AS feeTypeId,fee_type AS feeType FROM fee_type_info "
				+ "WHERE fee_type_id=?",feeTypeId);
		data.put("feeType", feeTypeMap);
		
		/**
		 * 车牌特征
		 */
		Map<String, Object> plateTzMap = jdbcTemplate.queryForMap(
				"SELECT tail_num_type AS tailNumType,tail_num AS tailNum FROM plate_feature "
				+ "WHERE pass_rule_id=?",id);
		data.put("plateFeature", plateTzMap);
		
		/**
		 * 查询车辆分类
		 */
		Integer vehicleClassifyId = passRule.getVehicleClassifyId();//车辆分类编号
		
		Map<String, Object> vehicleClassifyMap = jdbcTemplate.queryForMap(
				"SELECT id,vehicle_classify_name AS vehicleClassifyName FROM vehicle_classify "
				+ "WHERE id=?",vehicleClassifyId);
		data.put("vehicleClassify", vehicleClassifyMap);
		/**
		 * 查询收费规则
		 */
		Integer feeRuleId = passRule.getFeeRuleId();
		
		Map<String, Object> feeRuleMap = jdbcTemplate.queryForMap(
				"SELECT id,fee_rule_name AS feeRuleName FROM fee_rule "
				+ "WHERE id=?",feeRuleId);
		data.put("feeRule", feeRuleMap);
		
		
		
		return ResultData.createSuccess(data, "查询成功！");
	}
	
	/**
	 * 车辆类型通行规则修改
	 * @param vehiclePassRule
	 * @return
	 */
	@RequestMapping("/update")
	public ResultData update (@RequestBody PassRule passRule) {
		
		Integer id = passRule.getId();
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		Integer parkingId = passRule.getParkingId();
		String passRuleName = passRule.getPassRuleName();
		
		int count = passRuleService.selectCount(new EntityWrapper<PassRule>()
				.eq("parking_id", parkingId)
				.eq("pass_rule_name", passRuleName)
				.ne("id", id));
		if(count > 0) {
			return ResultData.createError("该车辆类型已设置过该通行规则！");
		}
			
		passRule.setUpdateTime(new Date());
		
		try {
			passRuleService.updateById(passRule);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("修改通行规则失败！");
		}
		
		PlateFeature plateFeature = passRule.getPlateFeature();
		
		try {
			plateFeatureService.update(plateFeature, new EntityWrapper<PlateFeature>()
					.eq("pass_rule_id", id));
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("修改通行规则失败！");
		}
		return ResultData.createSuccess("修改成功！");
	}
	
	/**
	 * 通行规则删除
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultData delete (Integer id) {
		
		if(StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		
		PassRule passRule = new PassRule();
		passRule.setId(id);
		passRule.setStatus(0);
		
		try {
			passRuleService.updateById(passRule);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("删除失败！");
		}
		
		return ResultData.createSuccess("删除成功！");
	}

	
}

