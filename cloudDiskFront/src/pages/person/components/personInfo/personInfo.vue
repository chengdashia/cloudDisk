<template>
  <div>
    <el-descriptions class="margin-top" title="基本信息" :column="1" border>
      <template slot="extra">
        <el-button type="primary" size="small" @click="goInfo('changeInfo')">修改信息</el-button>
      </template>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          用户名
        </template>
        <div class="info_span">{{ userInfo.userName }}</div>
        <!-- {{ user_tel }} -->
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          手机号
        </template>
        <!-- {{ userInfo.userTel }} -->
        <div class="info_span">{{ userInfo.userTel }}</div>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          居住地
        </template>
        {{ userInfo.userLocal }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          个性化
        </template>
        <el-tag type="success" size="small" v-if="userInfo.userInitialize == 1">完成</el-tag>
        <el-tag type="info" size="small" v-if="userInfo.userInitialize == 0">未完成</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          个人介绍
        </template>
        <div class="info_span">{{ userInfo.userIntroduction }}</div>
      </el-descriptions-item>
      <el-descriptions-item v-if="userInfo.userInitialize == 1">
        <template slot="label">
          <i class="el-icon-tickets"></i>
          兴趣标签
        </template>
        <el-tag v-for="(item, index) in fileLables2" :type="item.type" :key="index" size="small">{{ item.labels }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-time"></i>
          注册时间
        </template>
        <div class="info_span">{{ userInfo.createTime }}</div>
      </el-descriptions-item>
    </el-descriptions>


  </div>
</template>
<style lang="less" scoped>
@import "./personInfos.less";
</style>
<script>
import PersonInfo from "./personInfos";
export default PersonInfo;
</script>
