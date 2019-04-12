package com.donnie.interceptor;

import com.donnie.config.MultiTenantHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截请求，获取判断租户和写入ThreadLocal
 */
@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 一般不可能是传参数的，这里只做演示
        // 但可以判断用户登陆的session，或cookies，或二级域名识别是哪个租户
        String tenantId = request.getParameter("tenantId");
        log.info("preHandle---->>"+tenantId);

        if (tenantId != null && tenantId.length() > 0) {
            MultiTenantHolder.setCurrentNode("dn" + tenantId);
        }
        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MultiTenantHolder.remove();
        log.info("afterCompletion---->>");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle---->>");
    }
}
