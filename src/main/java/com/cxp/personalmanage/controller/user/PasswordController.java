package com.cxp.personalmanage.controller.user;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.controller.base.BaseController;
import com.cxp.personalmanage.pojo.UserInfo;
import com.cxp.personalmanage.service.MailService;
import com.cxp.personalmanage.service.UserInfoService;
import com.cxp.personalmanage.utils.ExceptionInfoUtil;
import com.cxp.personalmanage.utils.MD5Util;
import com.cxp.personalmanage.utils.RandomCharUtil;
import com.cxp.personalmanage.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author : cheng
 * @date : 2019-11-07 22:20
 */
@RestController
public class PasswordController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/pwd/forget_password")
    public String forgetPwd(@RequestParam Map<String,Object> map){
        logger.info(map.toString());
        try{
            String userName = StringUtil.conveterStr(String.valueOf(map.get("userName")));
            if (StringUtils.isBlank(userName)){
                return buildFailedResultInfo(-1,"用户名不能为空!");
            }
            String email = StringUtil.conveterStr(String.valueOf(map.get("email")));
            String emailcode = StringUtil.conveterStr(String.valueOf(map.get("emailcode")));
            if (StringUtils.isBlank(emailcode)){
                return buildFailedResultInfo(-1,"邮箱验证码必须填写!");
            }
            Map<String, Object>  existMap = InitMemoryConfig.emailCodeMap.get(email);
            if (MapUtils.isEmpty(existMap)){
                return buildFailedResultInfo(-1,"请发送邮箱验证码后再来修改密码!");
            }
            String mapEmailcode = (String) existMap.get(Constant.EMAIL_VERITY_CODE);
            if ( !emailcode.equalsIgnoreCase(mapEmailcode) ){
                return buildFailedResultInfo(-1,"您输入的邮箱验证码不正确!");
            }
            String password = StringUtil.conveterStr(String.valueOf(map.get("password")));
            String confirmPwd = StringUtil.conveterStr(String.valueOf(map.get("confirmPwd")));
            if (StringUtils.isNotBlank(password) && !password.equals(confirmPwd)){
                return buildFailedResultInfo(-1,"密码和确认密码不匹配,请重新确认!");
            }

            UserInfo userInfo = userInfoService.getUserInfoByUserName(userName);
            if (userInfo== null){
                return buildFailedResultInfo(-1,"用户不存在!");
            }
            String pwd = MD5Util.encryptM5(Constant.MD5, password, Constant.SALT, Constant.HASHITERATIONS);
            userInfo.setPassword(pwd);
            userInfoService.updateUserInfo(userInfo);

            InitMemoryConfig.emailCodeMap.remove(email);

            return buildSuccessResultInfo(null);
        }catch (Exception e){
            logger.error("PasswordController forgetPwd Exception : " + e.getMessage(), e);
            ExceptionInfoUtil.saveExceptionInfo("PasswordController",e.getMessage(), e);
            return buildFailedResultInfo(-1, "服务器休息会.");
        }
    }

    /**
     * 发送邮箱验证码
     * @return
     */
    @RequestMapping(value = "/pwd/sendEmailCode")
    @CrossOrigin
    public String sendEmailCode(String toMail){
        String randomChar = RandomCharUtil.getRandomChar(6);
        try {
            Map<String, Object>  existMap = InitMemoryConfig.emailCodeMap.get(toMail);
            if (MapUtils.isNotEmpty(existMap)){
                long sendTime = (long) existMap.get(Constant.EMAIL_VERITY_TIME);
                if ((System.currentTimeMillis() - sendTime) < Constant.EMAIL_VERITY_TIME_NUM){
                    return buildFailedResultInfo(-1, "5分钟后才能再次获取验证码");
                }
            }
            logger.info("开始发送邮箱验证码: " + randomChar);
            mailService.sendMail(toMail,"我自己的项目啊", String.format("邮箱验证码: %s,很正常的发送啊",randomChar), null,null);

            Map<String,Object> map = new HashMap<>(2);
            map.put(Constant.EMAIL_VERITY_CODE,randomChar);
            map.put(Constant.EMAIL_VERITY_TIME,System.currentTimeMillis());
            InitMemoryConfig.emailCodeMap.put(toMail, map);
            return buildSuccessResultInfo(null);
        } catch (Exception e) {
            logger.error("PasswordController sendEmailCode exception : " + e.getMessage(), e);
            ExceptionInfoUtil.saveExceptionInfo("PasswordController sendEmailCode",e.getMessage(), e);
            return buildFailedResultInfo(-1,"服务器休息了一会!"+e.getMessage());
        }
    }
}
