package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsCamera;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;




/**
 * DmsCameraDao
 *
 */
public interface DmsCameraDao extends FastbootRepository<DmsCamera,Long> {

    

   
   DmsCamera findByCodeAndValid(String code,Short valid);
   

   


}
