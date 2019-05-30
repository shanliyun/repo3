package com.wynlink.park_platform.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.Gcjldata;
import com.wynlink.park_platform.entity.MoneyCheckVo;
import com.wynlink.park_platform.service.GcjldataService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Vincent
 * @since 2019-03-28
 */
@Service
public class GcjldataServiceImpl implements GcjldataService {

	@Autowired
	@Qualifier("secondaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/*
	 * //@DataSource(name = DataSourceNames.SECOND) public List<Map<String, Object>>
	 * fytj() {
	 * 
	 * List<Map<String, Object>> obj = this.baseMapper.selectMaps(new
	 * EntityWrapper<Gcjldata>()
	 * .setSqlSelect("month(payment_pay_time) as month,sum(payment_toll_fee) as total"
	 * ).where("year(payment_pay_time) = DATE_FORMAT(NOW(), '%Y') ")
	 * .groupBy("month (payment_pay_time)")); System.out.println(obj); return obj; }
	 * 
	 * @DataSource(name = DataSourceNames.SECOND) public List<Map<String, Object>>
	 * findMaxId() { List<Map<String, Object>> obj = this.baseMapper.selectMaps(new
	 * EntityWrapper<Gcjldata>() .setSqlSelect("MAX(id)id")); return obj; }
	 * 
	 * //@DataSource(name = DataSourceNames.SECOND) public List<Map<String, Object>>
	 * totalVehicle() { List<Map<String, Object>> obj =
	 * this.baseMapper.selectMaps(new EntityWrapper<Gcjldata>()
	 * .setSqlSelect("plate_num,count(*) AS count").groupBy("plate_num").having(
	 * "count=1"));
	 * 
	 * 
	 * return obj; }
	 * 
	 * 
	 */

	/**
	 * 查询过车信息所有车辆并去重，并标注场内场外
	 */
	@Override
	public List<Map<String, Object>> allVehicle(Page page, Map<String, Object> params) {

		List<Map<String, Object>> list = null;

		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT server_id AS serverId,parking_id AS parkingId,img_id AS imgId,plate_num AS plateNo,parking_name AS parkingName,plate_color AS plateColor,rfid,passing_time AS passingTime,recognition_type AS recognitionType,station_type AS stationType ");
		sb.append("FROM gcjldata AA WHERE 1=1");
		/*
		 * sb.append(
		 * "WHERE id = (SELECT MAX(id) FROM gcjldata BB WHERE BB.plate_num = AA.plate_num GROUP BY BB.plate_num) "
		 * );
		 */
		if (!StringUtils.isEmpty(params.get("parkingName"))) {
			sb.append(" AND parking_name LIKE ");
			sb.append("'%" + params.get("parkingName") + "%'");
		}

		if (!StringUtils.isEmpty(params.get("plateNo"))) {
			sb.append(" AND plate_num LIKE ");
			sb.append("'%" + params.get("plateNo") + "%'");
		}

		sb.append(" ORDER BY passing_time DESC LIMIT ?,?");

		try {
			list = jdbcTemplate.queryForList(sb.toString(), (page.getCurrent() - 1) * page.getSize(), page.getSize());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				/*
				 * String stationType = "0"; String plateNo = map.get("plateNo").toString();
				 * Map<String, Object> stationTypeMap = jdbcTemplate.
				 * queryForMap("SELECT plate_num,station_type,station_name FROM gcjldata WHERE plate_num = ? ORDER BY passing_time DESC limit 1;\n"
				 * + "", plateNo);
				 * 
				 * try { stationType =
				 * stationTypeMap.get("station_type")==null?"0":stationTypeMap.get(
				 * "station_type").toString(); } catch (Exception e) { e.printStackTrace(); }
				 */
				String stationType = "0";
				if (!StringUtils.isEmpty(map.get("stationType"))) {
					stationType = map.get("stationType").toString();
				}
				if ("0".equals(stationType)) {
					map.put("isIn", "场内");
				} else {
					map.put("isIn", "场外");
				}
			}
		}

		return list;
	}

