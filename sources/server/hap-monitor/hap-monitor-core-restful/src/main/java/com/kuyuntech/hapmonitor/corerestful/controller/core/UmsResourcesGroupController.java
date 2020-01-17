package com.kuyuntech.hapmonitor.corerestful.controller.core;

import com.kuyuntech.hapmonitor.coreapi.service.core.UmsResourcesGroupService;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsResourcesGroupBean;
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
* UmsResourcesGroupController
*
*/
@RestController
public class UmsResourcesGroupController {

    private static final Logger logger = LoggerFactory.getLogger(UmsResourcesGroupController.class);

    @Autowired
    UmsResourcesGroupService umsResourcesGroupService;

    

    /**
    * 新增
    * TODO 待实现
    * @param umsResourcesGroupBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) UmsResourcesGroupBean umsResourcesGroupBean){

         umsResourcesGroupBean = this.umsResourcesGroupService.add(umsResourcesGroupBean);


         if (umsResourcesGroupBean == null) {
               return ResponseBean.serverError("操作失败！");
         }

          return ResponseBean.success("操作成功！").addData("code", umsResourcesGroupBean.getCode());

    }

    /**
    * 更新
    * TODO 待实现
    * @param umsResourcesGroupBean 更新参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) UmsResourcesGroupBean umsResourcesGroupBean){

       umsResourcesGroupBean = this.umsResourcesGroupService.update(umsResourcesGroupBean);


       if (umsResourcesGroupBean == null) {
           return ResponseBean.serverError("操作失败！");
       }

       return ResponseBean.success("操作成功！").addData("code", umsResourcesGroupBean.getCode());

    }


    /**
    * 删除
    * TODO 待实现
    * @param umsResourcesGroupBean 删除参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) UmsResourcesGroupBean umsResourcesGroupBean){

         umsResourcesGroupBean = this.umsResourcesGroupService.delete(umsResourcesGroupBean);

          if (umsResourcesGroupBean == null) {
             return ResponseBean.serverError("操作失败！");
          }

          return ResponseBean.success("操作成功！");

    }


    /**
    * 查询列表
    * TODO 待实现
    * @param umsResourcesGroupBean 查询参数
    * @return
    */
    @RequestMapping
    public Object list(UmsResourcesGroupBean umsResourcesGroupBean, PagerBean pagerBean){

        PagerBean<UmsResourcesGroupBean> umsResourcesGroupBeanPagerBean = this.umsResourcesGroupService.findPager(umsResourcesGroupBean,pagerBean);

        List<Map> umsResourcesGroupMapList = new ArrayList<>();

        umsResourcesGroupBeanPagerBean.getItems().forEach((e) ->{
             Map umsResourcesGroupMap = MapBuilder.newBuilder()
                                            .put("name",e.getName())
                                            .put("code",e.getCode())
                                            .put("createTime",e.getCreateTime())
                                            .put("updateTime",e.getUpdateTime())
                                            .build();
                    umsResourcesGroupMapList.add(umsResourcesGroupMap);
             });


                return ResponseBean.success("操作成功！").addData("umsResourcesGroups",umsResourcesGroupMapList).addData("pager",umsResourcesGroupBeanPagerBean.simplePager());

    }



    /**
    * 查询详情
    * TODO 待实现
    * @param umsResourcesGroupBean 查询参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) UmsResourcesGroupBean umsResourcesGroupBean,Errors errors){

           umsResourcesGroupBean = this.umsResourcesGroupService.find(umsResourcesGroupBean);

                if(umsResourcesGroupBean == null){
                    return  ResponseBean.serverError("该记录不存在!");
                }

                Map umsResourcesGroupMap = MapBuilder.newBuilder()
                        .put("name",umsResourcesGroupBean.getName())
                        .put("code",umsResourcesGroupBean.getCode())
                        .put("createTime",umsResourcesGroupBean.getCreateTime())
                        .put("updateTime",umsResourcesGroupBean.getUpdateTime())
                        .build();

        return ResponseBean.success("操作成功！").addData("umsResourcesGroup",umsResourcesGroupMap);

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
            this.umsResourcesGroupService.batchDeleteByCodes(codes);
        }catch (Exception e){
            return  ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    

}
