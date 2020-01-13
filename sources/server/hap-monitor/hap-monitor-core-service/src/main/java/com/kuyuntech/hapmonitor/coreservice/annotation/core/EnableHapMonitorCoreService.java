package com.kuyuntech.hapmonitor.coreservice.annotation.core;

import com.kuyuntech.hapmonitor.coreservice.configuration.core.HapMonitorCoreServiceConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HapMonitorCoreServiceConfiguration.class)
public @interface EnableHapMonitorCoreService {

}
