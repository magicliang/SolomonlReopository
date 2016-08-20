<template id="wtf-template">
  最好还是提供一个根元素来做锚定，不然就成为fragment instance
  <div>
    <div>{{msg}}</div>
    <slot name="one">
      slot1
    </slot>
    <slot name="two">
      slot2
    </slot>
    <slot>
      如果没有分发内容则显示我。这里有点类似ember里的yield，但ember的yield没有默认的placeholder。
    </slot>
    <div>{{number}}</div>
    <div>{{message}}</div>
    <br>
    <input v-model="abc">
    <br>
    <!--v-bind 的缩写语法，双向绑定-->
    <other-component :efg.sync="abc"></other-component>
    <br>
    <table>
      <tr id="tr-{{ abc }}" is="other-component"></tr>
      <!--这是dom parser的一个缺点，只有可以内嵌的tag才能放进去，不然就只能is-->
      <!--<tr><other-component v-bind:efg="abc"></other-component>-->
      </tr>
    </table>
    <br>
    b is: {{b}}
    <br>
    <button @click="notify">Dispatch Event</button>
    <!--<other-component>中间这段根本没意义</other-component>-->
  </div>
</template>
<style lang="css" scoped>
  /*这是一个css注释的例子*/
  /*body{*/
  /*background-color:#ff0000;*/
  /*}*/
</style>
<script>
  import Vue from 'vue'

  // 注意看，这是一个匿名的component，所以应该怎样使用和识别它？注册的时候识别，注意看import语句和components语句
  // 这样的组件有一个优点，就是不需要注册了
  let WTFComponent123 = Vue.extend({
    template: '#wtf-template',
    props: ['message'],
    // 经过优化的data，总是用一个函数返回值为好
    data () {
      return {
        abc: 'nihao',
        a: 1, // a 不能放在外面
        msg: 'WTF',
        // 箭头函数是没有this的，所以它的this就是词法的this，不会再有binding的问题
        number: (() => { return 123 })()
      }
    },
    computed: {
      // 一个计算属性的 getter
      b () {
        // `this` 指向 vm 实例
        return this.a + 1
      }
    },
    components: {
      // 不需要这些
      'other-component': {
        name: 'other-component',
        // 只有props才能暴露出去给外部组件，也只有它可以用sync
        props: ['efg'],
        // 一个vue文件里，只能有一个template标签
        template: '<div>一个匿名的，局部的component!</div>' +
        '<br>注意看，这里可能会被xss攻击<br>' +
        '<div>原始的html文本，双向绑定<input v-model="efg"> {{{efg | capitalize }}}</div>'
      }
      // HeaderComponent,
    },
    // 用methods来相应action
    methods: {
      notify: function () {
        console.log('notify ')
        if (this.abc.trim()) {
          this.$dispatch('wtf-msg', this.abc, 'extra msg')
          this.abc = ''
        }
      }
    }
  })

//  只有 vue实例才能注册这种$watch函数，但我们现在只是声明了一个component，甚至都没有全局注册过。所以不能用
//  WTFComponent123.$watch('abc', function () {
//    console.log('This abc changed: ' + this.abc)
//  })
  export default WTFComponent123
</script>
