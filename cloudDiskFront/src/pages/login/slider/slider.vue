<template>
  <div>
    <Vcode :show="isShow" @success="success" @close="close"></Vcode>
    <button @click="submit">登录</button>
  </div>
</template>

<script>
import Vcode from "vue-puzzle-vcode";
export default {
  components: {
    Vcode
  },
  data() {
    return {
      isShow: false, // 验证码模态框是否出现
    }
  },
  methods: {
    submit(){
      this.isShow = true;
    },
    // 用户通过了验证
    success(msg){
      this.isShow = false; // 通过验证后，需要手动隐藏模态框
    },
    // 用户点击遮罩层，应该关闭模态框
    close(){
      this.isShow = false;
    }
  }
}
</script>

<style scoped>

</style>
