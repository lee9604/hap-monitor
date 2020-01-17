package com.kuyuntech.hapmonitor.coreservice.domain.core;

import com.wbspool.fastboot.core.jpa.entiy.FastbootEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UmsUser extends FastbootEntity {
    private Long roleId;
    // 0->代表是开发组发放的一级账号，其他代表从属于其他账号
    private Long parentId;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String company;
    private Short state;
}
