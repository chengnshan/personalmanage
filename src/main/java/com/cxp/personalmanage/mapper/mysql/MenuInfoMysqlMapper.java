package com.cxp.personalmanage.mapper.mysql;

import com.cxp.personalmanage.pojo.MenuInfo;

import java.util.List;
import java.util.Map;

public interface MenuInfoMysqlMapper {

	/**
	 * 获取菜单列表
	 * @param menuInfo
	 * @return
	 */
    public List<MenuInfo> findMenuInfoList(MenuInfo menuInfo);

    /**
    	根据角色Id查询所拥有的菜单
     */
    public List<MenuInfo> findMenuListByRoleId(Map<String,Object> param);

    /**
     * 根据菜单Id获取子菜单
     * @param menuId
     * @return
     */
    public List<MenuInfo> getChildrenMenus(String menuId);
    
    /**
     * 添加菜单
     * @param menuInfo
     * @return
     */
    public int insertMenuInfo(MenuInfo menuInfo);
    
    /**
     * 更新菜单信息
     * @param menuInfo
     * @return
     */
    public int updateMenuInfo(MenuInfo menuInfo);
}
