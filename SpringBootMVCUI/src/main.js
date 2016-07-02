import Vue from 'vue'
import App from './App'

// 只有开这个debug模式，才可以在chrome里配断点
Vue.config.debug = true

/* eslint-disable no-new */
new Vue({
  el: 'body',
  components: {App}

})// ES Lint 不鼓励使用分号 ;
