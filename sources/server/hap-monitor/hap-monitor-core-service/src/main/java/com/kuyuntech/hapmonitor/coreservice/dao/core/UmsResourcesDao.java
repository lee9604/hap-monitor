package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsResources;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;




/**
 * UmsResourcesDao
 *
 */
public interface UmsResourcesDao extends FastbootRepository<UmsResources,Long> {

    

   
   UmsResources findByCodeAndValid(String code,Short valid);
   

   


}
