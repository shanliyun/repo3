package com.wynlink.park_platform.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.SysUser;
import com.wynlink.park_platform.entity.VehicleInfo;
import com.wynlink.park_platform.entity.vo.VehicleInfoVo;
import com.wynlink.park_platform.entity.vo.VehicleInfoVoPlus;
import com.wynlink.park_platform.service.SysUserService;
import com.wynlink.park_platform.service.VehicleInfoService;

/**
 * <p>
 * 车辆信息表 前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-03-20
 */
@RestController
@RequestMapping("/vehicleInfo")
public class VehicleInfoController {

	@Autowired
	private VehicleInfoService vehicleInfoService;

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 初始化车场列表下拉框
	 * 
	 * @return
	 */
	@RequestMapping("/initParkingList")
	public ResultData initParkingList(Integer userId) {
		
		List<Map<String,Object>> list = null;
		
		/**
		 * 根据用户id去查询用户类型
		 */
		SysUser sysUser = sysUserService.selectById(userId);
		if(sysUser != null) {
			Integer userType = sysUser.getUserType();
			if(userType == 1) {//管理员
				list = jdbcTemplate.queryForList("SELECT id,parking_name AS parkingName FROM parking_info");
			} else {
				list = jdbcTemplate.queryForList("SELECT id,parking_name AS parkingName FROM parking_info WHERE user_id=?",userId);
			}
		}

		return ResultData.createSuccess(list, "");
	}
	
	/**
	 * 初始化驾驶证上的车辆类型数据
	 */
	@RequestMapping("/initJszCllx")
	public ResultData initJszCllx() {
		List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT id,vehicle_type_name AS vehicleTypeName FROM vehicle_type_info");
		return ResultData.createSuccess(list, "");
	}
	
	/**
	 * 初始化荷载人数数据
	 */
	@RequestMapping("/initJszHzrs")
	public ResultData initJszHzrs() {
		List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT id,hzrs FROM vehicle_hzrs");
		return ResultData.createSuccess(list, "");
	}

	/**
	 * 加载车牌颜色下拉框
	 * 
	 * @return
	 */
	@RequestMapping("/plateColorList")
	public ResultData plateColorList() {
		List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT plate_color_no AS plateColorNo,plate_color AS plateColor FROM plate_color_info");
		return ResultData.createSuccess(list, "");
	}


