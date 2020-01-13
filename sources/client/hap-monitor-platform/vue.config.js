

module.exports = {
    transpileDependencies: [
        // 'vue-echarts',
        // 'resize-detector'
    ],
    devServer: {
        port: 8081,
        proxy: {
            '/api': {
              target: 'http://192.168.1.128:7510',
              // target: 'https://gc.wetime520.com',
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    },
};
