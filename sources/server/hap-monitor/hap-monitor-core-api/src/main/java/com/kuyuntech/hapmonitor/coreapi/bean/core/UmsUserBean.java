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

    private String username ;

    private String password ;

    private String name ;

    private String phone ;

    private String company ;

    private Long id ;

    private Long version ;

    private String code ;

    private Short valid ;

    private Date createTime ;

    private Date updateTime ;



}
