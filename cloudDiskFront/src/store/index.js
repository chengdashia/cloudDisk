import Vue from 'vue'
import Vuex from 'vuex'
import PersonV from './person'
import FileV from './file'
Vue.use(Vuex)
// 准备state——用于存储数据

// 准备actions——用于响应组件中的动作
// actions只管逻辑

// 准备mutations——用于操作数据（state）
// mutations只管操作

// 创建并暴露vuex.store
export default new Vuex.Store({
  modules:{
    PersonV,
    FileV
  }
})
