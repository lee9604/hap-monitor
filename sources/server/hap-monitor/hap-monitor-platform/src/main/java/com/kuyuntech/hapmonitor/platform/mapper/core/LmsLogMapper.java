package com.kuyuntech.hapmonitor.platform.mapper.core;

import com.kuyuntech.hapmonitor.platform.domain.core.LmsLog;
import com.kuyuntech.hapmonitor.platform.domain.core.LmsLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LmsLogMapper {
    long countByExample(LmsLogExample example);

    int deleteByExample(LmsLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LmsLog record);

    int insertSelective(LmsLog record);

    List<LmsLog> selectByExample(LmsLogExample example);

    LmsLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LmsLog record, @Param("example") LmsLogExample example);

    int updateByExample(@Param("record") LmsLog record, @Param("example") LmsLogExample example);

    int updateByPrimaryKeySelective(LmsLog record);

    int updateByPrimaryKey(LmsLog record);
}