package com.wynlink.park_platform.mapper;

import com.wynlink.park_platform.entity.BlackList;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Vincent
 * @since 2019-03-19
 */
public interface BlackListMapper extends BaseMapper<BlackList> {

	
	@Select(
			"<script>"
			+ "SELECT * FROM black_list a "
			+ "LEFT JOIN parking_info c ON a.parking_id=c.id"
			+ " <where>"
			+ " is_black=1 "
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
	List<BlackList> findAll(Page<BlackList> page, Map<String, Object> map);

	@Select(
			"<script>"
			+ "SELECT * FROM black_list a "
			+ "LEFT JOIN parking_info c ON a.parking_id=c.id"
			+ " WHERE a.id=#{id}"
			+ "</script>")
	BlackList findById(Integer id);

}
