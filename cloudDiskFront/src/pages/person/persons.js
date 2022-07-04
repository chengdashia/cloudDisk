import { mapState,mapActions } from 'vuex'
import Header from '../../components/Header'
export default{
  data() {
    return {
        msg:"as大三的"
    }
  },
  components: {
   Header
  },
  computed:{
    ...mapState('PersonV',['token']),
    ...mapState('FileV',['active_index']),
  },
  mounted() {
    this.getUserInfo()
  },
  methods: {
    ...mapActions('PersonV',["change_info"]),
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    goInfo(path) {
      this.$router.replace({
        name: path,
      });
    },
    async getUserInfo(){
      const res = await this.$myRequest({
        url: '/userInfo/getUserInfo',
        method: 'post',
        header:{
          token:localStorage.getItem('token')
        },
        data: {},
      })
      if(res.data.code==200){
        localStorage.setItem('folderId',res.data.data.data.folderId)
        this.change_info(res.data.data.data)
      }
      else{
        this.$message({
          message: '登录信息失效',
          type: 'warning'
        });
        this.$router.push({
          name: 'loginLogin',
        })
      }
      // that.$store.dispatch('PersonV/change_tel',this.ruleForm.user_tel)
      // console.log(res.data.data.data)

  },
    goBackPlaza(){
      this.$router.push({
        name: 'plaza',
      });
    }
  },

}
