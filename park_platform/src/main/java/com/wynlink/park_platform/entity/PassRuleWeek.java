package com.wynlink.park_platform.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 通行规则星期表
 * </p>
 *
 * @author Vincent
 * @since 2019-04-17
 */
public class PassRuleWeek implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 时间表id
     */
    private Integer parkingId;
    private Integer timeId;
    /**
     * 星期几
     */
    private String whatWeek;
    private String startTime;
    private String endTime;

    
    
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

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public String getWhatWeek() {
        return whatWeek;
    }

    public void setWhatWeek(String whatWeek) {
        this.whatWeek = whatWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PassRuleWeek{" +
        ", id=" + id +
        ", timeId=" + timeId +
        ", whatWeek=" + whatWeek +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        "}";
    }
}
