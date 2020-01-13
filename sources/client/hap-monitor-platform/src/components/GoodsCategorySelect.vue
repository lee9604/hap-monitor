<template>
    <span>
        <el-select v-model="selectValue" placeholder="请选择">
        <el-option
                v-for="item in goodsCategorys"
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
        name: "GoodsCategorySelect",
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
                goodsCategorys:[],
                selectValue:[],
            }
        },
        created() {

            let vm = this;

            vm.$http.post(vm.$api.goodsCategory.list,vm.$qs.stringify({onPage:false})).then((resp)=>{
                    vm.goodsCategorys = resp.data.data.goodsCategorys;
            });

        }
    }
</script>

<style scoped>

</style>
