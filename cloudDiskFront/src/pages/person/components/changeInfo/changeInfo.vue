<template>
  <div class="change_all">
    <el-dialog title="修改手机号" :visible.sync="show_tel" width="30%" :before-close="handleClose">
      <el-input :placeholder="info.userTel" v-model="input_user_name">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show_tel = false">取 消</el-button>
        <el-button type="primary" @click="confirmChange()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改名称" :visible.sync="show_name" width="30%" :before-close="handleClose">
      <el-input :placeholder="info.userName" v-model="input_user_name">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show_name = false">取 消</el-button>
        <el-button type="primary" @click="confirmChange(1)">确 定</el-button>
      </span>
    </el-dialog>

       <el-dialog title="修改个人地址" :visible.sync="show_local" width="30%" :before-close="handleClose">
      <el-input :placeholder="info.userLocal" v-model="input_user_local">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show_local = false">取 消</el-button>
        <el-button type="primary" @click="confirmChange(2)">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改个人描述" :visible.sync="show_introduction" width="30%" :before-close="handleClose">
      <el-input :placeholder="info.userIntroduction" v-model="input_user_introduction">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show_introduction = false">取 消</el-button>
        <el-button type="primary" @click="confirmChange(3)">确 定</el-button>
      </span>
    </el-dialog>

      <el-dialog title="管理标签" :visible.sync="show_initialize" width="30%" :before-close="handleClose">
      <!-- <el-input :placeholder="info.userIntroduction" v-model="input_user_name">
      </el-input> -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="show_initialize = false">取 消</el-button>
        <el-button type="primary" @click="confirmChange(4)">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改个人密码" :visible.sync="show_pwd" width="30%" :before-close="handleClose">
      <el-input :placeholder="info.userPwd" v-model="input_user_pwd">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show_pwd = false">取 消</el-button>
        <el-button type="primary" @click="confirmChange(5)">确 定</el-button>
      </span>
    </el-dialog>

<!--    1用户头像-->
<!--    <el-upload-->
<!--      class="avatar-uploader"-->
<!--      :action= action-->
<!--      :headers="myHeaders"-->
<!--      :show-file-list="false"-->
<!--      :on-success="handleAvatarSuccess"-->
<!--      :before-upload="beforeAvatarUpload"-->
<!--    >-->
<!--      <img v-if="imageUrl" :src="imageUrl" class="avatar" />-->
<!--      <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
<!--    </el-upload>-->
<!--    2头像上传-->
    <el-avatar  class="avatar" :src="imageUrl"></el-avatar>
    <el-upload class="img-btn" action="#"
               :show-file-list="false"  :before-upload="beforeAvatarUpload"
               :http-request="uploadImg" >
      <el-button type="success" plain round size="mini" :headers="headers" class="change">更改头像</el-button></el-upload>

<!--&lt;!&ndash;    3头像上传&ndash;&gt;-->
<!--    <el-upload-->
<!--      class="avatar-uploader"-->
<!--      action="http://127.0.0.1:9081/userinfo/updateUserAvatar"-->
<!--      :show-file-list="false"-->
<!--      :on-success="handleAvatarSuccess"-->
<!--      :on-remove="handleRemove"-->
<!--      :before-upload="beforeAvatarUpload">-->
<!--      <img v-if="imageUrl" :src="imageUrl" class="avatar">-->
<!--      <span v-if="imageUrl" class="el-upload-action" @click.stop="handleRemove()"></span>-->
<!--      <i v-else class="el-icon-upload2 avatar-uploader-icon" stop></i>-->
<!--    </el-upload>-->

    <div class="labels">
      <div class="label_left">
        手机号:
      </div>
      <div class="label_right">
        <el-input placeholder="请输入内容" v-model="info.userTel" :disabled="true">
        </el-input>
      </div>
<!--      <div class="operation_button">-->
<!--      </div>-->
    </div>
    <div class="labels">
      <div class="label_left">
        名称:
      </div>
      <div class="label_right">
        <el-input placeholder="请输入内容" v-model="info.userName" :disabled="true">
        </el-input>
      </div>
<!--      <div class="operation_button">-->
<!--        <el-button type="primary" round @click="changeName">修改</el-button>-->
<!--      </div>-->
    </div>
    <div class="labels">
      <div class="label_left">
        地址:
      </div>
      <div class="label_right">
        <el-input placeholder="请输入内容" v-model="info.userLocal" :disabled="true">
        </el-input>
      </div>
<!--      <div class="operation_button">-->
<!--        <el-button type="primary" round @click="changeLocal">修改</el-button>-->
<!--      </div>-->
    </div>
    <div class="labels">
      <div class="label_left">
        描述:
      </div>
      <div class="label_right">
        <el-input placeholder="请输入内容" v-model="info.userIntroduction" :disabled="true">
        </el-input>
      </div>
<!--      <div class="operation_button">-->
<!--        <el-button type="primary" round @click="changeIntroduction">修改</el-button>-->
<!--      </div>-->
    </div>
       <div class="labels">
      <div class="label_left">
        标签:
      </div>
      <div class="label_right">
        <el-input placeholder="请输入内容" v-model="info.userIntroduction" :disabled="true">
        </el-input>
      </div>
<!--      <div class="operation_button">-->
<!--        <el-button type="primary" round @click="changeInitialize">修改</el-button>-->
<!--      </div>-->
    </div>
    <div class="labels">
      <div class="label_left">
        密码:
      </div>
      <div class="label_right">
        <el-input placeholder="请输入内容" v-model="info.userPwd" :disabled="true" type="password">
        </el-input>
      </div>
<!--      <div class="operation_button">-->
<!--        <el-button type="primary" round @click="changePwd">修改</el-button>-->
<!--      </div>-->
    </div>
    <div class="operation_button">
      <el-button type="primary" round @click="changeUserInfo">修改</el-button>
    </div>
  </div>
</template>
<style lang="less" scoped>
@import "./changeInfos.less";
</style>
<script>
import ChangeInfo from "./changeInfos";
export default ChangeInfo;
</script>
