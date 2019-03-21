<template>
	<div>
		<mes-table-layout>
			<div class="mes-content" slot="container">
				<div class="mes-tool">
					<el-button type="primary" @click="create_getPage">新建<i class="el-icon-upload el-icon--right"></i>
					</el-button>
					<el-input placeholder="请输入搜索信息" icon="search" v-model="searchData" :on-icon-click="search" @keyup.enter.native="search">
					</el-input>
				</div>
				<div class="mes-table" v-if="table.data">
					<mes-table :table-data="table.data" :table-option="table.option" :table-loading="table.loading" :total="table.pageTotal" :pageSize="table.pageSize" :pageChange="pageChange" :sizeChange="sizeChange" :pageNum="table.pageNum" :showCheckBox="false">
						<el-table-column fixed="right" label="操作" width="120" v-if="table.data.length>0" slot="operationTool">
							<template slot-scope="scope">
								<el-button @click.native.prevent="edit_getPage(scope.row)" type="text" size="small">编辑</el-button>
								<el-button @click.native.prevent="delete_getPage(scope.row)" type="text" size="small">删除</el-button>
							</template>
						</el-table-column>
					</mes-table>
				</div>
			</div>
		</mes-table-layout>
		<createForm ref="create_form"></createForm>
		<editForm ref="edit_form"></editForm>
	</div>
</template>

<script>
	import {
		queryData,
		deleting
	} from "./service";

	import createForm from "./create"
	import editForm from "./edit"

	export default {
		components: {
			createForm,
			editForm
		},
		created() {
			this.init();
		},
		data() {
			return {
				table: {
					option: [{
						prop: "dataType",
						label: "数据项"
					}, {
						prop: "serverStoreDir",
						label: "服务器路径"
<<<<<<< HEAD
					}, 
// 					{
// 						prop: "queryTarget",
// 						label: "IOV地址"
// 					}, {
// 						prop: "executeTarget",
// 						label: "解析地址"
// 					}, 
					{
=======
					}, {
						prop: "queryTarget",
						label: "IOV地址"
					}, {
						prop: "executeTarget",
						label: "解析地址"
					}, {
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
						prop: "creater",
						label: "创建人"
					}, {
						prop: "createDate",
						label: "创建时间"
					}],
					data: false,
					loading: true,
					pageSize: 10,
					pageNum: 1
				},
				searchData: ''
			};
		},
		methods: {
			init() {
				this.queryData()
			},
			async queryData() {
				this.table.loading = true;
				let data = {
					condition: {
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
			create_getPage() {
				this.$refs.create_form.getPage()
			},
			edit_getPage(data) {
				this.$refs.edit_form.getPage(data)
			},
			delete_getPage(data) {
				this.$.delete('此操作将永久删除该数据项, 是否继续?', () => {
					deleting({
						ids: data.id
					}).then((res) => {
						this.queryData()
					})
				})
			},
			search() {
				this.table.pageNum = 1;
				this.queryData();
			},
			pageChange(currentPage) {
				this.table.pageNum = currentPage;
				this.queryData();
			},
			sizeChange(size) {
				this.table.pageSize = size
				this.queryData();
			}
		}
	};
</script>

<style scoped>

</style>