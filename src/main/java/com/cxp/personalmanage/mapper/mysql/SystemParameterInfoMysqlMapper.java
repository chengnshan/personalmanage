package com.cxp.personalmanage.mapper.mysql;

import java.util.List;
import java.util.Map;

import com.cxp.personalmanage.pojo.SystemParameterInfo;

public interface SystemParameterInfoMysqlMapper {

	/**
	 * 根据Code获取对象
	 * @param paramCode
	 * @return
	 */
	public List<SystemParameterInfo> getParameterInfoByCode(Map<String,Object> param);
	
	public SystemParameterInfo getByCode(String code);
}
