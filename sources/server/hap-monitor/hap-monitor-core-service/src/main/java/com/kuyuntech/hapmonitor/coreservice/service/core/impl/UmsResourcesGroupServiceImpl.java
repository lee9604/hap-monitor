package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.UmsResourcesGroupService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsResourcesGroupDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsResourcesGroupBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsResourcesGroup;
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
* UmsResourcesGroupService
*
*/
@Service("umsResourcesGroupService")
@Transactional(rollbackFor = Exception.class,transactionManager = "hapMonitorCoreServiceTransactionManager")
public class UmsResourcesGroupServiceImpl extends AbstractFastbootService<UmsResourcesGroup,UmsResourcesGroupBean> implements UmsResourcesGroupService {

    private static final Logger logger = LoggerFactory.getLogger(UmsResourcesGroupServiceImpl.class);

    @Autowired
    UmsResourcesGroupDao umsResourcesGroupDao;

    

    @Override
    public UmsResourcesGroupBean add(UmsResourcesGroupBean umsResourcesGroupBean) {

        UmsResourcesGroup umsResourcesGroup = new UmsResourcesGroup();
        beanToDomain(umsResourcesGroupBean,umsResourcesGroup,"id");
        umsResourcesGroupDao.save(umsResourcesGroup);
        domainToBean(umsResourcesGroup,umsResourcesGroupBean);
        return umsResourcesGroupBean;

    }

    @Override
    public UmsResourcesGroupBean update(UmsResourcesGroupBean umsResourcesGroupBean) {

        if(umsResourcesGroupBean == null ){
            return null ;
        }

        
        UmsResourcesGroup umsResourcesGroup = umsResourcesGroupDao.findByCodeAndValid(umsResourcesGroupBean.getCode(),VALID);
        

        if(umsResourcesGroup == null){
            return null;
        }

        beanToDomain(umsResourcesGroupBean,umsResourcesGroup,"id","code","version","createTime","updateTime","valid");

        umsResourcesGroupDao.save(umsResourcesGroup);

        domainToBean(umsResourcesGroup,umsResourcesGroupBean);

        return umsResourcesGroupBean;
    }

    @Override
    public UmsResourcesGroupBean delete(UmsResourcesGroupBean umsResourcesGroupBean) {

        if(umsResourcesGroupBean == null){
            return null ;
        }

        
        UmsResourcesGroup umsResourcesGroup = umsResourcesGroupDao.findByCodeAndValid(umsResourcesGroupBean.getCode(),VALID);
        


        if(umsResourcesGroup == null){
            return null;
        }

        
        umsResourcesGroup.setValid(INVALID);
        umsResourcesGroupDao.save(umsResourcesGroup);
        

        domainToBean(umsResourcesGroup,umsResourcesGroupBean);

        return umsResourcesGroupBean;
    }

    @Override
    public UmsResourcesGroupBean find(UmsResourcesGroupBean umsResourcesGroupBean) {
        if(umsResourcesGroupBean == null){
            return null ;
        }

        
        UmsResourcesGroup umsResourcesGroup = umsResourcesGroupDao.findByCodeAndValid(umsResourcesGroupBean.getCode(),VALID);
        


        if(umsResourcesGroup == null){
            return null ;
        }
        BeanUtils.copyProperties(umsResourcesGroup,umsResourcesGroupBean);
        return umsResourcesGroupBean;
    }

    @Override
    public UmsResourcesGroupBean find(String code) {
        return  this.find(UmsResourcesGroupBean.builder().code(code).build());
    }

    @Override
    public List<UmsResourcesGroupBean> findAll(UmsResourcesGroupBean umsResourcesGroupBean, PagerBean pagerBean) {
        List<UmsResourcesGroupBean> umsResourcesGroupBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(umsResourcesGroupBean);
        List<UmsResourcesGroup> umsResourcesGroups = umsResourcesGroupDao.findAll(detachedCriteria,pagerBean);
        for (UmsResourcesGroup umsResourcesGroup : umsResourcesGroups) {
            UmsResourcesGroupBean umsResourcesGroupBeanTemp = new UmsResourcesGroupBean();
            domainToBean(umsResourcesGroup,umsResourcesGroupBeanTemp);
            umsResourcesGroupBeans.add(umsResourcesGroupBeanTemp);
        }
        return umsResourcesGroupBeans;
    }

    @Override
    public List<UmsResourcesGroupBean> findAll(UmsResourcesGroupBean umsResourcesGroupBean) {
       return  this.findAll(umsResourcesGroupBean,null);
    }

    @Override
     public Long countAll(UmsResourcesGroupBean umsResourcesGroupBean) {
        DetachedCriteria detachedCriteria = createListCriteria(umsResourcesGroupBean);
        return umsResourcesGroupDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<UmsResourcesGroupBean> findPager(UmsResourcesGroupBean umsResourcesGroupBean, PagerBean pagerBean) {
        List<UmsResourcesGroupBean> umsResourcesGroupBeans = this.findAll(umsResourcesGroupBean, pagerBean);
        Long count = this.countAll(umsResourcesGroupBean);
        PagerBean<UmsResourcesGroupBean> umsResourcesGroupPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsResourcesGroupPageBean);
        umsResourcesGroupPageBean.setItemCount(count.intValue());
        umsResourcesGroupPageBean.init();
        umsResourcesGroupPageBean.setItems(umsResourcesGroupBeans);
        return  umsResourcesGroupPageBean ;
     }

     /**
      * 创建列表查询条件
      * @param umsResourcesGroupBean 查询参数
      * @return
      */
     private static DetachedCriteria createListCriteria(UmsResourcesGroupBean umsResourcesGroupBean){
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UmsResourcesGroup.class);
         detachedCriteria.add(Restrictions.eq("valid",VALID));
         return  detachedCriteria;
     }



    @Override
    public void batchDelete(List<UmsResourcesGroupBean> umsResourcesGroupBean) {
        if(umsResourcesGroupBean.isEmpty()){
            return ;
        }
        List<String> codes = new ArrayList<>();
       umsResourcesGroupBean.forEach((e) ->{
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
            Assert.notNull(this.delete(UmsResourcesGroupBean.builder().code(code).build()),"batch delete by codes error! ");
        }

    }

    


}
