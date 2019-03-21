<template>
    <div class="mes-tree" ref="mesTree">
        <div class="title">
            <span>{{title}}</span>
            <div class="tool">
                <slot name="tool"></slot>
            </div>
        </div>
        <div class="extra" ref="extra" v-show="extraLabel">
            <div class="left">{{extraLabel}}</div>
            <div class="right">
                <slot name="extra"></slot>
            </div>
        </div>
        <div class="treeBox">
            <mes-ztree ref="tree" :data="data" :option="option" :placeholder="placeholder"></mes-ztree>
        </div>
    </div>
</template>
<script>
    export default {
        props: ['treeData'],
        mounted() {
            if(this.treeData.pNode) {
                this.title = this.treeData.pNode
                this.placeholder = "请搜索" + this.treeData.pNode.replace(/列表/g, "")
            }
            this.data = this.process(this.treeData)
            if(this.$refs.extra.querySelector(".el-input__inner")) {
                this.extraLabel = this.$refs.extra.querySelector(".el-input__inner").placeholder.replace(/请选择/g, "")
            }
        },
        data() {
            return {
                extraLabel: "",
                searchData: '',
                title: "数据项列表",
                data: [],
                placeholder: "请搜索",
                option: {
                    view: {
                        addDiyDom: (treeId, treeNode) => {
                            if(treeNode.disabled) {
                                $("#" + treeNode.tId).addClass("forbid")
                            }
                        }
                    },
                    data: {
                        simpleData: {
                            enable: true
                        },
                        keep: {
                            parent: true,
                            leaf: true
                        },
                    },
                    callback: {
                        beforeClick: (treeId, treeNode, clickFlag) => {
                            if(treeNode.disabled || this.selectId == treeNode.id) {
                                return false
                            }
                        },
                        onClick: (event, treeId, treeNode) => {
                            this.selectId = treeNode.id
                            this.$emit('tree-click', treeNode);
                        }
                    }
                }
            }
        },
        watch: {
            searchData(val) {
                this.$refs.tree.filter(val);
            },
            treeData(val) {
                this.data = this.process(val)
                this.selectId = null
            }
        },
        methods: {
            process(data) {
                var process = (arr) => {
                    arr.forEach((val) => {
                        val.open = true
                        if(val.children) {
                            val.isParent = true
                            process(val.children)
                        }
                    })
                }
                process(data)
                return data
            },
            getTreeObj() {
                return this.$refs.tree.getTreeObj()
            }
        }
    };
</script>

