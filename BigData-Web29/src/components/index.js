/**
 * Created by yue.hao on 2017/6/21.
 */
import Vue from 'vue'

//引入组件
import headMenu from "./headMenu"
import navBar from "./navBar";
import tableLayout from "./tableLayout";
import tree from "./tree";
import table from "./table";
import tableTree from "./tableTree";
import breadcrumb from "./breadcrumb";
import ztree from "./ztree";
//注册全局组件
Vue.component('mes-nav-menu', headMenu)
Vue.component('mes-nav-bar', navBar)
Vue.component('mes-table-layout', tableLayout)
Vue.component('mes-tree', tree)
Vue.component('mes-table', table)
Vue.component('mes-table-tree', tableTree)
Vue.component('mes-breadcrumb', breadcrumb)
Vue.component('mes-ztree', ztree)
