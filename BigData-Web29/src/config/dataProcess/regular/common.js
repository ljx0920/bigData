export default {
	commonEn: {
		pattern: /^[0-9a-zA-Z_.]+$/,
		message: "请勿输入特殊字符"
	},
	commonCn: {
		pattern: /^[\u4E00-\u9FA50-9a-zA-Z_\-.]+$/,
		message: "请勿输入特殊字符"
	},
	nameEn: {
		pattern: /^[a-zA-Z]+$/,
		message: "请输入纯英文"
	},
	nameCn: {
		pattern: /^[\u4E00-\u9FA5]+$/,
		message: "请输入纯中文"
	},
	number: {
		pattern: /^[0-9]+$/,
		message: "请输入纯数字"
	},
	phone: {
		pattern: /^[0-9-]+$/,
		message: "请输入正确格式座机号"
	},
	mobilePhone: {
		pattern: /^1[34578]\d{9}$/,
		message: "请输入正确格式手机号"
	},
	email: {
		pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
		message: "请输入正确格式邮箱"
	},
	password: {
		pattern: /^[0-9a-zA-Z._]{5,17}$/,
		message: "长度在6~18之间，只能包含字母、数字"
	},
  ip: {
    pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/,
    message: "请输入正确格式IP地址"
  },
  mpPhoneOrphone:{
    pattern: /(^((\d{11})|(\d{7,8})|(\d{4}|\d{3})-(\d{7,8}))$)/,
    message: "请输入正确的联系方式"
  }
}