	/**
	 * 根据车牌号查询所有过车历史记录详情
	 */
	@Override
	public List<Map<String, Object>> findDetailByPlateNo(String plateNo) {

		List<Map<String, Object>> list = null;

		String sql = "SELECT server_id AS serverId,parking_id AS parkingId,parking_name AS parkingName,plate_num AS plateNo,plate_color AS plateColor\n"
				+ "		,station_type AS stationType,station_name AS stationName,passing_time AS passingTime\n"
				+ "		,payment_toll_fee AS feeMoney,rfid,img_id AS imgId \n"
				+ "				FROM gcjldata WHERE plate_num = ? ORDER BY passing_time DESC;";

		try {
			list = jdbcTemplate.queryForList(sql, plateNo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 统计昨日收益
	 */
	@Override
	public List<Map<String, Object>> yesterdayRecord() {
		List<Map<String, Object>> list = null;

		String sql = "SELECT " + "parking_name" + ",passing_time" + ",plate_num" + ",plate_color" + ",payment_toll_fee "
				+ "FROM gcjldata " + "WHERE TO_DAYS(NOW()) - TO_DAYS(passing_time) = 1 " + "AND payment_toll_fee != 0";

		try {
			list = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 分月统计
	 */
	@Override
	public List<Map<String, Object>> fytj() {

		List<Map<String, Object>> list = null;

		String sql = "SELECT month(passing_time) as month,sum(payment_toll_fee) as total \n"
				+ "		FROM gcjldata \n" + "WHERE year(passing_time) = DATE_FORMAT(NOW(), '%Y') \n"
				+ "GROUP BY month (passing_time)";

		try {
			list = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询所有车辆
	 */
	@Override
	public List<Map<String, Object>> findAll() {
		List<Map<String, Object>> list = null;
		String sql = "SELECT parking_name AS parkingName,plate_num AS plateNum,plate_color AS plateColor\n"
				+ "		,station_type AS stationType,station_name AS stationName,passing_time AS passingTime\n"
				+ "		,payment_toll_fee AS paymentTollFee,rfid,recognition_type,img_id AS imgId \n"
				+ "				FROM gcjldata ORDER BY id";

		try {
			list = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> findMaxId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询过车表总数
	 */
	@Override
	public Integer findCount() {

		Integer count = 0;
		Map<String, Object> map = null;
		String sql = "SELECT COUNT(*) AS count FROM gcjldata";
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (map != null) {
			if (!StringUtils.isEmpty(map.get("count"))) {
				count = Integer.parseInt(map.get("count").toString());
			}
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> totalVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 实时统计当日收益
	 * 
	 * @return
	 */
	@Override
	public Map<String, Object> todayRecord() {

		Map<String, Object> map = null;
		String sql = "SELECT SUM(payment_toll_fee) as totalTodayMoney FROM gcjldata WHERE TO_DAYS(passing_time) = TO_DAYS(NOW());\n"
				+ "";

		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@Override
	public Long allVehicleCount(Map<String, Object> params) {
		Map<String, Object> map = null;

		Long count = 0L;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  COUNT(*) AS count ");
		sb.append("FROM gcjldata AA ");
		sb.append(
				"WHERE id = (SELECT MIN(id) FROM gcjldata BB WHERE BB.plate_num = AA.plate_num GROUP BY BB.plate_num) ");
		if (!StringUtils.isEmpty(params.get("parkingName"))) {
			sb.append(" AND parking_name LIKE ");
			sb.append("'%" + params.get("parkingName") + "%'");
		}

		if (!StringUtils.isEmpty(params.get("plateNo"))) {
			sb.append(" AND plate_num LIKE ");
			sb.append("'%" + params.get("plateNo") + "%'");
		}

		try {
			map = jdbcTemplate.queryForMap(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (map != null) {
			count = Long.parseLong(map.get("count").toString());
		}
		return count;
	}

	/**
	 * 收入明细
	 */
	@Override
	public List<MoneyCheckVo> incomeList(Map<String, String> map) {

		StringBuilder sb = new StringBuilder(
				"SELECT payment_order_no AS orderNo,passing_time AS payTime,payment_toll_fee AS money");
		
		
		if("0".equals(map.get("type"))) {//昨天
			sb.append("FROM gcjldata WHERE TO_DAYS(NOW()) - TO_DAYS(passing_time) = 1");
		} else if("1".equals(map.get("type"))) {//当天
			sb.append("FROM gcjldata WHERE to_days(passing_time) = to_days(now())");
		} else if("2".equals(map.get("type"))) {//本周
			sb.append("FROM gcjldata WHERE YEARWEEK(date_format(passing_time,'%Y-%m-%d'),1) = YEARWEEK(now(),1)");
		} else if("3".equals(map.get("type"))) {//当月
			sb.append("FROM gcjldata WHERE DATE_FORMAT( passing_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )");
		} else if("4".equals(map.get("type"))) {//上个月
			sb.append("FROM gcjldata WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) ,date_format( passing_time, '%Y%m' ) ) =1");
		}
		
		
		sb.append(" AND payment_toll_fee != 0 ");
		//sb.append(" ORDER BY passing_time DESC");
		
		List<MoneyCheckVo> vos = new ArrayList<MoneyCheckVo>();
		try {
			
			vos = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<>(MoneyCheckVo.class));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vos; 
	}

	/**
	 * 收入明细列表展示
	 */
	@Override
	public List<Map<String, Object>> incomeList(Page page, Map<String, String> map) {
		List<Map<String, Object>> list = null;

		int currentPage = page.getCurrent();
		int pageSize = page.getSize();

		StringBuilder sb = new StringBuilder(
				"SELECT parking_name AS parkingName,passing_time AS passingTime,plate_num AS plateNo,plate_color AS plateColor,payment_toll_fee AS paymentTollFee ");
		
		if("0".equals(map.get("type"))) {//昨天
			sb.append("FROM gcjldata WHERE TO_DAYS(NOW()) - TO_DAYS(passing_time) = 1");
		} else if("1".equals(map.get("type"))) {//当天
			sb.append("FROM gcjldata WHERE to_days(passing_time) = to_days(now())");
		} else if("2".equals(map.get("type"))) {//本周
			sb.append("FROM gcjldata WHERE YEARWEEK(date_format(passing_time,'%Y-%m-%d'),1) = YEARWEEK(now(),1)");
		} else if("3".equals(map.get("type"))) {//当月
			sb.append("FROM gcjldata WHERE DATE_FORMAT( passing_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )");
		} else if("4".equals(map.get("type"))) {//上个月
			sb.append("FROM gcjldata WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) ,date_format( passing_time, '%Y%m' ) ) =1");
		}
		
		
		sb.append(" AND payment_toll_fee != 0 ");
		if (!StringUtils.isEmpty(map.get("parkingName"))) {
			sb.append(" AND parking_name LIKE ");
			sb.append("'%" + map.get("parkingName") + "%'");
		}
		if (!StringUtils.isEmpty(map.get("plateNo"))) {
			sb.append(" AND plate_num LIKE ");
			sb.append("'%" + map.get("plateNo") + "%'");
		}
		sb.append(" ORDER BY passing_time DESC LIMIT ?,?");
		/*
		 * String sql = "SELECT " + "parking_name AS parkingName" +
		 * ",passing_time AS passingTime" + ",plate_num AS plateNo" +
		 * ",plate_color AS plateColor" + ",payment_toll_fee AS paymentTollFee " +
		 * "FROM gcjldata WHERE TO_DAYS(NOW()) - TO_DAYS(passing_time) = 1 \n" +
		 * "		AND payment_toll_fee != 0 \n" +
		 * 
		 * 
		 * "ORDER BY passing_time DESC LIMIT ?,?";
		 */

		try {
			list = jdbcTemplate.queryForList(sb.toString(), (currentPage - 1) * pageSize, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 收入明细总数
	 */
	@Override
	public Long incomeCount(Map<String, String> map) {

		Map<String, Object> totalMap = null;
		Long total = 0L;
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) total ");
		
		if("0".equals(map.get("type"))) {//昨天
			sb.append("FROM gcjldata WHERE TO_DAYS(NOW()) - TO_DAYS(passing_time) = 1");
		} else if("1".equals(map.get("type"))) {//当天
			sb.append("FROM gcjldata WHERE to_days(passing_time) = to_days(now())");
		} else if("2".equals(map.get("type"))) {//本周
			sb.append("FROM gcjldata WHERE YEARWEEK(date_format(passing_time,'%Y-%m-%d'),1) = YEARWEEK(now(),1)");
		} else if("3".equals(map.get("type"))) {//当月
			sb.append("FROM gcjldata WHERE DATE_FORMAT( passing_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )");
		} else if("4".equals(map.get("type"))) {//上个月
			sb.append("FROM gcjldata WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) ,date_format( passing_time, '%Y%m' ) ) =1");
		}
		
		sb.append(" AND payment_toll_fee != 0 ");
		if (!StringUtils.isEmpty(map.get("parkingName"))) {
			sb.append(" AND parking_name LIKE ");
			sb.append("'%" + map.get("parkingName") + "%'");
		}
		if (!StringUtils.isEmpty(map.get("plateNo"))) {
			sb.append(" AND plate_num LIKE ");
			sb.append("'%" + map.get("plateNo") + "%'");
		}

		try {
			totalMap = jdbcTemplate.queryForMap(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (totalMap != null) {
			total = Long.parseLong(totalMap.get("total").toString());
		}

		return total;
	}

}
