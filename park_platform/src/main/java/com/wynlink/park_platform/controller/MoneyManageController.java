package com.wynlink.park_platform.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.MoneyCheckVo;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.service.GcjldataService;

/**
 * 财务管理Controller
 * 
 * @author vincent
 *
 */
@RestController
@RequestMapping("/moneyManage")
public class MoneyManageController {

	@Autowired
	private GcjldataService gcjldataService;

	/**
	 * 收入统计列表展示
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/incomeList")
	public ResultData incomeList(@RequestBody Map<String, Object> params) {
		
		String type = null;
		if(StringUtils.isEmpty(params.get("type"))) {
			return ResultData.createError("type不能为空！");
		}

		Integer currentPage = 1;
		if (!StringUtils.isEmpty(params.get("currentPage"))) {
			currentPage = Integer.parseInt(params.get("currentPage").toString());
		}

		Integer pageSize = 10;

		if (!StringUtils.isEmpty(params.get("pageSize"))) {
			pageSize = Integer.parseInt(params.get("pageSize").toString());
		}
		
		String parkingName = null;
		if(!StringUtils.isEmpty(params.get("parkingName"))) {
			parkingName = params.get("parkingName").toString();
		}
		
		String plateNo = null;
		if(!StringUtils.isEmpty(params.get("plateNo"))) {
			plateNo = params.get("plateNo").toString();
		}
		
		type = params.get("type").toString();

		Map<String, String> condition = new HashMap<>();// 封装条件
		condition.put("parkingName", parkingName);
		condition.put("plateNo", plateNo);
		condition.put("type", type);
		Page page = new Page(currentPage, pageSize);

		List<Map<String, Object>> records = gcjldataService.incomeList(page, condition);
		Long total = gcjldataService.incomeCount(condition);

		page.setRecords(records);
		page.setTotal(total);

		return ResultData.createSuccess(page, "查询成功！");
	}

	/**
	 * 对账
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/moneychecking")
	public ResultData moneychecking(Map<String, Object> params) {
		
		String type = null;
		if(StringUtils.isEmpty(params.get("type"))) {
			return ResultData.createError("type不能为空！");
		}
		

		Map<String,String> condition = new HashMap<>();//封装查询条件
		condition.put("type", params.get("type").toString());

		/**
		 * 先查询昨天收益
		 */
		List<MoneyCheckVo> records = gcjldataService.incomeList(condition);
		/**
		 * 支付中心流水账单
		 */
		List<MoneyCheckVo> zfzxzd = this.zfzxzd();
		
		
		
		
		List<Map<String,Object>> result = new ArrayList<>();
		
		
		
		
		
		
		Map<String, MoneyCheckVo> map1 = new HashMap<String, MoneyCheckVo>();//本地
		Map<String, MoneyCheckVo> map2 = new HashMap<String, MoneyCheckVo>();//支付中心
		
		
		for (MoneyCheckVo moneyCheckVo : records) {
			map1.put(moneyCheckVo.getOrderNo(), moneyCheckVo);
		}
		for (MoneyCheckVo moneyCheckVo : zfzxzd) {
			map2.put(moneyCheckVo.getOrderNo(), moneyCheckVo);
		}
		
		Set<Map<String,Object>> setBd = new HashSet<>();//本地特有
		Set<Map<String,Object>> setDsf = new HashSet<>();//第三方特有
		
		for (Entry<String, MoneyCheckVo> m1 : map1.entrySet()) {
			for (Entry<String, MoneyCheckVo> m2 : map2.entrySet()) {
				if (!map1.containsKey(m2.getKey())) {
					
					MoneyCheckVo checkVo = m2.getValue();
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("orderNo", checkVo.getOrderNo());
					map.put("payTime", checkVo.getPayTime());
					map.put("bdmoney", 0);
					map.put("zfMoney", checkVo.getMoney());
					map.put("type", "漏款");
					setDsf.add(map);
				} 
				if (!map2.containsKey(m1.getKey())) {
					MoneyCheckVo checkVo = m1.getValue();
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("orderNo", checkVo.getOrderNo());
					map.put("payTime", checkVo.getPayTime());
					map.put("bdmoney", checkVo.getMoney());
					map.put("zfMoney", 0);
					map.put("type", "长款");
					setBd.add(map);
				}
			}
		}
		
		List<Map<String, Object>>  diffSet  = new ArrayList<>();//相同订单号，价格差异
		Map<Integer, List<MoneyCheckVo>> diff = this.findListDiff(records, zfzxzd);
		
