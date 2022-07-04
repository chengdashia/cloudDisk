// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import VideoPlayer from 'vue-video-player'
require('video.js/dist/video-js.css')
require('vue-video-player/src/custom-theme.css')
axios.defaults.baseURL = '/api'
Vue.use(VideoPlayer)
// import 'lib-flexible'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import myRequest from './public/request'
Vue.use(ElementUI)
import VueCropper from 'vue-cropper'
Vue.use(VueCropper)
// 引入store
import store from './store'
Vue.config.productionTip = false
axios.defaults.baseURL = '/api'
Vue.prototype.$axios=axios
Vue.prototype.$myRequest=myRequest
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
