package com.kuyuntech.hapmonitor.coreapi.service.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsResourcesGroupResourcesRelationBean;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.List;

/**
 * UmsResourcesGroupResourcesRelationService
 *
 */
public interface UmsResourcesGroupResourcesRelationService  {

    

    /**
     * 新增记录
     * @param umsResourcesGroupResourcesRelationBean 新增参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsResourcesGroupResourcesRelationBean add(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean);

    /**
     * 更新记录
     * @param umsResourcesGroupResourcesRelationBean 更新参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsResourcesGroupResourcesRelationBean update(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean);

    /**
     * 删除记录
     * @param umsResourcesGroupResourcesRelationBean 删除参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsResourcesGroupResourcesRelationBean delete(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean);

    /**
     * 查找一条记录
     * @param umsResourcesGroupResourcesRelationBean 查找参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsResourcesGroupResourcesRelationBean find(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean);

    /**
     * 通过唯一标识查找记录
     * @param code 用户标识
     * @return 记录存在返回对应的bean，否则返回null
     */
     UmsResourcesGroupResourcesRelationBean find(String code);

    /**
     * 分页查找多条记录
     * @param umsResourcesGroupResourcesRelationBean 查找参数
     * @param pagerBean  分页参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsResourcesGroupResourcesRelationBean> findAll(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean, PagerBean pagerBean);



    /**
     * 查找所有记录
     * @param umsResourcesGroupResourcesRelationBean 查找参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsResourcesGroupResourcesRelationBean> findAll(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean);


    /**
     * 统计所有记录
     * @param umsResourcesGroupResourcesRelationBean 统计参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     Long countAll(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean);

    /**
     * 分页查找多条记录
     * @param umsResourcesGroupResourcesRelationBean 查询条件参数
     * @param pagerBean 分页参数
     * @return 返回查询结果的Pager封装
     */
     PagerBean<UmsResourcesGroupResourcesRelationBean> findPager(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean, PagerBean pagerBean);

    /**
     * 批量删除
     * @param umsResourcesGroupResourcesRelationBeans 批量删除元素集合
     */
     void batchDelete(List<UmsResourcesGroupResourcesRelationBean> umsResourcesGroupResourcesRelationBeans);

    /**
     * 批量删除
     * @param codes 唯一标识集合
     */
    void batchDeleteByCodes(List<String> codes);

    

}
