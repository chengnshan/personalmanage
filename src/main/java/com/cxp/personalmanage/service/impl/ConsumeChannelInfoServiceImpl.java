package com.cxp.personalmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxp.personalmanage.mapper.postgresql.ConsumeChannelInfoMapper;
import com.cxp.personalmanage.pojo.consumer.ConsumeChannelInfo;
import com.cxp.personalmanage.service.ConsumeChannelInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "consumeChannelInfoService")
public class ConsumeChannelInfoServiceImpl extends ServiceImpl<ConsumeChannelInfoMapper,ConsumeChannelInfo>
        implements ConsumeChannelInfoService {

    @Override
    public List<ConsumeChannelInfo> findConsumeChannelList(ConsumeChannelInfo consumeChannelInfo) {
        return baseMapper.findConsumeChannelList(consumeChannelInfo);
    }

    @Override
    public ConsumeChannelInfo findConsumeChannelById(Integer id) {
        return baseMapper.findConsumeChannelById(id);
    }

    @Override
    public ConsumeChannelInfo findConsumeChannelByCode(String channelCode) {
        return baseMapper.findConsumeChannelByCode(channelCode);
    }
}
