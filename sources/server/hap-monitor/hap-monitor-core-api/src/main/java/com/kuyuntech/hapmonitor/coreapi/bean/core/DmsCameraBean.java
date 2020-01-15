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
* DmsCameraBean
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DmsCameraBean  implements Serializable {


    private Long groupId ;

    @NotBlank(groups = {ValidGroup.Add.class, ValidGroup.Update.class}, message = "设备编号不能为空")
    private String num ;

    @NotBlank(groups = {ValidGroup.Add.class, ValidGroup.Update.class}, message = "序列号不能为空")
    private String serialNum ;

    @NotBlank(groups = {ValidGroup.Add.class, ValidGroup.Update.class}, message = "设备名称不能为空")
    private String name ;

    private String position ;

    private Short state ;

    private Long id ;

    private Long version ;

    private String code ;

    private Short valid ;

    private Date createTime ;

    private Date updateTime ;

    // 分组名
    private String groupName;



}
