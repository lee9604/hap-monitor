package com.kuyuntech.hapmonitor.corerestful.controller.core;

import com.kuyuntech.hapmonitor.coreapi.service.core.UmsResourcesService;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsResourcesBean;
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
* UmsResourcesController
*
*/
@RestController
public class UmsResourcesController {

    private static final Logger logger = LoggerFactory.getLogger(UmsResourcesController.class);

    @Autowired
    UmsResourcesService umsResourcesService;

    

    /**
    * 新增
    * TODO 待实现
    * @param umsResourcesBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) UmsResourcesBean umsResourcesBean){

         umsResourcesBean = this.umsResourcesService.add(umsResourcesBean);


         if (umsResourcesBean == null) {
               return ResponseBean.serverError("操作失败！");
         }

          return ResponseBean.success("操作成功！").addData("code", umsResourcesBean.getCode());

    }

    /**
    * 更新
    * TODO 待实现
    * @param umsResourcesBean 更新参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) UmsResourcesBean umsResourcesBean){

       umsResourcesBean = this.umsResourcesService.update(umsResourcesBean);


       if (umsResourcesBean == null) {
           return ResponseBean.serverError("操作失败！");
       }

       return ResponseBean.success("操作成功！").addData("code", umsResourcesBean.getCode());

    }


    /**
    * 删除
    * TODO 待实现
    * @param umsResourcesBean 删除参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) UmsResourcesBean umsResourcesBean){

         umsResourcesBean = this.umsResourcesService.delete(umsResourcesBean);

          if (umsResourcesBean == null) {
             return ResponseBean.serverError("操作失败！");
          }

          return ResponseBean.success("操作成功！");

    }


    /**
    * 查询列表
    * TODO 待实现
    * @param umsResourcesBean 查询参数
    * @return
    */
    @RequestMapping
    public Object list(UmsResourcesBean umsResourcesBean, PagerBean pagerBean){

        PagerBean<UmsResourcesBean> umsResourcesBeanPagerBean = this.umsResourcesService.findPager(umsResourcesBean,pagerBean);

        List<Map> umsResourcesMapList = new ArrayList<>();

        umsResourcesBeanPagerBean.getItems().forEach((e) ->{
             Map umsResourcesMap = MapBuilder.newBuilder()
                                            .put("url",e.getUrl())
                                            .put("desc",e.getInfo())
                                            .put("code",e.getCode())
                                            .put("createTime",e.getCreateTime())
                                            .put("updateTime",e.getUpdateTime())
                                            .build();
                    umsResourcesMapList.add(umsResourcesMap);
             });


                return ResponseBean.success("操作成功！").addData("umsResourcess",umsResourcesMapList).addData("pager",umsResourcesBeanPagerBean.simplePager());

    }



    /**
    * 查询详情
    * TODO 待实现
    * @param umsResourcesBean 查询参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) UmsResourcesBean umsResourcesBean,Errors errors){

           umsResourcesBean = this.umsResourcesService.find(umsResourcesBean);

                if(umsResourcesBean == null){
                    return  ResponseBean.serverError("该记录不存在!");
                }

                Map umsResourcesMap = MapBuilder.newBuilder()
                        .put("url",umsResourcesBean.getUrl())
                        .put("desc",umsResourcesBean.getInfo())
                        .put("code",umsResourcesBean.getCode())
                        .put("createTime",umsResourcesBean.getCreateTime())
                        .put("updateTime",umsResourcesBean.getUpdateTime())
                        .build();

        return ResponseBean.success("操作成功！").addData("umsResources",umsResourcesMap);

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
            this.umsResourcesService.batchDeleteByCodes(codes);
        }catch (Exception e){
            return  ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    

}
