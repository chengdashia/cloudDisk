import { mapState, mapActions } from 'vuex'
export default {
  data() {
    return {
      userInfo: {
        createTime: "2022-04-17 22:28:12",
        userAvatar: "暂无",
        userId: "00812c858be54d2f8fed5ed5392a2e7b",
        userInfoId: null,
        userInitialize: 0,
        userIntroduction: "暂无",
        userLocal: "未知",
        userName: "1350168278584b5b",
        userPwd: "728",
        userTel: "13501682785",
      },
      // createTime: "2022-04-17 22:28:12"
      // userAvatar: "暂无"
      // userId: "00812c858be54d2f8fed5ed5392a2e7b"
      // userInfoId: null
      // userInitialize: 0
      // userIntroduction: "暂无"
      // userLocal: "未知"
      // userName: "1350168278584b5b"
      // userPwd: "728"
      // userTel: "13501682785"
      Interestlabel: [],
      fileLables: ['轻音乐', '运动', '说唱', '国风', '流行'],
      fileLables2: [],
    }
  },
  beforeCreate() {
    // console.log("调用")
  },
  computed: {
    ...mapState('PersonV', ['user_tel', 'token']),

  },
  mounted() {
    this.change_active_index('1-1')
    this.useLabes()
    this.getUserInfo()
  },
  methods: {
    ...mapActions("FileV", ['change_active_index']),
    async getUserInfo(){
      const res = await this.$myRequest({
        url: '/userInfo/getUserInfo',
        method: 'post',
        header:{
          token:localStorage.getItem('token')
        },
        data: {},
      })
      this.userInfo=res.data.data.data
    },
    useLabes() {
      const item_style = ['success', 'info', 'warning', 'danger']
      let retun_obj = []
      for (let i = 0; i < this.fileLables.length; i++) {
        let use_index = Math.ceil(Math.random() * 4);
        let obj = { type: item_style[use_index], labels: this.fileLables[i] }
        retun_obj.push(obj)
      }
      this.fileLables2 = retun_obj
      console.log(this.fileLables2)
    },
    goInfo(path) {
      this.$router.push({
        name: path,
      });
    }
  },
}
