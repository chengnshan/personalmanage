require.config({
    baseUrl : '../../js/',
    shim:{
        'bootstrap' :{
            deps:['jquery']
        },
        'ajaxfileupload':{
            deps:['jquery','bootstrap']
        },
        'theme':{
            deps :['jquery']
        },
        'bootstrap_elements':{
            deps:['jquery','bootstrap','theme'],
            exports:'bootstrap_elements'
        },
        "datetimepicker":{
            deps:["jquery","bootstrap"]
        },
        "moment":{
            deps:["jquery","bootstrap"]
        },
        'common_share':{
            deps:["jquery","bootstrap"]
        },
        "defaults":{
            deps:["jquery","bootstrap"]
        }
    },
    paths:{
        'jquery' : 'common/jquery-1.9.1.min',
        'bootstrap':'bootstrap/js/bootstrap.min',
        'ajaxfileupload':'common/ajaxfileupload',
        'theme':'bootstrap/js/theme',
        'My97DatePicker':'common/My97DatePicker/WdatePicker',
        'datetimepicker':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.min',
        'moment':'common/bootstrap-datetimepicker/moment-with-locales',
        'common_share':'myjs/common/common_share',
        'bootstrap_elements':'myjs/bootstrap_elements'
    },
	waitSeconds: 0
});

require(['jquery','bootstrap','bootstrap_elements','ajaxfileupload','theme',"moment",'My97DatePicker','datetimepicker','common_share'],
    function ($, bootstrap, bootstrap_elements,ajaxfileupload,theme,moment,My97DatePicker,datetimepicker,common_share) {
//		$('#nav').load("top.html");
	
        bootstrap_elements.messsage();

        common_share.getLoginUser();

});

define(['bootstrap_elements'],function(bootstrap_elements){
    var bootstrap_elements = {
        messsage:function () {
            console.log("加载了!");
        },

        getLoginUser:function(){
            $.ajax({
                url: '/admin/getLoginUser',
                dataType: 'json',
                data: '',
                contentType: 'application/x-www-form-urlencoded',
                success: function (data) {
                    let resultCode = data.resultCode;
                    if ( resultCode >= 0){
                        var resultData = data.resultData;
                        var userInfo = JSON.parse(resultData);
                        $(".msg_span").text(userInfo.userInfo.realName);
                        var menuList = userInfo.menuListByRoleId;
                        html = "";
                        $.each(menuList, function (index, item) {

                            if(item.childrenMenus.length >0 ){
                                var chilHtml = "";
                                html += '<li class="nav nav-list nav-list-expandable nav-list-expanded">' +
                                    '<a><i class="fa fa-user"></i>'+item.menuName+' <span class="caret"></span></a>' +
                                    '<ul class="nav navbar-nav" style="width: 100%;">';
                                $.each(item.childrenMenus,function (index1,child) {
                                    console.log(child.menuName);
                                    chilHtml +=
                                        '   <li><a href="../'+child.menuUrl+'"><i class="'+child.classStyle+'"></i> '+child.menuName+'</a></li>' ;
                                })
                                html+= chilHtml +'</ul></li>';
                            }else{
                                if(item.menuId == 'bootstrap_elements'){
                                    html += '<li class="active"><a href="../'+item.menuUrl+'"><i class="'+item.classStyle+'"></i> '+item.menuName+'</a></li>';
                                }else{
                                    html += '<li class=""><a href="../'+item.menuUrl+'"><i class="'+item.classStyle+'"></i> '+item.menuName+'</a></li>';
                                }
                            }

                        });
                        $("#menu_ul").append(html);
                    }else {
                        let resultMessage = data.resultMessage;
                        alert(resultMessage);
                    }
                }
            });
        },

        //下载方法
        fileDownload :function (url) {
            $.ajaxFileUpload({
                url:url,
                data:{timestamp:new Date().getTime()},
                dataType:'json',
                success:function (data) {
                    
                }
            });
        }
    };
    return bootstrap_elements;
});