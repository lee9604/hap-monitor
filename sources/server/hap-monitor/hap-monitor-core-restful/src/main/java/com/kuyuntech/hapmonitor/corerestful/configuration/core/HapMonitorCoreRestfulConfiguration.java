package com.kuyuntech.hapmonitor.corerestful.configuration.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.wbspool.fastboot.core.jpa.annotation.FastbootAutoDatasource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import com.wbspool.fastboot.core.dubbo.configuration.FastbootDubboConfiguration;
import org.springframework.context.annotation.Import;


@ConditionalOnExpression("!${fastboot.comp.hap-monitor-core-restful.dubbo.enable:false}")
@Configuration
@EnableJpaRepositories(repositoryBaseClass = FastbootRepositoryImpl.class, basePackages = "com.kuyuntech.hapmonitor.corerestful", transactionManagerRef = "${fastboot.restful.hap-monitor-core-restful.transactionManager:hapMonitorCoreRestfulTransactionManager}", entityManagerFactoryRef = "${fastboot.restful.hap-monitor-core-restful.entityManagerFactory:hapMonitorCoreRestfulEntityManagerFactory}")
@FastbootAutoDatasource(appName = "hap-monitor-core-restful",entityPackages = {"com.kuyuntech.hapmonitor.corerestful"})
@ComponentScan(basePackages = "com.kuyuntech.hapmonitor.corerestful")
public class HapMonitorCoreRestfulConfiguration {

    @ConditionalOnExpression("${fastboot.dubbo.enable:false}")
    @Import(FastbootDubboConfiguration.class)
    static class EnableDubbo{

    }



}
