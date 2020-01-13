package com.kuyuntech.hapmonitor.corerestful.annotation.core;

import com.kuyuntech.hapmonitor.corerestful.configuration.core.HapMonitorCoreRestfulConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HapMonitorCoreRestfulConfiguration.class)
public @interface EnableHapMonitorCoreRestful {

}
