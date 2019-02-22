<template>
    <div class="tableLayout">
        <mes-breadcrumb v-if="!hideCrumb"></mes-breadcrumb>
        <div class="layoutContainer" :class="{tree:treeData,collapsed:collapsed}">
            <div v-if="treeData" class="treeContainer">
            	<div class="tapContainer">
            		<div class="tap" @click="collapsed=!collapsed"></div>
            	</div>
                <mes-tree :tree-data="treeData" @tree-click="treeClick" ref="tree">
                    <template slot="extra">
                        <slot name="extra">
                        </slot>
                    </template>
                    <template slot="tool">
                        <slot name="tool">
                        </slot>
                    </template>
                </mes-tree>
            </div>
            <div class="tableContainer">
                <slot name="container"></slot>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: ['treeData', 'treeTool', 'treeExtra', 'hideCrumb'],
        data() {
            return {
                collapsed: false
            };
        },
        methods: {
            treeClick(data) {
                this.$emit('tree-click', data);
            },
            setFirstTreeNode(){
            	let treeObj = this.$refs.tree.getTreeObj()
            	let validNodes = treeObj.transformToArray(treeObj.getNodes()).filter((val)=>{
            		return !val.disabled
            	})
            	if(validNodes[0]){
            		treeObj.selectNode(validNodes[0]);
            		return validNodes[0]
            	}else{
            		return false
            	}
            },
            getTreeObj(){
            	return this.$refs.tree.getTreeObj()
            }
        }
    };
</script>

<style>
    .tableLayout .layoutContainer {
        position: absolute;
        top: 50px;
        left: 0;
        right: 0;
        bottom: 0;
    }
    
    .tableLayout .layoutContainer.tree .treeContainer {
        width: 220px;
        height: 100%;
        position: relative;
        left: 0;
        transition: all linear 0.1s;
    }
    
    .tableLayout .layoutContainer.tree .treeContainer .tapContainer {
    	position: absolute;
        top: calc(50% - 20px);
        right: 0;
        height: 40px;
        width: 18px;
        z-index: 99;
        overflow: hidden;
    }
    .tableLayout .layoutContainer.tree .treeContainer .tap {
        height:100%;
        width:100%;
        color: #ddd;
        box-shadow: 0 0 8px #ddd;
       	border: 1px solid #ddd;
        cursor: pointer;
        position: relative;
        opacity:0.2;
        transition: all linear 0.1s;
        background: #666;
    }
    .tableLayout .layoutContainer.tree .treeContainer .tapContainer:hover .tap {
        opacity:1;
    }
    
    .tableLayout .layoutContainer.tree .treeContainer .tap:after,
    .tableLayout .layoutContainer.tree .treeContainer .tap:before {
        content: "";
        display: block;
        height: 3px;
        width: 10px;
        background: #ddd;
        position: absolute;
        left: 3px;
    }
    
    .tableLayout .layoutContainer.tree .treeContainer .tap:before {
        top: 14px;
        transform: rotate(-45deg);
    }
    
    .tableLayout .layoutContainer.tree .treeContainer .tap:after {
        bottom: 15px;
        transform: rotate(45deg);
    }
    .tableLayout .layoutContainer.tree.collapsed .treeContainer .tapContainer {
        right: -17px;
    }
    .tableLayout .layoutContainer.tree.collapsed .treeContainer .tapContainer .tap{
        right: 0;
    }
    
    .tableLayout .layoutContainer.tree .tableContainer {
        position: absolute;
        top: 0;
        bottom: 0;
        left: 220px;
        right: 0;
        transition: all linear 0.1s;
    }
    
    .tableLayout .layoutContainer.tree.collapsed .treeContainer {
        left: -220px;
    }
    
    .tableLayout .layoutContainer.tree.collapsed .tableContainer {
        left: 0;
    }
    .tableLayout .layoutContainer.tree.collapsed .treeContainer .tap:before {
        transform: rotate(45deg);
    }
    
    .tableLayout .layoutContainer.tree.collapsed .treeContainer .tap:after {
        transform: rotate(-45deg);
    }
    
    .tableLayout .mes-tree {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        border-right: 1px solid #ccc;
        border-top: 4px solid rgb(68,140,203);
    }
    
    .tableLayout .mes-tool {
        position: absolute;
        top: 0;
        height: 56px;
        left: 0;
        right: 0;
        padding: 19px 20px 0;
    }
    
    .tableLayout .mes-tool .el-button {
        float: left;
    }
    
    .tableLayout .mes-tool .el-button i {
        margin-left: 5px
    }
    
    .tableLayout .mes-tool .split {
        float: left;
        display: inline-block;
        height: 100%;
        width: 1px;
        background: #eee;
        margin: 0 25px;
    }
    
    .tableLayout .mes-tool .el-input {
        width: 220px;
        float: right;
    }
    
    .tableLayout .mes-table {
        position: absolute;
        top: 60px;
        left: 0;
        right: 0;
        bottom: 0;
        padding: 11px 20px;
    }
</style>