package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.LmsLogService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.LmsLogDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.LmsLogBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.LmsLog;
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
* LmsLogService
*
*/
@Service("lmsLogService")
@Transactional(rollbackFor = Exception.class,transactionManager = "hapMonitorCoreServiceTransactionManager")
public class LmsLogServiceImpl extends AbstractFastbootService<LmsLog,LmsLogBean> implements LmsLogService {

    private static final Logger logger = LoggerFactory.getLogger(LmsLogServiceImpl.class);

    @Autowired
    LmsLogDao lmsLogDao;

    

    @Override
    public LmsLogBean add(LmsLogBean lmsLogBean) {

        LmsLog lmsLog = new LmsLog();
        beanToDomain(lmsLogBean,lmsLog,"id");
        lmsLogDao.save(lmsLog);
        domainToBean(lmsLog,lmsLogBean);
        return lmsLogBean;

    }

    @Override
    public LmsLogBean update(LmsLogBean lmsLogBean) {

        if(lmsLogBean == null ){
            return null ;
        }

        
        LmsLog lmsLog = lmsLogDao.findByCodeAndValid(lmsLogBean.getCode(),VALID);
        

        if(lmsLog == null){
            return null;
        }

        beanToDomain(lmsLogBean,lmsLog,"id","code","version","createTime","updateTime","valid");

        lmsLogDao.save(lmsLog);

        domainToBean(lmsLog,lmsLogBean);

        return lmsLogBean;
    }

    @Override
    public LmsLogBean delete(LmsLogBean lmsLogBean) {

        if(lmsLogBean == null){
            return null ;
        }

        
        LmsLog lmsLog = lmsLogDao.findByCodeAndValid(lmsLogBean.getCode(),VALID);
        


        if(lmsLog == null){
            return null;
        }

        
        lmsLog.setValid(INVALID);
        lmsLogDao.save(lmsLog);
        

        domainToBean(lmsLog,lmsLogBean);

        return lmsLogBean;
    }

    @Override
    public LmsLogBean find(LmsLogBean lmsLogBean) {
        if(lmsLogBean == null){
            return null ;
        }

        
        LmsLog lmsLog = lmsLogDao.findByCodeAndValid(lmsLogBean.getCode(),VALID);
        


        if(lmsLog == null){
            return null ;
        }
        BeanUtils.copyProperties(lmsLog,lmsLogBean);
        return lmsLogBean;
    }

    @Override
    public LmsLogBean find(String code) {
        return  this.find(LmsLogBean.builder().code(code).build());
    }

    @Override
    public List<LmsLogBean> findAll(LmsLogBean lmsLogBean, PagerBean pagerBean) {
        List<LmsLogBean> lmsLogBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(lmsLogBean);
        List<LmsLog> lmsLogs = lmsLogDao.findAll(detachedCriteria,pagerBean);
        for (LmsLog lmsLog : lmsLogs) {
            LmsLogBean lmsLogBeanTemp = new LmsLogBean();
            domainToBean(lmsLog,lmsLogBeanTemp);
            lmsLogBeans.add(lmsLogBeanTemp);
        }
        return lmsLogBeans;
    }

    @Override
    public List<LmsLogBean> findAll(LmsLogBean lmsLogBean) {
       return  this.findAll(lmsLogBean,null);
    }

    @Override
     public Long countAll(LmsLogBean lmsLogBean) {
        DetachedCriteria detachedCriteria = createListCriteria(lmsLogBean);
        return lmsLogDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<LmsLogBean> findPager(LmsLogBean lmsLogBean, PagerBean pagerBean) {
        List<LmsLogBean> lmsLogBeans = this.findAll(lmsLogBean, pagerBean);
        Long count = this.countAll(lmsLogBean);
        PagerBean<LmsLogBean> lmsLogPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, lmsLogPageBean);
        lmsLogPageBean.setItemCount(count.intValue());
        lmsLogPageBean.init();
        lmsLogPageBean.setItems(lmsLogBeans);
        return  lmsLogPageBean ;
     }

     /**
      * 创建列表查询条件
      * @param lmsLogBean 查询参数
      * @return
      */
     private static DetachedCriteria createListCriteria(LmsLogBean lmsLogBean){
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LmsLog.class);
         detachedCriteria.add(Restrictions.eq("valid",VALID));
         return  detachedCriteria;
     }



    @Override
    public void batchDelete(List<LmsLogBean> lmsLogBean) {
        if(lmsLogBean.isEmpty()){
            return ;
        }
        List<String> codes = new ArrayList<>();
       lmsLogBean.forEach((e) ->{
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
            Assert.notNull(this.delete(LmsLogBean.builder().code(code).build()),"batch delete by codes error! ");
        }

    }

    


}
