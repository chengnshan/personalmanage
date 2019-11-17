package com.cxp.personalmanage.pojo.consumer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "consume_type_info", resultMap = "baseResultMap")
public class ConsumeTypeInfo implements Serializable {

	private static final long serialVersionUID = 8049806183277682943L;

	@TableId(value = "id", type = IdType.AUTO)
	@TableField
	private Integer id;

	@TableField(value = "consume_id")
	private String consumeId;

	@TableField(value = "consume_name")
	private String consumeName;

	@TableField(value = "remark")
	private String remark;

	@TableField(value = "import_no")
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
