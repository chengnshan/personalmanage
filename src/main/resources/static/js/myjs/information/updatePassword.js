require.config({
    baseUrl : '../../../js/',
    shim:{
    	'top':{
    		deps:['jquery']
    	},
        'bootstrap' :{
            deps:['jquery']
        },
        'selectpicker':{
            deps:['jquery','bootstrap']
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
        'bootstrap_fileinput':{
        	deps:['jquery','bootstrap']
        },
        'fileinput_locale_zh':{
        	deps:['jquery','bootstrap','bootstrap_fileinput']
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
        'top':'myjs/top',
        'bootstrap':'bootstrap/js/bootstrap.min',
        'ajaxfileupload':'common/ajaxfileupload',
        'theme':'bootstrap/js/theme',
        'My97DatePicker':'common/My97DatePicker/WdatePicker',
        'datetimepicker':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.min',
        'moment':'common/bootstrap-datetimepicker/moment-with-locales',
        'bootstrap_fileinput':'bootstrap/bootstrap-fileinput/js/fileinput.min',
        'fileinput_locale_zh':'bootstrap/bootstrap-fileinput/js/fileinput_locale_zh',
        'selectpicker':'common/bootstrap-select-1.12.4/js/bootstrap-select.min',
        'CustCommonJs':'common/CustCommonJs',
        'common_share':'myjs/common/common_share',
        'updatePassword':'myjs/information/updatePassword'
    },
	waitSeconds: 0
});

require(['jquery','bootstrap','updatePassword','ajaxfileupload','theme',"moment",'My97DatePicker','datetimepicker',
	'bootstrap_fileinput','fileinput_locale_zh','selectpicker','CustCommonJs','common_share'],
    function ($, bootstrap, updatePassword,ajaxfileupload,theme,moment,My97DatePicker,datetimepicker,
    		bootstrap_fileinput,fileinput_locale_zh,selectpicker,CustCommonJs, common_share) {

//		$('#nav').load("../top.html");
	
		$('.selectpicker').selectpicker({'noneSelectedText':'请选择'});
		$('.selectpicker').selectpicker('refresh');

        common_share.getLoginUser();

		updatePassword.initFileInput('file-Portrait', "/consumeDetail/importConsumeDetailInfo");
		
		 $(document).on('click','#saveConsumeDetail',function(){
			 updatePassword.updateOwnerInfo();
	     });
		 
		 
		 $(document).on('click','#changePwd',function(){
			 var isChangePwd= $('#isChangePwd').val();
			 $('#changePwdDiv').toggleClass('changePwdDiv');
			 if(isChangePwd == 'NO'){
				 $('#isChangePwd').val('YES');
			 }else{
				 $('#isChangePwd').val('NO');
			 }
	     });
		 
});

define(['updatePassword'],function(updatePassword){
    var updatePassword = {

        getLoginUser:function(){
            $.ajax({
                url: '/admin/getLoginUser',
                dataType: 'json',
                data: '',
                contentType: 'application/x-www-form-urlencoded',
                success: function (data) {
                    var userInfo = data.userInfo;
                    $(".msg_span").text(userInfo.realName);
                    
                    var rolestr="";
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
                                    '   <li><a href="../../'+child.menuUrl+'"><i class="'+child.classStyle+'"></i> '+child.menuName+'</a></li>' ;
                            })
                            html+= chilHtml +'</ul></li>';
                        }else{
                            if(item.menuId == 'updatePassword'){
                                html += '<li class="active"><a href="../../'+item.menuUrl+'"><i class="'+item.classStyle+'"></i> '+item.menuName+'</a></li>';
                            }else{
                                html += '<li class=""><a href="../../'+item.menuUrl+'"><i class="'+item.classStyle+'"></i> '+item.menuName+'</a></li>';
                            }
                        }
                        	
                    });
                    updatePassword.getUserInfo(userInfo,userInfo.roleList);
                    $("#menu_ul").append(html);
                }
            });
        },
        
        getUserInfo:function(userInfo,roleList){
        	var userName = $('#userName').val(userInfo.userName);
        	var realName = $('#realName').val(userInfo.realName);
        	var id = $('#id').val(userInfo.id);
        	
        	var create_time = $('#create_time').val(userInfo.create_time);
        	var html="";
        	var roleNames = "";
        	$.each(roleList,function(index,item){
        		html += '<option value="'+item.roleId+'">'+item.roleName+'</option>';
        		roleNames +=item.roleName+",";
        	});
        	$('#roleSelect').val(roleNames.substring(0,roleNames.length-1));
//        	 $('#roleSelect').append("<option value=''>请选择</option>"+html);
 /*            $('#roleSelect').selectpicker({
                 size : 10
             });*/
 //           $('#roleSelect').selectpicker('refresh');
        },
        
        updateOwnerInfo:function(){
        	var userName = $('#userName').val();
        	var realName = $('#realName').val();
        	var id = $('#id').val();
        	var password = $('#password').val();
        	var confirmPassword = $('#confirmPassword').val();
        	
        	var isChangePwd= $('#isChangePwd').val();
        	if(isChangePwd == 'YES'){
        		if(password == '' || password == undefined){
        			$(".warning.updatePwd").text('请填写新密码!');
                	$('.alert-warning.updatePwd').slideDown(500).delay(2000).slideUp(1000);
        			return false;
            	}
        		if(confirmPassword == '' || confirmPassword == undefined){
        			$(".warning.updatePwd").text('请填写确认密码!');
                	$('.alert-warning.updatePwd').slideDown(500).delay(2000).slideUp(1000);
        			return false;
            	}
        		if( password != confirmPassword ){
            		$(".warning.updatePwd").text('新密码与确认密码不一致!');
                	$('.alert-warning.updatePwd').slideDown(500).delay(2000).slideUp(1000);
        			return false;
            	}
        	}
        	
 //       	var params = $.param()+'&'+$.param()+'&'+$.param()+'&'+$.param()+'&'+
        	
        	$.ajaxFileUpload({
        		url: '/admin/updateOwnerInfo',
        		secureuri: false,//是否启用安全提交  默认为false
                dataType: 'json',	//服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                type: 'POST',	//当要提交自定义参数时，这个参数要设置成post
                data: {userName:userName,realName:realName,password:password,confirmPassword:confirmPassword,isChangePwd:isChangePwd},
  //            contentType: 'application/x-www-form-urlencoded',
                fileElementId: "file-Portrait", //需要上传的文件域的ID，即<input type="file">的ID。
          //      async : true,   //是否是异步
                success: function (data) {
                	var result=data.resultData;
                	if(data.resultCode >=0 && result){
                		
                //		location.href ='../childrenMenu/user_manage.html';
                		location.href ='./updatePassword.html';
                	}
                },
                error: function(data, status, e) {  //提交失败自动执行的处理函数。
                    console.log(e);
                }
        	});
        },
        
      //初始化fileinput控件（第一次初始化）
        initFileInput:function(ctrlName, uploadUrl) {    
            var control = $('#' + ctrlName); 

            //初始化上传控件的样式
            control.fileinput({
                language: 'zh', //设置语言
                uploadUrl: uploadUrl, //上传的地址
                uploadExtraData:{id : 1,userName:"李青"},  //要上传的数据
                allowedFileExtensions: ['jpg','png','xls'],//接收的文件后缀
                showUpload: false, //是否显示上传按钮
                showCancel:false,	//是否显示取消按钮
                showCaption: false,//是否显示标题
                browseClass: "btn btn-primary", //按钮样式     
                //dropZoneEnabled: false,//是否显示拖拽区域
                //minImageWidth: 50, //图片的最小宽度
                //minImageHeight: 50,//图片的最小高度
                //maxImageWidth: 1000,//图片的最大宽度
                //maxImageHeight: 1000,//图片的最大高度
                //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
                //minFileCount: 0,
                maxFileCount: 10, //表示允许同时上传的最大文件个数
                maxFileSize : 100000,//单位为kb，如果为0表示不限制文件大小
                maxFilesNum : 10,//
                layoutTemplates :{
                    // actionDelete:'', //去除上传预览的缩略图中的删除图标
                    actionUpload:'',//去除上传预览缩略图中的上传图片；
                    actionZoom:''   //去除上传预览缩略图中的查看详情预览的缩略图标。
                },
                enctype: 'multipart/form-data',
                validateInitialCount:true,
                previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
                msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            }).on("fileuploaded", function (event, data, previewId, index) {
            	//导入文件上传完成之后的事件
            	var dataText = data.response;
                var result= dataText.resultData;
            	 if(dataText.resultCode >=0 && result) {
               //     fileName.push(data.response.fileName);
              //       $("#uploadConsumeDetail").modal("toggle");
             //        signup.queryConsumeDetailInfo(page,1);
                 }else{
                     $(".uploadConsume_p").text(dataText.resultMessage);
             		 $('.uploadConsume_div').slideDown(500).delay(2000).slideUp(1000);
                 }
            	 control.fileinput("clear");
            	 control.fileinput("reset");
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
    return updatePassword;
});