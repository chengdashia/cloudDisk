module.exports={
  plugins:{
    autoprefixer:{browsers:'last 5 version'},
    'postcss-px2rem-exclude':{
      remUnit:192,
      // exclude:/node_modules
    }
  }
}
