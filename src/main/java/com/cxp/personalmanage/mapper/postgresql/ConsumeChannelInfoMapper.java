package com.cxp.personalmanage.mapper.postgresql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxp.personalmanage.pojo.consumer.ConsumeChannelInfo;

import java.util.List;

public interface ConsumeChannelInfoMapper extends BaseMapper<ConsumeChannelInfo> {

    /**
     * 根据对象属性查询列表
     * @param consumeChannelInfo
     * @return
     */
    public List<ConsumeChannelInfo> findConsumeChannelList(ConsumeChannelInfo consumeChannelInfo);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public ConsumeChannelInfo findConsumeChannelById(Integer id);

    /**
     * 根据code查询-
     * @param channelCode
     * @return
     */
    public ConsumeChannelInfo findConsumeChannelByCode(String channelCode);
}
