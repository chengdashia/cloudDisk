<template>
  <div>
    <div class="top">
      <div class="top_left">
        <div class="logo_img">
          <div>
            <el-image
              src="https://img1.imgtp.com/2022/06/29/BlsBNj8x.png"
              fit="contain"
              class="font_logo"
            ></el-image>
          </div>
        </div>
      </div>
      <div class="top_center all_center">
        <!-- <div class="superior" :class="[isActive == 0 ? 'active' : '']" @click="goSuperior">优质文件</div> -->
        <div class="plaza" :class="[isActive == 1 ? 'active' : '']" @click="goPlaza">文件广场</div>
      </div>
      <div class="top_right all_center">
        <el-avatar  class="avatar" :src="imageUrl" style="margin-right: 10px"></el-avatar>
        <el-dropdown>
          <span class="el-dropdown-link">
            {{ user_tel }}
            <span v-if="user_tel.length == 0" @click="login">请登录</span>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown" v-if="user_tel.length!=0">
            <el-dropdown-item icon="el-icon-user" @click.native="person">个人中心</el-dropdown-item>
            <el-dropdown-item icon="el-icon-switch-button" @click.native="quit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <transition name="test" mode="out-in">
      <router-view />
    </transition>
  </div>
</template>
<style lang="less" scoped>
@import "./homes.less";
@import "../../public/public.less";
</style>
<script>
import HOME from './homes'
export default HOME
</script>
