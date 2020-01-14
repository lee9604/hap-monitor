package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsAdminBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.DmsGroupDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsUserDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsUserGroupRelationDao;
import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsGroup;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUserGroupRelation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUser;
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

import com.wbspool.fastboot.core.jpa.service.AbstractFastbootService;
import org.springframework.util.Assert;

import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.*;


/**
 * UmsUserService
 */
@ConfigurationProperties(prefix = "md5")
@Service("umsUserService")
@Transactional(rollbackFor = Exception.class, transactionManager = "hapMonitorCoreServiceTransactionManager")
public class UmsUserServiceImpl extends AbstractFastbootService<UmsUser, UmsUserBean> implements UmsUserService {

    private static final Logger logger = LoggerFactory.getLogger(UmsUserServiceImpl.class);

    @Value("${md5.salt}")
    private String SALT;

    @Autowired
    UmsUserDao umsUserDao;

    @Autowired
    UmsUserGroupRelationDao umsUserGroupRelationDao;

    @Autowired
    DmsGroupDao dmsGroupDao;

    @Override
    public UmsUserBean add(UmsUserBean umsUserBean) {

        UmsUser umsUser = new UmsUser();
        beanToDomain(umsUserBean, umsUser, "id");
        umsUserDao.save(umsUser);
        domainToBean(umsUser, umsUserBean);
        return umsUserBean;

    }

    @Override
    public UmsUserBean update(UmsUserBean umsUserBean) {

        if (umsUserBean == null) {
            return null;
        }


        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(), VALID);


        if (umsUser == null) {
            return null;
        }

        beanToDomain(umsUserBean, umsUser, "id", "code", "version", "createTime", "updateTime");

        umsUserDao.save(umsUser);

        domainToBean(umsUser, umsUserBean);

