<template>
	<el-dialog v-dialog-drag title="新建角色" v-if='show' :visible.sync="show" size="tiny" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" ref="form">
			<el-form-item label="数据项:" prop="dataType">
				<el-input v-model="form.dataType" placeholder="数据项"></el-input>
			</el-form-item>
<<<<<<< HEAD
			<!-- <el-form-item label="IOV地址:" prop="queryTarget">
=======
			<el-form-item label="IOV地址:" prop="queryTarget">
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
				<el-input v-model="form.queryTarget" placeholder="IOV地址"></el-input>
			</el-form-item>
			<el-form-item label="解析地址:" prop="executeTarget">
				<el-input v-model="form.executeTarget" placeholder="解析地址"></el-input>
<<<<<<< HEAD
			</el-form-item> -->
=======
			</el-form-item>
>>>>>>> e10212a07eb61f28afed3b9dd130234dc8223128
			<el-form-item label="上传地址:" prop="serverStoreDir">
				<el-input v-model="form.serverStoreDir" placeholder="上传地址"></el-input>
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
		byCondition,
		create,
	} from "./service";

	class Form {
		constructor() {
			this.creater = "";
			this.dataType = "";
			this.serverStoreDir = "";
			this.queryTarget = "";
			this.executeTarget = "";
		}
	}
	export default {
		data() {
			return {
				show: false,
				form: {},
				rules: {
					dataType: [{
						required: true,
						message: '请输入数据项名称',
						trigger: 'change,blur'
					}, {
						validator: this.$.checkRepeat(byCondition, null, "dataType", "该名称已存在"),
						trigger: 'change,blur'
					}],
					serverStoreDir: [{
						required: true,
						message: '请输入上传地址',
						trigger: 'change,blur'
					}, {
						validator: this.$.checkRepeat(byCondition, null, "serverStoreDir", "该地址已存在"),
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
				}
			}
		},
		methods: {
			getPage() {
				this.form = new Form();
				this.show = true;
			},
			submit() {
				this.$refs['form'].validate((valid) => {
					if(valid) {
						this.form.creater = this.$store.state.user.userInfo.name;
						this.show = false;
						create(this.form).then((res) => {
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