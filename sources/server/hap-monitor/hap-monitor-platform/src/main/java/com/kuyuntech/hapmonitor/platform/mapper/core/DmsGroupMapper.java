package com.kuyuntech.hapmonitor.platform.mapper.core;

import com.kuyuntech.hapmonitor.platform.domain.core.DmsGroup;
import com.kuyuntech.hapmonitor.platform.domain.core.DmsGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmsGroupMapper {
    long countByExample(DmsGroupExample example);

    int deleteByExample(DmsGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DmsGroup record);

    int insertSelective(DmsGroup record);

    List<DmsGroup> selectByExample(DmsGroupExample example);

    DmsGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DmsGroup record, @Param("example") DmsGroupExample example);

    int updateByExample(@Param("record") DmsGroup record, @Param("example") DmsGroupExample example);

    int updateByPrimaryKeySelective(DmsGroup record);

    int updateByPrimaryKey(DmsGroup record);
}