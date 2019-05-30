package com.wynlink.park_platform.entity;

import java.io.Serializable;

public class Jckxx implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6884281131967636501L;

	/*
	 * 进出口编号
	 * 指该进出口在本停车场 的数字编号，从 1 开始 按自然数顺延且不能重 复，用以明确进出口在 
	 * 停车场内的地理位置及 数量。进口与出口处于 * 同一地理位置的，应统 2 深圳市公安局交通
	 * 警察局 10 一采用同一个进出口编 号。如：天安数码城停 车场，南进出口编号为： 1，西进出口编号为：2
	 */
	private String JCKBH;	
	
	/*
	 * 进出口名称 
	 * 指该进出口在本停车场 所使用通用名称，如： 一号进出口、百花路进 出口、主进出口等
	 */
	private String JCKMC;
	
	/*
	 * 0-百度；1-高德； 2-其它
	 */
	private String JCKJWDBZ;
	
	/* 进出口经度 */
	private String JCKJD;
	
	/* 进出口纬度 */
	private String JCKWD;

	public Jckxx() {
		super();
	}

	public Jckxx(String jCKBH, String jCKMC, String jCKJWDBZ, String jCKJD, String jCKWD) {
		super();
		JCKBH = jCKBH;
		JCKMC = jCKMC;
		JCKJWDBZ = jCKJWDBZ;
		JCKJD = jCKJD;
		JCKWD = jCKWD;
	}

	public String getJCKBH() {
		return JCKBH;
	}

	public void setJCKBH(String jCKBH) {
		JCKBH = jCKBH;
	}

	public String getJCKMC() {
		return JCKMC;
	}

	public void setJCKMC(String jCKMC) {
		JCKMC = jCKMC;
	}

	public String getJCKJWDBZ() {
		return JCKJWDBZ;
	}

	public void setJCKJWDBZ(String jCKJWDBZ) {
		JCKJWDBZ = jCKJWDBZ;
	}

	public String getJCKJD() {
		return JCKJD;
	}

	public void setJCKJD(String jCKJD) {
		JCKJD = jCKJD;
	}

	public String getJCKWD() {
		return JCKWD;
	}

	public void setJCKWD(String jCKWD) {
		JCKWD = jCKWD;
	}
	
	
}