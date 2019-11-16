package com.cxp.personalmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.mapper.postgresql.MenuInfoMapper;
import com.cxp.personalmanage.pojo.MenuInfo;
import com.cxp.personalmanage.pojo.UserInfo;
import com.cxp.personalmanage.service.MenuInfoService;
import com.cxp.personalmanage.utils.CommonUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

@Service(value = "menuInfoService")
public class MenuInfoServiceImpl extends ServiceImpl<MenuInfoMapper, MenuInfo> implements MenuInfoService {

    @Autowired
    private MenuInfoMapper menuInfoMapper;
    
    @Autowired
	@Qualifier(value = "stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<MenuInfo> findMenuInfoList(MenuInfo menuInfo) {
        return menuInfoMapper.findMenuInfoList(menuInfo);
    }

    @Transactional
    @Override
    public List<MenuInfo> findMenuListByRoleId(Map<String,Object> param) {
        return menuInfoMapper.findMenuListByRoleId(param);
    }

    @Transactional(value="txPrimaryManager",rollbackFor= {RuntimeException.class,Exception.class})
	@Override
	public int insertMenuInfo(MenuInfo menuInfo,HttpServletRequest request) throws Exception{
    	String parentId = menuInfo.getParentMenuId();
    	MenuInfo paramMenu=new MenuInfo();
    	paramMenu.setMenuId(parentId);
    	List<MenuInfo> menuInfo2 = findMenuInfoList(paramMenu);
    	if(CollectionUtils.isNotEmpty(menuInfo2)) {
    		Integer paranLevel = menuInfo2.get(0).getMenuLevel();
    		menuInfo.setMenuLevel(++paranLevel);
    	}
    	int num = menuInfoMapper.insertMenuInfo(menuInfo);
    	if(num <= 0) {
    		throw new RuntimeException("添加菜单失败.");
    	}
    	refreshMenuInfo(request);
		return num;
	}

    /**
     * 更新菜单
     */
    @Transactional(rollbackFor= {RuntimeException.class,Exception.class},timeout=10000,propagation=Propagation.REQUIRED)
	@Override
	public int updateMenuInfo(MenuInfo menuInfo,HttpServletRequest request) throws Exception {
    	MenuInfo param = new MenuInfo();
    	param.setMenuId(menuInfo.getMenuId());
    	List<MenuInfo> findMenuInfoList = findMenuInfoList(param);
    	if(CollectionUtils.isEmpty(findMenuInfoList)) {
    		throw new RuntimeException("此菜单不存在无法更新");
    	}
    	int updateMenuInfo = menuInfoMapper.updateMenuInfo(menuInfo);
    	refreshMenuInfo(request);
		return updateMenuInfo;
	}
    
    private void refreshMenuInfo(HttpServletRequest request) {
    	//对缓存菜单进行更新
    	Map<String, Object> initMap = InitMemoryConfig.initMap;
    	if(MapUtils.isNotEmpty(initMap)) {
    		if(initMap.containsKey(Constant.InitKey.MENU_INFO_LIST)) {
    			initMap.remove(Constant.InitKey.MENU_INFO_LIST);
    			/**获取菜单详情*/
    			List<MenuInfo> findMenuInfoList = findMenuInfoList(null);
    			initMap.put(Constant.InitKey.MENU_INFO_LIST, findMenuInfoList);
    		}
    	}
    	
    	//对redis中存储的菜单进行更新
    	// 获取当前登录用户
    	UserInfo userInfo = CommonUtil.getCurrentLoginUser(request);
    	String userMenuKey = CommonUtil.getIpAddr(request) + "-" + Constant.LOGIN_MENU_INFO+"-"+userInfo.getUserName();
    	stringRedisTemplate.delete(userMenuKey);
    }
}
