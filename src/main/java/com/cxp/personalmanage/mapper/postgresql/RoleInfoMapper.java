package com.cxp.personalmanage.mapper.postgresql;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxp.personalmanage.pojo.RoleInfo;

public interface RoleInfoMapper extends BaseMapper<RoleInfo> {

	public List<RoleInfo> findUserRoleInfoList(String userName);

	/**
	 * 查询角色信息
	 * @param roleInfo
	 * @return
	 */
	public List<RoleInfo> findRoleList(RoleInfo roleInfo);
}
