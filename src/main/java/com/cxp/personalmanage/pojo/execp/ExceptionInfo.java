package com.cxp.personalmanage.pojo.execp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : cheng
 * @date : 2019-11-22 10:14
 */
@Data
@ToString
@TableName(value = "exception_info", resultMap = "baseResultMap")
public class ExceptionInfo implements Serializable {

    private static final long serialVersionUID = 8948735743040933445L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField
    private Long id;
    private String exceptionPosition;
    private Date createTime;
    private String exceptionName;
    private String exceptionDetail;
}