		if (diff != null) {
			// 取出本地特有数据
			List<MoneyCheckVo> bd = diff.get(0);
			// 取出支付中心特有数据
			List<MoneyCheckVo> zf = diff.get(2);


			for (MoneyCheckVo v1 : bd) {
				for (MoneyCheckVo v2 : zf) {
					if ((v1.getOrderNo()).equals(v2.getOrderNo())) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("orderNo", v1.getOrderNo());
						map.put("payTime", v1.getPayTime());
						map.put("bdmoney", v1.getMoney());
						map.put("zfMoney", v2.getMoney());
						if (Integer.parseInt(v1.getMoney().toString()) > Integer.parseInt(v2.getMoney().toString())) {
							map.put("type", "长款");
						} else {
							map.put("type", "漏款");
						}
						diffSet.add(map);
					} 
				}
				
				
			}
		}
		
		/*
		 * resultData.put("diffSet", diffSet); resultData.put("setBd", setBd);
		 * resultData.put("setDsf", setDsf);
		 */
		
		result.addAll(diffSet);
		result.addAll(setBd);
		result.addAll(setDsf);

		return ResultData.createSuccess(result, "查询成功");
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		for (String str : list) {
			System.out.println(str);
		}
	}

	/**
	 * 获取两个集合不同
	 * 
	 * @param rps1 rps1数据
	 * @param rps2 rps2数据
	 * @return 0:rps1中独有的数据;1:交集的数据;2:rps2中的独有数据
	 */
	private Map<Integer, List<MoneyCheckVo>> findListDiff(List<MoneyCheckVo> bd, List<MoneyCheckVo> zf) {
		// 判断不能为空
		if (bd == null || bd.isEmpty() || zf == null || zf.isEmpty())
			return null;
		// 保存最后的数据
		Map<Integer, List<MoneyCheckVo>> mapList = new HashMap<Integer, List<MoneyCheckVo>>(3);

		// 复制本地支付，作为备份
		List<MoneyCheckVo> bd_bak = new ArrayList<MoneyCheckVo>(bd);

		// 1、获取本地与支付中心不同的元素
		bd.removeAll(zf);

		// 2、获取本地与支付中心相同的元素
		bd_bak.removeAll(bd);

		// 3、获取支付中心中与本地中不同的元素
		zf.removeAll(bd_bak);

		// 经过此转换后本地中数据与支付中心中的数据完全不同
		// bd_bak是bd和zf的交集
		// zf中的数据与bd中的数据完全不同

		mapList.put(0, bd);// 本地中独有的数据
		mapList.put(1, bd_bak);// 交集的数据
		mapList.put(2, zf);// 支付中心中的独有数据

		return mapList;
	}
	
	
	
	private static List<MoneyCheckVo> getDiffrent5(List<MoneyCheckVo> list1, List<MoneyCheckVo> list2) {
        long st = System.nanoTime();
         List<MoneyCheckVo> diff = new ArrayList<MoneyCheckVo>();
         List<MoneyCheckVo> maxList = list1;
         List<MoneyCheckVo> minList = list2;
         if(list2.size()>list1.size())
         {
             maxList = list2;
             minList = list1;
         }
         Map<MoneyCheckVo,Integer> map = new HashMap<MoneyCheckVo,Integer>(maxList.size());
         for (MoneyCheckVo vo : maxList) {
             map.put(vo, 1);
         }
         for (MoneyCheckVo vo : minList) {
             if(map.get(vo)!=null)
             {
                 map.put(vo, 2);
                 continue;
             }
             diff.add(vo);
         }
         for(Map.Entry<MoneyCheckVo, Integer> entry:map.entrySet())
         {
             if(entry.getValue()==1)
             {
                 diff.add(entry.getKey());
             }
         }
        System.out.println("getDiffrent5 total times "+(System.nanoTime()-st));
        return diff;
        
    }

	private List<MoneyCheckVo> zfzxzd() {
		List<MoneyCheckVo> zfzxList = new ArrayList<>();
		MoneyCheckVo checkVo = new MoneyCheckVo("111111", "2019-05-23 12:29:45", "12");
		zfzxList.add(checkVo);
		checkVo = new MoneyCheckVo("222222", "2019-05-23 13:29:45", "13");
		zfzxList.add(checkVo);
		checkVo = new MoneyCheckVo("333333", "2019-03-30 14:14:11", "12");
		zfzxList.add(checkVo);
		checkVo = new MoneyCheckVo("444444", "2019-03-30 15:14:11", "11");
		zfzxList.add(checkVo);
		checkVo = new MoneyCheckVo("555555", "2019-03-30 15:14:11", "5");
		zfzxList.add(checkVo);
		checkVo = new MoneyCheckVo("777777", "2019-03-30 15:14:11", "77");
		zfzxList.add(checkVo);
		return zfzxList;

	}

}
