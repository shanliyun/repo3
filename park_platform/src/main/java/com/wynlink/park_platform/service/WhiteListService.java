package com.wynlink.park_platform.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wynlink.park_platform.entity.WhiteList;

/**
 * <p>
 * 白名单车辆 服务类
 * </p>
 *
 * @author Vincent
 * @since 2019-04-13
 */
public interface WhiteListService extends IService<WhiteList> {

	List<Map<String, Object>> findAll(Integer parkingId);

	WhiteList findById(Integer id);

	Page<WhiteList> findAll(Page<WhiteList> page, Map<String, Object> map);

}
