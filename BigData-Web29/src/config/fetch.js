/**
 * Created by yue.hao on 2017/7/10.
 */
import { baseUrl } from './env'
import vm from '@/main'
import { Loading } from 'element-ui';
import util from '@/config/dataProcess'
export default async(url = '', data = {}, type = 'GET', notify = false, loading = false, method = 'fetch') => {
	type = type.toUpperCase();
	url = baseUrl + url;
	let token = util.getSystemToken()
	if(type == 'GET') {
		let dataStr = ''; //数据拼接字符串
		Object.keys(data).forEach(key => {
			dataStr += key + '=' + data[key] + '&';
		})

		if(dataStr !== '') {
			dataStr = dataStr.substr(0, dataStr.lastIndexOf('&'));
			url = url + '?' + dataStr;
		}
	}
	if(window.fetch && method == 'fetch') {
		let requestConfig = {
			credentials: 'include',
			method: type,
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				'IOVTOKEN': token
			},
			mode: "cors",
			cache: "no-cache"
		}
		if(type == 'POST') {
			Object.defineProperty(requestConfig, 'body', {
				value: JSON.stringify(data)
			})
		}
		let loadingInstance;
		if(loading) {
			loadingInstance = Loading.service({
				fullscreen: true,
				customClass: 'mes',
				text: '请稍后'
			});
		}
		const response = await fetch(url, requestConfig);
		if(loading && loadingInstance) {
			loadingInstance.close();
		}
		if(response.status == 200) {
			const responseJson = await response.json();
			if(notify && responseJson) {
				if(vm.$_notify) {
					vm.$_notify.close()
				}
				if(responseJson.status == "success" || responseJson.status == "PASS") {
					vm.$_notify = vm.$notify({
						title: '成功',
						message: responseJson.message || "操作成功",
						type: 'success',
						duration: 3000
					});
				} else {
					vm.$_notify = vm.$notify.error({
						title: '失败',
						message: responseJson.message || "fetch.js操作失败",
						duration: 3000
					});
				}
			}
			return responseJson
		} else if(response.status == 401) {
			if(!vm.$_overTime) {
				vm.$_overTime = vm.$notify.error({
					title: '失败',
					message: "登录授权失效,3秒后退出登录"
				});
				setTimeout(() => {
					window.location.href = "/"
				}, 3000)
			}
			return responseJson
		} else {
			vm.$_notify = vm.$notify.error({
				title: '失败',
				message: "操作失败,请联系管理员,错误代码" + response.status
			});
			return false
		}
	} else {
		return new Promise((resolve, reject) => {
			let requestObj;
			if(window.XMLHttpRequest) {
				requestObj = new XMLHttpRequest();
			} else {
				requestObj = new ActiveXObject;
			}
			let sendData = '';
			if(type == 'POST') {
				sendData = JSON.stringify(data);
			}

			requestObj.open(type, url, true);
			requestObj.setRequestHeader("Content-type", "application/json");
			requestObj.send(sendData);

			requestObj.onreadystatechange = () => {
				if(requestObj.readyState == 4) {
					if(requestObj.status == 200) {
						let obj = requestObj.response
						if(typeof obj !== 'object') {
							obj = JSON.parse(obj);
						}
						resolve(obj)
					} else {
						reject(requestObj)
					}
				}
			}
		})
	}
}