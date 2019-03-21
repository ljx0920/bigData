import { login, queryData } from './service'
import util from '@/config/dataProcess'

function permission(data) {
	let res = data.filter((val) => {
		return !val.parentId || val.parentId == "0"
	})
	res = util.buildTree(res, data)
	res = util.deepSort(res, 'itemOrder', 'children')
	return res
}

function getRoutes(menu) {
	function getRedirect(data) {
		let url = ""
		if(data.children && data.children.length > 0) {
			let childData = data.children[0]
			if(childData.children && childData.children.length > 0) {
				url = childData.children[0].url
			} else {
				url = childData.url
			}
		}
		return url
	}
	let _res = [{
		path: '/appIndex',
		component: resolve => require(['pages/appIndex'], resolve),
		children: []
	}]
	for(let x of menu) {
		let _x = {
			path: x.url,
			redirect: getRedirect(x),
			name: x.name,
			component: resolve => require(['pages/' + x.url.slice(1, x.url.length) + '/index'], resolve),
			children: []
		}
		if(x.children) {
			for(let y of x.children) {
				if(y.url) {
					_x.children.push({
						path: y.url,
						name: y.name,
						component: resolve => require(['pages/' + y.url.slice(1, y.url.length) + '/index'], resolve)
					})
				}
				if(y.children) {
					for(let z of y.children) {
						_x.children.push({
							path: z.url,
							name: z.name,
							component: resolve => require(['pages/' + z.url.slice(1, z.url.length) + '/index'], resolve)
						})
					}
				}
			}
		}
		_res[0].children.push(_x)
	}
	return _res;
}
const user = {
	state: {
		userInfo: '',
		menu: '',
		routes: '',
	},
	mutations: {
		SET_USERINFO: (state, userInfo) => {
			state.userInfo = userInfo;
		},
		SET_MENU: (state, menu) => {
			state.menu = menu;
		},
		SET_ROUTES: (state, routes) => {
			state.routes = routes;
		}
	},
	actions: {
		//登录
		login({
			commit
		}, userInfo) {
			return new Promise((resolve, reject) => {
				login(userInfo).then((res) => {
					if(res.status == "success" && res.content) {
						let data = res.content;
						let menu = permission(getMenu());
						commit('SET_USERINFO', data.userInfo);
						commit('SET_MENU', menu);
						commit('SET_ROUTES', getRoutes(menu));
						resolve(res)
					}
				});
			});
		}
	}
};

function getMenu() {
	let menu = [{
		"id": "1000",
		"createDate": null,
		"updateDate": null,
		"expandMap": null,
		"parentId": "0",
		"parentName": null,
		"level": 1,
		"name": "首页",
		"url": "/home",
		"description": "首页",
		"itemOrder": 1,
		"icon": "1.png",
		"subSysMenus": null
	}, {
		"id": "1100",
		"createDate": null,
		"updateDate": null,
		"expandMap": null,
		"parentId": "1000",
		"parentName": "首页",
		"level": 2,
		"name": "配置管理",
		"url": "/home/manage",
		"description": "配置管理",
		"itemOrder": 1,
		"icon": "sysManage/1.png",
		"subSysMenus": null
	}, {
		"id": "1200",
		"createDate": null,
		"updateDate": null,
		"expandMap": null,
		"parentId": "1000",
		"parentName": "首页",
		"level": 2,
		"name": "数据上传",
		"url": "/home/dataimport",
		"description": "数据上传",
		"itemOrder": 2,
		"icon": "sysManage/2.png",
		"subSysMenus": null
	}, {
		"id": "1300",
		"createDate": null,
		"updateDate": null,
		"expandMap": null,
		"parentId": "1000",
		"parentName": "首页",
		"level": 2,
		"name": "最近上传",
		"url": "/home/recentimport",
		"description": "最近上传",
		"itemOrder": 3,
		"icon": "sysManage/3.png",
		"subSysMenus": null
<<<<<<< HEAD
	}, 
// 	{
// 		"id": "1400",
// 		"createDate": null,
// 		"updateDate": null,
// 		"expandMap": null,
// 		"parentId": "1000",
// 		"parentName": "首页",
// 		"level": 2,
// 		"name": "数据查询",
// 		"url": "/home/dataquery",
// 		"description": "数据查询",
// 		"itemOrder": 4,
// 		"icon": "sysManage/4.png",
// 		"subSysMenus": null
// 	},
// 	{
// 		"id": "1500",
// 		"createDate": null,
// 		"updateDate": null,
// 		"expandMap": null,
// 		"parentId": "1000",
// 		"parentName": "首页",
// 		"level": 2,
// 		"name": "数据导入",
// 		"url": "/home/dataImportNew",
// 		"description": "数据导入",
// 		"itemOrder": 5,
// 		"icon": "sysManage/2.png",
// 		"subSysMenus": null
// 	},
	{
=======
	}, {
		"id": "1400",
		"createDate": null,
		"updateDate": null,
		"expandMap": null,
		"parentId": "1000",
		"parentName": "首页",
		"level": 2,
		"name": "数据查询",
		"url": "/home/dataquery",
		"description": "数据查询",
		"itemOrder": 4,
		"icon": "sysManage/4.png",
		"subSysMenus": null
	},
	{
		"id": "1500",
		"createDate": null,
		"updateDate": null,
		"expandMap": null,
		"parentId": "1000",
		"parentName": "首页",
		"level": 2,
		"name": "数据导入",
		"url": "/home/dataImportNew",
		"description": "数据导入",
		"itemOrder": 5,
		"icon": "sysManage/2.png",
		"subSysMenus": null
	},{
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
		"id": "1600",
		"createDate": null,
		"updateDate": null,
		"expandMap": null,
		"parentId": "1000",
		"parentName": "首页",
		"level": 2,
		"name": "查询与下载",
		"url": "/home/dataQueryDownlode",
		"description": "查询与下载",
		"itemOrder": 6,
		"icon": "sysManage/4.png",
		"subSysMenus": null
	}];
	return menu;
};
export default user;