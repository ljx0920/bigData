/**
 * Created by yue.hao on 2017/6/21.
 */
/**
 * vue全家桶
 */
import Vue from 'vue'
import router from './router'
import store from './store'
/**
 * 核心文件
 */
import 'assets/style.css'
import 'assets/jquery/jquery.min'
import 'assets/ztree/jquery.ztree.core'
import 'assets/ztree/jquery.ztree.excheck'
import 'assets/ztree/jquery.ztree.exedit'
import 'assets/ztree/zTreeStyle.css'
import 'assets/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js'
import 'assets/mCustomScrollbar/jquery.mCustomScrollbar.min.css'
import App from './app'
import './directive'
/**
 * 工具库
 */
import util from '@/config/dataProcess'
Vue.prototype.$ = util
//UI组件库
import MesUI from './components'
import errLog from '@/store/errLog';
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import 'assets/fontawesome/css/font-awesome.min.css'
Vue.use(ElementUI)
//主题
import 'assets/theme/blue.css'
router.beforeEach((to, from, next) => {
	//通过不验证白名单
	if(to.path == 'login') {
		next()
	} else {
		next()
	}
});
//关闭生产模式的提示
Vue.config.productionTip = false
//实例化Vue(入口)
const vm = new Vue({
	el: '#mes',
	router,
	store,
	template: '<App/>',
	components: {
		App
	}
})
export default vm