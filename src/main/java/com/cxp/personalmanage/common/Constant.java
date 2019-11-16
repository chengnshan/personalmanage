package com.cxp.personalmanage.common;

public class Constant {

	public static final String FLAG_Y="Y";

	public static final String VRIFYCODE="vrifyCode";
	public static final String SUCC="SUCC";
    public static final int ROWS = 10;
    public static final String ADMIN = "admin";
    
    public static final String YES="YES";
    public static final String NO="NO";
    public static final int ONE = 1;
    
    public static final String SALT = "abcdefg";
    public static final String MD5 = "MD5";
    public static final int HASHITERATIONS = 3;
    
    public static final String SPRINGBOOT_FILENAME = "SpringBoot.docx";
    public static final String LINUX_FILENAME = "Linux.docx";
    
    public static final String REDISDAO_LOGIN_PREFIX = "shiro_login_";
    public static final String REDISDAO_CACHE_PREFIX = "shiro_cache_";
    
    public static final String LOGIN_MENU_INFO ="userMenu";
    
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time"; 
    public static final String CREATE_USER = "create_user"; 
    public static final String UPDATE_USER = "update_user";

	public static final String EMAIL_VERITY_CODE = "email_verity_code";
	public static final String EMAIL_VERITY_TIME = "email_verity_time";
	public static final long EMAIL_VERITY_TIME_NUM = 300000;

	/**
	 * redis分布式锁常量
	 */
	public class DistributedCon{
		public static final String CONSUME_DETAIL_LOCK = "consume_detail_lock";
		public static final String SYN_DATA_TO_MYSQL_LOCK = "syn_data_to_mysql_lock";
	}

    
    public class WeiXin{
    	 public static final String APPID = "wx6354e8685cb793b1";
    	 public static final String APPSECRET = "73833d9e27d5773d53ea4ddeabff5f48";
    	 public static final String GRANT_TYPE = "client_credential";
    	 /**获取access_token*/
    	 public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    	 /**创建菜单*/
    	 public static final String CUSTOM_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create";
    	 /**查询菜单接口*/
    	 public static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    	 /**查询菜单接口*/
    	 public static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    }
    
    public class InitKey{
    	public static final String ROLELIST = "roleList";
    	public static final String CONSUMER_TYPE_LIST = "consumeTypeList";
    	public static final String MENU_INFO_LIST = "menuInfoList";
    	public static final String SYSTEM_PARAMTER_INFO_LIST = "system_paramter_info_list";
    }
    
    public class LinuxPath{
    	public static final String UPLOAD_PATH = "image/upload";//"/home/upload"; //"E:\\dowload\\wordcount";//
    }
    
    public class ScheduConsume{
    	/**定时添加地铁费用数据*/
    	public static final String CONSUME_CODE = "COSUME_SCHEDU_SETTING";
    	/**定时添加地铁费用Cron表达式*/
    	public static final String COSUME_SCHEDU_TIME = "COSUME_SCHEDU_TIME";
    	public static final String ADMIN = "admin";
    	/**同步消费明细接口开关*/
    	public static final String SYNC_COSUME_DETAIL_SWITCH = "SYNC_COSUME_DETAIL_SWITCH";
    	/**同步消费明细Url接口地址*/
    	public static final String SYNC_COSUME_DETAIL_URL = "SYNC_COSUME_DETAIL_URL";
    	
    	/**定时同步mysql数据Cron表达式*/
    	public static final String MYSQL_DATA_SCHEDU_TIME = "MYSQL_DATA_SCHEDU_TIME";
    }
    
    public class encypt{
    	public static final String ENCYPT_PASSWORD_KEY="SHRIO_SYNCHRONIZED_PASSWORD";
    }
    
    public class CommonInterface{
    	public static final String ACTION = "action";
    	public static final String REQUESTDATA = "requestData";
    	public static final String INSERT = "insert";
    	public static final String UPDATE = "update";
    }

    public class RedisCustomKey{
		public static final String CONSUMECHANNELKEY = "consumechannelkey";
	}

	public class RabbitMQConstant{
		public static final String USE_RABBITMQ_SWITCH = "USE_RABBITMQ_SWITCH";
	}
}
