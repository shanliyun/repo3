package com.wynlink.park_platform.service;

import com.wynlink.park_platform.entity.BlackList;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Vincent
 * @since 2019-03-19
 */
public interface BlackListService extends IService<BlackList> {

	List<Map<String, Object>> findAll(Integer parkingId);

	Page<BlackList> findAll(Page<BlackList> page, Map<String, Object> map);

	BlackList findById(Integer id);

}
