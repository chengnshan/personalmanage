package com.cxp.personalmanage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cxp.personalmanage.pojo.RoleMenuInfo;

public interface RoleMenuInfoService {
	/**
	 * 查询角色菜单信息 
	 * @param roleMenuInfo
	 * @return
	 */
	public List<RoleMenuInfo> findRoleMenuInfo(RoleMenuInfo roleMenuInfo);
	
	/**
	 * 保存角色菜单信息
	 * @param roleMenuInfo
	 * @return
	 */
	public String saveRoleMenuInfo(HttpServletRequest request,String roleId,String menuId);
	
	/**
	 * 批量添加角色菜单信息
	 * @param list
	 * @return
	 */
	public int batchRoleMenuInfo(List<RoleMenuInfo> list);
	
	/**
	 * 更新角色信息
	 * @param roleMenuInfo
	 * @return
	 */
	public int updateRoleMenuInfo(RoleMenuInfo roleMenuInfo);
	
	/**
	 * 删除角色菜单信息
	 * @param roleMenuInfo
	 * @return
	 */
	public int deleteMenuInfo(RoleMenuInfo roleMenuInfo);
}
