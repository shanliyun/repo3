package com.wynlink.park_platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.wynlink.park_platform.utils.DataHandleUtil;

@Service
public class PicUnusualService {
	@Autowired
	@Qualifier("secondaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public String percent(Integer type) {
		
		List<Map<String, Object>> resultData = new ArrayList<>();
		String sql = "";
		if(type == 1) {
			sql = "SELECT a.plate_no AS plateNo,b.apply_status FROM rfid_vehicle a,rfid_tag_apply b WHERE a.id=b.vehicle_id AND b.apply_status=3 AND  \r\n" + 
					"	EXISTS (SELECT plate_num FROM gcjldata c WHERE c.plate_num=a.plate_no AND c.recognition_type = 1)";
			
			List<Map<String,Object>> picList = jdbcTemplate.queryForList(sql);
			if(picList != null && picList.size() > 0) {
				for (Map<String, Object> map : picList) {
					
					String sqlD = "SELECT plate_num AS plateNo,plate_color AS plateColor,parking_name AS parkingName,passing_time AS passingTime,recognition_type AS recognitionType\r\n" + 
							",payment_toll_fee AS paymentTollFee,rfid,lp_problem AS lpProblem  FROM gcjldata c WHERE c.plate_num=? AND recognition_type=1;";
					List<Map<String, Object>> vehicleInfo = jdbcTemplate.queryForList(sqlD,map.get("plateNo"));
					resultData.addAll(vehicleInfo);
					
				}
			}
		} else if(type == 2) {
			sql = "SELECT plate_num AS plateNo,passing_time,recognition_type FROM gcjldata WHERE recognition_type = 0";
			resultData = jdbcTemplate.queryForList(sql);
		} else if(type == 3) {
			sql = "SELECT plate_num AS plateNo,passing_time,recognition_type FROM gcjldata WHERE recognition_type = 3";
			resultData = jdbcTemplate.queryForList(sql);
		}
		
		
		Map<String, Object> map = jdbcTemplate.queryForMap("SELECT COUNT(*) count FROM gcjldata");
		Integer total = Integer.parseInt(map.get("count").toString());
		Integer size = resultData.size();
		String data = DataHandleUtil.division(size, total);
		
		return data;
	}

	public List<Map<String, Object>> list(Integer type) {
		
		List<Map<String, Object>> resultData = new ArrayList<>();
		
		String sql = "";
		if(type == 1) {
			sql = "SELECT a.plate_no AS plateNo,b.apply_status FROM rfid_vehicle a,rfid_tag_apply b WHERE a.id=b.vehicle_id AND b.apply_status=3 AND  \r\n" + 
					"	EXISTS (SELECT plate_num FROM gcjldata c WHERE c.plate_num=a.plate_no AND c.recognition_type = 1)";
			
			List<Map<String,Object>> picList = jdbcTemplate.queryForList(sql);
			if(picList != null && picList.size() > 0) {
				for (Map<String, Object> map : picList) {
					
					String sqlD = "SELECT plate_num AS plateNo,plate_color AS plateColor,parking_name AS parkingName,passing_time AS passingTime,recognition_type AS recognitionType\r\n" + 
							",payment_toll_fee AS paymentTollFee,rfid,lp_problem AS lpProblem  FROM gcjldata c WHERE c.plate_num=? AND recognition_type=1;";
					List<Map<String, Object>> vehicleInfo = jdbcTemplate.queryForList(sqlD,map.get("plateNo"));
					resultData.addAll(vehicleInfo);
					
				}
			}
		} else if(type == 2) {
			sql = "SELECT plate_num AS plateNo,plate_color AS plateColor,parking_name AS parkingName,passing_time AS passingTime,recognition_type AS recognitionType,payment_toll_fee AS paymentTollFee,rfid,lp_problem AS lpProblem FROM gcjldata WHERE recognition_type = 0";
			resultData = jdbcTemplate.queryForList(sql);
		} else if(type == 3) {
			sql = "SELECT plate_num AS plateNo,plate_color AS plateColor,parking_name AS parkingName,passing_time AS passingTime,recognition_type AS recognitionType,payment_toll_fee AS paymentTollFee,rfid,lp_problem AS lpProblem FROM gcjldata WHERE recognition_type = 3";
			resultData = jdbcTemplate.queryForList(sql);
		}
		
		
		
		return resultData;
	}
}
