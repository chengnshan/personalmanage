package com.cxp.personalmanage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxp.personalmanage.mapper.postgresql.SystemParameterInfoMapper;
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import com.cxp.personalmanage.service.SystemParameterInfoService;

@Service(value = "systemParameterInfoService")
public class SystemParameterInfoServiceImpl implements SystemParameterInfoService {

	@Autowired
	private SystemParameterInfoMapper systemParameterInfoMapper;

	@Override
	public List<SystemParameterInfo> getParameterInfoByCode(Map<String, Object> param) {

		return systemParameterInfoMapper.getParameterInfoByCode(param);
	}

	@Override
	public SystemParameterInfo getByCode(String code) {
		// TODO Auto-generated method stub
		return systemParameterInfoMapper.getByCode(code);
	}

	@Override
	public List<SystemParameterInfo> listByProperty(SystemParameterInfo systemParameterInfo) {
		return systemParameterInfoMapper.listByProperty(systemParameterInfo);
	}
}
