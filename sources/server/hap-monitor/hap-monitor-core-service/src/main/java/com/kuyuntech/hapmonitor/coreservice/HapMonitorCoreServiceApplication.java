package com.kuyuntech.hapmonitor.coreservice;

import com.kuyuntech.hapmonitor.coreservice.annotation.core.EnableHapMonitorCoreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.wbspool.fastboot.core.jpa.annotation.EnableFastbootJpaAutoConfiguration;



@SpringBootApplication
@EnableHapMonitorCoreService
@EnableFastbootJpaAutoConfiguration
public class HapMonitorCoreServiceApplication {

    public static void main(String[] args){
        SpringApplication.run(HapMonitorCoreServiceApplication.class,args);
    }
}
