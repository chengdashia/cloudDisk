import { mapState, mapGetters } from 'vuex'
const url = window.location.href
export default {
  data() {
    return {
      isActive: 1,
      url: url,
      user_tel:"",
      imageUrl:""
    }
  },
  watch: {
    $route(to, from) {
      if (to.name == "plaza") {
        this.isActive = 1
      }
      else {
        this.isActive = 0
      }
    },
    url: {
      immediate: true,
      handler(to, from) {
        let index = window.location.href
        if (index[index.length - 1] == 'r') {
          this.isActive = 0
        } else {
          this.goPlaza()
        }
        // if()
      }
    }
  },
  computed: {
    // ...mapState('PersonV',['user_tel']),

  },
  mounted() {
    this.user_tel=localStorage.getItem('userAccount')
    console.log("user_tel",this.user_tel)
    this.getUserInfo()
  },
  methods: {
    async getUserInfo(){
      const res = await this.$myRequest({
        url: '/userInfo/getUserInfo',
        method: 'post',
        header:{
          token:localStorage.getItem('token')
        },
        data: {},
      })
      this.imageUrl=res.data.data.data.userAvatar
    },
    goSuperior() {
      this.isActive = 0
      this.$router.push({
        name: 'superior',
      })
    },
    goPlaza() {
      this.isActive = 1
      this.$router.push({
        name: 'plaza',
      })
    },
    login() {
      this.$router.replace({
        name: 'loginLogin'
      })
    },
    quit() {
      const that = this
      console.log("退出登录")
      that.$confirm('确定退出账号?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        that.$message({
          type: 'success',
          message: '退出登录成功!'
        });
        setTimeout(() => {
          that.$router.replace({
            name: 'loginLogin',
          })
        }, 1000)
      }).catch(() => {
        that.$message({
          type: 'info',
          message: '取消退出登录'
        });
      });

    },
    person() {
      this.$router.replace({
        name: 'personInfo',
      })
    }
  },
}
