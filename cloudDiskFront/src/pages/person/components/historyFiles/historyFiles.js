import { mapState, mapActions } from 'vuex'
export default {
  data() {
    return {
      search: '',
      folderList: [
        { folderId: 1, fileName: '文件夹1', fileOthers: '文件夹介绍', viewTime: '2022-3-16 13:42:13' },
        { folderId: 2, fileName: '文件夹2', fileOthers: '这是什么', viewTime: '2022-3-16 13:42:13',  },
        { folderId: 3, fileName: '文件夹3', fileOthers: '这啥也不是', viewTime: '2022-3-16 13:42:13' },
        { folderId: 4, fileName: '文件夹4', fileOthers: '12313', viewTime: '2022-3-16 13:42:13' },
        { folderId: 5, fileName: '文件夹5', fileOthers: '222', viewTime: '2022-3-16 13:42:13' },
        { folderId: 6, fileName: '文件夹6', fileOthers: '11', viewTime: '2022-3-16 13:42:13',  },
        { folderId: 7, fileName: '文件夹7', fileOthers: '', viewTime: '2022-3-16 13:42:13',  },
        { folderId: 8, fileName: '文件夹8', fileOthers: '驱蚊器翁', viewTime: '2022-3-16 13:42:13' },
        { folderId: 9, fileName: '文件夹9', fileOthers: '刚刚过', viewTime: '2022-3-16 13:42:13',  },
        { folderId: 10, fileName: '文件夹10', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
        { folderId: 11, fileName: '文件夹11', fileOthers: '44445', viewTime: '2022-3-16 13:42:13' },
        { folderId: 12, fileName: '文件夹12', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
        { folderId: 13, fileName: '文件夹13', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
        { folderId: 14, fileName: '文件夹14', fileOthers: '22123', viewTime: '2022-3-16 13:42:13' },
        { folderId: 15, fileName: '文件夹15', fileOthers: '好几个', viewTime: '2022-3-16 13:42:13' },
        { folderId: 16, fileName: '文件夹16', fileOthers: '发生', viewTime: '2022-3-16 13:42:13' },
        { folderId: 17, fileName: '文件夹17', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
        { folderId: 18, fileName: '文件夹18', fileOthers: '123123', viewTime: '2022-3-16 13:42:13' },
        { folderId: 19, fileName: '文件夹19', fileOthers: '213', viewTime: '2022-3-16 13:42:13' },
        { folderId: 20, fileName: '文件夹20', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
        { folderId: 21, fileName: '文件夹21', fileOthers: '驱蚊器翁', viewTime: '2022-3-16 13:42:13' },
        { folderId: 22, fileName: '文件夹22', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
        { folderId: 23, fileName: '文件夹23', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
        { folderId: 24, fileName: '文件夹24', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
        { folderId: 25, fileName: '文件夹22', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
        { folderId: 26, fileName: '文件夹23', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
        { folderId: 27, fileName: '文件夹24', fileOthers: '', viewTime: '2022-3-16 13:42:13' },
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
    this.change_active_index('3')
    this.getData()
  },
  methods: {
    ...mapActions('FileV', ['change_active_index']),
    async delFileFolder(res, row) {
        // /fileHistory/delMyFileHistory
        console.log(row)
        const res2 = await this.$myRequest({
          url: '/fileHistory/delMyFileHistory',
          method: 'post',
          header:{
            token:localStorage.getItem("token")
          },
          data: { historyId:row},
        })
        if(res2.data.code==200){
          this.$message({
            type: 'success',
            message: '删除记录成功!'
          });
          this.folderList.splice(res, 1)
        }
      // console.log("确认删除", row)
    },
    async getData(){
      const res = await this.$myRequest({
        url: '/fileHistory/getMyFileHistory',
        method: 'post',
        header:{
          token:localStorage.getItem("token")
        },
        data: { },
      })
      if(res.data.code==200){
        this.folderList=res.data.data
      }
    },
    handleEdit(index, row) {
      console.log(index, row);
      this.$router.push({
        name: 'File',
        query: {
          fileId: row
        }
      })
    },
    handleDelete(index, row) {
      // console.log(index, row);
    },
  },
}
