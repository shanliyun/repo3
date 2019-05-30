package com.wynlink.park_platform.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 停车场信息表
 * </p>
 *
 * @author Vincent
 * @since 2019-03-27
 */
public class ParkingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private Integer groupId;
    /**
     * 车场编号
     */
    private String parkingNo;
    /**
     * 车场名称
     */
    private String parkingName;
    /**
     * 经度坐标
     */
    private String longitude;
    /**
     * 纬度坐标
     */
    private String latitude;
    /**
     * 剩余车位总数
     */
    private Integer remainCount;
    /**
     * 被预约的车位数
     */
    private Integer reservationCount;
    /**
     * 总车位数
     */
    private Integer totalSpace;
    /**
     * 进出口数量
     */
    private Integer enterExitCount;
    /**
     * 停车场所属城市
     */
    private String cityName;
    /**
     * 停车场详细地址
     */
    private String parkingAddress;
    /**
     * 车场类型：1.小车场 2.大车场
     */
    private Integer parkingType;
    /**
     * 车场种类1.封闭式 2.开放式（路侧）
     */
    private Integer parkingKind;

    /**
     * 停车场所有者
     */
    private String parkingOwner;
    /**
     * 车场状态0.待审核 1.正常 2.满位
     */
    private Integer parkingStatus;
    /**
     * 是否支持网上预约
     */
    private Integer onlineReservation;
    private Date createTime;
    private Date updateTime;

    @TableField(exist=false)
    private List<EnterExitInfo> enterExitInfos = new ArrayList<EnterExitInfo>();
   

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getEnterExitCount() {
		return enterExitCount;
	}

	public void setEnterExitCount(Integer enterExitCount) {
		this.enterExitCount = enterExitCount;
	}

	public List<EnterExitInfo> getEnterExitInfos() {
		return enterExitInfos;
	}

	public void setEnterExitInfos(List<EnterExitInfo> enterExitInfos) {
		this.enterExitInfos = enterExitInfos;
	}


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkingNo() {
        return parkingNo;
    }

    public void setParkingNo(String parkingNo) {
        this.parkingNo = parkingNo;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Integer remainCount) {
        this.remainCount = remainCount;
    }

    public Integer getReservationCount() {
        return reservationCount;
    }

    public void setReservationCount(Integer reservationCount) {
        this.reservationCount = reservationCount;
    }

    public Integer getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(Integer totalSpace) {
        this.totalSpace = totalSpace;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public void setParkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    public Integer getParkingType() {
        return parkingType;
    }

    public void setParkingType(Integer parkingType) {
        this.parkingType = parkingType;
    }

    public Integer getParkingKind() {
        return parkingKind;
    }

    public void setParkingKind(Integer parkingKind) {
        this.parkingKind = parkingKind;
    }


    public String getParkingOwner() {
        return parkingOwner;
    }

    public void setParkingOwner(String parkingOwner) {
        this.parkingOwner = parkingOwner;
    }

    public Integer getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(Integer parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public Integer getOnlineReservation() {
        return onlineReservation;
    }

    public void setOnlineReservation(Integer onlineReservation) {
        this.onlineReservation = onlineReservation;
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
		return "ParkingInfo {id=" + id + ", groupId=" + groupId + ", parkingNo=" + parkingNo + ", parkingName="
				+ parkingName + ", longitude=" + longitude + ", latitude=" + latitude + ", remainCount=" + remainCount
				+ ", reservationCount=" + reservationCount + ", totalSpace=" + totalSpace + ", enterExitCount="
				+ enterExitCount + ", cityName=" + cityName + ", parkingAddress=" + parkingAddress + ", parkingType="
				+ parkingType + ", parkingKind=" + parkingKind + ", parkingOwner=" + parkingOwner + ", parkingStatus="
				+ parkingStatus + ", onlineReservation=" + onlineReservation + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", enterExitInfos=" + enterExitInfos + "}";
	}
    
}
