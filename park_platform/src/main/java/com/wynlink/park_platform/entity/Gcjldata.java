package com.wynlink.park_platform.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Vincent
 * @since 2019-03-28
 */
public class Gcjldata implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer imgId;
    private Integer serverType;
    private Integer serverId;
    private String serverName;
    private String hasPayed;
    private Date passingTime;
    private Integer recognitionType;
    private Integer paymentCarFeeType;
    private String paymentFeeSystemTradeNo;
    private Double paymentFreeFee;
    private String paymentOrderNo;
    private Integer paymentPay;
    private Date paymentPayTime;
    private Integer paymentPayType;
    private String paymentPaymentNo;
    private String paymentPfOrderNo;
    private Double paymentTollFee;
    private String plateColor;
    private String plateNum;
    private String rfid;
    private Integer statusCode;
    private Date createTime;
    private Date updateTime;


	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getHasPayed() {
        return hasPayed;
    }

    public void setHasPayed(String hasPayed) {
        this.hasPayed = hasPayed;
    }

    public Date getPassingTime() {
        return passingTime;
    }

    public void setPassingTime(Date passingTime) {
        this.passingTime = passingTime;
    }

    public Integer getRecognitionType() {
        return recognitionType;
    }

    public void setRecognitionType(Integer recognitionType) {
        this.recognitionType = recognitionType;
    }

    public Integer getPaymentCarFeeType() {
        return paymentCarFeeType;
    }

    public void setPaymentCarFeeType(Integer paymentCarFeeType) {
        this.paymentCarFeeType = paymentCarFeeType;
    }

    public String getPaymentFeeSystemTradeNo() {
        return paymentFeeSystemTradeNo;
    }

    public void setPaymentFeeSystemTradeNo(String paymentFeeSystemTradeNo) {
        this.paymentFeeSystemTradeNo = paymentFeeSystemTradeNo;
    }

    public Double getPaymentFreeFee() {
        return paymentFreeFee;
    }

    public void setPaymentFreeFee(Double paymentFreeFee) {
        this.paymentFreeFee = paymentFreeFee;
    }

    public String getPaymentOrderNo() {
        return paymentOrderNo;
    }

    public void setPaymentOrderNo(String paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo;
    }

    public Integer getPaymentPay() {
        return paymentPay;
    }

    public void setPaymentPay(Integer paymentPay) {
        this.paymentPay = paymentPay;
    }

    public Date getPaymentPayTime() {
        return paymentPayTime;
    }

    public void setPaymentPayTime(Date paymentPayTime) {
        this.paymentPayTime = paymentPayTime;
    }

    public Integer getPaymentPayType() {
        return paymentPayType;
    }

    public void setPaymentPayType(Integer paymentPayType) {
        this.paymentPayType = paymentPayType;
    }

    public String getPaymentPaymentNo() {
        return paymentPaymentNo;
    }

    public void setPaymentPaymentNo(String paymentPaymentNo) {
        this.paymentPaymentNo = paymentPaymentNo;
    }

    public String getPaymentPfOrderNo() {
        return paymentPfOrderNo;
    }

    public void setPaymentPfOrderNo(String paymentPfOrderNo) {
        this.paymentPfOrderNo = paymentPfOrderNo;
    }

    public Double getPaymentTollFee() {
        return paymentTollFee;
    }

    public void setPaymentTollFee(Double paymentTollFee) {
        this.paymentTollFee = paymentTollFee;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
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
		return "Gcjldata [id=" + id + ", imgId=" + imgId + ", serverType=" + serverType + ", serverId=" + serverId
				+ ", serverName=" + serverName + ", hasPayed=" + hasPayed + ", passingTime=" + passingTime
				+ ", recognitionType=" + recognitionType + ", paymentCarFeeType=" + paymentCarFeeType
				+ ", paymentFeeSystemTradeNo=" + paymentFeeSystemTradeNo + ", paymentFreeFee=" + paymentFreeFee
				+ ", paymentOrderNo=" + paymentOrderNo + ", paymentPay=" + paymentPay + ", paymentPayTime="
				+ paymentPayTime + ", paymentPayType=" + paymentPayType + ", paymentPaymentNo=" + paymentPaymentNo
				+ ", paymentPfOrderNo=" + paymentPfOrderNo + ", paymentTollFee=" + paymentTollFee + ", plateColor="
				+ plateColor + ", plateNum=" + plateNum + ", rfid=" + rfid + ", statusCode=" + statusCode
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
    
    

}
