package com.wynlink.park_platform.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 月租车信息表
 * </p>
 *
 * @author Vincent
 * @since 2019-04-15
 */
public class VehicleMonthly implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 车场id
     */
    private Integer parkingId;
    /**
     * 车牌号
     */
    private String plateNo;
    /**
     * 车牌颜色
     */
    private String plateColorName;
    /**
     * 月租类型编号
     */
    private Integer monthlyTypeNo;
    /**
     * 月数
     */
    private Integer months;
    /**
     * 每月租金
     */
    private Integer monthlyRent;
    /**
     * 总租金
     */
    private Integer totalRent;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 截至时间
     */
    private Date endTime;
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

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getPlateColorName() {
        return plateColorName;
    }

    public void setPlateColorName(String plateColorName) {
        this.plateColorName = plateColorName;
    }

    public Integer getMonthlyTypeNo() {
        return monthlyTypeNo;
    }

    public void setMonthlyTypeNo(Integer monthlyTypeNo) {
        this.monthlyTypeNo = monthlyTypeNo;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Integer monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public Integer getTotalRent() {
        return totalRent;
    }

    public void setTotalRent(Integer totalRent) {
        this.totalRent = totalRent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
        return "VehicleMonthly{" +
        ", id=" + id +
        ", parkingId=" + parkingId +
        ", plateNo=" + plateNo +
        ", plateColorName=" + plateColorName +
        ", monthlyTypeNo=" + monthlyTypeNo +
        ", months=" + months +
        ", monthlyRent=" + monthlyRent +
        ", totalRent=" + totalRent +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
