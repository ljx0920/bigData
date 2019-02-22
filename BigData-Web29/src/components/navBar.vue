<template>
	<div id="navBar" :class="{'active':collapse}">
		<div class="navBar">
			<div class="head">
				<span class="icon-list" @click="collapse=!collapse"></span>
			</div>
			<div class="menuBox" v-scroll='{"axis":"y","theme":"dark"}'>
				<el-menu :default-active="path" class="el-menu-vertical-demo" theme="dark" router unique-opened :collapse="collapse">
					<div v-for="item in navData">
						<el-submenu v-if="item.children&&item.children.length>0" :index="item.url||item.id">
							<template slot="title">
								<img class="navIcon" :src="require('assets/images/index/menu/' + item.icon)" />
								<span class="itemName">{{item.name}}</span>
							</template>
							<el-menu-item v-for="x in item.children" :key="x.id" :index="x.url||item.id">
								<span class="split"></span> {{x.name}}
							</el-menu-item>
						</el-submenu>
						<el-menu-item v-if="!(item.children&&item.children.length>0)" :index="item.url||item.id">
							<img class="navIcon" :src="require('assets/images/index/menu/'+item.icon)" />
							<span slot="title"class="itemName">{{item.name}}</span>
						</el-menu-item>
					</div>
				</el-menu>
			</div>
		</div>
		<div class="content">
			<router-view></router-view>
		</div>
	</div>
</template>

<script>
	export default {
		created() {
			let data = this.$route.matched || []
			let path = ""
			for(let x of data) {
				if(x.path.split("/").length > 2) {
					path = x.path
					break;
				}
			}
			this.path = path
		},
		props: ['navData'],
		data() {
			return {
				collapse: false
			};
		},
		methods: {}
	}
</script>

<style>
	#navBar .mCustomScrollBox .menuBox,
	#navBar .mCSB_container .menuBox {
		overflow: visible;
	}
	
	#navBar>.navBar {
		position: absolute;
		top: 0;
		left: 0;
		width: 200px;
		bottom: 0;
		overflow: visible;
		transition: all linear 0.1s;
	}
	
	#navBar>.navBar>div.head {
		background-color: #324157;
		border-bottom: 1px #1F2229 solid;
		height: 50px;
		width: 100%;
		text-align: right;
		padding: 15px 25px 15px 0px;
	}
	
	#navBar .navBar>div.head .icon-list {
		color: #bfcbd9;
		cursor: pointer;
	}
	
	
	#navBar>.navBar .split {
		padding-left: 20px;
	}
	
	#navBar>.content {
		position: absolute;
		top: 0;
		left: 200px;
		right: 0;
		bottom: 0;
		transition: all linear 0.1s;
		overflow: hidden;
	}
	#navBar>.content>div {
		height: 100%;
		width: 100%;
	}
	#navBar>.navBar .el-menu>.icon {
		height: 50px;
		width: 100%;
		text-align: center;
	}
	#navBar>.navBar .el-menu>.icon span {
		font-size: 16px;
		color: #bfcbd9;
		line-height: 50px;
		cursor: pointer
	}
	#navBar>.navBar .el-menu>.icon span:hover {
		color: #fff;
	}
	#navBar>.navBar .el-submenu .el-menu-item{
		min-width: auto;
	}
	#navBar>.navBar .menuBox {
		height: calc(100% - 50px);
		width: 100%;
		background-color: #324157;
	}
	#navBar>.navBar .navIcon {
		margin-right: 20px;
		margin-left: 5px;
		height: 14px;
		width: 14px;
		position: relative;
		top: 2px
	}
	
	
	#navBar.active>.content {
		left: 64px;
		transition: all 0.3s linear;
	}
	#navBar.active .navBar {
		width: 64px;
		transition: all 0.3s linear;
	}
	#navBar.active .el-menu>div>li>div{
		overflow: hidden;
	}
	#navBar.active .el-submenu__icon-arrow{
		margin-top:-2px;
		margin-right: -11px;
	}
	#navBar.active .el-menu--collapse .el-submenu .el-menu{
		margin-left: 0;
		z-index: 30;
	}
	#navBar.active .mCustomScrollBox,#navBar.active .mCSB_container{
		overflow: visible;
	}
</style>