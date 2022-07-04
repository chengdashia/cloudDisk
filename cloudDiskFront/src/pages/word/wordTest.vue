<template>
  <div>
    aaaa
    <el-button @click="goPreview">点击预览word文件</el-button>
    <el-button @click="downLoad">点击下载word文件</el-button>
    <div class="docWrap">
      <!-- 预览文件的地方（用于渲染） -->
      <div ref="file"></div>
    </div>
  </div>
</template>

<script>
// 引入axios用来发请求
import axios from "axios";
// 引入docx-preview插件
let docx = require("docx-preview");
export default {
  mounted() {
    console.log("使用插件的renderAsync方法来渲染", docx); //
  },
  methods: {
    // 预览
     goPreview() {
      // const res = await this.$myRequest({
      //   url: './test.docx',
      //   methods: 'get'
      // })
     this.$axios({
      // url: 'http://127.0.0.1:3040/data',http://192.168.27.114:9081
      url: 'http://192.168.27.114:9081/data',
      method:  "get",
      headers:{
      'Content-Type': 'application/x-www-form-urlencoded',
    },
      params:{
        num:10
      }
    })
        console.log( "res")
      // axios({
      //   method: "get",
      //   responseType: "blob", // 因为是流文件，所以要指定blob类型
      //   url: "./test.docx", // 自己的服务器，提供的一个word下载文件接口
      // }).then(({ data }) => {
      //   console.log(data); // 后端返回的是流文件
      //     docx.renderAsync(data, this.$refs.file); // 渲染到页面
      // });
    },
    // // 下载

  },
};
</script>
<style lang="less" scoped>
.docWrap {
  // 指定样式宽度
  width: 900px;
  overflow-x: auto;
}
</style>
