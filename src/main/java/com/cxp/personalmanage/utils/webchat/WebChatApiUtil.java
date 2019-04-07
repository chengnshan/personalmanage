package com.cxp.personalmanage.utils.webchat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.pojo.webchat.menu.Button;
import com.cxp.personalmanage.pojo.webchat.menu.ViewButton;
import com.cxp.personalmanage.pojo.webchat.menu.WeiXinMenu;
import com.cxp.personalmanage.utils.HttpClientUtils;
import com.cxp.personalmanage.utils.JackJsonUtil;

import net.sf.json.JSONObject;
/**
 * 微信Api工具类
 * @author Administrator
 *
 */
public class WebChatApiUtil {

	/**
	 *  返回码			说明
		   -1		系统繁忙，此时请开发者稍候再试
			0		请求成功
		40001		AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性
		40002		请确保grant_type字段值为client_credential
		40164		调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置。（小程序及小游戏调用不要求IP地址在白名单内。）
	 * @return
	 */
	public static String getAccessToken() {
		Map<String, String> param = new HashMap<>();
		param.put("appid", Constant.WeiXin.APPID);
		param.put("secret", Constant.WeiXin.APPSECRET);
		param.put("grant_type", Constant.WeiXin.GRANT_TYPE);
		String result = HttpClientUtils.httpGet(Constant.WeiXin.ACCESS_TOKEN_URL, param);
		System.out.println(result);
		return result;
	}
	
	/**
	 * 自定义菜单
	 * POST
	 * https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
	 * @param accessToken
	 * @param url
	 * @return 13_-Mia6cfKx8BBEgBDJL1xeNKvfI0KAjyAx64LoYkod615hGtTIFHQNtep3Bvk9-JCG0cXn21tDWRGANNhJhl3kSjOJK39OoiLCNLMTnLd-ujzYUECw2eWI6IcvqkRGHTBO9mR_6fk3VpdUuafTWMhAFABEU
	 */
	public static JSONObject customChatMenu(String accessToken,String url ,WeiXinMenu menu) {
		JSONObject result = null;
		Map<String, String> map = new HashMap<>();
		String objectToString = JackJsonUtil.objectToString(menu);
		System.out.println(objectToString);
		map.put("body", objectToString);
		StringBuffer sbf=new StringBuffer(url);
		sbf.append("?access_token=").append(accessToken);
		result = doPostStr(sbf.toString(), objectToString);
		return result;
	}
	
	 /**
     * post请求
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url,String outStr){
    	CloseableHttpClient httpClient = HttpClients.createDefault();;
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
        httpPost.setHeader("Accept", "application/json;charset=" + "UTF-8");
		httpPost.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=" + "UTF-8");
		httpPost.setHeader("Connection", "Close");
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            jsonObject = JSONObject.fromObject(result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	try {
        		httpPost.releaseConnection();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return jsonObject;
    }

	
	/**
	 * @return
	 */
	public static WeiXinMenu initMenu() {
		WeiXinMenu menu = new WeiXinMenu();
		
		List<Button> buttonList=new ArrayList<>();
		//======================================================
		ViewButton vbtn1 = new ViewButton();
		vbtn1.setName("百度搜索一下");
		vbtn1.setType("view");
		vbtn1.setUrl("http://www.baidu.com");
		
		Button cbtn1=new Button();
		cbtn1.setName("相关资源");
		cbtn1.setSub_button(new Button[] {vbtn1});
		
		buttonList.add(cbtn1);
		//======================================================
		ViewButton cbtn1_vbtn1 = new ViewButton();
		cbtn1_vbtn1.setName("回复图文");
		cbtn1_vbtn1.setType("view");
		cbtn1_vbtn1.setUrl("http://5804d277.ngrok.io");
		ViewButton cbtn1_vbtn2 = new ViewButton();
		cbtn1_vbtn2.setName("博客");
		cbtn1_vbtn2.setType("view");
		cbtn1_vbtn2.setUrl("http://5804d277.ngrok.io/html/study/SpringMVC/springMVC_index.html");
		
		ViewButton cbtn1_vbtn3 = new ViewButton();
		cbtn1_vbtn3.setName("添加收支");
		cbtn1_vbtn3.setType("view");
		cbtn1_vbtn3.setUrl("http://5804d277.ngrok.io/html/weixin/addConsumeDetail.html");
		
		Button cbtn2=new Button();
//		cbtn1.setType("click");
		cbtn2.setName("菜单");
		cbtn2.setSub_button(new Button[] {cbtn1_vbtn1,cbtn1_vbtn2,cbtn1_vbtn3});
		
		buttonList.add(cbtn2);
		//======================================================
		ViewButton vbtn2 = new ViewButton();
		vbtn2.setName("来个美女");
		vbtn2.setType("view");
		vbtn2.setUrl("http://5804d277.ngrok.io/image/weixin/timg1.jpg");
		buttonList.add(vbtn2);
		
		menu.setButton(buttonList);
		return menu;
	}
	
	public static void main(String[] args) throws Exception {
//		System.out.println(getAccessToken());
//		String jsonStr = JackJsonUtil.objectToString(initMenu());
		JSONObject jsonStr = customChatMenu("13_rYi0GXjtiO1wievh8H_d2s204bcfVMJAlc2bff6Tj7LiV29SinMrKcl4mmxy8td3UJfHV8xlUaPlmNNGvvdt0ipRsOyMyALCdjiotgHGMU1ZzABV-ZzTvvTZavsNMQjACAXWA",
				Constant.WeiXin.CUSTOM_MENU_URL, initMenu());
		System.out.println(jsonStr);
	}
}
