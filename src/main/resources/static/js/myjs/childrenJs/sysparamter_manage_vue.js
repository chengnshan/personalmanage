
var vue = new Vue({
    el: '#wrapper',
    data(){
        return {
            currentPage: '',
            param_code:'',
            list: [],
            menu_html : '',
            loginUserNameStr : '',

            updateParamCode:'',
            updateParamName:'',
            updateParamValue:'',
            updateEnable:'',
            updateParamId:'',

            addParamCode:'',
            addParamName:'',
            addParamValue:'',
            addParamEnable:'',
        }
    },
    mounted: function(){
        this.$http.post('/admin/getLoginUser',{}, {emulateJSON: true}).then(function (result) {
            var body = result.body;
            if (body && body.resultCode >= 0){

                var resultData = body.resultData;
                var userInfo = JSON.parse(resultData);

                var menuList = userInfo.menuListByRoleId;
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
        this.$http.post('/systemParamter/listSystemParam',{'param_code' : this.param_code}, {emulateJSON: true}).then(function (result) {
            var body = result.body;
            if (body && body.resultCode >= 0){
                this.list = body.resultData;
            }
        });
    },
    methods:{
        queryParam(param){
            this.$http.post('/systemParamter/listSystemParam',{'param_code' : this.param_code}, {emulateJSON: true}).then(function (result) {
                var body = result.body;
                if (body && body.resultCode >= 0){
                    this.list = body.resultData;
                }
            });
        },
        clearQueryText(){
            this.param_code = '';
        },
        updateParamModel(id){
            this.$http.post('/systemParamter/getSystemParamById',{'id' : id}, {emulateJSON: true}).then(function (result) {
                var body = result.body;
                if (body && body.resultCode >= 0){
                    var info = body.resultData;
                    this.updateParamCode = info.param_code;
                    this.updateParamName = info.param_name;
                    this.updateParamValue = info.param_value;
                    this.updateEnable = info.enable;
                    this.updateParamId = info.id;
                    // $('#updateEnable').selectpicker('refresh');
                }
            });
            // this.$refs.updateEnable;
           // console.log(document.querySelector('#updateEnable'));
        },
        removeParamModel(id){
            var rem = this.$http;
            Ewin.confirm({ message: "确认要删除<span style='color:red;font-size:16px;'></span>吗?" }).on(function (e) {
                if (!e) {
                    return;
                }
                rem.post('/systemParamter/deleteSystemParamById',{'id' : id}, {emulateJSON: true}).then(function (result) {
                    var body = result.body;
                    if (body && body.resultCode >= 0){
                        this.queryParam();
                    }
                });
            });
        },
        saveParamInfo(){
            //获取ref定义的dom节点
            console.log(this.$refs.updateModal);
            this.$http.post('/systemParamter/saveSystemParam'
                ,{ 'param_code':this.addParamCode, 'param_name':this.addParamName, 'param_value':this.addParamValue, 'enable':this.addParamEnable}
                , {emulateJSON: true})
                .then(function (result) {
                    var body = result.body;
                    if (body && body.resultCode >= 0){
                        $('#addParamModal').modal('hide');
                        this.queryParam();
                    }
                });
        },
        updateParamInfo(){
            this.$http.post('/systemParamter/updateSystemParam'
                ,{'id' : this.updateParamId, 'param_code':this.updateParamCode,
                    'param_name':this.updateParamName, 'param_value':this.updateParamValue, 'enable':this.updateEnable}
                , {emulateJSON: true})
                .then(function (result) {
                var body = result.body;
                if (body && body.resultCode >= 0){
                    $('#updateModal').modal('hide');
                    this.queryParam();
                }
            });
        }
    }
});