<template>

  <span >
    <el-cascader
            clearable
            :size="size"
            :placeholder="placeholder"
            @change="handleArtCategoryCodeChange"
            :options="options"
            v-model="defaultArtCategoryCode"
            :value="defaultArtCategoryCode"
            change-on-select
    ></el-cascader>
    <input  type="text" :value="value" style='display:none;'>
  </span>
</template>

<script>

  export default {
    name:"ArtCategoryCascader",
    components: {},
    props:{
      rootCode:{
        type:String,
        default:function () {
          return '';
        }
      },
      size:{
        type:String,
        default:''
      },
      value: {
        type: String,
        default:''
      },
      placeholder: {
        type: String,
        default:"选择所属模块"
      },

    },
    watch:{
      value(val) {
        if(!val){
          this.defaultArtCategoryCode = []
        }
      },defaultArtCategoryCode(e) {
        if(e[e.length - 1]){
          this.$emit('input', e[e.length - 1]);
        }else{
          this.$emit('input', "");
        }
      }
    },

    data () {
      return {
        options: [],
        selectValue:[],
        defaultArtCategoryCode:[],
      }
    },
    computed: {},
    methods: {
      handleArtCategoryCodeChange:function (e) {
        if(e[e.length - 1]){
          this.$emit('input', e[e.length - 1]);
        }else{
          this.$emit('input', "");
        }

      },

      loadArtCategoryTree:function () {
        let vm = this;
        vm.$http.post(vm.$api.scafeArtCategory.tree,vm.$qs.stringify({rootCode:vm.rootCode})).then(function (resp) {
          vm.options = vm.artCategoryTreeToArtCategoryOption(resp.data.data.tree).children ;

          let getDefaultArtCategoryCodeValue = function (code, options, result) {


            for (let e in options) {

              if (options[e].value === code) {
                result.push(options[e].value);
                return true ;

              } else if (options[e].children && options[e].children.length > 0) {
                if(getDefaultArtCategoryCodeValue(code, options[e].children, result)){
                  result.push(options[e].value);
                  return true ;
                }
              }
            }


            return false ;

          };

          if(vm.value){
            let result = [];
            getDefaultArtCategoryCodeValue(vm.value, vm.options,result);
            vm.defaultArtCategoryCode = result.reverse();
          }

          console.log(vm.defaultArtCategoryCode);



        });
      },
      artCategoryTreeToArtCategoryOption:function (artCategoryTree) {
        let vm = this ;
        let artCategoryOption = {};


        if(artCategoryTree.code){
          artCategoryOption.artCategoryOption = artCategoryTree.name;
          artCategoryOption.value = artCategoryTree.code;
          artCategoryOption.flag = artCategoryTree.flag;
          artCategoryOption.label = artCategoryTree.name;
        }

        if(artCategoryTree.children !== null && artCategoryTree.children.length > 0){
          artCategoryOption.children = [] ;
          artCategoryTree.children.forEach(function (e) {
            artCategoryOption.children.push(vm.artCategoryTreeToArtCategoryOption(e));
          });
        }
        return artCategoryOption ;
      }
    },
    created: function () {
      this.loadArtCategoryTree();

    }
  }
</script>

<style ref="stylesheet/scss" lang="scss">




</style>
