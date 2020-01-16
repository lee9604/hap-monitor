package com.kuyuntech.hapmonitor.platform.mapper.core;

import com.kuyuntech.hapmonitor.platform.domain.core.DmsAlarm;
import com.kuyuntech.hapmonitor.platform.domain.core.DmsAlarmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmsAlarmMapper {
    long countByExample(DmsAlarmExample example);

    int deleteByExample(DmsAlarmExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DmsAlarm record);

    int insertSelective(DmsAlarm record);

    List<DmsAlarm> selectByExample(DmsAlarmExample example);

    DmsAlarm selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DmsAlarm record, @Param("example") DmsAlarmExample example);

    int updateByExample(@Param("record") DmsAlarm record, @Param("example") DmsAlarmExample example);

    int updateByPrimaryKeySelective(DmsAlarm record);

    int updateByPrimaryKey(DmsAlarm record);
}