
import { mapState, mapGetters, mapMutations, mapActions } from 'vuex'
import { Debounce, Throttle } from "../../../public/Debounce";
export default {
  data() {
    return {
      input: '',
      page:1,
      /*
      **
          fileAvatar: "3a3208a9-eaf2-4781-ad92-17b1be1b3841"
          fileClickNums: "26487"
          fileDownloadNums: "35223"
          fileFolderId: "410c1f7ba72c4d8a8702bd8e8f919f9b"
          fileInfoId: "d021496f-03a6-4d2d-bdac-a9b49d5e3f81"
          fileName: "087b750aa8ec41228ea75380cc7dbe0a"
          fileOthers: "aea3e8c8434c468c8200a07d6a351971"
          fileStatus: 1
          fileType: 1
          fileUploadTime: "2022-04-10 10:27:11"
          userName: "17337995232f5e52"

      */
      fileData: [
        { fileId: 11, fileName: "三千数据10", tips: "这是1个数据", uploadTime: '2022年3月12日17:03:17', clickCount: 200, fileUploader: '江河三千里', avatar: 'http://ljy0427.online/favicon.ico' },
        { fileId: 12, fileName: "三千数据11", tips: "这是2个数据", uploadTime: '2022年3月12日17:03:17', clickCount: 200, fileUploader: '江河三千里江河三千里江河三千里', avatar: 'http://ljy0427.online/favicon.ico' },
        { fileId: 13, fileName: "三千数据12", tips: "这是3个数据", uploadTime: '2022年3月12日17:03:17', clickCount: 200, fileUploader: '江河三千里', avatar: 'http://ljy0427.online/favicon.ico' },
        { fileId: 14, fileName: "三千数据13", tips: "这是4个数据", uploadTime: '2022年3月12日17:03:17', clickCount: 200, fileUploader: '江河三千里', avatar: 'http://ljy0427.online/favicon.ico' },
        { fileId: 15, fileName: "三千数据27", tips: "这是5个数据", uploadTime: '2022年3月12日17:03:17', clickCount: 200, fileUploader: '江河三千里', avatar: 'http://ljy0427.online/favicon.ico' },
        { fileId: 16, fileName: "三千数据28", tips: "这是6个数据", uploadTime: '2022年3月12日17:03:17', clickCount: 200, fileUploader: '江河三千里', avatar: 'http://ljy0427.online/favicon.ico' },
        { fileId: 17, fileName: "三千数据40", tips: "这是7个数据", uploadTime: '2022年3月12日17:03:17', clickCount: 200, fileUploader: '江河三千里', avatar: 'http://ljy0427.online/favicon.ico' },
        { fileId: 18, fileName: "三千数据90", tips: "这是8个数据", uploadTime: '2022年3月12日17:03:17', clickCount: 200, fileUploader: '江河三千里', avatar: 'http://ljy0427.online/favicon.ico' },
        { fileId: 19, fileName: "三千数据44", tips: "这是9个数据", uploadTime: '2022年3月12日17:03:17', clickCount: 200, fileUploader: '江河三千里', avatar: 'http://ljy0427.online/favicon.ico' },
        { fileId: 20, fileName: "三千数据22", tips: "这是10个数据", uploadTime: '2022年3月12日17:03:17', clickCount: 200, fileUploader: '江河三千里', avatar: 'http://ljy0427.online/favicon.ico' },
      ],
      timer: null
    }

  },
  computed: {
    ...mapState('PersonV',['name', 'address', 'user_tel']),
    fileDatas: {
      set(value) {
        // this.fileDatas=value
      },
      get() {
        return this.fileData.filter((val) => {
          return val.fileName.indexOf(this.input) !== -1;
        })
      }
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    async getData() {
      const res = await this.$myRequest({
        url: '/fileInfo/getFileInfoListByPage',
        method: 'post',
        header:{
          token:""
        },
        data: { Page:this.page },
      })
      if (res.data.code == 200) {
        this.fileData = res.data.data.records
      } else {
      }
      console.log(this.fileData)
    },
    // scrollEvent(e) {
    //   let that = this

    //   // if (e.target.scrollTop + e.target.offsetHeight >= e.target.scrollHeight) {
    //   //   console.log("到底了")
    //   //   //防抖节流
    //   //   clearInterval(this.timer)
    //   //   this.timer = setTimeout(() => {
    //   //     if (that.input.length == 0) {

    //   //       that.getMore()
    //   //     } else {
    //   //       that.$message('请先清空搜索框');
    //   //     }
    //   //   }, 500)

    //   // }
    // },

    async scrollEvent(e) {
      let that = this
      // document.documentElement.scrollTop+ document.documentElement.clientHeight >= document.body.scrollHeight
      if (e.target.scrollTop + e.target.offsetHeight >= e.target.scrollHeight) {
        //防抖节流
        // Debounce(that.getMore())
          clearInterval(this.timer)
          this.timer = setTimeout(() => {
            that.getMore()
          }, 1000)

      }

    },
    async getMore() {
      console.log(this.fileData)
      this.page+=1
      const res = await this.$myRequest({
        url: '/fileInfo/getFileInfoListByPage',
        method: 'post',
        header:{
          // token:""
        },
        data: {
          Page:this.page
        },
      })
      console.log("res.data.data.records.length",res.data.data.records.length)
      if (res.data.code == 200) {
        this.fileData = this.fileData.concat(res.data.data.records)
        this.fileDatas = this.fileDatas.concat(res.data.data.records)
        // this.fileDatas.push(res.data.fileData)
      } else {
        console.log("请求失败")
      }

    },
    goFileInfo(res) {
      // this.$router.push({
      //   name: 'File',
      //   params:{
      //     id:'0',
      //     title:"m.title"
      //   }
      // })
      this.$router.push({
        name: 'File',
        query: {
          fileId: res
        }
      })
    }
  },

}
