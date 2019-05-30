package com.wynlink.park_platform.timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wynlink.park_platform.entity.EnterExitInfo;
import com.wynlink.park_platform.entity.HttpResult;
import com.wynlink.park_platform.entity.Jckxx;
import com.wynlink.park_platform.entity.ParkingInfo;
import com.wynlink.park_platform.entity.Tccxx;
import com.wynlink.park_platform.service.EnterExitInfoService;
import com.wynlink.park_platform.service.HttpAPIService;
import com.wynlink.park_platform.service.ParkingInfoService;
import com.wynlink.park_platform.utils.MapTrunPojo;

import cn.hutool.json.JSONUtil;

@Component
public class UploadTimer {
	
	@Autowired
	private ParkingInfoService parkingInfoService;
	
	@Autowired
	private EnterExitInfoService enterExitInfoService;
	
	@Autowired
    private HttpAPIService httpAPIService;
	/**
	 * 定时上传交警平台
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	 //@Scheduled(cron = "0/2 * * * * *")
	public void uploadJjpt() {
		
		
		List<ParkingInfo> parkings = parkingInfoService.selectList(null);
		if(parkings != null && parkings.size() > 0) {
			for (ParkingInfo info : parkings) {
				/*Integer id = info.getId();
				
				List<EnterExitInfo> enex = enterExitInfoService.selectList(new EntityWrapper<EnterExitInfo>()
						.eq("parking_id", id));
				
				info.setEnterExitInfos(enex);*/
				
				
				Tccxx tccxx = this.packagingTccxx(info);
				
				Map<String, Object> map = MapTrunPojo.object2Map(tccxx);
				
				
				String url = "http://111.231.195.158:8899/szjj/tccxx";
				//上传到张健服务
				try {
					HttpResult result = httpAPIService.doPost(url, map);
					String body = result.getBody();
					System.out.println(body);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	
	/**
	 * 封装上报交警信息
	 */
	private Tccxx packagingTccxx(ParkingInfo parkingInfo) {
		Tccxx tccxx = new Tccxx();
		tccxx.setCSID("10000014");
		tccxx.setCSPTLS("10000014");
		
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
			jckxx.setJCKBH(enterExit.getEnterExitNo());
			jckxx.setJCKMC(enterExit.getEnterExitName());
			jckxx.setJCKJWDBZ("0");
			jckxx.setJCKJD(enterExit.getEnterExitLongitude());
			jckxx.setJCKWD(enterExit.getEnterExitLatitude());
			
			jckxxs.add(jckxx);
		}
		tccxx.setJCKXX(JSONUtil.toJsonStr(jckxxs));
		return tccxx;
	}
	
}
