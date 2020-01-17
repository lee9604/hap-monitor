package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.UmsResourcesGroupResourcesRelationService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsResourcesGroupResourcesRelationDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsResourcesGroupResourcesRelationBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsResourcesGroupResourcesRelation;
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
* UmsResourcesGroupResourcesRelationService
*
*/
@Service("umsResourcesGroupResourcesRelationService")
@Transactional(rollbackFor = Exception.class,transactionManager = "hapMonitorCoreServiceTransactionManager")
public class UmsResourcesGroupResourcesRelationServiceImpl extends AbstractFastbootService<UmsResourcesGroupResourcesRelation,UmsResourcesGroupResourcesRelationBean> implements UmsResourcesGroupResourcesRelationService {

    private static final Logger logger = LoggerFactory.getLogger(UmsResourcesGroupResourcesRelationServiceImpl.class);

    @Autowired
    UmsResourcesGroupResourcesRelationDao umsResourcesGroupResourcesRelationDao;

    

    @Override
    public UmsResourcesGroupResourcesRelationBean add(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean) {

        UmsResourcesGroupResourcesRelation umsResourcesGroupResourcesRelation = new UmsResourcesGroupResourcesRelation();
        beanToDomain(umsResourcesGroupResourcesRelationBean,umsResourcesGroupResourcesRelation,"id");
        umsResourcesGroupResourcesRelationDao.save(umsResourcesGroupResourcesRelation);
        domainToBean(umsResourcesGroupResourcesRelation,umsResourcesGroupResourcesRelationBean);
        return umsResourcesGroupResourcesRelationBean;

    }

    @Override
    public UmsResourcesGroupResourcesRelationBean update(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean) {

        if(umsResourcesGroupResourcesRelationBean == null ){
            return null ;
        }

        
        UmsResourcesGroupResourcesRelation umsResourcesGroupResourcesRelation = umsResourcesGroupResourcesRelationDao.findByCodeAndValid(umsResourcesGroupResourcesRelationBean.getCode(),VALID);
        

        if(umsResourcesGroupResourcesRelation == null){
            return null;
        }

        beanToDomain(umsResourcesGroupResourcesRelationBean,umsResourcesGroupResourcesRelation,"id","code","version","createTime","updateTime","valid");

        umsResourcesGroupResourcesRelationDao.save(umsResourcesGroupResourcesRelation);

        domainToBean(umsResourcesGroupResourcesRelation,umsResourcesGroupResourcesRelationBean);

        return umsResourcesGroupResourcesRelationBean;
    }

    @Override
    public UmsResourcesGroupResourcesRelationBean delete(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean) {

        if(umsResourcesGroupResourcesRelationBean == null){
            return null ;
        }

        
        UmsResourcesGroupResourcesRelation umsResourcesGroupResourcesRelation = umsResourcesGroupResourcesRelationDao.findByCodeAndValid(umsResourcesGroupResourcesRelationBean.getCode(),VALID);
        


        if(umsResourcesGroupResourcesRelation == null){
            return null;
        }

        
        umsResourcesGroupResourcesRelation.setValid(INVALID);
        umsResourcesGroupResourcesRelationDao.save(umsResourcesGroupResourcesRelation);
        

        domainToBean(umsResourcesGroupResourcesRelation,umsResourcesGroupResourcesRelationBean);

        return umsResourcesGroupResourcesRelationBean;
    }

    @Override
    public UmsResourcesGroupResourcesRelationBean find(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean) {
        if(umsResourcesGroupResourcesRelationBean == null){
            return null ;
        }

        
        UmsResourcesGroupResourcesRelation umsResourcesGroupResourcesRelation = umsResourcesGroupResourcesRelationDao.findByCodeAndValid(umsResourcesGroupResourcesRelationBean.getCode(),VALID);
        


        if(umsResourcesGroupResourcesRelation == null){
            return null ;
        }
        BeanUtils.copyProperties(umsResourcesGroupResourcesRelation,umsResourcesGroupResourcesRelationBean);
        return umsResourcesGroupResourcesRelationBean;
    }

    @Override
    public UmsResourcesGroupResourcesRelationBean find(String code) {
        return  this.find(UmsResourcesGroupResourcesRelationBean.builder().code(code).build());
    }

    @Override
    public List<UmsResourcesGroupResourcesRelationBean> findAll(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean, PagerBean pagerBean) {
        List<UmsResourcesGroupResourcesRelationBean> umsResourcesGroupResourcesRelationBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(umsResourcesGroupResourcesRelationBean);
        List<UmsResourcesGroupResourcesRelation> umsResourcesGroupResourcesRelations = umsResourcesGroupResourcesRelationDao.findAll(detachedCriteria,pagerBean);
        for (UmsResourcesGroupResourcesRelation umsResourcesGroupResourcesRelation : umsResourcesGroupResourcesRelations) {
            UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBeanTemp = new UmsResourcesGroupResourcesRelationBean();
            domainToBean(umsResourcesGroupResourcesRelation,umsResourcesGroupResourcesRelationBeanTemp);
            umsResourcesGroupResourcesRelationBeans.add(umsResourcesGroupResourcesRelationBeanTemp);
        }
        return umsResourcesGroupResourcesRelationBeans;
    }

    @Override
    public List<UmsResourcesGroupResourcesRelationBean> findAll(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean) {
       return  this.findAll(umsResourcesGroupResourcesRelationBean,null);
    }

    @Override
     public Long countAll(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean) {
        DetachedCriteria detachedCriteria = createListCriteria(umsResourcesGroupResourcesRelationBean);
        return umsResourcesGroupResourcesRelationDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<UmsResourcesGroupResourcesRelationBean> findPager(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean, PagerBean pagerBean) {
        List<UmsResourcesGroupResourcesRelationBean> umsResourcesGroupResourcesRelationBeans = this.findAll(umsResourcesGroupResourcesRelationBean, pagerBean);
        Long count = this.countAll(umsResourcesGroupResourcesRelationBean);
        PagerBean<UmsResourcesGroupResourcesRelationBean> umsResourcesGroupResourcesRelationPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsResourcesGroupResourcesRelationPageBean);
        umsResourcesGroupResourcesRelationPageBean.setItemCount(count.intValue());
        umsResourcesGroupResourcesRelationPageBean.init();
        umsResourcesGroupResourcesRelationPageBean.setItems(umsResourcesGroupResourcesRelationBeans);
        return  umsResourcesGroupResourcesRelationPageBean ;
     }

     /**
      * 创建列表查询条件
      * @param umsResourcesGroupResourcesRelationBean 查询参数
      * @return
      */
     private static DetachedCriteria createListCriteria(UmsResourcesGroupResourcesRelationBean umsResourcesGroupResourcesRelationBean){
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UmsResourcesGroupResourcesRelation.class);
         detachedCriteria.add(Restrictions.eq("valid",VALID));
         return  detachedCriteria;
     }



    @Override
    public void batchDelete(List<UmsResourcesGroupResourcesRelationBean> umsResourcesGroupResourcesRelationBean) {
        if(umsResourcesGroupResourcesRelationBean.isEmpty()){
            return ;
        }
        List<String> codes = new ArrayList<>();
       umsResourcesGroupResourcesRelationBean.forEach((e) ->{
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
            Assert.notNull(this.delete(UmsResourcesGroupResourcesRelationBean.builder().code(code).build()),"batch delete by codes error! ");
        }

    }

    


}
