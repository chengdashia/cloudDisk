<template>
  <div>
    <div class="ver_login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
        <el-form-item prop="mail">
          <el-input v-model="ruleForm.mail" maxlength="17" placeholder="请输入邮箱" class="input_class"
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
      <div class="pwd_login">
        <span @click="pwdLogin">密码登陆</span>
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
.pwd_login {
  width: 80%;
  text-align: right;
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
    let validMail = (rule, value, callback) => {
      let reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
      if (!reg.test(value)) {
        callback(new Error('邮箱不合法'))
      } else {
        callback()
      }
    }
    return {
      getVerStr: "获取验证码",
      time: 59,
      getVerLoad: false,
      ruleForm: {
        mail: '',
        ver: '',
      },
      rules: {
        mail: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: validMail, trigger: 'blur' }
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
    ...mapState('PersonV',['user_mail','token']),
  },
  methods: {
    ...mapActions('PersonV',{login_mail:'change_mail'}),
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
    checkMail() {
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
        url: '/userInfo/loginByMail',
        method: 'post',
        header: {
          token: ""
        },
        data: {
          mailbox: that.ruleForm.mail,
          mailCode: that.ruleForm.ver
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
        that.$store.dispatch('PersonV/change_mail',this.ruleForm.mail)
        localStorage.setItem('token',res.data.data.tokenValue)
        localStorage.setItem('user_mail',this.ruleForm.mail)
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
      let reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
      if (reg.test(this.ruleForm.mail)) {
        const res = await this.$myRequest({
          url: '/mail/sendMailCodeForLogin',
          method: 'post',
          header: {
            token: ""
          },
          data: {
            mailbox: that.ruleForm.mail,
          }
        })
        if (res.data.code == 200) {
          that.$message({
            message: '发送验证码成功',
            type: 'success'
          });
          that.checkTel()
        } else {
          that.$message({
            message: '发送验证码失败，稍后再试',
            type: 'info'
          });
        }
      } else {
        that.$message({
          message: '邮箱有误',
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
