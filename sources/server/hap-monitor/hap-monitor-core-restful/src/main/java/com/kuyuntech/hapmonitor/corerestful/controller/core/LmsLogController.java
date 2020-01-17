package com.kuyuntech.hapmonitor.corerestful.controller.core;

import com.kuyuntech.hapmonitor.coreapi.service.core.LmsLogService;
import com.kuyuntech.hapmonitor.coreapi.bean.core.LmsLogBean;
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
* LmsLogController
*
*/
@RestController
public class LmsLogController {

    private static final Logger logger = LoggerFactory.getLogger(LmsLogController.class);

    @Autowired
    LmsLogService lmsLogService;

    

    /**
    * 新增
    * TODO 待实现
    * @param lmsLogBean 新增参数
    * @return
    *
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object add(@Validated(ValidGroup.Add.class) LmsLogBean lmsLogBean){

         lmsLogBean = this.lmsLogService.add(lmsLogBean);


         if (lmsLogBean == null) {
               return ResponseBean.serverError("操作失败！");
         }

          return ResponseBean.success("操作成功！").addData("code", lmsLogBean.getCode());

    }

    /**
    * 更新
    * TODO 待实现
    * @param lmsLogBean 更新参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object update(@Validated(ValidGroup.Update.class) LmsLogBean lmsLogBean){

       lmsLogBean = this.lmsLogService.update(lmsLogBean);


       if (lmsLogBean == null) {
           return ResponseBean.serverError("操作失败！");
       }

       return ResponseBean.success("操作成功！").addData("code", lmsLogBean.getCode());

    }


    /**
    * 删除
    * TODO 待实现
    * @param lmsLogBean 删除参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object delete(@Validated(ValidGroup.Delete.class) LmsLogBean lmsLogBean){

         lmsLogBean = this.lmsLogService.delete(lmsLogBean);

          if (lmsLogBean == null) {
             return ResponseBean.serverError("操作失败！");
          }

          return ResponseBean.success("操作成功！");

    }


    /**
    * 查询列表
    * TODO 待实现
    * @param lmsLogBean 查询参数
    * @return
    */
    @RequestMapping
    public Object list(LmsLogBean lmsLogBean, PagerBean pagerBean){

        PagerBean<LmsLogBean> lmsLogBeanPagerBean = this.lmsLogService.findPager(lmsLogBean,pagerBean);

        List<Map> lmsLogMapList = new ArrayList<>();

        lmsLogBeanPagerBean.getItems().forEach((e) ->{
             Map lmsLogMap = MapBuilder.newBuilder()
                                            .put("operator",e.getOperator())
                                            .put("desc",e.getInfo())
                                            .put("target",e.getTarget())
                                            .put("code",e.getCode())
                                            .put("createTime",e.getCreateTime())
                                            .put("updateTime",e.getUpdateTime())
                                            .build();
                    lmsLogMapList.add(lmsLogMap);
             });


                return ResponseBean.success("操作成功！").addData("lmsLogs",lmsLogMapList).addData("pager",lmsLogBeanPagerBean.simplePager());

    }



    /**
    * 查询详情
    * TODO 待实现
    * @param lmsLogBean 查询参数
    * @return
    */
    @RequestMapping
    @ParamErrorAutoResponse
    public Object detail(@Validated(ValidGroup.Detail.class) LmsLogBean lmsLogBean,Errors errors){

           lmsLogBean = this.lmsLogService.find(lmsLogBean);

                if(lmsLogBean == null){
                    return  ResponseBean.serverError("该记录不存在!");
                }

                Map lmsLogMap = MapBuilder.newBuilder()
                        .put("operator",lmsLogBean.getOperator())
                        .put("desc",lmsLogBean.getInfo())
                        .put("target",lmsLogBean.getTarget())
                        .put("code",lmsLogBean.getCode())
                        .put("createTime",lmsLogBean.getCreateTime())
                        .put("updateTime",lmsLogBean.getUpdateTime())
                        .build();

        return ResponseBean.success("操作成功！").addData("lmsLog",lmsLogMap);

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
            this.lmsLogService.batchDeleteByCodes(codes);
        }catch (Exception e){
            return  ResponseBean.serverError("操作失败！");
        }


        return ResponseBean.success("操作成功！");
    }

    

}
