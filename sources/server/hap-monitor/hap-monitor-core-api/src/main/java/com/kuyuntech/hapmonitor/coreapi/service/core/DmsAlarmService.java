package com.kuyuntech.hapmonitor.coreapi.service.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.DmsAlarmBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.List;

/**
 * DmsAlarmService
 *
 */
public interface DmsAlarmService  {

    

    /**
     * 新增记录
     * @param dmsAlarmBean 新增参数
     * @return 如果成功返回对应bean，否则返回null
     */
     DmsAlarmBean add(DmsAlarmBean dmsAlarmBean);

    /**
     * 更新记录
     * @param dmsAlarmBean 更新参数
     * @return 如果成功返回对应bean，否则返回null
     */
     DmsAlarmBean update(DmsAlarmBean dmsAlarmBean);

    /**
     * 删除记录
     * @param dmsAlarmBean 删除参数
     * @return 如果成功返回对应bean，否则返回null
     */
    DmsAlarmBean delete(DmsAlarmBean dmsAlarmBean);

    /**
     * 查找一条记录
     * @param dmsAlarmBean 查找参数
     * @return 如果成功返回对应bean，否则返回null
     */
    DmsAlarmBean find(DmsAlarmBean dmsAlarmBean);

    /**
     * 通过唯一标识查找记录
     * @param code 用户标识
     * @return 记录存在返回对应的bean，否则返回null
     */
     DmsAlarmBean find(String code);

    /**
     * 分页查找多条记录
     * @param dmsAlarmBean 查找参数
     * @param pagerBean  分页参数
     * @param umsUserBean
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<DmsAlarmBean> findAll(DmsAlarmBean dmsAlarmBean, PagerBean pagerBean, UmsUserBean umsUserBean);



    /**
     * 查找所有记录
     * @param dmsAlarmBean 查找参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<DmsAlarmBean> findAll(DmsAlarmBean dmsAlarmBean,UmsUserBean umsUserBean);


    /**
     * 统计所有记录
     * @param dmsAlarmBean 统计参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     Long countAll(DmsAlarmBean dmsAlarmBean);

    /**
     * 分页查找多条记录
     * @param dmsAlarmBean 查询条件参数
     * @param pagerBean 分页参数
     * @param umsUserBean
     * @return 返回查询结果的Pager封装
     */
     PagerBean<DmsAlarmBean> findPager(DmsAlarmBean dmsAlarmBean, PagerBean pagerBean, UmsUserBean umsUserBean);

    /**
     * 批量删除
     * @param dmsAlarmBeans 批量删除元素集合
     */
     void batchDelete(List<DmsAlarmBean> dmsAlarmBeans);

    /**
     * 批量删除
     * @param codes 唯一标识集合
     */
    void batchDeleteByCodes(List<String> codes);


}
