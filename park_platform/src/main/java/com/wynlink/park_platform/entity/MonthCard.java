package com.wynlink.park_platform.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 月卡
 * </p>
 *
 * @author Vincent
 * @since 2019-05-29
 */
public class MonthCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private Integer groupId;
    
    private Integer months;
    /**
     * 车辆所有人
     */
    private String owner;
    /**
     * 车牌号
     */
    private String plateNo;
    /**
     * 车牌颜色
     */
    private String plateColor;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 所属车场
     */
    private Integer parkingId;
    /**
     * 车位
     */
    private String spaceNo;
    /**
     * 截至时间
     */
    private Date endTime;
    
    private String money;
    /**
     * 办理人
     */
    private String transactor;
    private Date createTime;
    private Date updateTime;

    

    public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getParkingId() {
        return parkingId;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }

    public String getSpaceNo() {
        return spaceNo;
    }

    public void setSpaceNo(String spaceNo) {
        this.spaceNo = spaceNo;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTransactor() {
        return transactor;
    }

    public void setTransactor(String transactor) {
        this.transactor = transactor;
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
		return "MonthCard {id=" + id + ", groupId=" + groupId + ", months=" + months + ", owner=" + owner + ", plateNo="
				+ plateNo + ", plateColor=" + plateColor + ", phone=" + phone + ", parkingId=" + parkingId
				+ ", spaceNo=" + spaceNo + ", endTime=" + endTime + ", money=" + money + ", transactor=" + transactor
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "}";
	}
    
    

}
