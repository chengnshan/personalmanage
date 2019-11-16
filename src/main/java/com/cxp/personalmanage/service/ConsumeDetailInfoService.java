package com.cxp.personalmanage.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxp.personalmanage.pojo.consumer.ConsumeDetailInfo;
import com.cxp.personalmanage.pojo.excel.ExceConsumeDetailInfo;

public interface ConsumeDetailInfoService extends IService<ConsumeDetailInfo> {

	/**
	 * 根据属性查询 
	 * @param consumeDetailInfo
	 * @return
	 */
	public List<ConsumeDetailInfo> findConsumeDetailInfoListByObj(ConsumeDetailInfo consumeDetailInfo);
	
	/**
	 * 根据对象查询总记录数
	 * @param consumeDetailInfo
	 * @return
	 */
	public int findConsumeDetailInfoCountByObj(ConsumeDetailInfo consumeDetailInfo);
	
	/**
	 * 根据Map传过来 的条件查询
	 * @param map
	 * @return
	 */
	public List<ConsumeDetailInfo> findConsumeDetailInfoListByMap(Map<String,Object> map);
	
	/**
	 * 根据Map传过来 的条件查询总数
	 * @param map
	 * @return
	 */
	public int findConsumeDetailInfoCountByMap(Map<String,Object> map);
	
	/**
	 * 保存单个
	 * @param consumeDetailInfo
	 * @return
	 */
	public int saveConsumeDetail(ConsumeDetailInfo consumeDetailInfo) throws Exception;
	
	/**
	 * 更新
	 * @param consumeDetailInfo
	 * @return
	 */
	public int updateConsumeDetailInfoById(ConsumeDetailInfo consumeDetailInfo) throws Exception;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ConsumeDetailInfo getConsumeDetailInfoById(Integer id);
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public String batchInsert(List<ExceConsumeDetailInfo> list) throws Exception;
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int batchInsertDetailInfo(List<ConsumeDetailInfo> list);
}
