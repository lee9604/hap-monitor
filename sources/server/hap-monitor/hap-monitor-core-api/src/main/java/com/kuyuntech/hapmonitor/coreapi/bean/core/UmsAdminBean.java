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
* UmsAdminBean
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UmsAdminBean  implements Serializable {


    private Long roleId ;

    @NotBlank(groups = ValidGroup.Detail.class, message = "用户名不能为空")
    private String username ;

    @NotBlank(groups = ValidGroup.Detail.class, message = "密码不能为空")
    private String password ;

    private Long id ;

    private Long version ;

    private String code ;

    private Short valid ;

    private Date createTime ;

    private Date updateTime ;



}
