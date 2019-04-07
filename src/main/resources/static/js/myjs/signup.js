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
        'selectpicker':{
            deps:['jquery','bootstrap']
        },
        'i18n':{
            deps:['jquery','bootstrap']
        },
        'signup':{
            deps:['jquery','bootstrap','theme'],
            exports:'signup'
        },
        "datetimepicker":{
            deps:["jquery","bootstrap"]
        },
        'selectpicker_zh_CN':{
            deps:['jquery','bootstrap','datetimepicker']
        },
        "moment":{
            deps:["jquery","bootstrap"]
        },
        "page" : {
            deps :["jquery"]
        },
        'CustCommonJs':{
        	deps:["jquery","bootstrap"]
        },
        'bootstrap_fileinput':{
        	deps:['jquery','bootstrap']
        },
        'fileinput_locale_zh':{
        	deps:['jquery','bootstrap','bootstrap_fileinput']
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
        'selectpicker':'common/bootstrap-select-1.12.4/js/bootstrap-select.min',
        'i18n':'common/bootstrap-select-1.12.4/js/i18n/defaults-zh_CN',
        'My97DatePicker':'common/My97DatePicker/WdatePicker',
        'datetimepicker':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.min',
        'selectpicker_zh_CN':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN',
        'moment':'common/bootstrap-datetimepicker/moment-with-locales',
        'page' : 'common/pagegenerator',
        'CustCommonJs':'common/CustCommonJs',
        'bootstrap_fileinput':'bootstrap/bootstrap-fileinput/js/fileinput.min',
        'fileinput_locale_zh':'bootstrap/bootstrap-fileinput/js/fileinput_locale_zh',
        'signup':'myjs/signup'
    },
	waitSeconds: 0
});

require(['jquery','bootstrap','signup','ajaxfileupload','theme','selectpicker','i18n',"moment","page",'My97DatePicker','datetimepicker'
	,'selectpicker_zh_CN','CustCommonJs','bootstrap_fileinput','fileinput_locale_zh'],
    function ($, bootstrap, signup,ajaxfileupload,theme,selectpicker,i18n,moment,page,My97DatePicker,datetimepicker
    		,selectpicker_zh_CN,CustCommonJs,bootstrap_fileinput,fileinput_locale_zh) {
	
//		$('#nav').load("top.html");
	
		$.comleteAjax();

        signup.messsage();

        signup.getLoginUser();
        
        signup.queryConsumeTypeInfo();

        signup.queryConsumeChannelInfo();

        signup.initDateTimePicker();

        //默认进入只查询当日消费明细
        signup.queryConsumeDetailInfo(page,1);
        
        $(document).on('click','#queryDetail',function(){
        	signup.queryConsumeDetailInfo(page,1);
        });
        
        $(document).on('click','#addConsumeDetail',function(){
        	signup.clearAddText('add');
        	$("#addConsumeDetailModal").modal("toggle");
        });
        
        $(document).on('click','#saveConsumeDetail',function(){
        	signup.saveConsumeDetailFun(page);
        });

        $(document).on('click','#clearQueryText',function(){
        	signup.clearAddText();
        });
        
        $(document).on('click','#updateConsumeDetailA',function(){
        	var detailId = $(this).attr('value');
        	signup.updateConsumeDetail(detailId);
        });
        
        $(document).on('click','#updateConsumeDetailBtn',function(){
        	signup.saveUpdateConsumeDetail(page);
        });
        
        //初始化fileinput控件（第一次初始化）
        signup.initFileInput('file-Portrait', "/consumeDetail/importConsumeDetailInfo",page);
        
        $(document).on('click','#importConsumeDetail',function(){
        	$('#file-Portrait').fileinput("clear");
        	$('#file-Portrait').fileinput("reset");
        	$("#uploadConsumeDetail").modal("toggle");
        });
});

