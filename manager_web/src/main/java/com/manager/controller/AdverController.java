package com.manager.controller;

import com.manager.common.AppResponse;
import com.manager.pojo.AdverInfo;
import com.manager.service.AdverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/adver")
public class AdverController {

    @Autowired
    AdverService adverService;

    @RequestMapping("/list")
    public AppResponse getAdverList(String adverName){

        log.info("enter AdverController ~~~");

        AppResponse resp = new AppResponse();

        if(StringUtils.isBlank(adverName)){
            adverName = "易汇众盟";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("adverNameLike",adverName);
        List<AdverInfo> adverInfoList = adverService.getAdverInfoList(map);

        resp.setData(adverInfoList);
        return resp;
    }


}
