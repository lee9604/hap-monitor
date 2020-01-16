package com.kuyuntech.hapmonitor.platform.log.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.LmsLogBean;

import java.util.Date;

public class AddGroupLog implements LogHandler {
    @Override
    public void executeRecord(LmsLogBean lmsLogBean, String targetName) {
        lmsLogBean.setOperationTime(new Date(System.currentTimeMillis()));
        lmsLogBean.setInfo("添加分组");
        lmsLogBean.setTarget(targetName);
    }
}
