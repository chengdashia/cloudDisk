<template>
  <div>
    <el-dialog title="图片剪裁" :visible.sync="dialogVisible" append-to-body @close="closeCropper">
      <div class="cropper-content">
        <div class="cropper" style="text-align:center">
          <vueCropper ref="cropper" :img="option.img" :outputSize="option.size" :outputType="option.outputType"
            :info="true" :full="option.full" :canMove="option.canMove" :canMoveBox="option.canMoveBox"
            :original="option.original" :autoCrop="option.autoCrop" :fixed="option.fixed"
            :fixedNumber="option.fixedNumber" :centerBox="option.centerBox" :infoTrue="option.infoTrue"
            :fixedBox="option.fixedBox"></vueCropper>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeCropper">取 消</el-button>
        <el-button type="primary" @click="finish" :loading="loading">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: "cropper",
  data() {
    return {
      // 图片裁剪
      dialogVisible: true,
      // 裁剪配置
      option: {
        img: '',                // 裁剪图片的地址
        info: true,             // 裁剪框的大小信息
        outputSize: 0.8,        // 裁剪生成图片的质量
        outputType: 'jpeg',     // 裁剪生成图片的格式
        canScale: false,        // 图片是否允许滚轮缩放
        autoCrop: true,         // 是否默认生成截图框
        // autoCropWidth: 300,  // 默认生成截图框宽度
        // autoCropHeight: 200, // 默认生成截图框高度
        fixedBox: false,        // 固定截图框大小不允许改变
        fixed: false,           // 是否开启截图框宽高固定比例
        fixedNumber: [1, 1],    // 截图框的宽高比例
        full: true,             // 是否输出原图比例的截图
        canMoveBox: true,       // 截图框能否拖动
        original: false,        // 上传图片按照原始比例渲染
        centerBox: true,        // 截图框是否被限制在图片里面
        infoTrue: true          // true 为展示真实输出图片宽高 false 展示看到的截图框宽高
      },
      // 防止重复提交
      loading: false,
      // 裁剪过后的图片
      imgUrl: ""

    };
  },

  methods: {
    // 选择图片（加在图片上的事件，点击图片，获取图片的链接地址）
    selectImage(src) {
      // 设置图片Base64
      this.setImageBase64(src, (base64) => {
        this.option.img = base64;
        this.dialogVisible = true;
      });
    },

    // 设置图片Base64
    setImageBase64(src, callback) {
      let image = new Image();
      // 支持跨域
      image.crossOrigin = "anonymous";
      // 处理缓存
      image.src = src + '?v=' + Math.random();
      // 动态生成
      image.onload = function () {
        let canvas = document.createElement("canvas");
        canvas.width = image.width;
        canvas.height = image.height;
        let ctx = canvas.getContext("2d");
        ctx.drawImage(image, 0, 0, image.width, image.height);
        let result = canvas.toDataURL("image/png");
        callback && callback(result);
      }
    },

    // 确认裁剪
    finish() {
      this.$refs.cropper.getCropData((data) => {
        this.loading = true;
        let formData = new FormData();
        // 以表单的形式将数据传给服务端
        formData.append("imgBase64", data);
        // 此处上传的地址为模拟地址
        this.$http.post("saveBase64", formData).then((result) => {
          if (result.code == 10000) {

            this.loading = false;
            this.dialogVisible = false;

            // 获取服务端返回的裁剪图片地址
            this.imgUrl = result.url;
          }
        });
      })
    },
    // 关闭裁剪
    closeCropper() {
      this.dialogVisible = false;
    }
  }
};
</script>
<style lang="less" scoped>
// @import "./files.less";
// @import "../../public/public.less";
.cropper-content {
    .cropper {
        width: auto;
        height: 300px;
    }
}
</style>
