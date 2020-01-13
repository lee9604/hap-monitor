import Vue from 'vue'
import App from './App.vue'
import _ from 'lodash'
import router from './router'
import store from './store'


//引入element
import ElementUI from 'element-ui';
import {Message} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);


//引入Fastboot UI库
import  FastbootUI from 'fastboot-ui/src/components';
import 'fastboot-ui/src/scss/element-theme.scss'

Vue.use(FastbootUI);


import FastbootAdmin from 'fastboot-admin';
import DictSelect from "fastboot-admin/src/components/DictSelect.vue";
import OrgCascader from "fastboot-admin/src/components/OrgCascader.vue";
import DictText from "fastboot-admin/src/components/DictText.vue";
import AreaCascader from "fastboot-admin/src/components/AreaCascader.vue";
Vue.component(DictSelect.name,DictSelect);
Vue.component(OrgCascader.name,OrgCascader);
Vue.component(AreaCascader.name,AreaCascader);
Vue.component(DictText.name,DictText);


//引入全局工具和配置
import qs from 'qs'
import axios from 'axios'

//引入接口
import baseApi from './config/api'
let api = _.defaultsDeep(baseApi,FastbootAdmin.api);
router.addRoutes(FastbootAdmin.routes)

import moment from 'moment'

Vue.prototype.$moment = moment;
Vue.prototype.$api = api ;
Vue.prototype.$http = axios;
Vue.prototype.$qs = qs;
axios.defaults.withCredentials = true;

Vue.config.productionTip = false


//http响应拦截器
Vue.prototype.$http.interceptors.response.use(resp => {


  //如果未授权，则跳转至授权页面
  if(resp.data.status === 401){

    if(sessionStorage.getItem("login-user-info") != null){
      Message.warning("30分钟未操作自动退出登录");
    }

    sessionStorage.removeItem("login-user-info");




    router.push("/login");


  }

  //如果未授权，则跳转至授权页面
  if(resp.data.status === 403){
    router.push("/no-permission");
  }

  return resp;

}, error => {
  console.log(error);
  return Promise.reject(error)
});






//加载系统信息
axios.post(api.app.appInfo).then((resp)=>{

  window.appInfo = resp.data.data.appInfo;
  try {
    window.appSetting = JSON.parse(resp.data.data.appInfo.setting);
  }catch (e) {
    window.appSetting = {};
  }

  if(!window.appSetting){
    window.appSetting={};
  }

  if(window.appInfo.logo){
    window.appInfo.logoUrl = `${api.commonFile.download}?code=${window.appInfo.logo}`;
  }


  if(window.appInfo.name){
    document.title=window.appInfo.name;
  }else{
    document.title="未配置系统名称";
  }


  //路由拦截器
  router.beforeEach((to,form,next)=>{




    if(to.path.startsWith('/update-user-password')){
      next();
      if(window.updatePassword){
        Message.warning("请修改密码再进行操作！");
      }
      return;
    }


    if(sessionStorage.getItem("login-user-info") != null){


      let userInfo = JSON.parse(sessionStorage.getItem("login-user-info"));




      if(window.appSetting["UPDATE_PWD_INTERVAL"] && window.appSetting["UPDATE_PWD_INTERVAL"].value !== "0"){

        if(!userInfo.lastUpdatePwdTime){
          window.updatePassword = true;

          next('/update-user-password')
          return ;
        }

        if(userInfo.lastUpdatePwdTime <= parseInt(window.appSetting["UPDATE_PWD_INTERVAL"]) * 24 * 60 * 60 * 1000){
          next('/update-user-password');
          return;
        }

        next();



      }else{
        next();
      }

    }else{
      next();
    }

  });

  window.routeViewType = 'route';

  new Vue({
    router,
    store,
    render: h => h(App)
  }).$mount('#app');

});

