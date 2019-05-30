package com.wynlink.park_platform.entity.vo;

import java.util.Date;

/**
 * <p>
 * 月租车信息表
 * </p>
 *
 * @author Vincent
 * @since 2019-04-15
 */
public class VehicleMonthlyVoPlus extends VehicleMonthlyVo {
	
	/**
	 * 月租类型编号
	 */
	private Integer monthlyTypeNo;

	/**
     * 月租类型
     */
    private String monthlyType;
    
    /**
     * 每月租金
     */
    private Integer monthlyRent;
    
    /**
     * 开始时间
     */
    private Date startTime;
    
    

	public Integer getMonthlyTypeNo() {
		return monthlyTypeNo;
	}

	public void setMonthlyTypeNo(Integer monthlyTypeNo) {
		this.monthlyTypeNo = monthlyTypeNo;
	}

	public String getMonthlyType() {
		return monthlyType;
	}

	public void setMonthlyType(String monthlyType) {
		this.monthlyType = monthlyType;
	}

	public Integer getMonthlyRent() {
		return monthlyRent;
	}

	public void setMonthlyRent(Integer monthlyRent) {
		this.monthlyRent = monthlyRent;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
    
    
    
}
