package com.cxp.personalmanage.mapper.postgresql;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxp.personalmanage.pojo.UserRoleInfo;

public interface UserRoleInfoMapper extends BaseMapper<UserRoleInfo> {

    /**
     * 新增数据
     * @param userRoleInfo
     * @return
     */
    public int insertUserRoleInfo(UserRoleInfo userRoleInfo);

    /**
     * 根据条件更新用户
     * @param userRoleInfo
     * @return
     */
    public int updateUserRoleInfo(UserRoleInfo userRoleInfo);
    
    /**
     * 批量插入用户角色
     * @param userRoleInfo
     * @return
     */
    public int batchInsertUserRole(List<UserRoleInfo> userRoleInfo);
    
    public List<UserRoleInfo> findUserRoleByUserName(UserRoleInfo userRoleInfo);
    
    /**
     * 根据用户名删除用户角色关系
     * @param userName
     * @return
     */
    public int deleteUserRoleInfo(String userName);
}
