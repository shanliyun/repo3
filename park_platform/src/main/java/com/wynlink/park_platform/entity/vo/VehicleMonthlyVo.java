package com.wynlink.park_platform.entity.vo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 月租车信息表
 * </p>
 *
 * @author Vincent
 * @since 2019-04-15
 */
public class VehicleMonthlyVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 车场名称
     */
    private Integer parkingId;
    private String parkingName;
    /**
     * 车牌号
     */
    private String plateNo;
    /**
     * 车牌颜色
     */
    private String plateColorName;
    /**
     * 月数
     */
    private Integer months;
    /**
     * 总租金
     */
    private Integer totalRent;
    /**
     * 截至时间
     */
    private Date endTime;
    
    
    
	public Integer getParkingId() {
		return parkingId;
	}
	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
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
	public Integer getMonths() {
		return months;
	}
	public void setMonths(Integer months) {
		this.months = months;
	}
	public Integer getTotalRent() {
		return totalRent;
	}
	public void setTotalRent(Integer totalRent) {
		this.totalRent = totalRent;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

    
}
