package com.wynlink.park_platform.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 黑名单车辆表
 * </p>
 *
 * @author Vincent
 * @since 2019-03-22
 */
public class BlackList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parkingId;
    @TableField(exist=false)
    private String parkingName;
    private String plateNo;
    
    private String plateColorName;
    /**
     * 黑名单类型：逃费为1，套牌为2。
     */
    private Integer blackType;
    /**
     * 欠费次数
     */
    private Integer arrearageCount;
    /**
     * 欠费总额
     */
    private Double arrearageTotal;
    private String remark;
    /**
     * 是否黑名单 0:不是  1：是
     */
    private Integer isBlack;
    private Date createTime;
    private Date updateTime;

    

    public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public String getPlateColorName() {
		return plateColorName;
	}

	public void setPlateColorName(String plateColorName) {
		this.plateColorName = plateColorName;
	}

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

	public Integer getBlackType() {
        return blackType;
    }

    public void setBlackType(Integer blackType) {
        this.blackType = blackType;
    }


    public Integer getArrearageCount() {
        return arrearageCount;
    }

    public void setArrearageCount(Integer arrearageCount) {
        this.arrearageCount = arrearageCount;
    }

    public Double getArrearageTotal() {
        return arrearageTotal;
    }

    public void setArrearageTotal(Double arrearageTotal) {
        this.arrearageTotal = arrearageTotal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(Integer isBlack) {
        this.isBlack = isBlack;
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
        return "BlackList{" +
        ", id=" + id +
        ", plateNo=" + plateNo +
        ", blackType=" + blackType +
        ", arrearageCount=" + arrearageCount +
        ", arrearageTotal=" + arrearageTotal +
        ", remark=" + remark +
        ", isBlack=" + isBlack +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
