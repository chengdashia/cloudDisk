import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
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
    };
  },
  mounted() {
    this.change_active_index("1-2");
  },
  computed: {
    ...mapState('PersonV', ['info']),
  },
  methods: {
    ...mapActions('FileV', ["change_active_index"]),
    change(res) {
      this.show_name = true
      console.log(res)
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
  },
};
