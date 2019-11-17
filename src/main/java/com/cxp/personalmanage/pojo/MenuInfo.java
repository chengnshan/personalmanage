package com.cxp.personalmanage.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

@TableName(value = "menu_info", resultMap = "baseResultMap")
public class MenuInfo extends BaseEntityInfo implements Serializable{

	private static final long serialVersionUID = 8684119698965611167L;

	@TableId(value = "id", type = IdType.AUTO)
	@TableField
	private Integer id;

	@TableField(value = "menuid")
	private String menuId;

	@TableField(value = "menuName")
	private String menuName;

	@TableField(value = "menuUrl")
	private String menuUrl;

	@TableField(value = "remark")
	private String remark;

	@TableField(value = "classStyle")
	private String classStyle;

	@TableField(value = "sort")
	private Integer sort;

	@TableField(value = "parent_menuid")
	private String parentMenuId;

	@TableField(exist = false)
	private List<MenuInfo> childrenMenus;

	@TableField(value = "enable")
	private int enable =1;

	@TableField(value = "menu_level")
	private Integer menuLevel;

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public String getClassStyle() {
		return classStyle;
	}

	public void setClassStyle(String classStyle) {
		this.classStyle = classStyle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<MenuInfo> getChildrenMenus() {
		return childrenMenus;
	}

	public void setChildrenMenus(List<MenuInfo> childrenMenus) {
		this.childrenMenus = childrenMenus;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	@Override
	public String toString() {
		return "MenuInfo{" + "menuId='" + menuId + '\'' + ", menuName='" + menuName + '\'' + ", menuUrl='" + menuUrl
				+ '\'' + ", remark='" + remark + '\'' + ", sort=" + sort + '}';
	}
}
