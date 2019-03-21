<template>
	<header class="mes__header">
		<div class="logo">
			<img src="~assets/images/index/logo2.png" />
			<span>{{title}}</span>
		</div>
		<el-popover ref="popovers" placement="bottom" width="380" trigger="click">
			<div v-html="content" class="popovers"></div>
		</el-popover>
		<div class="menu" v-show="showMenu">
			<ul>
				<li v-for="value in data" :key="value.id">
					<router-link :to="value.url">
						<img :src="require('assets/images/index/' + value.icon)" />
						<span>{{value.name}}</span>
					</router-link>
				</li>
			</ul>
		</div>
		<div class="loginBox">
			<div class="skin"></div>
			<div class="tool">
				<span class="icon-bell-alt"></span>
				<span class="icon-user"></span>
				<div class="off" @click="logout">
					<span class="icon-off"></span>
					<span>退出</span>
				</div>
				<span id="username">
          <marquee direction="left" width="150px" scrollamount="4">欢迎登陆,{{userInfo.name}}</marquee>
        </span>

			</div>
		</div>
		<error-log v-if="log.length>0" class="errLog-container" :logsList="log"></error-log>
	</header>
</template>

<script>
	import errLogStore from '@/store/errLog';
	import errorLog from 'components/errorLog';
	import fetch from '@/config/fetch'
	const getLicenseInfo = (data) => fetch('/user/getLicenseInfo', {}, "GET");
	export default {
		props: {
			title: {
				default: "数据管理与应用系统"
			},
			showMenu: {
				default: true
			},
			showExit: {
				default: false
			}
		},
		components: {
			errorLog
		},
		created() {
			this.data = this.$store.getters.menu;
			this.userInfo = this.$store.getters.userInfo;
		},
		data() {
			return {
				log: errLogStore.state.errLog,
				data: [],
				userInfo: {},
				content: ''
			};
		},
		methods: {
			logout() {
				this.$router.push("/login")
			},
		}
	}
</script>

<style>
	.mes__header {
		width: 100%;
		height: 80px;
		box-shadow: 0px 1px 10px #888888;
		position: relative;
		z-index: 2;
	}
	
	.mes__header .logo {
		float: left;
		width: 300px;
		height: 100%;
		overflow: hidden;
		font: 400 18px "CenturyGothic";
		color: #FFF;
		padding: 25px;
		letter-spacing: 2px;
	}
	
	.mes__header .logo img {
		float: left;
		margin-right: 5px;
		position: relative;
		top: -10px;
		width: 50px;
	}
	
	.mes__header .logo span {
		float: left;
		line-height: 30px;
	}
	
	.mes__header .menu {
		height: 100%;
		position: absolute;
		left: 280px;
		right: 310px;
		top: 0;
		bottom: 0;
	}
	
	.mes__header .menu ul {
		padding: 0 10px 10px 10px;
		float: left;
		position: absolute;
		height: 80px;
		overflow: hidden;
		transition: all linear 0.3s;
	}
	
	.mes__header .menu ul li {
		position: relative;
		float: left;
		display: block;
		height: 80px;
		width: 100px;
		font: 400 14px "microsoft yahei";
		text-align: center;
		cursor: pointer;
	}
	
	.mes__header .menu ul li a {
		display: inline-block;
		height: 100%;
		width: 100%;
		border-top: 4px solid transparent
	}
	
	.mes__header .menu ul li a span {
		display: inline-block;
		width: 100%;
		position: absolute;
		bottom: 12px;
		left: 0;
		text-align: center;
		color: rgb(225, 225, 228)
	}
	
	.mes__header .menu ul li a img {
		display: inline-block;
		text-align: center;
		margin-top: 15px;
	}
	
	.mes__header .menu ul li a.router-link-active {
		background: rgb(49, 120, 203);
		border-color: rgb(49, 195, 145)
	}
	
	.mes__header .menu ul li a.router-link-active span {
		color: rgb(255, 255, 255)
	}
	
	.mes__header .loginBox {
		height: 100%;
		color: #000;
		position: absolute;
		right: 0;
		width: 360px;
	}
	
	.mes__header .loginBox .skin {
		height: 40%;
		width: 100%;
		position: relative;
		visibility: hidden;
	}
	
	.mes__header .loginBox .tool {
		height: 60%;
		width: 100%;
		line-height: 48px;
		vertical-align: top;
		position: relative;
	}
	
	.mes__header .loginBox .tool>span {
		color: #71AFF2;
	}
	
	.mes__header .loginBox .tool>span {
		font-size: 16px;
		margin: 0px 12px;
	}
	
	.mes__header .loginBox .tool>span {
		cursor: pointer;
	}
	
	.mes__header .loginBox div.off {
		float: right;
		height: 100%;
		width: 75px;
		color: #E5EAF0;
		cursor: pointer;
	}
	
	.mes__header .loginBox .tool span#username {
		float: right;
		font-size: 14px;
		letter-spacing: 1px;
		margin-left: -5px;
		cursor: default;
		color: #E5EAF0;
	}
	
	.mes__header .loginBox div.off span:first-of-type {
		margin-right: 8px;
		cursor: pointer;
	}
	
	.errLog-container {
		position: absolute;
		right: 0;
		top: 0;
	}
	
	.el-popover {
		font-size: 14px;
	}
</style>