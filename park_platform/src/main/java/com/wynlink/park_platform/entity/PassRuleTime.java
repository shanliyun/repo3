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
 * @since 2019-04-17
 */
public class PassRuleTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parkingId;
    private String timeName;
    private Date createTime;
    private Date updateTime;
    
    @TableField(exist=false)
    private List<PassRuleDate> passRuleDate;
    @TableField(exist=false)
    private List<PassRuleHoliday> passRuleHoliday;
    @TableField(exist=false)
    private List<PassRuleWeek> passRuleWeek;
    
    

	public Integer getParkingId() {
		return parkingId;
	}

	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}

	public List<PassRuleDate> getPassRuleDate() {
		return passRuleDate;
	}

	public void setPassRuleDate(List<PassRuleDate> passRuleDate) {
		this.passRuleDate = passRuleDate;
	}

	public List<PassRuleHoliday> getPassRuleHoliday() {
		return passRuleHoliday;
	}

	public void setPassRuleHoliday(List<PassRuleHoliday> passRuleHoliday) {
		this.passRuleHoliday = passRuleHoliday;
	}

	public List<PassRuleWeek> getPassRuleWeek() {
		return passRuleWeek;
	}

	public void setPassRuleWeek(List<PassRuleWeek> passRuleWeek) {
		this.passRuleWeek = passRuleWeek;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeName() {
        return timeName;
    }

    public void setTimeName(String timeName) {
        this.timeName = timeName;
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
        return "PassRuleTime{" +
        ", id=" + id +
        ", timeName=" + timeName +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
