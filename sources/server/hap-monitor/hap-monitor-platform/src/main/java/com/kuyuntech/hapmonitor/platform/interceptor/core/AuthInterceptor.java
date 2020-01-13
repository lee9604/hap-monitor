package com.kuyuntech.hapmonitor.platform.interceptor.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        if (StringUtils.isBlank(code)) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", "401");
            map.put("msg", "用户未登录或者身份验证已过期，请登录后在进行操作");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(map));
            return false;
        }
        return true;
    }
}