	/**
	 * 加载车辆分类下拉框
	 */
	@RequestMapping("/initVehicleClassify")
	public ResultData initVehicleClassify() {
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT id,vehicle_classify_name AS vehicleClassifyName FROM vehicle_classify WHERE status=1"
				+ " AND vehicle_classify_name != '临时车'");
		return ResultData.createSuccess(list, "");
	}

	/**
	 * 新增车辆
	 */
	@RequestMapping("/add")
	public ResultData add(@RequestBody VehicleInfo vehicleInfo) {
		/**
		 * 参数非空校验
		 */
		ResultData checkVehicleInfoParams = this.checkVehicleInfoParams(vehicleInfo);
		if(checkVehicleInfoParams.getStatus() == 1) {
			return checkVehicleInfoParams;
		}
		
		
		int count = vehicleInfoService.selectCount(new EntityWrapper<VehicleInfo>().eq("parking_id", vehicleInfo.getParkingId())
				.eq("vehicle_classify_id", vehicleInfo.getVehicleClassifyId())
				.eq("plate_no", vehicleInfo.getPlateNo()));
		if (count > 0) {
			return ResultData.createError("勿重复添加车辆！");
		}

		vehicleInfo.setCreateTime(new Date());
		vehicleInfo.setUpdateTime(new Date());

		try {
			vehicleInfoService.insert(vehicleInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("新增车辆失败！");
		}

		return ResultData.createSuccess("新增车辆成功！");
	}

	/**
	 * 修改车辆
	 */
	@RequestMapping("/update")
	public ResultData update(@RequestBody VehicleInfo vehicleInfo) {

		Integer id = vehicleInfo.getId();//id
		
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("车辆Id不能为空！");
		}
		
		Integer parkingId = vehicleInfo.getParkingId();// 车场id
		Integer vehicleClassifyId = vehicleInfo.getVehicleClassifyId();// 车辆类型id
		String plateNo = vehicleInfo.getPlateNo();// 车牌号
		
		/*
		 * 修改的数据需要排除它本身，在其他数据中也不存在，才可以修改
		 */
		int count = vehicleInfoService.selectCount(new EntityWrapper<VehicleInfo>()
				.eq("parking_id", parkingId)
				.eq("vehicle_classify_id", vehicleClassifyId)
				.eq("plate_no", plateNo)
				.ne("id", id));
		if (count > 0) {
			return ResultData.createError("此车辆已存在！");
		}
		
		vehicleInfo.setUpdateTime(new Date());

		try {
			vehicleInfoService.updateById(vehicleInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("修改车辆失败！");
		}

		return ResultData.createSuccess("修改车辆成功！");
	}

	/**
	 * 删除车辆
	 */
	@RequestMapping("/deleteVehicle")
	public ResultData deleteVehicle(Integer id) {

		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("车辆Id不能为空！");
		}

		try {
			vehicleInfoService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("删除车辆失败！");
		}

		return ResultData.createSuccess("删除车辆成功！");
	}

	/**
	 * 车辆列表
	 * 
	 * @return
	 */
	@RequestMapping("/vehicleList")
	public ResultData vehicleList(@RequestBody Map<String, Object> params) {

		Integer currentPage = 1;
		if (!StringUtils.isEmpty(params.get("currentPage"))) {
			currentPage = Integer.parseInt(params.get("currentPage").toString());
		}

		Integer pageSize = 10;
		if (!StringUtils.isEmpty(params.get("pageSize"))) {
			pageSize = Integer.parseInt(params.get("pageSize").toString());
		}

		Map<String, Object> conditions = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(params.get("parkingId"))) {
			conditions.put("parkingId", params.get("parkingId"));
		}

		if (!StringUtils.isEmpty(params.get("plateColorNo"))) {
			conditions.put("plateColorNo", params.get("plateColorNo"));
		}

		if (!StringUtils.isEmpty(params.get("vehicleClassifyId"))) {
			conditions.put("vehicleClassifyId", params.get("vehicleClassifyId"));
		}

		if (!StringUtils.isEmpty(params.get("plateNo"))) {
			conditions.put("plateNo", params.get("plateNo"));
		}

		Page<VehicleInfoVo> page = vehicleInfoService.selectVehicleVoPage(new Page<>(currentPage, pageSize), conditions);
		return ResultData.createSuccess(page, "查询成功！");

	}

	/**
	 * 车辆详情/回显
	 */
	@GetMapping("/detail")
	public ResultData detail(Integer id) {
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("车辆Id不能为空！");
		}

		VehicleInfoVoPlus vo = vehicleInfoService.findById(id);

		return ResultData.createSuccess(vo, "");
	}

	
	private ResultData checkVehicleInfoParams(VehicleInfo vehicleInfo) {
		Integer parkingId = vehicleInfo.getParkingId();// 车场id
		if(StringUtils.isEmpty(parkingId)) {
			return ResultData.createError("请选择车场！");
		}
		
		Integer vehicleClassifyId = vehicleInfo.getVehicleClassifyId();
		if(StringUtils.isEmpty(vehicleClassifyId)) {
			return ResultData.createError("请选择车辆分类！");
		}
		String plateNo = vehicleInfo.getPlateNo();// 车牌号
		if(StringUtils.isEmpty(plateNo)) {
			return ResultData.createError("请输入车牌号！");
		}
		
		String vehicleColor = vehicleInfo.getVehicleColor();
		if(StringUtils.isEmpty(vehicleColor)) {
			return ResultData.createError("请选择车牌颜色！");
		}
		
		String ownerPhone = vehicleInfo.getOwnerPhone();
		if(StringUtils.isEmpty(ownerPhone)) {
			return ResultData.createError("请填写车主手机号码！");
		}
		
		return ResultData.createSuccess("");
	}
}
