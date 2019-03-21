<template>
	<el-dialog v-dialog-drag title="编辑数据项" v-if="show" :visible.sync="show" size="tiny" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" ref="form">
			<el-form-item label="数据项:" prop="dataType">
				<el-input v-model="form.dataType" placeholder="数据项"></el-input>
			</el-form-item>
			<el-form-item label="IOV地址:" prop="queryTarget">
				<el-input v-model="form.queryTarget" placeholder="IOV地址"></el-input>
			</el-form-item>
			<el-form-item label="解析地址:" prop="executeTarget">
				<el-input v-model="form.executeTarget" placeholder="解析地址"></el-input>
			</el-form-item>
			<el-form-item label="上传地址:" prop="serverStoreDir">
				<el-input v-model="form.serverStoreDir" placeholder="上传地址" :disabled='true'></el-input>
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
		edit,
		byCondition
	} from "./service";

	class Form {
		constructor(data = {}) {
			this.id = data.id;
			this.dataType = data.dataType;
			this.serverStoreDir = data.serverStoreDir;
			this.queryTarget = data.queryTarget;
			this.executeTarget = data.executeTarget;
		}
	}

	export default {
		created() {},
		data() {
			return {
				show: false,
				form: {},
				rules: {}
			}
		},
		methods: {
			getPage(data) {
				this.form = new Form(data);
				this.rules = {
					dataType: [{
						required: true,
						message: '请输入数据项名称',
						trigger: 'change,blur'
					}, {
						validator: this.$.checkRepeat(byCondition, this.form.id, "dataType", "该名称已存在"),
						trigger: 'change,blur'
					}],
					queryTarget: {
						required: true,
						message: '请输入IOV地址',
						trigger: 'change,blur'
					},
					executeTarget: {
						required: true,
						message: '请输入解析地址',
						trigger: 'change,blur'
					}
				};
				this.show = true;
			},
			submit() {
				this.$refs['form'].validate((valid) => {
					if(valid) {
						this.show = false;
						edit(this.form).then((res) => {
							this.$parent.queryData();
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