package com.cxp.personalmanage.mapper.mysql;

import java.util.List;
import java.util.Map;

import com.cxp.personalmanage.pojo.consumer.ConsumeDetailInfo;

public interface ConsumeDetailInfoMysqlMapper {

	/**
	 * 根据属性查询 
	 * @param consumeDetailInfo
	 * @return
	 */
	public List<ConsumeDetailInfo> findConsumeDetailInfoListByObj(ConsumeDetailInfo consumeDetailInfo);
	
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
	public int saveConsumeDetail(ConsumeDetailInfo consumeDetailInfo);
	
	/**
	 * 更新
	 * @param consumeDetailInfo
	 * @return
	 */
	public int updateConsumeDetailInfoById(ConsumeDetailInfo consumeDetailInfo);
	
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
	public int batchInsert(List<ConsumeDetailInfo> list);
}
