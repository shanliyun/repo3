package com.wynlink.park_platform.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author Vincent
 * @since 2019-04-22
 */
public class FeeRuleConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parkingId;
    private Integer feeRuleId;
    private Integer indexNum;
    private Integer startMin;
    private Integer endMin;
    /**
     * 周期类型 ： 固定，按30分钟，按60分钟
     */
    private Integer cycleTypeId;
    
    private Integer cycleMin;
    /**
     * 周期金额，单位分
     */
    private Integer cycleFee;

    
    
    
    public Integer getParkingId() {
		return parkingId;
	}

	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}

	public Integer getCycleMin() {
		return cycleMin;
	}

	public void setCycleMin(Integer cycleMin) {
		this.cycleMin = cycleMin;
	}

	public Integer getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFeeRuleId() {
        return feeRuleId;
    }

    public void setFeeRuleId(Integer feeRuleId) {
        this.feeRuleId = feeRuleId;
    }

    
    

	public Integer getStartMin() {
		return startMin;
	}

	public void setStartMin(Integer startMin) {
		this.startMin = startMin;
	}

	public Integer getEndMin() {
		return endMin;
	}

	public void setEndMin(Integer endMin) {
		this.endMin = endMin;
	}

	public Integer getCycleTypeId() {
        return cycleTypeId;
    }

    public void setCycleTypeId(Integer cycleTypeId) {
        this.cycleTypeId = cycleTypeId;
    }

    public Integer getCycleFee() {
        return cycleFee;
    }

    public void setCycleFee(Integer cycleFee) {
        this.cycleFee = cycleFee;
    }

    @Override
    public String toString() {
        return "FeeRuleConfig{" +
        ", id=" + id +
        ", feeRuleId=" + feeRuleId +
        ", startMin=" + startMin +
        ", endMin=" + endMin +
        ", cycleTypeId=" + cycleTypeId +
        ", cycleFee=" + cycleFee +
        "}";
    }
}
