package com.kuyuntech.hapmonitor.coreapi.bean.core;

import com.kuyuntech.hapmonitor.coreapi.constant.core.CustomValidGroup;
import com.wbspool.fastboot.core.common.constant.ValidGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import java.lang.Long;
import java.lang.Short;
import java.util.Date;
import java.lang.String;
import java.util.List;

/**
* UmsUserBean
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UmsUserBean  implements Serializable {


    @NotNull(groups = {CustomValidGroup.UpdateLevelOne.class, CustomValidGroup.UpdateLevelTow.class}, message =
            "必须选择账号等级类型")
    private Long roleId ;

    private Long parentId ;

    @NotBlank(groups = {ValidGroup.Add.class, ValidGroup.Update.class, CustomValidGroup.UpdateLevelOne.class, CustomValidGroup.UpdateLevelTow.class}, message =
            "用户名不能为空")
    private String username ;

    @NotBlank(groups = {ValidGroup.Add.class, ValidGroup.Update.class, CustomValidGroup.UpdateLevelOne.class, CustomValidGroup.UpdateLevelTow.class}, message =
            "密码不能为空")
    private String password ;

    @NotBlank(groups = {CustomValidGroup.UpdateLevelOne.class, CustomValidGroup.UpdateLevelTow.class}, message = "管理员姓名不能为空")
    private String name ;

    private String phone ;

    @NotBlank(groups = {ValidGroup.Add.class, ValidGroup.Update.class}, message = "公司名称不能为空")
    private String company ;

    private Long id ;

    private Long version ;

    private String code ;

    private Short valid ;

    private Date createTime ;

    private Date updateTime ;

    // 设备数量
    private Integer cameraNum;

    // 分组ids
    private List<Long> groupIdList;

}
