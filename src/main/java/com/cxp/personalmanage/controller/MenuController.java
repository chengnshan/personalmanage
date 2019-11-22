package com.cxp.personalmanage.controller;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.cxp.personalmanage.utils.ExceptionInfoUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.controller.base.BaseController;
import com.cxp.personalmanage.pojo.MenuInfo;
import com.cxp.personalmanage.service.MenuInfoService;
import com.cxp.personalmanage.utils.CommonUtil;

@RestController
@RequestMapping(value = "/menu")
public class MenuController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    
    @Autowired
	@Qualifier(value = "stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MenuInfoService menuInfoService;

    /**
     * 获取菜单列表
     * @param menuInfo
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/findMenuList",method = RequestMethod.POST)
    public String findMenuList(MenuInfo menuInfo,String queryOneLevel){

    	List<MenuInfo> menuInfoList = null;
    	Set<MenuInfo> menuInfoSet = new LinkedHashSet<>();
    	//如何是查询1级菜单
		if (StringUtils.isNotBlank(queryOneLevel)) {
			Map<String, Object> initMap = InitMemoryConfig.initMap;
			if (MapUtils.isNotEmpty(initMap) && initMap.containsKey(Constant.InitKey.MENU_INFO_LIST)) {
				menuInfoList = (List<MenuInfo>) initMap.get(Constant.InitKey.MENU_INFO_LIST);
				if (CollectionUtils.isNotEmpty(menuInfoList)) {
					for (MenuInfo menuInfo2 : menuInfoList) {
						if (menuInfo2.getMenuLevel().equals(Constant.ONE)) {
							menuInfoSet.add(menuInfo2);
						}
					}
				}

			} else {
				menuInfoList = menuInfoService.findMenuInfoList(menuInfo);
				menuInfoSet.addAll(menuInfoList);
			}
			return buildSuccessResultInfo(menuInfoSet);
		}else {
    		menuInfoList = menuInfoService.findMenuInfoList(menuInfo);
    		return buildSuccessResultInfo(menuInfoList);
    	}
        
    }
    
    /**
     * 根据菜单Id获取菜单信息
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/getMenuInfoByMenuId",method = RequestMethod.POST)
    public String getMenuInfoByMenuId(String menuId){
    	MenuInfo menuInfo = new MenuInfo();
    	menuInfo.setMenuId(menuId);
    	List<MenuInfo> menuInfo2 = menuInfoService.findMenuInfoList(menuInfo);
    	return buildSuccessResultInfo(menuInfo2.get(0));
    }
    
    /**
     *添加菜单
     * @param menuInfo
     * @param request
     * @return
     */
    @RequestMapping(value="/insertMenuInfo")
    public String insertMenuInfo(MenuInfo menuInfo,HttpServletRequest request) {
    	String returnStr = "";
    	logger.info("insertMenuInfo 入参: menuInfo : "+menuInfo);
    	if(menuInfo!=null && StringUtils.isNotBlank(menuInfo.getMenuId()) && StringUtils.isNotBlank(menuInfo.getMenuUrl())) {
    		try {
    			MenuInfo queryMenu = new MenuInfo();
    			queryMenu.setMenuId(menuInfo.getMenuId());
    			List<MenuInfo> exsitsMenuInfo = menuInfoService.findMenuInfoList(queryMenu);
    			if(CollectionUtils.isEmpty(exsitsMenuInfo)) {
    				menuInfoService.insertMenuInfo(menuInfo,request);
    				returnStr = buildSuccessResultInfo("菜单添加成功!");
    				//清除redis中存储的菜单列表,重新从数据加载
    				stringRedisTemplate.delete(CommonUtil.getIpAddr(request)+"-"+Constant.LOGIN_MENU_INFO);
				}else {
    				returnStr = buildFailedResultInfo(-1, "此菜单已经存在,不能重复添加!");
    			}
			} catch (Exception e) {
				logger.error("insertMenuInfo添加菜单出错 : "+e.getMessage(), e);
				ExceptionInfoUtil.saveExceptionInfo("insertMenuInfo",e.getMessage(), e);
				returnStr = buildFailedResultInfo(-1, "服务器异常,添加失败!");
			}
    	}
    	return returnStr;
    }
    
    /**
     * 更新菜单信息
     * @param menuInfo
     * @param request
     * @return
     */
    @RequestMapping(value="/updateMenuInfo")
    public String updateMenuInfo(MenuInfo menuInfo,HttpServletRequest request) {
    	if(null != menuInfo && null != menuInfo.getMenuId()) {
    		try {
				menuInfoService.updateMenuInfo(menuInfo,request);
				return buildSuccessResultInfo("更新成功!");
			} catch (Exception e) {
				logger.error("updateMenuInfo更新菜单出错 : "+e.getMessage());
				return buildFailedResultInfo(-1, "服务器异常,更新菜单失败!");
			}
    	}
    	return buildFailedResultInfo(-1, "更新菜单失败,菜单数据不完整!");
    }
}
