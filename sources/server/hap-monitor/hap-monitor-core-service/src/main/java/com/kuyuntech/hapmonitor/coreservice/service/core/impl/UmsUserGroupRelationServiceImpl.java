package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserGroupRelationService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsUserGroupRelationDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserGroupRelationBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUserGroupRelation;
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
* UmsUserGroupRelationService
*
*/
@Service("umsUserGroupRelationService")
@Transactional(rollbackFor = Exception.class,transactionManager = "hapMonitorCoreServiceTransactionManager")
public class UmsUserGroupRelationServiceImpl extends AbstractFastbootService<UmsUserGroupRelation,UmsUserGroupRelationBean> implements UmsUserGroupRelationService {

    private static final Logger logger = LoggerFactory.getLogger(UmsUserGroupRelationServiceImpl.class);

    @Autowired
    UmsUserGroupRelationDao umsUserGroupRelationDao;

    

    @Override
    public UmsUserGroupRelationBean add(UmsUserGroupRelationBean umsUserGroupRelationBean) {

        UmsUserGroupRelation umsUserGroupRelation = new UmsUserGroupRelation();
        beanToDomain(umsUserGroupRelationBean,umsUserGroupRelation,"id");
        umsUserGroupRelationDao.save(umsUserGroupRelation);
        domainToBean(umsUserGroupRelation,umsUserGroupRelationBean);
        return umsUserGroupRelationBean;

    }

    @Override
    public UmsUserGroupRelationBean update(UmsUserGroupRelationBean umsUserGroupRelationBean) {

        if(umsUserGroupRelationBean == null ){
            return null ;
        }

        
        UmsUserGroupRelation umsUserGroupRelation = umsUserGroupRelationDao.findByCodeAndValid(umsUserGroupRelationBean.getCode(),VALID);
        

        if(umsUserGroupRelation == null){
            return null;
        }

        beanToDomain(umsUserGroupRelationBean,umsUserGroupRelation,"id","code","version","createTime","updateTime","valid");

        umsUserGroupRelationDao.save(umsUserGroupRelation);

        domainToBean(umsUserGroupRelation,umsUserGroupRelationBean);

        return umsUserGroupRelationBean;
    }

    @Override
    public UmsUserGroupRelationBean delete(UmsUserGroupRelationBean umsUserGroupRelationBean) {

        if(umsUserGroupRelationBean == null){
            return null ;
        }

        
        UmsUserGroupRelation umsUserGroupRelation = umsUserGroupRelationDao.findByCodeAndValid(umsUserGroupRelationBean.getCode(),VALID);
        


        if(umsUserGroupRelation == null){
            return null;
        }

        
        umsUserGroupRelation.setValid(INVALID);
        umsUserGroupRelationDao.save(umsUserGroupRelation);
        

        domainToBean(umsUserGroupRelation,umsUserGroupRelationBean);

        return umsUserGroupRelationBean;
    }

    @Override
    public UmsUserGroupRelationBean find(UmsUserGroupRelationBean umsUserGroupRelationBean) {
        if(umsUserGroupRelationBean == null){
            return null ;
        }

        
        UmsUserGroupRelation umsUserGroupRelation = umsUserGroupRelationDao.findByCodeAndValid(umsUserGroupRelationBean.getCode(),VALID);
        


        if(umsUserGroupRelation == null){
            return null ;
        }
        BeanUtils.copyProperties(umsUserGroupRelation,umsUserGroupRelationBean);
        return umsUserGroupRelationBean;
    }

    @Override
    public UmsUserGroupRelationBean find(String code) {
        return  this.find(UmsUserGroupRelationBean.builder().code(code).build());
    }

    @Override
    public List<UmsUserGroupRelationBean> findAll(UmsUserGroupRelationBean umsUserGroupRelationBean, PagerBean pagerBean) {
        List<UmsUserGroupRelationBean> umsUserGroupRelationBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(umsUserGroupRelationBean);
        List<UmsUserGroupRelation> umsUserGroupRelations = umsUserGroupRelationDao.findAll(detachedCriteria,pagerBean);
        for (UmsUserGroupRelation umsUserGroupRelation : umsUserGroupRelations) {
            UmsUserGroupRelationBean umsUserGroupRelationBeanTemp = new UmsUserGroupRelationBean();
            domainToBean(umsUserGroupRelation,umsUserGroupRelationBeanTemp);
            umsUserGroupRelationBeans.add(umsUserGroupRelationBeanTemp);
        }
        return umsUserGroupRelationBeans;
    }

    @Override
    public List<UmsUserGroupRelationBean> findAll(UmsUserGroupRelationBean umsUserGroupRelationBean) {
       return  this.findAll(umsUserGroupRelationBean,null);
    }

    @Override
     public Long countAll(UmsUserGroupRelationBean umsUserGroupRelationBean) {
        DetachedCriteria detachedCriteria = createListCriteria(umsUserGroupRelationBean);
        return umsUserGroupRelationDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<UmsUserGroupRelationBean> findPager(UmsUserGroupRelationBean umsUserGroupRelationBean, PagerBean pagerBean) {
        List<UmsUserGroupRelationBean> umsUserGroupRelationBeans = this.findAll(umsUserGroupRelationBean, pagerBean);
        Long count = this.countAll(umsUserGroupRelationBean);
        PagerBean<UmsUserGroupRelationBean> umsUserGroupRelationPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsUserGroupRelationPageBean);
        umsUserGroupRelationPageBean.setItemCount(count.intValue());
        umsUserGroupRelationPageBean.init();
        umsUserGroupRelationPageBean.setItems(umsUserGroupRelationBeans);
        return  umsUserGroupRelationPageBean ;
     }

     /**
      * 创建列表查询条件
      * @param umsUserGroupRelationBean 查询参数
      * @return
      */
     private static DetachedCriteria createListCriteria(UmsUserGroupRelationBean umsUserGroupRelationBean){
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UmsUserGroupRelation.class);
         detachedCriteria.add(Restrictions.eq("valid",VALID));
         return  detachedCriteria;
     }



    @Override
    public void batchDelete(List<UmsUserGroupRelationBean> umsUserGroupRelationBean) {
        if(umsUserGroupRelationBean.isEmpty()){
            return ;
        }
        List<String> codes = new ArrayList<>();
       umsUserGroupRelationBean.forEach((e) ->{
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
            Assert.notNull(this.delete(UmsUserGroupRelationBean.builder().code(code).build()),"batch delete by codes error! ");
        }

    }

    


}
