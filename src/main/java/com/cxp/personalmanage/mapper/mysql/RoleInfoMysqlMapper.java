package com.cxp.personalmanage.mapper.mysql;

import java.util.List;

import com.cxp.personalmanage.pojo.RoleInfo;
import com.cxp.personalmanage.pojo.UserInfo;

public interface RoleInfoMysqlMapper {

	public List<RoleInfo> findUserRoleInfoList(String userName);

	/**
	 * 查询角色信息
	 * @param roleInfo
	 * @return
	 */
	public List<RoleInfo> findRoleList(RoleInfo roleInfo);
}
