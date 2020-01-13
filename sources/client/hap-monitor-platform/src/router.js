import Vue from 'vue'
import Router from 'vue-router'
import routes from './config/routes'
Vue.use(Router);
console.log(routes);
export default new Router({
    // mode: 'history',
    // base: process.env.BASE_URL,
    routes: routes
})
