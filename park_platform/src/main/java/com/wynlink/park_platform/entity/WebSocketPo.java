package com.wynlink.park_platform.entity;

import java.io.Serializable;

/**
 * WebSocket消息格式对象
 * @author jzhang
 *
 */
public class WebSocketPo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1131969937382627920L;
	
	//发送者
	public String from;
	//发送者名称
	public String fromName;
	//接收者
	public String to;
	//发送的文本
	public Object data;
	//发送日期
	public String date;
	//消息类型
	private String lx;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public WebSocketPo() {
		super();
	}
	
}
