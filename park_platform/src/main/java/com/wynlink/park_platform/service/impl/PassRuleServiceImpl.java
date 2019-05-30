package com.wynlink.park_platform.service.impl;

import com.wynlink.park_platform.entity.PassRule;
import com.wynlink.park_platform.entity.vo.PassRuleVo;
import com.wynlink.park_platform.mapper.PassRuleMapper;
import com.wynlink.park_platform.service.PassRuleService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 通行规则表 服务实现类
 * </p>
 *
 * @author Vincent
 * @since 2019-04-17
 */
@Service
public class PassRuleServiceImpl extends ServiceImpl<PassRuleMapper, PassRule> implements PassRuleService {

	@Override
	public Page<PassRuleVo> findAll(Page page, Map<String, Object> map) {
		return page.setRecords(this.baseMapper.findAll(page,map));
	}

}
