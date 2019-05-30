package com.wynlink.park_platform.mapper;

import com.wynlink.park_platform.entity.PassRule;
import com.wynlink.park_platform.entity.vo.PassRuleVo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 通行规则表 Mapper 接口
 * </p>
 *
 * @author Vincent
 * @since 2019-04-17
 */
public interface PassRuleMapper extends BaseMapper<PassRule> {

	
	@Select(
			"<script>"
			+ "SELECT * FROM pass_rule a "
			+ "LEFT JOIN parking_info b ON a.parking_id = b.id "
			+ "LEFT JOIN vehicle_classify c ON a.vehicle_classify_id=c.id "
			+ "LEFT JOIN fee_rule d ON a.fee_rule_id=d.id "
			+ " <where>"
			+ " a.status=1 "
			+ " <if test='parkingId!=null'> "
			+ " AND a.parking_id=#{parkingId}"
			+ " </if>"
			+ "<if test='vehicleClassifyId!=null'>"
			+ " AND a.vehicle_classify_id=#{vehicleClassifyId}"
			+ "</if>"
			+ "</where> "
			+ "ORDER BY a.update_time DESC"
			+ "</script>")
	List<PassRuleVo> findAll(Page page, Map<String, Object> map);

}
