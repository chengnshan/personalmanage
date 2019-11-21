var menucom1 = menucom();
var vue = new Vue({
    el: '#wrapper',
    data(){
        return {
            menulist : [],
            menu_html : '',
            loginUserNameStr: ''
        }
    },
    methods: {

    },
    mounted(){
        this.$http.post('/admin/getLoginUser',{}, {emulateJSON: true}).then(function (result) {
            var body = result.body;
            if (body && body.resultCode >= 0){

                var resultData = body.resultData;
                var userInfo = JSON.parse(resultData);

                var menuList = userInfo.menuListByRoleId;
                this.menulist = menuList;
                html = "";
                for (var i =0 ; i< menuList.length; i++){
                    if(menuList[i].childrenMenus.length >0 ){
                        var chilHtml = "";
                        html += '<li class="nav nav-list nav-list-expandable nav-list-expanded">' +
                            '<a><i class="fa fa-user"></i>'+menuList[i].menuName+' <span class="caret"></span></a>' +
                            '<ul class="nav navbar-nav" style="width: 100%; display: block;">';
                        for (var j =0 ; j< menuList[i].childrenMenus.length; j++){
                            var childrenMenus = menuList[i].childrenMenus;
                            console.log(childrenMenus[j].menuName);
                            chilHtml +=
                                '   <li><a href="'+childrenMenus[j].menuUrl+'">' +
                                '<i class="'+ childrenMenus[j].classStyle+'"></i> '+
                                childrenMenus[j].menuName+'</a></li>' ;
                        }
                        html+= chilHtml +'</ul></li>';
                    }else{
                        html += '<li class=""><a href="'+menuList[i].menuUrl+'"><i class="'+menuList[i].classStyle+'"></i> '+menuList[i].menuName+'</a></li>';
                    }
                }
                this.menu_html = html;
                this.loginUserNameStr = userInfo.userInfo.realName;
            }
        });
    },
    components: {
        menucomponet : menucom1
    }
});