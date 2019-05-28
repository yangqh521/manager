package com.manager.common;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.manager.enu.IsSuccessType;
import com.manager.enu.LogKeyType;
import com.manager.enu.LogNameType;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import java.util.Map;

@Slf4j
public class LogWebContext {

    private static final ThreadLocal<Map<String, Object>> cache = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return Maps.newLinkedHashMap();
        }
    };

    private static LogWebContext instance = new LogWebContext();

    public static LogWebContext getInstance(){
        return instance;
    }

    private LogWebContext(){}

    public void clear() {
        cache.remove();
    }

    public Map<String,Object> getMap(){
        return cache.get();
    }

    @SuppressWarnings("unchecked")
    public Map<String,Object> getLogMap(){
        Object object = getMap().get(LogKeyType.LOG_MAP.name());
        if(object != null)	{
            return (Map<String,Object>)object;
        }
        return Maps.newLinkedHashMap();
    }

    public Object getLog(String key){
        Map<String, Object> logMap = this.getLogMap();
        if(logMap == null)	{
            return null;
        }
        if(logMap.get(key) != null)	{
            return logMap.get(key);
        }
        return null;
    }

    public void setLogName(LogNameType LogNameType){
        getMap().put(LogKeyType.LOG_NAME.name(), LogNameType.getLogName());
    }

    public void setIsSuccess(IsSuccessType isSuccess){
        getMap().put(LogKeyType.IS_SUCCESS.name(), isSuccess.name());
    }

    public void putLog(String key, Object value){
        Map<String, Object> logMap = getLogMap();
        logMap.put(key, value);
        getMap().put(LogKeyType.LOG_MAP.name(), logMap);
    }

    public void printLog(){
        Map<String, Object> map = getMap();
        System.out.println(JSONObject.toJSONString(map));

        if(map.get(LogKeyType.LOG_NAME.name()) == null) {
            return;
        }

        String logName = (String) map.get(LogKeyType.LOG_NAME.name());
        Logger logger = Logger.getLogger(logName);
        logger.info(JSONObject.toJSONString(map));
        return;
    }


}
