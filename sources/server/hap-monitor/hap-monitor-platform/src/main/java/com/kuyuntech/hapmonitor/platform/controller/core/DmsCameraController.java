package com.kuyuntech.hapmonitor.platform.controller.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.DmsCameraBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.DmsCameraService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
* DmsCameraController
*
*/
@RestController
public class DmsCameraController {

    private static final Logger logger = LoggerFactory.getLogger(DmsCameraController.class);

    @Value("${md5.salt}")
    private String SALT;

    @Autowired
    DmsCameraService dmsCameraService;

    @Autowired
    UmsUserService umsUserService;
    

    /**
    * 新增
    * TODO 待实现
    * @param dmsCameraBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) DmsCameraBean dmsCameraBean, BindingResult result){

         dmsCameraBean = this.dmsCameraService.add(dmsCameraBean);


         if (dmsCameraBean == null) {
               return ResponseBean.serverError("操作失败！");
         }

          return ResponseBean.success("操作成功！").addData("code", dmsCameraBean.getCode());

    }

    /**
    * 更新
    * TODO 待实现
    * @param dmsCameraBean 更新参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) DmsCameraBean dmsCameraBean, BindingResult result){

       dmsCameraBean = this.dmsCameraService.update(dmsCameraBean);


       if (dmsCameraBean == null) {
           return ResponseBean.serverError("操作失败！");
       }

       return ResponseBean.success("操作成功！").addData("code", dmsCameraBean.getCode());

    }


    /**
    * 删除
    * TODO 待实现
    * @param dmsCameraBean 删除参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) DmsCameraBean dmsCameraBean, String password,
                         HttpServletRequest request){

        String code = (String) request.getSession().getAttribute("code");
        UmsUserBean umsUserBean = umsUserService.find(code);

        if (!umsUserBean.getPassword().equals(DigestUtils.md5Hex(password + SALT))) {
            return ResponseBean.unAuthorize("密码错误！");
        }

         dmsCameraBean = this.dmsCameraService.delete(dmsCameraBean);

          if (dmsCameraBean == null) {
             return ResponseBean.serverError("操作失败！");
          }

          return ResponseBean.success("操作成功！");

    }


    /**
    * 查询列表
    * TODO 待实现
    * @param dmsCameraBean 查询参数
    * @return
    */
    @RequestMapping
    public Object list(DmsCameraBean dmsCameraBean, PagerBean pagerBean, HttpServletRequest request){

        String code = (String) request.getSession().getAttribute("code");
        UmsUserBean umsUserBean = umsUserService.find(code);

        PagerBean<DmsCameraBean> dmsCameraBeanPagerBean = this.dmsCameraService.findPager(dmsCameraBean,pagerBean, umsUserBean);

        List<Map> dmsCameraMapList = new ArrayList<>();

        dmsCameraBeanPagerBean.getItems().forEach((e) ->{
             Map dmsCameraMap = MapBuilder.newBuilder()
                                            .put("groupId",e.getGroupId())
                                            .put("num",e.getNum())
                                            .put("serialNum",e.getSerialNum())
                                            .put("name",e.getName())
                                            .put("position",e.getPosition())
                                            .put("state",e.getState())
                                            .put("code",e.getCode())
                                            .put("createTime",e.getCreateTime())
                                            .build();
                    dmsCameraMapList.add(dmsCameraMap);
             });


                return ResponseBean.success("操作成功！").addData("dmsCameras",dmsCameraMapList).addData("pager",dmsCameraBeanPagerBean.simplePager());

    }



    /**
    * 查询详情
    * TODO 待实现
    * @param dmsCameraBean 查询参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) DmsCameraBean dmsCameraBean,Errors errors){

           dmsCameraBean = this.dmsCameraService.find(dmsCameraBean);

                if(dmsCameraBean == null){
                    return  ResponseBean.serverError("该记录不存在!");
                }

                Map dmsCameraMap = MapBuilder.newBuilder()
                        .put("groupId",dmsCameraBean.getGroupId())
                        .put("num",dmsCameraBean.getNum())
                        .put("serialNum",dmsCameraBean.getSerialNum())
                        .put("name",dmsCameraBean.getName())
                        .put("position",dmsCameraBean.getPosition())
                        .put("state",dmsCameraBean.getState())
                        .put("code",dmsCameraBean.getCode())
                        .put("createTime",dmsCameraBean.getCreateTime())
                        .put("updateTime",dmsCameraBean.getUpdateTime())
                        .build();

        return ResponseBean.success("操作成功！").addData("dmsCamera",dmsCameraMap);

    }

    /**
    * 批量删除
    * @param codes 删除唯一标识集合
    * @return
    */
    @RequestMapping
    public Object batchDelete(List<String> codes){

        if(codes.isEmpty()){
            return ParamErrorResultBuilder.newBuilder().message("未选择任何删除记录！").paramError("codes","不能为空！").build();
        }

        try{
            this.dmsCameraService.batchDeleteByCodes(codes);
        }catch (Exception e){
            return  ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    


    @RequestMapping
    public Object getCameraInfo(HttpServletRequest request) {
        String code = (String) request.getSession().getAttribute("code");
        UmsUserBean umsUserBean = umsUserService.find(code);
        Map<String, Object> map = dmsCameraService.findCameraInfo(umsUserBean);

        return ResponseBean.success("操作成功").addData("dmsCameraNum", map);
    }
}
