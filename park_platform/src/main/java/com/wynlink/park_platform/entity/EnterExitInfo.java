package com.wynlink.park_platform.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 出入口信息表
 * </p>
 *
 * @author Vincent
 * @since 2019-04-01
 */
public class EnterExitInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parkingId;
    /**
     * 进出口类型
     */
    private Integer enterExitType;
    
    @TableField(exist=false)
    private String typeName;
    /**
     * 数量
     */
    private Integer typeCount;
    /**
     * 进出口ID
     */
    private String enterExitNo;
    /**
     * 进出口编号
     */
    private Integer enterExitNum;
    /**
     * 进出口名称
     */
    private String enterExitName;
    /**
     * 进出口经度
     */
    private String enterExitLongitude;
    /**
     * 进出口纬度
     */
    private String enterExitLatitude;
    /**
     * 1：入口 2：出口
     */
    private Integer enterExitKind;
    private Integer status;

    
    
    public Integer getEnterExitNum() {
		return enterExitNum;
	}

	public void setEnterExitNum(Integer enterExitNum) {
		this.enterExitNum = enterExitNum;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

    public Integer getEnterExitType() {
        return enterExitType;
    }

    public void setEnterExitType(Integer enterExitType) {
        this.enterExitType = enterExitType;
    }

    public Integer getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(Integer typeCount) {
        this.typeCount = typeCount;
    }

    public String getEnterExitNo() {
        return enterExitNo;
    }

    public void setEnterExitNo(String enterExitNo) {
        this.enterExitNo = enterExitNo;
    }

    public String getEnterExitName() {
        return enterExitName;
    }

    public void setEnterExitName(String enterExitName) {
        this.enterExitName = enterExitName;
    }

    public String getEnterExitLongitude() {
        return enterExitLongitude;
    }

    public void setEnterExitLongitude(String enterExitLongitude) {
        this.enterExitLongitude = enterExitLongitude;
    }

    public String getEnterExitLatitude() {
        return enterExitLatitude;
    }

    public void setEnterExitLatitude(String enterExitLatitude) {
        this.enterExitLatitude = enterExitLatitude;
    }

    public Integer getEnterExitKind() {
        return enterExitKind;
    }

    public void setEnterExitKind(Integer enterExitKind) {
        this.enterExitKind = enterExitKind;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "EnterExitInfo {id=" + id + ", parkingId=" + parkingId + ", enterExitType=" + enterExitType
				+ ", typeName=" + typeName + ", typeCount=" + typeCount + ", enterExitNo=" + enterExitNo
				+ ", enterExitNum=" + enterExitNum + ", enterExitName=" + enterExitName + ", enterExitLongitude="
				+ enterExitLongitude + ", enterExitLatitude=" + enterExitLatitude + ", enterExitKind=" + enterExitKind
				+ ", status=" + status + "}";
	}

    
}
