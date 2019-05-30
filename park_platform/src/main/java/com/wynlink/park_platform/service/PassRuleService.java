package com.wynlink.park_platform.service;

import com.wynlink.park_platform.entity.PassRule;
import com.wynlink.park_platform.entity.vo.PassRuleVo;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 通行规则表 服务类
 * </p>
 *
 * @author Vincent
 * @since 2019-04-17
 */
public interface PassRuleService extends IService<PassRule> {

	Page<PassRuleVo> findAll(Page page, Map<String, Object> map);

}
