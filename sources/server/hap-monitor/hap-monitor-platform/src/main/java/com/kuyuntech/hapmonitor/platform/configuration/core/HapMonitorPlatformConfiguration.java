package com.kuyuntech.hapmonitor.platform.configuration.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.wbspool.fastboot.core.jpa.repository.FastbootRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.wbspool.fastboot.core.jpa.annotation.FastbootAutoDatasource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import com.wbspool.fastboot.core.dubbo.configuration.FastbootDubboConfiguration;
import org.springframework.context.annotation.Import;


@ConditionalOnExpression("!${fastboot.comp.hap-monitor-platform.dubbo.enable:false}")
@Configuration
@EnableJpaRepositories(repositoryBaseClass = FastbootRepositoryImpl.class, basePackages = "com.kuyuntech.hapmonitor.platform", transactionManagerRef = "${fastboot.restful.hap-monitor-platform.transactionManager:hapMonitorPlatformTransactionManager}", entityManagerFactoryRef = "${fastboot.restful.hap-monitor-platform.entityManagerFactory:hapMonitorPlatformEntityManagerFactory}")
@FastbootAutoDatasource(appName = "hap-monitor-platform",entityPackages = {"com.kuyuntech.hapmonitor.platform"})
@ComponentScan(basePackages = "com.kuyuntech.hapmonitor.platform")
public class HapMonitorPlatformConfiguration {

    @ConditionalOnExpression("${fastboot.dubbo.enable:false}")
    @Import(FastbootDubboConfiguration.class)
    static class EnableDubbo{

    }



}
