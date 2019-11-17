package com.cxp.personalmanage.pojo.consumer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "consume_channel_info", resultMap = "baseResultMap")
public class ConsumeChannelInfo implements Serializable {

    private static final long serialVersionUID = 8497031604047864584L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField
    private Integer id;

    @TableField(value = "channel_code")
    private String channel_code;

    @TableField(value = "channel_name")
    private String channel_name;

    @TableField(value = "channel_remark")
    private String channel_remark;

}
