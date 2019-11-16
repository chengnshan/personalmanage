package com.cxp.personalmanage.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.personalmanage.controller.base.BaseController;
import com.cxp.personalmanage.pojo.RoleMenuInfo;
import com.cxp.personalmanage.service.RoleMenuInfoService;
import com.cxp.personalmanage.utils.JackJsonUtil;

@RestController
@RequestMapping(value = "/roleMenu")
public class RoleMenuController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(RoleMenuController.class);

	@Autowired
	private RoleMenuInfoService roleMenuInfoService;

	/**
	 * 查询角色菜单信息
	 * @param request
	 * @param roleMenuInfo
	 * @return
	 */
	@RequestMapping(value = "/findRoleMenuInfoList")
	public String findRoleMenuInfoList(HttpServletRequest request, RoleMenuInfo roleMenuInfo) {
		List<RoleMenuInfo> findRoleMenuInfo = roleMenuInfoService.findRoleMenuInfo(roleMenuInfo);
		return buildSuccessResultInfo(findRoleMenuInfo);
	}
	
	/**
	 * 删除角色菜单信息
	 * @param request
	 * @param roleMenuInfo
	 * @return
	 */
	@RequestMapping(value = "/deleteRoleMenuInfo")
	public String deleteRoleMenuInfo(HttpServletRequest request, RoleMenuInfo roleMenuInfo) {
		int num = roleMenuInfoService.deleteMenuInfo(roleMenuInfo);
		return buildSuccessResultInfo(num);
	}
	
	/**
	 * 更新角色菜单信息
	 * @param request
	 * @param roleMenuInfo
	 * @return
	 */
	@RequestMapping(value = "/updateRoleMenuInfo")
	public String updateRoleMenuInfo(HttpServletRequest request, RoleMenuInfo roleMenuInfo) {
		int num = roleMenuInfoService.updateRoleMenuInfo(roleMenuInfo);
		return buildSuccessResultInfo(num);
	}
	
	/**
	 * 添加角色菜单信息
	 * @param request
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/saveRoleMenuInfo")
	public String saveRoleMenuInfo(HttpServletRequest request,String roleId,String menuId) {
		logger.info("saveRoleMenuInfo入参 : roleId ="+ roleId +" , menuId = "+menuId);
		if(StringUtils.isBlank(roleId)) {
			return buildFailedResultInfo(-1, "角色不能为空!");
		}
		try {
			roleMenuInfoService.saveRoleMenuInfo(request, roleId, menuId);
		} catch (Exception e) {
			logger.info("服务器异常,更新权限失败"+e.getMessage());
			return buildFailedResultInfo(-1, "保存角色菜单信息失败!");
		}
		
		return buildSuccessResultInfo("保存角色菜单信息成功!");
	}
}
