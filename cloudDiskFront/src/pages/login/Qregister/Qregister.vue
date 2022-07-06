<template>
  <div>
    <div class="title_tips">
      已有账号？
      <router-link :to="{
        name: 'loginLogin',
      }">登陆</router-link>
    </div>
    <div class="register">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="邮箱" prop="mail" >
          <el-input v-model="ruleForm.mail" maxlength="17" placeholder="请输入邮箱" class="input_class"
            @keyup.enter.native="submitForm('ruleForm')"></el-input>
        </el-form-item>
        <el-form-item label="密码" label-width="100px" prop="password">
          <el-input v-model="ruleForm.password" type="password" maxlength="20" placeholder="请输入密码" class="input_class"
            @keyup.enter.native="submitForm('ruleForm')"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" label-width="100px" prop="password2">
          <el-input v-model="ruleForm.password2" type="password" maxlength="20" placeholder="请再次输入密码"
            class="input_class" @keyup.enter.native="submitForm('ruleForm')"></el-input>
        </el-form-item>
        <el-form-item label="验证码" label-width="100px" prop="ver">
          <el-input v-model="ruleForm.ver" type="text" maxlength="6" placeholder="输入验证码" class="verification"
            @keyup.enter.native="submitForm('ruleForm')"></el-input>
          <el-button @click="getVer" size="mini" type="success" class="ver_btn" :loading="getVerLoad">{{getVerStr}}
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即注册</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style lang="less" scoped>
  @import "../loginPublic.less";
  @import "./Qregisters.less";
</style>
<script>
  import Qregisters from './Qregisters'
  export default Qregisters
</script>
