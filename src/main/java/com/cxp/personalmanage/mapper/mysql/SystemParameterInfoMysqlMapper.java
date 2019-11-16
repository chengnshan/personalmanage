package com.cxp.personalmanage.mapper.mysql;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxp.personalmanage.pojo.SystemParameterInfo;

public interface SystemParameterInfoMysqlMapper extends BaseMapper<SystemParameterInfo> {

	/**
	 * 根据Code获取对象
	 * @param param
	 * @return
	 */
	public List<SystemParameterInfo> getParameterInfoByCode(Map<String,Object> param);
	
	public SystemParameterInfo getByCode(String code);
}
