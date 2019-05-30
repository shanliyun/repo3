package com.wynlink.park_platform.task;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;

import com.wynlink.park_platform.entity.WebSocketPo;
import com.wynlink.park_platform.service.GcjldataService;
import com.wynlink.park_platform.utils.DateFormatUtil;
import com.wynlink.park_platform.utils.JsonUtil;
import com.wynlink.park_platform.websocket.SocketHandler;


/**
 * 实时推送
 * @author jzhang
 *
 */
@Lazy(false)
@Service
public class DataSendTimer {

	private final Logger logger = Logger.getLogger(DataSendTimer.class);
	
	@Autowired
	private SocketHandler socketHandler;
	@Autowired
	private GcjldataService gcjldataService;
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	private static Integer index = 0;
	
	/**
	 * 统计推送流量流速
	 */
	@Scheduled(cron = "0/1 * * * * ?")
	public void dataSend(){
		
		/*List<Map<String,Object>> maxIdMap = gcjldataService.findMaxId();
		Integer maxId = 0;
		if(maxIdMap != null && maxIdMap.size() > 0) {
			maxId = Integer.parseInt(maxIdMap.get(0).get("id").toString());
		}*/
		
		Integer count = gcjldataService.findCount();
		
		//System.out.println(count);
		
		if(index < count-1) {
			index++;
		} else {
			return;
		}
		
		List<Map<String, Object>> data = gcjldataService.findAll();
		
		//数据是空不推送
		if (!data.isEmpty()) {
			//id = DataUtils.toStr(data.get(0).get("id"), "-1");
			
			if("0".equals(data.get(index).get("stationType"))) {
				System.out.println(data.get(index).get("plateNum") + "进场了！");
			}else {
				System.out.println(data.get(index).get("plateNum") + "出场了！");
			}
			
			Map<String, Object> map = data.get(index);
			
			Object payObj = map.get("stationType");
			if("1".equals(payObj)) {
				map.put("inOrOut", "离场");
			} else {
				map.put("inOrOut", "进场");
			}
			
			WebSocketPo wsp = new WebSocketPo();
			wsp.setData(map);
			wsp.setLx("parkingGcsj");
			socketHandler.sendMessagesToUsers(new TextMessage(JsonUtil.toJson(wsp)));
			
			logger.info("推送"+ DateFormatUtil.dateFormat(new Date()));
			
		} else {
			return;
		}
		
		
	}
	
	
}