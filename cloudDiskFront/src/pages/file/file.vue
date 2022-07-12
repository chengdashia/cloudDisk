<template>
  <div>
    <Header></Header>
    <div class="file">
      <div class="file_left">
        <div class="file_left_top">
          <div class="files_avatar">
            <el-avatar :src="imageUrl" :size="130" class="avatar"></el-avatar>
          </div>
          <div class="user_info">
            <div class="user_info_sub">
              <div class="user_info_sub_left"><span>上传者：</span> </div>
              <div class="user_info_sub_right">{{ files.userName }}</div>
            </div>
            <div class="user_info_sub">
              <div class="user_info_sub_left"><span>ID：</span> </div>
              <div class="user_info_sub_right">{{ files.fileUploadId }}</div>
            </div>
            <!-- <div class="user_info_sub">
              <span>ID：</span>
              {{ files.fileUploadId }}
            </div> -->
          </div>
        </div>
        <div class="file_left_bottom">
          <div>
            <span>文件名:</span>
            {{ files.fileName }}
          </div>
          <div>
            <span>上传时间:</span>
            {{ files.fileUploadTime }}
          </div>
          <el-tooltip :content="files.fileOthers" placement="top" effect="light" v-if="files.fileOthers.length > 61">
            <div>
              <span>介绍：</span>
              {{ files.fileOthers }}
            </div>
          </el-tooltip>
          <div v-if="files.fileOthers.length < 61">
            <span>介绍：</span>
            {{ files.fileOthers }}
          </div>
          <div>
            <span>访问次数：</span>
            {{ files.fileClickNums }}
          </div>
          <div>
            <span>文件类型:</span>
            <span v-if="files.fileType == 1">文档</span>
            <span v-if="files.fileType == 2">音乐</span>
            <span v-if="files.fileType == 3">视频</span>
            <span v-if="files.fileType == 4">图片</span>
            <span v-if="files.fileType == 5">其他</span>

          </div>
          <div>
            <span>文件标签:</span>
            <el-tag v-for="(item, index) in fileLables" :type="item.type" :key="index" size="small">{{ item}}
            </el-tag>

          </div>
        </div>
      </div>
      <div class="file_center">
        <div class="show">
          <MusicPlay v-if="files.fileType == 2" @getFileUrl="getFileUrl" ref="musicplay" @getIsPlay="getIsPlay"
            :musicUrl="1111" />
          <VideoPlayer v-if="files.fileType == 3" @getFileUrl="getFileUrl" />
          <div class="demo-image__placeholder" v-if="files.fileType == 4">
            <div class="block">
              <el-image :src="src">
                <div slot="placeholder" class="image-slot">
                  加载中<span class="dot">...</span>
                </div>
              </el-image>
            </div>
          </div>
          <div class="demo-image__placeholder" v-if="files.fileType == 5">
            <div class="block">
              <el-image src="https://pica.zhimg.com/v2-e375fcdf3cf16c0c1e434e431e18d7d9_1440w.jpg?source=172ae18b">
                <div slot="placeholder" class="image-slot">
                  加载中<span class="dot">...</span>
                </div>
              </el-image>
            </div>
          </div>

          <el-empty :description="files.fileName" v-if="files.fileType == 1"></el-empty>
        </div>

        <div class="click_button">
          <div>
            <el-button @click="playFile" type="primary" v-if="files.fileType == 2">点击{{ playTips }}</el-button>
            <el-button @click="downloadFile" type="success">点击下载</el-button>
            <el-button @click="clickCollect" type="warning">点击收藏</el-button>
          </div>
        </div>
      </div>

      <div class="file_right">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>可能感兴趣</span>
            <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
          </div>
          <div v-for="item in fileRand" :key="item.fileId" class="test" @click="goFileInfo(item)">
            {{ item.fileName }}
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>
<style lang="less" scoped>
@import "./files.less";
@import "../../public/public.less";
</style>
<script >
import Files from "./files";
export default Files;
</script>
