package com.kuyuntech.hapmonitor.platform.log.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.LmsLogBean;

import java.util.Date;

public class UpdateCameraLog implements LogHandler {
    @Override
    public void executeRecord(LmsLogBean lmsLogBean, String targetName) {
        lmsLogBean.setOperationTime(new Date(System.currentTimeMillis()));
        lmsLogBean.setInfo("编辑设备");
        lmsLogBean.setTarget(targetName);
    }
}
