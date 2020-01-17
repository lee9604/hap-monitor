package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsAdminBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsAdminService;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsAdminDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsUserDao;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsAdmin;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUser;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import com.wbspool.fastboot.core.jpa.service.AbstractFastbootService;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.INVALID;
import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.VALID;


/**
 * UmsAdminService
 */
@Service("umsAdminService")
@Transactional(rollbackFor = Exception.class, transactionManager = "hapMonitorCoreServiceTransactionManager")
public class UmsAdminServiceImpl extends AbstractFastbootService<UmsAdmin, UmsAdminBean> implements UmsAdminService {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Value("${md5.salt}")
    private String SALT;

    @Autowired
    UmsAdminDao umsAdminDao;

    @Autowired
    UmsUserDao umsUserDao;

    @Autowired
    UmsUserService umsUserService;

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

        // 按照时间逆序排序
        detachedCriteria.addOrder(Order.desc("createTime"));

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

        UmsAdmin umsAdmin = umsAdminDao.findByUsernameAndPassword(umsAdminBean.getUsername(),
                DigestUtils.md5Hex(umsAdminBean.getPassword() + SALT));

        if (null == umsAdmin) {
            return null;
        }

        BeanUtils.copyProperties(umsAdmin, umsAdminBean);

        return umsAdminBean;
    }

    @Override
    public UmsUserBean updateUmsUser(UmsUserBean umsUserBean) {
        if (umsUserBean == null) {
            return null;
        }


        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(), VALID);


        if (umsUser == null) {
            return null;
        }

        BeanUtils.copyProperties(umsUserBean, umsUser, "id", "code", "version", "createTime", "updateTime");

        umsUser = umsUserDao.save(umsUser);

        BeanUtils.copyProperties(umsUser, umsUserBean, "id", "code", "version", "createTime", "updateTime");

        return umsUserBean;
    }

    @Override
    public UmsUserBean addUmsUser(UmsUserBean umsUserBean) {
        UmsUser umsUser = new UmsUser();
        // 赋予一级管理员权限
        umsUser.setRoleId(2L);

        BeanUtils.copyProperties(umsUserBean, umsUser, "id");

        umsUser = umsUserDao.save(umsUser);

        BeanUtils.copyProperties(umsUser, umsUserBean);

        return umsUserBean;
    }

    @Override
    @Transactional
    public UmsUserBean deleteUmsUser(UmsUserBean umsUserBean) {
        if (null == umsUserBean) {
            return null;
        }

        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(), VALID);

        if (umsUser == null) {
            return null;
        }


        List<UmsUser> umsUserList = umsUserDao.findUmsUsersByParentId(umsUser.getId());
        if (null != umsUserList) {
            for (UmsUser user : umsUserList) {
                // 判断删除的用户是否是一级用户，若是则删除一级用户名下的二级用户
                if (user.getRoleId() == 2) {
                    List<UmsUser> umsUserChildrenList = umsUserDao.findUmsUsersByParentId(user.getId());
                    for (UmsUser userChildren : umsUserChildrenList) {
                        userChildren.setValid(INVALID);
                        umsUserDao.save(userChildren);
                    }
                }
                user.setValid(INVALID);
                umsUserDao.save(user);
            }
        }

        umsUser.setValid(INVALID);
        umsUserDao.save(umsUser);

        BeanUtils.copyProperties(umsUser, umsUserBean);

        return umsUserBean;
    }


}
