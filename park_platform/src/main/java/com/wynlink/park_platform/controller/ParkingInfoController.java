package com.wynlink.park_platform.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.EnterExitInfo;
import com.wynlink.park_platform.entity.EnterExitTypeInfo;
import com.wynlink.park_platform.entity.HttpResult;
import com.wynlink.park_platform.entity.Jckxx;
import com.wynlink.park_platform.entity.ParkingInfo;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.SysUser;
import com.wynlink.park_platform.entity.Tccxx;
import com.wynlink.park_platform.service.EnterExitInfoService;
import com.wynlink.park_platform.service.EnterExitTypeInfoService;
import com.wynlink.park_platform.service.HttpAPIService;
import com.wynlink.park_platform.service.ParkingInfoService;
import com.wynlink.park_platform.service.SysUserService;
import com.wynlink.park_platform.utils.MapTrunPojo;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

/**
 * <p>
 * 停车场信息表 前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-03-18
 */
@RestController
@RequestMapping("/parkingInfo")
public class ParkingInfoController {

	private Logger log = LoggerFactory.getLogger(ParkingInfoController.class);

	@Autowired
	private ParkingInfoService parkingInfoService;
	
	@Autowired
	private EnterExitInfoService enterExitInfoService;

	@Autowired
    private HttpAPIService httpAPIService;
	
	@Autowired
	private EnterExitTypeInfoService enterExitTypeInfoService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Value("${url.zj}")
	private String urlZj;
	
	@Value("${url.hh}")
	private String urlHh;
	/**
	 * 加载出入口类型下拉框
	 * @return
	 */
	@RequestMapping("/enterExitType")
	public ResultData enterExitType() {
		List<EnterExitTypeInfo> list = enterExitTypeInfoService.selectList(null);
		
		return ResultData.createSuccess(list, "");
	}
	
