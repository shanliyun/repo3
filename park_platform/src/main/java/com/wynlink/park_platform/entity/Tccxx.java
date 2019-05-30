package com.wynlink.park_platform.entity;

import java.io.Serializable;
import java.util.List;

public class Tccxx implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8319913223563180384L;

	private String CSID; // 平台分配 服务商在平台的注册编号
	private String CSPTLS; // 服务商系统记录流水号 取随机数 单服务商停车场管理系统内唯一（必须唯 一）
	private String QQSJ; // yyyy-MM-dd HH:mm:ss 请求时间
	private String TCCID; // 停车场 ID 在申请接入时由平台管理方统一编制发 放，平台内唯一
	private String TCCMC; // 停车场名称 停车场申办经营性许可证所使用详细准 确名称
	private String JWDBZ; // 经纬度地图标准 0-百度；1-高德；2-其它
	private String TCCJD; // 停车场经度 停车场中心点或主出入口经度
	private String TCCWD; // 停车场纬度 停车场中心点或主出入口纬度
	private String TCCCWS; // 停车场注册总车位数
	private String KRNCWS; // 可容纳总车位数 指停车场满负荷时实际可提供的总车位数
	private String GXSJ; // yyyy-MM-dd HH:mm:ss 服务商停车场管理系统内更新本条记录 的时间
	private String XXDZ; // 停车场详细地址 停车场申办经营性许可证所使用详细准 确地址
	private String XKZH; // 经营性停车场许可证号 停车场尚未取得许可证前不强制要求报 送，取得后强制要求报送
	private String TPIDS; // 停车场图片的全名列表 [“**.jpg”]，以 jsonlist 转 string 成为 该属性的值
	private String JCKXX;


	public String getJCKXX() {
		return JCKXX;
	}

	public void setJCKXX(String jCKXX) {
		JCKXX = jCKXX;
	}

	public String getCSID() {
		return CSID;
	}

	public void setCSID(String cSID) {
		CSID = cSID;
	}

	public String getCSPTLS() {
		return CSPTLS;
	}

	public void setCSPTLS(String cSPTLS) {
		CSPTLS = cSPTLS;
	}

	public String getQQSJ() {
		return QQSJ;
	}

	public void setQQSJ(String qQSJ) {
		QQSJ = qQSJ;
	}

	public String getTCCID() {
		return TCCID;
	}

	public void setTCCID(String tCCID) {
		TCCID = tCCID;
	}

	public String getTCCMC() {
		return TCCMC;
	}

	public void setTCCMC(String tCCMC) {
		TCCMC = tCCMC;
	}

	public String getJWDBZ() {
		return JWDBZ;
	}

	public void setJWDBZ(String jWDBZ) {
		JWDBZ = jWDBZ;
	}

	public String getTCCJD() {
		return TCCJD;
	}

	public void setTCCJD(String tCCJD) {
		TCCJD = tCCJD;
	}

	public String getTCCWD() {
		return TCCWD;
	}

	public void setTCCWD(String tCCWD) {
		TCCWD = tCCWD;
	}

	public String getTCCCWS() {
		return TCCCWS;
	}

	public void setTCCCWS(String tCCCWS) {
		TCCCWS = tCCCWS;
	}

	public String getKRNCWS() {
		return KRNCWS;
	}

	public void setKRNCWS(String kRNCWS) {
		KRNCWS = kRNCWS;
	}

	public String getGXSJ() {
		return GXSJ;
	}

	public void setGXSJ(String gXSJ) {
		GXSJ = gXSJ;
	}

	public String getXXDZ() {
		return XXDZ;
	}

	public void setXXDZ(String xXDZ) {
		XXDZ = xXDZ;
	}

	public String getXKZH() {
		return XKZH;
	}

	public void setXKZH(String xKZH) {
		XKZH = xKZH;
	}

	public String getTPIDS() {
		return TPIDS;
	}

	public void setTPIDS(String tPIDS) {
		TPIDS = tPIDS;
	}


}