        return umsUserBean;
    }

    @Override
    public UmsUserBean delete(UmsUserBean umsUserBean) {

        if (umsUserBean == null) {
            return null;
        }


        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(), VALID);


        if (umsUser == null) {
            return null;
        }


        umsUser.setValid(INVALID);
        umsUserDao.save(umsUser);


        domainToBean(umsUser, umsUserBean);

        return umsUserBean;
    }

    @Override
    public UmsUserBean find(UmsUserBean umsUserBean) {
        if (umsUserBean == null) {
            return null;
        }


        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(), VALID);


        if (umsUser == null) {
            return null;
        }
        BeanUtils.copyProperties(umsUser, umsUserBean);
        return umsUserBean;
    }

    @Override
    public UmsUserBean find(String code) {
        return this.find(UmsUserBean.builder().code(code).build());
    }

    @Override
    public List<UmsUserBean> findAll(UmsUserBean umsUserBean, PagerBean pagerBean) {
        List<UmsUserBean> umsUserBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(umsUserBean);
        List<UmsUser> umsUsers = umsUserDao.findAll(detachedCriteria, pagerBean);
        for (UmsUser umsUser : umsUsers) {
            UmsUserBean umsUserBeanTemp = new UmsUserBean();
            domainToBean(umsUser, umsUserBeanTemp);
            umsUserBeans.add(umsUserBeanTemp);
        }
        return umsUserBeans;
    }

    @Override
    public List<UmsUserBean> findAll(UmsUserBean umsUserBean) {
        return this.findAll(umsUserBean, null);
    }

    @Override
    public Long countAll(UmsUserBean umsUserBean) {
        DetachedCriteria detachedCriteria = createListCriteria(umsUserBean);
        return umsUserDao.countAll(detachedCriteria);
    }

    @Override
    public PagerBean<UmsUserBean> findPager(UmsUserBean umsUserBean, PagerBean pagerBean) {
        List<UmsUserBean> umsUserBeans = this.findAll(umsUserBean, pagerBean);
        Long count = this.countAll(umsUserBean);
        PagerBean<UmsUserBean> umsUserPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsUserPageBean);
        umsUserPageBean.setItemCount(count.intValue());
        umsUserPageBean.init();
        umsUserPageBean.setItems(umsUserBeans);
        return umsUserPageBean;
    }

    /**
     * 创建列表查询条件
     *
     * @param umsUserBean 查询参数
     * @return
     */
    private static DetachedCriteria createListCriteria(UmsUserBean umsUserBean) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UmsUser.class);
        detachedCriteria.add(Restrictions.eq("valid", VALID));
        return detachedCriteria;
    }


    @Override
    public void batchDelete(List<UmsUserBean> umsUserBean) {
        if (umsUserBean.isEmpty()) {
            return;
        }
        List<String> codes = new ArrayList<>();
        umsUserBean.forEach((e) -> {
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
            Assert.notNull(this.delete(UmsUserBean.builder().code(code).build()), "batch delete by codes error! ");
        }

    }

    /**
     * 超级管理员查询所有的一级用户分页列表
     * @param umsUserBean
     * @param pagerBean
     * @return
     */
    @Override
    public PagerBean<UmsUserBean> findPagerForUmsAdmin(UmsUserBean umsUserBean, PagerBean pagerBean) {
        // 查询所有具备一级账号权限的用户
        DetachedCriteria detachedCriteria = createListCriteria(umsUserBean);
        List<UmsUserBean> umsUserBeans = this.findAllWithCondition(umsUserBean, pagerBean, detachedCriteria);

        Long count = this.countAll(umsUserBean);

        PagerBean<UmsUserBean> umsUserPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsUserPageBean);

        umsUserPageBean.setItemCount(count.intValue());
        umsUserPageBean.init();
        umsUserPageBean.setItems(umsUserBeans);

        return umsUserPageBean;
    }

    @Override
    public void checkPassword(String password) {

    }

    @Override
    public UmsUserBean findByUmsAdminBean(UmsUserBean umsUserBean) {
        if (null == umsUserBean) {
            return null;
        }

        UmsUser umsUser = umsUserDao.findByUsernameAndPassword(umsUserBean.getUsername(),
                DigestUtils.md5Hex(umsUserBean.getPassword() + SALT));

        if (null == umsUser) {
            return null;
        }

        BeanUtils.copyProperties(umsUser, umsUserBean);

        return umsUserBean;
    }

    /**
     * 根据条件查询一级账户列表
     * @param umsUserBean
     * @param pagerBean
     * @param detachedCriteria
     * @return
     */
    public List<UmsUserBean> findAllWithCondition(UmsUserBean umsUserBean, PagerBean pagerBean, DetachedCriteria detachedCriteria) {
        List<UmsUserBean> umsUserBeans = new ArrayList<>();
//        // 根据条件进行查询
        detachedCriteria.add(Restrictions.eq("roleId", 1L));
        if (null != umsUserBean) {
            if (!StringUtils.isBlank(umsUserBean.getUsername())) {
                detachedCriteria.add(Restrictions.eq("username", umsUserBean.getUsername()));
            }
            if (!StringUtils.isBlank(umsUserBean.getCompany())) {
                detachedCriteria.add(Restrictions.eq("company", umsUserBean.getCompany()));
            }
            if (null != umsUserBean.getValid()) {
                detachedCriteria.add(Restrictions.eq("valid", umsUserBean.getValid()));
            }
        }

        List<UmsUser> umsUsers = umsUserDao.findAll(detachedCriteria, pagerBean);

        for (UmsUser umsUser : umsUsers) {
            List<UmsUserGroupRelation> umsUserGroupRelationList =
                    umsUserGroupRelationDao.findUmsUserGroupRelationsByUserId(umsUser.getId());

            List<DmsGroup> dmsGroupList = new ArrayList<>();
            Integer cameraNum = 0;
            for (UmsUserGroupRelation umsUserGroupRelation : umsUserGroupRelationList) {
                DmsGroup dmsGroup = dmsGroupDao.findDmsGroupsById(umsUserGroupRelation.getGroupId());
                cameraNum += dmsGroup.getQuantity();
            }

            UmsUserBean umsUserBeanTemp = new UmsUserBean();
            domainToBean(umsUser, umsUserBeanTemp);
            umsUserBeanTemp.setCameraNum(cameraNum);
            umsUserBeans.add(umsUserBeanTemp);
        }
        return umsUserBeans;
    }

}
