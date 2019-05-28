package com.manager.filter;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import com.manager.common.LogWebContext;
import com.manager.enu.LogKeyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;


@Slf4j
@Order(1)
@Component
@WebFilter(filterName = "logWebFilter", urlPatterns = "/*")
public class LogWebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("------>>> notice: LogWebFilter init ~~~");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestURI = request.getRequestURI();

        long startTime = System.currentTimeMillis();

        LogWebContext logWebContext = LogWebContext.getInstance();
        logWebContext.getMap().put(LogKeyType.URI.name(), requestURI);
        logWebContext.getMap().put(LogKeyType.PAMRAMS.name(), JSONObject.toJSONString(parameterMap));

        // 向下执行
        filterChain.doFilter(servletRequest, servletResponse);

        String costTime = (System.currentTimeMillis() - startTime) + "ms";
        logWebContext.getMap().put(LogKeyType.COST_TIME.name(), costTime);
        logWebContext.printLog();
        logWebContext.clear();
        log.info("[ "+ requestURI +" ] params:" + JSONObject.toJSONString(parameterMap) + ", cost:" + costTime);

    }

    @Override
    public void destroy() {
        log.info("------>>> notice: LogWebFilter destroy !!!");
    }

}


