package com.wynlink.park_platform.entity;

import java.io.Serializable;

/**
 * 数据交互数据对象
 *
 * @author yangyiyong
 * @date 2015-12-21
 *
 */
public class XMessage implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2458647478125194526L;

	/**
	 *  协议类型： 
	 *  	Normal(普通交互) 		= 1
	 *  	HeartbeatRequest	= 2
	 *  	HeartbeatResponse	= 3 
	 */
	private String protocolType;
	
	/**
	 * 源应用类型
	 * 		Adminserver 	= 1
	 * 		Device			= 4
	 * 		Application		= 5
	 * 		Service			= 6
	 */
	private String sourceType;
	
	/**
	 * 源应用SN
	 */
	private String sourceSn;
	
	/**
	 * 源IP
	 */
	private String sourceIp;
	
	/**
	 * 目标应用类型
	 * 	  	Adminserver 	= 1
	 * 		Device			= 4
	 * 		Application		= 5
	 * 		Service			= 6
	 */
	private String targetType;
	
	/**
	 * 目标应用SN
	 */
	private String targetSn;
	
	/**
	 * 消息类型
	 * Request  = 1						//请求
	 * Response = 2						//响应
	 */
	private String messageType;
	
	/**
	 * 协议命令
	 */
	private String command;
	
	/**
	 * 数据体(Request有效)
	 */
	private Object body;

	/**
	 * 响应结果(Response有效)
	 */
	private Boolean success;
	
	/**
	 * 响应结果值(Response有效)
	 */
	private Object resultData;

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceSn() {
		return sourceSn;
	}

	public void setSourceSn(String sourceSn) {
		this.sourceSn = sourceSn;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getTargetSn() {
		return targetSn;
	}

	public void setTargetSn(String targetSn) {
		this.targetSn = targetSn;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

	public XMessage() {
		super();
	}

	public XMessage(String protocolType, String sourceType, String sourceSn, String sourceIp, String targetType, 
			String targetSn, String messageType, String command, Object body) {
		super();
		this.protocolType = protocolType;
		this.sourceType = sourceType;
		this.sourceSn = sourceSn;
		this.sourceIp = sourceIp;
		this.targetType = targetType;
		this.targetSn = targetSn;
		this.messageType = messageType;
		this.command = command;
		this.body = body;
	}

	public XMessage(String protocolType, String sourceType, String sourceSn, String sourceIp, String targetType, 
			String targetSn, String messageType, String command, Object body, Boolean success, Object resultData) {
		super();
		this.protocolType = protocolType;
		this.sourceType = sourceType;
		this.sourceSn = sourceSn;
		this.sourceIp = sourceIp;
		this.targetType = targetType;
		this.targetSn = targetSn;
		this.messageType = messageType;
		this.command = command;
		this.body = body;
		this.success = success;
		this.resultData = resultData;
	}

	
	
}
