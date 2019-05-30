package com.wynlink.park_platform.utils;

import java.io.IOException;
import java.io.StringWriter;

import java.text.DateFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wynlink.park_platform.entity.XMessage;


/**
 * json
 * 
 * @author Yang
 * @date 2013-8-29
 *
 */
public final class JsonUtil {
	
	private final static Logger logger = Logger.getLogger(JsonUtil.class);

	/**
	 * 转换对象为json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		StringWriter sw = new StringWriter();
		try {
			new ObjectMapper().writeValue(sw, object);
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}

		return sw.toString();
	}
	
	/**
	 * 转换对象为json字符串,并指定date的格式化
	 * 
	 * @param object
	 * @param dateFormat
	 * @return
	 */
	public static String toJson(Object object, DateFormat dateFormat){
		ObjectMapper mapper = new ObjectMapper();
		if(null != dateFormat) 
			mapper.setDateFormat(dateFormat);
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, object);
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}

		return sw.toString();
	}
	
	/**
	 * 解析jsonString字符串为一个具体的object
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T parseJsonToObject(String jsonString, Class<T> clazz){
		try {
			return new ObjectMapper().readValue(jsonString, clazz);
		} catch (JsonParseException e) {
			logger.error(e.getStackTrace());
		} catch (JsonMappingException e) {
			logger.error(e.getStackTrace());
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
		return null;
	}

	/**
	 * 解析jsonString字符串为map
	 * 
	 * @param jsonString
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map parseJsonToMap(String jsonString) {
		if(null == jsonString){
			return null;
		}
		
		try {
			return new ObjectMapper().readValue(jsonString, Map.class);
		} catch (JsonParseException e) {
			logger.error(e.getStackTrace());
		} catch (JsonMappingException e) {
			logger.error(e.getStackTrace());
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
		return null;
	}
	
	/**
	 * 解析jsonString字符串为list
	 * 
	 * @param jsonString
	 * @return
	 */
	public static List<?> parseJsonToList(String jsonString) {
		if(null == jsonString){
			return null;
		}
		
		try {
			return (List<?>) new ObjectMapper().readValue(jsonString, List.class);
		} catch (JsonParseException e) {
			logger.error(e.getStackTrace());
		} catch (JsonMappingException e) {
			logger.error(e.getStackTrace());
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
		return null;
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ldid", "路段id");
		map.put("cdid", "车道id");
		map.put("ldmc", "路段名称");
		map.put("cdmc", "车道名称");
		map.put("ll", "流量");
		map.put("ls", "流速");
		map.put("sjcjj", "时间车间距");
		map.put("cgsj", "采集时间");
		map.put("dlid", "道路Id");
		map.put("dlmc", "道路名称");
		
		XMessage xMessage = new XMessage();
		xMessage.setBody(map);
		
		
		System.out.println(toJson(xMessage));
		
	}
	
}
