<template>
    <span>
        <el-select v-model="selectValue" placeholder="请选择">
        <el-option
                v-for="item in suppliers"
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
        name: "SupplierSelect",
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
                }
            }
        },
        data(){
            return {
                suppliers:[],
                selectValue:[],
            }
        },
        created() {

            let vm = this;

            vm.$http.post(vm.$api.supplier.list,vm.$qs.stringify({onPage:false})).then((resp)=>{
                    vm.suppliers = resp.data.data.suppliers;
            });

        }
    }
</script>

<style scoped>

</style>
