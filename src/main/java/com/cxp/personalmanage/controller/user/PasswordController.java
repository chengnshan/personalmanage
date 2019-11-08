package com.cxp.personalmanage.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : cheng
 * @date : 2019-11-07 22:20
 */
@Controller
public class PasswordController {

    @RequestMapping(value = "/pwd/forget_password")
    public String forgetPwd(){
        return "403";
    }
}
