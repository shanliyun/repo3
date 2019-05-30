package com.wynlink.park_platform.service.impl;

import com.wynlink.park_platform.entity.MonthCard;
import com.wynlink.park_platform.entity.vo.MonthCardVo;
import com.wynlink.park_platform.mapper.MonthCardMapper;
import com.wynlink.park_platform.service.MonthCardService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 月卡 服务实现类
 * </p>
 *
 * @author Vincent
 * @since 2019-05-29
 */
@Service
public class MonthCardServiceImpl extends ServiceImpl<MonthCardMapper, MonthCard> implements MonthCardService {

	@Override
	public Page<MonthCardVo> findAllPage(Page<MonthCardVo> page, Map<String, Object> map) {
		return page.setRecords(this.baseMapper.findAllPage(page,map));
	}

}
