package com.cxp.personalmanage.pojo.dto;

import lombok.Data;

/**
 * @author : cheng
 * @date : 2019-11-08 19:59
 */
@Data
public class RedisKeyValueDto {
    private String dataType;
    private String key;
    private String value;
    private Long expire;
}
