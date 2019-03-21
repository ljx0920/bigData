<template>
    <div class="headBreadcrumb">
        <span class="icon-map-marker"></span>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: item.path }" v-for="item in data" :key="item.id" v-if="item.name">{{item.name}}</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
</template>

<script>
    export default {
        created() {
            this.data = this.$route.matched || []
        },
        data() {
            return {
                data: []
            };
        }
    };
</script>

<style>
    .headBreadcrumb {
        height: 50px;
        width: 100%;
        padding: 15px 25px;
        border-bottom: 1px solid rgb(220, 220, 220);
        background: #FAFAFA;
    }
    
    .headBreadcrumb>span {
        float: left;
        font-size: 18px;
        color: #ADADAF;
        margin-right: 8px;
    }
    
    .headBreadcrumb>.el-breadcrumb {
        float: left;
    }
    
    .el-breadcrumb__item__inner,
    .el-breadcrumb__item__inner a,
    .el-breadcrumb__separator {
        font-size: 16px;
    }
</style>