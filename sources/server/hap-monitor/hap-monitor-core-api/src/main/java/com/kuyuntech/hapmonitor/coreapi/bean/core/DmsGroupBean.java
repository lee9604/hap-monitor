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
import java.lang.Integer;
import java.util.List;

/**
* DmsGroupBean
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DmsGroupBean  implements Serializable {


    @NotBlank(groups = {ValidGroup.Add.class}, message = "分组名称不能为空")
    private String name ;

    private Integer quantity ;

    private Long id ;

    private Long version ;

    private String code ;

    private Short valid ;

    private Date createTime ;

    private Date updateTime ;

    // 设备集合
    private List<DmsCameraSimpleBean> dmsCameraSimpleBeanList;

}
