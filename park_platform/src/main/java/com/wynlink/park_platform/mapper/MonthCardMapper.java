package com.wynlink.park_platform.mapper;

import com.wynlink.park_platform.entity.MonthCard;
import com.wynlink.park_platform.entity.vo.MonthCardVo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 月卡 Mapper 接口
 * </p>
 *
 * @author Vincent
 * @since 2019-05-29
 */
public interface MonthCardMapper extends BaseMapper<MonthCard> {

	
	/*
	 * @Select( "<script>" + "SELECT * FROM month_card a " +
	 * "LEFT JOIN parking_info b ON a.parking_id = b.id " + " <where>" + " 1=1 " +
	 * " <if test='parkingId!=null'> " + " AND a.parking_id=#{parkingId}" + " </if>"
	 * + "<if test='plateColorNo!=null'>" + " AND a.plate_color_no=#{plateColorNo}"
	 * + "</if>" + "</if>" + "<if test='plateNo!=null'>" +
	 * " AND a.plate_no LIKE #{plateNo}" + "</if>" + "<if test='owner!=null'>" +
	 * " AND a.owner LIKE #{owner}" + "</if>" + "<if test='spaceNo!=null'>" +
	 * " AND a.spaceNo LIKE #{spaceNo}" + "</if>" +
	 * "<if test='start!=null and end!=null'>" +
	 * " AND a.end_time &gt;= #{start} AND a.end_time &lt;= #{end}" + "</if>" +
	 * "</where> " + "ORDER BY a.update_time DESC" + "</script>")
	 */
	List<MonthCardVo> findAllPage(Page<MonthCardVo> page, Map<String, Object> map);

}
