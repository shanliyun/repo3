package com.wynlink.park_platform.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.wynlink.park_platform.entity.VehicleInfo;
import com.wynlink.park_platform.entity.vo.VehicleInfoVo;
import com.wynlink.park_platform.entity.vo.VehicleInfoVoPlus;

/**
 * <p>
 * 车辆信息表 Mapper 接口
 * </p>
 *
 * @author Vincent
 * @since 2019-03-20
 */
public interface VehicleInfoMapper extends BaseMapper<VehicleInfo> {
	
	@Select(
			"<script>"
			+ "SELECT * FROM vehicle_info a "
			+ "LEFT JOIN vehicle_classify b ON a.vehicle_classify_id = b.id "
			+ "LEFT JOIN parking_info c ON a.parking_id=c.id"
			+ " <where>"
			+ " 1=1 "
			+ " <if test='parkingId!=null'> "
			+ " AND a.parking_id=#{parkingId}"
			+ " </if>"
			+ "<if test='plateColorNo!=null'>"
			+ " AND a.plate_color_no=#{plateColorNo}"
			+ "</if>"
			+ "<if test='vehicleClassifyId!=null'>"
			+ " AND a.vehicle_classify_id=#{vehicleClassifyId}"
			+ "</if>"
			+ "<if test='plateNo!=null'>"
			+ " AND a.plate_no=#{plateNo}"
			+ "</if>"
			+ "</where> "
			+ "ORDER BY a.update_time DESC"
			+ "</script>")
	
	List<VehicleInfoVo> selectVehiclePage(Pagination page,Map map);

	
	@Select(
			"<script>"
			+ "SELECT * FROM vehicle_info a "
			+ "LEFT JOIN vehicle_classify b ON a.vehicle_classify_id = b.id "
			+ "LEFT JOIN parking_info c ON a.parking_id=c.id"
			+ " WHERE a.id=#{id}"
			+ "</script>")
	VehicleInfoVoPlus findById(Integer id);

}
