const userUrl='http://localhost:8888'

export default function myRequest(options) {
  return new Promise((resolve, reject) => {
    const request= new this.$axios({
      url: userUrl + options.url,
      method: options.method || "post",
      headers:{
      'Content-Type': 'application/x-www-form-urlencoded',
      "token":options.header.token || ""
    },
      params:options.data
    })
    request.then((res) => {
      console.log(res)
      resolve(res)
    })
    request.catch((error)=>{
      this.$message({
        message: '请求超时',
        type: 'error'
      });
      resolve(error)
    })
  })
}
