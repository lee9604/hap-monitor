package com.kuyuntech.hapmonitor.platform.log.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.LmsLogBean;

import java.util.HashMap;
import java.util.Map;

public class LogFactory {

    private static Map<String, LogHandler> map = new HashMap<>();

    private static final LogHandler EMPTY = new EmptyLogHandler();

    static {
        map.put("/umsUser/login", new LoginLog());
        map.put("/umsUser/logout", new LogoutLog());
        map.put("/umsUser/add", new AddUserLog());
        map.put("/umsUser/update", new UpdateUserLog());
        map.put("/umsUser/delete", new DeleteUserLog());

        map.put("/dmsCamera/add", new AddCameraLog());
        map.put("/dmsCamera/update", new UpdateCameraLog());
        map.put("/dmsCamera/delete", new DeleteCameraLog());

        map.put("/dmsGroup/add", new AddGroupLog());
        map.put("/dmsGroup/update", new UpdateGroupLog());
        map.put("/dmsGroup/delete", new DeleteGroupLog());

        map.put("/dmsAlarm/delete", new DeleteAlarmLog());
    }

    public static LogHandler getInstance(String url) {
        LogHandler logHandler = map.get(url);
        return logHandler == null ? EMPTY : logHandler;
    }

    private static class EmptyLogHandler implements LogHandler {
        @Override
        public void executeRecord(LmsLogBean lmsLogBean, String targetName) {

        }
    }
}
