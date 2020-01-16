package com.kuyuntech.hapmonitor.platform.log.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.LmsLogBean;

public interface LogHandler {
    void executeRecord(LmsLogBean lmsLogBean, String targetName);
}
