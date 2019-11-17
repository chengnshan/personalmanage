package com.cxp.personalmanage.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "roleinfo", resultMap = "resultMap")
public class RoleInfo extends BaseEntityInfo implements Serializable{

	private static final long serialVersionUID = 3206208596229553207L;

	@TableId(value = "id", type = IdType.AUTO)
	@TableField
	private Integer id;

	@TableField(value = "roleId")
	private String roleId;

	@TableField(value = "roleName")
	private String roleName;

	@TableField(value = "description")
	private String description;

	@TableField(value = "enable")
	private int enable;

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "RoleInfo [id=" + id + ", roleId=" + roleId + ", roleName=" + roleName + ", description=" + description
				+ ", create_time=" + create_time + ", create_user=" + create_user + ", update_time=" + update_time
				+ ", update_user=" + update_user + "]";
	}

}
