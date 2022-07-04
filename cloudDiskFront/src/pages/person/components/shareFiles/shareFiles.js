import { mapState, mapActions } from 'vuex'
export default {
  data() {
    return {
      search: '',
      folderList: [
        { fileId: 1, fileName: '文件夹1', fileOthers: '文件夹介绍', fileUploadTime: '2022-3-16 13:42:13' },
        { fileId: 2, fileName: '文件夹2', fileOthers: '这是什么', fileUploadTime: '2022-3-16 13:42:13'  },
      ]
    }
  },
  computed: {
    folderLists: {
      set(value) {
        // this.fileDatas=value
      },
      get() {
        return this.folderList.filter((val) => {
          return val.fileName.indexOf(this.search) !== -1;
        })
      }
    }

  },
  mounted() {
    this.change_active_index('2-3')
    this.getData()
  },
  methods: {
    ...mapActions('FileV', ['change_active_index']),
    async getData(){
      const res = await this.$myRequest({
        url: '/fileInfo/getFileInfoByShare',
        method: 'post',
        header: {
          token: localStorage.getItem("token")
        },
        data: {  },
      })
      this.folderList=res.data.data
    },
    async delFileFolder(res, row) {
      const res1 = await this.$myRequest({
        url: '/fileInfo/updateFileStatus',
        method: 'post',
        header: {
          token: localStorage.getItem("token")
        },
        data: {
          fileId: row.fileId,
          fileStatus: 0
        },
      })
      if (res1.data.code == 200) {
        this.folderList.splice(res,1)
        this.$message({
          type: 'success',
          message: '取消分享成功!'
        });
      }else{
        this.$message({
          type: 'warning',
          message: '稍后再试!'
        });
      }
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
  },
}
