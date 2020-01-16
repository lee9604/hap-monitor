package com.kuyuntech.hapmonitor.platform.domain.core;

import java.util.Date;

public class DmsAlarm {
    private Long id;

    private String code;

    private Date createTime;

    private Date updateTime;

    private Short valid;

    private Long version;

    private Long cameraId;

    private String cameraName;

    private String cameraNum;

    private String cameraPosition;

    private Long groupId;

    private Short state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getValid() {
        return valid;
    }

    public void setValid(Short valid) {
        this.valid = valid;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getCameraId() {
        return cameraId;
    }

    public void setCameraId(Long cameraId) {
        this.cameraId = cameraId;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName == null ? null : cameraName.trim();
    }

    public String getCameraNum() {
        return cameraNum;
    }

    public void setCameraNum(String cameraNum) {
        this.cameraNum = cameraNum == null ? null : cameraNum.trim();
    }

    public String getCameraPosition() {
        return cameraPosition;
    }

    public void setCameraPosition(String cameraPosition) {
        this.cameraPosition = cameraPosition == null ? null : cameraPosition.trim();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }
}