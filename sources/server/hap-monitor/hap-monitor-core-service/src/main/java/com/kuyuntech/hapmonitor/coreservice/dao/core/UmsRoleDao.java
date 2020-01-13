package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsRole;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;




/**
 * UmsRoleDao
 *
 */
public interface UmsRoleDao extends FastbootRepository<UmsRole,Long> {

    

   
   UmsRole findByCodeAndValid(String code,Short valid);
   

   


}
