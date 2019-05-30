package com.wynlink.park_platform.entity.vo;

import java.util.Date;

public class VehicleInfoVoPlus extends VehicleInfoVo {
	
	/**
     * 车辆颜色
     */
    private String vehicleColor;
    
    /**
     * 车辆品牌型号
     */
    private String vehicleModel;
    
    /**
     * 车辆所有人
     */
    private String vehicleOwner;
    /**
     * 车主身份证
     */
    private String ownerIdNo;
    /**
     * 车主性别：1男  2女
     */
    private Integer ownerGender;
    
    /**
     * 车主出生日期
     */
    private Date ownerBirth;
    
    private String ownerAddress;
    /**
     * 电子车牌序列号
     */
    private String rfidNo;
	public String getVehicleColor() {
		return vehicleColor;
	}
	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public String getVehicleOwner() {
		return vehicleOwner;
	}
	public void setVehicleOwner(String vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}
	public String getOwnerIdNo() {
		return ownerIdNo;
	}
	public void setOwnerIdNo(String ownerIdNo) {
		this.ownerIdNo = ownerIdNo;
	}
	public Integer getOwnerGender() {
		return ownerGender;
	}
	public void setOwnerGender(Integer ownerGender) {
		this.ownerGender = ownerGender;
	}
	public Date getOwnerBirth() {
		return ownerBirth;
	}
	public void setOwnerBirth(Date ownerBirth) {
		this.ownerBirth = ownerBirth;
	}
	public String getOwnerAddress() {
		return ownerAddress;
	}
	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}
	public String getRfidNo() {
		return rfidNo;
	}
	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}
    
    
}
