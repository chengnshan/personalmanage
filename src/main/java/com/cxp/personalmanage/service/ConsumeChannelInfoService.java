package com.cxp.personalmanage.service;

import com.cxp.personalmanage.pojo.consumer.ConsumeChannelInfo;

import java.util.List;

public interface ConsumeChannelInfoService {

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
