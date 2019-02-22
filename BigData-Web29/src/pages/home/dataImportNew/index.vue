<template>
	<div>
		<mes-table-layout :tree-data="treeData" ref="layout">
		<div class="mes-content" slot="container">
			<p1>请输入以下信息</p1>
			<el-form ref = "form" model = "form">
				<el-form-item lable = "课题">
			</el-form>
			
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
	
</style>
