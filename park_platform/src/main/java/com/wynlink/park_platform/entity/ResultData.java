package com.wynlink.park_platform.entity;

public class ResultData {
	
	private static final Integer ERROR_STATUS = 1;
	private static final Integer SUCCESS_STATUS = 0;
	
	private Object data;
	
	private String message;
	
	private Integer status;
	
	public ResultData() {
	}

	public ResultData(Object data) {
		this();
		setData(data);
	}

	public ResultData(Object data, String message) {
		this();
		setData(data);
		setMessage(message);
	}
	
	public ResultData(Integer status, String message) {
		setStatus(status);
		setMessage(message);
	}
	
	public static ResultData createError(String message) {
		return new ResultData(ERROR_STATUS, message);
	}
	
	
	public static ResultData createSuccess(String message) {
		
		return new ResultData(SUCCESS_STATUS, message);
	}
	
	public static ResultData createSuccess(Object data, String message) {
		ResultData resultData = new ResultData(SUCCESS_STATUS, message);
		resultData.setData(data);
		return resultData;
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
