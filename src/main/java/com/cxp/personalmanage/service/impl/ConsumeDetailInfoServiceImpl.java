package com.cxp.personalmanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.mapper.postgresql.ConsumeDetailInfoMapper;
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import com.cxp.personalmanage.pojo.UserInfo;
import com.cxp.personalmanage.pojo.consumer.ConsumeDetailInfo;
import com.cxp.personalmanage.pojo.consumer.ConsumeTypeInfo;
import com.cxp.personalmanage.pojo.excel.ExceConsumeDetailInfo;
import com.cxp.personalmanage.service.ConsumeDetailInfoService;
import com.cxp.personalmanage.service.ConsumeTypeInfoService;
import com.cxp.personalmanage.service.SystemParameterInfoService;
import com.cxp.personalmanage.service.UserInfoService;
import com.cxp.personalmanage.utils.HttpClientUtils;
import com.cxp.personalmanage.utils.JackJsonUtil;
import com.cxp.personalmanage.utils.encrypt.AESUtil;

@Service(value = "consumeDetailInfoService")
public class ConsumeDetailInfoServiceImpl extends ServiceImpl<ConsumeDetailInfoMapper,ConsumeDetailInfo>
        implements ConsumeDetailInfoService {

	private static final Logger logger = LoggerFactory.getLogger(ConsumeDetailInfoServiceImpl.class);

	@Autowired
	private ConsumeDetailInfoMapper consumeDetailInfoMapper;

	@Autowired
	private UserInfoService userInfoServcie;

	@Autowired
	private ConsumeTypeInfoService consumeTypeInfoService;
	
	@Autowired
	private SystemParameterInfoService systemParameterInfoService;

	@Override
	public List<ConsumeDetailInfo> findConsumeDetailInfoListByObj(ConsumeDetailInfo consumeDetailInfo) {
		// TODO Auto-generated method stub
		return consumeDetailInfoMapper.findConsumeDetailInfoListByObj(consumeDetailInfo);
	}
	

	@Override
	public int findConsumeDetailInfoCountByObj(ConsumeDetailInfo consumeDetailInfo) {
		// TODO Auto-generated method stub
		return consumeDetailInfoMapper.findConsumeDetailInfoCountByObj(consumeDetailInfo);
	}

	@Override
	public List<ConsumeDetailInfo> findConsumeDetailInfoListByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return consumeDetailInfoMapper.findConsumeDetailInfoListByMap(map);
	}

	@Override
	public int findConsumeDetailInfoCountByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return consumeDetailInfoMapper.findConsumeDetailInfoCountByMap(map);
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	@Override
	public int saveConsumeDetail(ConsumeDetailInfo consumeDetailInfo) throws Exception {
        int saveNum = 0;
        //直接插入数据库
        saveNum = consumeDetailInfoMapper.saveConsumeDetail(consumeDetailInfo);
        //同步消费信息
        String synConsumeSwitch = InitMemoryConfig.getParamValue(Constant.ScheduConsume.SYNC_COSUME_DETAIL_SWITCH);
        if(StringUtils.isBlank(synConsumeSwitch)) {
            SystemParameterInfo systemParam = systemParameterInfoService.getByCode(Constant.ScheduConsume.SYNC_COSUME_DETAIL_SWITCH);
            synConsumeSwitch = systemParam != null ? systemParam.getParam_value() : "";
        }

        //如果开关是开
        if(Constant.FLAG_Y.equals(synConsumeSwitch)) {
            String synConsumeUrl = InitMemoryConfig.getParamValue(Constant.ScheduConsume.SYNC_COSUME_DETAIL_URL);
            Map<String, String> map =new HashMap<>();
            map.put("requestId", System.currentTimeMillis()+"");
            map.put("requestUser", "aliyun");

            Map<String, Object> mapData =new HashMap<>();
            mapData.put(Constant.CommonInterface.ACTION, Constant.CommonInterface.INSERT);
            List<ConsumeDetailInfo> list =new ArrayList<>();
            list.add(consumeDetailInfo);
            mapData.put(Constant.CommonInterface.REQUESTDATA, list);

            //加密请求内容
            String requestData = AESUtil.encrypt(JackJsonUtil.objTojson(mapData), Constant.encypt.ENCYPT_PASSWORD_KEY);
            map.put("requestObject", requestData);
            String postresult = HttpClientUtils.httpPost_header(synConsumeUrl, map);
            logger.info("同步消费明细结果 : "+ postresult );
            if(StringUtils.isNotBlank(postresult) && !"null".equals(postresult)) {
                Map<String, Object> returnMap = JackJsonUtil.jsonToMap(postresult);
                if(MapUtils.isNotEmpty(returnMap)) {
                    String isSuccess = (String) returnMap.get("responseCode");
                    if("life-00001".equals(isSuccess)) {
                        return saveNum;
                    }else {
                        String errorMsg = (String) returnMap.get("responseObject");
                        throw new Exception("添加消费信息失败,同步接口调用失败! "+ errorMsg);
                    }
                }
            }else {
                throw new Exception("添加消费信息失败,同步接口调用异常!");
            }
        }
        return saveNum;
	}

	@Transactional(value="txPrimaryManager",rollbackFor = { Exception.class, RuntimeException.class }, propagation = Propagation.REQUIRED)
	@Override
	public int updateConsumeDetailInfoById(ConsumeDetailInfo consumeDetailInfo) throws Exception {
		ConsumeDetailInfo consumeDetailInfo1 = getConsumeDetailInfoById(consumeDetailInfo.getId());
		if (null != consumeDetailInfo1) {
			int num = consumeDetailInfoMapper.updateConsumeDetailInfoById(consumeDetailInfo);
			return num;
		} else {
			throw new Exception("不存在这条记录,无法更新!");
		}
	}

	@Override
	public ConsumeDetailInfo getConsumeDetailInfoById(Integer id) {
		return consumeDetailInfoMapper.getConsumeDetailInfoById(id);
	}

	@Transactional(value="txPrimaryManager",rollbackFor = { Exception.class, RuntimeException.class }, propagation = Propagation.REQUIRED)
	@Override
	public String batchInsert(List<ExceConsumeDetailInfo> list) throws Exception {
		ConsumeDetailInfo consumeDetailInfo = null;
		ConsumeTypeInfo type = null;
		List<ConsumeDetailInfo> saveList = new ArrayList<>();
		int i = 0;
		if (CollectionUtils.isNotEmpty(list)) {
			for (ExceConsumeDetailInfo exceConsumeDetailInfo : list) {
				String userName = exceConsumeDetailInfo.getUserName();
				UserInfo userInfo = userInfoServcie.getUserInfoByUserName(userName);
				if (userInfo == null) {
					throw new Exception("第" + (i + 1) + "行用户不存在!");
				}
				if (null == exceConsumeDetailInfo.getConsumeType()) {
					throw new Exception("第" + (i + 1) + "行消费必须填写!");
				}
				if (null == exceConsumeDetailInfo.getConsume_money()) {
					throw new Exception("第" + (i + 1) + "行消费金额必须填写!");
				}
				if (null == exceConsumeDetailInfo.getConsume_time()) {
					throw new Exception("第" + (i + 1) + "行消费时间必须填写!");
				}
				consumeDetailInfo = new ConsumeDetailInfo();
				consumeDetailInfo.setUserName(userName);
				consumeDetailInfo.setConsume_money(exceConsumeDetailInfo.getConsume_money());
				consumeDetailInfo.setConsume_time(exceConsumeDetailInfo.getConsume_time());
				type = consumeTypeInfoService.getConsumeTypeByNum(exceConsumeDetailInfo.getConsumeType());
				consumeDetailInfo.setConsumeId(type != null ? type.getConsumeId() : null);
				consumeDetailInfo.setRemark(exceConsumeDetailInfo.getRemark());

				saveList.add(consumeDetailInfo);
			}
		}
		try {
			consumeDetailInfoMapper.batchInsert(saveList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception("保存数据失败!");
		}
		return Constant.SUCC;
	}

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	@Override
	public int batchInsertDetailInfo(List<ConsumeDetailInfo> list) {
		return consumeDetailInfoMapper.batchInsert(list);
	}
}
