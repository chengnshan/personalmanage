package com.cxp.personalmanage.service;

import java.util.List;
import java.util.Map;

import com.cxp.personalmanage.pojo.SystemParameterInfo;

public interface SystemParameterInfoService {

	/**
	 * 根据Code获取对象
	 * @param param
	 * @return
	 */
	public List<SystemParameterInfo> getParameterInfoByCode(Map<String,Object> param);

	/**
	 * 根据code获取
	 * @param code
	 * @return
	 */
	public SystemParameterInfo getByCode(String code);

	public List<SystemParameterInfo> listByProperty(SystemParameterInfo systemParameterInfo);
}
