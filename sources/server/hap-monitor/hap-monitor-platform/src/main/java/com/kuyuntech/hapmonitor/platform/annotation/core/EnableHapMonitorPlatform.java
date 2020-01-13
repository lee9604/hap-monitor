package com.kuyuntech.hapmonitor.platform.annotation.core;

import com.kuyuntech.hapmonitor.platform.configuration.core.HapMonitorPlatformConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HapMonitorPlatformConfiguration.class)
public @interface EnableHapMonitorPlatform {

}
