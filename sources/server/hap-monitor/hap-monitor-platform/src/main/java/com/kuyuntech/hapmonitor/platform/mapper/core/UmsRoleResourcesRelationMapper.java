package com.kuyuntech.hapmonitor.platform.mapper.core;

import com.kuyuntech.hapmonitor.platform.domain.core.UmsRoleResourcesRelation;
import com.kuyuntech.hapmonitor.platform.domain.core.UmsRoleResourcesRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsRoleResourcesRelationMapper {
    long countByExample(UmsRoleResourcesRelationExample example);

    int deleteByExample(UmsRoleResourcesRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsRoleResourcesRelation record);

    int insertSelective(UmsRoleResourcesRelation record);

    List<UmsRoleResourcesRelation> selectByExample(UmsRoleResourcesRelationExample example);

    UmsRoleResourcesRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsRoleResourcesRelation record, @Param("example") UmsRoleResourcesRelationExample example);

    int updateByExample(@Param("record") UmsRoleResourcesRelation record, @Param("example") UmsRoleResourcesRelationExample example);

    int updateByPrimaryKeySelective(UmsRoleResourcesRelation record);

    int updateByPrimaryKey(UmsRoleResourcesRelation record);
}