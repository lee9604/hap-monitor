package com.kuyuntech.hapmonitor.corerestful;

import com.wbspool.fastboot.core.web.annotation.EnableFastbootParameterValidate;
import com.wbspool.fastboot.core.web.annotation.EnableFastbootUrlMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.wbspool.fastboot.core.jpa.annotation.EnableFastbootJpaAutoConfiguration;
import com.kuyuntech.hapmonitor.coreservice.annotation.core.EnableHapMonitorCoreService;




@SpringBootApplication
@EnableFastbootUrlMapping
@EnableFastbootParameterValidate
@EnableFastbootJpaAutoConfiguration
@EnableHapMonitorCoreService
public class HapMonitorCoreRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(HapMonitorCoreRestfulApplication.class, args);
    }

}
