import { mapState, mapActions } from 'vuex'
export default {
  props: ['musicUrl'],
  data() {
    return {
      myMsg: this.msg,
      isPlay: false,
      song_sing: "song_sing",
      name: "",
      realMusicTime: "00:00",
      totalMusicTime: "00:00",
      url_use: "",
      url_paly: "http://hadoop1:9864/webhdfs/v1/test/qqqg.mp3?op=OPEN&namenoderpcaddress=hadoop1:8020&offset=0" ,
      audioSrc: "",
      song_id: "",
      Create_time: "",
      song_hot_comments: "",
      song_publish_time: "",
      song_img: "http://ljy0427.online/favicon.ico",
      // song_img: "http://hadoop3:9864/webhdfs/v1/test/ba.png?op=OPEN&namenoderpcaddress=hadoop1:8020&offset=0",
      play_index: false
    };
  },
  watch: {
    msg(res) {
      console.dir(res)
    },
    totalMusicTime(res) {
      if (!(this.$data.totalMusicTime == "00:00")) {
        this.$data.play_index = true
      }
      this.$emit('getFileUrl',this.url_paly);
      console.log(this.$data.play_index)
    }
  },
  created() { },
  mounted() {
    try {
      this.updateMusic()
      // song_sing:"song_sing",
    } catch (error) {
      console.error(error);
    }
    console.log(this.$data.myMsg)
    console.log(this.$data.song_id)
    this.watchMusicTime();
    this.getMusic();
    this.change_download_url(this.url_paly)
  },
  methods: {
    ...mapActions('FileV', ['change_download_url']),
    updateMusic(){
      this.$data.name = this.$data.myMsg.song_name
      this.$data.song_sing = this.$data.myMsg.song_sing
      this.$data.song_id = this.$data.myMsg.song_id
      this.$data.song_publish_time = this.$data.myMsg.song_publish_time
    },
    async getMusic() {
      // const res = await this.$myRuquest({
      //   url: '/api/song/getSongInfoById/',
      //   method: "POST",
      //   data: {
      //     "id": this.$data.song_id
      //   }
      // })
      // this.$data.url_paly = res.url
      // this.$data.song_img = res.img
      // console.log(res)
      // console.log(typeof(res.lrc))
      // this.$emit('child-event',res.lrc)
    },
    play() {
      if (this.music.paused) {
        this.playMusic()
      } else {
        this.music.pause();
        this.isPlay = false;
      }
    },

    playMusic(){
      if (this.$data.play_index==true) {
          try {
            this.music.play();
            this.isPlay = true;
          } catch (error) {
            console.log(error)
          }
        }else{
          this.$message({
        message: '当前音乐资源无法加载',
        type: 'warning'
      });
          console.log("无法播放")
        }
    },
    // 处理时间
    handlMusicTime() {
      //用秒数来显示当前播放进度
      let timeDisplay = Math.floor(this.music.currentTime); //获取实时时间
      //分钟
      let minute = parseInt(timeDisplay / 60);
      if (minute < 10) {
        minute = "0" + minute;
      }
      //秒
      let second = Math.round(timeDisplay % 60);
      if (second < 10) {
        second = "0" + second;
      }
      this.realMusicTime = minute + ":" + second;
    },
    // 处理进度条
    handMusicBar() {
      let slid = this.$refs.slid;
      let duration = this.music.duration;
      let x = ((this.music.currentTime / duration) * 100).toFixed(2) + "%";
      slid.style.width = x;
    },
    // 处理点击进度条事件
    handClickBar(e) {
      const barTotalWidth = this.bar.offsetWidth; // bar 总宽度
      const rect = e.target.getBoundingClientRect(); // 元素右边距离页面边距的距离 返回上下左右
      let length = e.pageX - rect.left;
      this.music.currentTime = (length / barTotalWidth) * this.music.duration; // 计算播放时间 位置百分比*总时间
      this.$nextTick(() => {
        this.music.play();
        this.isPlay = true;
      });
    },
    // 切换歌曲
    switchMusic() {
      this.isPlay = false;
      this.audioSrc = this.$data.url_use;
      console.log("重新播放")
      try {
        this.playMusic()
        this.music.currentTime=0
      } catch (error) {
        console.log(error)
      }
    },
    watchMusicTime() {
      this.music = this.$refs.music;
      this.bar = this.$refs.bar;
      console.log(this)
      console.log(this.music)
      this.music.addEventListener(
        "timeupdate",
        () => {
          this.handlMusicTime();
          this.$nextTick(() => {
            this.handMusicBar();
          });
        },
        false
      );
      // 播放完毕
      this.music.addEventListener("ended", () => {
        this.switchMusic(); // 自动播放
      });

      // 捕获音频文件已准备完毕
      // 当媒体文件可以播放的时候会触发oncanplay事件,也可以用oncanplay
      this.music.oncanplaythrough = () => {
        let time = this.music.duration;
        //分钟
        let minutes = parseInt(time / 60);
        if (minutes < 10) {
          minutes = "0" + minutes;
        }
        //秒
        let seconds = Math.round(time % 60);
        if (seconds < 10) {
          seconds = "0" + seconds;
        }
        this.totalMusicTime = minutes + ":" + seconds;
      };
    }
  },
};