<style>
    .mes-tree {
        height: 100%;
        width: 100%;
        display: flex;
        flex-direction: column;
    }
    
    .mes-tree .extra {
        height: 50px;
        display: flex;
    }
    
    .mes-tree .extra>.left {
        padding: 15px;
        white-space: nowrap;
    }
    
    .mes-tree .extra>.right {
        padding: 10px 15px 10px 0;
    }
    
    .mes-tree .extra>.right .cascader .el-cascader .el-input__inner {
        height: 30px
    }
    
    .mes-tree .extra>.right .cascader .el-cascader .el-cascader__label {
        line-height: 30px;
    }
    
    .mes-tree .treeBox {
        flex: 1
    }
    
    .mes-tree .title {
        padding: 10px 16px;
        border-top: 1px solid rgb(186, 199, 215);
        border-bottom: 1px solid rgb(186, 199, 215);
        background: #eef1f6;
        height: 44px;
    }
    
    .mes-tree .title>span {
        font-size: 14px;
        color: rgb(39, 64, 92);
    }
    
    .mes-tree .title>.tool {
        float: right
    }
    
    .mes-tree .title>.tool>button {
        color: #FFF;
        background-color: rgb(49, 120, 202);
        border: none;
        padding: 5px;
        font-size: 12px;
    }
    
    .mes-tree .title>.tool>button:hover {}
    
    .mes-tree .title>.tool>button.is-disabled {
        background-color: rgb(115, 163, 218);
    }
    
    .mes-tree .title>.tool>button+.el-button {
        margin-left: 7px;
    }
    
    .mes-tree .treeBox .treeContent {
        padding: 5px 16px 16px 16px;
        height: 100%;
    }
    
    .mes-tree .treeBox .treeContent .search .el-input__inner {
        border: 1px solid rgb(186, 199, 215);
    }
    
    .mes-tree .treeBox .treeContent .search .el-input__inner:focus {
        border-color: #999;
    }
    
    .mes-tree .treeBox .treeContent .ztreeContent {
        top: 42px;
        /*top: 0;*/
        left: 0;
        right: 0;
        bottom: 10px;
        background-color: transparent
    }
    
    .mes-tree .treeBox .treeContent .ztreeContent .mCSB_vertical_horizontal>.mCSB_scrollTools.mCSB_scrollTools_vertical {
        width: 7px;
    }
    
    .mes-tree .treeBox .treeContent .ztreeContent .mCSB_scrollTools.mCSB_scrollTools_horizontal {
        height: 13px;
    }
    
    .mes-tree .ztree li.forbid>a {
        cursor: not-allowed;
        color: #999
    }
    
    .mes-tree .ztree li.forbid>a:hover {
        text-decoration: none
    }
    
    .mes-tree .ztree li.forbid>a span {
        cursor: not-allowed
    }
    
    .mes-tree .ztree li span {
        max-width: 170px;
    }
    
    .mes-tree .ztree li a.curSelectedNode {
        padding: 3px 0;
        height: 23px;
        border: 1px #FFB951 solid;
    }
    
    .mes-tree .ztree li a {
        padding: 3px 0;
        height: 23px;
        border: 1px solid transparent;
    }
    
    .mes-tree .ztree li span.button.switch {
        height: 23px;
        width: 22px;
    }
    
    .ztree li ul.line {
        background-size: 11.1px 2px
    }
    
    .mes-tree .ztree li span.button {
        background-size: 193.6px 117.37px;
    }
    
    .mes-tree .ztree li span.button.root_open {
        background-position: -111.3px -65.3px
    }
    
    .mes-tree .ztree li span.button.root_close {
        background-position: -89.5px -65.3px
    }
    
    .mes-tree .ztree li span.button.roots_open {
        background-position: -111.3px -0
    }
    
    .mes-tree .ztree li span.button.roots_close {
        background-position: -89.5px 0
    }
    
    .mes-tree .ztree li span.button.center_open {
        background-position: -111.3px -21.8px
    }
    
    .mes-tree .ztree li span.button.center_close {
        background-position: -89.5px -21.8px
    }
    
    .mes-tree .ztree li span.button.bottom_open {
        background-position: -111.3px -43.6px
    }
    
    .mes-tree .ztree li span.button.bottom_close {
        background-position: -89.5px -43.6px
    }
    
    .mes-tree .ztree li span.button.noline_open {
        background-position: -111.3px -87.1px
    }
    
    .mes-tree .ztree li span.button.noline_close {
        background-position: -89.5px -87.1px
    }
    
    .mes-tree .ztree li span.button.root_docu {
        background: none;
    }
    
    .mes-tree .ztree li span.button.roots_docu {
        background-position: -67.8px 0
    }
    
    .mes-tree .ztree li span.button.center_docu {
        background-position: -67.8px -21.8px
    }
    
    .mes-tree .ztree li span.button.bottom_docu {
        background-position: -67.8px -43.6px
    }
    
    .mes-tree .ztree li span.button.noline_docu {
        background: none;
    }
    
    .mes-tree .ztree li a span.button {
        background: url("~assets/ztree/img/ztreeIcon/bk2.png") 0 0 no-repeat;
    }
    
    .mes-tree .ztree li a span.button.ico_docu {
        background: url("~assets/ztree/img/ztreeIcon/bk3.png") 0 0 no-repeat;
    }
</style>