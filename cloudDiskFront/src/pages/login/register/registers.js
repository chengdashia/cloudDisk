export default {
  name: "register",
  data() {
    let validTel = (rule, value, callback) => {
      let reg = /^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/
      if (!reg.test(value)) {
        callback(new Error('手机号不合法'))
      } else {
        callback()
      }
    }
    let validPassword = (rule, value, callback) => {
      let reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,20}$/
      if (!reg.test(value)) {
        callback(new Error('密码必须是由4-20位字母+数字组合'))
      } else {
        callback()
      }
    }
    let validPassword2 = (rule, value, callback) => {
      console.log(rule)
      console.log(this.ruleForm.password)
      if (value != this.ruleForm.password) {
        callback(new Error('两次输入的密码不一致'))
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
        password: '',
        password2: '',
        ver: ''
      },
      rules: {
        tel: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: validTel, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
          { validator: validPassword, trigger: 'blur' }
        ],
        password2: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validPassword2, trigger: 'blur' }
        ],
        ver: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { min: 6, max: 6, message: '请输入正确的验证码', trigger: 'blur' }
        ]
      }
    }
  },
  beforeDestroy() {
    console.log('注册组件即将被销毁了')
  },
  watch: {
  },
  methods: {
    async submitForm(formName) {
      let that = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          that.registered()
        } else {
          return false;
        }
      });
    },
    async registered() {
      let that = this
      const res = await this.$myRequest({
        url: '/userInfo/registered',
        method: 'post',
        header: {
          token: ""
        },
        data: {
          phone: that.ruleForm.tel,
          pwd: that.ruleForm.password,
          smsCode: that.ruleForm.ver,
        }
      })
      if (res.data.code == "507") {
        that.$message({
          message: '验证码有误',
          type: 'error'
        });
      }
      else if( res.data.code ==202){
        that.$message({
          message: '用户已存在',
          type: 'info'
        });
      }else if(res.data.code==200){
        that.$message({
          message: '注册成功',
          type: 'success'
        });
        
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    checkTel() {
      let that=this
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
      },1000)
    },
    async getVer(){
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
        if(res.data.code==200){
          that.$message({
            message: '下发验证码成功',
            type: 'success'
          });
          that.checkTel()
        }else{
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
  }
}
