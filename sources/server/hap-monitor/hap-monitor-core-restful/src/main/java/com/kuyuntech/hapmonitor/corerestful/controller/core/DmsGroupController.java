package com.kuyuntech.hapmonitor.corerestful.controller.core;

import com.kuyuntech.hapmonitor.coreapi.service.core.DmsGroupService;
import com.kuyuntech.hapmonitor.coreapi.bean.core.DmsGroupBean;
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
* DmsGroupController
*
*/
@RestController
public class DmsGroupController {

    private static final Logger logger = LoggerFactory.getLogger(DmsGroupController.class);

    @Autowired
    DmsGroupService dmsGroupService;

    

    /**
    * 新增
    * TODO 待实现
    * @param dmsGroupBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) DmsGroupBean dmsGroupBean){

         dmsGroupBean = this.dmsGroupService.add(dmsGroupBean, null);


         if (dmsGroupBean == null) {
               return ResponseBean.serverError("操作失败！");
         }

          return ResponseBean.success("操作成功！").addData("code", dmsGroupBean.getCode());

    }

    /**
    * 更新
    * TODO 待实现
    * @param dmsGroupBean 更新参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) DmsGroupBean dmsGroupBean){

       dmsGroupBean = this.dmsGroupService.update(dmsGroupBean);


       if (dmsGroupBean == null) {
           return ResponseBean.serverError("操作失败！");
       }

       return ResponseBean.success("操作成功！").addData("code", dmsGroupBean.getCode());

    }


    /**
    * 删除
    * TODO 待实现
    * @param dmsGroupBean 删除参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) DmsGroupBean dmsGroupBean){

         dmsGroupBean = this.dmsGroupService.delete(dmsGroupBean);

          if (dmsGroupBean == null) {
             return ResponseBean.serverError("操作失败！");
          }

          return ResponseBean.success("操作成功！");

    }


    /**
    * 查询列表
    * TODO 待实现
    * @param dmsGroupBean 查询参数
    * @return
    */
    @RequestMapping
    public Object list(DmsGroupBean dmsGroupBean, PagerBean pagerBean){

        PagerBean<DmsGroupBean> dmsGroupBeanPagerBean = this.dmsGroupService.findPager(dmsGroupBean,pagerBean);

        List<Map> dmsGroupMapList = new ArrayList<>();

        dmsGroupBeanPagerBean.getItems().forEach((e) ->{
             Map dmsGroupMap = MapBuilder.newBuilder()
                                            .put("name",e.getName())
                                            .put("quantity",e.getQuantity())
                                            .put("code",e.getCode())
                                            .put("createTime",e.getCreateTime())
                                            .put("updateTime",e.getUpdateTime())
                                            .build();
                    dmsGroupMapList.add(dmsGroupMap);
             });


                return ResponseBean.success("操作成功！").addData("dmsGroups",dmsGroupMapList).addData("pager",dmsGroupBeanPagerBean.simplePager());

    }



    /**
    * 查询详情
    * TODO 待实现
    * @param dmsGroupBean 查询参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) DmsGroupBean dmsGroupBean,Errors errors){

           dmsGroupBean = this.dmsGroupService.find(dmsGroupBean);

                if(dmsGroupBean == null){
                    return  ResponseBean.serverError("该记录不存在!");
                }

                Map dmsGroupMap = MapBuilder.newBuilder()
                        .put("name",dmsGroupBean.getName())
                        .put("quantity",dmsGroupBean.getQuantity())
                        .put("code",dmsGroupBean.getCode())
                        .put("createTime",dmsGroupBean.getCreateTime())
                        .put("updateTime",dmsGroupBean.getUpdateTime())
                        .build();

        return ResponseBean.success("操作成功！").addData("dmsGroup",dmsGroupMap);

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
            this.dmsGroupService.batchDeleteByCodes(codes);
        }catch (Exception e){
            return  ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    

}
