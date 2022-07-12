import { mapState, mapActions } from "vuex";
import axios from "axios";
export default {
  data() {
    return {
      userAvatar: "",
      input: "",
      input_user_name: "",
      input_user_introduction: "",
      input_user_pwd: "",
      input_user_local: "",
      show_tel: false,
      show_name: false,
      show_local: false,
      show_introduction: false,
      show_pwd: false,
      show_initialize: false,
      imageUrl: "https://ts1.cn.mm.bing.net/th/id/R-C.7c9c4e243fdb9a9ee4ef8543b8775774?rik=aSIWGQ1%2fQpDFcw&riu=http%3a%2f%2fimg.jutoula.com%2f202001%2f13%2f112034433.jpg&ehk=7Ih01mtXJxyOWholMYPxNQFyVqwJhAkQmJkql3lLVfc%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1",
      // imageUrl:this.data.msg,
      // myHeaders: {Authorization: localStorage.getItem('_UTK')}, //获取Token
      action: "http://localhost:9081/" + 'userinfo/updateUserAvatar',
      // action: "http://172.20.21.92:8084/" + 'file/uploadFile',
    };
  },
  mounted() {
    this.change_active_index("1-2");
    this.getUserInfo()
  },
  computed: {
    ...mapState('PersonV', ['info','token']),
    headers() {
      return {
        "Authorization": localStorage.getItem("token")
      }
    }
  },
  methods: {
    ...mapActions('FileV', ["change_active_index"]),
    change(res) {
      this.show_name = true
      console.log(res)
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
      this.imageUrl=res.data.data.data.userAvatar
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => { });
    },
    confirmChange(res) {
      if (res == 1) {
        this.changeConfirmName()
      } else if (res == 2) {
        this.changeConfirmLocal()
      } else if (res == 3) {
        this.changeConfirmIntroduction()
      } else if (res == 5) {
        this.changeConfirmPwd()
      }
    },
    // input_user_name:"",
    // input_user_introduction:"",
    // input_user_pwd:"",
    // input_user_local:"",
    changeName() {
      this.show_name = true

    },
    changeConfirmName() {
      if (this.input_user_name.length == 0) {
        this.infoTips()
      } else {
        this.$message({
          message: '修改成功',
          type: 'success'
        });
        this.show_name = false
        this.input_user_name = ''
      }
    },
    changeConfirmLocal() {
      if (this.input_user_local.length == 0) {
        this.infoTips()
      } else {
        this.$message({
          message: '修改成功',
          type: 'success'
        });
        this.show_local = false
        this.input_user_local = ''
      }
    },
    changeConfirmIntroduction() {
      if (this.introduction.length == 0) {
        this.infoTips()
      } else {
        this.$message({
          message: '修改成功',
          type: 'success'
        });
        this.show_introduction = false
        this.introduction = ''
      }
    },
    changeConfirmPwd() {
      if (this.input_user_pwd.length == 0) {
        this.infoTips()
      } else {
        this.$message({
          message: '修改成功',
          type: 'success'
        });
        this.show_pwd = false
        this.input_user_pwd = ''
      }
    },
    infoTips() {
      this.$message({
        message: '请将信息填写完整',
        type: 'warning'
      });
    },
    changeLocal() {
      this.show_local = true
    },
    changeIntroduction() {
      this.show_introduction = true
    },
    changeInitialize() {
      this.show_initialize = true
    },
    changePwd() {
      this.show_pwd = true
    },
    // 1头像上传
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/png" || "image/jpg" || "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG/PNG/JPEG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    uploadImg (f) {
      console.log(f)
      this.progressFlag = true
      let formdata = new FormData()
      formdata.append('file', f.file)
      axios({
        url: 'http://localhost:9081' + '/userInfo/updateUserAvatar',
        method: 'post',
        data: formdata,
        headers: {'Content-Type': 'multipart/form-data',
          'Accept': '*/*',
          'token':localStorage.getItem('token')},
        onUploadProgress: progressEvent => {
          // progressEvent.loaded:已上传文件大小
          // progressEvent.total:被上传文件的总大小
          this.progressPercent = (progressEvent.loaded / progressEvent.total * 100)
        }
      }).then(res => {
        this.userInfo.userAvatar = res.data.msg
        if (this.progressPercent === 100) {
          this.progressFlag = false
          this.progressPercent = 0
        }
      }).then(error => {
        console.log(error)
      })
    },

},
};
