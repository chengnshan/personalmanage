package com.cxp.personalmanage.pojo.consumer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.pojo.BaseEntityInfo;
import com.cxp.personalmanage.utils.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

@TableName(value = "consume_detail_info", resultMap = "baseResultMap")
public class ConsumeDetailInfo extends BaseEntityInfo implements Serializable {

	private static final long serialVersionUID = 8650652903061206863L;

	private Integer id;
	private String userName;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date consume_time;
	private BigDecimal consume_money;
	private String consumeId;
	private String remark;
	private String channel_code;

	private ConsumeTypeInfo consumeTypeInfo;
	private ConsumeChannelInfo consumeChannelInfo;

	private String consumeName;
	private String weekDay;

	public String getWeekDay() {
		return DateTimeUtil.getWeek(this.consume_time);
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getConsume_time() {
		return consume_time;
	}

	public void setConsume_time(Date consume_time) {
		this.consume_time = consume_time;
	}

	public BigDecimal getConsume_money() {
		return consume_money;
	}

	public void setConsume_money(BigDecimal consume_money) {
		this.consume_money = consume_money;
	}

	public String getConsumeId() {
		return consumeId;
	}

	public void setConsumeId(String consumeId) {
		this.consumeId = consumeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ConsumeTypeInfo getConsumeTypeInfo() {
		return consumeTypeInfo;
	}

	public void setConsumeTypeInfo(ConsumeTypeInfo consumeTypeInfo) {
		this.consumeTypeInfo = consumeTypeInfo;
	}

	public ConsumeChannelInfo getConsumeChannelInfo() {
		return consumeChannelInfo;
	}

	public void setConsumeChannelInfo(ConsumeChannelInfo consumeChannelInfo) {
		this.consumeChannelInfo = consumeChannelInfo;
	}

	public String getChannel_code() {
		return channel_code;
	}

	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}

	@SuppressWarnings("unchecked")
	public String getConsumeName() {
		List<ConsumeTypeInfo> consumeTypeList = null;
		Map<String, Object> initMap = InitMemoryConfig.initMap;
		if (MapUtils.isNotEmpty(initMap)) {
			consumeTypeList = (List<ConsumeTypeInfo>) initMap.get(Constant.InitKey.CONSUMER_TYPE_LIST);
		}
		if (CollectionUtils.isNotEmpty(consumeTypeList)) {
			for (ConsumeTypeInfo consumeTypeInfo : consumeTypeList) {
				if (this.consumeId.equals(consumeTypeInfo.getConsumeId())) {
					return consumeTypeInfo.getConsumeName();
				}
			}
		}

		return null;
	}

	public void setConsumeName(String consumeName) {
		this.consumeName = consumeName;
	}

	@Override
	public String toString() {
		return "ConsumeDetailInfo{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", consume_time=" + consume_time +
				", consume_money=" + consume_money +
				", consumeId='" + consumeId + '\'' +
				", remark='" + remark + '\'' +
				", consumeTypeInfo=" + consumeTypeInfo +
				", consumeChannelInfo=" + consumeChannelInfo +
				", consumeName='" + consumeName + '\'' +
				", weekDay='" + weekDay + '\'' +
				", create_time=" + create_time +
				", create_user='" + create_user + '\'' +
				", update_time=" + update_time +
				", update_user='" + update_user + '\'' +
				'}';
	}
}
