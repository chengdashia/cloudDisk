import { mapState, mapActions } from 'vuex'
export default {
  data() {
    return {
      search: '',
      folderList: [
        { fileId: 1, fileName: '文件夹1', expirationTime: '文件夹介绍', fileDelTime: '2022-3-16 13:42:13' },
        { fileId: 2, fileName: '文件夹2', expirationTime: '这是什么', fileDelTime: '2022-3-16 13:42:13',  },
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
    this.change_active_index('4')
    this.getData()
  },
  methods: {
    ...mapActions('FileV', ['change_active_index']),
    async delFileFolder(res, row) {
        const res2= await this.$myRequest({
          url:"/fileDel/delRecycleMyFile",
          method:"post",
          header:{
            token:localStorage.getItem("token")
          },
          data:{
            fileId:row.fileId
          }
        })
        if(res2.data.code==200){
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
           this.folderList.splice(res, 1)
        }
      console.log("确认删除", row.fileId)
    },
    async getData(){
      const res = await this.$myRequest({
        url: '/fileDel/getRecycleMyFileList',
        method: 'post',
        header:{
          token:localStorage.getItem("token")
        },
        data: { },
      })
      this.folderList=res.data.data
    },
    handleEdit(index, row) {
      this.recoveryMyFile(index,row.fileId)
    },
    async recoveryMyFile(res,id){
      console.log(res, id);
      const res2= await this.$myRequest({
        url:"/fileDel/recoveryMyFile",
        method:"post",
        header:{
          token:localStorage.getItem("token")
        },
        data:{
          fileId:id
        }
      })
      if(res2.data.code==200){
        this.$message({
          type: 'success',
          message: '恢复成功!'
        });
         this.folderList.splice(res, 1)
      }
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
  },
}
