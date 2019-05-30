package com.wynlink.park_platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.service.BlackListService;
import com.wynlink.park_platform.service.VehicleMonthlyService;
import com.wynlink.park_platform.service.WhiteListService;

/**
 * 给晓飞接口
 * 
 * @author vincent
 *
 */
@RestController
@RequestMapping("/XF")
public class ToXFController {

	@Autowired
	private WhiteListService whiteListService;

	@Autowired
	private VehicleMonthlyService vehicleMonthlyService;

	@Autowired
	private BlackListService blackListService;

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * passRule汇总
	 */
	@RequestMapping("/passRuleTotal")
	public ResultData passRuleTotal(Integer parkingId) {

		List<Map<String, Object>> list = jdbcTemplate.queryForList(
				"SELECT a.id AS vehicleClassifyId,a.vehicle_classify_name AS vehicleClassifyName\r\n"
						+ "	,b.pass_rule_name AS passRuleName,b.facility,b.allow_type AS allowType,b.time_id AS timeId,time.time_name AS timeName\r\n"
						+ "	,b.vehicle_hzrs AS hzrs,c.plate_color AS plateColor,d.vehicle_type_name AS vehicleTypeName\r\n"
						+ "	,f.pass_type AS passType,g.fee_type AS feeType\r\n"
						+ "	,h.id AS feeRuleId,h.fee_rule_name AS feeRuleName,h.max_fee AS maxFee\r\n"
						+ "	FROM vehicle_classify a\r\n"
						+ "		LEFT JOIN pass_rule b ON a.id=b.vehicle_classify_id\r\n"
						+ "		LEFT JOIN plate_color_info c ON b.plate_color_no=c.plate_color_no\r\n"
						+ "		LEFT JOIN vehicle_type_info d ON b.vehicle_type_id=d.id\r\n"
						+ "		LEFT JOIN pass_type_info f ON b.pass_type_id=f.pass_type_id\r\n"
						+ "		LEFT JOIN fee_type_info g ON b.fee_type_id=g.fee_type_id\r\n"
						+ "		LEFT JOIN fee_rule h ON b.fee_rule_id=h.id\r\n"
						+ "		LEFT JOIN pass_rule_time time ON b.time_id=time.id\r\n" + "WHERE a.parking_id=?",
				parkingId);

		return ResultData.createSuccess(list, "查询成功！");
	}

	/**
	 * 收费规则
	 */
	@RequestMapping("/feeRule")
	public ResultData feeRule(Integer parkingId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT \r\n" + "	fee_rule_id AS feeRuleId\r\n"
				+ "	,index_num AS indexNum\r\n" + "	,start_min AS startMin\r\n" + "	,end_min AS endTime\r\n"
				+ "	,a.cycle_min AS cycleMin\r\n" + "	,a.cycle_fee AS cycleFee \r\n"
				+ "FROM fee_rule_config a LEFT JOIN fee_rule_cycle_type b ON a.cycle_type_id=b.cycle_type_id WHERE a.parking_id=?",
				parkingId);

