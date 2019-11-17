package com.cxp.personalmanage.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "user_role_info", resultMap = "baseResultMap")
public class UserRoleInfo extends BaseEntityInfo implements Serializable{
    
	private static final long serialVersionUID = -6495861512374197391L;

    //主键,且属于自增型
    @TableId(type = IdType.AUTO)
    @TableField
	private int id;

    @TableField(value = "roleid")
    private String roleId;

    @TableField(value = "username")
    private String userName;

    @TableField
    private int enable;

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserRoleInfo{" +
                "id=" + id +
                ", roleId='" + roleId + '\'' +
                ", userName='" + userName + '\'' +
                ", create_time=" + create_time +
                ", create_user='" + create_user + '\'' +
                ", update_time=" + update_time +
                ", update_user='" + update_user + '\'' +
                '}';
    }
}
