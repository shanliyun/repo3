package com.wynlink.park_platform.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.VehicleClassify;
import com.wynlink.park_platform.entity.vo.VehicleClassifyVo;

/**
 * <p>
 * 车辆分类表 Mapper 接口
 * </p>
 *
 * @author Vincent
 * @since 2019-04-16
 */
public interface VehicleClassifyMapper extends BaseMapper<VehicleClassify> {

	
	@Select(
			"<script>"
			+ "SELECT * FROM vehicle_classify a "
			+ "LEFT JOIN parking_info c ON a.parking_id=c.id"
			+ " <where>"
			+ " a.vehicle_classify_name != '临时车' AND a.vehicle_classify_name != '月租车' " 
			+ " <if test='parkingId!=null'> "
			+ " AND a.parking_id=#{parkingId}"
			+ " </if>"
			+ "</where> "
			+ "ORDER BY a.update_time DESC"
			+ "</script>")
	List<VehicleClassifyVo> findAll(Page page, Map<String, Object> map);

	@Select(
			"<script>"
			+ "SELECT * FROM vehicle_classify a "
			+ "LEFT JOIN parking_info c ON a.parking_id=c.id"
			+ " WHERE a.id=#{id}"
			+ "</script>")
	VehicleClassifyVo findById(Integer id);

}
