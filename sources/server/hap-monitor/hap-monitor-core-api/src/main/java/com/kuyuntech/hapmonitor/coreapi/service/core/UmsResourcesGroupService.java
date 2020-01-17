package com.kuyuntech.hapmonitor.coreapi.service.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsResourcesGroupBean;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.List;

/**
 * UmsResourcesGroupService
 *
 */
public interface UmsResourcesGroupService  {

    

    /**
     * 新增记录
     * @param umsResourcesGroupBean 新增参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsResourcesGroupBean add(UmsResourcesGroupBean umsResourcesGroupBean);

    /**
     * 更新记录
     * @param umsResourcesGroupBean 更新参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsResourcesGroupBean update(UmsResourcesGroupBean umsResourcesGroupBean);

    /**
     * 删除记录
     * @param umsResourcesGroupBean 删除参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsResourcesGroupBean delete(UmsResourcesGroupBean umsResourcesGroupBean);

    /**
     * 查找一条记录
     * @param umsResourcesGroupBean 查找参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsResourcesGroupBean find(UmsResourcesGroupBean umsResourcesGroupBean);

    /**
     * 通过唯一标识查找记录
     * @param code 用户标识
     * @return 记录存在返回对应的bean，否则返回null
     */
     UmsResourcesGroupBean find(String code);

    /**
     * 分页查找多条记录
     * @param umsResourcesGroupBean 查找参数
     * @param pagerBean  分页参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsResourcesGroupBean> findAll(UmsResourcesGroupBean umsResourcesGroupBean, PagerBean pagerBean);



    /**
     * 查找所有记录
     * @param umsResourcesGroupBean 查找参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsResourcesGroupBean> findAll(UmsResourcesGroupBean umsResourcesGroupBean);


    /**
     * 统计所有记录
     * @param umsResourcesGroupBean 统计参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     Long countAll(UmsResourcesGroupBean umsResourcesGroupBean);

    /**
     * 分页查找多条记录
     * @param umsResourcesGroupBean 查询条件参数
     * @param pagerBean 分页参数
     * @return 返回查询结果的Pager封装
     */
     PagerBean<UmsResourcesGroupBean> findPager(UmsResourcesGroupBean umsResourcesGroupBean, PagerBean pagerBean);

    /**
     * 批量删除
     * @param umsResourcesGroupBeans 批量删除元素集合
     */
     void batchDelete(List<UmsResourcesGroupBean> umsResourcesGroupBeans);

    /**
     * 批量删除
     * @param codes 唯一标识集合
     */
    void batchDeleteByCodes(List<String> codes);

    

}
