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
* DmsAlarmBean
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DmsAlarmBean  implements Serializable {


    private Long cameraId ;

    private Long groupId ;

    private String cameraPosition ;

    private String cameraName ;

    private String cameraNum ;

    private Short state ;

    private Long id ;

    private Long version ;

    private String code ;

    private Short valid ;

    private Date createTime ;

    private Date updateTime ;



}
