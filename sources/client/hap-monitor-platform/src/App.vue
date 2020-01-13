<template>
  <div id="app">
    <transition name="el-fade-in-linear">
      <router-view/>
    </transition>
  </div>
</template>

<style lang="scss">

  * {
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  }

  html, body, #app {
    height: 100%;
    position: relative;
    overflow: hidden;
  }
</style>
