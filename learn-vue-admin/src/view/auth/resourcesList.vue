<template>

</template>

<script>
export default {
  name: "resourcesList",
  data () {
    return {
      value: '',
      data: [{
        title: 'parent 1',
        expand: true,
        children: [
          {
            title: 'parent 1-1',
            expand: true,
            children: [
              {
                title: 'leaf 1-1-1'
              },
              {
                title: 'leaf 1-1-2'
              }
            ]
          },
          {
            title: 'parent 1-2',
            expand: true,
            children: [
              {
                title: 'leaf 1-2-1'
              },
              {
                title: 'leaf 1-2-1'
              }
            ]
          }
        ]
      }]
    }
  },
  methods:{
    selectNode(treeData){
      this.model9 =treeData.title
    }
  }
}
</script>

<style scoped>

</style>
