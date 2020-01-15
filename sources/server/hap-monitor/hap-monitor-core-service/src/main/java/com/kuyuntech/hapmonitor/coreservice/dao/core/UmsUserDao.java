package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUser;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;

import java.util.List;


/**
 * UmsUserDao
 */
public interface UmsUserDao extends FastbootRepository<UmsUser, Long> {


    UmsUser findByCodeAndValid(String code, Short valid);


    UmsUser findByUsernameAndPassword(String username, String password);

    List<UmsUser> findUmsUsersByParentId(Long parentId);

}
