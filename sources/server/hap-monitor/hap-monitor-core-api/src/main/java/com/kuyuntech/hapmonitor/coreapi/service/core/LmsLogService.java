package com.kuyuntech.hapmonitor.coreapi.service.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.LmsLogBean;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.List;

/**
 * LmsLogService
 *
 */
public interface LmsLogService  {

    

    /**
     * 新增记录
     * @param lmsLogBean 新增参数
     * @return 如果成功返回对应bean，否则返回null
     */
     LmsLogBean add(LmsLogBean lmsLogBean);

    /**
     * 更新记录
     * @param lmsLogBean 更新参数
     * @return 如果成功返回对应bean，否则返回null
     */
     LmsLogBean update(LmsLogBean lmsLogBean);

    /**
     * 删除记录
     * @param lmsLogBean 删除参数
     * @return 如果成功返回对应bean，否则返回null
     */
    LmsLogBean delete(LmsLogBean lmsLogBean);

    /**
     * 查找一条记录
     * @param lmsLogBean 查找参数
     * @return 如果成功返回对应bean，否则返回null
     */
    LmsLogBean find(LmsLogBean lmsLogBean);

    /**
     * 通过唯一标识查找记录
     * @param code 用户标识
     * @return 记录存在返回对应的bean，否则返回null
     */
     LmsLogBean find(String code);

    /**
     * 分页查找多条记录
     * @param lmsLogBean 查找参数
     * @param pagerBean  分页参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<LmsLogBean> findAll(LmsLogBean lmsLogBean, PagerBean pagerBean);



    /**
     * 查找所有记录
     * @param lmsLogBean 查找参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<LmsLogBean> findAll(LmsLogBean lmsLogBean);


    /**
     * 统计所有记录
     * @param lmsLogBean 统计参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     Long countAll(LmsLogBean lmsLogBean);

    /**
     * 分页查找多条记录
     * @param lmsLogBean 查询条件参数
     * @param pagerBean 分页参数
     * @return 返回查询结果的Pager封装
     */
     PagerBean<LmsLogBean> findPager(LmsLogBean lmsLogBean, PagerBean pagerBean);

    /**
     * 批量删除
     * @param lmsLogBeans 批量删除元素集合
     */
     void batchDelete(List<LmsLogBean> lmsLogBeans);

    /**
     * 批量删除
     * @param codes 唯一标识集合
     */
    void batchDeleteByCodes(List<String> codes);

    

}
