package com.cxp.personalmanage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.cxp.personalmanage.pojo.consumer.ConsumeDetailInfo;
import com.cxp.personalmanage.utils.DateTimeUtil;
import com.cxp.personalmanage.utils.JackJsonUtil;

public class Test {

	@org.junit.Test
	public void test1() {
		String sql = "SELECT b.id,b.roleid,b.rolename,b.decription,b.enable,b.create_time,b.create_uer,b.update_time,b.update_uer\r\n" + 
				"			FROM uer_role_info a ,roleinfo b\r\n" + 
				"			WHERE a.roleid = b.roleid  AND a.enable = 1\r\n" + 
				"			  		and a.uername = ?";
		String replaceFirst = sql.replaceFirst("\\?", "80003382");
		System.out.println(replaceFirst);
	}
	
	@org.junit.Test
	public void test2() {
		System.out.println(DateTimeUtil.parse(DateTimeUtil.DATE_PATTERN,"2018-08-31" ));
		
		ConsumeDetailInfo cdi =new ConsumeDetailInfo();
		cdi.setConsume_time(new Date());
		cdi.setConsume_money(new BigDecimal(5.87));
		cdi.setConsumeId("transport");
		cdi.setRemark("");
		cdi.setCreate_user("");
		cdi.setUpdate_user("");
		cdi.setUserName("80003382");
		List<ConsumeDetailInfo> list = new ArrayList<>();
		list.add(cdi);
		
		System.out.println(JackJsonUtil.objectToString(list));
	}
	
	@org.junit.Test
	public void test3() throws Exception {
		List<ConsumeDetailInfo> list1= new ArrayList<>();
		
		ConsumeDetailInfo c1 = new ConsumeDetailInfo();
		c1.setConsumeId("abc");
		c1.setConsumeName("abc");
		c1.setConsume_time(new Date());
		
		ConsumeDetailInfo c2 = new ConsumeDetailInfo();
		c2.setConsumeId("poi");
		c2.setConsumeName("poi");
		c2.setConsume_time(new Date());
		list1.add(c2);
		list1.add(c1);
		//[{"create_time":null,"create_user":null,"update_time":null,"update_user":null,"id":null,"userName":null,"consume_time":"2018-09-14","consume_money":null,"consumeId":"poi","remark":null,"consumeTypeInfo":null,"consumeName":null,"weekDay":"星期五"}]

		String objTojson = JackJsonUtil.objTojson(list1);
		System.out.println(objTojson);
		
		List<ConsumeDetailInfo> List = JackJsonUtil.readListJavaBean(objTojson, ConsumeDetailInfo.class);
		System.out.println(List);
	/*	list2.removeAll(list1);
		System.out.println(list2);*/
		
//		list2.retainAll(list1);
//		System.out.println(list2);
	}
	

	@org.junit.Test
	public void test4() throws Exception {
		List<String> list1= new ArrayList<>();
		list1.add("aa1");
		list1.add("bb1");
		list1.add("cc1");
		list1.add("dd1");
		ListIterator<String> listIterator = list1.listIterator();
		while(listIterator.hasNext()) {
			String next = listIterator.next();
			if("cc1".equals(next)) {
				list1.remove(next);
			}
		}
		System.out.println(list1);
	}
}