		return ResultData.createSuccess(list, "查询成功！");
	}

	/**
	 * 通行规则时间
	 */
	@RequestMapping("/passRuleTime")
	public ResultData passRuleTime(Integer parkingId) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> date = jdbcTemplate.queryForList("SELECT time_id AS timeId"
				+ ",start_date AS startDate" + ",end_date AS endDate" + ",start_time AS startTime"
				+ ",end_time AS endTime " + "FROM pass_rule_date WHERE parking_id=?", parkingId);
		map.put("date", date);
		List<Map<String, Object>> holiday = jdbcTemplate
				.queryForList("SELECT time_id AS timeId" + ",holiday_name AS holidayName" + ",start_time AS startTime"
						+ ",end_time AS endTime " + "FROM pass_rule_holiday WHERE parking_id=?", parkingId);
		map.put("holiday", holiday);
		List<Map<String, Object>> week = jdbcTemplate.queryForList("SELECT time_id AS timeId" + ",what_week AS whatWeek"
				+ ",start_time AS startTime" + ",end_time AS endTime " + "FROM pass_rule_week WHERE parking_id=?",
				parkingId);
		map.put("week", week);
		return ResultData.createSuccess(map, "查询成功！");
	}

	/**
	 * 黑白名单、月租车辆列表
	 */

	@RequestMapping("/blackWhiteRentList")
	public ResultData blackWhiteList(Integer parkingId) {

		/**
		 * 查询白名单车辆
		 */
		List<Map<String, Object>> whiteList = whiteListService.findAll(parkingId);
		/**
		 * 查询黑名单车辆
		 */
		List<Map<String, Object>> blackList = blackListService.findAll(parkingId);
		/**
		 * 查询月租车辆
		 */
		List<Map<String, Object>> rentList = vehicleMonthlyService.findAll(parkingId);
		Map<String, Object> data = new HashMap<>();
		data.put("whiteList", whiteList);
		data.put("blackList", blackList);
		data.put("rentList", rentList);
		return ResultData.createSuccess(data, "查询成功！");
	}
	
	/**
	 * 查询所有车辆
	 */
	@RequestMapping("/vehicle")
	public ResultData vehicle(Integer parkingId) {
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT a.vehicle_classify_id AS vehicleClassifyId"
				+ ",b.vehicle_classify_name AS vehicleClassifyName,a.plate_no AS plateNo,a.plate_color AS plateColor FROM vehicle_info a"
				+ " LEFT JOIN vehicle_classify b ON a.vehicle_classify_id=b.id WHERE a.parking_id=?",parkingId);
		return ResultData.createSuccess(list, "查询成功！");
	}

	/*
	*//**
		 * 通行规则
		 * 
		 * @param parkingId
		 * @return
		 */
	/*
	 * @RequestMapping("/passRuleList") public ResultData passRuleList(Integer
	 * parkingId) {
	 * 
	 * List<Map<String,Object>> passRuleList =
	 * vehiclePassRuleService.findAll(parkingId);
	 * 
	 * return ResultData.createSuccess(passRuleList, "查询成功！"); }
	 * 
	 *//**
		 * 车辆与车辆类型
		 */
	/*
	 * @RequestMapping("/fee") public ResultData feeRuleList(Integer parkingId) {
	 * 
	 * }
	 *//**
		 * 车辆类型与车辆特征
		 */
	/*
	 * @RequestMapping("/vehicleTypeAndFeature") public ResultData
	 * vehicleTypeAndFeature(Integer parkingId) {
	 * 
	 * List<Map<String,Object>> vehicleTypeAndFeature =
	 * vehicleTypeService.findAllVehicleTypeAndFeature(parkingId); return
	 * ResultData.createSuccess(vehicleTypeAndFeature, "查询成功！"); }
	 * 
	 * 
	 *//**
		 * 车辆类型对应收费规则
		 * 
		 * @return
		 */
	/*
	 * @RequestMapping("/feeRuleList") public ResultData feeRuleList(Integer
	 * parkingId) {
	 * 
	 * List<Map<String,Object>> feeRuleList = vehicleTypeService.findAll(parkingId);
	 * 
	 * return ResultData.createSuccess(feeRuleList, "查询成功！"); }
	 * 
	 *//**
		 * 收费规则
		 * 
		 * @param params
		 * @return
		 */
	/*
	 * 
	 * @RequestMapping("/feeRuleList") public ResultData feeRuleList(Integer
	 * parkingId) {
	 * 
	 * List<Map<String,Object>> feeRuleList =
	 * vehicleTypeFeeRuleService.findAll(parkingId);
	 * 
	 * return ResultData.createSuccess(feeRuleList, "查询成功！"); }
	 * 
	 *//**
		 * 收费规则详情
		 * 
		 * @param parkingId
		 * @return
		 */
	/*
	 * @RequestMapping("/ruleDetail") public ResultData ruleDetail(Integer
	 * parkingId) {
	 *//**
		 * 查询按时收费规则
		 */
	/*
	 * List<Map<String,Object>> feeRuleTime = feeRuleTimeService.findAll(parkingId);
	 *//**
		 * 查询按次收费规则
		 */
	/*
	 * List<Map<String,Object>> feeRuleCount =
	 * feeRuleCountService.findAll(parkingId);
	 * 
	 * Map<String,Object> map = new HashMap<>(); map.put("feeRuleTime",
	 * feeRuleTime); map.put("feeRuleCount", feeRuleCount);
	 * 
	 * return ResultData.createSuccess(map, "查询成功！"); }
	 * 
	 * 
	 * @RequestMapping("/parkingFee") public ResultData parkingFee(@RequestBody
	 * Map<String,Object> params) {
	 * 
	 * Object parking_id = params.get("parkingId"); Object plate_no =
	 * params.get("plateNo"); Object pass_time = params.get("passTime");
	 * 
	 * if(StringUtils.isEmpty(parking_id) || StringUtils.isEmpty(plate_no) ||
	 * StringUtils.isEmpty(pass_time)) { return ResultData.createError("参数不完整！"); }
	 * 
	 * String parkingId = parking_id.toString(); String plateNo =
	 * plate_no.toString(); String passTime = pass_time.toString();
	 * 
	 * boolean txgzFlag = true;
	 * 
	 *//**
		 * 查询车辆信息
		 */
	/*
	 * VehicleInfo vehicleInfo = vehicleInfoService.selectOne(new
	 * EntityWrapper<VehicleInfo>() .eq("parking_id", parkingId) .eq("plate_no",
	 * plateNo));
	 * 
	 * if(vehicleInfo != null) {//如果车辆信息不为空 Integer vehicleTypeId =
	 * vehicleInfo.getVehicleTypeId();//车辆类型id
	 *//**
		 * 查询通行规则
		 */
	/*
	 * VehiclePassRule vehiclePassRule = vehiclePassRuleService.selectOne(new
	 * EntityWrapper<VehiclePassRule>() .eq("parking_id", parkingId)
	 * .eq("vehicle_type_id", vehicleTypeId)); if(vehiclePassRule != null)
	 * {//如果通行规则有
	 * 
	 *//**
		 * 计算当前时间是否在限制时间内
		 */
	/*
	 * //String startTime = vehiclePassRule.getStartTime(); //String endTime =
	 * vehiclePassRule.getEndTime(); DateFormat df = new SimpleDateFormat("hh:mm");
	 * try { Date dt1 = df.parse(startTime); Date dt2 = df.parse(endTime); String
	 * nowTimeStr = DateFormatUtil.dateFormat(new Date(), "HH:mm"); Date nowDate =
	 * df.parse(nowTimeStr); if(nowDate.getTime() >= dt1.getTime() &&
	 * nowDate.getTime() <= dt2.getTime()) { //如果当前时间在限制时间内就禁止通行 txgzFlag = false; }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } else {//如果通信规则没有
	 *//**
		 * 根据车牌号去通信规则中查询
		 */
	/*
	 * } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * return null; }
	 * 
	 * 
	 *//**
		 * 是否可以通行
		 */
	/*
	 * @RequestMapping("/isPass") public ResultData isPass(Integer parkingId,String
	 * plateNo) {
	 * 
	 * if(StringUtils.isEmpty(parkingId)||StringUtils.isEmpty(plateNo)) { return
	 * ResultData.createError("车场ID或车牌号不能为空!"); }
	 * 
	 * boolean flag = true;
	 * 
	 * VehicleInfo vehicleInfo = vehicleInfoService.selectOne(new
	 * EntityWrapper<VehicleInfo>() .eq("parking_id", parkingId) .eq("plate_no",
	 * plateNo)); if(vehicleInfo != null) { Integer vehicleTypeId =
	 * vehicleInfo.getVehicleTypeId(); VehiclePassRule vehiclePassRule =
	 * vehiclePassRuleService.selectOne(new EntityWrapper<VehiclePassRule>()
	 * .eq("parking_id", parkingId) .eq("vehicle_type_id", vehicleTypeId));
	 * 
	 * if(vehiclePassRule == null) { vehiclePassRule =
	 * vehiclePassRuleService.selectOne(new EntityWrapper<VehiclePassRule>()
	 * .eq("parking_id", parkingId) .eq("plate_no", plateNo)); }
	 * 
	 * if(vehiclePassRule != null) { String startTime =
	 * vehiclePassRule.getStartTime(); String endTime =
	 * vehiclePassRule.getEndTime(); DateFormat df = new SimpleDateFormat("hh:mm");
	 * try { Date dt1 = df.parse(startTime); Date dt2 = df.parse(endTime); String
	 * nowTimeStr = DateFormatUtil.dateFormat(new Date(), "HH:mm"); Date nowDate =
	 * df.parse(nowTimeStr); if(nowDate.getTime() >= dt1.getTime() &&
	 * nowDate.getTime() <= dt2.getTime()) { flag = false; }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * } else { VehiclePassRule vehiclePassRule =
	 * vehiclePassRuleService.selectOne(new EntityWrapper<VehiclePassRule>()
	 * .eq("parking_id", parkingId) .eq("plate_no", plateNo));
	 * 
	 * if(vehiclePassRule != null) { String startTime =
	 * vehiclePassRule.getStartTime(); String endTime =
	 * vehiclePassRule.getEndTime(); DateFormat df = new SimpleDateFormat("hh:mm");
	 * try { Date dt1 = df.parse(startTime); Date dt2 = df.parse(endTime); String
	 * nowTimeStr = DateFormatUtil.dateFormat(new Date(), "HH:mm"); Date nowDate =
	 * df.parse(nowTimeStr); if(nowDate.getTime() >= dt1.getTime() &&
	 * nowDate.getTime() <= dt2.getTime()) { flag = false; }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } }
	 * 
	 * Map<String,Object> map = new HashMap<>(); map.put("isPass", flag);
	 * 
	 * return ResultData.createSuccess(map,"查询成功！"); }
	 * 
	 *//**
		 * 查询车牌号是否免费车
		 * 
		 * @param parkingId
		 * @param plateNo
		 * @return
		 */
	/*
	 * @RequestMapping("/isFree") public ResultData isFree(Integer parkingId, String
	 * plateNo) {
	 * 
	 * if(StringUtils.isEmpty(parkingId)||StringUtils.isEmpty(plateNo)) { return
	 * ResultData.createError("车场ID或车牌号不能为空!"); }
	 * 
	 * boolean flag = false;
	 * 
	 * VehicleInfo vehicleInfo = vehicleInfoService.selectOne(new
	 * EntityWrapper<VehicleInfo>() .eq("plate_no", plateNo));
	 * 
	 * if(vehicleInfo != null) { Integer vehicleTypeId =
	 * vehicleInfo.getVehicleTypeId(); VehicleType vehicleType =
	 * vehicleTypeService.selectById(vehicleTypeId); if(vehicleType != null) {
	 * String vehicleTypeName = vehicleType.getVehicleTypeName(); if() {
	 * 
	 * } } }
	 * 
	 *//**
		 * 查看月租表中有无该车辆
		 */
	/*
	 * VehicleMonthly vehicleMonthly = vehicleMonthlyService.selectOne(new
	 * EntityWrapper<VehicleMonthly>() .eq("parking_id", parkingId) .eq("plate_no",
	 * plateNo));
	 * 
	 * if(vehicleMonthly != null) { Date startTime = vehicleMonthly.getStartTime();
	 * int months = 0; Integer monthlyTypeNo = vehicleMonthly.getMonthlyTypeNo();
	 * if(monthlyTypeNo == 1) { months = 1; } else if(monthlyTypeNo == 2) { months =
	 * 3; } else if(monthlyTypeNo == 3) { months = 6; } else if(monthlyTypeNo == 4)
	 * { months = 12; }
	 * 
	 * Date maxDate = DateFormatUtil.addMonths(startTime, months);//到期时间 String
	 * nowDateStr = DateFormatUtil.dateFormat(new Date(), FORMAT); Date nowDate =
	 * DateFormatUtil.strFormat(nowDateStr, FORMAT); if(maxDate.getTime() >=
	 * nowDate.getTime()) { flag = true; } }else {
	 *//**
		 * 查看收费规则是否是免费车辆
		 */
	/*
	 * 
	 * VehicleInfo vehicleInfo = vehicleInfoService.selectOne(new
	 * EntityWrapper<VehicleInfo>() .eq("parking_id", parkingId) .eq("plate_no",
	 * plateNo)); if(vehicleInfo != null) { Integer vehicleTypeId =
	 * vehicleInfo.getVehicleTypeId();
	 * 
	 * VehicleTypeFeeRule typeFeeRule = vehicleTypeFeeRuleService.selectOne(new
	 * EntityWrapper<VehicleTypeFeeRule>() .eq("parking_id", parkingId)
	 * .eq("vehicle_type_id", vehicleTypeId)); if(typeFeeRule != null) { Integer
	 * feeRuleId = typeFeeRule.getFeeRuleId(); if(feeRuleId == 3) { flag = true; } }
	 * }
	 * 
	 * }
	 * 
	 * Map<String,Object> map = new HashMap<>(); map.put("isFree", flag);
	 * 
	 * return ResultData.createSuccess(map,"查询成功！"); }
	 * 
	 *//**
		 * 
		 * 查询哪种收费规则
		 * 
		 * @param plateNo
		 * @return
		 */
	/*
	 * @RequestMapping("/whatFeeRuleId") public ResultData whatFeeRuleId(Integer
	 * parkingId,String plateNo) { Integer feeRuleId = 1;
	 * 
	 * VehicleInfo vehicleInfo = vehicleInfoService.selectOne(new
	 * EntityWrapper<VehicleInfo>() .eq("parking_id", parkingId) .eq("plate_no",
	 * plateNo)); if(vehicleInfo != null) { Integer vehicleTypeId =
	 * vehicleInfo.getVehicleTypeId();
	 * 
	 * 根据车辆类型去匹配收费规则
	 * 
	 * VehicleTypeFeeRule vehicleTypeFeeRule =
	 * vehicleTypeFeeRuleService.selectOne(new EntityWrapper<VehicleTypeFeeRule>()
	 * .eq("parking_id", parkingId) .eq("vehicle_type_id",vehicleTypeId));
	 * if(vehicleTypeFeeRule != null) { feeRuleId =
	 * vehicleTypeFeeRule.getFeeRuleId(); } }
	 * 
	 * Map<String,Object> map = new HashMap<>(); map.put("feeRuleId", feeRuleId);
	 * 
	 * return ResultData.createSuccess(map,"查询成功！"); }
	 * 
	 *//**
		 * 根据收费规则id查询收费规则
		 * 
		 * @param
		 * @return
		 *//*
			 * @RequestMapping("/feeRule") public ResultData feeRule(Integer
			 * parkingId,Integer feeRuleId) {
			 * 
			 * if(StringUtils.isEmpty(feeRuleId)) { return
			 * ResultData.createError("收费规则id不能为空！"); }
			 * 
			 * if(feeRuleId == 1) {//按时收费 FeeRuleTime feeRuleTime =
			 * feeRuleTimeService.selectOne(new EntityWrapper<FeeRuleTime>()
			 * .eq("parking_id", parkingId)); return ResultData.createSuccess(feeRuleTime,
			 * ""); } else if(feeRuleId == 2) {//按次收费 FeeRuleCount feeRuleCount =
			 * feeRuleCountService.selectOne(new EntityWrapper<FeeRuleCount>()
			 * .eq("parking_id", parkingId)); return ResultData.createSuccess(feeRuleCount,
			 * ""); }
			 * 
			 * return ResultData.createError("收费规则id有误！"); }
			 * 
			 * 
			 * 
			 * public static void main(String[] args) throws Exception { DateFormat df = new
			 * SimpleDateFormat("yyyy-MM-dd hh:mm"); Date dt1 =
			 * df.parse("1995-11-12 15:21"); Date dt2 = df.parse("1999-12-11 09:59");
			 * System.out.println(dt2.getTime() > dt1.getTime());
			 * 
			 * System.out.println(DateFormatUtil.dateFormat(new Date(), "HH:mm"));
			 * 
			 * }
			 */
}
