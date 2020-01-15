package com.kuyuntech.hapmonitor.platform.interceptor.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.LmsLogBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    UmsUserService umsUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String code = (String) request.getSession().getAttribute("code");
        UmsUserBean umsUserBean = umsUserService.find(code);

        LmsLogBean lmsLogBean = new LmsLogBean();

        // 设置操作者
        lmsLogBean.setOperator(umsUserBean.getUsername());


        if (Pattern.matches("(umsUser)|(umsAdmin)/(login)$", request.getRequestURI())) {
        }

        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
