package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreapi.service.core.DmsAlarmService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.DmsAlarmDao;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsUserGroupRelationDao;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUserGroupRelation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.DmsAlarmBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsAlarm;
import com.wbspool.fastboot.core.common.bean.PagerBean;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import java.util.ArrayList;

import com.wbspool.fastboot.core.jpa.service.AbstractFastbootService;
import org.springframework.util.Assert;
import static com.wbspool.fastboot.core.jpa.constant.DataValidTypes.*;





/**
* DmsAlarmService
*
*/
@Service("dmsAlarmService")
@Transactional(rollbackFor = Exception.class,transactionManager = "hapMonitorCoreServiceTransactionManager")
public class DmsAlarmServiceImpl extends AbstractFastbootService<DmsAlarm,DmsAlarmBean> implements DmsAlarmService {

    private static final Logger logger = LoggerFactory.getLogger(DmsAlarmServiceImpl.class);

    @Autowired
    DmsAlarmDao dmsAlarmDao;

    @Autowired
    UmsUserGroupRelationDao umsUserGroupRelationDao;
    

    @Override
    public DmsAlarmBean add(DmsAlarmBean dmsAlarmBean) {

        DmsAlarm dmsAlarm = new DmsAlarm();
        beanToDomain(dmsAlarmBean,dmsAlarm,"id");
        dmsAlarmDao.save(dmsAlarm);
        domainToBean(dmsAlarm,dmsAlarmBean);
        return dmsAlarmBean;

    }

    @Override
    public DmsAlarmBean update(DmsAlarmBean dmsAlarmBean) {

        if(dmsAlarmBean == null ){
            return null ;
        }

        
        DmsAlarm dmsAlarm = dmsAlarmDao.findByCodeAndValid(dmsAlarmBean.getCode(),VALID);
        

        if(dmsAlarm == null){
            return null;
        }

        beanToDomain(dmsAlarmBean,dmsAlarm,"id","code","version","createTime","updateTime","valid");

        dmsAlarmDao.save(dmsAlarm);

        domainToBean(dmsAlarm,dmsAlarmBean);

        return dmsAlarmBean;
    }

    @Override
    public DmsAlarmBean delete(DmsAlarmBean dmsAlarmBean) {

        if(dmsAlarmBean == null){
            return null ;
        }

        
        DmsAlarm dmsAlarm = dmsAlarmDao.findByCodeAndValid(dmsAlarmBean.getCode(),VALID);
        


        if(dmsAlarm == null){
            return null;
        }

        
        dmsAlarm.setValid(INVALID);
        dmsAlarmDao.save(dmsAlarm);
        

        domainToBean(dmsAlarm,dmsAlarmBean);

        return dmsAlarmBean;
    }

    @Override
    public DmsAlarmBean find(DmsAlarmBean dmsAlarmBean) {
        if(dmsAlarmBean == null){
            return null ;
        }

        
        DmsAlarm dmsAlarm = dmsAlarmDao.findByCodeAndValid(dmsAlarmBean.getCode(),VALID);
        


        if(dmsAlarm == null){
            return null ;
        }
        BeanUtils.copyProperties(dmsAlarm,dmsAlarmBean);
        return dmsAlarmBean;
    }

    @Override
    public DmsAlarmBean find(String code) {
        return  this.find(DmsAlarmBean.builder().code(code).build());
    }

