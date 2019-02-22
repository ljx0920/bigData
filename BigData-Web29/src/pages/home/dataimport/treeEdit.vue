<template>
	<el-dialog v-dialog-drag :title="title" v-if='show' :visible.sync="show" size="tiny" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" ref="form">
			<el-form-item label="课题" prop="name" v-if='selectNode.level==1'>
				<el-input v-model="form.name" placeholder="课题"></el-input>
			</el-form-item>
			<el-form-item label="日期" prop="name" v-if='selectNode.level==2'>
				<el-date-picker v-model="form.name" placeholder="日期" @change='dateChange'></el-date-picker>
			</el-form-item>
			<el-form-item label="科目" prop="name" v-if='selectNode.level==3'>
				<el-input v-model="form.name" placeholder="科目"></el-input>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
		    <el-button @click="show = false">取 消</el-button>
		    <el-button type="primary" @click="submit()">确 定</el-button>
		  </span>
	</el-dialog>
</template>

<script>
	import {
		treeEdit
	} from "./service";

	class Form {
		constructor(data = {}) {
			this.id = data.id;
			this.name = data.name;
		}
	}

	export default {
		data() {
			return {
				show: false,
				form: {},
				rules: {},
				title: '',
				selectNode: ''
			}
		},
		methods: {
			getPage(data) {
				this.form = new Form(data);
				this.selectNode = data;
				if(data.level == 1) {
					this.title = '编辑课题'
				} else if(data.level == 2) {
					this.title = '编辑日期'
				} else if(data.level == 3) {
					this.title = '编辑科目'
				}
				this.rules = {
					name: [{
						required: true,
						message: '请输入内容',
						trigger: 'change,blur'
					}]
				}
				this.show = true
			},
			dateChange(data) {
				this.form.name = data;
			},
			submit() {
				this.$refs['form'].validate((valid) => {
					if(valid) {
						this.show = false;
						treeEdit(this.form).then((res) => {
							this.$parent.getTree();
						});
					} else {
						return false;
					}
				});
			}
		}
	}
</script>

<style scoped>

</style>