package com.wynlink.park_platform.entity.vo;

import java.util.Date;

public class PassRuleVo {

	private Integer id;
	private Integer parkingId;
	private String parkingName;
	private String passRuleName;
	private Integer vehicleClassifyId;
	private String vehicleClassifyName;
	private Integer feeRuleId;
	private String feeRuleName;
	private Date updateTime;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParkingId() {
		return parkingId;
	}

	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public String getPassRuleName() {
		return passRuleName;
	}

	public void setPassRuleName(String passRuleName) {
		this.passRuleName = passRuleName;
	}

	public Integer getVehicleClassifyId() {
		return vehicleClassifyId;
	}

	public void setVehicleClassifyId(Integer vehicleClassifyId) {
		this.vehicleClassifyId = vehicleClassifyId;
	}

	public String getVehicleClassifyName() {
		return vehicleClassifyName;
	}

	public void setVehicleClassifyName(String vehicleClassifyName) {
		this.vehicleClassifyName = vehicleClassifyName;
	}

	public Integer getFeeRuleId() {
		return feeRuleId;
	}

	public void setFeeRuleId(Integer feeRuleId) {
		this.feeRuleId = feeRuleId;
	}

	public String getFeeRuleName() {
		return feeRuleName;
	}

	public void setFeeRuleName(String feeRuleName) {
		this.feeRuleName = feeRuleName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
