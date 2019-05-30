package com.wynlink.park_platform.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author Vincent
 * @since 2019-04-16
 */
public class FeeRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parkingId;
    @TableField(exist=false)
    private String parkingName;
    /**
     * 收费规则名称
     */
    private String feeRuleName;
    
    private Integer cycleNotEnough;
    
    private Integer maxFee;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    
    @TableField(exist=false)
    private List<FeeRuleConfig> feeRuleConfigs;
    @TableField(exist=false)
    private List<FeeRuleConfig2> feeRuleConfig2s;
    
    /*@TableField(exist=false)
    private FeeRuleConfigOne feeRuleConfigOne;
    @TableField(exist=false)
    private FeeRuleConfigTwo feeRuleConfigTwo;
    @TableField(exist=false)
    private FeeRuleConfigThree feeRuleConfigThree;*/

    

	public String getParkingName() {
		return parkingName;
	}


	public List<FeeRuleConfig2> getFeeRuleConfig2s() {
		return feeRuleConfig2s;
	}


	public void setFeeRuleConfig2s(List<FeeRuleConfig2> feeRuleConfig2s) {
		this.feeRuleConfig2s = feeRuleConfig2s;
	}


	public Integer getCycleNotEnough() {
		return cycleNotEnough;
	}


	public void setCycleNotEnough(Integer cycleNotEnough) {
		this.cycleNotEnough = cycleNotEnough;
	}


	public List<FeeRuleConfig> getFeeRuleConfigs() {
		return feeRuleConfigs;
	}

	public void setFeeRuleConfigs(List<FeeRuleConfig> feeRuleConfigs) {
		this.feeRuleConfigs = feeRuleConfigs;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public Integer getMaxFee() {
		return maxFee;
	}

	public void setMaxFee(Integer maxFee) {
		this.maxFee = maxFee;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
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

    public String getFeeRuleName() {
        return feeRuleName;
    }

    public void setFeeRuleName(String feeRuleName) {
        this.feeRuleName = feeRuleName;
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
        return "FeeRule{" +
        ", id=" + id +
        ", parkingId=" + parkingId +
        ", feeRuleName=" + feeRuleName +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
