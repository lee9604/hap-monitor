package com.kuyuntech.hapmonitor.platform.mapper.core;

import com.kuyuntech.hapmonitor.platform.domain.core.DmsCamera;
import com.kuyuntech.hapmonitor.platform.domain.core.DmsCameraExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmsCameraMapper {
    long countByExample(DmsCameraExample example);

    int deleteByExample(DmsCameraExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DmsCamera record);

    int insertSelective(DmsCamera record);

    List<DmsCamera> selectByExample(DmsCameraExample example);

    DmsCamera selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DmsCamera record, @Param("example") DmsCameraExample example);

    int updateByExample(@Param("record") DmsCamera record, @Param("example") DmsCameraExample example);

    int updateByPrimaryKeySelective(DmsCamera record);

    int updateByPrimaryKey(DmsCamera record);
}