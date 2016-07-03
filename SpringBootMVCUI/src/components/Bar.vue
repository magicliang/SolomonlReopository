<template>
  <div>
    This is Bar
  </div>
  <!-- 嵌套路由 -->
  <a v-link="{ path: '/' }">Go to index</a>
  <div>
    <p>当前路径: {{$route.path}}</p>
    <p>当前路由参数: {{$route.params | json}}</p>
    <p>当前query2: {{$route.query2 | json}}</p>
  </div>
</template>
<style>
</style>
<script>
  export default{
    data () {
      return {
      }
    }
  }
</script>
