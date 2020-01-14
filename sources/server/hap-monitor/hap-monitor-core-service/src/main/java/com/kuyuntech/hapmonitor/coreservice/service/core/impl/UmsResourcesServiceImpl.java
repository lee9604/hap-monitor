package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.UmsResourcesService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsResourcesDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsResourcesBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsResources;
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
* UmsResourcesService
*
*/
@Service("umsResourcesService")
@Transactional(rollbackFor = Exception.class,transactionManager = "hapMonitorCoreServiceTransactionManager")
public class UmsResourcesServiceImpl extends AbstractFastbootService<UmsResources,UmsResourcesBean> implements UmsResourcesService {

    private static final Logger logger = LoggerFactory.getLogger(UmsResourcesServiceImpl.class);

    @Autowired
    UmsResourcesDao umsResourcesDao;

    

    @Override
    public UmsResourcesBean add(UmsResourcesBean umsResourcesBean) {

        UmsResources umsResources = new UmsResources();
        beanToDomain(umsResourcesBean,umsResources,"id");
        umsResourcesDao.save(umsResources);
        domainToBean(umsResources,umsResourcesBean);
        return umsResourcesBean;

    }

    @Override
    public UmsResourcesBean update(UmsResourcesBean umsResourcesBean) {

        if(umsResourcesBean == null ){
            return null ;
        }

        
        UmsResources umsResources = umsResourcesDao.findByCodeAndValid(umsResourcesBean.getCode(),VALID);
        

        if(umsResources == null){
            return null;
        }

        beanToDomain(umsResourcesBean,umsResources,"id","code","version","createTime","updateTime","valid");

        umsResourcesDao.save(umsResources);

        domainToBean(umsResources,umsResourcesBean);

        return umsResourcesBean;
    }

    @Override
    public UmsResourcesBean delete(UmsResourcesBean umsResourcesBean) {

        if(umsResourcesBean == null){
            return null ;
        }

        
        UmsResources umsResources = umsResourcesDao.findByCodeAndValid(umsResourcesBean.getCode(),VALID);
        


        if(umsResources == null){
            return null;
        }

        
        umsResources.setValid(INVALID);
        umsResourcesDao.save(umsResources);
        

        domainToBean(umsResources,umsResourcesBean);

        return umsResourcesBean;
    }

    @Override
    public UmsResourcesBean find(UmsResourcesBean umsResourcesBean) {
        if(umsResourcesBean == null){
            return null ;
        }

        
        UmsResources umsResources = umsResourcesDao.findByCodeAndValid(umsResourcesBean.getCode(),VALID);
        


        if(umsResources == null){
            return null ;
        }
        BeanUtils.copyProperties(umsResources,umsResourcesBean);
        return umsResourcesBean;
    }

    @Override
    public UmsResourcesBean find(String code) {
        return  this.find(UmsResourcesBean.builder().code(code).build());
    }

    @Override
    public List<UmsResourcesBean> findAll(UmsResourcesBean umsResourcesBean, PagerBean pagerBean) {
        List<UmsResourcesBean> umsResourcesBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(umsResourcesBean);
        List<UmsResources> umsResourcess = umsResourcesDao.findAll(detachedCriteria,pagerBean);
        for (UmsResources umsResources : umsResourcess) {
            UmsResourcesBean umsResourcesBeanTemp = new UmsResourcesBean();
            domainToBean(umsResources,umsResourcesBeanTemp);
            umsResourcesBeans.add(umsResourcesBeanTemp);
        }
        return umsResourcesBeans;
    }

    @Override
    public List<UmsResourcesBean> findAll(UmsResourcesBean umsResourcesBean) {
       return  this.findAll(umsResourcesBean,null);
    }

    @Override
     public Long countAll(UmsResourcesBean umsResourcesBean) {
        DetachedCriteria detachedCriteria = createListCriteria(umsResourcesBean);
        return umsResourcesDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<UmsResourcesBean> findPager(UmsResourcesBean umsResourcesBean, PagerBean pagerBean) {
        List<UmsResourcesBean> umsResourcesBeans = this.findAll(umsResourcesBean, pagerBean);
        Long count = this.countAll(umsResourcesBean);
        PagerBean<UmsResourcesBean> umsResourcesPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsResourcesPageBean);
        umsResourcesPageBean.setItemCount(count.intValue());
        umsResourcesPageBean.init();
        umsResourcesPageBean.setItems(umsResourcesBeans);
        return  umsResourcesPageBean ;
     }

     /**
      * 创建列表查询条件
      * @param umsResourcesBean 查询参数
      * @return
      */
     private static DetachedCriteria createListCriteria(UmsResourcesBean umsResourcesBean){
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UmsResources.class);
         detachedCriteria.add(Restrictions.eq("valid",VALID));
         return  detachedCriteria;
     }



    @Override
    public void batchDelete(List<UmsResourcesBean> umsResourcesBean) {
        if(umsResourcesBean.isEmpty()){
            return ;
        }
        List<String> codes = new ArrayList<>();
       umsResourcesBean.forEach((e) ->{
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
            Assert.notNull(this.delete(UmsResourcesBean.builder().code(code).build()),"batch delete by codes error! ");
        }

    }

    @Override
    public List<UmsResourcesBean> findUmsResourcesByIdIn(List<Long> resourcesIdList) {

        List<UmsResources> umsResourcesList = umsResourcesDao.findUmsResourcesByIdIn(resourcesIdList);

        List<UmsResourcesBean> umsResourcesBeanList = new ArrayList<>();

        for (UmsResources umsResources : umsResourcesList) {
            UmsResourcesBean umsResourcesBean = new UmsResourcesBean();
            BeanUtils.copyProperties(umsResources, umsResourcesBean);
            umsResourcesBeanList.add(umsResourcesBean);
        }

        return umsResourcesBeanList;
    }


}
