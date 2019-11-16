package com.cxp.personalmanage.mapper.postgresql;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import org.apache.ibatis.annotations.Mapper;

public interface SystemParameterInfoMapper extends BaseMapper<SystemParameterInfo> {

	/**
	 * 根据Code获取对象
	 * @param param
	 * @return
	 */
	public List<SystemParameterInfo> getParameterInfoByCode(Map<String,Object> param);
	
	public SystemParameterInfo getByCode(String code);

	/**
	 * 根据属性查询
	 * @param systemParameterInfo
	 * @return
	 */
	public List<SystemParameterInfo> listByProperty(SystemParameterInfo systemParameterInfo);
}
