<template>
  <div class="file_list" @scroll="scrollEvent($event)">
    <div class="search all_center">
      <div class="search_input">
        <el-input v-model="input" placeholder="请输入内容"></el-input>
      </div>
    </div>
    <div class="file_info_all">
    <div class="file_info_p" v-for="(item, index) in fileDatas" :key="item.fileId" @click="goFileInfo(item.fileId)" >
      <el-tooltip :content="item.fileOthers" placement="bottom" effect="light">
        <div class="file_tip">{{ item.fileOthers }}</div>
      </el-tooltip>
      <div class="file_introduce">
        <div>
          <el-avatar :src="item.fileAvatar">
            暂无
          </el-avatar>
        </div>
        <el-tooltip :content="item.fileName" placement="right" effect="light">
          <div class="file_introduce_text">{{ item.fileName }}</div>
        </el-tooltip>
      </div>
      <div class="filer">
        <el-tooltip :content="item.userName" placement="bottom" effect="light">
          <div>上传者：{{ item.userName }}</div>
        </el-tooltip>
        <el-tooltip :content="item.fileUploadTime" placement="bottom" effect="light">
          <div>时间：{{ item.fileUploadTime }}</div>
        </el-tooltip>
        <div>访问:{{ item.fileClickNums }}</div>
      </div>
    </div>

    </div>
    <el-empty description="找不到相关文件" v-if="fileDatas.length==0"></el-empty>
       <!-- <el-button @click="getMore"> 获取更多信息</el-button> -->
    <el-backtop target=".file_list" :visibility-height="200"></el-backtop>
  </div>
</template>
<style lang="less" scoped>
@import "./plazas.less";
@import "../../../public/public.less";
</style>
<script>
import PLAZA from './plazas.js'
export default PLAZA
</script>
