package com.cxp.personalmanage.controller;

import com.cxp.personalmanage.pojo.RoleInfo;
import com.cxp.personalmanage.service.RoleInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleInfoService roleInfoService;

    @RequestMapping(value = "/findRoleList",method = RequestMethod.POST)
    public List<RoleInfo> findRoleList(){
        List<RoleInfo> roleList = roleInfoService.findRoleList(null);
        return roleList;
    }
}
