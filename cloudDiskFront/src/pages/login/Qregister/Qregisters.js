export default {
  name: "register",
  data() {
    let validMail = (rule, value, callback) => {
      let reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
      if (!reg.test(value)) {
        callback(new Error('邮箱不合法'))
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
        mail: '',
        password: '',
        password2: '',
        ver: ''
      },
      rules: {
        mail: [{
            required: true,
            message: '请输入邮箱',
            trigger: 'blur'
          },
          {
            validator: validMail,
            trigger: 'blur'
          }
        ],
        password: [{
            required: true,
            message: '请输入确认密码',
            trigger: 'blur'
          },
          {
            validator: validPassword,
            trigger: 'blur'
          }
        ],
        password2: [{
            required: true,
            message: '请再次输入密码',
            trigger: 'blur'
          },
          {
            validator: validPassword2,
            trigger: 'blur'
          }
        ],
        ver: [{
            required: true,
            message: '请输入验证码',
            trigger: 'blur'
          },
          {
            min: 6,
            max: 6,
            message: '请输入正确的验证码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  beforeDestroy() {
    console.log('注册组件即将被销毁了')
  },
  watch: {},
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
        url: '/userInfo/registerByMail',
        method: 'post',
        header: {
          token: ""
        },
        data: {
          mailbox: that.ruleForm.mail,
          mailCode: that.ruleForm.ver,
          pwd: that.ruleForm.password,
        }
      })
      if (res.data.code == "507") {
        that.$message({
          message: '验证码有误',
          type: 'error'
        });
      } else if (res.data.code == 202) {
        that.$message({
          message: '用户已存在',
          type: 'info'
        });
      } else if (res.data.code == 200) {
        that.$message({
          message: '注册成功',
          type: 'success'
        });
        setTimeout(() => {
          this.$router.push({
            name: 'loginLogin',
          })
        }, 1000)
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
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
    async getVer() {
      let that = this
      let reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
      if (reg.test(this.ruleForm.mail)) {
        const res = await this.$myRequest({
          url: '/mail/sendMailCodeForRegister',
          method: 'post',
          header: {
            token: ""
          },
          data: {
           mailbox: that.ruleForm.mail,
          }
        })
        if(res.data.code==200){
          that.$message({
            message: '发送验证码成功',
            type: 'success'
          });
          that.checkMail()
        }else{
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
  }
}
