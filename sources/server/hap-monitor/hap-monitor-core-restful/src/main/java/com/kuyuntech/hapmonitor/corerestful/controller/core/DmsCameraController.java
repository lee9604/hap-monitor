package com.kuyuntech.hapmonitor.corerestful.controller.core;

import com.kuyuntech.hapmonitor.coreapi.service.core.DmsCameraService;
import com.kuyuntech.hapmonitor.coreapi.bean.core.DmsCameraBean;
import com.wbspool.fastboot.core.common.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wbspool.fastboot.core.web.result.ParamErrorResultBuilder;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.Map;
import com.wbspool.fastboot.core.common.builder.MapBuilder;
import com.wbspool.fastboot.core.web.annotation.ParamErrorAutoResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.Errors;
import com.wbspool.fastboot.core.common.constant.ValidGroup;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
* DmsCameraController
*
*/
@RestController
public class DmsCameraController {

    private static final Logger logger = LoggerFactory.getLogger(DmsCameraController.class);

    @Autowired
    DmsCameraService dmsCameraService;

    

    /**
    * 新增
    * TODO 待实现
    * @param dmsCameraBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) DmsCameraBean dmsCameraBean){

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
    public Object update(@Validated(ValidGroup.Update.class) DmsCameraBean dmsCameraBean){

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
    public Object delete(@Validated(ValidGroup.Delete.class) DmsCameraBean dmsCameraBean){

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
    public Object list(DmsCameraBean dmsCameraBean, PagerBean pagerBean){

        PagerBean<DmsCameraBean> dmsCameraBeanPagerBean = this.dmsCameraService.findPager(dmsCameraBean,pagerBean);

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
                                            .put("updateTime",e.getUpdateTime())
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

    

}
