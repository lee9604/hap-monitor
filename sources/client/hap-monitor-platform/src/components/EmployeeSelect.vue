<template>
    <span>
        <el-select v-model="selectValue" filterable placeholder="输入姓名检索" :clearable="clearable">
        <el-option
                v-for="item in employees"
                :key="item.code"
                :label="item.name"
                :value="item.code">
            <span style="float: left">{{ item.name }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.departmentName }}</span>
        </el-option>
    </el-select>
        <input v-model="value" style="display: none"></input>
    </span>
</template>

<script>
    export default {
        name: "EmployeeSelect",
        props:{
            value: {
                type: String,
                default:function () {
                    return '' ;
                }
            },clearable: {
                type: Boolean,
                default:function () {
                    return false ;
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
                    this.$emit('handleSelect',this.employees.find((e)=>e.code===this.selectValue));
                }
            }
        },
        data(){
            return {
                employees:[],
                selectValue:''
            }
        },
        created() {

            let vm = this;

            vm.$http.post(vm.$api.employee.list,vm.$qs.stringify({onPage:false})).then((resp)=>{
                    vm.employees = resp.data.data.employees;
                    vm.selectValue = vm.value;
            });

        }
    }
</script>

<style scoped>

</style>
