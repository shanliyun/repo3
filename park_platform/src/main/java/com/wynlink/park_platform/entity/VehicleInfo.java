package com.wynlink.park_platform.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 车辆信息表
 * </p>
 *
 * @author Vincent
 * @since 2019-04-16
 */
public class VehicleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 车场id
     */
    private Integer parkingId;
    /**
     * 车辆分类id
     */
    private Integer vehicleClassifyId;
    /**
     * 车牌号
     */
    private String plateNo;
    /**
     * 车牌颜色代号
     */
    private Integer plateColorNo;
    /**
     * 车牌颜色
     */
    private String plateColor;
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
    private String ownerPhone;
    private String ownerAddress;
    /**
     * 电子车牌序列号
     */
    private String rfidNo;
    private Date createTime;
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

    public Integer getVehicleClassifyId() {
        return vehicleClassifyId;
    }

    public void setVehicleClassifyId(Integer vehicleClassifyId) {
        this.vehicleClassifyId = vehicleClassifyId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public Integer getPlateColorNo() {
        return plateColorNo;
    }

    public void setPlateColorNo(Integer plateColorNo) {
        this.plateColorNo = plateColorNo;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

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

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "VehicleInfo{" +
        ", id=" + id +
        ", parkingId=" + parkingId +
        ", vehicleClassifyId=" + vehicleClassifyId +
        ", plateNo=" + plateNo +
        ", plateColorNo=" + plateColorNo +
        ", plateColor=" + plateColor +
        ", vehicleColor=" + vehicleColor +
        ", vehicleModel=" + vehicleModel +
        ", vehicleOwner=" + vehicleOwner +
        ", ownerIdNo=" + ownerIdNo +
        ", ownerGender=" + ownerGender +
        ", ownerBirth=" + ownerBirth +
        ", ownerPhone=" + ownerPhone +
        ", ownerAddress=" + ownerAddress +
        ", rfidNo=" + rfidNo +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
