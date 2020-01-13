package com.kuyuntech.hapmonitor.mobile.configuration.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.wbspool.fastboot.core.jpa.annotation.FastbootAutoDatasource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import com.wbspool.fastboot.core.dubbo.configuration.FastbootDubboConfiguration;
import org.springframework.context.annotation.Import;


@ConditionalOnExpression("!${fastboot.comp.hap-monitor-mobile.dubbo.enable:false}")
@Configuration
@EnableJpaRepositories(repositoryBaseClass = FastbootRepositoryImpl.class, basePackages = "com.kuyuntech.hapmonitor.mobile", transactionManagerRef = "${fastboot.restful.hap-monitor-mobile.transactionManager:hapMonitorMobileTransactionManager}", entityManagerFactoryRef = "${fastboot.restful.hap-monitor-mobile.entityManagerFactory:hapMonitorMobileEntityManagerFactory}")
@FastbootAutoDatasource(appName = "hap-monitor-mobile",entityPackages = {"com.kuyuntech.hapmonitor.mobile"})
@ComponentScan(basePackages = "com.kuyuntech.hapmonitor.mobile")
public class HapMonitorMobileConfiguration {

    @ConditionalOnExpression("${fastboot.dubbo.enable:false}")
    @Import(FastbootDubboConfiguration.class)
    static class EnableDubbo{

    }



}
