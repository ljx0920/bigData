<template>
	<div class="mesTableTree">
        <span id="size" ref="size"></span>
		<mes-table v-if="data" :maxHeight="height" :hideSortable="true" :hideIndex="true" :silence="true" :sampleColor="true" :table-data="data" :table-option="option" :table-loading="tableLoading" :staticIndex="true" :showCheckBox="false" >
        	<template slot-scope="operationTool">
                <slot name="operationTool">
                </slot>
            </template>
        	<el-table-column slot="head" width="50" class-name="bom">
                <template slot-scope="props">
                	<span class="split" v-if="!props.row._isLastChild" :class="{'first':props.row._isDataFirst,'last':props.row._isDataLast}"></span>
                	<span class="split right" v-if="!props.row._isChild"></span>
                	<span v-if="!props.row._isChild" class="control" @click="expand(props)" :class="{'expand':props.row._isExpanded}"></span>
                 </template>
            </el-table-column>
            <el-table-column slot="head" :label="firstLabel" :width = "firstWidth" class-name="bomChild">
                <template slot-scope="props">
            		<div class="split" v-if="props.row._isChild">
                		<span class="top"></span>
                		<span class="bottom" v-if="!props.row._expandIsLast"></span>
                		<span class="right"></span>
                	</div>
                	<span class="word">{{props.row[firstProp]}}</span>
                </template>
            </el-table-column>
        </mes-table>
	</div>
</template>

<script>
	export default {
		props:['extraData','tableOption','tableData','tableLoading'],
		data() {
			return {
				data:false,
				option:[],
				height:this.$.clientHeight() - 360,
				firstProp:"",
				firstLabel:"",
				firstWidth:0
			};
		},
		mounted(){
			this.data = this.tableData
			this.option = this.$.deepCopy(this.tableOption)
			let data = this.option.shift()
			this.firstProp = data.prop
			this.firstLabel = data.label
			
			if(this.tableData.length>0){
				this.tableData[0]._isDataFirst = true
				this.tableData[this.tableData.length-1]._isDataLast = true
			}
			
			
			let arr = this.tableData.map((val) => {
                let value = (val[this.firstProp] || "").toString()
                let len1 = value.split(";").length - 1
                let len2 = value.split("&").length - 1
                this.$refs.size.innerHTML = value.replace(/[&;]/g, "")
                return this.$refs.size.offsetWidth + 36 + len1 * 6 + len2 * 10
            })
			this.$refs.size.innerHTML = this.firstLabel
            arr.push(this.$refs.size.offsetWidth + 57)
            this.firstWidth = Math.max(...arr) + 20
			
		},
		methods:{
			expand(props){
				let setData = (data) => {
					if(this.$.isArray(data)&&data.length>0){
						props.row._isExpanded = !props.row._isExpanded
						data.forEach((val,index)=>{
							val._expandParentId = props.row.id
							val._isChild = true
							if(props.row._isDataLast){
								val._isLastChild = true
							}
						})
						data[0]._expandIsFirst = true
						data[data.length-1]._expandIsLast = true
						
						
		                if(props.row._isExpanded){
		                	this.data.splice(props.$index+1,0,...data)
		                }else{
		                	this.data = this.data.filter((val)=>{
		                		return val._expandParentId != props.row.id
		                	})
		                }
		                this.data = this.$.deepCopy(this.data)
					}
				}
            	this.$emit("expand",setData,props,this.extraData)
            },
		}
	};
</script>


<style>
	.mesTableTree #size {
        position: absolute;
        visibility: hidden;
        opacity: 0;
    }
  	.mesTableTree .el-table tr,.mesTableTree .el-table tr th,.mesTableTree .el-table tr th .cell{
  		background: rgb(250,250,250);
  	}
  	.mesTableTree .el-table .el-table__header-wrapper{
  		border-bottom:1px solid #dfe6ec
  	}
  	.mesTableTree .el-table__header-wrapper tr th:nth-of-type(2) .cell{
  		padding-left: 0;
  	}
  	.mesTableTree .el-table__body-wrapper tr td:nth-of-type(2) .cell{
  		padding-left: 0;
  	}
  	
  	.mesTableTree .bom .cell{
  		padding-right: 0;
  		line-height: 40px;
  		position: relative;
  		width: 100%;
  		height: 100%;
  	}
  	.mesTableTree .bom .cell .split{
  		height:100%;
  		width:0;
  		border-right: 1px solid rgb(151,177,212);
  		position: absolute;
  		left:24px;
  		top:0
  	}
  	.mesTableTree .bom .cell .split.right{
  		height:0;
  		width:50%;
  		border-top: 1px solid rgb(151,177,212);
  		position: absolute;
  		left:24px;
  		top:50%
  	}
  	.mesTableTree .bom .cell .split.first{
  		height:50%;
  		top:50%
  	}
  	.mesTableTree .bom .cell .split.last{
  		height:50%;
  	}
  	.mesTableTree .bom .cell .splitRight{
  		height:0%;
  		width:50%;
  		float: left;
  		border-top: 1px solid rgb(151,177,212);
  		position: absolute;
  		left:24px;
  		top:50%
  	}
  	
  	.mesTableTree .bomChild .cell{
  		padding-right: 0;
  		line-height: 40px;
  		position: relative;
  		width: 100%;
  		height: 100%;
  	}
  	.mesTableTree .bomChild .cell .split{
  		height:100%;
  		width:40px;
  		position: relative;
  		float: left;
  	}
  	.mesTableTree .bomChild .cell .word{
  		float: left;
  	}
  	.mesTableTree .bomChild .cell .split span.right{
  		height:0;
  		width:100%;
  		border-top: 1px solid rgb(151,177,212);
  		position: absolute;
  		left:0;
  		top:50%
  	}
  	.mesTableTree .bomChild .cell .split span.top{
  		height:50%;
  		width:0;
  		border-left: 1px solid rgb(151,177,212);
  		position: absolute;
  		left:0;
  		top:0
  	}
  	.mesTableTree .bomChild .cell .split span.bottom{
  		height:50%;
  		width:0;
  		border-left: 1px solid rgb(151,177,212);
  		position: absolute;
  		left:0;
  		bottom:0
  	}
  	
  	.mesTableTree .bom .cell .control{
		display: inline-block;
		height:12px;
		width:12px;
		border: 1px solid rgb(186,199,215);
		position:relative;
		cursor: pointer;
		background: rgb(250,250,250);
	}
	.mesTableTree .bom .cell .control:before{
		content: "";
		display: block;
		width: 6px;
		border: 1px solid rgb(186,199,215);
		position: absolute;
		top: 4px;
		left: 2px;
	}
	.mesTableTree .bom .cell .control:after{
		content: "";
		display: block;
		height: 6px;
		border: 1px solid rgb(186,199,215);
		position: absolute;
		top: 2px;
		left: 4px;
	}
	.mesTableTree .bom .cell .control.expand:after{
		display: none;
	}
</style>
