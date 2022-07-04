import { mapState, mapActions } from 'vuex'
export default {
  data() {
    return {
      dialogVisible: false,
      fileVisible: false,
      input: "",
      fileTips: '',
      show_file_name:false,
      show_file_remark:false,
      show_folder_name:false,
      show_folder_remark:false,
      file_info_remark:"",
      folder_info_name:"",
      goableIndex:0,
      search: '',
      fileName: '',
      folderList: [],
      file_info_name:"",
      input_file_name:"",
      folder_info_remark:"",
      headers: {
        token: localStorage.getItem("token")
      },
      uploadData: {
        folderId: localStorage.getItem("folderId"),
        fileName: "",
        labelList: "",
        remarks: ""
      },
      options: [{
        interestLabelId: 'HTML',
        labelName: 'HTML'
      }],
      fileInfo:{
        remark:"",
        id:"",
        name:""
      },
      folderInfo:{
        remark:"",
        id:"",
        name:""
      },
      value: [],
      loading: true,
      directoryList: [],
      labels: [],
    }
  },
  computed: {
    folderLists: {
      set(value) {
        // this.fileDatas=value
      },
      get() {
        return this.folderList.filter((val) => {
          return val.folderFileName.indexOf(this.search) !== -1;
        })
      }
    }

  },
  mounted() {
    this.change_active_index('2-1')
    this.getData(1)
  },
  watch: {

  },
  methods: {
    ...mapActions('FileV', ['change_active_index']),
    async getData(res1) {
      this.folderList.splice(0, this.folderList.length);
      const res = await this.$myRequest({
        url: '/folderFileInfo/getFolderFileByFolderId',
        method: 'post',
        header: {
          token: localStorage.getItem("token")
        },
        data: { folderId: localStorage.getItem("folderId") },
      })
      let tempFileList = res.data.data.fileList
      let tempFloderList = res.data.data.folderList
      let tempArray = []
      if (res.data.data.folderList != null) {
        for (let index in tempFloderList) {
          if (res.data.data.folderList[index].folderId == localStorage.getItem("folderId")) {
            let folderObj = { folderId: res.data.data.folderList[index].folderId, folderName: res.data.data.folderList[index].folderName }
            if(res1==1){
              this.directoryList.push(folderObj)
            }

          } else {
            let obj = {
              "folderFileId": res.data.data.folderList[index].folderId,
              "folderFileName": res.data.data.folderList[index].folderName,
              "folderFileTips": res.data.data.folderList[index].folderTips,
              "folderFileTime": res.data.data.folderList[index].folderCreateTime,
              "type": 1,
              "options": [{
                "value": '1',
                "label": '共享'
              }, {
                "value": '0',
                "label": '私密'
              },
              {
                "value": '2',
                "label": '其他'
              }]
            }
            tempArray.push(obj)
          }
        }

      }
      for (let index in res.data.data.fileList) {
        console.log( res.data.data.fileList[index])
        let obj = {
          "folderFileId": res.data.data.fileList[index].fileId,
          "folderFileName": res.data.data.fileList[index].fileName,
          "folderFileTips": res.data.data.fileList[index].fileOthers,
          "folderFileTime": res.data.data.fileList[index].fileUploadTime,
          "type": 2,
          "folderFileStatus": res.data.data.fileList[index].fileStatus == 0 ? "私密" : res.data.data.fileList[index].fileStatus == 1 ? "共享" : "其他",
          "options": [{
            "value": '1',
            "label": '共享'
          }, {
            "value": '0',
            "label": '私密'
          },
          {
            "value": '2',
            "label": '其他'
          }]
        }
        tempArray.push(obj)
      }
      for (let temp in tempArray) {
        console.log(temp)
        this.folderList.push(tempArray[temp])
      }
      this.loading = false
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => { });
    },
    delFileFolder(res, row) {
      if (row.type == 1) {
        this.$confirm('此操作将该文件夹下的所有文件以及文件夹移动到根文件夹, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      } else {
        this.delFile(res, row.folderFileId)
      }

    },
    selectLable(res) {
      console.log(res)
      this.labels = res
    },
    uploadtest(){
      this.fileVisible = true
      this.getLabelsInfo()
    },
    async getLabelsInfo() {
      const res = await this.$myRequest({
        url: '/labelInfo/getLabelInfoRandom20',
        method: 'post',
        header: {
          token: localStorage.getItem("token")
        },
        data: {},
      })
      this.options = res.data.data.records
    },
    async changeStatus(res1, res2) {
      console.log(res2)
      const res = await this.$myRequest({
        url: '/fileInfo/updateFileStatus',
        method: 'post',
        header: {
          token: localStorage.getItem("token")
        },
        data: {
          fileId: res2.folderFileId,
          fileStatus: res1
        },
      })
      if (res.data.code == 200) {
        this.$message({
          type: 'success',
          message: '修改状态成功!'
        });
      }
    },
    async delFile(index, id) {
      const res = await this.$myRequest({
        url: '/fileInfo/delFile',
        method: 'post',
        header: {
          token: localStorage.getItem("token")
        },
        data: { fileId: id },
      })
      if (res.data.code == 200) {
        this.$message({
          type: 'success',
          message: '删除记录成功!'
        });
        this.folderLists.splice(index, 1)
      }
    },
    async addFiler() {
      let that = this
      let str = ""
      for (let i = 0; i < this.labels.length - 1; i++) {
        let temp = this.labels[i] + ","
        str += temp
      }
      str += this.labels[this.labels.length - 1]
      console.log(that.uploadData)
      that.uploadData.labelList = str
      const res = this.$refs.upload.submit();
      console.log("上传", res)
    },
    successUpload(response, file, fileList) {
      if (response.code == 200) {
        this.$notify({
          title: '成功',
          message: '上传文件成功',
          type: 'success'
        });
        this.fileVisible = false
        this.uploadData.fileName = ""
        this.uploadData.labelList = ""
        this.uploadData.remarks = ""
        this.getData(2)
      } else {
        this.$notify({
          title: '警告',
          message: '当前文件无法上传成功，稍后再试',
          type: 'warning'
        });
      }
    },
    async goFolder(res, index) {
      localStorage.setItem("folderId", index)
      console.log(this.uploadData)
      let folderObj = { folderId: res.folderFileId, folderName: res.folderFileName }
      this.directoryList.push(folderObj)
      this.uploadData.folderId = index
      console.log(localStorage.getItem("folderId"))
      this.getData(1)
      console.log(res, index)
    },
    async addFoler() {
      var reg = /[\\/:*?"<>|]/;
      if (!reg.test(this.input) && this.input.length > 0) {
        let obj = { folderFileId: this.folderList.length, folderFileName: this.input, folderFileTips: this.fileTips, folderFileTime: this.dateFormat(), type: 1 }
        const res = await this.$myRequest({
          url: '/folderInfo/createFolder',
          method: 'post',
          header: {
            token: localStorage.getItem("token")
          },
          data: {
            folderName: this.input,
            folderDesc: this.fileTips,
            parentFolderId: localStorage.getItem("folderId")
          },
        })
        this.$notify({
          title: '成功',
          message: '添加成功',
          type: 'success'
        });
        this.input=""
        this.fileTips=""
        this.folderList.push(obj)
        this.dialogVisible = false
      } else {
        this.$notify({
          title: '警告',
          message: '请填写正确的文件夹名称',
          type: 'warning'
        });
      }
    },
    filterTag(value, row) {
      return row.type == value
    },
    handleEdit2(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {

      console.log(index, row);
    },
    progress(event, file, fileList) {
      console.log('上传文件')
      console.log('event', event)
      this.uploadData.fileName = event.name.substring(0, event.name.indexOf("."));
      this.use_file = file[0].raw
      console.log('file', this.use_file)

    },
    handleRemove(file) {
      console.log(file);
    },
    uploadError(err, file, fileList) {
      console.log("上传失败")
      console.log('err', err)
      console.log('file', file)
      console.log('fileList', fileList)
    },
    goFolderList(item, index) {
      if (index == 0) {
        let tempObj={folderId:this.directoryList[0].folderId,folderName:this.directoryList[0].folderName}
        this.directoryList=[]
        this.directoryList.push(tempObj)
        // this.directoryList = this.directoryList.slice(0, 0)
      } else {
        if (index == this.directoryList.length - 1) {
          console.log("1", this.directoryList)
        } else {
          this.directoryList = this.directoryList.slice(0, index + 1)
          console.log("2", this.directoryList)
        }
      }
      console.log("3", this.directoryList)
      localStorage.setItem("folderId", item.folderId)
      this.getData(2)
      console.log(item, index)
    },
    dateFormat() {
      var date = new Date();
      var year = date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },
    handleEdit(index, row) {
      if (index == 2) {
        this.$router.push({
          name: 'File',
          query: {
            fileId: row
          }
        })
      }
    },
    showChangeFileName(){
        console.log("修改文件名")
    },
    showFileNameChange(index,res){
      this.goableIndex=index
      this.file_info_name=res.folderFileName
      this.fileInfo.id=res.folderFileId
      this.show_file_name=true
    },
    showFileRemarkChange(index,res){
      this.goableIndex=index
      this.show_file_remark=true
      this.file_info_remark=res.folderFileTips
      this.fileInfo.id=res.folderFileId
      console.log("res",res)
    },

    showFolderNameChange(index,res){
      this.goableIndex=index
      this.folder_info_name=res.folderFileName
      this.show_folder_name=true
      this.folderInfo.id=res.folderFileId
      console.log("this.folderInfo",this.folderInfo)
    },
    showFolderRemarkChange(index,res){
      this.goableIndex=index
      this.folder_info_remark=res.folderFileTips
      this.show_folder_remark=true
      this.folderInfo.id=res.folderFileId
    },
    confirmChange(res) {
      if (res == 1) {
        this.ChangeFileName()
      } else if (res == 2) {
        this.ChangeFileRemark()
      } else if (res == 3) {
        this.ChangeFolderName()
      } else if (res == 4) {
        this.ChangeFolderRemark()
      }
    },
    infoTips() {
      this.$message({
        message: '请将信息填写完整',
        type: 'warning'
      });
    },
    async ChangeFileName(){
      if (this.fileInfo.name.length == 0) {
        this.infoTips()
      } else {
        const res = await this.$myRequest({
          url: '/fileInfo/updateFileName',
          method: 'post',
          header: {
            token: localStorage.getItem("token")
          },
          data: {
            fileId: this.fileInfo.id,
            fileName: this.fileInfo.name,
          },
        })
        if(res.data.code == 200){
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.folderLists[this.goableIndex].folderFileName=this.fileInfo.name
          console.log(this.folderLists[this.goableIndex])
          this.show_file_name = false
          this.fileInfo.name = ''
        }else{
          this.$message({
            message: '修改遇到问题了',
            type: 'warning'
          });
        }
      }
    },
    async ChangeFileRemark(){
      console.log(this.fileInfo.remark)
      if (this.fileInfo.remark.length == 0) {
        this.infoTips()
      } else {
        const res = await this.$myRequest({
          url: '/fileInfo/updateFileRemark',
          method: 'post',
          header: {
            token: localStorage.getItem("token")
          },
          data: {
            fileId: this.fileInfo.id,
            fileRemark: this.fileInfo.remark,
          },
        })
        if(res.data.code == 200){
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.folderLists[this.goableIndex].folderFileTips=this.fileInfo.remark
          this.show_file_remark = false
          this.fileInfo.remark = ''
        }else{
          this.$message({
            message: '修改遇到问题了',
            type: 'warning'
          });
        }
      }
    },
    async ChangeFolderName(){
      if (this.folderInfo.name.length == 0) {
        this.infoTips()
      } else {
        const res = await this.$myRequest({
          url: '/folderInfo/updateFolderName',
          method: 'post',
          header: {
            token: localStorage.getItem("token")
          },
          data: {
            folderId: this.folderInfo.id,
            folderName: this.folderInfo.name,
          },
        })
        if(res.data.code == 200){
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.folderLists[this.goableIndex].folderFileName=this.folderInfo.name
          this.show_folder_name = false
          this.folderInfo.name = ''
        }else{
          this.$message({
            message: '修改遇到问题了',
            type: 'warning'
          });
        }
      }
    },
    async ChangeFolderRemark(){
      if (this.folderInfo.remark.length == 0) {
        this.infoTips()
      } else {
        const res = await this.$myRequest({
          url: '/folderInfo/updateFolderRemark',
          method: 'post',
          header: {
            token: localStorage.getItem("token")
          },
          data: {
            folderId: this.folderInfo.id,
            folderRemark: this.folderInfo.remark,
          },
        })
        if(res.data.code == 200){
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.folderLists[this.goableIndex].folderFileTips=this.folderInfo.remark
          this.show_folder_remark = false
          this.folderInfo.remark = ''
        }else{
          this.$message({
            message: '修改遇到问题了',
            type: 'warning'
          });
        }
      }
    }
  },
}
