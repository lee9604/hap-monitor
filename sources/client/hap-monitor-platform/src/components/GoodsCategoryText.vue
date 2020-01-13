<template>
    <span>
       {{text}}
    </span>
</template>

<script>
    export default {
        name: "GoodsCategoryText",
        props:{
            value: {
                type: String,
                default:function () {
                    return '' ;
                }
            }
        },
        watch:{

        },
        computed:{
             text(){

                let e =  this.goodsCategorys.find((e)=>{
                      return e.code === this.selectValue
                 });

                 return e ? e.name:'';
;
             }
        },
        data(){
            return {
                selectValue:'',
                goodsCategorys:[],
            }
        },
        created() {

            let vm = this;
            vm.$http.post(vm.$api.goodsCategory.list,vm.$qs.stringify({onPage:false})).then((resp)=>{
                    vm.goodsCategorys = resp.data.data.goodsCategorys;
                    vm.selectValue = vm.value;

            });

        }
    }
</script>

<style scoped>

</style>
