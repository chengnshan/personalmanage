package com.cxp.personalmanage.service.impl;

import com.cxp.personalmanage.mapper.postgresql.ConsumeChannelInfoMapper;
import com.cxp.personalmanage.pojo.consumer.ConsumeChannelInfo;
import com.cxp.personalmanage.service.ConsumeChannelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "consumeChannelInfoService")
public class ConsumeChannelInfoServiceImpl implements ConsumeChannelInfoService {

    @Autowired
    private ConsumeChannelInfoMapper consumeChannelInfoMapper;

    @Override
    public List<ConsumeChannelInfo> findConsumeChannelList(ConsumeChannelInfo consumeChannelInfo) {
        return consumeChannelInfoMapper.findConsumeChannelList(consumeChannelInfo);
    }

    @Override
    public ConsumeChannelInfo findConsumeChannelById(Integer id) {
        return consumeChannelInfoMapper.findConsumeChannelById(id);
    }

    @Override
    public ConsumeChannelInfo findConsumeChannelByCode(String channelCode) {
        return consumeChannelInfoMapper.findConsumeChannelByCode(channelCode);
    }
}