define(['signup'],function(signup){
    var signup = {
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
                                console.log(child.menuName);
                                chilHtml +=
                                    '   <li><a href="../'+child.menuUrl+'"><i class="'+child.classStyle+'"></i> '+child.menuName+'</a></li>' ;
                            })
                            html+= chilHtml +'</ul></li>';
                        }else{
                            if(item.menuId == 'signup'){
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
        /**
         * 下拉框获取消费类型
         */
        queryConsumeTypeInfo:function(){
        	$.ajax({
        		 url:'/consumeType/findConsumeTypeInfoList',
                 dataType:'json',
                 data:{timestamp:new Date()},
                 type:'post',
                 contentType:'application/x-www-form-urlencoded',
                 success:function (data) {
                	 var result = data.resultData;
                	 if(data.resultCode >=0 && result){
                		 var html = "";
                		 $.each(result,function(index,item){
                			 html +='<option value="'+item.consumeId+'">'+item.consumeName+'</option>';
                		 });
                	 }
                	 
                	 $('.consumeId').append("<option value=''>请选择</option>");
                     $('.consumeId').append(html);
                     $('.selectpicker').selectpicker({
                         size : 10
                     });
                     $('.selectpicker').selectpicker('refresh');
                 }
        	});
        },
        /**
		 * 查询下拉框消费渠道选项
         */
		queryConsumeChannelInfo : function(){
        	$.ajax({
				url : '/consumeChannel/findConsumeChannelInfoList',
				dataType : 'json',
				data : {timestamp:new Date()} ,
				type : 'POST' ,
				contentType : 'application/x-www-form-urlencoded' ,
				success : function (data) {
                    var result = data.resultData;
                    if(data.resultCode >=0 && result){
                        var html = "";
                        $.each(result,function(index,item){
                            html +='<option value="'+item.channel_code+'">'+item.channel_name+'</option>';
                        });
					}
                    $('#qConsumeChannel,#addChannelCode').append("<option value=''>请选择</option>");
                    $('#qConsumeChannel,#addChannelCode').append(html);
                    $('.selectpicker').selectpicker({
                        size : 10
                    });
                    $('.selectpicker').selectpicker('refresh');
                }
			});
		},
        
        /**
         * 查询消费明细
         */
        queryConsumeDetailInfo:function(page,pageNo){
        	var num=0;
            if(pageNo){
                $("#currentPage").val(pageNo);
                num=parseInt(pageNo-1)*10;
            }
            
            var totalMoney=0;
        	var qUserName=$('#qUserName').val();
        	var qConsumeId = $('#qConsumeId').val();
        	var startTime = $('#startTime').val();
        	var endTime = $('#endTime').val();
        	
        	if((startTime == '' || startTime == null) || (endTime ==null || endTime == '')){
        		/*$(".warning.queryUser").text("请选择查询日期");
            	$('.alert-warning.queryUser').slideDown(500).delay(2000).slideUp(1000);
        		return false;*/
        		//如果没有选择,则走默认当天
        		$('#startTime').val(signup.getNowFormatDate());
        		$('#endTime').val(signup.getNowFormatDate());
        	}

        	var diffDay = signup.GetDateDiff(startTime,endTime,"day");
        	if( diffDay > 30){
        		$(".warning.queryUser").text("查询时间不能超过1个月");
            	$('.alert-warning.queryUser').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	
        	var params = $('#consumeForm').serialize()+'&'+$.param({timestamp:new Date().getTime()});
        	$.ajax({
        		 url:'/consumeDetail/findConsumeDetailInfoList',
                 dataType:'json',
 //              data:{qUserName:qUserName,qConsumeId:qConsumeId,startTime:startTime,endTime:endTime,timestamp:new Date()},
                 data:params,
                 type:'post',
                 contentType:'application/x-www-form-urlencoded',
                 success:function (data) {
                	 var result = data.resultData;
                	 var html = "";
                	 if(data.resultCode >=0 && result){
                		 
                		 $.each(result.consumeDetailInfoList,function (index, item) {
                			 html += "<tr>" +
                			 '<td class="text-center" >'+parseInt(num+1+index)+'</td>'+
                			 '<td class="text-center" >'+item.userName+'</td>'+
                			 '<td class="text-center" >'+item.consume_time+'</td>'+
                			 '<td class="text-center" >'+item.weekDay+'</td>'+
                			 '<td class="text-center" >'+item.consumeName+'</td>'+
                			 '<td>'+'消费'+'</td>'+
                			 '<td>'+item.consume_money+'</td>'+
							 '<td>'+item.consumeChannelInfo.channel_name+'</td>'+
                			 '<td>'+(item.remark == undefined ? '':item.remark )+'</td>'+
                			 '<td>'+item.update_time+'</td>'+
                			 '<td><button class="btn btn-link" id="updateConsumeDetailA" value="'+item.id+'">修改</button></td>'+
                			 		"</tr>";
                			 
                			 totalMoney += item.consume_money;
                		 });
                		 html += '<tr><td colspan="5"></td><td colspan="1">小计: </td>'+
                		 '<td colspan="1" style="text-align:center;">'+totalMoney.toFixed(2)
                		 +'</td><td colspan="4"></td></tr>';
                		 
                		 html += '<tr><td colspan="5"></td><td colspan="1" style="text-align:center;color:red;font-weight:bold;font-size:16px;">合计: </td>'+
                		 '<td colspan="1" style="text-align:center;color:red;font-weight:bold;font-size:16px;">'+result.decimal
                		 +'</td><td colspan="4"></td></tr>';
                		 
                		 var totalRows=data.resultCode;
                         totalRows = totalRows == 0 ?1 :totalRows ;
                         var pageHtml=page.pageWithUrl('/consumeDetail/findConsumeDetailInfoList',pageNo,totalRows);
                         
                         $("#page").html(pageHtml);
                         $("#page nav").removeAttr("style");
                         signup.pageClick(page);
                	 }else{
                		 html+="<tr name='trDataBody'><td colspan='11' align='center'><span style='color:#ff0000;' >"
                             +"没有查询合适条件的数据</span></td></tr>";
                         $("#page").hide();
                	 }
                	 if(html ==""){
                         html+="<tr name='trDataBody'><td colspan='11' align='center'><span style='color:#ff0000;' >"
                             +"没有查询合适条件的数据</span></td></tr>";
                         $("#page").hide();
                     }

                     $('#consumeDetailInfoBody').html(html);
                 },
                 error:function (data) {
                     var html ="<tr name='trDataBody'><td colspan='11' align='center'><span style='color:#ff0000;' >"
                         +"网络异常</span></td></tr>";
                     $("#consumeDetailInfoBody").html(html);
                 }
        	});
        },
        
        saveConsumeDetailFun:function(page){
        	var userName = $('#userName').val();
        	var consumeId = $('#consumeId').val();
        	var consume_time = $('#consume_time').val();
        	var consume_money = $('#consume_money').val();
        	var remark = $('#remark').val();
        	var addChannelCode = $('#addChannelCode').val();

        	if(userName ==null || userName == '' ||userName == undefined){
        		$(".warning_p.addConsume").text("用户名必须填写");
            	$('.alert-warning.addConsume').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	
        	if(consumeId ==null || consumeId == '' ||consumeId == undefined){
        		$(".warning_p.addConsume").text("消费类型必须选择");
            	$('.alert-warning.addConsume').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}

            if(addChannelCode ==null || addChannelCode == '' ||addChannelCode == undefined){
                $(".warning_p.addConsume").text("消费渠道必须选择");
                $('.alert-warning.addConsume').slideDown(500).delay(2000).slideUp(1000);
                return false;
            }
        	
        	if(consume_time ==null || consume_time == '' ||consume_time == undefined){
        		$(".warning_p.addConsume").text("消费时间必须选择");
            	$('.alert-warning.addConsume').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	
        	if(consume_money ==null || consume_money == '' || consume_money == undefined){
        		$(".warning_p.addConsume").text("消费金额必须填写");
            	$('.alert-warning.addConsume').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	
        	var params={userName:userName,consumeId:consumeId,consume_time:consume_time,consume_money:consume_money,
				remark:remark,channel_code:addChannelCode};
        	var jsonParams = JSON.stringify(params);
        	$.ajax({
        		url:'/consumeDetail/saveConsumeDetailInfo?timestamp='+new Date(),
                dataType:'json',
                type:'POST',
                contentType:'application/json',
                data:jsonParams,
                success:function (data) {
                	var result=data.resultData;
                	if(data.resultCode >=0 && result){
                		$("#addConsumeDetailModal").modal("toggle");
                		signup.queryConsumeDetailInfo(page,1);
                	}else{
                		$(".warning_p.addConsume").text(data.resultMessage);
                    	$('.alert-warning.addConsume').slideDown(500).delay(2000).slideUp(1000);
                	}
                },
                error:function(){
                	
                }
        	});
        },
        
        updateConsumeDetail:function(detailId){
        	$.ajax({
        		url:'/consumeDetail/getConsumeDetailInfoById?timestamp='+new Date(),
                dataType:'json',
                type:'POST',
                contentType:'application/x-www-form-urlencoded',
                data:{id:detailId},
                success:function (data) {
                	var result=data.resultData;
                	if(data.resultCode >=0 && result){
                		signup.updateConsumeHtml(result.consumeDetailInfo,result.consumeTypeList,result.channelInfoList);
                	}
                }
        	});
        },
        
        updateConsumeHtml:function(consumeDetailInfo,consumeTypeInfo,consumeChannelInfo){
        	$("body").find("#updateConsumeDetailModal").remove();
        	
        	var optionHtml = "";
        	$.each(consumeTypeInfo,function(index,item){
        		if(consumeDetailInfo.consumeId == item.consumeId){
        			optionHtml+="<option value='"+item.consumeId+"' selected>"+item.consumeName+"</option>";
        		}else{
        			optionHtml+="<option value="+item.consumeId+">"+item.consumeName+"</option>";
        		}
        	});
        	var channelOptionHtml = "";
        	$.each(consumeChannelInfo,function (index, item) {
				if (consumeDetailInfo.channel_code == item.channel_code){
                    channelOptionHtml +="<option value='"+item.channel_code+"' selected>"+item.channel_name+"</option>";
				}else{
                    channelOptionHtml +="<option value='"+item.channel_code+"'>"+item.channel_name+"</option>";
				}
            });
        	var modalHtml =
        	'<div class="modal fade in" id="updateConsumeDetailModal" role="dialog" aria-labelledby="myModalLabel" >'+
        		'<div class="modal-dialog" style="width: 60%;" role="document">'+
        			'<div class="modal-content">'+
        				'<div class="modal-header">'+
        					'<button type="button" class="close" data-dismiss="modal">'+
        						'<span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'+
        						'<h4 class="modal-title" style="font-family: \'Arial Black\';font-weight: bold;font-size: 20px;">更新消费详情</h4>'+
        				'</div>'+
        				'<div class="modal-body" style="width: 100%;">'+
        					'<div class="alert alert-success updateConsume" role = "alert" style="display: none">'+
        						'<button class="close"  data-dismiss="alert" type="button" >&times;</button>'+
        						'<p class="success_p updateConsume"></p>'+
        					'</div>'+
        					'<div class="alert alert-warning updateConsume" role="alert" style="display: none">'+
        						'<button class="close"  data-dismiss="alert" type="button" >&times;</button>'+
        						'<p class="warning_p updateConsume"></p>'+
        					'</div>'+
        					'<form class="form-horizontal" id="addConsumeForm">'+
        					'<input type="hidden" name ="updateId" id="updateId" value="'+consumeDetailInfo.id+'"/>'+
        					'<div class="row" style="border:0px red solid;text-center:center;">'+
        						'<div class="col-xs-6 form-group">'+
        							'<label class="col-xs-3 control-label">用&nbsp;户&nbsp;名&nbsp;:</label>'+
        							'<div class="col-xs-9">'+
	        							'<div class="col-xs-10">'+
	        								'<input type="text" class="form-control" id="updateUserName" name="updateUserName" placeholder="请输入用户名" value="'+consumeDetailInfo.userName+'"/>'+
	        							'</div>'+
        							'</div>'+
        						'</div>'+
        						'<div class="col-xs-6 form-group">'+    
        							'<label class="col-xs-3 control-label">消费类型:</label>'+ 
        							'<div class="col-xs-9">'+  
                                    	'<div class="col-xs-10">'+    
                                    		'<select class="selectpicker form-control updateConsumeId" style="" data-live-search="true" data-style="btn-default" id="updateConsumeId" name="updateConsumeId">'+
                                    		  optionHtml+
                                    		'</select>'+
                                    	'</div>'+
                                    '</div>'+
                                 '</div>'+
                            '</div>'+
                            '<div class="row" style="border:0px red solid;text-center:center;">'+
                            	'<div class="col-xs-6 form-group">'+
                            		'<label class="col-xs-3 control-label">消费时间:</label>'+
                            		'<div class="col-xs-9">'+
                            			'<div class="col-xs-10">'+
                            				'<input class="datetimepicker form-control" id="updateConsume_time" type="text" name="updateConsume_time" placeholder="请输入开始时间" readonly value="'+consumeDetailInfo.consume_time+'"/>'+
                            			'</div>'+
                            		'</div>'+
                            	'</div>'+
                            	'<div class="col-xs-6 form-group">'+
                            		'<label class="col-xs-3 control-label">消费金额:</label>'+
                            		'<div class="col-xs-9">'+
                            			'<div class="col-xs-10">'+
                            				'<input type="text" class="form-control" id="updateConsume_money" name="updateConsume_money" placeholder="请输入金额" value='+consumeDetailInfo.consume_money+'>'+
                                        '</div>'+
                                    '</div>'+
                                '</div>'+
                           '</div>'+
                           '<div class="row" style="border:0px red solid;text-center:center;">'+
                        		'<div class="col-xs-6 form-group">'+
                        			'<label class="col-xs-3 control-label">&nbsp;备&nbsp;&nbsp;注&nbsp;:</label>'+
                        			'<div class="col-xs-9">'+
	                        			'<div class="col-xs-10">'+
	                        				'<input type="text" class="form-control" id="updateRemark" name="updateRemark" placeholder="请输入备注" value = "'+(consumeDetailInfo.remark == undefined ? "":consumeDetailInfo.remark)+'"/>'+
	                        			'</div>'+
	                        		'</div>'+   
	                        	'</div>'+
                				'<div class="col-xs-6 form-group">'+
									'<label class="col-xs-3 control-label">消费渠道:</label>'+
									'<div class="col-xs-9">'+
										'<div class="col-xs-10">'+
											'<select class="selectpicker form-control updateChannelCode" style="" data-live-search="true" + data-style="btn-default" id="updateChannelCode" name="updateChannelCode">'+
                								channelOptionHtml+
											'</select>'+
										'</div>'+
									'</div>'+
                				'</div>'+
	                        '</div>'+            
                         '</form>'+
                    '</div>'+
                    '<div class="modal-footer">'+
                    	'<div style="border:1px red sole;margin-right:50px;">'+
                    		'<button type="button" class="btn btn-primary" id="updateConsumeDetailBtn">修&nbsp;改</button>'+
                    			'<span class="" style="width: 20px;margin-left: 15px;"></span>'+ 
                    			'<button type="button" class="btn btn-info" data-dismiss="modal">关&nbsp;闭</button>'+
                    	'</div>'+        
                	'</div>'+
                   '</div>'+
                '</div>'+
              '</div>';
        	
        	$("body").append(modalHtml);
        	$("#updateConsume_time").datetimepicker({
             	 language :'zh-CN',
             	 format: 'yyyy-mm-dd',
             	 weekStart: 1,
             	 todayBtn : true,
             	 todayHighlight :true,
             	 minuteStep:5,
             	 minView : 2,
             	 autoclose:true
             });
        	
        	 $('.updateConsumeId,.updateChannelCode').selectpicker('refresh');
        	 $('.updateConsumeId,.updateChannelCode').selectpicker({
        		 size : 10
        	 });
        	 
        	$('#updateConsumeDetailModal').modal('toggle');
        	
        },
        
        saveUpdateConsumeDetail:function(page){
        	var userName = $('#updateUserName').val();
        	var consumeId = $('#updateConsumeId').val();
        	var consume_time = $('#updateConsume_time').val();
        	var consume_money = $('#updateConsume_money').val();
        	var remark = $('#updateRemark').val();
        	var id = $('#updateId').val();
        	var updateChannelCode = $('#updateChannelCode').val();

        	if(userName ==null || userName == '' ||userName == undefined){
        		$(".warning_p.updateConsume").text("用户名必须填写");
            	$('.alert-warning.updateConsume').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	
        	if(consumeId ==null || consumeId == '' ||consumeId == undefined){
        		$(".warning_p.updateConsume").text("消费类型必须选择");
            	$('.alert-warning.updateConsume').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}

            if(updateChannelCode ==null || updateChannelCode == '' ||updateChannelCode == undefined){
                $(".warning_p.updateConsume").text("消费渠道必须选择");
                $('.alert-warning.updateConsume').slideDown(500).delay(2000).slideUp(1000);
                return false;
            }
        	
        	if(consume_time ==null || consume_time == '' ||consume_time == undefined){
        		$(".warning_p.updateConsume").text("消费时间必须选择");
            	$('.alert-warning.updateConsume').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	
        	if(consume_money ==null || consume_money == '' || consume_money == undefined){
        		$(".warning_p.updateConsume").text("消费金额必须填写");
            	$('.alert-warning.updateConsume').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	
        	var params={id:id,userName:userName,consumeId:consumeId,consume_time:consume_time,
				consume_money:consume_money,remark:remark,channel_code:updateChannelCode};
        	var jsonParams = JSON.stringify(params);
        	
        	$.ajax({
        		url:'/consumeDetail/updateConsumeDetailInfo?timestamp='+new Date(),
                dataType:'json',
                type:'POST',
                contentType:'application/json',
                data:jsonParams,
                success:function (data) {
                	var result=data.resultData;
                	if(data.resultCode >=0 && result){
                		$("#updateConsumeDetailModal").modal("toggle");
                		signup.queryConsumeDetailInfo(page,1);
                	}else{
                		$(".warning_p.updateConsume").text(data.resultMessage);
                    	$('.alert-warning.updateConsume').slideDown(500).delay(2000).slideUp(1000);
                	}
                }
        	});
        },
        
      //初始化fileinput控件（第一次初始化）
        initFileInput:function(ctrlName, uploadUrl,page) {    
            var control = $('#' + ctrlName); 

            //初始化上传控件的样式
            control.fileinput({
                language: 'zh', //设置语言
                uploadUrl: uploadUrl, //上传的地址
                uploadExtraData:{id : 1,userName:"李青"},  //要上传的数据
                allowedFileExtensions: ['xls'],//接收的文件后缀
                showUpload: true, //是否显示上传按钮
                showCancel:true,	//是否显示取消按钮
                showCaption: false,//是否显示标题
                browseClass: "btn btn-primary", //按钮样式     
                //dropZoneEnabled: false,//是否显示拖拽区域
                //minImageWidth: 50, //图片的最小宽度
                //minImageHeight: 50,//图片的最小高度
                //maxImageWidth: 1000,//图片的最大宽度
                //maxImageHeight: 1000,//图片的最大高度
                //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
                //minFileCount: 0,
                maxFileCount: 1, //表示允许同时上传的最大文件个数
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
                     $("#uploadConsumeDetail").modal("toggle");
                     signup.queryConsumeDetailInfo(page,1);
                 }else{
                     $(".uploadConsume_p").text(dataText.resultMessage);
             		 $('.uploadConsume_div').slideDown(500).delay(2000).slideUp(1000);
                 }
            	 control.fileinput("clear");
            	 control.fileinput("reset");
            });
            
        },

        initDateTimePicker:function(){
	        	$("#startTime").datetimepicker({
	           	 language :'zh-CN',
//	           	 format: 'yyyy-mm-dd hh:ii:ss',
	           	format: 'yyyy-mm-dd',
	           	 weekStart: 1,
	           	 todayBtn : true,
	           	 todayHighlight :true,
	           	 minuteStep:5,
	           	 minView : 2,
	           	 autoclose:true
	           	 
	           }).on("click",function(){  
	               $("#startTime").datetimepicker("setEndDate",$("#endTime").val());  
	           });
	           
	           $("#endTime").datetimepicker({
	          	 language :'zh-CN',
//	          	 format: 'yyyy-mm-dd hh:ii:ss',
	          	format: 'yyyy-mm-dd',
	          	 weekStart: 1,
	          	 todayBtn : true,
	          	 todayHighlight :true,
	          	 minuteStep:5,
	          	 minView : 2,
	          	 autoclose:true
	          	 
	          }).on("click",function(){  
	              $("#endTime").datetimepicker("setStartDate",$("#startTime").val());  
	          });
	           
	           $(document).on("click","#endTime",function(){  
	               $('#endTime').datetimepicker('show');  
	               $('#startTime').datetimepicker('hide');  
	           });  
	         
	   	    $(document).on("click","#startTime",function(){  
	   	        $('#startTime').datetimepicker('show');  
	   	        $('#endTime').datetimepicker('hide');  
	   	    });  
	   	    
	   	 $("#consume_time").datetimepicker({
          	 language :'zh-CN',
          	 format: 'yyyy-mm-dd',
          	 weekStart: 1,
          	 todayBtn : true,
          	 todayHighlight :true,
          	 minuteStep:5,
          	 minView : 2,
          	 autoclose:true
          	 
          });
        },
        
        clearAddText : function(addText){
        	if(addText == 'add'){
        		 $('#userName').val('');
            	 $('#consumeId').selectpicker('val','');
                 $('#consume_time').val('');
                 $('#consume_money').val('');
                 $('#remark').val('');
        	}else{
        		$('#qUserName').val('');
                $('#qConsumeId').selectpicker('val','');
                $('#startTime').val('');
                $('#endTime').val('');
        	}
        },
        
         GetDateDiff : function(startTime, endTime, diffType) {
            //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式 
            startTime = startTime.replace(/\-/g, "/");
            endTime = endTime.replace(/\-/g, "/");
            //将计算间隔类性字符转换为小写
            diffType = diffType.toLowerCase();
            var sTime =new Date(startTime); //开始时间
            var eTime =new Date(endTime); //结束时间
            //作为除数的数字
            var timeType =1;
            switch (diffType) {
                case"second":
                    timeType =1000;
                break;
                case"minute":
                    timeType =1000*60;
                break;
                case"hour":
                    timeType =1000*3600;
                break;
                case"day":
                    timeType =1000*3600*24;
                break;
                default:
                break;
            }
            return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(timeType));
        },
        
      //获取当前时间，格式YYYY-MM-DD
        getNowFormatDate :function (date,num) {
        	if(date == null || num == null){
        		var date = new Date();
        		 var seperator1 = "-";
                 var year = date.getFullYear();
                 var month = date.getMonth() + 1;
                 var strDate = date.getDate();
                 if (month >= 1 && month <= 9) {
                     month = "0" + month;
                 }
                 if (strDate >= 0 && strDate <= 9) {
                     strDate = "0" + strDate;
                 }
                 var currentdate = year + seperator1 + month + seperator1 + strDate;
                 return currentdate;
        	}else{
        		 var seperator1 = "-";
                 var year = date.getFullYear();
                 var month = date.getMonth() + 1;
                 var strDate = date.getDate()+num;
                 if (month >= 1 && month <= 9) {
                     month = "0" + month;
                 }
                 if (strDate >= 0 && strDate <= 9) {
                     strDate = "0" + strDate;
                 }
                 var currentdate = year + seperator1 + month + seperator1 + strDate;
                 return currentdate;
        	}
           
        },
        
        pageClick : function(page){
            var element = $("#page nav li:not(.disabled) a");
            $(element).each(function(){
                var url=$(this).attr("href");
                if(url != "#"){
                    var pageNo= url.substr(url.indexOf("pageNo=") + 7 ,url.length);
                    //绑定click事件
                    $(this).click(function () {
                    	signup.queryConsumeDetailInfo(page,parseInt(pageNo));
                        return false;
                    });
                }
            });
        }
    };
    return signup;
});