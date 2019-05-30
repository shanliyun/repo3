package com.wynlink.park_platform.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wynlink.park_platform.entity.FeeRule;
import com.wynlink.park_platform.entity.vo.FeeRuleVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Vincent
 * @since 2019-04-16
 */
public interface FeeRuleService extends IService<FeeRule> {

	Page<FeeRuleVo> findAll(Page<FeeRuleVo> page, Map<String, Object> map);



}
