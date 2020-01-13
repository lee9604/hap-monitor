package com.kuyuntech.hapmonitor.platform.controller.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.DmsAlarmBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.DmsAlarmService;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import com.wbspool.fastboot.core.common.bean.ResponseBean;
import com.wbspool.fastboot.core.common.builder.MapBuilder;
import com.wbspool.fastboot.core.common.constant.ValidGroup;
import com.wbspool.fastboot.core.web.annotation.ParamErrorAutoResponse;
import com.wbspool.fastboot.core.web.result.ParamErrorResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
* DmsAlarmController
*
*/
@RestController
public class DmsAlarmController {

    private static final Logger logger = LoggerFactory.getLogger(DmsAlarmController.class);

    @Autowired
    DmsAlarmService dmsAlarmService;

    

    /**
    * 新增
    * TODO 待实现
    * @param dmsAlarmBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) DmsAlarmBean dmsAlarmBean){

         dmsAlarmBean = this.dmsAlarmService.add(dmsAlarmBean);


         if (dmsAlarmBean == null) {
               return ResponseBean.serverError("操作失败！");
         }

          return ResponseBean.success("操作成功！").addData("code", dmsAlarmBean.getCode());

    }

    /**
    * 更新
    * TODO 待实现
    * @param dmsAlarmBean 更新参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) DmsAlarmBean dmsAlarmBean){

       dmsAlarmBean = this.dmsAlarmService.update(dmsAlarmBean);


       if (dmsAlarmBean == null) {
           return ResponseBean.serverError("操作失败！");
       }

       return ResponseBean.success("操作成功！").addData("code", dmsAlarmBean.getCode());

    }


    /**
    * 删除
    * TODO 待实现
    * @param dmsAlarmBean 删除参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) DmsAlarmBean dmsAlarmBean){

         dmsAlarmBean = this.dmsAlarmService.delete(dmsAlarmBean);

          if (dmsAlarmBean == null) {
             return ResponseBean.serverError("操作失败！");
          }

          return ResponseBean.success("操作成功！");

    }


    /**
    * 查询列表
    * TODO 待实现
    * @param dmsAlarmBean 查询参数
    * @return
    */
    @RequestMapping
    public Object list(DmsAlarmBean dmsAlarmBean, PagerBean pagerBean){

        PagerBean<DmsAlarmBean> dmsAlarmBeanPagerBean = this.dmsAlarmService.findPager(dmsAlarmBean,pagerBean);

        List<Map> dmsAlarmMapList = new ArrayList<>();

        dmsAlarmBeanPagerBean.getItems().forEach((e) ->{
             Map dmsAlarmMap = MapBuilder.newBuilder()
                                            .put("cameraId",e.getCameraId())
                                            .put("groupId",e.getGroupId())
                                            .put("cameraPosition",e.getCameraPosition())
                                            .put("cameraName",e.getCameraName())
                                            .put("cameraNum",e.getCameraNum())
                                            .put("state",e.getState())
                                            .put("code",e.getCode())
                                            .put("createTime",e.getCreateTime())
                                            .put("updateTime",e.getUpdateTime())
                                            .build();
                    dmsAlarmMapList.add(dmsAlarmMap);
             });


                return ResponseBean.success("操作成功！").addData("dmsAlarms",dmsAlarmMapList).addData("pager",dmsAlarmBeanPagerBean.simplePager());

    }



    /**
    * 查询详情
    * TODO 待实现
    * @param dmsAlarmBean 查询参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) DmsAlarmBean dmsAlarmBean,Errors errors){

           dmsAlarmBean = this.dmsAlarmService.find(dmsAlarmBean);

                if(dmsAlarmBean == null){
                    return  ResponseBean.serverError("该记录不存在!");
                }

                Map dmsAlarmMap = MapBuilder.newBuilder()
                        .put("cameraId",dmsAlarmBean.getCameraId())
                        .put("groupId",dmsAlarmBean.getGroupId())
                        .put("cameraPosition",dmsAlarmBean.getCameraPosition())
                        .put("cameraName",dmsAlarmBean.getCameraName())
                        .put("cameraNum",dmsAlarmBean.getCameraNum())
                        .put("state",dmsAlarmBean.getState())
                        .put("code",dmsAlarmBean.getCode())
                        .put("createTime",dmsAlarmBean.getCreateTime())
                        .put("updateTime",dmsAlarmBean.getUpdateTime())
                        .build();

        return ResponseBean.success("操作成功！").addData("dmsAlarm",dmsAlarmMap);

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
            this.dmsAlarmService.batchDeleteByCodes(codes);
        }catch (Exception e){
            return  ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    

}
