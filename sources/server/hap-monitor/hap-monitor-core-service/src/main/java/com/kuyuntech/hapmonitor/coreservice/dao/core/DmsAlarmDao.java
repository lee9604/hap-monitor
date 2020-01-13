package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsAlarm;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;




/**
 * DmsAlarmDao
 *
 */
public interface DmsAlarmDao extends FastbootRepository<DmsAlarm,Long> {

    

   
   DmsAlarm findByCodeAndValid(String code,Short valid);
   

   


}
