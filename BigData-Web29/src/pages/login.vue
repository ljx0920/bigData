<template>
	<div id="login">
		<div class="banner">
			<div class="login">
				<div class="loginBox">
					<div class="wel">
						欢迎登录系统
					</div>
					<div class="pls">
						请登录
					</div>
					<div class="loginForm">
						<el-form :model="form" :rules="rules" ref="form">
							<el-form-item prop="username">
								<el-input type="text" name="username" v-model="form.username" placeholder="请输入用户名" />
								<el-input type="text" class="perventAutoComplete" disabled/>
							</el-form-item>
							<el-form-item prop="password">
								<span class="showPsw" :class="pswParam.icon" @click="showPassword"></span>
								<el-input :type="pswParam.type" class="passwordInput" name="password" v-model="form.password" placeholder="请输入密码" @keyup.enter.native="login" @keypress.native="checkCapsLock" />
							</el-form-item>
							<div class="showTags">
								<span v-if="showTags">
                                	提示！大写锁定已打开
                                </span>
							</div>
							<el-button type="button" class="btn btn-primary" @click="login" :disabled='disabled'>登 录</el-button>
						</el-form>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		computed: {
			pswParam: function() {
				let res = {}
				if(this.showPsw) {
					res.type = "text"
					res.icon = "icon-eye-close"
				} else {
					res.type = "password"
					res.icon = "icon-eye-open"
				}
				return res
			}
		},
		data() {
			return {
				showTags: false,
				showPsw: false,
				form: {
					username: '',
					password: ''
				},
				rules: {
					username: [{
						required: true,
						message: "账号必填",
						trigger: 'change,blur'
					}],
					password: [{
						required: true,
						message: "密码必填",
						trigger: 'change,blur'
					}]
				},
				loading: false,
				disabled: false
			}
		},
		methods: {
			login() {
				this.loading = true;
				this.disabled = true;
				this.$refs.form.validate(valid => {
					this.loading = false;
					if(valid) {
						this.$store.dispatch('login', this.form).then(() => {
							this.$router.addRoutes(this.$store.getters.routes)
							this.$router.push({
								path: '/home'
							});
							this.disabled = false
						});
					}
				});
				setTimeout(() => {
					this.disabled = false
				}, 3000);
			},
			checkCapsLock(e) {
				var capsLockKey = e.keyCode ? e.keyCode : e.which;
				var shifKey = e.shiftKey ? e.shiftKey : ((capsLockKey == 16) ? true : false);
				if(((capsLockKey >= 65 && capsLockKey <= 90) && !shifKey) || ((capsLockKey >= 97 && capsLockKey <= 122) && shifKey)) {
					this.showTags = true;
				} else if(((capsLockKey >= 65 && capsLockKey <= 90) && shifKey) || ((capsLockKey >= 97 && capsLockKey <= 122) && !shifKey)) {
					this.showTags = false;
				}
			},
			showPassword() {
				this.showPsw = !this.showPsw
			}
		}
	}
</script>

<style>
	#login {
		height: 100%;
		width: 100%;
		background-color: #FFF;
	}
	
	#login>.banner {
		height: 100%;
		width: 100%;
		background-image: url("~assets/images/index/bg.png");
		background-size: 100% 100%;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	#login>.banner>div {
		height: 100%;
		width: 50%;
	}
	
	#login>.banner>.image {
		padding-right: 50px;
	}
	
	#login>.banner>.image img {
		position: absolute;
		top: calc(50% - 261px);
		left: calc(50% - 373px);
	}
	
	#login>.banner>.login .loginBox {
		position: absolute;
		top: calc(50% - 200px);
		left: calc(50% - 180px);
		height: 400px;
		width: 360px;
		background: #FFF;
		padding: 25px
	}
	
	#login>.banner>.login .loginBox>.wel {
		text-align: center;
		padding: 10px;
		color: rgb(51, 138, 241);
		font-size: 22px;
		font-weight: bolder;
		letter-spacing: 2px;
	}
	
	#login>.banner>.login .loginBox>.pls {
		text-align: center;
		padding: 10px;
		color: #666;
	}
	
	#login>.top {
		position: absolute;
		top: 0;
		height: 15%;
		width: 100%;
	}
	
	#login>.top .changeLogin {
		position: absolute;
		top: calc(50% - 14px);
		right: calc(25% - 180px);
	}
	
	#login>.top .title {
		position: absolute;
		top: calc(50% - 35px);
		left: 10%;
	}
	
	#login>.top .title .titleWord {
		font-size: 22px;
		letter-spacing: 2px;
		line-height: 70px;
		float: left;
		margin-right: 20px;
	}
	
	#login>.top .title .version {
		font-size: 22px;
		line-height: 70px;
		float: left;
	}
	
	#login>.top .title .titleImg {
		float: left;
		margin-right: 20px;
	}
	
	#login>.bottom {
		position: absolute;
		bottom: 0;
		height: 10%;
		width: 100%;
		text-align: center;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	#login>.bottom .footer span {
		border-left: 1px solid #ccc;
		margin-left: 20px;
		padding-left: 20px;
		line-height: 23px;
		float: left;
	}
	
	#login>.bottom .footer img {
		float: left;
	}
	
	#login .loginForm {
		float: left;
		width: 100%;
		padding-top: 20px;
	}
	
	#login .loginForm .el-input__inner {
		display: inline-block;
		height: 50px;
	}
	
	#login .loginForm .passwordInput {
		margin-top: 20px;
	}
	
	#login .loginForm .el-form-item {
		margin-bottom: 0;
	}
	
	#login .loginForm .showTags {
		color: red;
		text-align: right;
		height: 20px;
	}
	
	#login .loginForm button {
		width: 100%;
		height: 50px;
		background-color: rgb(86, 136, 250);
		color: #fff;
		margin-top: 50px
	}
	
	#login .loginForm .perventAutoComplete {
		opacity: 0;
		height: 0;
		overflow: hidden;
		display: block;
	}
	
	#login .loginForm .perventAutoComplete input {
		height: 0;
	}
	
	#login .loginForm .showPsw {
		position: absolute;
		right: 10px;
		top: 35px;
		z-index: 2;
		font-size: 18px;
		color: #999;
		cursor: pointer;
	}
</style>