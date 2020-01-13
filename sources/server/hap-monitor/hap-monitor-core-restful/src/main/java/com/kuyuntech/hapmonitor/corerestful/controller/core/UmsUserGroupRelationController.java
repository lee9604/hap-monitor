package com.kuyuntech.hapmonitor.corerestful.controller.core;

import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserGroupRelationService;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserGroupRelationBean;
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
* UmsUserGroupRelationController
*
*/
@RestController
public class UmsUserGroupRelationController {

    private static final Logger logger = LoggerFactory.getLogger(UmsUserGroupRelationController.class);

    @Autowired
    UmsUserGroupRelationService umsUserGroupRelationService;

    

    /**
    * 新增
    * TODO 待实现
    * @param umsUserGroupRelationBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) UmsUserGroupRelationBean umsUserGroupRelationBean){

         umsUserGroupRelationBean = this.umsUserGroupRelationService.add(umsUserGroupRelationBean);


         if (umsUserGroupRelationBean == null) {
               return ResponseBean.serverError("操作失败！");
         }

          return ResponseBean.success("操作成功！").addData("code", umsUserGroupRelationBean.getCode());

    }

    /**
    * 更新
    * TODO 待实现
    * @param umsUserGroupRelationBean 更新参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) UmsUserGroupRelationBean umsUserGroupRelationBean){

       umsUserGroupRelationBean = this.umsUserGroupRelationService.update(umsUserGroupRelationBean);


       if (umsUserGroupRelationBean == null) {
           return ResponseBean.serverError("操作失败！");
       }

       return ResponseBean.success("操作成功！").addData("code", umsUserGroupRelationBean.getCode());

    }


    /**
    * 删除
    * TODO 待实现
    * @param umsUserGroupRelationBean 删除参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) UmsUserGroupRelationBean umsUserGroupRelationBean){

         umsUserGroupRelationBean = this.umsUserGroupRelationService.delete(umsUserGroupRelationBean);

          if (umsUserGroupRelationBean == null) {
             return ResponseBean.serverError("操作失败！");
          }

          return ResponseBean.success("操作成功！");

    }


    /**
    * 查询列表
    * TODO 待实现
    * @param umsUserGroupRelationBean 查询参数
    * @return
    */
    @RequestMapping
    public Object list(UmsUserGroupRelationBean umsUserGroupRelationBean, PagerBean pagerBean){

        PagerBean<UmsUserGroupRelationBean> umsUserGroupRelationBeanPagerBean = this.umsUserGroupRelationService.findPager(umsUserGroupRelationBean,pagerBean);

        List<Map> umsUserGroupRelationMapList = new ArrayList<>();

        umsUserGroupRelationBeanPagerBean.getItems().forEach((e) ->{
             Map umsUserGroupRelationMap = MapBuilder.newBuilder()
                                            .put("userId",e.getUserId())
                                            .put("groupId",e.getGroupId())
                                            .put("code",e.getCode())
                                            .put("createTime",e.getCreateTime())
                                            .put("updateTime",e.getUpdateTime())
                                            .build();
                    umsUserGroupRelationMapList.add(umsUserGroupRelationMap);
             });


                return ResponseBean.success("操作成功！").addData("umsUserGroupRelations",umsUserGroupRelationMapList).addData("pager",umsUserGroupRelationBeanPagerBean.simplePager());

    }



    /**
    * 查询详情
    * TODO 待实现
    * @param umsUserGroupRelationBean 查询参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) UmsUserGroupRelationBean umsUserGroupRelationBean,Errors errors){

           umsUserGroupRelationBean = this.umsUserGroupRelationService.find(umsUserGroupRelationBean);

                if(umsUserGroupRelationBean == null){
                    return  ResponseBean.serverError("该记录不存在!");
                }

                Map umsUserGroupRelationMap = MapBuilder.newBuilder()
                        .put("userId",umsUserGroupRelationBean.getUserId())
                        .put("groupId",umsUserGroupRelationBean.getGroupId())
                        .put("code",umsUserGroupRelationBean.getCode())
                        .put("createTime",umsUserGroupRelationBean.getCreateTime())
                        .put("updateTime",umsUserGroupRelationBean.getUpdateTime())
                        .build();

        return ResponseBean.success("操作成功！").addData("umsUserGroupRelation",umsUserGroupRelationMap);

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
            this.umsUserGroupRelationService.batchDeleteByCodes(codes);
        }catch (Exception e){
            return  ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    

}
