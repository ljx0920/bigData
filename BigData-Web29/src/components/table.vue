<template>
	<div ref="mesTable" class="mes__table">
		<span id="size" ref="size"></span>
		<el-table v-if="ready" @expand="expand" :data="tableData" style="width:100%" @current-change="currentChange" @selection-change="selectChange" :stripe="!sampleColor" :max-height="height" v-loading="tableLoading" element-loading-text="拼命加载中" :row-class-name="tableCalssName">
			<el-table-column :fixed="!staticIndex" v-if="showCheckBox&&!hideIndex" type="selection" width="40"></el-table-column>
			<el-table-column :fixed="!staticIndex" v-if="!hideIndex" type="index" label="序号" width="70"></el-table-column>
			<slot name="head"></slot>
			<el-table-column v-for="item in tableOption" :sortable="!hideSortable" :key="item.id" :prop="item.prop" :label="item.label" :min-width="item.width"></el-table-column>
			<slot name="prop"></slot>
			<slot name="operationTool"></slot>
		</el-table>
		<el-pagination v-if="pageSize" @size-change="sizeChange" @current-change="pageChange" :page-size="pageSize" :page-sizes="[5, 10, 20, 50, 100]" layout="sizes,total, prev, pager, next" :total="total">
		</el-pagination>
	</div>
</template>

<script>
	export default {
		props: ['tableOption', 'tableData', 'tableLoading', 'total', 'pageSize', 'pageChange', 'sizeChange', 'staticIndex', 'hideSortable', 'hideIndex', 'sampleColor', 'silence', 'showCheckBox', 'maxHeight', 'tableCalssName'],
		data() {
			return {
				height: this.maxHeight || this.$.clientHeight() - 260,
				ready: false
			};
		},
		mounted() {
			for(let x of this.tableOption) {
				let arr = this.tableData.map((val) => {
					let value = (val[x.prop] || "").toString()
					let len1 = value.split(";").length - 1
					let len2 = value.split("&").length - 1
					this.$refs.size.innerHTML = value.replace(/[&;]/g, "")
					return this.$refs.size.offsetWidth + 36 + len1 * 6 + len2 * 10
				})
				this.$refs.size.innerHTML = x.label
				arr.push(this.$refs.size.offsetWidth + 57)
				x.width = Math.max(...arr) + 20
			}
			this.ready = true
		},
		watch: {
			tableData(curVal, oldVal) {
				if(!this.init || (this.init && !this.silence)) {
					this.ready = false
				}
				for(let x of this.tableOption) {
					let arr = curVal.map((val) => {
						let value = (val[x.prop] || "").toString()
						let len1 = value.split(";").length - 1
						let len2 = value.split("&").length - 1
						this.$refs.size.innerHTML = value.replace(/[&;]/g, "")
						return this.$refs.size.offsetWidth + 36 + len1 * 6 + len2 * 10
					})
					this.$refs.size.innerHTML = x.label
					arr.push(this.$refs.size.offsetWidth + 57)
					x.width = Math.max(...arr) + 20
				}
				setTimeout(() => {
					this.ready = true
				})
			},
			ready(curVal, oldVal) {
				if(curVal) {
					setTimeout(() => {
						let scollDom = $(this.$refs.mesTable).find(".el-table__body-wrapper")
						let header, footer, fixedBody, fixedRight
						scollDom.mCustomScrollbar({
							axis: "yx",
							theme: "dark",
							scrollInertia: 0,
							callbacks: {
								whileScrolling: () => {
									header = $(this.$refs.mesTable).find(".el-table").children(".el-table__header-wrapper")
									footer = $(this.$refs.mesTable).find(".el-table").children(".el-table__footer-wrapper")
									fixedBody = $(this.$refs.mesTable).find(".el-table").children(".el-table__fixed")
									fixedRight = $(this.$refs.mesTable).find(".el-table").children(".el-table__fixed-right")
									$(this.$refs.mesTable).find(".el-table__fixed-body-wrapper").css({
										"max-height": scollDom.css("max-height")
									})
									let left = -parseInt($(this.$refs.mesTable).find(".mCSB_container").css("left"))
									let top = -parseInt($(this.$refs.mesTable).find(".mCSB_container").css("top"))
									if(header.length > 0) {
										header.scrollLeft(left)
									}
									if(footer.length > 0) {
										footer[0].scrollLeft = left
									}
									if(fixedBody.length > 0) {
										fixedBody.find(".el-table__fixed-body-wrapper")[0].scrollTop = top
									}
									if(fixedRight.length > 0) {
										fixedRight.find(".el-table__fixed-body-wrapper")[0].scrollTop = top
									}
								}
							}
						});
					})
					this.init = true
				}
			}
		},
		methods: {
			selectChange(arr) {
				let res = arr.map((val) => {
					return val.id
				})
				this.$emit("selectChange", res)
			},
			currentChange(obj) {
				this.$emit("currentChange", obj)
			},
			expand(row, expanded) {
				this.$emit("expand", row, expanded)
			},
		}
	};
</script>

<style>
	.mes__table .el-table__body-wrapper {
		overflow: hidden;
	}
	
	.mes__table .el-pagination {
		margin-top: 5px;
		float: right;
	}
	
	.mes__table #size {
		position: absolute;
		visibility: hidden;
		opacity: 0;
	}
	
	.mes__table .mCSB_container_wrapper {
		position: static;
	}
	
	.mes__table .el-table__fixed {
		bottom: 0 !important
	}
	
	.mes__table .el-table__fixed-right {
		bottom: 0 !important;
		right: 0 !important
	}
</style>