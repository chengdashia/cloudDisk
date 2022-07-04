import {mapState,mapGetters} from 'vuex'
export default{
  data() {
    return {
      src:'../../assets/font-logo.png',
      tips:"登陆"
    }
  },
  computed:{},
  mounted() {
    // console.log(window.location.href)
  },
  watch:{
    $route(to,from){
      if(to.name=="register"){
        this.tips="注册"
      }else{
        this.tips="登陆"
      }
    }
  },
  	beforeDestroy() {
			console.log('Home组件即将被销毁了')
		}
}
