package com.cxp.personalmanage.pojo.excel;

import java.math.BigDecimal;
import java.util.Date;

import com.cxp.personalmanage.annotation.ExcelVOAttribute;

public class ExceConsumeDetailInfo {

	@ExcelVOAttribute(name = "用户名", column = "A")
	private String userName; // 用户名
	
	@ExcelVOAttribute(name = "消费时间", column = "B")
	private Date consume_time; // 消费时间
	
	@ExcelVOAttribute(name = "消费类型(1.生活消费2.交通消费3.衣服消费4.房租消费)", column = "C")
	private Integer consumeType; // 消费类型(1.生活消费2.交通消费3.衣服消费4.房租消费)
	
	@ExcelVOAttribute(name = "支出/收入(1.支出2收入)", column = "D")
	private Integer consumeAction; // 支出/收入(1.支出2收入)
	
	@ExcelVOAttribute(name = "消费金额", column = "E")
	private BigDecimal consume_money;// 消费金额
	
	@ExcelVOAttribute(name = "备注", column = "F")
	private String remark; // 备注

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getConsume_time() {
		return consume_time;
	}

	public void setConsume_time(Date consume_time) {
		this.consume_time = consume_time;
	}

	public Integer getConsumeType() {
		return consumeType;
	}

	public void setConsumeType(Integer consumeType) {
		this.consumeType = consumeType;
	}

	public Integer getConsumeAction() {
		return consumeAction;
	}

	public void setConsumeAction(Integer consumeAction) {
		this.consumeAction = consumeAction;
	}

	public BigDecimal getConsume_money() {
		return consume_money;
	}

	public void setConsume_money(BigDecimal consume_money) {
		this.consume_money = consume_money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
