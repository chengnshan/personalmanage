package com.cxp.personalmanage.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.cxp.personalmanage.utils.CommonUtil;
import com.cxp.personalmanage.utils.RedisUtils;

public class CustomLogout extends LogoutFilter {

	 private static final Logger logger = LoggerFactory.getLogger(CustomLogout.class);
	 
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		//在这里执行退出系统前需要清空的数据
		logger.info("自定义退出登录开始...");
		Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        try {
            subject.logout();
            RedisUtils.remove(CommonUtil.getIpAddr((HttpServletRequest)request));
        } catch (SessionException ise) {
        	logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        issueRedirect(request, response, redirectUrl);
        return false;
	}
}
