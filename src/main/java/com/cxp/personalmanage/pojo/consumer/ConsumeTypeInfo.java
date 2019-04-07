package com.cxp.personalmanage.pojo.consumer;

import java.io.Serializable;

public class ConsumeTypeInfo implements Serializable {

	private static final long serialVersionUID = 8049806183277682943L;

	private Integer id;
	private String consumeId;
	private String consumeName;
	private String remark;
	private Integer importNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConsumeId() {
		return consumeId;
	}

	public void setConsumeId(String consumeId) {
		this.consumeId = consumeId;
	}

	public String getConsumeName() {
		return consumeName;
	}

	public void setConsumeName(String consumeName) {
		this.consumeName = consumeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getImportNo() {
		return importNo;
	}

	public void setImportNo(Integer importNo) {
		this.importNo = importNo;
	}

	@Override
	public String toString() {
		return "ConsumeType [id=" + id + ", consumeId=" + consumeId + ", consumeName=" + consumeName + ", remark="
				+ remark + "]";
	}

}
