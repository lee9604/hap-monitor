package com.kuyuntech.hapmonitor.coreapi.service.core;

import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsAdminBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.List;

/**
 * UmsUserService
 *
 */
public interface UmsUserService  {

    

    /**
     * 新增记录
     * @param umsUserBean 新增参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsUserBean add(UmsUserBean umsUserBean);

    /**
     * 更新记录
     * @param umsUserBean 更新参数
     * @return 如果成功返回对应bean，否则返回null
     */
     UmsUserBean update(UmsUserBean umsUserBean);

    /**
     * 删除记录
     * @param umsUserBean 删除参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsUserBean delete(UmsUserBean umsUserBean);

    /**
     * 查找一条记录
     * @param umsUserBean 查找参数
     * @return 如果成功返回对应bean，否则返回null
     */
    UmsUserBean find(UmsUserBean umsUserBean);

    /**
     * 通过唯一标识查找记录
     * @param code 用户标识
     * @return 记录存在返回对应的bean，否则返回null
     */
     UmsUserBean find(String code);

    /**
     * 分页查找多条记录
     * @param umsUserBean 查找参数
     * @param pagerBean  分页参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsUserBean> findAll(UmsUserBean umsUserBean, PagerBean pagerBean);



    /**
     * 查找所有记录
     * @param umsUserBean 查找参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     List<UmsUserBean> findAll(UmsUserBean umsUserBean);


    /**
     * 统计所有记录
     * @param umsUserBean 统计参数
     * @return 记录存在返回对应的bean集合，否则返回空集合
     */
     Long countAll(UmsUserBean umsUserBean);

    /**
     * 分页查找多条记录
     * @param umsUserBean 查询条件参数
     * @param pagerBean 分页参数
     * @return 返回查询结果的Pager封装
     */
     PagerBean<UmsUserBean> findPager(UmsUserBean umsUserBean, PagerBean pagerBean);

    /**
     * 批量删除
     * @param umsUserBeans 批量删除元素集合
     */
     void batchDelete(List<UmsUserBean> umsUserBeans);

    /**
     * 批量删除
     * @param codes 唯一标识集合
     */
    void batchDeleteByCodes(List<String> codes);


    PagerBean<UmsUserBean> findPagerForUmsAdmin(UmsUserBean umsUserBean, PagerBean pagerBean);

    void checkPassword(String password);

    UmsUserBean findByUmsAdminBean(UmsUserBean umsUserBean);
}
