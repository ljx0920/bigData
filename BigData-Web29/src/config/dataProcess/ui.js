import vm from '@/main'
import regular from './regular/index'
import store from '@/store'
/**
 * MES内部通用函数
 */
export default {
	/**
	 * 监听全局resize事件
	 */
	addResizeLisener() {
		//		window.onresize = this.debounce(() => {
		//			vm.$notify({
		//				title: '提示',
		//				message: '检测到您调整了窗口大小,建议刷新页面以便调整到最佳使用效果(F5)',
		//				type: 'warning',
		//				duration: 3000
		//			});
		//		}, 1000)
	},
	/**
	 * 校验规则
	 */
	regular: regular,
	delete(tips, callback) {
		vm.$confirm(tips, '提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning'
		}).then(() => {
			callback()
		}).catch(() => {
			vm.$message({
				type: 'info',
				message: '已取消'
			});
		});
	},
	/**
	 * 确认提示
	 * @param tips
	 * @param callback
	 */
	sureTip(tips, callback) {
		vm.$confirm(tips, '提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning'
		}).then(() => {
			callback()
		}).catch(() => {
			vm.$message({
				type: 'info',
				message: '已取消'
			});
		});
	},

	/**
	 * 查重校验
	 */
	checkRepeat(fun, id, key = "name", text = "该名称已被使用", extra) {
		return this.debounce((rule, value, callback) => {
			if(value) {
				let data = {}
				data[key] = value
				if(extra) {
					for(let x in extra) {
						data[x] = extra[x];
						if(extra[x] == "") {
							callback()
						}
					}
				}
				fun(data).then((res) => {
					//编辑
					if(id) {
						if(res.content.length > 0 && id != res.content[0].id) {
							callback(new Error(text));
						} else {
							callback()
						}
					}
					//新建
					else {
						if(res.content.length > 0) {
							callback(new Error(text));
						} else {
							callback()
						}
					}
				})
			}
		}, 1000)
	},
	/**
	 * 对于非字符值控件必填项校验
	 */
	validateRequire(msg) {
		return(rule, value, callback) => {
			let flag = false
			if(this.isArray(value)) {
				flag = value.length == 0
			} else if(this.isString(value)) {
				flag = value == ""
			} else {
				flag = !value
			}
			if(!flag) {
				callback()
			} else {
				callback(new Error(msg));
			}
		};
	},
	getSystemType() {
		return {
			"manageSystem": "MM",
			"workstation": "WS"
		}
	},
	getSystemToken() {
		let token = ""
		let systemType = store.getters.systemType
		if(systemType) {
			if(systemType == this.getSystemType().workstation) {
				token = this.cookie.getCookie("WSTOKEN")
			} else if(systemType == this.getSystemType().manageSystem) {
				token = this.cookie.getCookie("MMTOKEN")
			}
		}
		return token
	},
	getRootPath() {
		let path = ""
		let systemType = store.getters.systemType
		if(systemType) {
			if(systemType == this.getSystemType().workstation) {
				path = "/workstation"
			} else if(systemType == this.getSystemType().manageSystem) {
				path = "/"
			}
		}
		return path
	}

}