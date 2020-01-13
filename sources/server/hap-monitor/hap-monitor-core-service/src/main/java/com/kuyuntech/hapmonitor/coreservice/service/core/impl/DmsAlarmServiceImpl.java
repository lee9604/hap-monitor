package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.DmsAlarmService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.DmsAlarmDao;
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
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import java.util.ArrayList;
import org.hibernate.criterion.Order;
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
    public List<DmsAlarmBean> findAll(DmsAlarmBean dmsAlarmBean, PagerBean pagerBean) {
        List<DmsAlarmBean> dmsAlarmBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(dmsAlarmBean);
        List<DmsAlarm> dmsAlarms = dmsAlarmDao.findAll(detachedCriteria,pagerBean);
        for (DmsAlarm dmsAlarm : dmsAlarms) {
            DmsAlarmBean dmsAlarmBeanTemp = new DmsAlarmBean();
            domainToBean(dmsAlarm,dmsAlarmBeanTemp);
            dmsAlarmBeans.add(dmsAlarmBeanTemp);
        }
        return dmsAlarmBeans;
    }

    @Override
    public List<DmsAlarmBean> findAll(DmsAlarmBean dmsAlarmBean) {
       return  this.findAll(dmsAlarmBean,null);
    }

    @Override
     public Long countAll(DmsAlarmBean dmsAlarmBean) {
        DetachedCriteria detachedCriteria = createListCriteria(dmsAlarmBean);
        return dmsAlarmDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<DmsAlarmBean> findPager(DmsAlarmBean dmsAlarmBean, PagerBean pagerBean) {
        List<DmsAlarmBean> dmsAlarmBeans = this.findAll(dmsAlarmBean, pagerBean);
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
