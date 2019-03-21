<template>
	<div class="treeContent">
		<div class="search" v-if="filterable">
			<el-input v-model="search" :placeholder="placeholder" icon="search" @change="searchTree"></el-input>
		</div>
		<div class="ztreeContent" v-scroll>
			<div id="ztree" ref="treeDemo" class="ztree"></div>
		</div>
	</div>
</template>

<script>
	export default {
		props: {
			data: {
				type: [Array, Boolean],
				default: []
			},
			option: {},
			placeholder: {
				type: [String, Number],
				default: "请搜索"
			},
			filterable: {
				type: [Boolean],
				default: true
			}
		},
		mounted() {
			this.$refs.treeDemo.id = "ztree" + this._uid
			this.treeObj = $.fn.zTree.init($(this.$refs.treeDemo), this.option, this.data);
		},
		watch: {
			data(curVal, oldVal) {
				this.treeObj = $.fn.zTree.init($(this.$refs.treeDemo), this.option, this.data);
			}
		},
		data() {
			return {
				search: "",
				searchTree: this.$.debounce(() => {
					let data = this.data
					if(this.search) {
						let nodes = this.transformToArray(this.data)
						data = nodes.filter((val) => {
							return(val.name || "").indexOf(this.search) != -1
						})
						data = this.cplTree(data)
					}
					this.treeObj = $.fn.zTree.init($(this.$refs.treeDemo), this.option, data);
				}, 500)
			}
		},
		methods: {
			transformToArray(data) {
				let _data = JSON.parse(JSON.stringify(data))
				let res = []

				function pro(arr, id) {
					arr.forEach((val) => {
						val.pId = id
						if(val.children) {
							pro(val.children, val.id)
							val.children = []
						}
						res.push(val)
					})
				}
				pro(_data, null)
				return res
			},
			cplTree(data) {
				let res = []
				let _data = this.transformToArray(this.data)
				let getParent = (pId) => {
					let res = []
					let getP = (pId) => {
						let p = _data.filter((val) => {
							return val.id == pId
						})
						if(p.length > 0) {
							getP(p[0].pId)
							if(p[0].children) {
								p[0].children = []
							}
							res.push(p[0])
						}
					}
					getP(pId)
					return res
				}
				data.forEach((val) => {
					if(val.pId) {
						data = data.concat(getParent(val.pId))
					}
				})
				data.forEach((val, index) => {
					if(res.every((value) => {
							return value.id != val.id
						})) {
						res.push(val)
					}
				})
				return res
			},
			getTreeObj() {
				return this.treeObj
			},
			checkAllNodes(data) {
				return this.treeObj.checkAllNodes(data)
			},
			getCheckedNodes() {
				return this.treeObj.getCheckedNodes()
			},
			getCheckedNodesId() {
				return this.treeObj.getCheckedNodes().map((val) => {
					return val.id;
				});
			},
			getCheckedKeys() {
				let nodes = this.treeObj.getCheckedNodes().map((val) => {
					return val.id
				})
				return nodes
			},
			getNodesByParam(key, value, parentNode) {
				return this.treeObj.getNodesByParam(key, value, parentNode);
			},
			getNodeByParam(key, value, parentNode) {
				return this.treeObj.getNodeByParam(key, value, parentNode);
			}
		}
	};
</script>

<style>
	.treeContent {
		height: 100%;
		width: 100%;
		position: relative;
	}
	
	.ztreeContent {
		background-color: rgb(238, 241, 246);
		position: absolute;
		top: 42px;
		bottom: 0;
		left: 0;
		right: 0;
	}
</style>