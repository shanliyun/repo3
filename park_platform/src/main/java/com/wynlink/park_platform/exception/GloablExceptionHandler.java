package com.wynlink.park_platform.exception;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wynlink.park_platform.entity.ResultData;

@ControllerAdvice
public class GloablExceptionHandler {
	@ResponseBody
	@ExceptionHandler(UnauthorizedException.class)
	public ResultData UnauthorizedException() {
		
		ResultData data = new ResultData();
		data.setStatus(1002);
		data.setMessage("用户无权限");
		return data;
	}
	
	
	@ResponseBody
	@ExceptionHandler(UnauthenticatedException.class)
	public ResultData UnauthenticatedException() {
		
		ResultData data = new ResultData();
		data.setStatus(1003);
		data.setMessage("用户未登录");
		return data;
	}
}