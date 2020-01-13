<template>
    <span>
       {{text}}
    </span>
</template>

<script>
    export default {
        name: "SupplierText",
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

                let e =  this.suppliers.find((e)=>{
                      return e.code === this.selectValue
                 });

                 return e ? e.name:'';
;
             }
        },
        data(){
            return {
                selectValue:'',
                suppliers:[],
            }
        },
        created() {

            let vm = this;
            vm.$http.post(vm.$api.supplier.list,vm.$qs.stringify({onPage:false})).then((resp)=>{
                    vm.suppliers = resp.data.data.suppliers;
                    vm.selectValue = vm.value;

            });

        }
    }
</script>

<style scoped>

</style>
