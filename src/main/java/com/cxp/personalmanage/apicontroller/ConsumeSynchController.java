package com.cxp.personalmanage.apicontroller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.controller.base.BaseController;
import com.cxp.personalmanage.pojo.base.CommonRequestDTO;
import com.cxp.personalmanage.pojo.base.CommonResponseDTO;
import com.cxp.personalmanage.pojo.consumer.ConsumeDetailInfo;
import com.cxp.personalmanage.service.ConsumeDetailInfoService;
import com.cxp.personalmanage.utils.JackJsonUtil;
import com.cxp.personalmanage.utils.encrypt.AESUtil;

@RestController
@RequestMapping(value = "/synch")
public class ConsumeSynchController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ConsumeSynchController.class);

	@Autowired
	private ConsumeDetailInfoService consumeDetailInfoService;

	/***
	 * 一次请求一次动作,只有是保存和更新
	 * 
	 * @param request
	 * @param commonRequestDTO
	 * @return
	 * 
	 * 		请求格式: {"requestId":"1537027421623","requestUser":"admin",
	 *         "requestObject":"V6tIvLRJK2iWm/mKZ72BU9A+6GN0TFyTnz7oVsQ+J6IzA5An/iIQCJxxUwMZmEsqTbeCtH3UxreOsKq+LjQKYub3HmhMM7q9BmzWy7umsFuO65n4n/VaJelmaa4/lliUotuhcO7NIEXazRSe2csDRJWCs08As+7VPLaUTT6AfchDVHI1XsjqRr9URUq4Do4jL5jYQuqC9Z6r07+cm7sSRA=="}
	 */
	@RequestMapping(value = "/synchConsumeDetail")
	public CommonResponseDTO synchConsumeDetail(HttpServletRequest request, CommonRequestDTO commonRequestDTO) {

		logger.info("commonRequestDTO请求参数为: " + commonRequestDTO);
		if (commonRequestDTO != null && null != commonRequestDTO.getRequestObject()) {
			String strRequest = (String) commonRequestDTO.getRequestObject();
			logger.info("请求体的数据为: "+strRequest);
			try {
				String decryptData = null;
				if (StringUtils.isBlank(strRequest)) {
					return buildApiFailedResultInfo("life-99999", "请求失败,请求体数据requestObject为空!");
				}
				// 对加密的数据进行解密
				decryptData = AESUtil.decrypt(strRequest, Constant.encypt.ENCYPT_PASSWORD_KEY);
				logger.info(decryptData);
				// 解密后的字符json转换成Map
				Map<String, Object> jsonMap = JackJsonUtil.jsonToMap(decryptData); 
				if (MapUtils.isNotEmpty(jsonMap)) {
					String action = (String) jsonMap.get(Constant.CommonInterface.ACTION);
					List dataList = (List) jsonMap.get(Constant.CommonInterface.REQUESTDATA);

					if (CollectionUtils.isNotEmpty(dataList)) {
						List<ConsumeDetailInfo> list = new ArrayList<>(dataList.size());
						for (Object object : dataList) {
							ConsumeDetailInfo con = JackJsonUtil.MapToObject((Map) object, ConsumeDetailInfo.class);
							if (con != null) {
								if (StringUtils.isBlank(con.getUserName())) {
									return buildApiFailedResultInfo("life-99999", "请求失败,用户名不能为空或空字符串!");
								} else if (StringUtils.isBlank(con.getConsumeId())) {
									return buildApiFailedResultInfo("life-99999", "请求失败,消费ID不能为空或空字符串!");
								} else if (null == con.getConsume_money()) {
									return buildApiFailedResultInfo("life-99999", "请求失败,消费金额不能为空!");
								}
							}
							list.add(con);
						}

						int batchNum = 0;
						if (Constant.CommonInterface.INSERT.equals(action) && CollectionUtils.isNotEmpty(list)) {
							//数据库已经存在不需要添加
							ListIterator<ConsumeDetailInfo> listIterator = list.listIterator();
							while(listIterator.hasNext()) {
								ConsumeDetailInfo next = listIterator.next();
								List<ConsumeDetailInfo> nextConsumeDetailInfo = consumeDetailInfoService.findConsumeDetailInfoListByObj(next);
								if(CollectionUtils.isNotEmpty(nextConsumeDetailInfo)) {
									listIterator.remove();
								}
							}
							
							batchNum = consumeDetailInfoService.batchInsertDetailInfo(list);
							return buildApiSuccessResultInfo("成功同步添加[ " + batchNum + " ]条数据.");
						} else if (Constant.CommonInterface.UPDATE.equals(action) && CollectionUtils.isNotEmpty(list)) {
							for (ConsumeDetailInfo cons : list) {
								consumeDetailInfoService.updateConsumeDetailInfoById(cons);
								batchNum++;
							}
							return buildApiSuccessResultInfo("成功同步更新[ " + batchNum + " ]条数据.");
						} else {
							return buildApiFailedResultInfo("life-99999", "请求失败,更新还是添加不明确!");
						}
					} else {
						return buildApiFailedResultInfo("life-99999", "请求失败,数据传递不完整!");
					}
				} else {
					
				}
			} catch (Exception e) { 
				logger.error("synchConsumeDetail同步消费信息失败: " + e.getMessage());
				return buildApiFailedResultInfo("life-99999", "请求失败,数据传递格式不正确!");
			}

		}
		return buildApiFailedResultInfo("life-99999", "请求失败,数据传递格式不正确!");
	}

	public static void main(String[] args) {
		CommonRequestDTO commonRequestDTO = new CommonRequestDTO();
		commonRequestDTO.setRequestId(System.currentTimeMillis() + "");
		commonRequestDTO.setRequestUser("admin");

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("action", "insert");
		List<ConsumeDetailInfo> list = new ArrayList<>();
		ConsumeDetailInfo c2 = new ConsumeDetailInfo();
		c2.setUserName("80003382");
		c2.setConsume_money(new BigDecimal(93));
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 10, 15);
		c2.setConsume_time(cal.getTime());
		c2.setConsumeId("poi");
		c2.setConsumeName("poi");
		c2.setConsume_time(new Date());
		list.add(c2);
		jsonMap.put("requestData", list);

		String data = JackJsonUtil.objectToString(jsonMap);
		String encyptData = AESUtil.encrypt(data, Constant.encypt.ENCYPT_PASSWORD_KEY);

		commonRequestDTO.setRequestObject(encyptData);
		System.out.println(JackJsonUtil.objectToString(commonRequestDTO));

		System.out.println(AESUtil.decrypt(encyptData, Constant.encypt.ENCYPT_PASSWORD_KEY));
	}
}
