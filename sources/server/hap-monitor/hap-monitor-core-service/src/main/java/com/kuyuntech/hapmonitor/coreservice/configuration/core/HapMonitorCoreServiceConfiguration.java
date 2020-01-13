package com.kuyuntech.hapmonitor.coreservice.configuration.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.wbspool.fastboot.core.jpa.annotation.FastbootAutoDatasource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import com.wbspool.fastboot.core.dubbo.configuration.FastbootDubboConfiguration;
import org.springframework.context.annotation.Import;





@ConditionalOnExpression("!${fastboot.comp.hap-monitor-core-service.dubbo.enable:false}")
@Configuration
@ComponentScan(basePackages = "com.kuyuntech.hapmonitor.coreservice")
@EnableJpaRepositories(repositoryBaseClass = FastbootRepositoryImpl.class, basePackages = "com.kuyuntech.hapmonitor.coreservice", transactionManagerRef = "${fastboot.service.hap-monitor-core-service.transactionManager:hapMonitorCoreServiceTransactionManager}", entityManagerFactoryRef = "${fastboot.service.hap-monitor-core-service.entityManagerFactory:hapMonitorCoreServiceEntityManagerFactory}")
@FastbootAutoDatasource(appName = "hap-monitor-core-service",entityPackages = {"com.kuyuntech.hapmonitor.coreservice"})
public class HapMonitorCoreServiceConfiguration {


    @ConditionalOnExpression("${fastboot.dubbo.enable:false}")
    @Import(FastbootDubboConfiguration.class)
    static class EnableDubbo{

    }


}
