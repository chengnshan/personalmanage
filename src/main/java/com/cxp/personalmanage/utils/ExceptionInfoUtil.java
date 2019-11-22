package com.cxp.personalmanage.utils;

import com.cxp.personalmanage.pojo.execp.ExceptionInfo;
import com.cxp.personalmanage.service.ExceptionInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;

/**
 * @author : cheng
 * @date : 2019-11-22 09:50
 */
@Component
public class ExceptionInfoUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionInfoUtil.class);

    private static ExceptionInfoService exceptionInfoService;

    @Autowired
    public void setExceptionInfoService(ExceptionInfoService exceptionInfoService) {
        ExceptionInfoUtil.exceptionInfoService = exceptionInfoService;
    }

    public static void saveExceptionInfo(String position, String name, Exception ex) {
        ExceptionInfo info = new ExceptionInfo();
        info.setCreateTime(new Date());
        info.setExceptionPosition(position);
        info.setExceptionDetail(getExceptionAllInfo(ex));
        info.setExceptionName(name);

        try {
            exceptionInfoService.save(info);
        } catch (Exception e) {
            logger.error("saveExceptionInfo Exception : " + e.getMessage(), e);
        }
    }

    public static String getExceptionAllInfo(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            ex.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e) {
            return ex.getMessage();
        } finally {
            try {
                sw.close();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String sql = "INSERT INTO exception_info  ( exception_poition,\n" +
                "create_time,\n" +
                "exception_name,\n" +
                "exception_detail )  VALUES  ( 'saveConsumeDetailInfo',\n" +
                "'2019-11-22 11:12:26',\n" +
                "'/ by zero',\n" +
                "? )";

        String detail = "'java.lang.ArithmeticException: / by zero\n" +
                "\tat com.cxp.personalmanage.controller.ConsumeDetailInfoController.saveConsumeDetailInfo(ConsumeDetailInfoController.java:160)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "\tat java.lang.reflect.Method.invoke(Method.java:498)\n" +
                "\tat org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n" +
                "\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:133)\n" +
                "\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:97)\n" +
                "\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:827)\n" +
                "\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:738)\n" +
                "\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:85)\n" +
                "\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:967)\n" +
                "\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:901)\n" +
                "\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:970)\n" +
                "\tat org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:872)\n" +
                "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:661)\n" +
                "\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:846)\n" +
                "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:742)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat org.springframework.boot.web.filter.ApplicationContextHeaderFilter.doFilterInternal(ApplicationContextHeaderFilter.java:55)\n" +
                "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)\n" +
                "\tat org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\n" +
                "\tat org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\n" +
                "\tat org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n" +
                "\tat org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\n" +
                "\tat org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\n" +
                "\tat org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\n" +
                "\tat org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n" +
                "\tat org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\n" +
                "\tat org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:449)\n" +
                "\tat org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:365)\n" +
                "\tat org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)\n" +
                "\tat org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)\n" +
                "\tat org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:383)\n" +
                "\tat org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)\n" +
                "\tat org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat com.alibaba.druid.support.http.WebStatFilter.doFilter(WebStatFilter.java:123)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat org.springframework.boot.actuate.trace.WebRequestTraceFilter.doFilterInternal(WebRequestTraceFilter.java:111)\n" +
                "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)\n" +
                "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:109)\n" +
                "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93)\n" +
                "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:197)\n" +
                "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat org.springframework.boot.actuate.autoconfigure.MetricsFilter.doFilterInternal(MetricsFilter.java:106)\n" +
                "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n" +
                "\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)\n" +
                "\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\n" +
                "\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:496)\n" +
                "\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)\n" +
                "\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)\n" +
                "\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)\n" +
                "\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342)\n" +
                "\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:803)\n" +
                "\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n" +
                "\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:790)\n" +
                "\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1468)\n" +
                "\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n" +
                "\tat java.lang.Thread.run(Thread.java:748)\n" +
                "'";
        System.out.println(sql.indexOf("?"));
        String first = sql.replaceFirst("\\?", detail);
        System.out.println(first);
    }
}
