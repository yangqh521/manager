package com.manager.dao.dsp;

import com.manager.pojo.AdverInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdverInfoMapper {

    List<AdverInfo> getAdverInfoList(Map<String,Object> map);

}
