package com.kuyuntech.hapmonitor.platform.controller.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsAdminBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserService;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import com.wbspool.fastboot.core.common.bean.ResponseBean;
import com.wbspool.fastboot.core.common.builder.MapBuilder;
import com.wbspool.fastboot.core.common.constant.ValidGroup;
import com.wbspool.fastboot.core.web.annotation.ParamErrorAutoResponse;
import com.wbspool.fastboot.core.web.result.ParamErrorResultBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * UmsUserController
 */
@RestController
public class UmsUserController {

    private static final Logger logger = LoggerFactory.getLogger(UmsUserController.class);

    @Autowired
    UmsUserService umsUserService;


    /**
     * 新增
     * TODO 待实现
     *
     * @param umsUserBean 新增参数
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) UmsUserBean umsUserBean) {

        umsUserBean = this.umsUserService.add(umsUserBean);


        if (umsUserBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！").addData("code", umsUserBean.getCode());

    }

    /**
     * 更新
     * TODO 待实现
     *
     * @param umsUserBean 更新参数
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) UmsUserBean umsUserBean, BindingResult result) {

        umsUserBean = this.umsUserService.update(umsUserBean);


        if (umsUserBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！").addData("code", umsUserBean.getCode());

    }


    /**
     * 删除
     * TODO 待实现
     *
     * @param umsUserBean 删除参数
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) UmsUserBean umsUserBean) {

        umsUserBean = this.umsUserService.delete(umsUserBean);

        if (umsUserBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！");

    }


    /**
     * 查询列表
     * TODO 待实现
     *
     * @param umsUserBean 查询参数
     * @return
     */
    @RequestMapping
    public Object list(UmsUserBean umsUserBean, PagerBean pagerBean) {

        PagerBean<UmsUserBean> umsUserBeanPagerBean = this.umsUserService.findPager(umsUserBean, pagerBean);

        List<Map> umsUserMapList = new ArrayList<>();

        umsUserBeanPagerBean.getItems().forEach((e) -> {
            Map umsUserMap = MapBuilder.newBuilder()
                    .put("roleId", e.getRoleId())
                    .put("parentId", e.getParentId())
                    .put("username", e.getUsername())
                    .put("password", e.getPassword())
                    .put("name", e.getName())
                    .put("phone", e.getPhone())
                    .put("company", e.getCompany())
                    .put("code", e.getCode())
                    .put("createTime", e.getCreateTime())
                    .put("updateTime", e.getUpdateTime())
                    .build();
            umsUserMapList.add(umsUserMap);
        });


        return ResponseBean.success("操作成功！").addData("umsUsers", umsUserMapList).addData("pager", umsUserBeanPagerBean.simplePager());

    }


    /**
     * 查询详情
     * TODO 待实现
     *
     * @param umsUserBean 查询参数
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) UmsUserBean umsUserBean, Errors errors) {

        umsUserBean = this.umsUserService.find(umsUserBean);

        if (umsUserBean == null) {
            return ResponseBean.serverError("该记录不存在!");
        }

        Map umsUserMap = MapBuilder.newBuilder()
                .put("roleId", umsUserBean.getRoleId())
                .put("parentId", umsUserBean.getParentId())
                .put("username", umsUserBean.getUsername())
                .put("password", umsUserBean.getPassword())
                .put("name", umsUserBean.getName())
                .put("phone", umsUserBean.getPhone())
                .put("company", umsUserBean.getCompany())
                .put("code", umsUserBean.getCode())
                .put("createTime", umsUserBean.getCreateTime())
                .put("updateTime", umsUserBean.getUpdateTime())
                .build();

        return ResponseBean.success("操作成功！").addData("umsUser", umsUserMap);

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
            this.umsUserService.batchDeleteByCodes(codes);
        } catch (Exception e) {
            return ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    @RequestMapping
    @ParamErrorAutoResponse
    public Object login(@Validated(ValidGroup.Detail.class) UmsUserBean umsUserBean, BindingResult result,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        umsUserBean = this.umsUserService.findByUmsAdminBean(umsUserBean);

        if (null == umsUserBean) {
            return ResponseBean.unAuthorize("账号或者密码错误");
        }

        HttpSession session = request.getSession();
        session.setAttribute("code", umsUserBean.getCode());
        session.setAttribute("id", umsUserBean.getId());
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(12 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseBean.success("操作成功").addData("code", umsUserBean.getCode());
    }

    @RequestMapping
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        return ResponseBean.success("注销成功");
    }

}
