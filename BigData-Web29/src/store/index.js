import Vue from 'vue';
import Vuex from 'vuex';
import user from './modules/user';

Vue.use(Vuex);

const store = new Vuex.Store({
	modules: {
		user
	},
	getters: {
		userInfo: state => state.user.userInfo,
		menu: state => state.user.menu,
		routes: state => state.user.routes,
	}
});

export default store