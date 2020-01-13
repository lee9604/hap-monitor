package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.UmsUserService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsUserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsUserBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsUser;
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
* UmsUserService
*
*/
@Service("umsUserService")
@Transactional(rollbackFor = Exception.class,transactionManager = "hapMonitorCoreServiceTransactionManager")
public class UmsUserServiceImpl extends AbstractFastbootService<UmsUser,UmsUserBean> implements UmsUserService {

    private static final Logger logger = LoggerFactory.getLogger(UmsUserServiceImpl.class);

    @Autowired
    UmsUserDao umsUserDao;

    

    @Override
    public UmsUserBean add(UmsUserBean umsUserBean) {

        UmsUser umsUser = new UmsUser();
        beanToDomain(umsUserBean,umsUser,"id");
        umsUserDao.save(umsUser);
        domainToBean(umsUser,umsUserBean);
        return umsUserBean;

    }

    @Override
    public UmsUserBean update(UmsUserBean umsUserBean) {

        if(umsUserBean == null ){
            return null ;
        }

        
        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(),VALID);
        

        if(umsUser == null){
            return null;
        }

        beanToDomain(umsUserBean,umsUser,"id","code","version","createTime","updateTime","valid");

        umsUserDao.save(umsUser);

        domainToBean(umsUser,umsUserBean);

        return umsUserBean;
    }

    @Override
    public UmsUserBean delete(UmsUserBean umsUserBean) {

        if(umsUserBean == null){
            return null ;
        }

        
        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(),VALID);
        


        if(umsUser == null){
            return null;
        }

        
        umsUser.setValid(INVALID);
        umsUserDao.save(umsUser);
        

        domainToBean(umsUser,umsUserBean);

        return umsUserBean;
    }

    @Override
    public UmsUserBean find(UmsUserBean umsUserBean) {
        if(umsUserBean == null){
            return null ;
        }

        
        UmsUser umsUser = umsUserDao.findByCodeAndValid(umsUserBean.getCode(),VALID);
        


        if(umsUser == null){
            return null ;
        }
        BeanUtils.copyProperties(umsUser,umsUserBean);
        return umsUserBean;
    }

    @Override
    public UmsUserBean find(String code) {
        return  this.find(UmsUserBean.builder().code(code).build());
    }

    @Override
    public List<UmsUserBean> findAll(UmsUserBean umsUserBean, PagerBean pagerBean) {
        List<UmsUserBean> umsUserBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(umsUserBean);
        List<UmsUser> umsUsers = umsUserDao.findAll(detachedCriteria,pagerBean);
        for (UmsUser umsUser : umsUsers) {
            UmsUserBean umsUserBeanTemp = new UmsUserBean();
            domainToBean(umsUser,umsUserBeanTemp);
            umsUserBeans.add(umsUserBeanTemp);
        }
        return umsUserBeans;
    }

    @Override
    public List<UmsUserBean> findAll(UmsUserBean umsUserBean) {
       return  this.findAll(umsUserBean,null);
    }

    @Override
     public Long countAll(UmsUserBean umsUserBean) {
        DetachedCriteria detachedCriteria = createListCriteria(umsUserBean);
        return umsUserDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<UmsUserBean> findPager(UmsUserBean umsUserBean, PagerBean pagerBean) {
        List<UmsUserBean> umsUserBeans = this.findAll(umsUserBean, pagerBean);
        Long count = this.countAll(umsUserBean);
        PagerBean<UmsUserBean> umsUserPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsUserPageBean);
        umsUserPageBean.setItemCount(count.intValue());
        umsUserPageBean.init();
        umsUserPageBean.setItems(umsUserBeans);
        return  umsUserPageBean ;
     }

     /**
      * 创建列表查询条件
      * @param umsUserBean 查询参数
      * @return
      */
     private static DetachedCriteria createListCriteria(UmsUserBean umsUserBean){
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UmsUser.class);
         detachedCriteria.add(Restrictions.eq("valid",VALID));
         return  detachedCriteria;
     }



    @Override
    public void batchDelete(List<UmsUserBean> umsUserBean) {
        if(umsUserBean.isEmpty()){
            return ;
        }
        List<String> codes = new ArrayList<>();
       umsUserBean.forEach((e) ->{
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
            Assert.notNull(this.delete(UmsUserBean.builder().code(code).build()),"batch delete by codes error! ");
        }

    }

    


}
