<template>
  <div class="login_all">
    <div class="dynamic-area1"></div>
    <div class="dynamic-area2"></div>
    <el-image src="https://img1.imgtp.com/2022/06/29/BlsBNj8x.png" fit="contain" class="font_logo"></el-image>
    <div class="login_box">
      <div class="title">
        <div class="title_des">
          <h3>欢迎{{ tips }}</h3>

        </div>
      </div>
      <transition name="test" mode="out-in">
        <keep-alive :include="['Qregister','register','loginLogin','wxLogin','gitLogin']">
          <router-view />
        </keep-alive>
      </transition>
    </div>
  </div>
</template>
<style lang="less" scoped>
@import "./logins.less";
@import "../../public/public.less";
body {
  // background: url(../../assets/bg.jpg) no-repeat;
  background-size: cover;
  margin: 0 auto;
  height: 100%;
}
</style>
<script >
import logins from "./logins";
export default logins;
</script>
