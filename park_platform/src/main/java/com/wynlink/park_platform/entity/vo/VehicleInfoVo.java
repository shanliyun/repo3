package com.wynlink.park_platform.entity.vo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 车辆信息表
 * </p>
 *
 * @author Vincent
 * @since 2019-04-02
 */
public class VehicleInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private Integer parkingId;
    private String parkingName;
    
    private Integer vehicleClassifyId;
    private String vehicleClassifyName;
    /**
     * 车牌号
     */
    private String plateNo;
    /**
     * 车牌颜色
     */
    private String plateColor;
    private String ownerPhone;

    

	public Integer getParkingId() {
		return parkingId;
	}

	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}

	public Integer getVehicleClassifyId() {
		return vehicleClassifyId;
	}

	public void setVehicleClassifyId(Integer vehicleClassifyId) {
		this.vehicleClassifyId = vehicleClassifyId;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}


	public String getVehicleClassifyName() {
		return vehicleClassifyName;
	}

	public void setVehicleClassifyName(String vehicleClassifyName) {
		this.vehicleClassifyName = vehicleClassifyName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }


    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }



    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }


}
