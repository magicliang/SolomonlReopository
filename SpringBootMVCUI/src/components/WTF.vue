<template>
  <div>
    <div>this is WTF body</div>
    <div>{{msg}}</div>
    <div>{{number}}</div>
    <h5><other-component>中间这段根本没意义</other-component></h5>
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
  export default{
    data () {
      return {
        msg: 'WTF',
        number: (() => { return 123 })()
      }
    },
    components: {
      // 不需要这些
      'other-component': {
        template: '<div>一个匿名的，局部的component!</div>'
      }
      // HeaderComponent,
    }
  }
</script>
