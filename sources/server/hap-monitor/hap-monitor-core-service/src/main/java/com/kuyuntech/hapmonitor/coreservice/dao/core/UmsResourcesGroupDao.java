package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsResourcesGroup;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;




/**
 * UmsResourcesGroupDao
 *
 */
public interface UmsResourcesGroupDao extends FastbootRepository<UmsResourcesGroup,Long> {

    

   
   UmsResourcesGroup findByCodeAndValid(String code,Short valid);
   

   


}
