package com.kuyuntech.hapmonitor.corerestful.controller.core;

import com.kuyuntech.hapmonitor.coreapi.service.core.UmsRoleResourcesRelationService;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsRoleResourcesRelationBean;
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
* UmsRoleResourcesRelationController
*
*/
@RestController
public class UmsRoleResourcesRelationController {

    private static final Logger logger = LoggerFactory.getLogger(UmsRoleResourcesRelationController.class);

    @Autowired
    UmsRoleResourcesRelationService umsRoleResourcesRelationService;

    

    /**
    * 新增
    * TODO 待实现
    * @param umsRoleResourcesRelationBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) UmsRoleResourcesRelationBean umsRoleResourcesRelationBean){

         umsRoleResourcesRelationBean = this.umsRoleResourcesRelationService.add(umsRoleResourcesRelationBean);


         if (umsRoleResourcesRelationBean == null) {
               return ResponseBean.serverError("操作失败！");
         }

          return ResponseBean.success("操作成功！").addData("code", umsRoleResourcesRelationBean.getCode());

    }

    /**
    * 更新
    * TODO 待实现
    * @param umsRoleResourcesRelationBean 更新参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) UmsRoleResourcesRelationBean umsRoleResourcesRelationBean){

       umsRoleResourcesRelationBean = this.umsRoleResourcesRelationService.update(umsRoleResourcesRelationBean);


       if (umsRoleResourcesRelationBean == null) {
           return ResponseBean.serverError("操作失败！");
       }

       return ResponseBean.success("操作成功！").addData("code", umsRoleResourcesRelationBean.getCode());

    }


    /**
    * 删除
    * TODO 待实现
    * @param umsRoleResourcesRelationBean 删除参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) UmsRoleResourcesRelationBean umsRoleResourcesRelationBean){

         umsRoleResourcesRelationBean = this.umsRoleResourcesRelationService.delete(umsRoleResourcesRelationBean);

          if (umsRoleResourcesRelationBean == null) {
             return ResponseBean.serverError("操作失败！");
          }

          return ResponseBean.success("操作成功！");

    }


    /**
    * 查询列表
    * TODO 待实现
    * @param umsRoleResourcesRelationBean 查询参数
    * @return
    */
    @RequestMapping
    public Object list(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean, PagerBean pagerBean){

        PagerBean<UmsRoleResourcesRelationBean> umsRoleResourcesRelationBeanPagerBean = this.umsRoleResourcesRelationService.findPager(umsRoleResourcesRelationBean,pagerBean);

        List<Map> umsRoleResourcesRelationMapList = new ArrayList<>();

        umsRoleResourcesRelationBeanPagerBean.getItems().forEach((e) ->{
             Map umsRoleResourcesRelationMap = MapBuilder.newBuilder()
                                            .put("roleId",e.getRoleId())
                                            .put("resourcesId",e.getResourcesId())
                                            .put("code",e.getCode())
                                            .put("createTime",e.getCreateTime())
                                            .put("updateTime",e.getUpdateTime())
                                            .build();
                    umsRoleResourcesRelationMapList.add(umsRoleResourcesRelationMap);
             });


                return ResponseBean.success("操作成功！").addData("umsRoleResourcesRelations",umsRoleResourcesRelationMapList).addData("pager",umsRoleResourcesRelationBeanPagerBean.simplePager());

    }



    /**
    * 查询详情
    * TODO 待实现
    * @param umsRoleResourcesRelationBean 查询参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) UmsRoleResourcesRelationBean umsRoleResourcesRelationBean,Errors errors){

           umsRoleResourcesRelationBean = this.umsRoleResourcesRelationService.find(umsRoleResourcesRelationBean);

                if(umsRoleResourcesRelationBean == null){
                    return  ResponseBean.serverError("该记录不存在!");
                }

                Map umsRoleResourcesRelationMap = MapBuilder.newBuilder()
                        .put("roleId",umsRoleResourcesRelationBean.getRoleId())
                        .put("resourcesId",umsRoleResourcesRelationBean.getResourcesId())
                        .put("code",umsRoleResourcesRelationBean.getCode())
                        .put("createTime",umsRoleResourcesRelationBean.getCreateTime())
                        .put("updateTime",umsRoleResourcesRelationBean.getUpdateTime())
                        .build();

        return ResponseBean.success("操作成功！").addData("umsRoleResourcesRelation",umsRoleResourcesRelationMap);

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
            this.umsRoleResourcesRelationService.batchDeleteByCodes(codes);
        }catch (Exception e){
            return  ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    

}
