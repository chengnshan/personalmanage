package com.cxp.personalmanage.utils;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.cxp.personalmanage.pojo.UserInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	private static StringRedisTemplate stringRedisTemplate;

	@Autowired
	@Qualifier(value = "stringRedisTemplate")
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		CommonUtil.stringRedisTemplate = stringRedisTemplate;
		logger.info("CommonUtil注入stringRedisTemplate成功.");
	}

	private CommonUtil(){

	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 根据shiro中的主体获取当前登录用户
	 * @return
	 */
	public static UserInfo getCurrentSubject() {
		try {
			// 获取当前的Subject
			Subject currentUser = SecurityUtils.getSubject();
			if( null != currentUser) {
				Object principal = currentUser.getPrincipal();
				UserInfo userInfo = null;
				if (null != principal) {
					userInfo = new UserInfo();
					try {
						BeanUtils.copyProperties(userInfo, principal);
					} catch (BeansException e) {
						e.printStackTrace();
					}
				}
				return userInfo;
			}
		} catch (Exception e) {
			logger.error("根据shiro中的主体获取当前登录用户异常: "+e.getMessage());
		}
		return null;
	}

	public static UserInfo getCurrentLoginUser(HttpServletRequest request) {

		UserInfo userInfo = null;
		try {
			if (request == null){
				request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			}

			String strUserInfo = stringRedisTemplate.opsForValue().get(getIpAddr(request));
			if (org.apache.commons.lang.StringUtils.isNotBlank(strUserInfo)) {
				userInfo = JackJsonUtil.jsonToObject(strUserInfo, UserInfo.class);
			}
		} catch (Exception e) {
			logger.error("获取当前登录用户失败" + e.getMessage());
		}

		return userInfo;
	}
}
