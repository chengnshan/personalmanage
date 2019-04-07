package com.cxp.personalmanage.config.exceptionHandle;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxp.personalmanage.pojo.execp.MyException;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 默认异常处理方法,返回异常信息
	 */
	@ExceptionHandler(value = MyException.class)
	@ResponseBody
	public Map<String,Object> defaulErrorHandler(HttpServletRequest request, MyException ex) throws Exception {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resultCode", -1);
		map.put("resultMessage", ex.getMessage());
		return map;
	}

	/**
	 * @ExceptionHandler 匹配抛出自定义的异常类型 MyException ErrorInfo<String>
	 *                   为自定义的一个数据封装类，用于返回的json数据 如果返回的是json格式，需要加上@ResponsBody
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Map<String,Object> jsonErrorHandler(HttpServletRequest request, Exception ex) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resultCode", -1);
		map.put("resultMessage", ex.getMessage());
		return map;
	}

}