    @Override
    public List<DmsAlarmBean> findAll(DmsAlarmBean dmsAlarmBean, PagerBean pagerBean, UmsUserBean umsUserBean) {

        // 获取当前用户的所有分组
        List<UmsUserGroupRelation> umsUserGroupRelationList = umsUserGroupRelationDao.findUmsUserGroupRelationsByUserId(umsUserBean.getId());
        List<Long> dmsGroupIdList = new ArrayList<>();
        for (UmsUserGroupRelation umsUserGroupRelation : umsUserGroupRelationList) {
            dmsGroupIdList.add(umsUserGroupRelation.getGroupId());
        }

        List<DmsAlarmBean> dmsAlarmBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(dmsAlarmBean);

        // 按照发生时间倒序排序
        detachedCriteria.addOrder(Order.desc("createTime"));

        // 设置查询条件为用户所有分组的警报
        detachedCriteria.add(Restrictions.in("groupId", dmsGroupIdList));

        if (null != dmsAlarmBean.getGroupId()) {
            detachedCriteria.add(Restrictions.eq("groupId", dmsAlarmBean.getGroupId()));
        }
        if (!StringUtils.isBlank(dmsAlarmBean.getCameraName())) {
            detachedCriteria.add(Restrictions.eq("cameraName", dmsAlarmBean.getCameraName()));
        }
        if (!StringUtils.isBlank(dmsAlarmBean.getCameraNum())) {
            detachedCriteria.add(Restrictions.eq("cameraNum", dmsAlarmBean.getCameraNum()));
        }
        if (!(StringUtils.isBlank(dmsAlarmBean.getStartTime()+"")&&StringUtils.isBlank(dmsAlarmBean.getEndTime()+""))) {
            detachedCriteria.add(Restrictions.between("createTime", dmsAlarmBean.getStartTime(), dmsAlarmBean.getEndTime()));
        }

        List<DmsAlarm> dmsAlarms = dmsAlarmDao.findAll(detachedCriteria,pagerBean);
        for (DmsAlarm dmsAlarm : dmsAlarms) {
            DmsAlarmBean dmsAlarmBeanTemp = new DmsAlarmBean();
            domainToBean(dmsAlarm,dmsAlarmBeanTemp);
            dmsAlarmBeans.add(dmsAlarmBeanTemp);
        }
        return dmsAlarmBeans;
    }

    @Override
    public List<DmsAlarmBean> findAll(DmsAlarmBean dmsAlarmBean, UmsUserBean umsUserBean) {
       return  this.findAll(dmsAlarmBean,null, umsUserBean);
    }

    @Override
     public Long countAll(DmsAlarmBean dmsAlarmBean) {
        DetachedCriteria detachedCriteria = createListCriteria(dmsAlarmBean);
        return dmsAlarmDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<DmsAlarmBean> findPager(DmsAlarmBean dmsAlarmBean, PagerBean pagerBean, UmsUserBean umsUserBean) {
        List<DmsAlarmBean> dmsAlarmBeans = this.findAll(dmsAlarmBean, pagerBean, umsUserBean);
        Long count = this.countAll(dmsAlarmBean);
        PagerBean<DmsAlarmBean> dmsAlarmPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, dmsAlarmPageBean);
        dmsAlarmPageBean.setItemCount(count.intValue());
        dmsAlarmPageBean.init();
        dmsAlarmPageBean.setItems(dmsAlarmBeans);
        return  dmsAlarmPageBean ;
     }

     /**
      * 创建列表查询条件
      * @param dmsAlarmBean 查询参数
      * @return
      */
     private static DetachedCriteria createListCriteria(DmsAlarmBean dmsAlarmBean){
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DmsAlarm.class);
         detachedCriteria.add(Restrictions.eq("valid",VALID));
         return  detachedCriteria;
     }



    @Override
    public void batchDelete(List<DmsAlarmBean> dmsAlarmBean) {
        if(dmsAlarmBean.isEmpty()){
            return ;
        }
        List<String> codes = new ArrayList<>();
       dmsAlarmBean.forEach((e) ->{
          codes.add(e.getCode());
        });
        batchDeleteByCodes(codes);

    }


    @Override
    public void batchDeleteByCodes(List<String> codes) {

        if(codes.isEmpty()){
        return ;
        }

        for (String code : codes) {
            Assert.notNull(this.delete(DmsAlarmBean.builder().code(code).build()),"batch delete by codes error! ");
        }

    }


}
