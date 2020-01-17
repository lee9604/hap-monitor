package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.DmsGroupDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsUserDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsUserGroupRelationDao;
import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsGroup;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUser;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUserGroupRelation;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import com.wbspool.fastboot.core.jpa.service.AbstractFastbootService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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

import static com.kuyuntech.hapmonitor.coreapi.constant.core.RoleTypes.*;
import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.INVALID;
import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.VALID;


/**
 * UmsUserService
 */
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
    public UmsUserBean add(UmsUserBean umsUserBean, UmsUserBean loginUserBean) {

        UmsUser umsUser = new UmsUser();
        // 初始化
        umsUserBean.setState((short) 1);
        umsUserBean.setParentId(loginUserBean.getId());
        umsUserBean.setPassword(DigestUtils.md5Hex(umsUserBean.getPassword() + SALT));
        umsUserBean.setCompany(loginUserBean.getCompany());
        beanToDomain(umsUserBean, umsUser, "id");

        UmsUser user = umsUserDao.save(umsUser);
        // 二级账号需要添加分组
        if (user.getRoleId() == LEVEL2) {

            List<Long> groupIdList = umsUserBean.getGroupIdList();
            if (null != groupIdList) {
                for (Long groupId : groupIdList) {
                    UmsUserGroupRelation umsUserGroupRelation = new UmsUserGroupRelation();
                    umsUserGroupRelation.setUserId(user.getId());
                    umsUserGroupRelation.setGroupId(groupId);
                    umsUserGroupRelationDao.save(umsUserGroupRelation);
                }
            }
        }
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

        // 初始化
        umsUserBean.setPassword(DigestUtils.md5Hex(umsUserBean.getPassword() + SALT));

        // 忽略"roleId", "parentId", "company", "state"
        beanToDomain(umsUserBean, umsUser, "id", "code", "version", "createTime", "updateTime", "valid", "roleId", "parentId", "company", "state");

        UmsUser user = umsUserDao.save(umsUser);

        // 二级账号同时需要更新分组情况
        if (umsUserBean.getRoleId() == LEVEL2) {
            // 删除二级账号旧的分组情况
            umsUserGroupRelationDao.deleteUmsUserGroupRelationsByUserId(user.getId());
            // 添加新的分组情况
            List<Long> groupIdList = umsUserBean.getGroupIdList();
            if (null != groupIdList) {
                for (Long groupId : groupIdList) {
                    UmsUserGroupRelation umsUserGroupRelation = new UmsUserGroupRelation();
                    umsUserGroupRelation.setUserId(user.getId());
                    umsUserGroupRelation.setGroupId(groupId);
                    umsUserGroupRelationDao.save(umsUserGroupRelation);
                }
            }
        }

        domainToBean(umsUser, umsUserBean);

        return umsUserBean;
    }

    @Override
    @Transactional
    public UmsUserBean delete(UmsUserBean umsUserBean) {

        if (umsUserBean == null) {
            return null;
        }


        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(), VALID);


        if (umsUser == null) {
            return null;
        }

        // 判断删除的用户是否是一级用户，若是则删除一级用户名下的二级用户
        if (umsUser.getRoleId() == 2) {
            List<UmsUser> umsUserList = umsUserDao.findUmsUsersByParentId(umsUser.getId());
            if (null != umsUserList) {
                for (UmsUser user : umsUserList) {
                    user.setValid(INVALID);
                    umsUserDao.save(user);
                }
            }
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
    public List<UmsUserBean> findAll(UmsUserBean umsUserBean, PagerBean pagerBean, UmsUserBean loginUmsUserBean) {

        List<UmsUserBean> umsUserBeans = new ArrayList<>();


        // 设置查询条件为倒序、从属id
        DetachedCriteria detachedCriteria = createListCriteria(umsUserBean);
        detachedCriteria.addOrder(Order.desc("createTime"));
        detachedCriteria.add(Restrictions.eq("parentId", loginUmsUserBean.getId()));

        List<UmsUser> umsUsers = umsUserDao.findAll(detachedCriteria, pagerBean);
        for (UmsUser umsUser : umsUsers) {

            // 获取客户所有分组
            List<UmsUserGroupRelation> umsUserGroupRelationList = umsUserGroupRelationDao.findUmsUserGroupRelationsByUserId(umsUser.getId());
            List<String> groupNameList = new ArrayList<>();
            for (UmsUserGroupRelation umsUserGroupRelation : umsUserGroupRelationList) {
                DmsGroup dmsGroup = dmsGroupDao.findDmsGroupById(umsUserGroupRelation.getGroupId());
                groupNameList.add(dmsGroup.getName());
            }

            UmsUserBean umsUserBeanTemp = new UmsUserBean();

            domainToBean(umsUser, umsUserBeanTemp);

            umsUserBeanTemp.setGroupNameList(groupNameList);
            umsUserBeans.add(umsUserBeanTemp);
        }
        return umsUserBeans;
    }

    @Override
    public List<UmsUserBean> findAll(UmsUserBean umsUserBean, UmsUserBean loginUmsUserBean) {
        return this.findAll(umsUserBean, null, loginUmsUserBean);
    }

    @Override
    public Long countAll(UmsUserBean umsUserBean) {
        DetachedCriteria detachedCriteria = createListCriteria(umsUserBean);
        return umsUserDao.countAll(detachedCriteria);
    }

    @Override
    public PagerBean<UmsUserBean> findPager(UmsUserBean umsUserBean, PagerBean pagerBean, UmsUserBean loginUmsUserBean) {
        List<UmsUserBean> umsUserBeans = this.findAll(umsUserBean, pagerBean, loginUmsUserBean);
        Long count = this.countAll(umsUserBean);
        PagerBean<UmsUserBean> umsUserPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsUserPageBean);
        umsUserPageBean.setItemCount(count.intValue());
        umsUserPageBean.init();
        umsUserPageBean.setItems(umsUserBeans);
        return umsUserPageBean;
    }

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

    @Override
    public PagerBean<UmsUserBean> findPagerRoleLevelOne(UmsUserBean umsUserBean, PagerBean pagerBean) {

        List<UmsUserBean> umsUserBeans = this.findAllRoleLevelOne(umsUserBean, pagerBean);

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

    @Override
    public UmsUserBean updateLevelOneUser(UmsUserBean umsUserBean) {
        if (umsUserBean == null) {
            return null;
        }


        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(), VALID);


        if (umsUser == null) {
            return null;
        }

        beanToDomain(umsUserBean, umsUser, "id", "code", "version", "createTime", "updateTime", "roleId");

        umsUserDao.save(umsUser);

        domainToBean(umsUser, umsUserBean);

        return umsUserBean;
    }

    @Override
    @Transactional
    public UmsUserBean updateLevelTwoUser(UmsUserBean umsUserBean) {
        if (umsUserBean == null) {
            return null;
        }


        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(), VALID);


        if (umsUser == null) {
            return null;
        }

        beanToDomain(umsUserBean, umsUser, "id", "code", "version", "createTime", "updateTime", "roleId");

        // 删除旧的用户分组映射关系
        umsUserGroupRelationDao.deleteUmsUserGroupRelationsByUserId(umsUserBean.getId());
        // 添加新的用户分组映射关系
        List<DmsGroup> dmsGroupList = dmsGroupDao.findDmsGroupsByIdIn(umsUserBean.getGroupIdList());
        for (DmsGroup dmsGroup : dmsGroupList) {
            UmsUserGroupRelation umsUserGroupRelation = new UmsUserGroupRelation();
            umsUserGroupRelation.setUserId(umsUserBean.getId());
            umsUserGroupRelation.setGroupId(dmsGroup.getId());
            umsUserGroupRelationDao.save(umsUserGroupRelation);
        }

        umsUserDao.save(umsUser);

        domainToBean(umsUser, umsUserBean);

        return umsUserBean;
    }

    @Override
    public UmsUserBean addAdmin(UmsUserBean umsUserBean) {
        UmsUser umsUser = new UmsUser();
        beanToDomain(umsUserBean, umsUser, "id");

        // 设置超级管理员身份
        umsUser.setRoleId(1L);
        umsUserDao.save(umsUser);
        domainToBean(umsUser, umsUserBean);
        return umsUserBean;
    }

    @Override
    public PagerBean<UmsUserBean> findPagerAdmin(UmsUserBean umsUserBean, PagerBean pagerBean) {
        List<UmsUserBean> umsUserBeans = this.findAllAdmin(umsUserBean, pagerBean);

        Long count = this.countAll(umsUserBean);

        PagerBean<UmsUserBean> umsUserPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsUserPageBean);

        umsUserPageBean.setItemCount(count.intValue());
        umsUserPageBean.init();
        umsUserPageBean.setItems(umsUserBeans);

        return umsUserPageBean;
    }

    @Override
    public List<UmsUserBean> findAllAdmin(UmsUserBean umsUserBean, PagerBean pagerBean) {
        List<UmsUserBean> umsUserBeans = new ArrayList<>();


        // 设置查询条件为倒序、从属id
        DetachedCriteria detachedCriteria = createListCriteria(umsUserBean);
        detachedCriteria.addOrder(Order.desc("createTime"));
        detachedCriteria.add(Restrictions.eq("roleId", 1L));

        List<UmsUser> umsUsers = umsUserDao.findAll(detachedCriteria, pagerBean);
        for (UmsUser umsUser : umsUsers) {
            UmsUserBean umsUserBeanTemp = new UmsUserBean();
            domainToBean(umsUser, umsUserBeanTemp);
            umsUserBeans.add(umsUserBeanTemp);
        }
        return umsUserBeans;
    }

    @Override
    public UmsUserBean updateAdmin(UmsUserBean umsUserBean) {
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
    public UmsUserBean addUmsUser(UmsUserBean umsUserBean) {
        UmsUser umsUser = new UmsUser();
        // 初始化umsUserBean
        umsUserBean.setRoleId(2L);
        umsUserBean.setParentId(0L);
        umsUserBean.setPassword(DigestUtils.md5Hex(umsUserBean.getPassword() + SALT));

        BeanUtils.copyProperties(umsUserBean, umsUser, "id");

        umsUser = umsUserDao.save(umsUser);

        BeanUtils.copyProperties(umsUser, umsUserBean);

        return umsUserBean;
    }

    @Override
    public UmsUserBean updateUmsUser(UmsUserBean umsUserBean) {
        if (null == umsUserBean) {
            return null;
        }


        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(), VALID);


        if (null == umsUser) {
            return null;
        }

        BeanUtils.copyProperties(umsUserBean, umsUser, "id", "code", "version", "createTime", "updateTime");

        umsUser = umsUserDao.save(umsUser);

        BeanUtils.copyProperties(umsUser, umsUserBean, "id", "code", "version", "createTime", "updateTime");

        return umsUserBean;
    }

    public List<UmsUserBean> findAllRoleLevelOne(UmsUserBean umsUserBean, PagerBean pagerBean) {
        /*
        查询条件：
            roleId 一级管理员
            createTime 逆序排序
        筛选条件：
            username 账号名称
            company 公司名称
            valid 账号状态
         */
        DetachedCriteria detachedCriteria = createListCriteria(umsUserBean);
        detachedCriteria.add(Restrictions.eq("roleId", 1L));
        detachedCriteria.addOrder(Order.desc("createTime"));
        if (null != umsUserBean) {
            if (!StringUtils.isBlank(umsUserBean.getUsername())) {
                detachedCriteria.add(Restrictions.eq("username", umsUserBean.getUsername()));
            }
            if (!StringUtils.isBlank(umsUserBean.getCompany())) {
                detachedCriteria.add(Restrictions.eq("company", umsUserBean.getCompany()));
            }
            if (null != umsUserBean.getState()) {
                detachedCriteria.add(Restrictions.eq("state", umsUserBean.getState()));
            }
        }

        List<UmsUser> umsUserList = umsUserDao.findAll(detachedCriteria, pagerBean);
        List<UmsUserBean> umsUserBeans = new ArrayList<>();

        for (UmsUser umsUser : umsUserList) {

            // 统计用户管理的所有分组的总设备数
            List<UmsUserGroupRelation> umsUserGroupRelationList =
                    umsUserGroupRelationDao.findUmsUserGroupRelationsByUserId(umsUser.getId());
            Integer cameraNum = 0;
            for (UmsUserGroupRelation umsUserGroupRelation : umsUserGroupRelationList) {
                DmsGroup dmsGroup = dmsGroupDao.findDmsGroupById(umsUserGroupRelation.getGroupId());
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
