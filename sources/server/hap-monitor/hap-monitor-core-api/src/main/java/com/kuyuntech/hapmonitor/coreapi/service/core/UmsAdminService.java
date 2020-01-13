package com.kuyuntech.hapmonitor.coreapi.service.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsAdminBean;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.List;

/**
 * UmsAdminService
 *
 */
public interface UmsAdminService  {

    

    /**
     * 新增记录
     * @param umsAdminBean 新增参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsAdminBean add(UmsAdminBean umsAdminBean);

    /**
     * 更新记录
     * @param umsAdminBean 更新参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsAdminBean update(UmsAdminBean umsAdminBean);

    /**
     * 删除记录
     * @param umsAdminBean 删除参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsAdminBean delete(UmsAdminBean umsAdminBean);

    /**
     * 查找一条记录
     * @param umsAdminBean 查找参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsAdminBean find(UmsAdminBean umsAdminBean);

    /**
     * 通过唯一标识查找记录
     * @param code 用户标识
     * @return 记录存在返回对应的bean，否则返回null
     */
     UmsAdminBean find(String code);

    /**
     * 分页查找多条记录
     * @param umsAdminBean 查找参数
     * @param pagerBean  分页参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsAdminBean> findAll(UmsAdminBean umsAdminBean, PagerBean pagerBean);



    /**
     * 查找所有记录
     * @param umsAdminBean 查找参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsAdminBean> findAll(UmsAdminBean umsAdminBean);


    /**
     * 统计所有记录
     * @param umsAdminBean 统计参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     Long countAll(UmsAdminBean umsAdminBean);

    /**
     * 分页查找多条记录
     * @param umsAdminBean 查询条件参数
     * @param pagerBean 分页参数
     * @return 返回查询结果的Pager封装
     */
     PagerBean<UmsAdminBean> findPager(UmsAdminBean umsAdminBean, PagerBean pagerBean);

    /**
     * 批量删除
     * @param umsAdminBeans 批量删除元素集合
     */
     void batchDelete(List<UmsAdminBean> umsAdminBeans);

    /**
     * 批量删除
     * @param codes 唯一标识集合
     */
    void batchDeleteByCodes(List<String> codes);


    UmsAdminBean findByUmsAdminBean(UmsAdminBean umsAdminBean);
}
