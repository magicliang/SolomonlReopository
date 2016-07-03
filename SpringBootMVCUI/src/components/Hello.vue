<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <!--可以使用 @wtf-msg="antohernotify" 来用方法响应而不是使用events-->
    <wtf @wtf-msg="antohernotify"  message="this is a message">
      <p slot="one">Hello.vue给予WTF.vue的内容1</p>
      <p slot="two">Hello.vue给予WTF.vue的内容2</p>
    </wtf>
    <br>
    组件在 currentComponent 变化时改变
    <component :is="currentComponent" keep-alive>
      <!-- 非活动组件将被缓存 -->
    </component>
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
        msg: 'Hello World!',
        currentComponent: 'wtf'
      }
    },
    components: {
      // 驼峰命名法的组件会自动被转化成 W3C 命名法，即全小写字母加下划线的语法
      'wtf': WTFComponent
    },
    // 在创建实例时 `events` 选项简单地调用 `$on`
    // 用events来响应事件
    // 空 message不会被响应
    events: {
      'wtf-msg': function (msg) {
        // 事件回调内的 `this` 自动绑定到注册它的实例上
        console.log('wtf-message: ' + msg)
      }
    },
    // 与events矛盾，有它就不会events响应
    methods: {
      antohernotify: function () {
        console.log('another notify! ')
      }
    }

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1 {
    color: #42b983;
  }
</style>
