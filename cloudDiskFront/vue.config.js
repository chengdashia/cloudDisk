module.exports = {
  css: {
      loaderOptions: {
          css: {
              // options here will be passed to css-loader
          },
          "postcss": {
              "plugins": {
                  "autoprefixer": {},
                  "postcss-pxtorem": {
                      "rootValue": 192,
                      "propList":["*"]
                  }
              }
          }
      }
  }
}
