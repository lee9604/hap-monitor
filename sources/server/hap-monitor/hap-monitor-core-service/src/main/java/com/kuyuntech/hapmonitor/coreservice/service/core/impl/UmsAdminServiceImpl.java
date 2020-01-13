package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.UmsAdminService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsAdminDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsAdminBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsAdmin;
import com.wbspool.fastboot.core.common.bean.PagerBean;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;

import org.hibernate.criterion.Order;
import com.wbspool.fastboot.core.jpa.service.AbstractFastbootService;
import org.springframework.util.Assert;

import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.*;


/**
 * UmsAdminService
 */
@Service("umsAdminService")
@Transactional(rollbackFor = Exception.class, transactionManager = "hapMonitorCoreServiceTransactionManager")
public class UmsAdminServiceImpl extends AbstractFastbootService<UmsAdmin, UmsAdminBean> implements UmsAdminService {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    UmsAdminDao umsAdminDao;


    @Override
    public UmsAdminBean add(UmsAdminBean umsAdminBean) {

        UmsAdmin umsAdmin = new UmsAdmin();
        beanToDomain(umsAdminBean, umsAdmin, "id");
        umsAdminDao.save(umsAdmin);
        domainToBean(umsAdmin, umsAdminBean);
        return umsAdminBean;

    }

    @Override
    public UmsAdminBean update(UmsAdminBean umsAdminBean) {

        if (umsAdminBean == null) {
            return null;
        }


        UmsAdmin umsAdmin = umsAdminDao.findByCodeAndValid(umsAdminBean.getCode(), VALID);


        if (umsAdmin == null) {
            return null;
        }

        beanToDomain(umsAdminBean, umsAdmin, "id", "code", "version", "createTime", "updateTime", "valid");

        umsAdminDao.save(umsAdmin);

        domainToBean(umsAdmin, umsAdminBean);

        return umsAdminBean;
    }

    @Override
    public UmsAdminBean delete(UmsAdminBean umsAdminBean) {

        if (umsAdminBean == null) {
            return null;
        }


        UmsAdmin umsAdmin = umsAdminDao.findByCodeAndValid(umsAdminBean.getCode(), VALID);


        if (umsAdmin == null) {
            return null;
        }


        umsAdmin.setValid(INVALID);
        umsAdminDao.save(umsAdmin);


        domainToBean(umsAdmin, umsAdminBean);

        return umsAdminBean;
    }

    @Override
    public UmsAdminBean find(UmsAdminBean umsAdminBean) {
        if (umsAdminBean == null) {
            return null;
        }


        UmsAdmin umsAdmin = umsAdminDao.findByCodeAndValid(umsAdminBean.getCode(), VALID);


        if (umsAdmin == null) {
            return null;
        }
        BeanUtils.copyProperties(umsAdmin, umsAdminBean);
        return umsAdminBean;
    }

    @Override
    public UmsAdminBean find(String code) {
        return this.find(UmsAdminBean.builder().code(code).build());
    }

    @Override
    public List<UmsAdminBean> findAll(UmsAdminBean umsAdminBean, PagerBean pagerBean) {
        List<UmsAdminBean> umsAdminBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(umsAdminBean);
        List<UmsAdmin> umsAdmins = umsAdminDao.findAll(detachedCriteria, pagerBean);
        for (UmsAdmin umsAdmin : umsAdmins) {
            UmsAdminBean umsAdminBeanTemp = new UmsAdminBean();
            domainToBean(umsAdmin, umsAdminBeanTemp);
            umsAdminBeans.add(umsAdminBeanTemp);
        }
        return umsAdminBeans;
    }

    @Override
    public List<UmsAdminBean> findAll(UmsAdminBean umsAdminBean) {
        return this.findAll(umsAdminBean, null);
    }

    @Override
    public Long countAll(UmsAdminBean umsAdminBean) {
        DetachedCriteria detachedCriteria = createListCriteria(umsAdminBean);
        return umsAdminDao.countAll(detachedCriteria);
    }

    @Override
    public PagerBean<UmsAdminBean> findPager(UmsAdminBean umsAdminBean, PagerBean pagerBean) {
        List<UmsAdminBean> umsAdminBeans = this.findAll(umsAdminBean, pagerBean);
        Long count = this.countAll(umsAdminBean);
        PagerBean<UmsAdminBean> umsAdminPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsAdminPageBean);
        umsAdminPageBean.setItemCount(count.intValue());
        umsAdminPageBean.init();
        umsAdminPageBean.setItems(umsAdminBeans);
        return umsAdminPageBean;
    }

    /**
     * 创建列表查询条件
     *
     * @param umsAdminBean 查询参数
     * @return
     */
    private static DetachedCriteria createListCriteria(UmsAdminBean umsAdminBean) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UmsAdmin.class);
        detachedCriteria.add(Restrictions.eq("valid", VALID));
        return detachedCriteria;
    }


    @Override
    public void batchDelete(List<UmsAdminBean> umsAdminBean) {
        if (umsAdminBean.isEmpty()) {
            return;
        }
        List<String> codes = new ArrayList<>();
        umsAdminBean.forEach((e) -> {
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
            Assert.notNull(this.delete(UmsAdminBean.builder().code(code).build()), "batch delete by codes error! ");
        }

    }

    @Override
    public UmsAdminBean findByUmsAdminBean(UmsAdminBean umsAdminBean) {
        if (null == umsAdminBean) {
            return null;
        }

        UmsAdmin umsAdmin = umsAdminDao.findByUsernameAndPassword(umsAdminBean.getUsername(), umsAdminBean.getPassword());

        if (null == umsAdmin) {
            return null;
        }

        BeanUtils.copyProperties(umsAdmin, umsAdminBean);

        return umsAdminBean;
    }


}
