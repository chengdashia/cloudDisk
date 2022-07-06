<template>
  <div>
    <div class="title_tips">
      没有账号？
      <router-link :to="{ name: 'register',}">注册</router-link>
<!--      <router-link :to="{name:'mail',}">邮箱注册</router-link>-->
    </div>
    <div class="login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
        <div>
          <el-form-item prop="user_tel">
            <el-input
              v-model="ruleForm.user_tel"
              maxlength="11"
              placeholder="请输入手机号"
              class="input_class"
              @keyup.enter.native="submitForm('ruleForm')"
            ></el-input>
          </el-form-item>
        </div>
        <el-form-item label-width="100px" prop="password">
          <el-input
            v-model="ruleForm.password"
            type="password"
            maxlength="20"
            placeholder="请输入密码"
            class="input_class"
            @keyup.enter.native="submitForm('ruleForm')"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="submitForm('ruleForm')"
            class="login_btn"
            ref="login_now"
          >立即登陆</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="lognin_ver" @click="var_login">使用验证码登陆</div>
    <el-divider content-position="center">第三方登陆</el-divider>
    <div class="other_login">
      <i class="el-icon-chat-round"></i>
      <i class="el-icon-minus"></i>
      <i class="el-icon-plus"></i>
      <el-button icon="el-icon-more" type="primary" @click="click1" v-show="conShow" circle></el-button>
      <i class="el-icon-chat-line-round" v-show="conShow2"></i>
      <i class="el-icon-chat-dot-round" v-show="conShow3"></i>
      <el-button icon="el-icon-d-arrow-left" type="primary" @click="click2" v-show="conShow4" circle class="el-button2" size="mini"></el-button>
    </div>
  </div>
</template>
<style lang="less" scoped>
@import "../loginPublic.less";
@import "./loginLogins.less";
</style>
<script>
import loginLogins from './loginLogins'
export default loginLogins
</script>
