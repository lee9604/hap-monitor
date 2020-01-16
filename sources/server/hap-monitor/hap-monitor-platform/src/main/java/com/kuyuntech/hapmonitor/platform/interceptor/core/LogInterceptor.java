package com.kuyuntech.hapmonitor.platform.interceptor.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.LmsLogBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.LmsLogService;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserService;
import com.kuyuntech.hapmonitor.platform.log.core.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    UmsUserService umsUserService;

    @Autowired
    LmsLogService lmsLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String code = (String) request.getSession().getAttribute("code");
        UmsUserBean umsUserBean = umsUserService.find(code);

        LmsLogBean lmsLogBean = new LmsLogBean();

        // 设置操作者
        lmsLogBean.setOperator(umsUserBean.getUsername());

        // 设置操作的描述信息和被操作的对象
        LogFactory.getInstance(request.getRequestURI()).executeRecord(lmsLogBean,request.getParameter("name"));

        request.setAttribute("lmsLogBean", lmsLogBean);

        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LmsLogBean lmsLogBean = (LmsLogBean) request.getAttribute("lmsLogBean");
        lmsLogService.add(lmsLogBean);
    }
}
