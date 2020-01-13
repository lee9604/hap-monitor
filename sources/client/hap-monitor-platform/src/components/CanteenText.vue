<template>
    <span>
       {{text}}
    </span>
</template>

<script>
    export default {
        name: "CanteenText",
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

                let e =  this.canteens.find((e)=>{
                      return e.code === this.selectValue
                 });

                 return e ? e.name:'';
;
             }
        },
        data(){
            return {
                selectValue:'',
                canteens:[],
            }
        },
        created() {

            let vm = this;
            vm.$http.post(vm.$api.canteen.list,vm.$qs.stringify({onPage:false})).then((resp)=>{
                    vm.canteens = resp.data.data.canteens;
                    vm.selectValue = vm.value;

            });

        }
    }
</script>

<style scoped>

</style>
