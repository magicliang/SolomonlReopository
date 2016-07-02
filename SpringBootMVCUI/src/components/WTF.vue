<template>
  <div>
    <div>this is WTF body</div>
    <div>{{msg}}</div>
    <div>{{number}}</div>
  </div>
</template>
<style scoped>
  /*这是一个css注释的例子*/
  /*body{*/
  /*background-color:#ff0000;*/
  /*}*/
</style>
<script>
  // 注意看，这是一个匿名的component，所以应该怎样使用和识别它？
  export default{
    data () {
      return {
        msg: 'WTF',
        number: (() = > {return 123}
    )
      ()
    }
    };,
    components: {
      // 不需要这些
      // 'other-component':OtherComponent,
      // HeaderComponent,
    }
  }
</script>
