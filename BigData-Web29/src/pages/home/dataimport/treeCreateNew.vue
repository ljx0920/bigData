<template>
	<el-dialog v-dialog-drag :title="title" v-if='show' :visible.sync="show" size="tiny" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" ref="form" label-width="80px" >
			<el-form-item label="课题" prop="name" >
				<el-input v-model="form.name" placeholder="课题"></el-input>
			</el-form-item>
			<el-form-item label="日期" prop="date" >
				<el-date-picker v-model="form.date" placeholder="日期" @change='dateChange'></el-date-picker>
			</el-form-item>
			<el-form-item label="科目" prop="name1" >
				<el-input v-model="form.name1" placeholder="科目"></el-input>
			</el-form-item>
			<el-form-item label="GPS:">
				<el-upload ref='uploadItem' :action='uploadUrl' :auto-upload='false' multiple :data='otherData(item)' :on-success='success' :before-upload='beforeUpload'>
					<el-button slot='trigger'>选择文件</el-button>
				</el-upload>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
		    <el-button >取 消</el-button>
		    <el-button type="primary" @click="submit()">确 定</el-button>
		  </span>
	</el-dialog>
</template>

<script>
	import {
		treeCreateNew,
		queryTypeData,
		uploadUrl,
		queryData
	} from "./service";

	class Form1{
		constructor() {
			this.name = "";
			this.date = "";
			this.parentId = "";
			this.name1 = "";
		}
	}

	export default {
		data() {
			return {
				show: false,
				form: {},
				rules: {},
				title: '',
				allType: [],
				selectNode: '',
				uploadUrl: uploadUrl,
			}
		},
		methods: {
			getPage(data) {
				this.show = true;
				this.form = new Form1();
				this.title = '新建';
				this.selectNode = data;
				this.getTypeData();
				this.rules = {
					name: [{
						required: true,
						message: '请输入内容',
						trigger: 'change,blur'
					}],
					name1: [{
						required: true,
						message: '请输入内容',
						trigger: 'blur'
					}]
				}
			},
			async getTypeData() {
				let res = await queryTypeData();
				this.allType = res.content || [];
			},
			dateChange(data) {
				this.form.date = data;
			},			
			otherData(item) {
				return {
					uploader: this.$store.state.user.userInfo.name,
					dataTypeId: '7',
					uploadClassifyId:'0',
					name1:this.form.name1
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
			submit() {
				this.$refs['form'].validate((valid) => {
					if(valid) {
						this.show = false;
						this.form.parentId = this.selectNode.id;					
						treeCreateNew(this.form).then((res) => {
							this.$parent.getTree();
						});
					} else {
						return false;
					}
				
				if(this.$refs.uploadItem.length) {
					this.$refs.uploadItem.forEach((item) => {
						item.submit()
					})
				} else {
					this.$refs.uploadItem.submit();
				}
				});
			}
		}
	}
</script>


<style scoped>

</style>