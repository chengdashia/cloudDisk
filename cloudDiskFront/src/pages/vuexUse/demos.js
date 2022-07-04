import {mapState,mapGetters} from 'vuex'
export default{
  name:"VuexUse",
  data() {
    return {
      msg:"asdasd",
      num:0
    }
  },

  computed:{
    // 对象式写法
    // ...mapState({useName:"name",useAddress:"address"})
    // 这里的对象不能使用简写形式，如果使用简写形式，那么将会是userName:userName,但是没有userName这个变量
    // ...是对象的结构式赋值
    // 数组式写法
    // 如果使用数组是写法，那么就需要这里的变量和store中的变量名称一致
    ...mapState(['name','address']),
    ...mapGetters(['useData'])
  },
  methods: {
    addNum(){
      this.$store.dispatch('add',parseInt(this.num))
    },
    change(){
      this.$router.push({
					path:'login/login',
				})
    }
  }
}
