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


    @NotNull(groups = {CustomValidGroup.AddByCustomer.class,
            ValidGroup.Add.class,
            ValidGroup.Update.class,
            CustomValidGroup.UpdateLevelOne.class,
            CustomValidGroup.UpdateLevelTow.class,
            CustomValidGroup.AddUmsUser.class}, message =
            "必须选择账号等级类型")
    private Long roleId ;

    private Long parentId ;

    @NotBlank(groups = {
            CustomValidGroup.AddByCustomer.class,
            CustomValidGroup.AddByAdmin.class,
            CustomValidGroup.UpdateAdmin.class,
            ValidGroup.Add.class,
            ValidGroup.Update.class,
            CustomValidGroup.UpdateLevelOne.class,
            CustomValidGroup.UpdateLevelTow.class,
            CustomValidGroup.AddUmsUser.class,
            CustomValidGroup.AddAdmin.class},
            message =
            "用户名不能为空")
    private String username ;

    @NotBlank(groups = {
            CustomValidGroup.AddByCustomer.class,
            CustomValidGroup.AddByAdmin.class,
            CustomValidGroup.UpdateAdmin.class,
            ValidGroup.Add.class,
            ValidGroup.Update.class,
            CustomValidGroup.UpdateLevelOne.class,
            CustomValidGroup.UpdateLevelTow.class,
            CustomValidGroup.AddUmsUser.class,
            CustomValidGroup.AddAdmin.class},
            message =
            "密码不能为空")
    private String password ;

    @NotBlank(groups = {
            ValidGroup.Update.class,
            CustomValidGroup.AddByCustomer.class,
            CustomValidGroup.AddByAdmin.class,
            CustomValidGroup.UpdateLevelOne.class,
            CustomValidGroup.UpdateLevelTow.class,
            CustomValidGroup.AddUmsUser.class},
            message = "管理员姓名不能为空")
    private String name ;

    private String phone ;

    @NotBlank(groups = {ValidGroup.Add.class},
            message = "公司名称不能为空")
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
    @NotNull(groups = {
            ValidGroup.Update.class,
            CustomValidGroup.AddByCustomer.class,
            CustomValidGroup.AddUmsUser.class,
            CustomValidGroup.UpdateLevelTow.class},
            message = "至少选择一个分组")
    private List<Long> groupIdList;

    private List<String> groupNameList;

    private Short state;

}
