package com.cxp.personalmanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.service.MenuInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.mapper.postgresql.RoleMenuInfoMapper;
import com.cxp.personalmanage.pojo.RoleMenuInfo;
import com.cxp.personalmanage.pojo.UserInfo;
import com.cxp.personalmanage.service.RoleMenuInfoService;
import com.cxp.personalmanage.utils.CommonUtil;
import com.cxp.personalmanage.utils.JackJsonUtil;

@Service(value = "roleMenuInfoService")
public class RoleMenuInfoServiceImpl implements RoleMenuInfoService {

	private static final Logger logger = LoggerFactory.getLogger(RoleMenuInfoServiceImpl.class);

	@Autowired
	private RoleMenuInfoMapper roleMenuInfoMapper;

	@Autowired
	private MenuInfoService menuInfoService;
	
	@Autowired
	@Qualifier(value = "stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public List<RoleMenuInfo> findRoleMenuInfo(RoleMenuInfo roleMenuInfo) {
		// TODO Auto-generated method stub
		return roleMenuInfoMapper.findRoleMenuInfo(roleMenuInfo);
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="txPrimaryManager",rollbackFor = { Exception.class, RuntimeException.class }, propagation = Propagation.REQUIRED)
	@Override
	public String saveRoleMenuInfo(HttpServletRequest request, String roleId, String menuId) {

		List<RoleMenuInfo> savelist = new ArrayList<>();
		List<String> deletelist = new ArrayList<>();
		RoleMenuInfo roleMenuInfo = null;

		List<String> menuIdList = null;
		if (StringUtils.isNotBlank(menuId)) {
			// 获取需要更新这个角色的菜单信息
			menuIdList = JackJsonUtil.jsonToObject(menuId, List.class);
			logger.info("格式化菜单ID的Json字符串: " + menuIdList);
		}

		if (CollectionUtils.isNotEmpty(menuIdList)) {
			// 查询这个角色本来已经有的菜单权限
			RoleMenuInfo paramMenuInfo = new RoleMenuInfo();
			paramMenuInfo.setRoleId(roleId);
			List<RoleMenuInfo> findRoleMenuInfo = findRoleMenuInfo(paramMenuInfo);
			// 如果原来有角色,找出哪些需要添加,哪些需要删除
			if (CollectionUtils.isNotEmpty(findRoleMenuInfo)) {
				List<String> existsMenu = new ArrayList<>(findRoleMenuInfo.size());
				for (RoleMenuInfo roleMenuInfo2 : findRoleMenuInfo) {
					existsMenu.add(roleMenuInfo2.getMenuId());
					if (!menuIdList.contains(roleMenuInfo2.getMenuId())) { // 遍历数据库中的不包含,做删除
						deletelist.add(roleMenuInfo2.getMenuId());
					}
				}

				for (String str : menuIdList) {
					if (!existsMenu.contains(str)) { // 遍历前端过来的菜单列表,不包含,做添加
						roleMenuInfo = new RoleMenuInfo();
						roleMenuInfo.setMenuId(str);
						roleMenuInfo.setRoleId(roleId);
						savelist.add(roleMenuInfo);
					}
				}
			} else {
				// 如果原来没有角色,直接做添加
				for (String str : menuIdList) {
					roleMenuInfo = new RoleMenuInfo();
					roleMenuInfo.setMenuId(str);
					roleMenuInfo.setRoleId(roleId);
					savelist.add(roleMenuInfo);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(savelist)) {
			roleMenuInfoMapper.batchRoleMenuInfo(savelist);
		}
		
		if (CollectionUtils.isNotEmpty(deletelist)) {
			Map<String,Object> map=new HashMap<>();
			map.put("deletelist", deletelist);
			map.put("roleId", roleId);
			roleMenuInfoMapper.batchDelete(map);
		}
		//对redis中存储的菜单进行更新
    	// 获取当前登录用户
    	UserInfo userInfo = CommonUtil.getCurrentLoginUser(request);
    	String userMenuKey = CommonUtil.getIpAddr(request) + "-" + Constant.LOGIN_MENU_INFO+"-"+userInfo.getUserName();
    	stringRedisTemplate.delete(userMenuKey);
		InitMemoryConfig.initMap.put(Constant.InitKey.MENU_INFO_LIST, menuInfoService.findMenuInfoList(null));
    	
		return Constant.SUCC;
	}

	@Transactional(value="txPrimaryManager",rollbackFor = { Exception.class, RuntimeException.class }, propagation = Propagation.REQUIRED)
	@Override
	public int updateRoleMenuInfo(RoleMenuInfo roleMenuInfo) {
		// TODO Auto-generated method stub
		return roleMenuInfoMapper.updateRoleMenuInfo(roleMenuInfo);
	}

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class }, propagation = Propagation.REQUIRED)
	@Override
	public int deleteMenuInfo(RoleMenuInfo roleMenuInfo) {
		// TODO Auto-generated method stub
		return roleMenuInfoMapper.deleteMenuInfo(roleMenuInfo);
	}

	@Override
	public int batchRoleMenuInfo(List<RoleMenuInfo> list) {
		// TODO Auto-generated method stub
		return roleMenuInfoMapper.batchRoleMenuInfo(list);
	}

}
