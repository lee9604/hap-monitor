package com.kuyuntech.hapmonitor.platform.log.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.LmsLogBean;

public class LogContext {
    private LogHandler logHandler;

    public LogContext(LogHandler logHandler) {
        this.logHandler = logHandler;
    }

    public void executeLog(LmsLogBean lmsLogBean, String targetName) {
        logHandler.executeRecord(lmsLogBean, targetName);
    }
}
