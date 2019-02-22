<template>
	<div style="padding: 15px;">
	<!-- 	<mes-table-layout :tree-data="treeData" @tree-click="treeClick" v-if="treeData" ref="layout">
			<div class="mes-content" slot="container"> -->
			<el-row :gutter="20">
				<el-col :span="3"><div class="grid-content bg-purple" style="font-size: medium;text-align: center;">课题号：</div></el-col>
				<el-col :span="5"><div class="grid-content bg-purple">				
					<el-input v-model="input" placeholder="请输入内容"></el-input>
				</div></el-col>
				<el-col :span="3"><div class="grid-content bg-purple" style="font-size: medium;text-align: center;">试飞日期：</div></el-col>
				<el-col :span="5"><div class="grid-content bg-purple">
				<span class="demonstration"></span>
					<el-date-picker v-model="value1" type="date" placeholder="选择日期" prop="value1">
					</el-date-picker>
				</div></el-col>
				<el-col :span="3"><div class="grid-content bg-purple" style="font-size: medium;text-align: center;">试飞科目：</div></el-col>
				<el-col :span="5"><div class="grid-content bg-purple">
					<el-input v-model="input" placeholder="请输入内容"></el-input>
				</div></el-col>
			</el-row>

			<el-row type="flex" justify="end">
				<el-button type="primary">查询</el-button>
			</el-row>
			
			<div class="mes-content" slot="container">
				<div class="mes-table">
					<mes-table :table-data="table.data" :table-option="table.option" :table-loading="table.loading" :total="table.pageTotal" :pageSize="table.pageSize" :pageChange="pageChange" :sizeChange="sizeChange" :pageNum="table.pageNum" :showCheckBox="false"></mes-table>
				</div>
			</div>
<!-- 			</div>
		</mes-table-layout> -->
	</div>
</template>

<script>
	import { getRecentItem } from "./service";
	export default {
		mounted() {
			this.init();
		},
		data() {
			return {
				table: {
					option: [{
						prop: "dataTypeName",
						label: "数据项"
					}, {
						prop: "fileName",
						label: "文件名"
					}, {
						prop: "fileSize",
						label: "文件大小"
					},
// 					 {
// 						prop: "filePath",
// 						label: "文件路径"
// 					}, 
					{
						prop: "importTime",
						label: "上传时间"
					}, {
						prop: "importer",
						label: "上传人"
					}
// 					, {
// 						prop: "analyticProgress",
// 						label: "解析状态"
// 					}
					],
					data: [],
					loading: false,
					pageSize: 10,
					pageNum: 1
				}
			};
		},
		methods: {
			init() {
				this.queryData()
			},
			async queryData() {
				this.table.loading = true;
				let data = {
					condition: {},
					pageSize: this.table.pageSize,
					pageNum: this.table.pageNum
				}
				let res = await getRecentItem(data);
				if(res.status == "success") {
					this.table.data = res.content.rows || [];
					this.table.pageTotal = res.content.total;
				} else {
					this.table.data = [];
					this.table.pageTotal = 0;
				}
				this.table.loading = false;
			},
			pageChange(currentPage) {
				this.table.pageNum = currentPage
				this.queryData();
			},
			sizeChange(size) {
				this.table.pageSize = size
				this.queryData();
			},
		}
	};
</script>

<style scoped>
	.mes-table {
		top: 0
	}
</style>
