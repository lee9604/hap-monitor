package com.kuyuntech.hapmonitor.coreapi.bean.core;

import com.wbspool.fastboot.core.common.constant.ValidGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import java.lang.Long;
import java.lang.Short;
import java.util.Date;
import java.lang.String;

/**
* UmsUserBean
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UmsUserBean  implements Serializable {


    private Long roleId ;

    private Long parentId ;

    @NotBlank(groups = {ValidGroup.Add.class, ValidGroup.Update.class}, message = "用户名不能为空")
    private String username ;

    @NotBlank(groups = {ValidGroup.Add.class, ValidGroup.Update.class}, message = "密码不能为空")
    private String password ;

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

}
