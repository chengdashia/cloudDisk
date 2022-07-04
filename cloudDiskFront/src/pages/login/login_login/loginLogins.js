import { mapState, mapActions } from 'vuex'
export default {
  name:"loginLogin",
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
        user_tel: '',
        password: '',
      },
      rules: {
        user_tel: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: validTel, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
          // { validator: validPassword, trigger: 'blur' }
        ]
      }
    }
  },
  computed:{
    ...mapState('PersonV',['user_tel','token']),
  },
  mounted() {
    this.changeMarginClass()
    console.log(this.$refs.login_now.style)
  },
  beforeDestroy() {
    console.log('登陆组件即将被销毁了')
  },
  watch: {
  },
  methods: {
    ...mapActions('PersonV',{login_tel:'change_tel'}),
    async login_tel(){
      let that=this
      const res = await this.$myRequest({
        url: '/userInfo/loginByPassword',
        method: 'post',
        header:{
          token:""
        },
        data: {
          user_pwd:this.ruleForm.password,
          user_tel:this.ruleForm.user_tel
       },
      })
      if(res.data.code==200){
        that.$message({
          message: '登陆成功',
          type: 'success'
        });
        that.$store.dispatch('PersonV/change_tel',this.ruleForm.user_tel)
        localStorage.setItem('token',res.data.data.tokenValue)
        localStorage.setItem('user_tel',this.ruleForm.user_tel)
        setTimeout(()=>{
          this.$router.push({
            name:'plaza',
          })
        },1000)
      }else if(res.data.code == 504){
        that.$message({
          message: '用户不存在',
          type: 'warning'
        });
      }else if(res.data.code==501){
        that.$message({
          message: '密码错误',
          type: 'error'
        });
      }
    },
    changeMarginClass() {
      var red = document.getElementsByClassName('el-form-item__content');
      let useCss = ''
      // if (window.screen.width > 1600) {
      //   useCss = "margin-left: 40px;"
      // }
      // else {
        useCss = "margin-left: 0"
      // }
      for (let i = 0; i < red.length; i++) {
        red[i].style.cssText = useCss
      }
    },
    detectZoom() {
      var ratio = 0,
        screen = window.screen,
        ua = navigator.userAgent.toLowerCase();
      if (window.devicePixelRatio !== undefined) {
        ratio = window.devicePixelRatio;
      }
      else if (~ua.indexOf('msie')) {
        if (screen.deviceXDPI && screen.logicalXDPI) {
          ratio = screen.deviceXDPI / screen.logicalXDPI;
        }
      }
      else if (window.outerWidth !== undefined && window.innerWidth !== undefined) {
        ratio = window.outerWidth / window.innerWidth;
      }
      if (ratio) {
        ratio = Math.round(ratio * 100);
      }
      return ratio;
    },
    async submitForm(formName) {
      let that=this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          that.login_tel()
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    var_login(){
      this.$router.push({
        name:'verLogin',
      })
    }
  }
}
