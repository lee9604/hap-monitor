<template>
    <span>
        <el-select v-model="selectValue" placeholder="请选择">
        <el-option
                v-for="item in goodss"
                :key="item.code"
                :label="item.name"
                :value="item.code">
        </el-option>
    </el-select>
        <input v-model="value" style="display: none"></input>
    </span>
</template>

<script>
    export default {
        name: "GoodsSelect",
        props:{
            value: {
                type: String,
                default:function () {
                    return '' ;
                }
            }
        },
        watch:{
            value:{
              deep:true,
              handler:function () {
                  this.selectValue = this.value;
              }
            },
            selectValue:{
                deep:true,
                handler:function () {
                    this.$emit('input',this.selectValue);
                    this.$emit('handleSelect',this.selectValue);
                }
            }
        },
        data(){
            return {
                goodss:[],
                selectValue:''
            }
        },
        created() {

            let vm = this;

            vm.$http.post(vm.$api.goods.list,vm.$qs.stringify({onPage:false})).then((resp)=>{
                    vm.goodss = resp.data.data.goodss;
                    vm.selectValue = vm.value;
            });

        }
    }
</script>

<style scoped>

</style>
