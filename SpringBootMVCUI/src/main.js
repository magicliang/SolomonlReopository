import Vue from 'vue'
import Foo from './components/Foo'
import Bar from './components/Bar'
import App from './App'
import VueRouter from 'vue-router'

// 只有开这个debug模式，才可以在chrome里配断点?
// Vue.config.debug = true

// have to install Router，即使文档没有这一行
Vue.use(VueRouter)

// routing
var router = new VueRouter()

router.map({
  '/foo': {
    component: Foo
  },
  '/bar': {
    component: Bar
  }
  // 为了避免递归渲染，怎样映射app组件？不要映射，让主页和主component自动占据 http://localhost:8080/#!/，不要做重定向
  // '/': {
  //   component: App
  // }
})

// router.redirect({
//   '*': '/'
// })

/* eslint-disable no-new */
// 使用了router以后，不能这样初始化程序了
// new Vue({
//   el: 'body',
//   components: {App}
// })// ES Lint 不鼓励使用分号 ;
// 只能这样初始化
router.start(App, 'body')
