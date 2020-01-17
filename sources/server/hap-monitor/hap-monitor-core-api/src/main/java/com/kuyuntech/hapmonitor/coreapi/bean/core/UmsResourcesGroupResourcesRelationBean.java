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
* UmsResourcesGroupResourcesRelationBean
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UmsResourcesGroupResourcesRelationBean  implements Serializable {


    private Long resourcesGroupId ;

    private Long resourcesId ;

    private Long id ;

    private Long version ;

    private String code ;

    private Short valid ;

    private Date createTime ;

    private Date updateTime ;



}
