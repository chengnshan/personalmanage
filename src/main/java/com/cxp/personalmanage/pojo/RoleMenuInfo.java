package com.cxp.personalmanage.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "role_menu_info", resultMap = "baseResultMap")
public class RoleMenuInfo extends BaseEntityInfo implements Serializable {

	private static final long serialVersionUID = -6593321694359467857L;

	@TableId(value = "id", type = IdType.AUTO)
	@TableField
	private Integer id;

	@TableField
	private String roleId;

	@TableField
	private String menuId;

	@TableField
	private Integer enable = 1;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "RoleMenuInfo [id=" + id + ", roleId=" + roleId + ", menuId=" + menuId + ", enable=" + enable + "]";
	}

}
