<template>
  <div>
    <el-button type="primary" @click="addNum">主要按钮</el-button>
    <el-button type="primary" @click="change">点击登陆</el-button>
    <input type="text" v-model="num" />
    {{ msg }}
    <h1>结果:{{ $store.state.sum }}</h1>使用getters
    <h3>结果:{{ $store.getters.useData }}</h3>
    <h4>useAddress:{{ address }}</h4>
    <h4>useData:{{ useData }}</h4>
  </div>
</template>
<script>
import index from './demos.js'
export default index;
</script>
