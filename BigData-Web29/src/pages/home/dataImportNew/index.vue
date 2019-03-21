<template>
	<div>
		<mes-table-layout :tree-data="treeData" ref="layout">
<<<<<<< HEAD
		<div class="form1" slot="container">
			<p1>请输入以下信息</p1>
			<!-- <el-form ref = "form" :model = "form">
				<el-form-item lable = "课题">
					<el-input v-model="form.name"></el-input>
				</el-form-item>
			</el-form>
			 -->
=======
		<div class="mes-content" slot="container">
			<p1>请输入以下信息</p1>
			<el-form ref = "form" model = "form">
				<el-form-item lable = "课题">
			</el-form>
			
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
		</div>
		</mes-table-layout>
		
	</div>
</template>

<script>
	import{
		getTree
	}
	from "./service";
	export default{
		created() {
			this.init();
		},
		data(){
			return{
				treeData:false
			};
		},
		methods: {
			init() {
				this.getTree();
			},
			async getTree() {
				let res = await getTree();
				if(res.status == "success") {
					this.treeData = this.$.transformTree(res.content, {
						pNode: '课题列表'
					})
				} else {
					this.treeData = []
				}
			}
		}
	};
	
</script>

<style>
<<<<<<< HEAD
		.el-date-editor.el-input {
		width: 100% !important;
	}
	
	.mCSB_scrollTools.mCSB_scrollTools_horizontal .mCSB_dragger .mCSB_dragger_bar {
		margin: 8px auto;
	}
	
	.el-dialog--small {
		width: 40%;
	}
=======
	
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
</style>
