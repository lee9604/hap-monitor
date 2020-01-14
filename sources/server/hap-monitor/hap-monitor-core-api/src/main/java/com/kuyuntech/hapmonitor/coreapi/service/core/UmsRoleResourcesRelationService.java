package com.kuyuntech.hapmonitor.coreapi.service.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsRoleResourcesRelationBean;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.List;

/**
 * UmsRoleResourcesRelationService
 *
 */
public interface UmsRoleResourcesRelationService  {

    

    /**
     * 新增记录
     * @param umsRoleResourcesRelationBean 新增参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsRoleResourcesRelationBean add(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean);

    /**
     * 更新记录
     * @param umsRoleResourcesRelationBean 更新参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsRoleResourcesRelationBean update(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean);

    /**
     * 删除记录
     * @param umsRoleResourcesRelationBean 删除参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsRoleResourcesRelationBean delete(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean);

    /**
     * 查找一条记录
     * @param umsRoleResourcesRelationBean 查找参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsRoleResourcesRelationBean find(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean);

    /**
     * 通过唯一标识查找记录
     * @param code 用户标识
     * @return 记录存在返回对应的bean，否则返回null
     */
     UmsRoleResourcesRelationBean find(String code);

    /**
     * 分页查找多条记录
     * @param umsRoleResourcesRelationBean 查找参数
     * @param pagerBean  分页参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsRoleResourcesRelationBean> findAll(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean, PagerBean pagerBean);



    /**
     * 查找所有记录
     * @param umsRoleResourcesRelationBean 查找参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsRoleResourcesRelationBean> findAll(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean);


    /**
     * 统计所有记录
     * @param umsRoleResourcesRelationBean 统计参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     Long countAll(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean);

    /**
     * 分页查找多条记录
     * @param umsRoleResourcesRelationBean 查询条件参数
     * @param pagerBean 分页参数
     * @return 返回查询结果的Pager封装
     */
     PagerBean<UmsRoleResourcesRelationBean> findPager(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean, PagerBean pagerBean);

    /**
     * 批量删除
     * @param umsRoleResourcesRelationBeans 批量删除元素集合
     */
     void batchDelete(List<UmsRoleResourcesRelationBean> umsRoleResourcesRelationBeans);

    /**
     * 批量删除
     * @param codes 唯一标识集合
     */
    void batchDeleteByCodes(List<String> codes);


    List<UmsRoleResourcesRelationBean> findUmsRoleResourcesRelationsByRoleId(Long roleId);
}
