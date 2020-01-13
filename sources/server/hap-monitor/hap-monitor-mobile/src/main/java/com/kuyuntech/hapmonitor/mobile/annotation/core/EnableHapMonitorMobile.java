package com.kuyuntech.hapmonitor.mobile.annotation.core;

import com.kuyuntech.hapmonitor.mobile.configuration.core.HapMonitorMobileConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HapMonitorMobileConfiguration.class)
public @interface EnableHapMonitorMobile {

}
