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
        'forms':{
            deps:['jquery','bootstrap','theme'],
            exports:'forms'
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
        'top':'myjs/top',
        'bootstrap':'bootstrap/js/bootstrap.min',
        'ajaxfileupload':'common/ajaxfileupload',
        'theme':'bootstrap/js/theme',
        'My97DatePicker':'common/My97DatePicker/WdatePicker',
        'datetimepicker':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.min',
        'moment':'common/bootstrap-datetimepicker/moment-with-locales',
        'common_share':'myjs/common/common_share',
        'forms':'myjs/forms'
    },
	waitSeconds: 0
});

require(['jquery','bootstrap','forms','ajaxfileupload','theme',"moment",'My97DatePicker','datetimepicker','common_share'],
    function ($, bootstrap, forms,ajaxfileupload,theme,moment,My97DatePicker,datetimepicker,common_share) {

//		$('#nav').load("top.html");

        common_share.getLoginUser();

});

define(['forms'],function(forms){
    var forms = {

        getLoginUser:function(){
            $.ajax({
                url: '/admin/getLoginUser',
                dataType: 'json',
                data: '',
                contentType: 'application/x-www-form-urlencoded',
                success: function (data) {
                    var userInfo = data.userInfo;
                    $(".msg_span").text(userInfo.realName);
                    var menuList = data.menuListByRoleId;
                    html = "";
                    $.each(menuList, function (index, item) {

                        if(item.childrenMenus.length >0 ){
                            var chilHtml = "";
                            html += '<li class="nav nav-list nav-list-expandable nav-list-expanded">' +
                                '<a><i class="fa fa-user"></i>'+item.menuName+' <span class="caret"></span></a>' +
                                '<ul class="nav navbar-nav" style="width: 100%;">';
                            $.each(item.childrenMenus,function (index1,child) {

                                chilHtml +=
                                    '   <li><a href="../'+child.menuUrl+'"><i class="'+child.classStyle+'"></i> '+child.menuName+'</a></li>' ;
                            })
                            html+= chilHtml +'</ul></li>';
                        }else{
                            if(item.menuId == 'forms'){
                                html += '<li class="active"><a href="../'+item.menuUrl+'"><i class="'+item.classStyle+'"></i> '+item.menuName+'</a></li>';
                            }else{
                                html += '<li class=""><a href="../'+item.menuUrl+'"><i class="'+item.classStyle+'"></i> '+item.menuName+'</a></li>';
                            }
                        }

                    });
                    $("#menu_ul").append(html);
                }
            });
        },

       /* topHtml:function(){
        	var topHtml = 
        	'<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">'+
        	'	<div class="navbar-header">'+
        	'		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">'+
        	' 			<span class="sr-only">Toggle navigation</span>'+
        	'			<span class="icon-bar"></span>'+
        	'			<span class="icon-bar"></span><span class="icon-bar"></span>'+
        	'		</button>'+
        	' 		<a class="navbar-brand" href="../index.html">返回主页面</a>'+
        	'	</div>'+
        	'	<div class="collapse navbar-collapse navbar-ex1-collapse">'+
        	'		<ul class="nav navbar-nav side-nav" id="menu_ul">'+
        	' 		</ul>'+
        	'		<ul class="nav navbar-nav navbar-right navbar-user">'+
        	'			<li class="dropdown messages-dropdown">'+
        	'				<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> Messages '+
        	'				<span class="badge">2</span> <b class="caret"></b></a>'+
        	'				<ul class="dropdown-menu"><li class="dropdown-header">2 New Messages</li> '+
        	'					<li class="message-preview">'+
        	'						<a href="#"><span class="avatar"><i class="fa fa-bell"></i></span>'+
        	'						<span class="message">Security alert</span> </a>'+
            '					</li>'+
            '					<li class="divider"></li>'+
            '					<li class="message-preview">'+
            '						<a href="#">'+
            '						<span class="avatar"><i class="fa fa-bell"></i></span><span class="message">Security alert</span>'+
           '						</a>'+
           ' 					</li>'+
           '					<li class="divider"></li><li><a href="#">Go to Inbox <span class="badge">2</span></a></li>'+
           '				</ul>'+
           '			</li>'+
           '			<li class="dropdown user-dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">'+
           '				<i class="fa fa-user"></i> <span class="msg_span"></span> <b class="caret"></b></a>'+
           '				<ul class="dropdown-menu">'+
           '					<li><a href="#"><i class="fa fa-user"></i> Profile</a></li>'+
           '					<li><a href="#"><i class="fa fa-gear"></i> Settings</a></li>'+
           '					<li class="divider"></li>'+
           '					<li><a href="/logout"><i class="fa fa-power-off"></i> Log Out</a></li>'+
           '				</ul>'+
           '			</li>'+
           '		</ul>'+
           '	</div>'+
           '</nav>';
        	
        	$('#page-wrapper').before(topHtml);
        },*/
        
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
    return forms;
});