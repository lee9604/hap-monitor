package com.kuyuntech.hapmonitor.platform.mapper.core;

import com.kuyuntech.hapmonitor.platform.domain.core.UmsResources;
import com.kuyuntech.hapmonitor.platform.domain.core.UmsResourcesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsResourcesMapper {
    long countByExample(UmsResourcesExample example);

    int deleteByExample(UmsResourcesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsResources record);

    int insertSelective(UmsResources record);

    List<UmsResources> selectByExample(UmsResourcesExample example);

    UmsResources selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsResources record, @Param("example") UmsResourcesExample example);

    int updateByExample(@Param("record") UmsResources record, @Param("example") UmsResourcesExample example);

    int updateByPrimaryKeySelective(UmsResources record);

    int updateByPrimaryKey(UmsResources record);
}