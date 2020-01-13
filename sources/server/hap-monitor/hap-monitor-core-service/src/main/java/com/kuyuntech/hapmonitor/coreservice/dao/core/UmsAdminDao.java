package com.kuyuntech.hapmonitor.coreservice.dao.core;

import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsAdmin;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepository;




/**
 * UmsAdminDao
 *
 */
public interface UmsAdminDao extends FastbootRepository<UmsAdmin,Long> {

    

   
   UmsAdmin findByCodeAndValid(String code,Short valid);
   
   UmsAdmin findByUsernameAndPassword(String username, String password);
   


}
