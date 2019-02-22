<template>
	<el-dialog v-dialog-drag title="上传数据" v-if='show' :visible.sync="show" size="small" :close-on-click-modal="false" class='dataImport'>
		<el-tabs v-model='active' @tab-click='tabClick'>
			<el-tab-pane label='分类上传' name='one'></el-tab-pane>
			<el-tab-pane label='一键上传' name='two'></el-tab-pane>
		</el-tabs>
		<el-form label-width='120px' v-if='active=="one"'>
			<el-form-item v-for='item in allType' :key='item.id' :label="item.dataType+':'">
				<el-upload ref='uploadItem' :action='uploadUrl' :auto-upload='false' multiple :data='otherData(item)' :on-success='success' :before-upload='beforeUpload'>
					<el-button slot='trigger'>选择文件</el-button>
				</el-upload>
			</el-form-item>
		</el-form>
		<el-form label-width='120px' v-if='active=="two"'>
			<el-form-item label="文件列表:">
				<el-upload ref='uploadItem' :action='uploadUrl' :auto-upload='false' multiple :data='otherData()' :on-success='success' :before-upload='beforeUpload'>
					<el-button slot='trigger' class='webkitdirectory'>选择文件夹</el-button>
				</el-upload>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
		    <el-button @click="show = false">关 闭</el-button>
		    <el-button type="primary" @click="submit()">上 传</el-button>
		  </span>
	</el-dialog>
</template>
<script>
	import {
		queryTypeData,
		uploadUrl,
		queryData
	} from "./service";
	export default {
		data() {
			return {
				show: false,
				allType: [],
				uploadUrl: uploadUrl,
				selectNode: '',
				active: 'one'
			}
		},
		methods: {
			getPage(data) {
				this.show = true;
				this.active = 'one';
				this.selectNode = data;
				this.getTypeData()
			},
			async getTypeData() {
				let res = await queryTypeData();
				this.allType = res.content || [];
			},
			otherData(item) {
				return {
					uploader: this.$store.state.user.userInfo.name,
					dataTypeId: item ? item.id : '',
					uploadClassifyId: this.selectNode.id
				}
			},
			beforeUpload(file) {
				return this.checkRepeat(file);
			},
			checkRepeat(file, fn) {
				return new Promise((resolve, reject) => {
					let data = {
						condition: {
							search: file.name
						}
					};
					queryData(data).then((res) => {
						if(res.content.rows.length > 0) {
							this.$notify.error({
								title: '失败',
								message: file.name + ' 文件已存在！',
								duration: 3000
							});
							reject()
						} else {
							resolve()
						}
					});
				});
			},
			success(response, file, fileList) {
				if(response.status == "success") {
					this.$parent.queryData();
					this.$notify({
						title: '成功',
						message: response.message,
						type: 'success',
						duration: 3000
					});
				} else {
					this.$notify.error({
						title: '失败',
						message: response.message,
						duration: 3000
					});
				}
			},
			tabClick(tab, event) {
				if(tab.name == 'two') {
					this.$nextTick(() => {
						$('.webkitdirectory').next().attr('webkitdirectory', true)
					})
				}
			},
			submit() {
				if(this.$refs.uploadItem.length) {
					this.$refs.uploadItem.forEach((item) => {
						item.submit()
					})
				} else {
					this.$refs.uploadItem.submit();
				}
				alert("allType:");
			}
		}
	}
</script>

<style>
	.dataImport .el-dialog__body {
		padding: 30px !important;
	}
	
	.dataImport .el-upload-list {
		padding: 0 20px 0 0 !important;
	}
</style>