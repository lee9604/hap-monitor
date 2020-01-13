package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.LmsLog;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;




/**
 * LmsLogDao
 *
 */
public interface LmsLogDao extends FastbootRepository<LmsLog,Long> {

    

   
   LmsLog findByCodeAndValid(String code,Short valid);
   

   


}
