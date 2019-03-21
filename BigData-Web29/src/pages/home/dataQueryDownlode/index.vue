<template>
<<<<<<< HEAD
	<div>
		<mes-table-layout :tree-data="treeData" @tree-click="treeClick" v-if="treeData" ref="layout">
			<div class="mes-content" slot="container">
					<div class="mes-tool">
						<el-input placeholder="请输入搜索信息" icon="search" v-model="searchData" :on-icon-click="search" @keyup.enter.native="search">
						</el-input>
					</div>
					<div class="mes-table">
						<mes-table :table-data="table.data" :table-option="table.option" :table-loading="table.loading" :total="table.pageTotal" :pageSize="table.pageSize" :pageChange="pageChange" :sizeChange="sizeChange" :pageNum="table.pageNum">
							<el-table-column fixed="right" label="操作" width="150" v-if="table.data.length>0" slot="operationTool">
								<template slot-scope="scope">
									<el-button @click.native.prevent="downLoad(scope.row)" type="text" size="small">下载</el-button>
									<el-button @click.native.prevent="dataDelete(scope.row)" type="text" size="small">删除</el-button>
								</template>
							</el-table-column>
						</mes-table>
					</div>
			</div>
		</mes-table-layout>
=======
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
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
	</div>
</template>

<script>
<<<<<<< HEAD
	import {
		getTree,
		queryData,
		dataDelete,
		downLoadUrl
	} from "./service";
	export default {
		created() {
=======
	import { getRecentItem } from "./service";
	export default {
		mounted() {
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
			this.init();
		},
		data() {
			return {
<<<<<<< HEAD
				treeData: false,
=======
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
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
<<<<<<< HEAD
					}, {
=======
					},
// 					 {
// 						prop: "filePath",
// 						label: "文件路径"
// 					}, 
					{
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
						prop: "importTime",
						label: "上传时间"
					}, {
						prop: "importer",
						label: "上传人"
<<<<<<< HEAD
					}],
=======
					}
// 					, {
// 						prop: "analyticProgress",
// 						label: "解析状态"
// 					}
					],
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
					data: [],
					loading: false,
					pageSize: 10,
					pageNum: 1
<<<<<<< HEAD
				},
				searchData: '',
=======
				}
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
			};
		},
		methods: {
			init() {
<<<<<<< HEAD
				this.getTree();
			},
			async getTree() {
				let res = await getTree();
				this.treeData = this.$.transformTree(res.content, {
					pNode: '课题列表'})
				this.selectNode = null
				setTimeout(() => {
					if(this.$refs.layout.setFirstTreeNode()) {
						this.treeClick(this.$refs.layout.setFirstTreeNode())
					}
				})
			},
			treeClick: function(node) {
				this.selectNode = node
				if (this.selectNode.name=='课题列表'){
					this.queryDataNew();
				}
				else{
					this.queryData();
				}
			},
			async queryData() {
				this.table.loading = true;
				if (this.selectNode.level==1){
					// doQueryData(uploadClassifyId1,this.selectNode ? this.selectNode.id : '');
					let data = {
						condition: {
							uploadClassifyId1: this.selectNode ? this.selectNode.id : '',
							search: this.searchData
						},
						pageSize: this.table.pageSize,
						pageNum: this.table.pageNum
					}
					let res = await queryData(data);
					this.table.data = res.content.rows || [];
					this.table.pageTotal = res.content.total;
					this.table.loading = false;

				}
				else if(this.selectNode.level==2){
					// doQueryData(uploadClassifyId2,this.selectNode ? this.selectNode.id : '');
					let data = {
						condition: {
							uploadClassifyId2: this.selectNode ? this.selectNode.id : '',
							search: this.searchData
						},
						pageSize: this.table.pageSize,
						pageNum: this.table.pageNum
					}
					let res = await queryData(data);
					this.table.data = res.content.rows || [];
					this.table.pageTotal = res.content.total;
					this.table.loading = false;
				}
				else{
					// doQueryData(uploadClassifyId,this.selectNode ? this.selectNode.id : '');
					let data = {
						condition: {
							uploadClassifyId: this.selectNode ? this.selectNode.id : '',
							search: this.searchData
						},
						pageSize: this.table.pageSize,
						pageNum: this.table.pageNum
					}
					let res = await queryData(data);
					this.table.data = res.content.rows || [];
					this.table.pageTotal = res.content.total;
					this.table.loading = false;
				}
			},
// 			async doQueryData(upLoadName,upLoadId){
// 				let data = {
// 					condition: {
// 						upLoadName: upLoadId,
// 						search: this.searchData
// 					},
// 					pageSize: this.table.pageSize,
// 					pageNum: this.table.pageNum
// 				}
// 				let res = await queryData(data);
// 				this.table.data = res.content.rows || [];
// 				this.table.pageTotal = res.content.total;
// 				this.table.loading = false;
// 			},
			async queryDataNew() {
				this.table.loading = true;
				let data = {
					condition: {
						search: this.searchData
					},
					pageSize: this.table.pageSize,
					pageNum: this.table.pageNum
				}
				let res = await queryData(data);
				this.table.data = res.content.rows || [];
				this.table.pageTotal = res.content.total;
				this.table.loading = false;
			},
			dataDelete(data) {
				this.$.delete('此操作将永久删除该项, 是否继续?', () => {
					dataDelete({
						ids: data.id
					}).then((res) => {
						this.queryData()
					})
				})
			},
			search() {
				this.queryDataNew();
			},
			pageChange(currentPage) {
				this.table.pageNum = currentPage
				this.queryDataNew();
			},
			sizeChange(size) {
				this.table.pageSize = size
				this.queryDataNew();
			},
			downLoad(data) {
				window.open(`${downLoadUrl}?fileItemId=${data.id}`)
=======
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
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
			},
		}
	};
</script>

<<<<<<< HEAD
<style>
		.el-date-editor.el-input {
		width: 100% !important;
	}
	
	.mCSB_scrollTools.mCSB_scrollTools_horizontal .mCSB_dragger .mCSB_dragger_bar {
		margin: 8px auto;
	}
	
	.el-dialog--small {
		width: 40%;
	}
</style>
=======
<style scoped>
	.mes-table {
		top: 0
	}
</style>
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
