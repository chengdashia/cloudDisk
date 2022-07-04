

export default{
  data() {
    return {
      fileData:[],
          timer:null,
    }

  },
  mounted() {
    this.getData()
  },
  methods: {
    async getData(){
      const res = await this.$myRequest({
        url: '/fileCollection/getMyCollection',
        method: 'post',
        header:{
          token:localStorage.getItem("token")
        },
        data: { },
      })
      if (res.data.code == 200) {
        this.fileData = res.data.records
        this.fileData.sort(this.sortBy("fileClickNums"))
      } else {
        this.$message({
          type: 'warning',
          message: '网络错误，稍候再试！!'
        });
      }
    },
    sortBy(i) {
      return function(a,b) {
        return b[i] - a[i] //  a[i] - b[i]为正序，倒叙为  b[i] - a[i]
      }
    },

    goFileInfo(res){
      this.$router.push({
        name: 'File',
        query: {
          fileId: res
        }
      })
      console.log("页面跳转")
    }
  },
}
