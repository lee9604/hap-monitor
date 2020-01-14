package com.kuyuntech.hapmonitor.platform.interceptor.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsAdminBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsResourcesBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsRoleResourcesRelationBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsAdminService;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsResourcesService;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsRoleResourcesRelationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AuthAdminInterceptor implements HandlerInterceptor {

    @Autowired
    UmsAdminService umsAdminService;

    @Autowired
    UmsRoleResourcesRelationService umsRoleResourcesRelationService;

    @Autowired
    UmsResourcesService umsResourcesService;

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

//        UmsAdminBean umsAdminBean = umsAdminService.find(code);
//
//        if (null == umsAdminBean) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("status", "401");
//            map.put("msg", "账户被锁定或者账户不存在");
//            response.setContentType("application/json;charset=utf-8");
//            PrintWriter writer = response.getWriter();
//            writer.write(new ObjectMapper().writeValueAsString(map));
//            return false;
//        }

        /**
         * 查询出登录用户的权限信息，并且与访问的资源进行权限校验
         */
        UmsAdminBean umsAdminBean = umsAdminService.find(code);

        if (null == umsAdminBean) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", "401");
            map.put("msg", "账户被锁定或者账户不存在");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(map));
            return false;
        }

        List<UmsRoleResourcesRelationBean> umsRoleResourcesRelationList =
                umsRoleResourcesRelationService.findUmsRoleResourcesRelationsByRoleId(umsAdminBean.getRoleId());

        List<Long> resourcesIdList = new ArrayList<>();

        for (UmsRoleResourcesRelationBean umsRoleResourcesRelationBean : umsRoleResourcesRelationList) {
            resourcesIdList.add(umsRoleResourcesRelationBean.getResourcesId());
        }

        List<UmsResourcesBean> umsResourcesBeanList = umsResourcesService.findUmsResourcesByIdIn(resourcesIdList);

        List<String> urlList = new ArrayList<>();

        for (UmsResourcesBean umsResourcesBean : umsResourcesBeanList) {
            urlList.add(umsResourcesBean.getUrl());
        }

        if (Pattern.matches("/error", request.getRequestURI())) {
            return true;
        }

        for (String url : urlList) {
            if (Pattern.matches(url, request.getRequestURI())) {
                return true;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("status", "403");
        map.put("msg", "你不具备此访问权限");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(map));
        return false;

//        // 查询是否具备所请求资源的权限
//        if (!urlList.contains(request.getRequestURI())) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("status", "403");
//            map.put("msg", "你不具备此访问权限");
//            response.setContentType("application/json;charset=utf-8");
//            PrintWriter writer = response.getWriter();
//            writer.write(new ObjectMapper().writeValueAsString(map));
//            return false;
//        }
//        return true;
    }
}