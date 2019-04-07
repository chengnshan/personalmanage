package com.cxp.personalmanage.pojo.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumeChannelInfo implements Serializable {

    private static final long serialVersionUID = 8497031604047864584L;

    private Integer id;
    private String channel_code;
    private String channel_name;
    private String channel_remark;

}
