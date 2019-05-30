package com.wynlink.park_platform.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Vincent
 * @since 2019-05-06
 */
public class FeeRuleConfig2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parkingId;
    private Integer feeRuleId;
    private Integer hour;
    private Integer money;


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

    public Integer getFeeRuleId() {
        return feeRuleId;
    }

    public void setFeeRuleId(Integer feeRuleId) {
        this.feeRuleId = feeRuleId;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "FeeRuleConfig2{" +
        ", id=" + id +
        ", parkingId=" + parkingId +
        ", feeRuleId=" + feeRuleId +
        ", hour=" + hour +
        ", money=" + money +
        "}";
    }
}
