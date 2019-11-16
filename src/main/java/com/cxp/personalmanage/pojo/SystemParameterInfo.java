package com.cxp.personalmanage.pojo;

import java.io.Serializable;

public class SystemParameterInfo extends BaseEntityInfo implements Serializable{

	private static final long serialVersionUID = -3146624782820248912L;
	
	private Integer id;
	private String param_code;
	private String param_value;
	private String param_name;
	private Integer enable = 1 ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParam_code() {
		return param_code;
	}

	public void setParam_code(String param_code) {
		this.param_code = param_code;
	}

	public String getParam_value() {
		return param_value;
	}

	public void setParam_value(String param_value) {
		this.param_value = param_value;
	}

	public String getParam_name() {
		return param_name;
	}

	public void setParam_name(String param_name) {
		this.param_name = param_name;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "SystemParameterInfo [id=" + id + ", param_code=" + param_code + ", param_value=" + param_value
				+ ", param_name=" + param_name + ", enable=" + enable + "]";
	}

}
