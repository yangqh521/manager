package com.manager.service.impl;


import com.manager.dao.dsp.AdverInfoMapper;
import com.manager.pojo.AdverInfo;
import com.manager.service.AdverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AdverServiceImpl implements AdverService {

    @Autowired
    private AdverInfoMapper adverInfoMapper;

    @Override
    public List<AdverInfo> getAdverInfoList(Map<String, Object> map) {
        log.info("enter AdverServiceImpl ~~~");
        return adverInfoMapper.getAdverInfoList(map);
    }
}
