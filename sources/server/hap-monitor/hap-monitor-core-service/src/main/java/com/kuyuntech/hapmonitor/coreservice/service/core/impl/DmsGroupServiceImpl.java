package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.bean.core.DmsCameraSimpleBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.DmsGroupBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserGroupRelationBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.DmsGroupService;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserGroupRelationService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.DmsCameraDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.DmsGroupDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsUserGroupRelationDao;
import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsCamera;
import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsGroup;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUserGroupRelation;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import com.wbspool.fastboot.core.jpa.service.AbstractFastbootService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.INVALID;
import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.VALID;


/**
 * DmsGroupService
 */
@Service("dmsGroupService")
@Transactional(rollbackFor = Exception.class, transactionManager = "hapMonitorCoreServiceTransactionManager")
public class DmsGroupServiceImpl extends AbstractFastbootService<DmsGroup, DmsGroupBean> implements DmsGroupService {

    private static final Logger logger = LoggerFactory.getLogger(DmsGroupServiceImpl.class);

    @Autowired
    DmsGroupDao dmsGroupDao;

    @Autowired
    UmsUserGroupRelationDao umsUserGroupRelationDao;

    @Autowired
    DmsCameraDao dmsCameraDao;

    @Autowired
    UmsUserGroupRelationService umsUserGroupRelationService;


    @Override
    @Transactional
    public DmsGroupBean add(DmsGroupBean dmsGroupBean, UmsUserBean umsUserBean) {


        DmsGroup dmsGroup = new DmsGroup();

        // 初始化
        dmsGroupBean.setQuantity(0);

        beanToDomain(dmsGroupBean, dmsGroup, "id");
        DmsGroup dmsGroupTemp = dmsGroupDao.save(dmsGroup);

//        UmsUserGroupRelationBean umsUserGroupRelationBean = new UmsUserGroupRelationBean();
//        umsUserGroupRelationBean.setUserId(umsUserBean.getId());
//        umsUserGroupRelationBean.setGroupId(dmsGroupTemp.getId());
//        umsUserGroupRelationService.add(umsUserGroupRelationBean);

        domainToBean(dmsGroup, dmsGroupBean);
        return dmsGroupBean;

    }

    @Override
    public DmsGroupBean update(DmsGroupBean dmsGroupBean) {

        if (dmsGroupBean == null) {
            return null;
        }


        DmsGroup dmsGroup = dmsGroupDao.findByCodeAndValid(dmsGroupBean.getCode(), VALID);


        if (dmsGroup == null) {
            return null;
        }

        beanToDomain(dmsGroupBean, dmsGroup, "id", "code", "version", "createTime", "updateTime", "valid", "quantity");

        dmsGroupDao.save(dmsGroup);

        domainToBean(dmsGroup, dmsGroupBean);

        return dmsGroupBean;
    }

    @Override
    @Transactional
    public DmsGroupBean delete(DmsGroupBean dmsGroupBean) {

        if (dmsGroupBean == null) {
            return null;
        }


        DmsGroup dmsGroup = dmsGroupDao.findByCodeAndValid(dmsGroupBean.getCode(), VALID);


        if (dmsGroup == null) {
            return null;
        }

        // 删除分组下所有的设备
        List<DmsCamera> dmsCameraList = dmsCameraDao.findDmsCamerasByGroupId(dmsGroupBean.getId());
        if (dmsCameraList != null && !dmsCameraList.isEmpty()) {
            for (DmsCamera dmsCamera : dmsCameraList) {
                dmsCamera.setValid(INVALID);
                dmsCameraDao.save(dmsCamera);
            }
        }


        dmsGroup.setValid(INVALID);
        dmsGroupDao.save(dmsGroup);


        domainToBean(dmsGroup, dmsGroupBean);

        return dmsGroupBean;
    }

    @Override
    public DmsGroupBean find(DmsGroupBean dmsGroupBean) {
        if (dmsGroupBean == null) {
            return null;
        }


        DmsGroup dmsGroup = dmsGroupDao.findByCodeAndValid(dmsGroupBean.getCode(), VALID);


        if (dmsGroup == null) {
            return null;
        }
        BeanUtils.copyProperties(dmsGroup, dmsGroupBean);
        return dmsGroupBean;
    }

    @Override
    public DmsGroupBean find(String code) {
        return this.find(DmsGroupBean.builder().code(code).build());
    }

    @Override
    public List<DmsGroupBean> findAll(DmsGroupBean dmsGroupBean, PagerBean pagerBean) {
        List<DmsGroupBean> dmsGroupBeans = new ArrayList<>();


        DetachedCriteria detachedCriteria = createListCriteria(dmsGroupBean);

        List<DmsGroup> dmsGroups = dmsGroupDao.findAll(detachedCriteria, pagerBean);

        for (DmsGroup dmsGroup : dmsGroups) {


            DmsGroupBean dmsGroupBeanTemp = new DmsGroupBean();
            domainToBean(dmsGroup, dmsGroupBeanTemp);
            dmsGroupBeans.add(dmsGroupBeanTemp);
        }
        return dmsGroupBeans;
    }

