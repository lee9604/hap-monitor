package com.kuyuntech.hapmonitor.platform.controller.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsAdminBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsAdminService;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import com.wbspool.fastboot.core.common.bean.ResponseBean;
import com.wbspool.fastboot.core.common.builder.MapBuilder;
import com.wbspool.fastboot.core.common.constant.ValidGroup;
import com.wbspool.fastboot.core.web.annotation.ParamErrorAutoResponse;
import com.wbspool.fastboot.core.web.result.ParamErrorResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * UmsAdminController
 */
@RestController
public class UmsAdminController {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminController.class);

    @Autowired
    UmsAdminService umsAdminService;


    /**
     * 新增
     * TODO 待实现
     *
     * @param umsAdminBean 新增参数
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) UmsAdminBean umsAdminBean) {

        umsAdminBean = this.umsAdminService.add(umsAdminBean);


        if (umsAdminBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！").addData("code", umsAdminBean.getCode());

    }

    /**
     * 更新
     * TODO 待实现
     *
     * @param umsAdminBean 更新参数
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) UmsAdminBean umsAdminBean) {

        umsAdminBean = this.umsAdminService.update(umsAdminBean);


        if (umsAdminBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！").addData("code", umsAdminBean.getCode());

    }


    /**
     * 删除
     * TODO 待实现
     *
     * @param umsAdminBean 删除参数
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) UmsAdminBean umsAdminBean) {

        umsAdminBean = this.umsAdminService.delete(umsAdminBean);

        if (umsAdminBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！");

    }


    /**
     * 查询列表
     * TODO 待实现
     *
     * @param umsAdminBean 查询参数
     * @return
     */
    @RequestMapping
    public Object list(UmsAdminBean umsAdminBean, PagerBean pagerBean) {

        PagerBean<UmsAdminBean> umsAdminBeanPagerBean = this.umsAdminService.findPager(umsAdminBean, pagerBean);

        List<Map> umsAdminMapList = new ArrayList<>();

        umsAdminBeanPagerBean.getItems().forEach((e) -> {
            Map umsAdminMap = MapBuilder.newBuilder()
                    .put("roleId", e.getRoleId())
                    .put("username", e.getUsername())
                    .put("password", e.getPassword())
                    .put("code", e.getCode())
                    .put("createTime", e.getCreateTime())
                    .put("updateTime", e.getUpdateTime())
                    .build();
            umsAdminMapList.add(umsAdminMap);
        });


        return ResponseBean.success("操作成功！").addData("umsAdmins", umsAdminMapList).addData("pager", umsAdminBeanPagerBean.simplePager());

    }


    /**
     * 查询详情
     * TODO 待实现
     *
     * @param umsAdminBean 查询参数
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) UmsAdminBean umsAdminBean, Errors errors) {

        umsAdminBean = this.umsAdminService.find(umsAdminBean);

        if (umsAdminBean == null) {
            return ResponseBean.serverError("该记录不存在!");
        }

        Map umsAdminMap = MapBuilder.newBuilder()
                .put("roleId", umsAdminBean.getRoleId())
                .put("username", umsAdminBean.getUsername())
                .put("password", umsAdminBean.getPassword())
                .put("code", umsAdminBean.getCode())
                .put("createTime", umsAdminBean.getCreateTime())
                .put("updateTime", umsAdminBean.getUpdateTime())
                .build();

        return ResponseBean.success("操作成功！").addData("umsAdmin", umsAdminMap);

    }

    /**
     * 批量删除
     *
     * @param codes 删除唯一标识集合
     * @return
     */
    @RequestMapping
    public Object batchDelete(List<String> codes) {

        if (codes.isEmpty()) {
            return ParamErrorResultBuilder.newBuilder().message("未选择任何删除记录！").paramError("codes", "不能为空！").build();
        }

        try {
            this.umsAdminService.batchDeleteByCodes(codes);
        } catch (Exception e) {
            return ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }


    @RequestMapping
    @ParamErrorAutoResponse
    public Object login(@Validated(ValidGroup.Detail.class) UmsAdminBean umsAdminBean, BindingResult result,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        umsAdminBean = this.umsAdminService.findByUmsAdminBean(umsAdminBean);

        if (null == umsAdminBean) {
            return ResponseBean.serverError("操作失败");
        }

        HttpSession session = request.getSession();
        session.setAttribute("code", umsAdminBean.getCode());
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(30 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseBean.success("操作成功").addData("code", umsAdminBean.getCode());
    }

    @RequestMapping
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        return ResponseBean.success("注销成功");
    }

    @RequestMapping
    public Object hello() {
        return "hello";
    }


}
