/**
 * Created by yue.hao on 2017/7/10.
 */
import Vue from 'vue'
import Router from 'vue-router'
//注册路由
Vue.use(Router)
const app = {
	login: resolve => require(['@/pages/login'], resolve),
	appIndex: resolve => require(['@/pages/appIndex'], resolve),
	err: resolve => require(['@/pages/404'], resolve),
}

let routes = [{
	path: '/',
	name: '登录',
	redirect: '/login'
}, {
	path: '/login',
	name: '登录',
	component: app.login
}, {
	path: '*',
	name: '404',
	component: app.err
}, {
	path: '/appIndex',
	name: '首页',
	component: app.appIndex
}]
//配置路由
export default new Router({
	routes
})