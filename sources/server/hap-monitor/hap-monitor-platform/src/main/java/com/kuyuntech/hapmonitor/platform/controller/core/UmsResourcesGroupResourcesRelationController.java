package com.kuyuntech.hapmonitor.platform.controller.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsResourcesGroupResourcesRelationBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsResourcesGroupResourcesRelationService;
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
* UmsResourcesGroupResourcesRelationController
*
*/
@RestController
public class UmsResourcesGroupResourcesRelationController {

    private static final Logger logger = LoggerFactory.getLogger(UmsResourcesGroupResourcesRelationController.class);

    @Autowired
    UmsResourcesGroupResourcesRelationService umsResourcesGroupResourcesRelationService;

    

    /**
    * 新增
    * TODO 待实现
    * @param umsResourcesGroupResourcesRelationBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean){

         umsResourcesGroupResourcesRelationBean = this.umsResourcesGroupResourcesRelationService.add(umsResourcesGroupResourcesRelationBean);


         if (umsResourcesGroupResourcesRelationBean == null) {
               return ResponseBean.serverError("操作失败！");
         }

          return ResponseBean.success("操作成功！").addData("code", umsResourcesGroupResourcesRelationBean.getCode());

    }

    /**
    * 更新
    * TODO 待实现
    * @param umsResourcesGroupResourcesRelationBean 更新参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean){

       umsResourcesGroupResourcesRelationBean = this.umsResourcesGroupResourcesRelationService.update(umsResourcesGroupResourcesRelationBean);


       if (umsResourcesGroupResourcesRelationBean == null) {
           return ResponseBean.serverError("操作失败！");
       }

       return ResponseBean.success("操作成功！").addData("code", umsResourcesGroupResourcesRelationBean.getCode());

    }


    /**
    * 删除
    * TODO 待实现
    * @param umsResourcesGroupResourcesRelationBean 删除参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean){

         umsResourcesGroupResourcesRelationBean = this.umsResourcesGroupResourcesRelationService.delete(umsResourcesGroupResourcesRelationBean);

          if (umsResourcesGroupResourcesRelationBean == null) {
             return ResponseBean.serverError("操作失败！");
          }

          return ResponseBean.success("操作成功！");

    }


    /**
    * 查询列表
    * TODO 待实现
    * @param umsResourcesGroupResourcesRelationBean 查询参数
    * @return
    */
    @RequestMapping
    public Object list(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean, PagerBean pagerBean){

        PagerBean<UmsResourcesGroupResourcesRelationBean> umsResourcesGroupResourcesRelationBeanPagerBean = this.umsResourcesGroupResourcesRelationService.findPager(umsResourcesGroupResourcesRelationBean,pagerBean);

        List<Map> umsResourcesGroupResourcesRelationMapList = new ArrayList<>();

        umsResourcesGroupResourcesRelationBeanPagerBean.getItems().forEach((e) ->{
             Map umsResourcesGroupResourcesRelationMap = MapBuilder.newBuilder()
                                            .put("resourcesGroupId",e.getResourcesGroupId())
                                            .put("resourcesId",e.getResourcesId())
                                            .put("code",e.getCode())
                                            .put("createTime",e.getCreateTime())
                                            .put("updateTime",e.getUpdateTime())
                                            .build();
                    umsResourcesGroupResourcesRelationMapList.add(umsResourcesGroupResourcesRelationMap);
             });


                return ResponseBean.success("操作成功！").addData("umsResourcesGroupResourcesRelations",umsResourcesGroupResourcesRelationMapList).addData("pager",umsResourcesGroupResourcesRelationBeanPagerBean.simplePager());

    }



    /**
    * 查询详情
    * TODO 待实现
    * @param umsResourcesGroupResourcesRelationBean 查询参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean,Errors errors){

           umsResourcesGroupResourcesRelationBean = this.umsResourcesGroupResourcesRelationService.find(umsResourcesGroupResourcesRelationBean);

                if(umsResourcesGroupResourcesRelationBean == null){
                    return  ResponseBean.serverError("该记录不存在!");
                }

                Map umsResourcesGroupResourcesRelationMap = MapBuilder.newBuilder()
                        .put("resourcesGroupId",umsResourcesGroupResourcesRelationBean.getResourcesGroupId())
                        .put("resourcesId",umsResourcesGroupResourcesRelationBean.getResourcesId())
                        .put("code",umsResourcesGroupResourcesRelationBean.getCode())
                        .put("createTime",umsResourcesGroupResourcesRelationBean.getCreateTime())
                        .put("updateTime",umsResourcesGroupResourcesRelationBean.getUpdateTime())
                        .build();

        return ResponseBean.success("操作成功！").addData("umsResourcesGroupResourcesRelation",umsResourcesGroupResourcesRelationMap);

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
            this.umsResourcesGroupResourcesRelationService.batchDeleteByCodes(codes);
        }catch (Exception e){
            return  ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    

}
