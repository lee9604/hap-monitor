package com.kuyuntech.hapmonitor.platform.controller.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsAdminBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsAdminService;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserService;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import com.wbspool.fastboot.core.common.bean.ResponseBean;
import com.wbspool.fastboot.core.common.builder.MapBuilder;
import com.wbspool.fastboot.core.common.constant.ValidGroup;
import com.wbspool.fastboot.core.web.annotation.ParamErrorAutoResponse;
import com.wbspool.fastboot.core.web.result.ParamErrorResultBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * UmsAdminController
 */
@RestController
public class UmsAdminController {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminController.class);

    @Value("${md5.salt}")
    private String SALT;

    @Autowired
    UmsAdminService umsAdminService;

    @Autowired
    UmsUserService umsUserService;


    /**
     * 添加账号
     *
     * @param umsAdminBean 新增参数
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) UmsAdminBean umsAdminBean, BindingResult result) {

        umsAdminBean = this.umsAdminService.add(umsAdminBean);


        if (umsAdminBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！").addData("code", umsAdminBean.getCode());

    }

    /**
     * 更新Admin账号
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
     *
     * @param umsAdminBean 删除参数
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) UmsAdminBean umsAdminBean, String confirmPassword, HttpServletRequest request) {

        if (StringUtils.isBlank(confirmPassword)) {
            return ResponseBean.unAuthorize("密码不能为空！");
        }

        UmsAdminBean AdminBean = umsAdminService.find((String) request.getSession().getAttribute("code"));

        if (!AdminBean.getPassword().equals(DigestUtils.md5Hex(confirmPassword))) {
            return ResponseBean.unAuthorize("密码错误！");
        }


        umsAdminBean = this.umsAdminService.delete(umsAdminBean);

        if (umsAdminBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！");

    }


    /**
     * 查询列表，按照逆序排序
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
                    .put("username", e.getUsername())
                    .put("code", e.getCode())
                    .put("createTime", e.getCreateTime())
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


    /**
     * Admin登录
     *
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object login(@Validated(ValidGroup.Detail.class) UmsAdminBean umsAdminBean, BindingResult result,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        umsAdminBean = this.umsAdminService.findByUmsAdminBean(umsAdminBean);

        if (null == umsAdminBean) {
            return ResponseBean.unAuthorize("账号或者密码错误");
        }

        HttpSession session = request.getSession();
        session.setAttribute("code", umsAdminBean.getCode());
        session.setAttribute("id", umsAdminBean.getId());
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(12 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseBean.success("操作成功").addData("code", umsAdminBean.getCode());
    }

    /**
     * Admin注销
     *
     * @return
     */
    @RequestMapping
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        return ResponseBean.success("注销成功");
    }

    /**
     * 删除UmsUser
     * TODO 待完成
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object deleteUmsUser(@Validated(ValidGroup.Delete.class) UmsUserBean umsUserBean, BindingResult result, String password,
                                HttpServletRequest request) {

        if (StringUtils.isBlank(password)) {
            return ResponseBean.unAuthorize("密码不能为空！");
        }

        String code = (String) request.getSession().getAttribute("code");
        UmsAdminBean umsAdminBean = umsAdminService.find(code);
        if (!DigestUtils.md5Hex(password + SALT).equals(umsAdminBean.getPassword())) {
            return ResponseBean.unAuthorize("密码不正确！");
        }

        umsUserBean = this.umsAdminService.deleteUmsUser(umsUserBean);

        if (umsUserBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！");

    }

    /**
     * 添加客户
     *
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object addUmsUser(@Validated(ValidGroup.Add.class) UmsUserBean umsUserBean, BindingResult result) {

        umsUserBean = this.umsAdminService.addUmsUser(umsUserBean);


        if (umsUserBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！").addData("code", umsUserBean.getCode());

    }

    /**
     * 更新客户信息
     *
     * @return
     */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object updateUmsUser(@Validated(ValidGroup.Update.class) UmsUserBean umsUserBean, BindingResult result) {
        umsUserBean = this.umsAdminService.updateUmsUser(umsUserBean);


        if (umsUserBean == null) {
            return ResponseBean.serverError("操作失败！");
        }

        return ResponseBean.success("操作成功！").addData("code", umsUserBean.getCode());
    }

    /**
     * 查询客户列表，按照逆序排序
     *
     * @param umsUserBean 查询参数
     * @param pagerBean   分页参数
     * @return
     */
    @RequestMapping
    public Object listUmsUsers(UmsUserBean umsUserBean, PagerBean pagerBean) {

        PagerBean<UmsUserBean> umsUserBeanPagerBean = this.umsUserService.findPagerRoleLevelOne(umsUserBean, pagerBean);

        List<Map> umsUserMapList = new ArrayList<>();

        umsUserBeanPagerBean.getItems().forEach((e) -> {
            Map umsUserMap = MapBuilder.newBuilder()
                    .put("username", e.getUsername())
                    .put("name", e.getName())
                    .put("phone", e.getPhone())
                    .put("company", e.getCompany())
                    .put("code", e.getCode())
                    .put("createTime", e.getCreateTime())
                    .put("cameraNum", e.getCameraNum())
                    .put("valid", e.getValid())
                    .build();
            umsUserMapList.add(umsUserMap);
        });


        return ResponseBean.success("操作成功！").addData("umsUsers", umsUserMapList).addData("pager", umsUserBeanPagerBean.simplePager());
    }
}
