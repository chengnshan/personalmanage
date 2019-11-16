package com.cxp.personalmanage.config.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.cxp.personalmanage.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.pojo.MenuInfo;
import com.cxp.personalmanage.pojo.RoleInfo;
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import com.cxp.personalmanage.pojo.consumer.ConsumeTypeInfo;
import com.cxp.personalmanage.service.ConsumeTypeInfoService;
import com.cxp.personalmanage.service.MenuInfoService;
import com.cxp.personalmanage.service.RoleInfoService;
import com.cxp.personalmanage.service.SystemParameterInfoService;

/**
 * CommandLineRunner、ApplicationRunner 接口是在容器启动成功后的最后一步回调（类似开机自启动）
 * @author cheng
 * @date 2019-11-15
 */
@Configuration
public class InitMemoryConfig implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(InitMemoryConfig.class);

	 @Autowired
	 private RoleInfoService roleInfoService;
	 
	 @Autowired
	 private MenuInfoService menuInfoService;
	 
	 @Autowired
	 private SystemParameterInfoService systemParameterInfoService;
	 
	 @Autowired
	 @Qualifier(value = "consumeTypeInfoService")
	 private ConsumeTypeInfoService consumeTypeInfoService;
	
	 public static Map<String ,Object> initMap = new HashMap<>(8);

	 public static final Map<String,Map<String,Object>> emailCodeMap = new HashMap<>(16);
	
	 public void init() {
		 logger.info("initMap初始化了");
		/**获取角色列表*/
		List<RoleInfo> roleList = roleInfoService.findRoleList(null);
		if(!CollectionUtils.isEmpty(roleList)) {
			initMap.put(Constant.InitKey.ROLELIST, roleList);
		}
		
		/**获取消费类型*/
		List<ConsumeTypeInfo> typeInfoList = consumeTypeInfoService.findConsumeTypeInfo(null);
		if(!CollectionUtils.isEmpty(typeInfoList)) {
			initMap.put(Constant.InitKey.CONSUMER_TYPE_LIST, typeInfoList);
		}
		/**获取菜单详情*/
		List<MenuInfo> findMenuInfoList = menuInfoService.findMenuInfoList(null);
		if(!CollectionUtils.isEmpty(findMenuInfoList)) {
			initMap.put(Constant.InitKey.MENU_INFO_LIST, findMenuInfoList);
		}
		/**加载系统参数表中的内容*/
		 List<SystemParameterInfo> infos = systemParameterInfoService.listByProperty(null);
		 if(!CollectionUtils.isEmpty(infos)) {
			 initMap.put(Constant.InitKey.SYSTEM_PARAMTER_INFO_LIST, infos);
		 }

		/* *//**获取消费同步URL地址*//*
		SystemParameterInfo paramInfo = systemParameterInfoService.getByCode(Constant.ScheduConsume.SYNC_COSUME_DETAIL_URL);
		if(null != paramInfo) {
			initMap.put(Constant.ScheduConsume.SYNC_COSUME_DETAIL_URL, paramInfo.getParam_value());
		}
		*//**获取消费同步开关*//*
		SystemParameterInfo paramInfo1 = systemParameterInfoService.getByCode(Constant.ScheduConsume.SYNC_COSUME_DETAIL_SWITCH);
		if(null != paramInfo) {
			initMap.put(Constant.ScheduConsume.SYNC_COSUME_DETAIL_SWITCH, paramInfo1.getParam_value());
		}*/
	}

	/**
	 * 获取系统参数表中的数据
	 * @param code
	 * @return
	 */
	public static SystemParameterInfo getSysParamInfoByCode(String code){
		if (StringUtils.isBlank(StringUtil.conveterStr(code))){
			return null;
		}
		Map<String, Object> initMap = InitMemoryConfig.initMap;
		if(MapUtils.isNotEmpty(initMap)) {
			List<SystemParameterInfo> infos = (List<SystemParameterInfo>) initMap.get(Constant.InitKey.SYSTEM_PARAMTER_INFO_LIST);
			if ( !CollectionUtils.isEmpty(infos) ){
				Stream<SystemParameterInfo> infoStream = infos.stream().filter(
						systemParameterInfo -> code.equalsIgnoreCase(systemParameterInfo.getParam_code()));
				return infoStream.findFirst().get();
			}
		}
		return null;
	}

	public static String getParamValue(String code){
		SystemParameterInfo paramInfoByCode = getSysParamInfoByCode(code);
		if (paramInfoByCode != null){
			return paramInfoByCode.getParam_value();
		}
		return null;
	}

	/*@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
	 	//防止重复执行
		if(event.getApplicationContext().getParent() == null ) {
			this.init();
		}
		System.out.println("我的父容器为：" + event.getApplicationContext().getParent());
	}*/

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.init();
		logger.info(ArrayUtils.toString(args.getSourceArgs()));
	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(45);
		list.add(23);
		list.add(22);
		list.add(166);
		Stream<Integer> integerStream = list.stream().filter(i -> i == 16);
		System.out.println(integerStream.count());

		boolean b = list.stream().anyMatch(t -> t == 12);
		System.out.println(b);
	}
}
