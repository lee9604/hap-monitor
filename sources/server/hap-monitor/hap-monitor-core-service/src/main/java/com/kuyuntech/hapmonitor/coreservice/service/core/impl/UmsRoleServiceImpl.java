package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.UmsRoleService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.UmsRoleDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.UmsRoleBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.UmsRole;
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
* UmsRoleService
*
*/
@Service("umsRoleService")
@Transactional(rollbackFor = Exception.class,transactionManager = "hapMonitorCoreServiceTransactionManager")
public class UmsRoleServiceImpl extends AbstractFastbootService<UmsRole,UmsRoleBean> implements UmsRoleService {

    private static final Logger logger = LoggerFactory.getLogger(UmsRoleServiceImpl.class);

    @Autowired
    UmsRoleDao umsRoleDao;

    

    @Override
    public UmsRoleBean add(UmsRoleBean umsRoleBean) {

        UmsRole umsRole = new UmsRole();
        beanToDomain(umsRoleBean,umsRole,"id");
        umsRoleDao.save(umsRole);
        domainToBean(umsRole,umsRoleBean);
        return umsRoleBean;

    }

    @Override
    public UmsRoleBean update(UmsRoleBean umsRoleBean) {

        if(umsRoleBean == null ){
            return null ;
        }

        
        UmsRole umsRole = umsRoleDao.findByCodeAndValid(umsRoleBean.getCode(),VALID);
        

        if(umsRole == null){
            return null;
        }

        beanToDomain(umsRoleBean,umsRole,"id","code","version","createTime","updateTime","valid");

        umsRoleDao.save(umsRole);

        domainToBean(umsRole,umsRoleBean);

        return umsRoleBean;
    }

    @Override
    public UmsRoleBean delete(UmsRoleBean umsRoleBean) {

        if(umsRoleBean == null){
            return null ;
        }

        
        UmsRole umsRole = umsRoleDao.findByCodeAndValid(umsRoleBean.getCode(),VALID);
        


        if(umsRole == null){
            return null;
        }

        
        umsRole.setValid(INVALID);
        umsRoleDao.save(umsRole);
        

        domainToBean(umsRole,umsRoleBean);

        return umsRoleBean;
    }

    @Override
    public UmsRoleBean find(UmsRoleBean umsRoleBean) {
        if(umsRoleBean == null){
            return null ;
        }

        
        UmsRole umsRole = umsRoleDao.findByCodeAndValid(umsRoleBean.getCode(),VALID);
        


        if(umsRole == null){
            return null ;
        }
        BeanUtils.copyProperties(umsRole,umsRoleBean);
        return umsRoleBean;
    }

    @Override
    public UmsRoleBean find(String code) {
        return  this.find(UmsRoleBean.builder().code(code).build());
    }

    @Override
    public List<UmsRoleBean> findAll(UmsRoleBean umsRoleBean, PagerBean pagerBean) {
        List<UmsRoleBean> umsRoleBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(umsRoleBean);
        List<UmsRole> umsRoles = umsRoleDao.findAll(detachedCriteria,pagerBean);
        for (UmsRole umsRole : umsRoles) {
            UmsRoleBean umsRoleBeanTemp = new UmsRoleBean();
            domainToBean(umsRole,umsRoleBeanTemp);
            umsRoleBeans.add(umsRoleBeanTemp);
        }
        return umsRoleBeans;
    }

    @Override
    public List<UmsRoleBean> findAll(UmsRoleBean umsRoleBean) {
       return  this.findAll(umsRoleBean,null);
    }

    @Override
     public Long countAll(UmsRoleBean umsRoleBean) {
        DetachedCriteria detachedCriteria = createListCriteria(umsRoleBean);
        return umsRoleDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<UmsRoleBean> findPager(UmsRoleBean umsRoleBean, PagerBean pagerBean) {
        List<UmsRoleBean> umsRoleBeans = this.findAll(umsRoleBean, pagerBean);
        Long count = this.countAll(umsRoleBean);
        PagerBean<UmsRoleBean> umsRolePageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, umsRolePageBean);
        umsRolePageBean.setItemCount(count.intValue());
        umsRolePageBean.init();
        umsRolePageBean.setItems(umsRoleBeans);
        return  umsRolePageBean ;
     }

     /**
      * 创建列表查询条件
      * @param umsRoleBean 查询参数
      * @return
      */
     private static DetachedCriteria createListCriteria(UmsRoleBean umsRoleBean){
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UmsRole.class);
         detachedCriteria.add(Restrictions.eq("valid",VALID));
         return  detachedCriteria;
     }



    @Override
    public void batchDelete(List<UmsRoleBean> umsRoleBean) {
        if(umsRoleBean.isEmpty()){
            return ;
        }
        List<String> codes = new ArrayList<>();
       umsRoleBean.forEach((e) ->{
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
            Assert.notNull(this.delete(UmsRoleBean.builder().code(code).build()),"batch delete by codes error! ");
        }

    }

    


}
