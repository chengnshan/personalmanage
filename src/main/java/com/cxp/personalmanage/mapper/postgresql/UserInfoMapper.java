package com.cxp.personalmanage.mapper.postgresql;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxp.personalmanage.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
	/**
	 * 根据用户账号或者id查询唯一结果
	 * @param userInfo
	 * @return
	 */
	public UserInfo getUserInfo(UserInfo userInfo);
	
	public UserInfo getUserInfoByUserName(@Param("userAccount") String userName);

	/**
	 * 查询用户信息和权限信息
	 * @param param
	 * @return
	 */
	public List<UserInfo> findUserInfoList(Map<String,Object> param);

	/**
	 * 查询总数
	 * @param param
	 * @return
	 */
	public Integer findUserInfoListCount(Map<String,Object> param);
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int bathSaveUserInfo(List<UserInfo> list);
	
	/**
	 * 单条插入
	 * @param userInfo
	 * @return
	 */
	public int saveUserInfo(UserInfo userInfo);

	/**
	 * 更新用户
	 * @param userInfo
	 * @return
	 */
	public int updateUserInfo(UserInfo userInfo);
}
