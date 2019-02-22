<template>
	<div>
		<mes-table-layout :tree-data="treeData" @tree-click="treeClick" v-if="treeData" ref="layout">
			<template slot="tool">
				<el-button type="primary" icon="document" @click="treeCreate_getPage" :disabled='!selectNode||selectNode&&selectNode.level==3'></el-button>
				<el-button type="primary" icon="edit" @click="treeEdit_getPage" :disabled='!selectNode||selectNode&&selectNode.level==0'></el-button>
				<el-button type="primary" icon="delete" @click="treeDelete_getPage" :disabled='!selectNode||selectNode&&selectNode.level==0||selectNode&&selectNode.children.length!=0'></el-button>
				<el-button type="primary" icon="document" @click="treeCreateNew_getPage"></el-button>
			</template>
			<div class="mes-content" slot="container">
				<div class="mes-tool">
					<el-button type="primary" @click="create_getPage" :disabled="!selectNode||selectNode&&selectNode.level!=3">上传<i class="el-icon-upload el-icon--right"></i>
					</el-button>
					<el-input placeholder="请输入搜索信息" icon="search" v-model="searchData" :on-icon-click="search" @keyup.enter.native="search">
					</el-input>
				</div>
				<div class="mes-table">
					<mes-table :table-data="table.data" :table-option="table.option" :table-loading="table.loading" :total="table.pageTotal" :pageSize="table.pageSize" :pageChange="pageChange" :sizeChange="sizeChange" :pageNum="table.pageNum">
						<el-table-column fixed="right" label="操作" width="150" v-if="table.data.length>0" slot="operationTool">
							<template slot-scope="scope">
								<el-button @click.native.prevent="downLoad(scope.row)" type="text" size="small">下载</el-button>
								<el-button @click.native.prevent="dataDelete(scope.row)" type="text" size="small">删除</el-button>
								<el-button @click.native.prevent="execute(scope.row)" type="text" size="small" v-if='scope.row.analyticProgress=="未开始"||scope.row.analyticProgress=="解析失败"'>解析</el-button>
							</template>
						</el-table-column>
					</mes-table>
				</div>
			</div>
		</mes-table-layout>
		<treeCreateForm ref="treeCreate_form"></treeCreateForm>
		
		<treeCreateNewForm ref="treeCreateNew_form"></treeCreateNewForm>
		
		<treeEditForm ref="treeEdit_form"></treeEditForm>
		<createForm ref="create_form"></createForm>
	</div>
</template>

<script>
	import {
		getTree,
		queryData,
		treeDelete,
		dataDelete,
		downLoadUrl,
		execute,
		getStatus
	} from "./service";
	import treeCreateForm from "./treeCreate"
	
	import treeCreateNewForm from "./treeCreateNew"
		
	import treeEditForm from "./treeEdit"
	import createForm from "./create"

	export default {
		components: {
			treeCreateForm,
			
			treeCreateNewForm,
			
			treeEditForm,
			createForm
		},
		created() {
			this.init();
		},
		data() {
			return {
				treeData: false,
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
					}, {
						prop: "filePath",
						label: "文件路径"
					}, {
						prop: "importTime",
						label: "上传时间"
					}, {
						prop: "importer",
						label: "上传人"
					}, {
						prop: "analyticProgress",
						label: "解析状态"
					}],
					data: [],
					loading: false,
					pageSize: 10,
					pageNum: 1
				},
				searchData: '',
			};
		},
		methods: {
			init() {
				this.getTree();
				this.getStatus();
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
				this.selectNode = null
				setTimeout(() => {
					if(this.$refs.layout.setFirstTreeNode()) {
						this.treeClick(this.$refs.layout.setFirstTreeNode())
					}
				})
			},
			treeClick: function(node) {
				this.selectNode = node
				this.queryData()
			},
			async queryData() {
				this.table.loading = true;
				let data = {
					condition: {
						uploadClassifyId: this.selectNode ? this.selectNode.id : '',
						search: this.searchData
					},
					pageSize: this.table.pageSize,
					pageNum: this.table.pageNum
				}
				let res = await queryData(data);
				if(res.status == "success") {
					this.table.data = res.content.rows || [];
					this.table.pageTotal = res.content.total;
				} else {
					this.table.data = [];
					this.table.pageTotal = 0;
				}
				this.table.loading = false;
			},
			treeCreate_getPage() {
				this.$refs.treeCreate_form.getPage(this.selectNode)
			},
			
			treeCreateNew_getPage() {
				this.$refs.treeCreateNew_form.getPage(this.selectNode)
			},
			
			treeEdit_getPage() {
				this.$refs.treeEdit_form.getPage(this.selectNode)
			},
			treeDelete_getPage() {
				this.$.delete('此操作将永久删除该项, 是否继续?', () => {
					treeDelete({
						ids: this.selectNode.id
					}).then((res) => {
						this.getTree()
					})
				})
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
			create_getPage() {
				this.$refs.create_form.getPage(this.selectNode)
			},
			search() {
				this.queryData();
			},
			pageChange(currentPage) {
				this.table.pageNum = currentPage
				this.queryData();
			},
			sizeChange(size) {
				this.table.pageSize = size
				this.queryData();
			},
			downLoad(data) {
				window.open(`${downLoadUrl}?fileItemId=${data.id}`)
			},
			execute(data) {
				let datas = {
					fileItemId: data.id,
					className: this.selectNode.name,
					dateName: this.selectNode.getParentNode().name,
					subjectName: this.selectNode.getParentNode().getParentNode().name
				}
				execute(datas).then((res) => {
					if(res.status == 'success') {
						data.analyticProgress = '正在解析';
					}
				});
			},
			getStatus() {
				setInterval(() => {
					if(this.table.data.length == 0) return false;
					getStatus(this.table.data.map((item) => {
						return item.id
					})).then((res) => {
						this.table.data.forEach((item) => {
							for(let i = 0; i < res.content.length; i++) {
								if(item.id == res.content[i].id) {
									item.analyticProgress = res.content[i].analyticProgress;
									break;
								}
							}
						});
					});
				}, 60000);
			}
		}
	};
</script>

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