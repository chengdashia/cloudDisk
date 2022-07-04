<template>
  <div id="app">
    <transition name="test" mode="out-in">
    <router-view />
    </transition>
  </div>
</template>

<script>
export default {
  name: "App",
  methods: {

  },
};
</script>

<style lang="less">
  @import "./public/public.less";
  body{
  margin: 0 auto;
}
/*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
::-webkit-scrollbar {
    width: 8px;
    height: 6px
}
/*定义滚动条轨道*/
::-webkit-scrollbar-track {
    background-color: transparent;
    -webkit-border-radius: 2em;
    -moz-border-radius: 2em;
    border-radius: 2em
}
/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
    background-color: #607d8b;
    background-image: -webkit-linear-gradient(45deg,rgba(255,255,255,.4) 100%,transparent 100%,transparent 50%,rgba(255,255,255,.4) 50%,rgba(255,255,255,.4) 75%,transparent 75%,transparent);
    -webkit-border-radius: 2em;
    -moz-border-radius: 2em;
    border-radius: 2em
}
</style>
