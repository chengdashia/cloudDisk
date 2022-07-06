<template >
  <div>
    <div class="top">
      <div class="top_left">
        <div class="logo_img">
          <div>
            <el-image src="https://img1.imgtp.com/2022/06/29/BlsBNj8x.png" fit="contain" class="font_logo"></el-image>
          </div>
        </div>
      </div>
      <div class="top_right all_center">
        <el-dropdown>
          <span class="el-dropdown-link">
            {{ user_tel }}
            <span v-if="user_tel.length == 0" @click="login">请登录</span>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown" v-if="user_tel.length != 0">
            <el-dropdown-item icon="el-icon-user" @click.native="person">个人中心</el-dropdown-item>
            <el-dropdown-item icon="el-icon-arrow-right" @click.native="quit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>
<style lang="less" scoped>
@use_min_width: 1080px;
@use_top_height: 130px;
@font_color: white;
@file_info_width: 400px;

.top {
  width: 100%;
  height: 12vh;
  // background-color: rgb(96, 125, 139);
  background-image:url(../assets/header.png);
  background-size: cover;
  min-width: @use_min_width;
  display: flex;
  justify-content: space-between;
  // -webkit-box-shadow: 0px 1px 1px #de1dde;
  // -moz-box-shadow: 0px 1px 1px #de1dde;
  // box-shadow: 0px 1px 1px #de1dde;
  // min-height: 900px;
  color: #607d8b;
}

.top_left {
  // background-color: red;
}

.top_center {
  width: 400px;
  align-items: flex-end !important;
  // background-color: black;
}

.superior,
.plaza {
  color: #DCDCDC;
  width: 150px;
  text-align: center;
  cursor: pointer;
}

.active {
  color: @font_color;
  // background-color: blue;
  border-bottom: 4px solid @font_color;
}

.top_right {
  width: 350px;
  // background-color: blue;
}

.el-dropdown-link {
  // color: @font_color;
  cursor: pointer;
}

.logo_img {
  width: 300px;
  height: 100px;
  // background-color: aqua;
  display: flex;
  align-items: center;
  justify-content: center;
}

.font_logo {
  width: 200px;
  height: 100px;
  // margin-left:10vw;
  // margin-top:2vh
}

.file {
  height: 88vh;
  width: 100%;
  background-color: #f1f1f1;
  display: flex;
  min-width: @use_min_width;
  justify-content: space-between;
}

.file_left,
.file_right {
  width: 25%;
  background-color: antiquewhite;
}

.file_left_top {
  display: flex;
  width: 100%;
  height: 30vh;
  border-bottom: medium dashed #607d8b;

  flex-direction: column;
  justify-content: center;
  align-items: center;

  .user_info>div {
    width: 50%;
    text-align: center;
  }
}

.file_left_bottom {
  height: 40vh;
  // background-color: aqua;
  display: flex;
  width: 100%;
  // border-bottom: medium dashed #607d8b;

  flex-direction: column;
  justify-content: center;
}

.file_left_bottom>div {
  width: 85%;
  margin: auto;
  display: -webkit-box;
  overflow: hidden;
  // cursor: pointer;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: (3);
}

span {
  color: grey
}

.show {
  height: 60vh;
  // width: 40vw;
  display: flex;
  justify-content: center;
  align-items: center;
  // background-color: #607d8b;
}

.clcik_button {
  // margin: 0 auto;
  display: flex;
  // align-items: center;
  justify-content: center;
  // background-color: #de1dde;
}

.user_info {
  display: flex;
  width: 100%;
  height: 40px;
  align-items: center;
  // background-color: #de1dde;
  justify-content: space-between;
}

.file_left {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

}

.file_right {

  // background-color: aquamarine;
}

@media screen and(min-height:900px) {
  .top {
    // height: @use_top_height;
  }

  .logo_img {
    height: @use_top_height
  }
}

@media screen and(max-height:800px) {
  .top {
    height: 100px;
  }

  .file {
    height: 86vh;
  }
}

@media screen and(max-width:900px) {
  .top {
    // display: none;
  }
}

@media screen and(min-width:1500px) {

  // .top_left{
  //   background-color: red;
  // }
  // .top_center{
  //   // width: 1000px;
  //   background-color: yellow;
  // }
  // .top_right{
  //   // width: 350px;
  //   background-color: blue;
  // }
}
</style>
<script>
import { mapState, mapActions } from 'vuex'
export default {
  data() {
    return {
      user_tel: ""
    }
  },
  computed: {
    // ...mapState('PersonV', ['user_tel']),

  },
  mounted() {
    localStorage.getItem('token')
    console.log(this.user_tel)
    this.getData()
    this.user_tel=localStorage.getItem('user_tel')
  },
  methods: {
  ...mapActions('PersonV',["change_info",'change_tel']),
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
        localStorage.clear()
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
    },
     login() {
      this.$router.replace({
        name: 'loginLogin',
      })
    },
    async getData(){
    const res = await this.$myRequest({
        url: '/userInfo/getUserInfo',
        method: 'post',
        header:{
          token:localStorage.getItem('token')
        },
        data: {},
      })
      if(res.data.code==200){
        this.change_info(res.data.data.data)
      }
      else{
        localStorage.removeItem("token")
        localStorage.removeItem("user_tel")
        // this.$message({
        //   message: '登录信息失效',
        //   type: 'warning'
        // });
      }
    }
  },
}
</script>
