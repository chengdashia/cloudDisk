<template>
  <div class="music">
    <!-- <div class="SongName">
      <div>{{name}}</div>
      <div>作者:{{song_sing}}</div>
      <div>创建时间:{{song_publish_time}}</div>
    </div> -->
    <div class="music__main">
      <div :class="['music__main__cover', isPlay ? 'active' : '']" @click="play">
        <img :src="song_img" />
      </div>
      <div class="music__main__timeBar">
        <div class="time">
          <span>{{ realMusicTime }}</span>
          <span>{{ totalMusicTime }}</span>
        </div>
        <div class="bar" ref="bar" @click="handClickBar">
          <div class="bar__slid" ref="slid" @click="handClickBar"></div>
        </div>
      </div>
    </div>
    <div class="music__btn">
      <i class="el-icon-refresh" @click="switchMusic"></i>
    </div>
    <div class="music__mask"></div>
    <audio ref="music" :src="url_paly"></audio>
  </div>
</template>


<script>
   import MUSIC from './musics'
   export default MUSIC
</script>



<style lang="less">
  @import './musics.less';
</style>
