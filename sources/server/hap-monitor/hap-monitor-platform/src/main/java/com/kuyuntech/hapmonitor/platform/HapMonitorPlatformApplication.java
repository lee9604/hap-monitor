package com.kuyuntech.hapmonitor.platform;

import com.kuyuntech.hapmonitor.coreservice.annotation.core.EnableHapMonitorCoreService;
import com.wbspool.fastboot.comp.app.commonrestful.annotation.core.EnableAppCompCommonRestful;
import com.wbspool.fastboot.comp.area.commonrestful.annotation.core.EnableAreaCompCommonRestful;
import com.wbspool.fastboot.comp.auth.commonrestful.annotation.core.EnableAuthCompCommonRestful;
import com.wbspool.fastboot.comp.bsnm.commonrestful.annotation.core.EnableBsnmCompCommonRestful;
import com.wbspool.fastboot.comp.dict.commonrestful.annotation.core.EnableDictCompCommonRestful;
import com.wbspool.fastboot.comp.file.commonrestful.annotation.core.EnableFileCompCommonRestful;
import com.wbspool.fastboot.comp.file.nasserver.annotation.core.EnableFileCompNasServer;
import com.wbspool.fastboot.comp.menu.commonrestful.annotation.core.EnableMenuCompCommonRestful;
import com.wbspool.fastboot.comp.org.commonrestful.annotation.core.EnableOrgCompCommonRestful;
import com.wbspool.fastboot.comp.privilege.commonrestful.annotation.core.EnablePrivilegeCompCommonRestful;
import com.wbspool.fastboot.comp.user.commonrestful.annotation.core.EnableUserCompCommonRestful;
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
@EnableUserCompCommonRestful
@EnableAreaCompCommonRestful
@EnableDictCompCommonRestful
@EnableMenuCompCommonRestful
@EnablePrivilegeCompCommonRestful
@EnableAuthCompCommonRestful
@EnableFileCompCommonRestful
@EnableFileCompNasServer
@EnableBsnmCompCommonRestful
@EnableAppCompCommonRestful
@EnableOrgCompCommonRestful
public class HapMonitorPlatformApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        SpringApplication.run(HapMonitorPlatformApplication.class, args);
    }

}
