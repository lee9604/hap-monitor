<template>

  <span>
   {{defaultArtCategoryCode ? defaultArtCategoryCode :'(无)'}}
  </span>
</template>

<script>
    let win = window;
    export default {
        name: "ArtCategoryText",
        components: {},
        props: {
            artCategoryCode: {
                type: String,
                default: ''
            }

        },
        watch: {
            artCategoryCode(val) {
                if (val) {
                    this.loadArtCategoryTree();
                }
            }
        },

        data() {
            return {
                options: [],
                defaultArtCategoryCode: '',
            }
        },
        computed: {


        },
        methods: {

            loadArtCategoryTree: function () {



                let getDefaultArtCategoryCodeValue = function (code, options, result) {


                    for (let e in options) {

                        if (options[e].value === code) {
                            result.push(options[e].label);
                            return true;

                        } else if (options[e].children && options[e].children.length > 0) {
                            if (getDefaultArtCategoryCodeValue(code, options[e].children, result)) {
                                result.push(options[e].label);
                                return true;
                            }
                        }
                    }


                    return false;

                };


                let vm = this;
                let result = [];


                if(win.artCategoryOption){
                    vm.options = win.artCategoryOption;
                    if (vm.artCategoryCode) {
                        getDefaultArtCategoryCodeValue(vm.artCategoryCode,win.artCategoryOption, result);
                        vm.defaultArtCategoryCode = result.reverse().join(' / ');
                    }else{
                        vm.defaultArtCategoryCode = '';
                    }

                    return ;
                }


                vm.$http.get(vm.$api.scafeArtCategory.tree).then(function (resp) {


                    win.artCategoryOption = vm.options = vm.artCategoryTreeToArtCategoryOption(resp.data.data.tree).children;


                    if (vm.artCategoryCode) {
                        getDefaultArtCategoryCodeValue(vm.artCategoryCode, vm.options, result);
                        vm.defaultArtCategoryCode = result.reverse().join(' / ');
                    }


                });
            },
            artCategoryTreeToArtCategoryOption: function (artCategoryTree) {
                let vm = this;
                let artCategoryOption = {};


                if (artCategoryTree.code) {
                    artCategoryOption.artCategoryOption = artCategoryTree.name;
                    artCategoryOption.value = artCategoryTree.code;
                    artCategoryOption.flag = artCategoryTree.flag;
                    artCategoryOption.label = artCategoryTree.name;
                }

                if (artCategoryTree.children !== null && artCategoryTree.children.length > 0) {
                    artCategoryOption.children = [];
                    artCategoryTree.children.forEach(function (e) {
                        artCategoryOption.children.push(vm.artCategoryTreeToArtCategoryOption(e));
                    });
                }
                return artCategoryOption;
            }
        },
        created: function () {
            this.loadArtCategoryTree();
        }
    }
</script>

<style ref="stylesheet/scss" lang="scss">


</style>
