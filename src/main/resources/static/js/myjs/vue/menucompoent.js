
function menucom(){
    var menucomponet = {
        template:
            '<ul class="nav navbar-nav side-nav" >' +

            '<li v-for="(item,index) in menulist" :class="[item.childrenMenus.length > 0 ? childmenuclass : \'\' ]" @click="expandableclick($event)" :key="item.id">' +
                '<a v-bind:href=" item.childrenMenus.length >0 ? \'#\' : item.menuUrl ">' +
                '<i :class="item.classStyle"></i>{{item.menuName}} <span :class="[item.childrenMenus.length > 0 ? \'caret\':\'\']"></span></a></a>' +
                '<ul v-if="item.childrenMenus.length > 0" class="nav navbar-nav" style="width: 100%;display: block;">' +
                    '<li v-for="(child, index) in item.childrenMenus">' +
                        '<a :href="child.menuUrl"><i :class="child.classStyle"></i>{{child.menuName}} </a>' +
            '       </li>' +
                '</ul>' +
            '</li>' +

            ' </ul>',
        data(){
            return {
                expandable: false,
                childmenuclass : 'nav nav-list nav-list-expandable nav-list-expanded',
                ulclass: 'none'
            }
        },
        props: ['menulist'],
        methods: {
            expandableclick( event){
                // console.log(this.menulist);
                //获取点击对象
                var el = event.currentTarget;
                var cc = this._data.ulclass;
                $(el).find('ul').attr("style","width:100%;display:" + cc);
                cc == 'none' ? this._data.ulclass='block' : this._data.ulclass='none';
            }
        },
        mounted(){
        }
    };
    return menucomponet;
}