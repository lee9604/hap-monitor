package com.kuyuntech.hapmonitor.platform.controller.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsRoleBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsRoleService;
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
* UmsRoleController
*
*/
@RestController
public class UmsRoleController {

    private static final Logger logger = LoggerFactory.getLogger(UmsRoleController.class);

    @Autowired
    UmsRoleService umsRoleService;

    

    /**
    * 新增
    * TODO 待实现
    * @param umsRoleBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) UmsRoleBean umsRoleBean){

         umsRoleBean = this.umsRoleService.add(umsRoleBean);


         if (umsRoleBean == null) {
               return ResponseBean.serverError("操作失败！");
         }

          return ResponseBean.success("操作成功！").addData("code", umsRoleBean.getCode());

    }

    /**
    * 更新
    * TODO 待实现
    * @param umsRoleBean 更新参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) UmsRoleBean umsRoleBean){

       umsRoleBean = this.umsRoleService.update(umsRoleBean);


       if (umsRoleBean == null) {
           return ResponseBean.serverError("操作失败！");
       }

       return ResponseBean.success("操作成功！").addData("code", umsRoleBean.getCode());

    }


    /**
    * 删除
    * TODO 待实现
    * @param umsRoleBean 删除参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) UmsRoleBean umsRoleBean){

         umsRoleBean = this.umsRoleService.delete(umsRoleBean);

          if (umsRoleBean == null) {
             return ResponseBean.serverError("操作失败！");
          }

          return ResponseBean.success("操作成功！");

    }


    /**
    * 查询列表
    * TODO 待实现
    * @param umsRoleBean 查询参数
    * @return
    */
    @RequestMapping
    public Object list(UmsRoleBean umsRoleBean, PagerBean pagerBean){

        PagerBean<UmsRoleBean> umsRoleBeanPagerBean = this.umsRoleService.findPager(umsRoleBean,pagerBean);

        List<Map> umsRoleMapList = new ArrayList<>();

        umsRoleBeanPagerBean.getItems().forEach((e) ->{
             Map umsRoleMap = MapBuilder.newBuilder()
                                            .put("name",e.getName())
                                            .put("code",e.getCode())
                                            .put("createTime",e.getCreateTime())
                                            .put("updateTime",e.getUpdateTime())
                                            .build();
                    umsRoleMapList.add(umsRoleMap);
             });


                return ResponseBean.success("操作成功！").addData("umsRoles",umsRoleMapList).addData("pager",umsRoleBeanPagerBean.simplePager());

    }



    /**
    * 查询详情
    * TODO 待实现
    * @param umsRoleBean 查询参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) UmsRoleBean umsRoleBean,Errors errors){

           umsRoleBean = this.umsRoleService.find(umsRoleBean);

                if(umsRoleBean == null){
                    return  ResponseBean.serverError("该记录不存在!");
                }

                Map umsRoleMap = MapBuilder.newBuilder()
                        .put("name",umsRoleBean.getName())
                        .put("code",umsRoleBean.getCode())
                        .put("createTime",umsRoleBean.getCreateTime())
                        .put("updateTime",umsRoleBean.getUpdateTime())
                        .build();

        return ResponseBean.success("操作成功！").addData("umsRole",umsRoleMap);

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
            this.umsRoleService.batchDeleteByCodes(codes);
        }catch (Exception e){
            return  ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    

}
