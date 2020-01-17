package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.bean.core.DmsCameraBean;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.DmsCameraService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.DmsAlarmDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.DmsCameraDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.DmsGroupDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsUserGroupRelationDao;
import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsAlarm;
import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsCamera;
import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsGroup;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUserGroupRelation;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import com.wbspool.fastboot.core.jpa.service.AbstractFastbootService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.INVALID;
import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.VALID;


/**
 * DmsCameraService
 */
@Service("dmsCameraService")
@Transactional(rollbackFor = Exception.class, transactionManager = "hapMonitorCoreServiceTransactionManager")
public class DmsCameraServiceImpl extends AbstractFastbootService<DmsCamera, DmsCameraBean> implements DmsCameraService {

    private static final Logger logger = LoggerFactory.getLogger(DmsCameraServiceImpl.class);

    @Autowired
    DmsCameraDao dmsCameraDao;

    @Autowired
    DmsGroupDao dmsGroupDao;

    @Autowired
    UmsUserGroupRelationDao umsUserGroupRelationDao;

    @Autowired
    DmsAlarmDao dmsAlarmDao;

    @Override
    @Transactional
    public DmsCameraBean add(DmsCameraBean dmsCameraBean) {

        DmsCamera dmsCamera = new DmsCamera();
        // 初始化
        dmsCameraBean.setState((short) 1);

        beanToDomain(dmsCameraBean, dmsCamera, "id");

        if (null != dmsCameraBean.getGroupId()) {
            DmsGroup dmsGroup = dmsGroupDao.findDmsGroupById(dmsCameraBean.getGroupId());

            // 同步更新group设备数
            Integer oldQuantity = dmsGroup.getQuantity();
            dmsGroup.setQuantity(oldQuantity + 1);
            dmsGroupDao.save(dmsGroup);
        }

        dmsCameraDao.save(dmsCamera);
        domainToBean(dmsCamera, dmsCameraBean);
        return dmsCameraBean;

    }

    @Override
    public DmsCameraBean update(DmsCameraBean dmsCameraBean) {

        if (dmsCameraBean == null) {
            return null;
        }


        DmsCamera dmsCamera = dmsCameraDao.findByCodeAndValid(dmsCameraBean.getCode(), VALID);


        if (dmsCamera == null) {
            return null;
        }

        // 更新设备关联的分组情况
        if (dmsCamera.getGroupId() != dmsCameraBean.getGroupId()) {
            // 查询出旧的dmsGroup，并且更新dmsGroup的设备数
            DmsGroup OldDmsGroup = dmsGroupDao.findDmsGroupById(dmsCamera.getGroupId());
            OldDmsGroup.setQuantity(OldDmsGroup.getQuantity() - 1);
            dmsGroupDao.save(OldDmsGroup);

            // 查询出修改的分组，并且更新dmsGroup的设备数
            DmsGroup newDmsGroup = dmsGroupDao.findDmsGroupById(dmsCameraBean.getGroupId());
            newDmsGroup.setQuantity(newDmsGroup.getQuantity() + 1);
            dmsGroupDao.save(newDmsGroup);
        }

        // 忽略序列号，序列号不能修改
        beanToDomain(dmsCameraBean, dmsCamera, "id", "code", "version", "createTime", "updateTime", "valid", "serialNum", "state");

        // 同步更新alarm的groupId
        List<DmsAlarm> dmsAlarmList = dmsAlarmDao.findDmsAlarmsByCameraId(dmsCameraBean.getId());
        if (null != dmsAlarmList) {
            for (DmsAlarm dmsAlarm : dmsAlarmList) {
                dmsAlarm.setGroupId(dmsCameraBean.getGroupId());
                dmsAlarmDao.save(dmsAlarm);
            }
        }


        dmsCameraDao.save(dmsCamera);

        domainToBean(dmsCamera, dmsCameraBean);

        return dmsCameraBean;
    }

    @Override
    public DmsCameraBean delete(DmsCameraBean dmsCameraBean) {

        if (dmsCameraBean == null) {
            return null;
        }


        DmsCamera dmsCamera = dmsCameraDao.findByCodeAndValid(dmsCameraBean.getCode(), VALID);


        if (dmsCamera == null) {
            return null;
        }


        dmsCamera.setValid(INVALID);
        dmsCameraDao.save(dmsCamera);


        domainToBean(dmsCamera, dmsCameraBean);

        return dmsCameraBean;
    }

    @Override
    public DmsCameraBean find(DmsCameraBean dmsCameraBean) {
        if (dmsCameraBean == null) {
            return null;
        }

        DmsCamera dmsCamera = dmsCameraDao.findByCodeAndValid(dmsCameraBean.getCode(), VALID);

        if (dmsCamera == null) {
            return null;
        }
        BeanUtils.copyProperties(dmsCamera, dmsCameraBean);
        return dmsCameraBean;
    }

    @Override
    public DmsCameraBean find(String code) {
        return this.find(DmsCameraBean.builder().code(code).build());
    }

