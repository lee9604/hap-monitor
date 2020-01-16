package com.kuyuntech.hapmonitor.platform.mapper.core;

import com.kuyuntech.hapmonitor.platform.domain.core.UmsUserGroupRelation;
import com.kuyuntech.hapmonitor.platform.domain.core.UmsUserGroupRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsUserGroupRelationMapper {
    long countByExample(UmsUserGroupRelationExample example);

    int deleteByExample(UmsUserGroupRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsUserGroupRelation record);

    int insertSelective(UmsUserGroupRelation record);

    List<UmsUserGroupRelation> selectByExample(UmsUserGroupRelationExample example);

    UmsUserGroupRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsUserGroupRelation record, @Param("example") UmsUserGroupRelationExample example);

    int updateByExample(@Param("record") UmsUserGroupRelation record, @Param("example") UmsUserGroupRelationExample example);

    int updateByPrimaryKeySelective(UmsUserGroupRelation record);

    int updateByPrimaryKey(UmsUserGroupRelation record);
}