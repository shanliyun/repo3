package com.wynlink.park_platform.entity.vo;

import java.io.Serializable;
import java.util.Date;

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
public class FeeRuleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parkingId;
    private String parkingName;
    /**
     * 收费规则名称
     */
    private String feeRuleName;
    
    private Integer maxFee;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getMaxFee() {
		return maxFee;
	}

	public void setMaxFee(Integer maxFee) {
		this.maxFee = maxFee;
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
