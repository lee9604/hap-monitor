package com.kuyuntech.hapmonitor.coreservice.service.core.impl;


import com.kuyuntech.hapmonitor.coreapi.service.core.DmsCameraService;
import com.kuyuntech.hapmonitor.coreservice.dao.core.DmsCameraDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuyuntech.hapmonitor.coreapi.bean.core.DmsCameraBean;
import com.kuyuntech.hapmonitor.coreservice.domain.core.DmsCamera;
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
* DmsCameraService
*
*/
@Service("dmsCameraService")
@Transactional(rollbackFor = Exception.class,transactionManager = "hapMonitorCoreServiceTransactionManager")
public class DmsCameraServiceImpl extends AbstractFastbootService<DmsCamera,DmsCameraBean> implements DmsCameraService {

    private static final Logger logger = LoggerFactory.getLogger(DmsCameraServiceImpl.class);

    @Autowired
    DmsCameraDao dmsCameraDao;

    

    @Override
    public DmsCameraBean add(DmsCameraBean dmsCameraBean) {

        DmsCamera dmsCamera = new DmsCamera();
        beanToDomain(dmsCameraBean,dmsCamera,"id");
        dmsCameraDao.save(dmsCamera);
        domainToBean(dmsCamera,dmsCameraBean);
        return dmsCameraBean;

    }

    @Override
    public DmsCameraBean update(DmsCameraBean dmsCameraBean) {

        if(dmsCameraBean == null ){
            return null ;
        }

        
        DmsCamera dmsCamera = dmsCameraDao.findByCodeAndValid(dmsCameraBean.getCode(),VALID);
        

        if(dmsCamera == null){
            return null;
        }

        beanToDomain(dmsCameraBean,dmsCamera,"id","code","version","createTime","updateTime","valid");

        dmsCameraDao.save(dmsCamera);

        domainToBean(dmsCamera,dmsCameraBean);

        return dmsCameraBean;
    }

    @Override
    public DmsCameraBean delete(DmsCameraBean dmsCameraBean) {

        if(dmsCameraBean == null){
            return null ;
        }

        
        DmsCamera dmsCamera = dmsCameraDao.findByCodeAndValid(dmsCameraBean.getCode(),VALID);
        


        if(dmsCamera == null){
            return null;
        }

        
        dmsCamera.setValid(INVALID);
        dmsCameraDao.save(dmsCamera);
        

        domainToBean(dmsCamera,dmsCameraBean);

        return dmsCameraBean;
    }

    @Override
    public DmsCameraBean find(DmsCameraBean dmsCameraBean) {
        if(dmsCameraBean == null){
            return null ;
        }

        
        DmsCamera dmsCamera = dmsCameraDao.findByCodeAndValid(dmsCameraBean.getCode(),VALID);
        


        if(dmsCamera == null){
            return null ;
        }
        BeanUtils.copyProperties(dmsCamera,dmsCameraBean);
        return dmsCameraBean;
    }

    @Override
    public DmsCameraBean find(String code) {
        return  this.find(DmsCameraBean.builder().code(code).build());
    }

    @Override
    public List<DmsCameraBean> findAll(DmsCameraBean dmsCameraBean, PagerBean pagerBean) {
        List<DmsCameraBean> dmsCameraBeans = new ArrayList<>();
        DetachedCriteria detachedCriteria = createListCriteria(dmsCameraBean);
        List<DmsCamera> dmsCameras = dmsCameraDao.findAll(detachedCriteria,pagerBean);
        for (DmsCamera dmsCamera : dmsCameras) {
            DmsCameraBean dmsCameraBeanTemp = new DmsCameraBean();
            domainToBean(dmsCamera,dmsCameraBeanTemp);
            dmsCameraBeans.add(dmsCameraBeanTemp);
        }
        return dmsCameraBeans;
    }

    @Override
    public List<DmsCameraBean> findAll(DmsCameraBean dmsCameraBean) {
       return  this.findAll(dmsCameraBean,null);
    }

    @Override
     public Long countAll(DmsCameraBean dmsCameraBean) {
        DetachedCriteria detachedCriteria = createListCriteria(dmsCameraBean);
        return dmsCameraDao.countAll(detachedCriteria);
    }

     @Override
     public PagerBean<DmsCameraBean> findPager(DmsCameraBean dmsCameraBean, PagerBean pagerBean) {
        List<DmsCameraBean> dmsCameraBeans = this.findAll(dmsCameraBean, pagerBean);
        Long count = this.countAll(dmsCameraBean);
        PagerBean<DmsCameraBean> dmsCameraPageBean = new PagerBean<>();
        BeanUtils.copyProperties(pagerBean, dmsCameraPageBean);
        dmsCameraPageBean.setItemCount(count.intValue());
        dmsCameraPageBean.init();
        dmsCameraPageBean.setItems(dmsCameraBeans);
        return  dmsCameraPageBean ;
     }

     /**
      * 创建列表查询条件
      * @param dmsCameraBean 查询参数
      * @return
      */
     private static DetachedCriteria createListCriteria(DmsCameraBean dmsCameraBean){
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DmsCamera.class);
         detachedCriteria.add(Restrictions.eq("valid",VALID));
         return  detachedCriteria;
     }



    @Override
    public void batchDelete(List<DmsCameraBean> dmsCameraBean) {
        if(dmsCameraBean.isEmpty()){
            return ;
        }
        List<String> codes = new ArrayList<>();
       dmsCameraBean.forEach((e) ->{
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
            Assert.notNull(this.delete(DmsCameraBean.builder().code(code).build()),"batch delete by codes error! ");
        }

    }

    


}
