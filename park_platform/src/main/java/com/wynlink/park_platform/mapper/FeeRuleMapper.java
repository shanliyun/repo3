package com.wynlink.park_platform.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.wynlink.park_platform.entity.FeeRule;
import com.wynlink.park_platform.entity.vo.FeeRuleVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Vincent
 * @since 2019-04-16
 */
public interface FeeRuleMapper extends BaseMapper<FeeRule> {

	
	@Select(
			"<script>"
			+ "SELECT * FROM fee_rule a "
			+ "LEFT JOIN parking_info c ON a.parking_id=c.id"
			+ " <where>"
			+ " 1=1 "
			+ " <if test='parkingId!=null'> "
			+ " AND a.parking_id=#{parkingId}"
			+ " </if>"
			+ "<if test='feeRuleName!=null'>"
			+ " AND a.fee_rule_name LIKE #{feeRuleName}"
			+ "</if>"
			+ "<if test='status!=null'>"
			+ " AND a.status=#{status}"
			+ "</if>"
			+ "</where> "
			+ "ORDER BY a.update_time DESC"
			+ "</script>")
	List<FeeRuleVo> findAll(Pagination page, Map<String, Object> map);


}
