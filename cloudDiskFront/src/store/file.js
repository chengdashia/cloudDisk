const state={
  active_index: '222',
  download_url:""
}

export default {
  namespaced: true,
  state,
  actions: {
    change_active_index(context, value) {
      state.active_index = value
    },
    change_download_url(context, value) {
      console.log("修改文件路劲",value)
      state.download_url = value
    }
  },
  mutations: {
    CHANGETOKEN(data, value) {
      console.log("")
      state.token = value
    }
  },

  getters: {
  }
}
