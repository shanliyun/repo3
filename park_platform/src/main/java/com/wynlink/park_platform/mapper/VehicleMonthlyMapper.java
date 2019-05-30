package com.wynlink.park_platform.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.VehicleMonthly;
import com.wynlink.park_platform.entity.vo.VehicleMonthlyVo;
import com.wynlink.park_platform.entity.vo.VehicleMonthlyVoPlus;

/**
 * <p>
 * 月租车信息表 Mapper 接口
 * </p>
 *
 * @author Vincent
 * @since 2019-03-21
 */
public interface VehicleMonthlyMapper extends BaseMapper<VehicleMonthly> {

	
	@Select(
			"<script>"
			+ "SELECT * FROM vehicle_monthly a "
			+ "LEFT JOIN parking_info c ON a.parking_id = c.id "
			+ " <where>"
			+ " 1=1 "
			+ " <if test='parkingId!=null'> "
			+ " AND a.parking_id=#{parkingId}"
			+ " </if>"
			+ "<if test='plateNo!=null'>"
			+ " AND a.plate_no LIKE #{plateNo}"
			+ "</if>"
			+ "</where> "
			+ "ORDER BY a.update_time DESC"
			+ "</script>")
	List<VehicleMonthlyVo> findAllVehicleMonthly(Page page, Map<String, Object> map);

	
	@Select(
			"SELECT * FROM vehicle_monthly a "
			+ "LEFT JOIN parking_info c ON a.parking_id = c.id "
			+ "LEFT JOIN vehicle_monthly_type b ON a.monthly_type_no = b.monthly_type_no "
			+ "WHERE a.id=#{id}")
	VehicleMonthlyVoPlus findVehicleMonthlyById(Integer id);

}
