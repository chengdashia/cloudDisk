import { mapState, mapActions } from 'vuex'
import VideoPlayer from './components/video'
import MusicPlay from './components/music/music'
import Header from '../../components/Header'
export default {
  data() {
    return {
      files: {
        fileId: 12,
        fileName: "",
        fileOthers: "",
        fileUploadTime: '',
        fileType: 3,
        fileClickNums: 200,
        userName: '',
        fileAvatar: '',
        avatar:'',
        fileUploadId: 45456465,
        fileLabel:""
        },
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

      // files: {  },
      // fileLables: ['轻音乐', '运动', '说唱', '国风', '流行'],
      fileLables: [],
      fileLables2: [],
      globalStatus: '',
      downloadFileUrlUse: '',
      playTips: '播放',
      fileRand: [],
      page:1,
      src: 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
      imageUrl:"",
    }
  },
  components: {
    VideoPlayer, MusicPlay, Header
  },
  props:['fileId'],
  mounted() {
    this.getUserInfo()
    // if(this.$route.params.file){
    //   this.$message({
    //     type: 'success',
    //     message: '有数据!'
    //   });
    //   // this.files=this.$route.params.file
    // }else{
    //   this.$message({
    //     type: 'error',
    //     message: '没数据!'
    //   });
    // }
    // this.$router.go(0)
    this.getFileInfo(this.$route.query.fileId)
    this.globalStatus = this.files.fileType
    this.getRandFile()
    let file=document.getElementsByClassName("file")
    file[0].style.cssText="margin:0"
    if (location.href.indexOf("#reloaded")<=0) {
      location.href = location.href + "#reloaded"+"#reloaded";
      location.reload();
    }
  },

  computed: {
    ...mapState('FileV',['download_url']),
    // file(){
    //   return this.$route.params.file
    // }
  },
  methods: {
    ...mapActions('FileV', ['change_download_url']),
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
    async getFileInfo(id) {
      //  console.log('发送请求')
      // /fileHistory/addMyFileHistoryRedis
      const res = await this.$myRequest({
        url: '/fileInfo/getFileInfoById',
        method: 'post',
        header:{
          token:localStorage.getItem("token")
        },
        data: { fileId:id },
      })
      this.src=localStorage.getItem("downloadUrl")
      console.log(res.data.data.downLoad)
      localStorage.setItem("downloadUrl",res.data.data.downLoad)
      this.change_download_url(res.data.data.downLoad)
      if(localStorage.getItem("token").length!=0){
        const res2 = await this.$myRequest({
          url: '/fileHistory/addMyFileHistory',
          method: 'post',
          header:{
            token:localStorage.getItem("token")
          },
          data: { fileId:id },
        })
        // window.location.reload()

      }
        this.files=res.data.data.data[0]
        this.fileLables=res.data.data.label[0].labelName
        this.useLabes()
    },
    useLabes() {
      const item_style = ['success', 'info', 'warning', 'danger']
      let retun_obj = []
      for (let i = 0; i < this.fileLables.length; i++) {
        let use_index = Math.ceil(Math.random() * 4);
        if(use_index==4) use_index=3
        let obj = { type: item_style[use_index], labels: this.fileLables[i] }
        retun_obj.push(obj)
      }
      this.fileLables2 = retun_obj
    },
    async getRandFile(){
      const res = await this.$myRequest({
        url: '/fileInfo/getFileRandomTen',
        method: 'post',
        header:{
          token:localStorage.getItem("token")
        },
        data: {  },
      })
      this.fileRand=res.data.data.records
    },
    quit() {
      const that = this
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
    getFileUrl(res) {
      this.downloadFileUrlUse = res

    },
    async goFileInfo(res) {
      // this.files=res
      // this.globalStatus=res.fileType
      this.playTips = '播放'

      // document.title=res.fileName
      // this.getTest()
      const res1 = await this.$myRequest({
        url: '/fileInfo/getFileInfoById',
        method: 'post',
        header:{
          token:""
        },
        data: { fileId:res.fileId },
      })
      const res2 = await this.$myRequest({
        url: '/fileHistory/addMyFileHistory',
        method: 'post',
        header:{
          token:localStorage.getItem("token")
        },
        data: { fileId:res.fileId },
      })
        this.getRandFile()
        const query=JSON.parse(JSON.stringify(this.$route.query))
        query.fileId=res.fileId;//state 修改的参数
        this.$router.push({ path: this.$route.path, query })
        this.src=res1.data.data.downLoad
        localStorage.setItem("downloadUrl",res1.data.data.downLoad)
        this.change_download_url(res1.data.data.downLoad)
        this.files=res1.data.data.data[0]
        this.fileLables=res1.data.data.label
        this.useLabes()
    },
    playFile() {
      if (this.files.fileType == 2) {
        this.$refs.musicplay.play();
      }
    },
    getIsPlay(res) {
      if (res) {
        this.playTips = '暂停'
      } else {
        this.playTips = '播放'
      }
    },
    downloadFile() {
      window.open(this.download_url)
    },
    async clickCollect() {
      if(localStorage.getItem("token")==null){
        this.$message({
          type:"warning",
          message:"请先登录"
        })
      }else{
        const res = await this.$myRequest({
          url: '/fileCollection/fileCollection',
          method: 'post',
          header:{
            token:localStorage.getItem("token")
          },
          data: { fileId:this.$route.query.fileId },
        })
        if(res.data.code==200){
          this.$message({
            type: 'success',
            message: '收藏成功!'
          });
        }else if(res.data.code==511){
          this.$message({
            type: 'warning',
            message: '已收藏文件!'
          });
        }else if(res.data.code==401){
          this.$message({
            type: 'warning',
            message: '登录信息已失效，请重新登录!'
          });
        }
      }
    }
  },
}
