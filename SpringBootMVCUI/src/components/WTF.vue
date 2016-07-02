<template>
  <div>
    <div>{{msg}}</div>
    <div>{{number}}</div>
    <div>{{message}}</div>
    <input v-model="abc">
    <!--v-bind 的缩写语法-->
    <other-component :efg="abc"></other-component>
    <table>
      <tr id="tr-{{ abc }}" is="other-component"></tr>
      <!--这是dom parser的一个缺点，只有可以内嵌的tag才能放进去，不然就只能is-->
      <!--<tr><other-component v-bind:efg="abc"></other-component>-->
      </tr>
    </table>
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
  // 注意看，这是一个匿名的component，所以应该怎样使用和识别它？注册的时候识别，注意看import语句和components语句
  // 这样的组件有一个优点，就是不需要注册了
  export default{
    props: ['message'],
    // 经过优化的data，总是用一个函数返回值为好
    data () {
      return {
        msg: 'WTF',
        number: (() => { return 123 })()
      }
    },
    components: {
      // 不需要这些
      'other-component': {
        props: ['efg'],
        template: '<div>一个匿名的，局部的component!</div>' +
        '<br>注意看，这里可能会被xss攻击<br>' +
        '<div>原始的html文本{{{efg}}}</div>'
      }
      // HeaderComponent,
    }
  }
</script>
