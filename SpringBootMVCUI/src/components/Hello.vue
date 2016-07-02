<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <wtf></wtf>
  </div>
</template>

<script>
  import WTFComponent from './WTF.vue'
  export default {
    data () {
      return {
        // note: changing this line won't causes changes
        // with hot-reload because the reloaded component
        // preserves its current state and we are modifying
        // its initial state.
        msg: 'Hello World!'
      }
    },
    components: {
      // 驼峰命名法的组件会自动被转化成 W3C 命名法，即全小写字母加下划线的语法
      'wtf': WTFComponent
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1 {
    color: #42b983;
  }
</style>
