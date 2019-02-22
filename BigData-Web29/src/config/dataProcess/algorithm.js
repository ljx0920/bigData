/**
 * 常用算法及数据处理
 */
export default {
	clientHeight() {
		return window.innerHeight
	},
	clientWidth() {
		return window.innerWidth
	},
	cookie: {
		getCookie: function(name) {
			let val = ''
			let reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)")
			let tokenInfo = document.cookie.match(reg)
			if(tokenInfo) {
				val = tokenInfo[2]
			}
			return val
		},
		setCookie: function(name, value) {
			document.cookie = name + "=" + value
		}
	},
	/**
	 * 函数防抖
	 */
	debounce(fn, delay) {
		let timer = null;
		return function() {
			let context = this;
			let args = arguments;
			clearTimeout(timer);
			timer = setTimeout(function() {
				fn.apply(context, args);
			}, delay);
		}
	},
	/**
	 * 函数节流
	 */
	throttle(fn, delay) {
		let last = 0
		return function() {
			let curr = +new Date()
			if(curr - last > delay) {
				fn.apply(this, arguments)
				last = curr
			}
		}
	},
	/**
	 * 获取鼠标绝对位置
	 */
	mousePosition(ev) {
		if(ev.pageX || ev.pageY) {
			return {
				x: ev.pageX,
				y: ev.pageY
			};
		}
		return {
			x: ev.clientX + document.documentElement.scrollLeft - document.body.clientLeft,
			y: ev.clientY + document.documentElement.scrollTop - document.body.clientTop
		};
	},
	/**
	 * 获取元素绝对位置
	 */
	getElCoordinate(dom) {
		let t = dom.offsetTop;
		let l = dom.offsetLeft;
		dom = dom.offsetParent;
		while(dom) {
			t += dom.offsetTop;
			l += dom.offsetLeft;
			dom = dom.offsetParent;
		};
		return {
			top: t,
			left: l
		};
	},
	/**
	 * 数组去重
	 */
	uniq(arr) {
		return Array.from(new Set(arr))
	},
	/**
	 * 按照某种自定义规则批量修改数组中每一个对象的某个key值（用于某些不可使用filter的情形）
	 * 例：let arr = [{a:1,b:1},{a:1,b:1}]
	 * $.maps(arr,"a",function(val){
	 * 	return val++
	 * })
	 * ==> [{a:2,b:1},{a:2,b:1}]
	 */
	maps(arr, key, fun) {
		for(let x of arr) {
			x[key] = fun(x[key])
		}
		return arr
	},
	/**
	 * 统一修改yyyy-MM-dd HH:mm:ss时间格式
	 */
	parseDate(date) {
		return date.toLocaleString('chinese', {
			hour12: false
		}).replace(/\//g, "-")
	},
	/**
	 * 将id、parentId转换成children格式
	 */
	transformTree(data, option = {}) {
		let res;
		let _option = {
			pNode: "数据列表",
			listArray: false,
			name: null,
			keepParent: true,
			disabled: function(val) {
				return false
			}
		}
		for(let x in option) {
			_option[x] = option[x]
		}
		if(_option.listArray) {
			let pro = (arr) => {
				arr.forEach((val) => {
					if(_option.name) {
						val.name = val[_option.name]
					}
					if(_option.disabled) {
						val.disabled = _option.disabled(val)
					}
					if(!val.leaf) {
						val.isParent = true
						if(!val.children) {
							val.children = []
						} else {
							pro(val.children)
						}
					}
				})
			}
			pro(data)
			res = data
		} else {
			data.forEach((val) => {
				if(_option.name) {
					val.name = val[_option.name]
				}
				if(_option.keepParent) {
					val.isParent = _option.keepParent
				}
				if(_option.disabled) {
					val.disabled = _option.disabled(val)
				}
			})
			res = [{
				id: 0,
				parentId: null,
				isRoot: true,
				name: _option.pNode,
				children: data
			}]
		}
		if(option.pNode) {
			res.pNode = option.pNode
		}
		return res
	},
	buildTree(res, data, name, keepParent) {
		function build(obj) {
			if(keepParent) {
				obj.children ? obj.children : obj.children = []
			}
			for(let x of data) {
				if(x.parentId && x.parentId == obj.id) {
					obj.children ? obj.children : obj.children = []
					if(name) {
						x.name = x[name]
					}
					obj.children.push(x)
					build(x)
				} else if(x.pId && x.pId == obj.id) {
					obj.children ? obj.children : obj.children = []
					if(name) {
						x.name = x[name]
					}
					obj.children.push(x)
					build(x)
				}
			}
		}
		for(let x of res) {
			build(x)
		}
		return res
	},
	deepSort(res, key, children) {
		function deepSort(arr) {
			function sort(a, b) {
				return a[key] - b[key]
			}
			arr.sort(sort)
			for(let x of arr) {
				if(x[children]) {
					deepSort(x[children])
				}
			}
		}
		deepSort(res)
		return res
	},
	/**
	 * 将children转换成id、parentId格式
	 */
	transformArray(arr, key) {
		let res = [];

		function build(arr) {
			for(let x of arr) {
				res.push(x)
				if(x[key]) {
					build(x[key])
				}
			}
		}
		build(arr)
		return res
	},
	/**
	 * 重写es5 map方法
	 */
	map(data, key) {
		let arr = []
		for(let x of data) {
			arr.push(x[key])
		}
		return arr
	},
	/**
	 * 浅拷贝
	 */
	shallowCopy(src) {
		var dst = {};
		for(var prop in src) {
			if(src.hasOwnProperty(prop)) {
				dst[prop] = src[prop];
			}
		}
		return dst;
	},
	/**
	 * 深拷贝
	 */
	deepCopy(data) {
		let obj;
		if(this.isArray(data)) {
			obj = [];
			for(var i = 0, len = data.length; i < len; i++) {
				obj.push(this.deepCopy(data[i]));
			}
		} else if(this.isObject(data)) {
			obj = {};
			for(var key in data) {
				obj[key] = this.deepCopy(data[key]);
			}
		} else {
			return data;
		}
		return obj;
	},
	/**
	 * 通过key获取数组某个对象
	 */
	getKey(arr, key, value) {
		return arr.filter((val) => {
			return val[key] == value
		})[0]
	},
	/**
	 * 处理数组
	 * map:{新:旧} 映射
	 * add:{key:value}新增
	 */
	processArray(arr, map, add) {
		arr.forEach((val) => {
			if(map) {
				for(let x in map) {
					val[x] = val[map[x]]
				}
			}
			if(add) {
				for(let x in add) {
					val[x] = add[x]
				}
			}
		})
		return arr
	},
	/**
	 * 判断字符str是否包含在obj中,obj可以是数字、字符串、数组,不区分大小写
	 */
	includes(str, obj) {
		if(this.isNull(str)) {
			return false
		} else {
			if(this.isString(obj)) {
				return obj.toLowerCase().indexOf(str.toString().toLowerCase()) !== -1
			} else if(this.isNumber(obj)) {
				return obj.toString().toLowerCase().indexOf(str.toString().toLowerCase()) !== -1
			} else if(this.isArray(obj)) {
				return obj.some((val) => {
					return val.toString().toLowerCase().indexOf(str.toString().toLowerCase()) !== -1
				})
			} else {
				return false
			}
		}

	},
	isNull(obj) {
		return Object.prototype.toString.call(obj) == '[object Null]'
	},
	isString(obj) {
		return Object.prototype.toString.call(obj) == '[object String]'
	},
	isNumber(obj) {
		return Object.prototype.toString.call(obj) == '[object Number]'
	},
	isArray(obj) {
		return Object.prototype.toString.call(obj) == '[object Array]'
	},
	isObject(obj) {
		return Object.prototype.toString.call(obj) == '[object Object]'
	},
	isDate(obj) {
		return Object.prototype.toString.call(obj) == '[object Date]'
	}
}