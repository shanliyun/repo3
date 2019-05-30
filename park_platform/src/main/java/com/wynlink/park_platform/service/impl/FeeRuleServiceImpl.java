package com.wynlink.park_platform.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wynlink.park_platform.entity.FeeRule;
import com.wynlink.park_platform.entity.vo.FeeRuleVo;
import com.wynlink.park_platform.mapper.FeeRuleMapper;
import com.wynlink.park_platform.service.FeeRuleService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Vincent
 * @since 2019-04-16
 */
@Service
public class FeeRuleServiceImpl extends ServiceImpl<FeeRuleMapper, FeeRule> implements FeeRuleService {

	@Override
	public Page<FeeRuleVo> findAll(Page<FeeRuleVo> page, Map<String, Object> map) {
		return page.setRecords(this.baseMapper.findAll(page,map));
	}

}
