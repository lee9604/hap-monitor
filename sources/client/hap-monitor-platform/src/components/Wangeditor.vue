<template>
    <div>
        <div ref="editor" class="wangeditor"></div>
        <input type="hidden" v-model="value">
    </div>
</template>

<script>
    import  E from 'wangeditor';
    export default {
        name: "Wangeditor",
        props:{
            uploadImgServer:{
              type:String,
              default:function () {
                  return "";
              }
            },showLinkImg:{
              type:Boolean,
              default:function () {
                  return true;
              }
            },
            value:{
                type:String,
                default:function () {
                    return "";
                }
            }
        },
        watch:{
          value(){
              if(this.editor2 && !this.editor2.txt.html().replace(/<[^>]+>/g,"") ){
                  this.editor2.txt.html(this.value);
              }
          }
        },
        data(){
           return  {editor2:null}
        },
        mounted() {
            let vm = this;
            let editor2 = new E(this.$refs.editor);
            editor2.customConfig.uploadImgServer = vm.uploadImgServer ;
            editor2.customConfig.showLinkImg = vm.showLinkImg;

            editor2.customConfig.pasteFilterStyle = false;
            editor2.customConfig.zIndex = 100;
            editor2.customConfig.onchange = function (html) {
                vm.$emit('input',html);
            };
            editor2.create();
            this.editor2 = editor2;
            this.editor2.txt.html(this.value);

        }
    }
</script>

<style scoped>

    .wangeditor{
         z-index: -1;
    }

</style>
