package com.cxp.personalmanage.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cxp.personalmanage.pojo.base.BaseResultVo;
import com.cxp.personalmanage.pojo.base.CommonRequestDTO;
import com.cxp.personalmanage.pojo.base.CommonResponseDTO;
import com.cxp.personalmanage.utils.JackJsonUtil;

@Aspect
@Component
public class ApiRequestAOP {

	private static final Logger logger = LoggerFactory.getLogger(ApiRequestAOP.class);

	@Pointcut(value = "execution(* com.cxp.personalmanage.apicontroller..*.*(..))")
	public void apiPoint() {}

	/**
	 * JoinPoint : java.lang.Object[] getArgs()：获取连接点方法运行时的入参列表；  Signature
	 * getSignature() ：获取连接点的方法签名对象；  java.lang.Object getTarget() ：获取连接点所在的目标对象； 
	 * java.lang.Object getThis() ：获取代理对象本身；
	 * 
	 * @param joinPoint
	 * @return
	 */
	@Around(value = "apiPoint()")
	public Object checkReqeustParam(ProceedingJoinPoint joinPoint) {
		logger.info("拦截请求方法为: " + joinPoint.getSignature().getName());
		Object result = null;
		try {
			Object[] params = joinPoint.getArgs();
			if (params != null && params.length > 0) {
				CommonRequestDTO requestDto = (CommonRequestDTO) params[1];
				
				if (null == requestDto) {
					CommonResponseDTO response = new CommonResponseDTO();
					response.setResponseCode("life-99999");
					response.setResponseMsg("请求数据不能为空");
					return response;
				} else if (null != requestDto && null == requestDto.getRequestUser()) {
					logger.info("aop拦截的请求参数为:"+requestDto.toString());
					CommonResponseDTO response = new CommonResponseDTO();
					response.setResponseCode("life-99999");
					response.setResponseMsg("请求用户不能为空!");
					return response;
				}
			}
			result = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
}
