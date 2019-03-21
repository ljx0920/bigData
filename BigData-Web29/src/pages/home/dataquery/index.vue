<template>
	<div>
		<mes-table-layout :tree-data="treeData" @tree-click="treeClick" ref="layout">
			<div class="mes-content" slot="container">
				<iframe :src='selectNode.src+"&time=" + new Date().getTime()'></iframe>
			</div>
		</mes-table-layout>
	</div>
</template>

<script>
	import { queryData } from './service'
	export default {
		mounted() {
			this.getTree()
		},
		data() {
			return {
				treeData: [],
				selectNode: {}
			}
		},
		methods: {
			async getTree() {
				let res = await queryData();
				let arr = [];
				res.content.forEach((item) => {
					arr.push({
						"id": item.id,
						"name": item.dataType,
						'src': item.queryTarget
					})
				});
				this.treeData = this.$.transformTree(arr, {
					listArray: true
				});
				this.$nextTick(() => {
					if(this.$refs.layout.setFirstTreeNode()) {
						this.treeClick(this.$refs.layout.setFirstTreeNode())
					}
				})
			},
			treeClick: function(node) {
				this.selectNode = node
			}
		}
	};
</script>

<style scoped>
	.mes-content {
		height: 100%;
	}
	
	iframe {
		width: 100%;
		height: 100%;
		border: 0;
	}
</style>