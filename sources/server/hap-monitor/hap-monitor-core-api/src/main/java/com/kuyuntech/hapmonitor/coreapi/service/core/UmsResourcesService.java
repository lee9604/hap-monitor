package com.kuyuntech.hapmonitor.coreapi.service.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsResourcesBean;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.List;

/**
 * UmsResourcesService
 *
 */
public interface UmsResourcesService  {

    

    /**
     * 新增记录
     * @param umsResourcesBean 新增参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsResourcesBean add(UmsResourcesBean umsResourcesBean);

    /**
     * 更新记录
     * @param umsResourcesBean 更新参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsResourcesBean update(UmsResourcesBean umsResourcesBean);

    /**
     * 删除记录
     * @param umsResourcesBean 删除参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsResourcesBean delete(UmsResourcesBean umsResourcesBean);

    /**
     * 查找一条记录
     * @param umsResourcesBean 查找参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsResourcesBean find(UmsResourcesBean umsResourcesBean);

    /**
     * 通过唯一标识查找记录
     * @param code 用户标识
     * @return 记录存在返回对应的bean，否则返回null
     */
     UmsResourcesBean find(String code);

    /**
     * 分页查找多条记录
     * @param umsResourcesBean 查找参数
     * @param pagerBean  分页参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsResourcesBean> findAll(UmsResourcesBean umsResourcesBean, PagerBean pagerBean);



    /**
     * 查找所有记录
     * @param umsResourcesBean 查找参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsResourcesBean> findAll(UmsResourcesBean umsResourcesBean);


    /**
     * 统计所有记录
     * @param umsResourcesBean 统计参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     Long countAll(UmsResourcesBean umsResourcesBean);

    /**
     * 分页查找多条记录
     * @param umsResourcesBean 查询条件参数
     * @param pagerBean 分页参数
     * @return 返回查询结果的Pager封装
     */
     PagerBean<UmsResourcesBean> findPager(UmsResourcesBean umsResourcesBean, PagerBean pagerBean);

    /**
     * 批量删除
     * @param umsResourcesBeans 批量删除元素集合
     */
     void batchDelete(List<UmsResourcesBean> umsResourcesBeans);

    /**
     * 批量删除
     * @param codes 唯一标识集合
     */
    void batchDeleteByCodes(List<String> codes);


    List<UmsResourcesBean> findUmsResourcesByIdIn(List<Long> resourcesIdList);
}
