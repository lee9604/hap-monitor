package com.kuyuntech.hapmonitor.mobile;

import com.kuyuntech.hapmonitor.coreservice.annotation.core.EnableHapMonitorCoreService;
import com.wbspool.fastboot.core.web.annotation.EnableFastbootParameterValidate;
import com.wbspool.fastboot.core.web.annotation.EnableFastbootUrlMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.wbspool.fastboot.core.jpa.annotation.EnableFastbootJpaAutoConfiguration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication
@EnableFastbootUrlMapping
@EnableFastbootParameterValidate
@EnableFastbootJpaAutoConfiguration
@EnableHapMonitorCoreService
public class HapMonitorMobileApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        SpringApplication.run(HapMonitorMobileApplication.class, args);
    }

}
