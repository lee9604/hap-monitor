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
public class UmsResourcesGroupResourcesRelation extends FastbootEntity {
    private Long resourcesGroupId;
    private Long resourcesId;
}
