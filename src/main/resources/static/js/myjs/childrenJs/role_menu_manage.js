require.config({
    baseUrl : '../../../js/',
    shim:{
        'bootstrap' :{
            deps:['jquery']
        },
        'ajaxfileupload':{
            deps:['jquery','bootstrap']
        },
        "page" : {
            deps :["jquery"]
        },
        'theme':{
            deps :['jquery']
        },
        'icheck':{
        	 deps :['jquery']
        },
        'confirmTool':{
        	deps:['jquery','bootstrap']
        },
        'selectpicker':{
            deps:['jquery','bootstrap']
        },
        'i18n':{
            deps:['jquery','bootstrap']
        },
        'role_menu_manage':{
            deps:['jquery','bootstrap','theme'],
            exports:'role_menu_manage'
        },
        "datetimepicker":{
            deps:["jquery","bootstrap"]
        },
        "moment":{
            deps:["jquery","bootstrap"]
        },
        'CustCommonJs':{
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
        'confirmTool':'common/confirm/confirmTool',
        'My97DatePicker':'common/My97DatePicker/WdatePicker',
        'datetimepicker':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.min',
        'moment':'common/bootstrap-datetimepicker/moment-with-locales',
        'selectpicker':'common/bootstrap-select-1.12.4/js/bootstrap-select.min',
        'i18n':'common/bootstrap-select-1.12.4/js/i18n/defaults-zh_CN',
        'page' : 'common/pagegenerator',
        'icheck':'bootstrap/bootstrap-checkbox/js/icheck.min',
        'CustCommonJs':'common/CustCommonJs',
        'common_share':'myjs/common/common_share',
        'role_menu_manage':'myjs/childrenJs/role_menu_manage'
    },
	waitSeconds: 0
});

require(['jquery','bootstrap','role_menu_manage','ajaxfileupload','theme','confirmTool',"moment","page",'selectpicker',
	'i18n','My97DatePicker','datetimepicker','icheck','CustCommonJs','common_share'],
    function ($, bootstrap, role_menu_manage,ajaxfileupload,theme,confirmTool,moment,page,selectpicker,
    		i18n,My97DatePicker,datetimepicker,icheck,CustCommonJs,common_share) {
		
	//	$('#nav').load("../top.html");
	
		$.comleteAjax();
	
		role_menu_manage.messsage();

        common_share.getLoginUser();

		role_menu_manage.getRoleList();

		role_menu_manage.getMenuInfoList();
		
		$(document).on('click','#saveRoleMenu',function(){
			role_menu_manage.saveRoleMenuInfo();
		});
		

		$(document).on('click','#clearText',function(){
			role_menu_manage.clearCheckText();
		});
});	

define(['role_menu_manage'],function(role_menu_manage){
    var role_menu_manage = {
        messsage:function () {
            console.log("加载了!");
        },

        getLoginUser:function(){
            $.ajax({
                url: '/admin/getLoginUser',
                dataType: 'json',
                data: {timestamp:new Date()},
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
                                '<ul class="nav navbar-nav" style="width: 100%;display: block;">';

                            $.each(item.childrenMenus,function (index1,child) {
                                console.log(child.menuName);
                                if(child.menuId == 'role_menu_manage'){
                                    chilHtml +=
                                        '   <li class="active"><a href="../../'+child.menuUrl+'"><i class="'+child.classStyle+'"></i> '+child.menuName+'</a></li>' ;
                                }else{
                                    chilHtml +=
                                        '   <li><a href="../../'+child.menuUrl+'"><i class="'+child.classStyle+'"></i> '+child.menuName+'</a></li>' ;
                                }

                            })

                            html+= chilHtml +'</ul></li>';
                        }else{
                                html += '<li class=""><a href="../../'+item.menuUrl+'"><i class="'+item.classStyle+'"></i> '+item.menuName+'</a></li>';
                        }
                    });
                    $("#menu_ul").append(html);
                }
            });
        },
        getRoleList:function(){
            $.ajax({
                url:'/role/findRoleList',
                dataType:'json',
                data:{timestamp:new Date()},
                type:'post',
                contentType:'application/x-www-form-urlencoded',
                success:function (data) {
                    var html = '<ul class="list-group">';
                    if(data){
                        $.each(data,function (index, item) {
                        	html+='<li class="list-group-item">';
                            html += '<pre><div class="radio"><label><input type="radio" name="roleId" value="'+item.roleId+'">&nbsp;&nbsp;'+item.roleName+"</label></div></pre></li>";
                        });
                    }
                    html +="</ul>";
                    $('#roleIdList').append(html);
                    
                    //radio初始化
                    $("input[name='roleId']").iCheck({
                        handler:'checkbox',
                        radioClass:'iradio_square-red'
                    });
                    //事件在初始化之前绑定
                    $('input[name=roleId]').on('ifChecked',function(e){
                    	var radioVal = $(this).val();
            			console.log(radioVal);
            			role_menu_manage.getMenuInfoByRoleId(radioVal);
            			
            		});
                }
            });
        },
        
        /**
         * 根据角色Id获取菜单列表
         */
        getMenuInfoByRoleId:function(roleId){
        	$('input[name=menuId]').iCheck('uncheck');  //清除复选框选中状态,重新选择
        	$.ajax({
                url:'/roleMenu/findRoleMenuInfoList',
                dataType:'json',
                data:{timestamp:new Date(),roleId:roleId},
                type:'post',
                contentType:'application/x-www-form-urlencoded',
        //        async:false, 
                success:function (data) {
                	var result = data.resultData;
                    if(data.resultCode >=0 && result){
                    	$.each(result,function(index,item){
                    		$(':checkbox[value='+item.menuId+']').iCheck('check');
                    	});
                    }
                }
            });
        },
        
        getMenuInfoList:function(){
        	$.ajax({
                url:'/menu/findMenuList',
                dataType:'json',
                data:{timestamp:new Date()},
                type:'post',
                contentType:'application/x-www-form-urlencoded',
                success:function (data) {
                    var html = '<ul class="list-group">';
                    var result = data.resultData;
                    if(data.resultCode >=0 && result){
                        $.each(result,function (index, item) {
                        	html+='<li class="list-group-item">';
                            html += '<div class="checkbox"><label><input type="checkbox" name="menuId" value="'+item.menuId+'">&nbsp;&nbsp;'+item.menuName+"</label></div></li>";
                        });
                    }
                    html +="</ul>";
                    $('#menuIdList').append(html);
            		
                    $(':checkbox').iCheck({
                    	 handle : 'checkbox',
                         checkboxClass : 'icheckbox_square-red'
                    });
                    
            	//	$(':checkbox').iCheck('check');
                }
            });
        },
 
        saveRoleMenuInfo:function(){
        	var roleId = $(':radio[name=roleId]:checked').val();
    //    	console.log(roleId);
        	
        	var menuId = new Array();
        	$.each($('input[name="menuId"]:checked'),function(i,item){
        		menuId.push($(item).val());
        	});
    //    	console.log(JSON.stringify(menuId));
        	
        	if(roleId ==undefined || roleId == null || roleId == ''){
        		$(".warning_p.saveRoleMenu").text("请选择一个需要更新的角色!");
            	$('.alert-warning.saveRoleMenu').slideDown(500).delay(2000).slideUp(1000);
            	return false;
        	}
        	
        	$.ajax({
                url:'/roleMenu/saveRoleMenuInfo',
                dataType:'json',
                data:{timestamp:new Date(),roleId:roleId,menuId:JSON.stringify(menuId)},
                type:'post',
                contentType:'application/x-www-form-urlencoded',
                success:function (data) {
                    var result = data.resultData;
                    if(data.resultCode >=0 && result){
                    	$(".success_p.saveRoleMenu").text("角色菜单信息保存成功!");
                    	$('.alert-success.saveRoleMenu').slideDown(500).delay(2000).slideUp(1000);
                    }else{
                    	$(".warning_p.saveRoleMenu").text(data.resultMessage);
                    	$('.alert-warning.saveRoleMenu').slideDown(500).delay(2000).slideUp(1000);
                    }
                },
                error:function(){
                	$(".warning_p.saveRoleMenu").text("网络异常,角色菜单信息保存失败!");
                	$('.alert-warning.saveRoleMenu').slideDown(500).delay(2000).slideUp(1000);
                }
        	});
        },
        
        clearAddText : function(addText){
        	if(addText == 'add'){
        		 $('#addUserName').val('');
            	 $('#addRealName').val('');
                 $('#addRoleId').val('');
                 $('#addUserPassword').val('');
        	}else{
        		$('#userName').val('');
           	 	$('#realName').val('');
                $('#roleSelect').selectpicker('val','');
        	}
        },
        
        clearCheckText:function(){
        	$('input').iCheck('uncheck');
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
        },
        pageClick : function(page){
            var element = $("#page nav li:not(.disabled) a");
            $(element).each(function(){
                var url=$(this).attr("href");
                if(url != "#"){
                    var pageNo= url.substr(url.indexOf("pageNo=") + 7 ,url.length);
                    //绑定click事件
                    $(this).click(function () {
                        role_menu_manage.getUserList(page,parseInt(pageNo));
                        return false;
                    });
                }
            });
        }
    };
    return role_menu_manage;
});
