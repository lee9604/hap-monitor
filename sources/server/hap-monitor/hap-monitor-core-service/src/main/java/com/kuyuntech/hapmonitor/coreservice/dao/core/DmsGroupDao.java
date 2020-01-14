package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsGroup;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;

import java.util.List;


/**
 * DmsGroupDao
 *
 */
public interface DmsGroupDao extends FastbootRepository<DmsGroup,Long> {

    

   
   DmsGroup findByCodeAndValid(String code,Short valid);
   
   DmsGroup findDmsGroupsById(Long id);
   


}
