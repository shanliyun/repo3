package com.wynlink.park_platform.service;

import com.wynlink.park_platform.entity.MonthCard;
import com.wynlink.park_platform.entity.vo.MonthCardVo;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 月卡 服务类
 * </p>
 *
 * @author Vincent
 * @since 2019-05-29
 */
public interface MonthCardService extends IService<MonthCard> {

	Page<MonthCardVo> findAllPage(Page<MonthCardVo> page, Map<String, Object> map);

}
