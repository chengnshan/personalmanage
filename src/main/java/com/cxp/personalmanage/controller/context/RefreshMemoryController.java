package com.cxp.personalmanage.controller.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.controller.base.BaseController;

@Controller
@RequestMapping(value="/refresh")
public class RefreshMemoryController extends BaseController{
	
	@Autowired
	private InitMemoryConfig initMemoryConfig;

	@RequestMapping(value="/refreshContext")
	@ResponseBody
	public String refreshContext() {
		initMemoryConfig.init();
		return buildSuccessResultInfo("重置成功!");
	}
}
