package com.cxp.personalmanage.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cxp.personalmanage.pojo.consumer.ConsumeChannelInfo;
import com.cxp.personalmanage.service.ConsumeChannelInfoService;
import com.cxp.personalmanage.service.UserInfoService;
import com.cxp.personalmanage.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.controller.base.BaseController;
import com.cxp.personalmanage.pojo.RoleInfo;
import com.cxp.personalmanage.pojo.UserInfo;
import com.cxp.personalmanage.pojo.consumer.ConsumeDetailInfo;
import com.cxp.personalmanage.pojo.consumer.ConsumeTypeInfo;
import com.cxp.personalmanage.pojo.excel.ExceConsumeDetailInfo;
import com.cxp.personalmanage.service.ConsumeDetailInfoService;
import com.cxp.personalmanage.service.ConsumeTypeInfoService;

@RestController
@RequestMapping(value = "/consumeDetail")
public class ConsumeDetailInfoController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ConsumeDetailInfoController.class);

    @Autowired
    @Qualifier(value = "consumeDetailInfoService")
    private ConsumeDetailInfoService consumeDetailInfoService;

    @Autowired
    @Qualifier(value = "consumeTypeInfoService")
    private ConsumeTypeInfoService consumeTypeInfoService;

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private ConsumeChannelInfoService consumeChannelInfoService;

    @Autowired
    private UserInfoService userInfoServcie;

    @Value(value = "${custom.rabbitmq.exchange.direct.consumeDetail}")
    private String consumeDetailDirectExchange;

    @Value(value = "${custom.rabbitmq.routingKey.consumeDetail}")
    private String consumeDetailRoutingKey;

    /**
     * @param request
     * @param qUserName
     * @param qConsumeId
     * @param startTime
     * @param endTime
     * @param currentPage
     * @param qRemark
     * @param qConsumeChannel
     * @return
     */
    @RequestMapping(value = "/findConsumeDetailInfoList")
    public String findConsumeDetailInfoList(HttpServletRequest request, String qUserName, String qConsumeId, String startTime, String endTime,
                                            @RequestParam(defaultValue = "1", required = false) Integer currentPage, String qRemark,
                                            String qConsumeChannel) {
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> paramTotal = new HashMap<>();
        if (StringUtils.isNotBlank(qUserName)) {
            param.put("userName", qUserName);
        }
        if (StringUtils.isNotBlank(qConsumeId)) {
            param.put("consumeId", qConsumeId);
        }
        if (StringUtils.isNotBlank(startTime)) {
            param.put("startTime", DateTimeUtil.parse(DateTimeUtil.DATE_TIME_PATTERN, startTime + " 00:00:00"));
        }
        if (StringUtils.isNotBlank(endTime)) {
            param.put("endTime", DateTimeUtil.parse(DateTimeUtil.DATE_TIME_PATTERN, endTime + " 23:59:59"));
        }
        if (StringUtils.isNotBlank(qRemark)) {
            param.put("remark", qRemark);
        }
        if (StringUtils.isNotBlank(qConsumeChannel)) {
            param.put("channel_code", qConsumeChannel);
        }

        param.put("pageSize", Constant.ROWS);
        if (null != currentPage) {
            param.put("pageRow", (currentPage - 1) * Constant.ROWS);
        }

        List<ConsumeDetailInfo> consumeDetailInfoList = null;
        int count = 0;

        UserInfo currentLoginUser = CommonUtil.getCurrentLoginUser(request);
        if (null != currentLoginUser) {
            boolean isAdmin = false;
            List<RoleInfo> roleList = currentLoginUser.getRoleList();
            for (RoleInfo roleInfo : roleList) {
                if (Constant.ADMIN.equals(roleInfo.getRoleId())) {
                    isAdmin = true;
                    break;
                }
            }
            if (isAdmin) {
                consumeDetailInfoList = consumeDetailInfoService.findConsumeDetailInfoListByMap(param);
                count = consumeDetailInfoService.findConsumeDetailInfoCountByMap(param);
            } else {
                param.put("userName", currentLoginUser.getUserName());
                consumeDetailInfoList = consumeDetailInfoService.findConsumeDetailInfoListByMap(param);
                count = consumeDetailInfoService.findConsumeDetailInfoCountByMap(param);
            }
        }

        //获取总计
        param.remove("pageSize");
        param.remove("pageRow");
        List<ConsumeDetailInfo> listDecimal = consumeDetailInfoService.findConsumeDetailInfoListByMap(param);
        BigDecimal decimal = new BigDecimal(0);
        if (CollectionUtils.isNotEmpty(listDecimal)) {
            for (ConsumeDetailInfo consumeDetailInfo : listDecimal) {
                decimal = decimal.add(consumeDetailInfo.getConsume_money());
            }
        }
        paramTotal.put("consumeDetailInfoList", consumeDetailInfoList);
        paramTotal.put("decimal", decimal);
        return buildSuccessResultInfo(count, paramTotal);
    }

    @RequestMapping(value = "/saveConsumeDetailInfo")
    public String saveConsumeDetailInfo(@RequestBody ConsumeDetailInfo consumeDetailInfo, HttpServletRequest request) {
        logger.info("saveConsumeDetailInfo 方法入参: " + consumeDetailInfo.toString());
        try {
            if (StringUtils.isBlank(consumeDetailInfo.getUserName())) {
                return buildFailedResultInfo(-1, "添加的用户不能为空!");
            }
            if (null == consumeDetailInfo.getConsume_money()) {
                return buildFailedResultInfo(-1, "金额需要填写!");
            }
            if (null == consumeDetailInfo.getConsume_time()) {
                return buildFailedResultInfo(-1, "消费日期需要填写!");
            }
            UserInfo userInfo = userInfoServcie.getUserInfoByUserName(consumeDetailInfo.getUserName());
            if (null == userInfo) {
                throw new Exception("没有此用户存在,无法添加消费信息!");
            }
            UserInfo loginUser = CommonUtil.getCurrentLoginUser(request);
            consumeDetailInfo.setCreate_user(loginUser != null ? loginUser.getUserName() : "");
            consumeDetailInfo.setUpdate_user(loginUser != null ? loginUser.getUserName() : "");
            //获取rabbitqmq的开关
            String rabbitSwitch = InitMemoryConfig.getParamValue(Constant.RabbitMQConstant.USE_RABBITMQ_SWITCH);
            if (Constant.FLAG_Y.equalsIgnoreCase(rabbitSwitch)) {
                //解耦,发送给rabbitmq
                String str = String.valueOf(RabbitUtil.sendObject(consumeDetailDirectExchange, consumeDetailRoutingKey, consumeDetailInfo, ConsumeDetailInfo.class));
                logger.info("saveConsumeDetailInfo 发送给rabbitmq处理, 返回值{}", str);
                return buildSuccessResultInfo("添加成功!");
            } else {
                int num = consumeDetailInfoService.saveConsumeDetail(consumeDetailInfo);
                logger.info("saveConsumeDetailInfo 直接插入数据库处理");
                if (num > 0) {
                    return buildSuccessResultInfo("添加成功!");
                } else {
                    return buildFailedResultInfo(-1, "添加失败!");
                }
            }

        } catch (Exception e) {
            logger.error("saveConsumeDetailInfo Exception" + e.getMessage(), e);
            ExceptionInfoUtil.saveExceptionInfo("saveConsumeDetailInfo", e.getMessage(), e);
            return buildFailedResultInfo(-1, "服务器异常,添加失败!");
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getConsumeDetailInfoById")
    public String getConsumeDetailInfoById(Integer id) {
        Map<String, Object> param = new HashMap<>();

        ConsumeDetailInfo consumeDetailInfo = new ConsumeDetailInfo();
        consumeDetailInfo.setId(id);
        List<ConsumeDetailInfo> findConsumeDetailInfo = consumeDetailInfoService
                .findConsumeDetailInfoListByObj(consumeDetailInfo);
        ConsumeDetailInfo consumeDetailInfo1 = null;
        if (CollectionUtils.isNotEmpty(findConsumeDetailInfo)) {
            consumeDetailInfo1 = findConsumeDetailInfo.get(0);
        }

        List<ConsumeTypeInfo> consumeTypeList = null;
        Map<String, Object> initMap = InitMemoryConfig.initMap;
        if (MapUtils.isNotEmpty(initMap)) {
            consumeTypeList = (List<ConsumeTypeInfo>) initMap.get(Constant.InitKey.CONSUMER_TYPE_LIST);
        } else {
            consumeTypeList = consumeTypeInfoService.findConsumeTypeInfo(null);
        }

        List<ConsumeChannelInfo> consumeChannelList = (List<ConsumeChannelInfo>)
                redisTemplate.opsForValue().get(Constant.RedisCustomKey.CONSUMECHANNELKEY);
        if (CollectionUtils.isEmpty(consumeChannelList)) {
            consumeChannelList = consumeChannelInfoService.findConsumeChannelList(null);
        }

        param.put("consumeDetailInfo", consumeDetailInfo1);
        param.put("consumeTypeList", consumeTypeList);
        param.put("channelInfoList", consumeChannelList);

        return buildSuccessResultInfo(param);
    }

    @RequestMapping(value = "/updateConsumeDetailInfo")
    public String updateConsumeDetailInfo(@RequestBody ConsumeDetailInfo consumeDetailInfo) {
        logger.info("updateConsumeDetailInfo 方法入参: " + consumeDetailInfo.toString());
        try {
            if (null != consumeDetailInfo && null != consumeDetailInfo.getId()
                    && StringUtils.isNotBlank(consumeDetailInfo.getUserName())
                    && null != consumeDetailInfo.getConsume_money() && null != consumeDetailInfo.getConsume_time()) {
                consumeDetailInfoService.updateConsumeDetailInfoById(consumeDetailInfo);
                return buildSuccessResultInfo("更新成功");
            } else {
                return buildFailedResultInfo(-1, "更新资料不全,请确认后更新!");
            }
        } catch (Exception e) {
            logger.error("updateConsumeDetailInfo : " + e.getMessage(), e);
            ExceptionInfoUtil.saveExceptionInfo("updateConsumeDetailInfo", e.getMessage(), e);
            return buildFailedResultInfo(-1, "服务器异常,更新失败!");
        }
    }

    @RequestMapping(value = "/importConsumeDetailInfo")
    public String importConsumeDetailInfo(HttpServletResponse response,
                                          @RequestParam("file-Portrait") MultipartFile file, HttpServletRequest request) {
        InputStream in = null;
        if (!file.isEmpty()) {
            try {
                String saveFileName = file.getOriginalFilename();
                logger.info("上传文件解析开始.文件名为:" + saveFileName);
                in = file.getInputStream();
                ExcelUtil<ExceConsumeDetailInfo> excelUtil = new ExcelUtil<ExceConsumeDetailInfo>(
                        ExceConsumeDetailInfo.class);
                List<ExceConsumeDetailInfo> importExcel = excelUtil.importExcel("消费明细", in);
                consumeDetailInfoService.batchInsert(importExcel);
                return buildSuccessResultInfo("上传文件成功");
            } catch (Exception e) {
                logger.error("importConsumeDetailInfo 上传文件出错." + e.getMessage(), e);
                ExceptionInfoUtil.saveExceptionInfo("importConsumeDetailInfo", e.getMessage(), e);
                return buildFailedResultInfo(-1, e.getMessage());
            }
        } else {
            return buildFailedResultInfo(-1, "上传文件为空!");
        }
    }
}
