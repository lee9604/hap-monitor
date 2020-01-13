package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUserGroupRelation;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;




/**
 * UmsUserGroupRelationDao
 *
 */
public interface UmsUserGroupRelationDao extends FastbootRepository<UmsUserGroupRelation,Long> {

    

   
   UmsUserGroupRelation findByCodeAndValid(String code,Short valid);
   

   


}
