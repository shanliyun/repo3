package com.wynlink.park_platform.mapper;

import com.wynlink.park_platform.entity.WhiteList;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 白名单车辆 Mapper 接口
 * </p>
 *
 * @author Vincent
 * @since 2019-04-13
 */
public interface WhiteListMapper extends BaseMapper<WhiteList> {

	
	@Select(
			"<script>"
			+ "SELECT * FROM white_list a "
			+ "LEFT JOIN parking_info c ON a.parking_id=c.id"
			+ " WHERE a.id=#{id}"
			+ "</script>")
	WhiteList findById(Integer id);

	@Select(
			"<script>"
			+ "SELECT * FROM white_list a "
			+ "LEFT JOIN parking_info c ON a.parking_id=c.id"
			+ " <where>"
			+ " 1=1 "
			+ " <if test='parkingId!=null'> "
			+ " AND a.parking_id=#{parkingId}"
			+ " </if>"
			+ "<if test='plateColorNo!=null'>"
			+ " AND a.plate_color_no=#{plateColorNo}"
			+ "</if>"
			+ "<if test='plateNo!=null'>"
			+ " AND a.plate_no LIKE #{plateNo}"
			+ "</if>"
			+ "</where> "
			+ "ORDER BY a.update_time DESC"
			+ "</script>")
	List<WhiteList> findAll(Page<WhiteList> page, Map<String, Object> map);

}
