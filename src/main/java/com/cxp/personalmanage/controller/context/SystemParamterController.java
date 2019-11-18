package com.cxp.personalmanage.controller.context;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.controller.base.BaseController;
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import com.cxp.personalmanage.service.SystemParameterInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : cheng
 * @date : 2019-11-17 09:03
 */
@RestController
@RequestMapping(value = "/systemParamter")
public class SystemParamterController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SystemParamterController.class);

    @Autowired
    private SystemParameterInfoService systemParameterInfoService;

    /**
     * 查询列表
     * @param request
     * @param systemParameterInfo
     * @return
     */
    @RequestMapping(value = "/listSystemParam")
    public String listSystemParam(HttpServletRequest request, SystemParameterInfo systemParameterInfo,
                                  @RequestParam(defaultValue = "1", required = false) Integer currentPage){
        IPage<SystemParameterInfo> infoIPage = null;

        QueryWrapper<SystemParameterInfo> wrapper = null;
        Page<SystemParameterInfo> page = new Page<>(currentPage,10);
        wrapper= new QueryWrapper<>();
        if (StringUtils.isNotBlank(systemParameterInfo.getParam_code())){
            wrapper.isNotNull("param_code").like("param_code", systemParameterInfo.getParam_code());
        }
        if (StringUtils.isNotBlank(systemParameterInfo.getParam_name())){
            wrapper.isNotNull("param_name").like("param_name",systemParameterInfo.getParam_name());
        }

        infoIPage = systemParameterInfoService.page(page, wrapper);
        return buildSuccessResultInfo(Long.valueOf(infoIPage.getTotal()).intValue(), infoIPage.getRecords());
    }

    @RequestMapping(value = "/updateSystemParam")
    public String updateSystemParam(HttpServletRequest request, SystemParameterInfo systemParameterInfo){
        System.out.println(systemParameterInfo);
        boolean b = systemParameterInfoService.updateById(systemParameterInfo);
        if (b){
            refreshMemory();
            return buildSuccessResultInfo(null);
        }else {
            return buildFailedResultInfo(-1, "更新失败!");
        }
    }

    @RequestMapping(value = "/getSystemParamById")
    public String getSystemParamById(HttpServletRequest request, Integer id){
        if (id == null){
            return buildFailedResultInfo(-1, "id不能为空!");
        }
        SystemParameterInfo info = systemParameterInfoService.getById(id);
        return buildSuccessResultInfo(info);
    }

    @RequestMapping(value = "/deleteSystemParamById")
    public String deleteSystemParamById(HttpServletRequest request, Integer id){
        if (id == null){
            return buildFailedResultInfo(-1, "id不能为空!");
        }
        boolean b = systemParameterInfoService.removeById(id);
        if (b){
            refreshMemory();
            return buildSuccessResultInfo(null);
        }else {
            return buildFailedResultInfo(-1, "删除失败!");
        }
    }

    @RequestMapping(value = "/saveSystemParam")
    public String saveSystemParam(HttpServletRequest request, SystemParameterInfo systemParameterInfo){
        try{

            boolean save = systemParameterInfoService.save(systemParameterInfo);
            if (save){
                refreshMemory();
                return buildSuccessResultInfo(null);
            }
            return buildFailedResultInfo(-1,"添加失败");
        }catch (Exception e){
            logger.error("saveSystemParam Exception : "+e.getMessage(), e);
            return buildFailedResultInfo(-1, "后台开小差了,添加失败!");
        }
    }

    @Autowired
    private InitMemoryConfig initMemoryConfig;

    private void refreshMemory(){
        initMemoryConfig.init();
    }
}
