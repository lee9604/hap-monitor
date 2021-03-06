package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsResources;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * UmsResourcesDao
 *
 */
public interface UmsResourcesDao extends FastbootRepository<UmsResources,Long> {

    

   
   UmsResources findByCodeAndValid(String code,Short valid);
   
   List<UmsResources> findUmsResourcesByIdIn(List<Long> ids);

}
