package com.cxp.personalmanage.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@TableName(value = "userinfo", resultMap = "baseResultMap")
public class UserInfo extends BaseEntityInfo implements Serializable {
	private static final long serialVersionUID = -5890268832873153185L;

	//主键,且属于自增型
	@TableId(type = IdType.AUTO)
	@TableField
	private Integer id;

	@TableField(value = "username")
	private String userName;// 用户名

	@TableField(value = "password")
	private String password;// 密码

	@TableField(value = "salt")
	private String salt;// 盐

	@TableField(value = "realName")
	private String realName;

	@TableField(value = "tel_phone")
	private String telPhone;

	@TableField(value = "image_url")
	private String image_url; // 图片路径

	@TableField(exist = false)
	private List<RoleInfo> roleList = new ArrayList<RoleInfo>();

	@TableField(value = "enable")
	private Integer enable = 1;

	@TableField(exist = false)
	private String[] roleIds;

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<RoleInfo> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleInfo> roleList) {
		this.roleList = roleList;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", userName=" + userName + ", password=" + password + ", salt=" + salt
				+ ", realName=" + realName + ", roleList=" + roleList + ", create_time=" + create_time
				+ ", create_user=" + create_user + ", update_time=" + update_time + ", update_user=" + update_user
				+ "]";
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
}
