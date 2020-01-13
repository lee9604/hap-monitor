package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.UmsRoleResourcesRelationService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsRoleResourcesRelationDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsRoleResourcesRelationBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsRoleResourcesRelation;
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
* UmsRoleResourcesRelationService
*
*/
@Service("umsRoleResourcesRelationService")
@Transactional(rollbackFor = Exception.class,transactionManager = "hapMonitorCoreServiceTransactionManager")
public class UmsRoleResourcesRelationServiceImpl extends AbstractFastbootService<UmsRoleResourcesRelation,UmsRoleResourcesRelationBean> implements UmsRoleResourcesRelationService {

    private static final Logger logger = LoggerFactory.getLogger(UmsRoleResourcesRelationServiceImpl.class);

    @Autowired
    UmsRoleResourcesRelationDao umsRoleResourcesRelationDao;

    

    @Override
    public UmsRoleResourcesRelationBean add(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean) {

        UmsRoleResourcesRelation umsRoleResourcesRelation = new UmsRoleResourcesRelation();
        beanToDomain(umsRoleResourcesRelationBean,umsRoleResourcesRelation,"id");
        umsRoleResourcesRelationDao.save(umsRoleResourcesRelation);
        domainToBean(umsRoleResourcesRelation,umsRoleResourcesRelationBean);
        return umsRoleResourcesRelationBean;

    }

    @Override
    public UmsRoleResourcesRelationBean update(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean) {

        if(umsRoleResourcesRelationBean == null ){
            return null ;
        }

        
        UmsRoleResourcesRelation umsRoleResourcesRelation = umsRoleResourcesRelationDao.findByCodeAndValid(umsRoleResourcesRelationBean.getCode(),VALID);
        

        if(umsRoleResourcesRelation == null){
            return null;
        }

        beanToDomain(umsRoleResourcesRelationBean,umsRoleResourcesRelation,"id","code","version","createTime","updateTime","valid");

        umsRoleResourcesRelationDao.save(umsRoleResourcesRelation);

        domainToBean(umsRoleResourcesRelation,umsRoleResourcesRelationBean);

        return umsRoleResourcesRelationBean;
    }

    @Override
    public UmsRoleResourcesRelationBean delete(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean) {

        if(umsRoleResourcesRelationBean == null){
            return null ;
        }

        
        UmsRoleResourcesRelation umsRoleResourcesRelation = umsRoleResourcesRelationDao.findByCodeAndValid(umsRoleResourcesRelationBean.getCode(),VALID);
        


        if(umsRoleResourcesRelation == null){
            return null;
        }

        
        umsRoleResourcesRelation.setValid(INVALID);
        umsRoleResourcesRelationDao.save(umsRoleResourcesRelation);
        

        domainToBean(umsRoleResourcesRelation,umsRoleResourcesRelationBean);

        return umsRoleResourcesRelationBean;
    }

    @Override
    public UmsRoleResourcesRelationBean find(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean) {
        if(umsRoleResourcesRelationBean == null){
            return null ;
        }

        
        UmsRoleResourcesRelation umsRoleResourcesRelation = umsRoleResourcesRelationDao.findByCodeAndValid(umsRoleResourcesRelationBean.getCode(),VALID);
        


        if(umsRoleResourcesRelation == null){
            return null ;
        }
        BeanUtils.copyProperties(umsRoleResourcesRelation,umsRoleResourcesRelationBean);
        return umsRoleResourcesRelationBean;
    }

    @Override
    public UmsRoleResourcesRelationBean find(String code) {
        return  this.find(UmsRoleResourcesRelationBean.builder().code(code).build());
    }

    @Override
    public List<UmsRoleResourcesRelationBean> findAll(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean, PagerBean pagerBean) {
        List<UmsRoleResourcesRelationBean> umsRoleResourcesRelationBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(umsRoleResourcesRelationBean);
        List<UmsRoleResourcesRelation> umsRoleResourcesRelations = umsRoleResourcesRelationDao.findAll(detachedCriteria,pagerBean);
        for (UmsRoleResourcesRelation umsRoleResourcesRelation : umsRoleResourcesRelations) {
            UmsRoleResourcesRelationBean umsRoleResourcesRelationBeanTemp = new UmsRoleResourcesRelationBean();
            domainToBean(umsRoleResourcesRelation,umsRoleResourcesRelationBeanTemp);
            umsRoleResourcesRelationBeans.add(umsRoleResourcesRelationBeanTemp);
        }
        return umsRoleResourcesRelationBeans;
    }

    @Override
    public List<UmsRoleResourcesRelationBean> findAll(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean) {
       return  this.findAll(umsRoleResourcesRelationBean,null);
    }

    @Override
     public Long countAll(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean) {
        DetachedCriteria detachedCriteria = createListCriteria(umsRoleResourcesRelationBean);
        return umsRoleResourcesRelationDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<UmsRoleResourcesRelationBean> findPager(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean, PagerBean pagerBean) {
        List<UmsRoleResourcesRelationBean> umsRoleResourcesRelationBeans = this.findAll(umsRoleResourcesRelationBean, pagerBean);
        Long count = this.countAll(umsRoleResourcesRelationBean);
        PagerBean<UmsRoleResourcesRelationBean> umsRoleResourcesRelationPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsRoleResourcesRelationPageBean);
        umsRoleResourcesRelationPageBean.setItemCount(count.intValue());
        umsRoleResourcesRelationPageBean.init();
        umsRoleResourcesRelationPageBean.setItems(umsRoleResourcesRelationBeans);
        return  umsRoleResourcesRelationPageBean ;
     }

     /**
      * 创建列表查询条件
      * @param umsRoleResourcesRelationBean 查询参数
      * @return
      */
     private static DetachedCriteria createListCriteria(UmsRoleResourcesRelationBean umsRoleResourcesRelationBean){
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UmsRoleResourcesRelation.class);
         detachedCriteria.add(Restrictions.eq("valid",VALID));
         return  detachedCriteria;
     }



    @Override
    public void batchDelete(List<UmsRoleResourcesRelationBean> umsRoleResourcesRelationBean) {
        if(umsRoleResourcesRelationBean.isEmpty()){
            return ;
        }
        List<String> codes = new ArrayList<>();
       umsRoleResourcesRelationBean.forEach((e) ->{
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
            Assert.notNull(this.delete(UmsRoleResourcesRelationBean.builder().code(code).build()),"batch delete by codes error! ");
        }

    }

    


}
