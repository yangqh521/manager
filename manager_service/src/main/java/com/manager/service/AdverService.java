package com.manager.service;

import com.manager.pojo.AdverInfo;

import java.util.List;
import java.util.Map;

public interface AdverService {

    List<AdverInfo> getAdverInfoList(Map<String,Object> map);

}
