package com.wynlink.park_platform.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.Gcjldata;
import com.wynlink.park_platform.entity.MoneyCheckVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Vincent
 * @since 2019-03-28
 */
public interface GcjldataService{

	/**
	 * 统计昨日收益
	 * @return
	 */
	public List<Map<String,Object>> yesterdayRecord();
	/**
	 * 实时统计当日收益
	 * @return
	 */
	public Map<String, Object> todayRecord();
	
	/**
	 * 分月统计
	 * @return
	 */
	public List<Map<String,Object>> fytj ();
	
	/**
	 * 查询所有过车数据表信息
	 * @return
	 */
	public List<Map<String,Object>> findAll();
	
	//@DataSource(name = DataSourceNames.SECOND)
	List<Map<String, Object>> findMaxId();
	
	//@DataSource(name = DataSourceNames.SECOND)
	Integer findCount();

	//@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> totalVehicle();


	
	/**
	 * 查询过车信息所有车辆并去重，并标注场内场外
	 */
	public List<Map<String,Object>> allVehicle(Page page,Map<String,Object> map);

	/**
	 * 查询该车牌号的详情数据
	 * @param plateNo
	 * @return
	 */
	public List<Map<String, Object>> findDetailByPlateNo(String plateNo);

	/**
	 * 查询过车信息所有车辆并去重总数
	 */
	public Long allVehicleCount(Map<String,Object> map);
	
	
	/**
	 * 收入明细列表展示
	 */
	public List<Map<String,Object>> incomeList(Page page,
			Map<String,String> map);
	
	/**
	 * 收入明细总数
	 */
	public Long incomeCount(Map<String,String> map);
	/**
	 * 收入明细
	 */
	public List<MoneyCheckVo> incomeList(Map<String, String> map);
}
