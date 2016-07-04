<!--这种vue格式，真的只有webpack或者Browserify这样的转换器可以使用-->
<template>
  <div id="app">
    <!--src下使用使用相对路径./开头-->
    <img class="logo" src="./assets/logo.png">
    <!--static 文件夹就要用这种绝对路径-->
    <!--<img class="logo123" src="/static/logo123.png">-->
    <!--自定义的组件就可以拿来当标签用-->
    <hello></hello>

    <p>
      <!-- 使用指令 v-link 进行导航。注意，如果后面有动态段的话，也可能什么都没有显示出来吗，因为缺少动态段 -->
      <a v-link="{ path: '/foo' }">Go to Foo</a>
      <a v-link="{ path: '/bar' }">Go to Bar</a>
      <a v-link="{ name: 'xxx', params: { id: 123 } }">Go to XXX</a>
    </p>
    <!-- 路由外链 这里相当于ember的outlet -->
    <router-view></router-view>
  </div>
</template>

<script>
  import Hello from './components/Hello'

  export default {
    components: {
      Hello
    }
  }
</script>

<style>
  html {
    height: 100%;
  }

  body {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }

  #app {
    color: #2c3e50;
    margin-top: -100px;
    max-width: 600px;
    font-family: Source Sans Pro, Helvetica, sans-serif;
    text-align: center;
  }

  #app a {
    color: #42b983;
    text-decoration: none;
  }

  .logo {
    width: 100px;
    height: 100px
  }
</style>
