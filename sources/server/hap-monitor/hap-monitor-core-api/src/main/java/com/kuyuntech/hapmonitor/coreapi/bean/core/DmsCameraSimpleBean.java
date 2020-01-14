package com.kuyuntech.hapmonitor.coreapi.bean.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DmsCameraSimpleBean implements Serializable {
    private String num ;

    private String serialNum ;

    private String name ;

    private String position ;

    private Short state ;

    private String code ;

    private Date createTime ;

    // 分组名
    private String groupName;
}
