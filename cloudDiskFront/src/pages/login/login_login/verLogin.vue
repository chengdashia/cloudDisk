<template>
  <div>
    <div class="ver_login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
        <el-form-item prop="tel">
          <el-input v-model="ruleForm.tel" maxlength="11" placeholder="请输入手机号" class="input_class"
            @keyup.enter.native="submitForm('ruleForm')"></el-input>
        </el-form-item>
        <el-form-item label-width="100px" prop="ver">
          <el-input v-model="ruleForm.ver" type="text" maxlength="6" placeholder="请输入验证码" class="input_pwd"
            @keyup.enter.native="submitForm('ruleForm')"></el-input>
          <el-button type="success" @click="getVer" @keyup.enter.native="submitForm('ruleForm')" class="login_btn"
            ref="login_now" :loading="getVerLoad">{{ getVerStr }}</el-button>
        </el-form-item>
        <el-form-item>
          <div class="login_ver_btn_all">
            <el-button type="primary" @click="submitForm('ruleForm')" class="login_ver_btn" ref="login_now">立即登陆
            </el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div style="display: flex;align-items: center;justify-items: center;">
      <div class="mail_login">
        <span @click="mailLogin">邮箱登陆</span>
      </div>
      <div class="pwd_login">
        <span @click="pwdLogin">密码登陆</span>
      </div>
    </div>
  </div>
</template>
<style lang="less" scoped>
@login_width: 150px;

.ver_login {
  display: flex;
  height: 25vh;
  flex-direction: column;
  align-items: center;
  justify-items: center;
}

.input_class {
  width: 260px;
}

.input_pwd {
  width: 200px;
}

.el-form {
  margin: 0 auto;
}

.login_ver_btn_all {
  width: @login_width;
  margin: 0 auto;
  // background-color: red;
}

.login_ver_btn {
  width: @login_width;
}
.mail_login{
  width: 50%;
  text-align: center;
  color: grey;
  cursor: pointer;
}
.pwd_login {
  width: 50%;
  text-align: center;
  color: grey;
  cursor: pointer;
}

.pwd_login:hover {
  color: blue;
}

// .login_box{
//   height: 40vh !important;
// }
</style>
<script>
import { mapState, mapActions } from 'vuex'
export default {
  data() {
    let validTel = (rule, value, callback) => {
      let reg = /^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/
      if (!reg.test(value)) {
        callback(new Error('手机号不合法'))
      } else {
        callback()
      }
    }
    return {
      getVerStr: "获取验证码",
      time: 59,
      getVerLoad: false,
      ruleForm: {
        tel: '',
        ver: '',
      },
      rules: {
        tel: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: validTel, trigger: 'blur' }
        ],
        ver: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { min: 6, max: 6, message: '请输入正确的验证码', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.changeMarginClass()
  },
  computed:{
     ...mapState('PersonV',['user_tel','token']),
  },
  methods: {
     ...mapActions('PersonV',{login_tel:'change_tel'}),
    changeMarginClass() {
      var red = document.getElementsByClassName('el-form-item__content');
      var login = document.getElementsByClassName('login_box');
      login[0].style.cssText = "height : 40vh !important"
      console.log(login[0].style)
      let useCss = 'margin-left: 0'
      for (let i = 0; i < red.length; i++) {
        red[i].style.cssText = useCss
      }
    },
    mailLogin(){
      this.$router.push({
        name: 'mailVerLogin',
      })
    },
    pwdLogin() {
      this.$router.push({
        name: 'loginLogin',
      })
    },
    submitForm(formName) {
      let that = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          that.loginByVer()
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    checkTel() {
      let that = this
      let intval = setInterval(() => {
        if (that.time > 1) {
          that.time -= 1
          that.getVerLoad = true
          that.getVerStr = that.time + 's'
        } else {
          clearInterval(intval)
          that.time = 60
          that.getVerLoad = false
          that.getVerStr = '获取验证码'
        }
      }, 1000)
    },
    async loginByVer() {
      let that = this
      const res = await this.$myRequest({
        url: '/userInfo/loginByVerificationCode',
        method: 'post',
        header: {
          token: ""
        },
        data: {
          user_tel: that.ruleForm.tel,
          verification_code: that.ruleForm.ver
        }
      })
      if (res.data.code == "506") {
        that.$message({
          message: '验证码有误',
          type: 'error'
        });
      } else if (res.data.code == 200) {
        that.$message({
          message: '登陆成功',
          type: 'success'
        });
        that.$store.dispatch('PersonV/change_tel',this.ruleForm.tel)
        localStorage.setItem('token',res.data.data.tokenValue)
        localStorage.setItem('user_tel',this.ruleForm.tel)
        setTimeout(() => {
          this.$router.push({
            name: 'plaza',
          })
        }, 1000)
      }else if(res.data.code==504){
          that.$message({
          message: '用户不存在',
          type: 'warning'
        });
      }
    },
    async getVer() {
      let that = this
      let reg = /^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/
      if (reg.test(this.ruleForm.tel)) {
        const res = await this.$myRequest({
          url: '/captcha/sendSmsCaptcha',
          method: 'post',
          header: {
            token: ""
          },
          data: {
            user_tel: that.ruleForm.tel,
          }
        })
        if (res.data.code == 200) {
          that.$message({
            message: '下发验证码成功',
            type: 'success'
          });
          that.checkTel()
        } else {
          that.$message({
            message: '下发验证码失败，稍后再试',
            type: 'info'
          });
        }
      } else {
        that.$message({
          message: '手机号有误',
          type: 'warning'
        });
      }
    }
  },
  beforeDestroy() {
    console.log('验证码登陆即将被销毁了')
  },
  destroyed() {
    document.getElementsByClassName('login_box')[0].style.cssText = "height : 50vh"
  }
}
</script>
