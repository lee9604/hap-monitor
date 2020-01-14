package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsRoleResourcesRelation;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;

import java.util.List;


/**
 * UmsRoleResourcesRelationDao
 *
 */
public interface UmsRoleResourcesRelationDao extends FastbootRepository<UmsRoleResourcesRelation,Long> {

    

   
   UmsRoleResourcesRelation findByCodeAndValid(String code,Short valid);
   
   List<UmsRoleResourcesRelation> findUmsRoleResourcesRelationsByRoleId(Long roleId);


}
