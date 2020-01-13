package com.kuyuntech.hapmonitor.coreapi.service.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserGroupRelationBean;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.List;

/**
 * UmsUserGroupRelationService
 *
 */
public interface UmsUserGroupRelationService  {

    

    /**
     * 新增记录
     * @param umsUserGroupRelationBean 新增参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsUserGroupRelationBean add(UmsUserGroupRelationBean umsUserGroupRelationBean);

    /**
     * 更新记录
     * @param umsUserGroupRelationBean 更新参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsUserGroupRelationBean update(UmsUserGroupRelationBean umsUserGroupRelationBean);

    /**
     * 删除记录
     * @param umsUserGroupRelationBean 删除参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsUserGroupRelationBean delete(UmsUserGroupRelationBean umsUserGroupRelationBean);

    /**
     * 查找一条记录
     * @param umsUserGroupRelationBean 查找参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsUserGroupRelationBean find(UmsUserGroupRelationBean umsUserGroupRelationBean);

    /**
     * 通过唯一标识查找记录
     * @param code 用户标识
     * @return 记录存在返回对应的bean，否则返回null
     */
     UmsUserGroupRelationBean find(String code);

    /**
     * 分页查找多条记录
     * @param umsUserGroupRelationBean 查找参数
     * @param pagerBean  分页参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsUserGroupRelationBean> findAll(UmsUserGroupRelationBean umsUserGroupRelationBean, PagerBean pagerBean);



    /**
     * 查找所有记录
     * @param umsUserGroupRelationBean 查找参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsUserGroupRelationBean> findAll(UmsUserGroupRelationBean umsUserGroupRelationBean);


    /**
     * 统计所有记录
     * @param umsUserGroupRelationBean 统计参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     Long countAll(UmsUserGroupRelationBean umsUserGroupRelationBean);

    /**
     * 分页查找多条记录
     * @param umsUserGroupRelationBean 查询条件参数
     * @param pagerBean 分页参数
     * @return 返回查询结果的Pager封装
     */
     PagerBean<UmsUserGroupRelationBean> findPager(UmsUserGroupRelationBean umsUserGroupRelationBean, PagerBean pagerBean);

    /**
     * 批量删除
     * @param umsUserGroupRelationBeans 批量删除元素集合
     */
     void batchDelete(List<UmsUserGroupRelationBean> umsUserGroupRelationBeans);

    /**
     * 批量删除
     * @param codes 唯一标识集合
     */
    void batchDeleteByCodes(List<String> codes);

    

}
