package com.manager.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.HttpClientUtils;

import java.util.*;


/**
 * api签名校验
 */
@Slf4j
public class SignUtil {

    // key值
    private final static String ACCESS_KEY = "AccessKey";
    private final static String SECRET_KEY = "accessSecret";
    private final static String SIGN_KEY = "sign";
    private final static String TIMESTAMP_KEY = "timestamp";

    // value值
    public final static String ACCESS_VALUE = "zmeng123SiteOutApi";
    public final static String SECRET_VALUE = "qIg46lGyHEg7NveX";

    // 字符编码
    private final static String INPUT_CHARSET = "UTF-8";

    // 超时时间
    private final static int TIME_OUT = 30 * 60 * 1000;


    /**
     * 生成签名
     * @param paramsMap
     * @return
     */
    public static String createSign(SortedMap<String, Object> paramsMap) {
        StringBuffer sb = new StringBuffer( ACCESS_KEY + "=" + ACCESS_VALUE);
        Set es = paramsMap.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (StringUtils.isNotBlank(value) && !SIGN_KEY.equals(key) && !SECRET_KEY.equals(value)) {
                sb.append(key + "=" + value + "&");
            }
        }
        sb.append( SECRET_KEY + "=" + SECRET_VALUE);

        log.info("[ createSign ] sb.toStirng(): " + sb.toString());
        return MD5Util.MD5Encode(sb.toString(), INPUT_CHARSET).toUpperCase();
    }

    /**
     * 签名校验
     * @param paramsMap
     * @return
     */
    public static boolean verifySign(SortedMap<String, Object> paramsMap){

        // accessKey判断
        if(paramsMap.get(ACCESS_KEY) != null ){
            String accessKey = (String) paramsMap.get(ACCESS_KEY);
            if(!accessKey.equals(ACCESS_VALUE)){
                log.info("[ verifySign ] AccessKey is error !");
                return false;
            }
        }else{
            log.info("[ verifySign ] AccessKey is null !");
            return false;
        }

        // 签名判断
        String sign = "";
        if (paramsMap.get(SIGN_KEY) != null) {
            sign = (String) paramsMap.get(SIGN_KEY);
        }else {
            log.info("[ verifySign ] sign is null !!!");
            return false;
        }

        // 获取时间戳
        Long timestamp = null;
        if (paramsMap.get(TIMESTAMP_KEY) != null) {
            timestamp = (Long) paramsMap.get(TIMESTAMP_KEY);
        }else {
            return false;
        }

        // 校验签名
        String tenpaySign = ((String)paramsMap.get(SIGN_KEY)).toUpperCase();
        log.info("[ isTenpaySign ] tenpaySign: " + tenpaySign);

        if (tenpaySign.equals(createSign(paramsMap))) {
            //是否超时
            if ((System.currentTimeMillis() - Long.valueOf(timestamp)) > TIME_OUT){
                log.info("[ verifySign ] timestamp timeout !");
                return false;
            }
        }

        return true;
    }

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    public static void main(String[] args) {

        String accessKey = SignUtil.ACCESS_VALUE;
        String nonce = SignUtil.getRandomStringByLength(8);
        Long timestamp = System.currentTimeMillis();

        String url = "http://jz.union-wifi.com/industry/questionnaire";

        SortedMap<String,Object> paramsMap = new TreeMap<>();
        paramsMap.put("AccessKey", accessKey);
        paramsMap.put("industryId", 6);
        paramsMap.put("nonce", nonce);
        paramsMap.put("timestamp", timestamp);

        paramsMap.put("sign", SignUtil.createSign(paramsMap));

        paramsMap.put("industryId", " 6");

//        String result = HttpClientUtils.doPost(url, paramsMap, false);

//        System.out.println("result: " + result);

    }



}

