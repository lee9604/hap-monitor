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
* LmsLogBean
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LmsLogBean  implements Serializable {


    private String operator ;

    private String info ;

    private String target ;

    private Date operationTime;

    private Long id ;

    private Long version ;

    private String code ;

    private Short valid ;

    private Date createTime ;

    private Date updateTime ;



}
