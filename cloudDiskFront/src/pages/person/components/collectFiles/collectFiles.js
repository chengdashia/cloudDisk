import { mapState, mapActions } from 'vuex'
export default {
  data() {
    return {
      search: '',
      folderList: [
        { folderId: 1, fileName: '文件夹1', fileOthers: '文件夹介绍', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 2, fileName: '文件夹2', fileOthers: '这是什么', collectionTime: '2022-3-16 13:42:13',  },
        { folderId: 3, fileName: '文件夹3', fileOthers: '这啥也不是', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 4, fileName: '文件夹4', fileOthers: '12313', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 5, fileName: '文件夹5', fileOthers: '222', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 6, fileName: '文件夹6', fileOthers: '11', collectionTime: '2022-3-16 13:42:13',  },
        { folderId: 7, fileName: '文件夹7', fileOthers: '', collectionTime: '2022-3-16 13:42:13',  },
        { folderId: 8, fileName: '文件夹8', fileOthers: '驱蚊器翁', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 9, fileName: '文件夹9', fileOthers: '刚刚过', collectionTime: '2022-3-16 13:42:13',  },
        { folderId: 10, fileName: '文件夹10', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 11, fileName: '文件夹11', fileOthers: '44445', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 12, fileName: '文件夹12', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 13, fileName: '文件夹13', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 14, fileName: '文件夹14', fileOthers: '22123', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 15, fileName: '文件夹15', fileOthers: '好几个', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 16, fileName: '文件夹16', fileOthers: '发生', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 17, fileName: '文件夹17', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 18, fileName: '文件夹18', fileOthers: '123123', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 19, fileName: '文件夹19', fileOthers: '213', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 20, fileName: '文件夹20', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 21, fileName: '文件夹21', fileOthers: '驱蚊器翁', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 22, fileName: '文件夹22', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 23, fileName: '文件夹23', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 24, fileName: '文件夹24', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 25, fileName: '文件夹22', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 26, fileName: '文件夹23', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
        { folderId: 27, fileName: '文件夹24', fileOthers: '', collectionTime: '2022-3-16 13:42:13' },
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
    this.change_active_index('2-2')
    this.getData()
  },
  methods: {
    ...mapActions('FileV', ['change_active_index']),
    async getData(){
      const res = await this.$myRequest({
        url: '/fileCollection/getMyCollection',
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
    async delFileFolder(res, row) {
      const res2 = await this.$myRequest({
        url: '/fileCollection/delMyFileCollection',
        method: 'post',
        header:{
          token:localStorage.getItem("token")
        },
        data: { fileId:row},
      })
      if(res2.data.code==200){
        this.$message({
          type: 'success',
          message: '取消收藏成功!'
        });
        this.folderList.splice(res, 1)
      }
    },
    handleEdit(index, row) {
      this.$router.push({
        name: 'File',
        query: {
          fileId: row
        }
      })
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
  },
}
