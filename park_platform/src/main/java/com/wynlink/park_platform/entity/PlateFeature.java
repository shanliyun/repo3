package com.wynlink.park_platform.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 车牌号特征
 * </p>
 *
 * @author Vincent
 * @since 2019-04-23
 */
public class PlateFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parkingId;
    private Integer passRuleId;
    /**
     * 尾号类型  1.单号  2.双号
     */
    private Integer tailNumType;
    /**
     * 尾号数字
     */
    private String tailNum;


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

    public Integer getPassRuleId() {
        return passRuleId;
    }

    public void setPassRuleId(Integer passRuleId) {
        this.passRuleId = passRuleId;
    }

    public Integer getTailNumType() {
        return tailNumType;
    }

    public void setTailNumType(Integer tailNumType) {
        this.tailNumType = tailNumType;
    }

    public String getTailNum() {
        return tailNum;
    }

    public void setTailNum(String tailNum) {
        this.tailNum = tailNum;
    }


    @Override
    public String toString() {
        return "PlateFeature{" +
        ", id=" + id +
        ", parkingId=" + parkingId +
        ", passRuleId=" + passRuleId +
        ", tailNumType=" + tailNumType +
        ", tailNum=" + tailNum +
        "}";
    }
}