    @Override
    public List<DmsCameraBean> findAll(DmsCameraBean dmsCameraBean, PagerBean pagerBean, UmsUserBean umsUserBean) {
        List<DmsCameraBean> dmsCameraBeans = new ArrayList<>();

        // 获取当前用户的所有分组
        List<UmsUserGroupRelation> umsUserGroupRelationList = umsUserGroupRelationDao.findUmsUserGroupRelationsByUserId(umsUserBean.getId());
        // 若用户分组为空，则该用户下没用设备
        if (umsUserGroupRelationList == null || umsUserGroupRelationList.isEmpty()) {
            return dmsCameraBeans;
        }
        List<Long> dmsGroupIdList = new ArrayList<>();
        for (UmsUserGroupRelation umsUserGroupRelation : umsUserGroupRelationList) {
            dmsGroupIdList.add(umsUserGroupRelation.getGroupId());
        }

        DetachedCriteria detachedCriteria = createListCriteria(dmsCameraBean);

        // 添加分组ids查询条件
        detachedCriteria.add(Restrictions.in("groupId", dmsGroupIdList));

        // 按照创建时间逆序排序
        detachedCriteria.addOrder(Order.desc("createTime"));

        if (null != dmsCameraBean.getGroupId()) {
            detachedCriteria.add(Restrictions.eq("groupId", dmsCameraBean.getGroupId()));
        }
        if (!StringUtils.isBlank(dmsCameraBean.getName())) {
            detachedCriteria.add(Restrictions.like("name", dmsCameraBean.getName(), MatchMode.ANYWHERE));
        }
        if (!StringUtils.isBlank(dmsCameraBean.getNum())) {
            detachedCriteria.add(Restrictions.eq("num", dmsCameraBean.getNum()));
        }

        List<DmsCamera> dmsCameras = dmsCameraDao.findAll(detachedCriteria, pagerBean);
        // 如果没有设备，则返回空列表
        if (dmsCameras == null || dmsCameras.isEmpty()) {
            return dmsCameraBeans;
        }
        for (DmsCamera dmsCamera : dmsCameras) {

            DmsCameraBean dmsCameraBeanTemp = new DmsCameraBean();
            domainToBean(dmsCamera, dmsCameraBeanTemp);
            /**
             * 设置摄像头分组名
             */
            DmsGroup dmsGroup = dmsGroupDao.findDmsGroupById(dmsCamera.getGroupId());
            dmsCameraBeanTemp.setGroupName(dmsGroup.getName());

            dmsCameraBeans.add(dmsCameraBeanTemp);
        }
        return dmsCameraBeans;
    }

    @Override
    public List<DmsCameraBean> findAll(DmsCameraBean dmsCameraBean, UmsUserBean umsUserBean) {
        return this.findAll(dmsCameraBean, null, umsUserBean);
    }

    @Override
    public Long countAll(DmsCameraBean dmsCameraBean) {
        DetachedCriteria detachedCriteria = createListCriteria(dmsCameraBean);
        return dmsCameraDao.countAll(detachedCriteria);
    }

    @Override
    public PagerBean<DmsCameraBean> findPager(DmsCameraBean dmsCameraBean, PagerBean pagerBean, UmsUserBean umsUserBean) {
        List<DmsCameraBean> dmsCameraBeans = this.findAll(dmsCameraBean, pagerBean, umsUserBean);
        Long count = this.countAll(dmsCameraBean);
        PagerBean<DmsCameraBean> dmsCameraPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, dmsCameraPageBean);
        dmsCameraPageBean.setItemCount(count.intValue());
        dmsCameraPageBean.init();
        dmsCameraPageBean.setItems(dmsCameraBeans);
        return dmsCameraPageBean;
    }

    /**
     * 创建列表查询条件
     *
     * @param dmsCameraBean 查询参数
     * @return
     */
    private static DetachedCriteria createListCriteria(DmsCameraBean dmsCameraBean) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DmsCamera.class);
        detachedCriteria.add(Restrictions.eq("valid", VALID));
        return detachedCriteria;
    }


    @Override
    public void batchDelete(List<DmsCameraBean> dmsCameraBean) {
        if (dmsCameraBean.isEmpty()) {
            return;
        }
        List<String> codes = new ArrayList<>();
        dmsCameraBean.forEach((e) -> {
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
            Assert.notNull(this.delete(DmsCameraBean.builder().code(code).build()), "batch delete by codes error! ");
        }

    }

    @Override
    public Map<String, Object> findCameraInfo(UmsUserBean umsUserBean) {
        List<UmsUserGroupRelation> umsUserGroupRelationList = umsUserGroupRelationDao.findUmsUserGroupRelationsByUserId(umsUserBean.getId());
        List<Long> ids = new ArrayList<>();
        for (UmsUserGroupRelation umsUserGroupRelation : umsUserGroupRelationList) {
            ids.add(umsUserGroupRelation.getGroupId());
        }

        Integer total = dmsCameraDao.countByGroupIdIn(ids);
        Integer onlineNum = dmsCameraDao.countByGroupIdInAndState(ids, (short) 1);

        Map<String, Object> map = new HashMap<>();

        map.put("totalNum", total);
        map.put("onlineNum", onlineNum);

        return map;
    }


}
