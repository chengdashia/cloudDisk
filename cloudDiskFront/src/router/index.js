import Vue from 'vue'
import axios from 'axios'
import elementUI from 'element-ui';
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Home from '@/pages/home/home'
import Plaza from '@/pages/home/plaza/plaza'
import Superior from '@/pages/home/superior/superior'
import VuexUse from '@/pages/VuexUse/demo'
import Login from '@/pages/login/login'
import register from '@/pages/login/register/register'
import Qregister from "../pages/login/Qregister/Qregister";
import loginLogin from '@/pages/login/login_login/loginLogin'
import verLogin from '@/pages/login/login_login/verLogin'
import mailVerLogin from '@/pages/login/login_login/mailVerLogin'
import File from '@/pages/file/file'
import Person from '@/pages/person/person'
import PersonInfo from '@/pages/person/components/personInfo/personInfo'
import ChangeInfo from '@/pages/person/components/changeInfo/changeInfo'
import PersonFiles from '@/pages/person/components/personFiles/personFile'
import CollectFiles from '@/pages/person/components/collectFiles/collectFile'
import DelFile from '@/pages/person/components/delFiles/delFile'
import HistoryFile from '@/pages/person/components/historyFiles/historyFile'
import ShareFiles from '@/pages/person/components/shareFiles/shareFile'
import wordTest from '@/pages/word/wordTest'
// import mail from '@/pages/login/mail/mail'
import slider from "../pages/login/slider/slider";
// import gitLogin from "../pages/login/login_login/gitLogin"
// import wxLogin from "../pages/login/login_login/wxLogin"
import PersonV from '../store/person'
import test from "../pages/file/components/music/test";
// import oauthCallback from "../pages/login/login_login/oauthCallback";
Vue.use(Router)

//解决vue路由重复导航错误
//获取原型对象上的push函数
const originalPush = Router.prototype.push
//修改原型对象中的push方法
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
      children: [
        {
          name: 'plaza',
          path: 'plaza',
          component: Plaza,
          meta: { title: '文件广场', needLogin: false },
        },
        {
          name: 'superior',
          path: 'superior',
          component: Superior,
          meta: { title: '优质文件', needLogin: false },
        },
      ]
    },
    {
      path: '/word',
      name: 'wordTest',
      component: wordTest,
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      meta: {
        title: "登陆",
        needLogin: false
      },
      children: [
        {
          name: 'register',
          path: 'register',
          component: register,
          meta: {
            title: '手机注册',
            needLogin: false
          },
        },
        {
          name: 'Qregister',
          path: 'Qregister',
          component: Qregister,
          meta: {
            title: '邮箱注册',
            needLogin: false
          },
        },
        {
          name: 'loginLogin',
          path: 'login',
          component: loginLogin,
          meta: {
            title: '密码登陆',
            needLogin: false
          },
        },
        {
          name: 'verLogin',
          path: 'verLogin',
          component: verLogin,
          meta: {
            title: '手机验证码登陆',
            needLogin: false
          },
        },
        {
          name:'mailVerLogin',
          path: 'mailVerLogin',
          component: mailVerLogin,
          meta:{
            title: '邮箱验证码登录',
            needLogin: false
          },
        },
        {
          name:'slider',
          path: 'slider',
          component: slider,
          meta:{
            title: '验证滑块',
            needLogin: false
          },
        },
        // {
        //   name: 'wxLogin',
        //   path: 'wxLogin',
        //   component: wxLogin,
        //   meta: {
        //     title: '微信授权登陆',
        //     needLogin: false,
        //   }
        // },
        // {
        //   name: 'gitLogin',
        //   path: 'gitLogin',
        //   component: gitLogin,
        //   meta: {
        //     title: 'gitee授权登录',
        //     needLogin: false,
        //   }
        // },
      ]
    },
    {
      path: '/file',
      name: 'File',
      component: File,
      meta: {
        title: "文件详情",
        needLogin: false
      },
    },
    {
      path: '/person',
      name: 'Person',
      component: Person,
      meta: {
        title: "个人中心",
        needLogin: true
      },
      children: [
        {
          name: 'personInfo',
          path: 'personInfo',
          component: PersonInfo,
          meta: { title: '个人信息', needLogin: true },
        },
        {
          name: 'changeInfo',
          path: 'changeInfo',
          component: ChangeInfo,
          meta: { title: '信息修改', needLogin: true },
        },
        {
          name: 'personFiles',
          path: 'personFiles',
          component: PersonFiles,
          meta: { title: '个人文件', needLogin: true },
        },
        {
          name: 'collectFile',
          path: 'collectFile',
          component: CollectFiles,
          meta: { title: '收藏文件', needLogin: true },
        },
        {
          name: 'shareFile',
          path: 'shareFile',
          component: ShareFiles,
          meta: { title: '共享文件', needLogin: true },
        },
        {
          name: 'historyFile',
          path: 'historyFile',
          component: HistoryFile,
          meta: { title: '浏览历史', needLogin: true },
        },
        {
          name: 'delFile',
          path: 'delFile',
          component: DelFile,
          meta: { title: '回收站', needLogin: true },
        }
      ]
    }
  ]
})
router.beforeEach((to, from, next) => {
  next()
      // axios({
      //   method: 'get',
      //   url: 'http://localhost:3040/data',
      //   params: {
      //     num: 10
      //   }
      // }).then((res) => {
      //   console.log('数据：', res)
      // })
      // .catch((res)=>{
      //   console.log('错误请求',res)
      // })
  // console.log('前置路由守卫',to,from)
  if(to.meta.needLogin &&localStorage.getItem('token').length===0){ //判断是否需要鉴权
    console.log("需要授权")
    // console.log()
    // console.log(PersonV.state)
    // next({
    //   name: 'loginLogin',
    // })
  	// if(localStorage.getItem('school')==='atguigu'){
  	// 	next()
  	// }else{
  	// 	alert('学校名不对，无权限查看！')
  	// }
  }else{
  	next()
  }
})
//全局后置路由守卫————初始化的时候被调用、每次路由切换之后被调用
router.afterEach((to, from) => {
  // console.log('后置路由守卫',to,from)
  document.title = to.meta.title || '三千网盘'
})

export default router
