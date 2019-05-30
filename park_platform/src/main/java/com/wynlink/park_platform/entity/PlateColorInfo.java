package com.wynlink.park_platform.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 车牌颜色表
 * </p>
 *
 * @author Vincent
 * @since 2019-03-30
 */
public class PlateColorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer plateColorNo;
    private String plateColor;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlateColorNo() {
        return plateColorNo;
    }

    public void setPlateColorNo(Integer plateColorNo) {
        this.plateColorNo = plateColorNo;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    @Override
    public String toString() {
        return "PlateColorInfo{" +
        ", id=" + id +
        ", plateColorNo=" + plateColorNo +
        ", plateColor=" + plateColor +
        "}";
    }
}
