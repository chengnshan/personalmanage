package com.cxp.personalmanage.service;

import com.cxp.personalmanage.pojo.MenuInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public interface MenuInfoService {

	/**
	 * 获取菜单列表
	 * @param menuInfo
	 * @return
	 */
    public List<MenuInfo> findMenuInfoList(MenuInfo menuInfo);

    /**
      *根据角色Id查询所拥有的菜单
     */
    public List<MenuInfo> findMenuListByRoleId(Map<String,Object> param);
    
    /**
     * 添加菜单
     * @param menuInfo
     * @return
     */
    public int insertMenuInfo(MenuInfo menuInfo,HttpServletRequest request) throws Exception;
    
    /**
     * 更新菜单
     * @param menuInfo
     * @return
     * @throws Exception
     */
    public int updateMenuInfo(MenuInfo menuInfo,HttpServletRequest request) throws Exception;
}
