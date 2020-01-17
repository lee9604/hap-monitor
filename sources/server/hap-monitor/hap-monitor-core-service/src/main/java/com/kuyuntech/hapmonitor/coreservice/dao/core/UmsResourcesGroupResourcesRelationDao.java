package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsResourcesGroupResourcesRelation;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;




/**
 * UmsResourcesGroupResourcesRelationDao
 *
 */
public interface UmsResourcesGroupResourcesRelationDao extends FastbootRepository<UmsResourcesGroupResourcesRelation,Long> {

    

   
   UmsResourcesGroupResourcesRelation findByCodeAndValid(String code,Short valid);
   

   


}
