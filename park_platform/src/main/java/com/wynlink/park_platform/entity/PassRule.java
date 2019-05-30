package com.wynlink.park_platform.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 通行规则表
 * </p>
 *
 * @author Vincent
 * @since 2019-04-18
 */
public class PassRule implements Serializable {

   // private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parkingId;
    /**
     * 通行规则名称
     */
    private String passRuleName;
    /**
     * 时间id
     */
    private Integer timeId;
    /**
     * 设施
     */
    private String facility;
    /**
     * 车辆分类编号
     */
    private Integer vehicleClassifyId;
    /**
     * 车牌颜色编号
     */
    private Integer plateColorNo;
    /**
     * 车辆类型id
     */
    private Integer vehicleTypeId;
    /**
     * 核载人数id
     */
    private String vehicleHzrs;
    /**
     * 许可类型
     */
    private String allowType;
    /**
     * 通行类型id
     */
    private Integer passTypeId;
    /**
     * 收费类型id
     */
    private Integer feeTypeId;
    /**
     * 费率表
     */
    private Integer feeRuleId;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    @TableField(exist=false)
    private PlateFeature plateFeature;

    
    
    public PlateFeature getPlateFeature() {
		return plateFeature;
	}

	public void setPlateFeature(PlateFeature plateFeature) {
		this.plateFeature = plateFeature;
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

    public String getPassRuleName() {
        return passRuleName;
    }

    public void setPassRuleName(String passRuleName) {
        this.passRuleName = passRuleName;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    
    
    public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public Integer getVehicleClassifyId() {
        return vehicleClassifyId;
    }

    public void setVehicleClassifyId(Integer vehicleClassifyId) {
        this.vehicleClassifyId = vehicleClassifyId;
    }

    public Integer getPlateColorNo() {
        return plateColorNo;
    }

    public void setPlateColorNo(Integer plateColorNo) {
        this.plateColorNo = plateColorNo;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
    
    

    public String getVehicleHzrs() {
		return vehicleHzrs;
	}

	public void setVehicleHzrs(String vehicleHzrs) {
		this.vehicleHzrs = vehicleHzrs;
	}

	public Integer getFeeTypeId() {
		return feeTypeId;
	}

	public void setFeeTypeId(Integer feeTypeId) {
		this.feeTypeId = feeTypeId;
	}

	public String getAllowType() {
        return allowType;
    }

    public void setAllowType(String allowType) {
        this.allowType = allowType;
    }

    public Integer getPassTypeId() {
        return passTypeId;
    }

    public void setPassTypeId(Integer passTypeId) {
        this.passTypeId = passTypeId;
    }


    public Integer getFeeRuleId() {
        return feeRuleId;
    }

    public void setFeeRuleId(Integer feeRuleId) {
        this.feeRuleId = feeRuleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "PassRule{" +
        ", id=" + id +
        ", parkingId=" + parkingId +
        ", passRuleName=" + passRuleName +
        ", timeId=" + timeId +
        ", facility=" + facility +
        ", vehicleClassifyId=" + vehicleClassifyId +
        ", plateColorNo=" + plateColorNo +
        ", vehicleTypeId=" + vehicleTypeId +
        ", vehicleHzrs=" + vehicleHzrs +
        ", allowType=" + allowType +
        ", passTypeId=" + passTypeId +
        ", feeRuleId=" + feeRuleId +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