	/**
	 * 注册
	 * @param parkingInfo
	 * @return
	 */
	@Transactional
	@RequestMapping("/add")
	public ResultData add(@RequestBody ParkingInfo parkingInfo) {
		/**
		 * 字段必填校验
		 */
		ResultData checkParams = this.checkParkingParams(parkingInfo);
		if(checkParams.getStatus() == 1) {
			return checkParams;
		}
		/**
		 * 检查车场数据表中是否已存在添加的数据
		 */
		int count = parkingInfoService.selectCount(new EntityWrapper<ParkingInfo>()
				.eq("parking_no", parkingInfo.getParkingNo()));
		if (count > 0) {
			return ResultData.createError("停车场已存在，勿重复注册！");
		}
		/**
		 * 添加停车场
		 */
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();//当前登录用户（车场管理员）
		//获取用户组
		Integer groupId = user.getGroupId();
		
		parkingInfo.setGroupId(groupId);
		parkingInfo.setCreateTime(new Date());
		parkingInfo.setUpdateTime(new Date());
		
		try {
			parkingInfoService.insert(parkingInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("插入或更新parkingInfo表失败！");
			return ResultData.createError("注册失败，请重试！");
		}
		
		/**
		 * 停车场信息插入成功后，同步插入出入口信息表
		 */
		Integer parkingId = parkingInfo.getId();//车场id
		List<EnterExitInfo> enterExitInfos = parkingInfo.getEnterExitInfos();
		Integer enterExitCount = parkingInfo.getEnterExitCount();//进出口数量
		List<Integer> num = new LinkedList<>();
		for(int i=1;i<=enterExitCount;i++) {
			num.add(i);
		}
		for (EnterExitInfo enterExitInfo : enterExitInfos) {
			/**
			 * 字段必填校验
			 */
			ResultData checkEnExitParams = this.checkEnExitParams(enterExitInfo);
			if(checkEnExitParams.getStatus() == 1) {
				return checkEnExitParams;
			}
			enterExitInfo.setParkingId(parkingId);
			
			if(enterExitInfo.getEnterExitType()==1) {//一进一出
				enterExitInfo.setEnterExitNum(1);
			} else {
				enterExitInfo.setEnterExitNum(num.remove(0));
			}
			
			
		}
		
		try {
			enterExitInfoService.insertBatch(enterExitInfos);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("注册失败，请重试！");
		}
		
		Tccxx tccxx = this.packagingTccxx(parkingInfo);
		
		Map<String, Object> map = MapTrunPojo.object2Map(tccxx);
		
		
		//String url = "http://127.0.0.1:5001/szjj/tccxx";
		//String url = "http://111.231.195.158:5001/szjj/tccxx";
		//上传到张健服务
		try {
			HttpResult result = httpAPIService.doPost(urlZj, map);
			String body = result.getBody();
			System.out.println("xxxxxxxxx"+body);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	//	String url2 = "http://127.0.0.1:7788/parkInfo/insertPark";
		//上传到黄河服务
		try {
			String body = HttpUtil.post(urlHh, JSONUtil.toJsonStr(parkingInfo));
			System.out.println(body);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultData.createSuccess("注册成功！");
		
	}
	
	/**
	 * 修改
	 * @param parkingInfo
	 * @return
	 */
	@Transactional
	@RequestMapping("/update")
	public ResultData update(@RequestBody ParkingInfo parkingInfo) {
		
		/**
		 * 字段必填校验
		 */
		ResultData checkParams = this.checkParkingParams(parkingInfo);
		if(checkParams.getStatus() == 1) {
			return checkParams;
		}
		/**
		 * 检查重复项
		 */
		String parkingNo = parkingInfo.getParkingNo();
		Integer parkingId = parkingInfo.getId();
		int count = parkingInfoService.selectCount(new EntityWrapper<ParkingInfo>()
				.eq("parking_no", parkingNo)
				.ne("id", parkingId));
		if (count > 0) {
			return ResultData.createError("停车场已存在，勿重复注册！");
		}
		
		
		parkingInfo.setUpdateTime(new Date());
		
		try {
			parkingInfoService.updateById(parkingInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("修改失败，请重试！");
		}
		/**
		 * 修改停车场信息表成功后，再同步更新出入口信息表
		 */
		
		List<EnterExitInfo> enterExitInfos = parkingInfo.getEnterExitInfos();
		for (EnterExitInfo enterExitInfo : enterExitInfos) {
			/**
			 * 字段必填校验
			 */
			ResultData checkEnExitParams = this.checkEnExitParams(enterExitInfo);
			if(checkEnExitParams.getStatus() == 1) {
				return checkEnExitParams;
			}
			enterExitInfo.setParkingId(parkingId);
		}
		
		
		try {
			//先删除当前停车场下的进出口信息
			enterExitInfoService.delete(new EntityWrapper<EnterExitInfo>().eq("parking_id", parkingId));
			enterExitInfoService.insertBatch(enterExitInfos);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("注册失败，请重试！");
		}
		
		
		
		return ResultData.createSuccess("修改成功！");
		
	}
	

	
	/**
	 * 封装上报交警信息
	 */
	private Tccxx packagingTccxx(ParkingInfo parkingInfo) {
		Tccxx tccxx = new Tccxx();
		tccxx.setCSID("10000014");
		tccxx.setCSPTLS(parkingInfo.getId().toString());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tccxx.setQQSJ(sdf.format(new Date()));
		tccxx.setTCCID(parkingInfo.getParkingNo());
		tccxx.setTCCMC(parkingInfo.getParkingName());
		tccxx.setJWDBZ("0");
		tccxx.setTCCJD(parkingInfo.getLongitude());
		tccxx.setTCCWD(parkingInfo.getLatitude());
		
		Integer totalSpace = parkingInfo.getTotalSpace();
		if(StringUtils.isEmpty(totalSpace)) {
			totalSpace = 0;
		}
		tccxx.setTCCCWS(totalSpace+"");
		tccxx.setKRNCWS(totalSpace+"");
		tccxx.setGXSJ(sdf.format(new Date()));
		tccxx.setXXDZ(parkingInfo.getParkingAddress());
		tccxx.setXKZH("123");
		tccxx.setTPIDS("[\"szjjtccshuhuics01_0_0_0_0.jpg\"]");
		
		List<Jckxx> jckxxs = new ArrayList<>();
		List<EnterExitInfo> enterExits = enterExitInfoService.selectList(new EntityWrapper<EnterExitInfo>()
				.eq("parking_id", parkingInfo.getId()));
		
		for (EnterExitInfo enterExit : enterExits) {
			Jckxx jckxx = new Jckxx();
			jckxx.setJCKBH(enterExit.getEnterExitNum().toString());
			jckxx.setJCKMC(enterExit.getEnterExitName());
			jckxx.setJCKJWDBZ("0");
			jckxx.setJCKJD(enterExit.getEnterExitLongitude());
			jckxx.setJCKWD(enterExit.getEnterExitLatitude());
			
			jckxxs.add(jckxx);
		}
		
		tccxx.setJCKXX(JSONUtil.toJsonStr(jckxxs));
		return tccxx;
	}
	

	/**
	 * 停车场列表查询
	 * 
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
		
		Page<ParkingInfo> page = new Page<>(currentPage, pageSize);

		EntityWrapper<ParkingInfo> wrapper = new EntityWrapper<>();

		if (!StringUtils.isEmpty(params.get("parkingName"))) {
			wrapper.like("parking_name", params.get("parkingName").toString());
		}
		
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();//当前登录用户（车场管理员）
		//获取用户组
		Integer groupId = user.getGroupId();
		
		if(user.getUserType() != 2) {
			wrapper.eq("group_id", groupId);
		}
		

		Page<ParkingInfo> pageList = null;
		try {
			pageList = parkingInfoService.selectPage(page, wrapper);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("查询失败！");
		}
		
		return ResultData.createSuccess(pageList,"查询成功！");
	}

	/**
	 * 查看停车场详情
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/detail/{id}")
	public ResultData detail(@PathVariable Integer id) {

		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空!");
		}

		ParkingInfo parkingInfo = parkingInfoService.selectById(id);
		if (parkingInfo != null) {
			
			List<EnterExitInfo> enterExitList = enterExitInfoService.selectList(new EntityWrapper<EnterExitInfo>()
					.eq("parking_id", id));
			
			if(enterExitList != null && enterExitList.size() > 0) {
				for (EnterExitInfo enterExitInfo : enterExitList) {
					Integer enterExitType = enterExitInfo.getEnterExitType();
					Map<String, Object> map = jdbcTemplate.queryForMap("SELECT type_name FROM enter_exit_type_info WHERE type_no=?", enterExitType);
					enterExitInfo.setTypeName(map.get("type_name").toString());
				}
			}

			parkingInfo.setEnterExitInfos(enterExitList);
			
		}

		return ResultData.createSuccess(parkingInfo,"查询成功！");
	}
	
	private ResultData checkParkingParams(ParkingInfo parkingInfo) {
		String parkingNo = parkingInfo.getParkingNo();
		if(StringUtils.isEmpty(parkingNo)) {
			return ResultData.createError("请输入车场编号！");
		}
		
		String parkingName = parkingInfo.getParkingName();
		if(StringUtils.isEmpty(parkingName)) {
			return ResultData.createError("请输入车场名称！");
		}
		
		String parkingOwner = parkingInfo.getParkingOwner();
		if(StringUtils.isEmpty(parkingOwner)) {
			return ResultData.createError("请输入车场所有人！");
		}
		
		Integer onlineReservation = parkingInfo.getOnlineReservation();
		if(StringUtils.isEmpty(onlineReservation)) {
			return ResultData.createError("请选择是否网上预约！");
		}
		
		String cityName = parkingInfo.getCityName();
		if(StringUtils.isEmpty(cityName)) {
			return ResultData.createError("请输入所属城市！");
		}
		
		Integer parkingType = parkingInfo.getParkingType();
		if(StringUtils.isEmpty(parkingType)) {
			return ResultData.createError("请选择车场类型！");
		}
		
		Integer parkingKind = parkingInfo.getParkingKind();
		if(StringUtils.isEmpty(parkingKind)) {
			return ResultData.createError("请选择车场种类！");
		}
		
		Integer enterExitCount = parkingInfo.getEnterExitCount();
		if(StringUtils.isEmpty(enterExitCount)) {
			return ResultData.createError("请输入出入口数量！");
		}
		
		List<EnterExitInfo> enterExitInfos = parkingInfo.getEnterExitInfos();
		if(StringUtils.isEmpty(enterExitInfos)) {
			return ResultData.createError("出入口信息不能为空！");
		}
		return ResultData.createSuccess("");
	}
	
	private ResultData checkEnExitParams(EnterExitInfo enterExitInfo) {
		String enterExitNo = enterExitInfo.getEnterExitNo();
		if(StringUtils.isEmpty(enterExitNo)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultData.createError("请输入进出口编号！");
		}
		
		Integer enterExitType = enterExitInfo.getEnterExitType();
		if(StringUtils.isEmpty(enterExitType)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultData.createError("请选择进出口类型！");
		}
		
		String enterExitName = enterExitInfo.getEnterExitName();
		if(StringUtils.isEmpty(enterExitName)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultData.createError("请输入进出口名称！");
		}
		return ResultData.createSuccess("");
	}

}