    @Override
    public List<DmsGroupBean> findAll(DmsGroupBean dmsGroupBean) {
        return this.findAll(dmsGroupBean, null);
    }

    @Override
    public Long countAll(DmsGroupBean dmsGroupBean) {
        DetachedCriteria detachedCriteria = createListCriteria(dmsGroupBean);
        return dmsGroupDao.countAll(detachedCriteria);
    }

    @Override
    public PagerBean<DmsGroupBean> findPager(DmsGroupBean dmsGroupBean, PagerBean pagerBean) {
        List<DmsGroupBean> dmsGroupBeans = this.findAll(dmsGroupBean, pagerBean);

        Long count = this.countAll(dmsGroupBean);
        PagerBean<DmsGroupBean> dmsGroupPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, dmsGroupPageBean);
        dmsGroupPageBean.setItemCount(count.intValue());
        dmsGroupPageBean.init();
        dmsGroupPageBean.setItems(dmsGroupBeans);
        return dmsGroupPageBean;
    }

    /**
     * 创建列表查询条件
     *
     * @param dmsGroupBean 查询参数
     * @return
     */
    private static DetachedCriteria createListCriteria(DmsGroupBean dmsGroupBean) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DmsGroup.class);
        detachedCriteria.add(Restrictions.eq("valid", VALID));
        return detachedCriteria;
    }


    @Override
    public void batchDelete(List<DmsGroupBean> dmsGroupBean) {
        if (dmsGroupBean.isEmpty()) {
            return;
        }
        List<String> codes = new ArrayList<>();
        dmsGroupBean.forEach((e) -> {
            codes.add(e.getCode());
        });
        batchDeleteByCodes(codes);

    }


    @Override
    public void batchDeleteByCodes(List<String> codes) {

        if (codes.isEmpty()) {
            return;
        }

        for (String code : codes) {
            Assert.notNull(this.delete(DmsGroupBean.builder().code(code).build()), "batch delete by codes error! ");
        }

    }

    /**
     * 查询当前用户的分页信息
     *
     * @param dmsGroupBean
     * @param pagerBean
     * @param umsUserBean
     * @return
     */
    @Override
    public PagerBean<DmsGroupBean> findPager(DmsGroupBean dmsGroupBean, PagerBean pagerBean, UmsUserBean umsUserBean) {
        List<DmsGroupBean> dmsGroupBeans = this.findAll(dmsGroupBean, pagerBean, umsUserBean);

        Long count = this.countAll(dmsGroupBean);
        PagerBean<DmsGroupBean> dmsGroupPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, dmsGroupPageBean);
        dmsGroupPageBean.setItemCount(count.intValue());
        dmsGroupPageBean.init();
        dmsGroupPageBean.setItems(dmsGroupBeans);
        return dmsGroupPageBean;
    }

    /**
     * 查询当前用户的所有分组
     *
     * @param dmsGroupBean
     * @param pagerBean
     * @param umsUserBean
     * @return
     */
    @Override
    public List<DmsGroupBean> findAll(DmsGroupBean dmsGroupBean, PagerBean pagerBean, UmsUserBean umsUserBean) {
        List<DmsGroupBean> dmsGroupBeans = new ArrayList<>();

        // 获取当前用户的所有分组
        List<UmsUserGroupRelation> umsUserGroupRelationList = umsUserGroupRelationDao.findUmsUserGroupRelationsByUserId(umsUserBean.getId());
        List<Long> ids = new ArrayList<>();
        for (UmsUserGroupRelation umsUserGroupRelation : umsUserGroupRelationList) {
            ids.add(umsUserGroupRelation.getGroupId());
        }

        DetachedCriteria detachedCriteria = createListCriteria(dmsGroupBean);
        detachedCriteria.add(Restrictions.in("id", ids));

        detachedCriteria.addOrder(Order.desc("createTime"));

        List<DmsGroup> dmsGroups = dmsGroupDao.findAll(detachedCriteria, pagerBean);

        for (DmsGroup dmsGroup : dmsGroups) {
            // 把分组下的摄像头封装到分组bean中
            List<DmsCamera> dmsCameraList = dmsCameraDao.findDmsCamerasByGroupId(dmsGroup.getId());
            List<DmsCameraSimpleBean> dmsCameraBeanList = new ArrayList<>();
            for (DmsCamera dmsCamera : dmsCameraList) {
                DmsCameraSimpleBean dmsCameraSimpleBean = new DmsCameraSimpleBean();
                BeanUtils.copyProperties(dmsCamera, dmsCameraSimpleBean);
                dmsCameraBeanList.add(dmsCameraSimpleBean);
            }

            DmsGroupBean dmsGroupBeanTemp = new DmsGroupBean();
            dmsGroupBeanTemp.setDmsCameraSimpleBeanList(dmsCameraBeanList);
            domainToBean(dmsGroup, dmsGroupBeanTemp);

            dmsGroupBeans.add(dmsGroupBeanTemp);
        }
        return dmsGroupBeans;
    }


}
