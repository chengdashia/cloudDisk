<template>
  <div class="file_list" @scroll="scrollEvent($event)">
    <div class="file">
      <div class="file_info" v-for="(item, index) in fileData" :key="item.fileId" @click="goFileInfo(item.fileId)">
        <el-tooltip :content="item.fileOthers" placement="bottom" effect="light">
          <div class="file_tip">{{ item.fileOthers }}</div>
        </el-tooltip>
        <div class="file_introduce">
          <div>
            <el-avatar :src="item.avatar">暂无</el-avatar>
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
        <el-empty description="文件列表为空" v-if="fileData.length==0"></el-empty>
    <el-backtop target=".file_list" :visibility-height="200"></el-backtop>
  </div>
</template>
<style lang="less" scoped>
@import "./superiors.less";
</style>
<script>
import superiors from './superiors.js'
export default superiors
</script>
