package com.kuyuntech.hapmonitor.coreservice.domain.core;

import com.wbspool.fastboot.core.jpa.entiy.FastbootEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class LmsLog extends FastbootEntity {
    // 操作者
    private String operator;
    // 描述信息
    private String info;
    // 被操作对象
    private String target;
}
