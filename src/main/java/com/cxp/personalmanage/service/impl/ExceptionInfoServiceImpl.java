package com.cxp.personalmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxp.personalmanage.mapper.postgresql.ExceptionInfoMapper;
import com.cxp.personalmanage.pojo.execp.ExceptionInfo;
import com.cxp.personalmanage.service.ExceptionInfoService;
import org.springframework.stereotype.Service;

/**
 * @author : cheng
 * @date : 2019-11-22 10:20
 */
@Service(value = "exceptionInfoService")
public class ExceptionInfoServiceImpl extends ServiceImpl<ExceptionInfoMapper, ExceptionInfo>
        implements ExceptionInfoService {

}
