package com.kuyuntech.hapmonitor.coreapi.bean.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
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

    private String num ;

    private String serialNum ;

    private String name ;

    private String position ;

    private Short state ;

    private Long id ;

    private Long version ;

    private String code ;

    private Short valid ;

    private Date createTime ;

    private Date updateTime ;



}
