package com.cxp.personalmanage.mapper.postgresql;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxp.personalmanage.pojo.RoleMenuInfo;

public interface RoleMenuInfoMapper extends BaseMapper<RoleMenuInfo> {

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
	public int saveRoleMenuInfo(RoleMenuInfo roleMenuInfo);
	
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
	
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	public int batchDelete(Map<String,Object> map);
}
