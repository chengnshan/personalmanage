require.config({
    baseUrl : '../../../js/',
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
        'menu_manage':{
            deps:['jquery','bootstrap','theme'],
            exports:'menu_manage'
        },
        "datetimepicker":{
            deps:["jquery","bootstrap"]
        },
        "moment":{
            deps:["jquery","bootstrap"]
        },
        'bootstrap_treeview':{
        	deps:["jquery","bootstrap"]
        },
        'CustCommonJs':{
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
        'selectpicker':'common/bootstrap-select-1.12.4/js/bootstrap-select.min',
        'My97DatePicker':'common/My97DatePicker/WdatePicker',
        'datetimepicker':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.min',
        'moment':'common/bootstrap-datetimepicker/moment-with-locales',
        'bootstrap_treeview':'bootstrap/bootstrap-treeview/bootstrap-treeview.min',
        'CustCommonJs':'common/CustCommonJs',
        'menu_manage':'myjs/childrenJs/menu_manage'
    },
	waitSeconds: 0
});

require(['jquery','bootstrap','menu_manage','ajaxfileupload','theme','selectpicker',"moment",'My97DatePicker'
	,'datetimepicker','bootstrap_treeview','CustCommonJs'],
    function ($, bootstrap, menu_manage,ajaxfileupload,theme,selectpicker,moment,My97DatePicker
    		,datetimepicker,bootstrap_treeview,CustCommonJs) {
// 		$('#nav').load("../top.html");
	
		$.comleteAjax();
		$(".selectpicker").selectpicker({
			noneSelectedText : '请选择'	//默认显示内容
		});
        menu_manage.messsage();

        menu_manage.getLoginUser();

        menu_manage.getTreeData();
        
        $(document).on('click','#addMenuInfo',function(){
        	menu_manage.getMenuInListfo();
        	$('#addMenuModal').modal('toggle');
        });
        
        $(document).on('click','#addSuccBtn',function(){
        	menu_manage.saveMenuInfo();
        });
        
        $(document).on('click','#updateMenuInfo',function(){
     //   	console.log($('#tree').treeview('getSelected'));
        	//获取选中的菜单id
        	var selectMenuId = $('#tree').treeview('getSelected');
        	if(selectMenuId === undefined || selectMenuId.length <= 0){
        		alert("请选择一个菜单.");
        		return false;
        	}
        	if(selectMenuId && selectMenuId.length>0){
        		var selectMenuId = $(selectMenuId)[0].value;
        		menu_manage.getMenuInfoById(selectMenuId);
        	}
        });
        
        $(document).on('click','#updateSuccBtn',function(){
        	menu_manage.updateMenuInfo();
        });
});

define(['menu_manage'],function(menu_manage){
    var menu_manage = {
        messsage:function () {
 //           console.log("加载了!");
        },
        /**
         * 获取获取用户并加载菜单
         */
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
                                '<ul class="nav navbar-nav" style="width: 100%;display: block;">';

                            $.each(item.childrenMenus,function (index1,child) {
                             //   console.log(child.menuId);
                                if(child.menuId == 'menu_manage'){
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
        /**
         * 获取菜单树
         */
        getTreeData:function(){
        	$.ajax({
        		url:'/menu/findMenuList',
        		dataType:'json',
        		type:'POST',
        		contentType:'application/x-www-form-urlencoded',
        		data:$.param({menuLevel:1})+"&"+$.param({queryOneLevel:"queryOneLevel"}),
        		success:function(data){
        			var result = data.resultData;
                    if(data.resultCode >= 0 && result){
                    	
                    	 $('#tree').treeview({
                             data: menu_manage.getTree(result),	//data属性是必须的，是一个对象数组
                        //   borderColor : "#000000"  //容器边框颜色
                        //     multiSelect : true ,	//是否可以同时选择多个节点。
                       //      showCheckbox : true,    //是否在节点上显示checkboxes
                             uncheckedIcon: "glyphicon glyphicon-unchecked", //未选中的复选框时显示的图标，可以与showCheckbox一起使用
                             enableLinks: false, //是否将节点文本呈现为超链接。前提是在每个节点基础上，必须在数据结构中提供href值。
                         //   showTags: true, //是否显示每个节点右侧的标记。前提是这个标记必须在每个节点基础上提供数据结构中的值。
                             highlightSearchResults: true,
                             emptyIcon: "glyphicon"	, //当节点没有子节点的时候显示的图标
                             // 一个节点被选中事件,填充右边的值
                             onNodeSelected : function(event, node){
                            	 var menuId = node.value;
                            	 menu_manage.onSelectNode(node.value);
                             }
                         });
                    }
        		}
        	});
        },
        /**
         * 初始化树控件
         */
        getTree:function(menuList){
        //	console.log(menuList);
        	var nodes = new Array();
        	$.each(menuList,function(index,item){
        		node = new Object();
        		node.text=item.menuName;
        		node.value=item.menuId;
        		/*node.selectable = false;*/
        		
        		if(item.childrenMenus && item.childrenMenus.length > 0 ){
        			node.selectable = false;  //有子菜单时,不让选择
        			state = new Object();
        			state.expanded =true;
        			node.state = state
        			var chNodes = new Array();
        			$.each(item.childrenMenus,function(chIndex,chItem){
        				chNode = new Object();
            			chNode.text = chItem.menuName;
            			chNode.value = chItem.menuId;
            			chNodes[chIndex]=chNode;
        			});
        			node.nodes= chNodes;
        		}
        		
        		nodes[index]=node;
        	});
       // 	console.log(nodes);
        	 //节点上的数据遵循如下的格式：
            var tree = [{
                text: "菜单列表", //节点显示的文本值  string
                icon: "glyphicon glyphicon-play-circle", //节点上显示的图标，支持bootstrap的图标  string
                selectedIcon: "glyphicon glyphicon-ok", //节点被选中时显示的图标       string
//                color: "#000000", //节点的前景色      string
//                backColor: "#0000", //节点的背景色      string
                href: "http://www.baidu.com", //节点上的超链接
                selectable: false, //标记节点是否可以选择。false表示节点应该作为扩展标题，不会触发选择事件。  string
                state: { //描述节点的初始状态    Object
                    checked: false, //是否选中节点
                    /*disabled: true,*/ //是否禁用节点
                    expanded: true, //是否展开节点
                    selected: false //是否选中节点
                },
                tags: ['标签信息1', '标签信息2'], //向节点的右侧添加附加信息（类似与boostrap的徽章）    Array of Strings
                nodes: nodes
            }];

            return tree;
        },
        
        //修改菜单按钮时，根据menuId查询菜单详情
        getMenuInfoById:function(menuId){
        	$.ajax({
        		url:'/menu/getMenuInfoByMenuId',
        		dataType:'json',
        		type:'POST',
        		contentType:'application/x-www-form-urlencoded',
        		data:$.param({menuId:menuId}),
        		success:function(data){
        			var result = data.resultData;
                    if(data.resultCode >= 0 && result){
        //            	console.log(result);
                    	menu_manage.updateMenuInfoHtml(result);
                    }
                }
        	});
        },
        
        //拼接菜单详情框
        updateMenuInfoHtml:function(result){
        	$("body").find("#updateMenuModal").remove();
        	
        	var optionHtml = "";
        	//加载数据到下拉框
        	$.ajax({
        		url:'/menu/findMenuList',
        		dataType:'json',
        		type:'POST',
        		contentType:'application/x-www-form-urlencoded',
        		async:false, 
        		data:$.param({menuLevel:1}),
        		success:function(data){
        			var resultList = data.resultData;
        			optionHtml += "<option value=''>无</option>";
        			if(data.resultCode >= 0 && resultList){
                    	$.each(resultList,function(index,item1){
                    		if(result.parentMenuId == item1.menuId){
                    			optionHtml +="<option value="+item1.menuId+" selected>"+item1.menuName+"</option>";
                    		}else{
                    			optionHtml +="<option value="+item1.menuId+">"+item1.menuName+"</option>";
                    		}
                    	});
                    }
        		}
        	});
        	
        	/**
        	 * 菜单是否可用
        	 */
        	var enableHtml ="";
        	if(result.enable == 1){
        		enableHtml += "<option value='1' selected>是</option>";
        		enableHtml += "<option value='0'>否</option>";
        	}else{
        		enableHtml += "<option value='1'>是</option>";
        		enableHtml += "<option value='0' selected>否</option>";
        	}
        	
        	var modelHtml= 
            	'<div class="modal fade" id="'+'updateMenuModal'+'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
            	' <div class="modal-dialog" style="width: 70%;">'+
            	'	<div class="modal-content">'+
            	'		<div class="modal-header">'+
            	'			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
            	'			<h4 class="modal-title" id="myModalLabel" style="font-family: \'Arial Black\';font-weight: bold;font-size: 20px;">修改菜单</h4>'+
            	'		</div>'+
            	'		<div class="modal-body" style="width: 100%;">'+
            	'			<div class="alert alert-success updateMenuAlert" role = "alert" style="display: none">'+
            	'				<button class="close"  data-dismiss="alert" type="button" >&times;</button>'+
            	'				<p class="success_p updateMenuAlert"></p>'+
            	'			</div>'+
            	'			<div class="alert alert-warning updateMenuAlert" role = "alert" style="display: none">'+
            	'				<button class="close"  data-dismiss="alert" type="button" >&times;</button>'+
            	'				<p class="warning_p updateMenuAlert"></p>'+
            	'			</div>'+
            	'			<form class="form-horizontal" id="updateUserForm">'+
            	'				<input type="hidden" name="updateId"  id="updateId" value ="'+result.id+'" />'+
            	'				<div class="row" style="border:0px red solid;text-center:center;">'+
            	'					<div class="col-xs-6 form-group">'+
            	'						<label class="col-xs-3 control-label">菜单名称:</label>'+
            	'						<div class="col-xs-9">'+
            	'							<div class="col-xs-10">'+
            	'								<input type="text" class="form-control" id="updateMenuName" name="updateMenuName" value="'+result.menuName+'">'+
            	'							</div>'+
            	'						</div>'+
            	'					</div>'+
            	'					<div class="col-xs-6 form-group">'+
            	'						<label class="col-xs-3 control-label">菜&nbsp;单&nbsp;&nbsp;&nbsp;Id:</label>'+
            	'						<div class="col-xs-9">'+
            	'							<div class="col-xs-10">'+
            	'								<input type="text" class="form-control" id="updateMenuId" readonly="readonly" name="updateMenuId" value="'+result.menuId+'">'+
            	'							</div>'+
            	'						</div>'+
            	'					</div>'+
            	'				</div>'+
            	'				<div class="row" style="border:0px red solid;text-center:center;">'+
            	'					<div class="col-xs-6 form-group">'+
            	'						<label class="col-xs-3 control-label">父&nbsp;菜&nbsp;单:</label>'+
            	'						<div class="col-xs-9">'+
            	'							<div class="col-xs-10">'+
            	'								<select class="selectpicker form-control updateSelectpicker" multiple data-live-search="true" data-style="btn-default" id="parentMenuId" name="parentMenuId">'+
            	'								'+optionHtml+
            	'								</select>'+
            	'							</div>'+
            	'						</div>'+
            	'					</div>'+
            	'					<div class="col-xs-6 form-group">'+
            	'						<label class="col-xs-3 control-label">菜单URL:</label>'+
            	'						<div class="col-xs-9">'+
            	'							<div class="col-xs-10">'+
            	'								<input type="text" class="form-control" id="updateMenuUrl" name="updateMenuUrl" value="'+result.menuUrl+'">'+
            	'							</div>'+
            	'						</div>'+
            	'					</div>'+
            	'				</div>'+
            	
            	'				<div class="row" style="border:0px red solid;text-center:center;">'+
            	'					<div class="col-xs-6 form-group">'+
            	'						<label class="col-xs-3 control-label">菜单级别:</label>'+
            	'						<div class="col-xs-9">'+
            	'							<div class="col-xs-10">'+
            	'								<input type="text" class="form-control" id="updateMenuLevel" name="updateMenuLevel" value="'+result.menuLevel+'">'+
            	'							</div>'+
            	'						</div>'+
            	'					</div>'+
            	'					<div class="col-xs-6 form-group">'+
            	'						<label class="col-xs-3 control-label">菜单是否可用:</label>'+
            	'						<div class="col-xs-9">'+
            	'							<div class="col-xs-10">'+
            	'								<select class="selectpicker form-control updateSelectpicker" data-style="btn-default" id="updateEnable" name="updateEnable">'+
            	'								'+enableHtml+
            	'								</select>'+
            	'							</div>'+
            	'						</div>'+
            	'					</div>'+
            	'				</div>'+
            	
            	'			</form>'+
            	'		</div>'+
            	'		<div class="modal-footer">'+
            	'			<div style="margin-right:50px;">'+
            	'		 		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>'+
            	'				<span class="" style="width: 20px;margin-left: 15px;"></span>'+
            	'		 		<button type="button" class="btn btn-primary" id="updateSuccBtn">修改</button>'+
            	'			</div>'+
            	'		</div>'+
            	'	</div>'+
            	' </div>'+
            	'</div>';
        	
        	$("body").append(modelHtml);
        	
        	$(".selectpicker").selectpicker({
    			noneSelectedText : '无'	//默认显示内容
    		});
        	$(".updateSelectpicker").selectpicker('refresh');
        	$(".updateSelectpicker").selectpicker({'size':10});
        	$("#updateMenuModal").modal('toggle');
        },
        /**
         * 获取菜单列表
         */
        getMenuInListfo : function(){
        	$.ajax({
        		url:'/menu/findMenuList',
        		dataType:'json',
        		type:'POST',
        		data:$.param({menuLevel:1})+"&"+$.param({queryOneLevel:"queryOneLevel"}),
        		contentType:'application/x-www-form-urlencoded',
        		success:function(data){
        			var result = data.resultData;
                    if(data.resultCode >= 0 && result){
                    	var html="<option>请选择</option>";
                    	$.each(result,function(index,item){
                    		html +="<option value='"+item.menuId+"'>"+item.menuName+"</option>";
                    	});
                    	$('#parentMenuId').html(html);
                    	$('.addSelectpicker').selectpicker({
                    		size : 10
                    	});
                    	$('.addSelectpicker').selectpicker('refresh');
                    }
        		}
        	});
        },
        /**
         * 添加菜单
         */
        saveMenuInfo:function(){
        	var menuName = $('#menuName').val();
        	var menuId = $('#menuId').val();
        	var parentMenuId = $('parentMenuId').val();
        	var menuUrl = $('#menuUrl').val();
        	var enable = $('#enable').val();
        	var menuLevel = $('#menuLevel').val();
        	if(menuName == null || menuName == ''){
        		$(".warning_p.add").text("菜单名称必须填写");
            	$('.alert-warning.add').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	if(menuId == null || menuId == ''){
        		$(".warning_p.add").text("菜单标识必须填写");
            	$('.alert-warning.add').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	if(menuUrl == null || menuUrl == ''){
        		$(".warning_p.add").text("菜单路径必须填写");
            	$('.alert-warning.add').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	
        	var data = $('#addMenuForm').serialize()+'&'+$.param({timestamp:new Date().getTime()});
        		$.ajax({
            		url:'/menu/insertMenuInfo',
            		dataType:'json',
            		type:'POST',
            		data:data,
            		contentType:'application/x-www-form-urlencoded',
            		beforeSend : function(jqXHR,settings){
        	//			console.log('添加菜单请求开始.');
        			},
            		success:function(data){
            			var result = data.resultData;
                        if(data.resultCode >= 0 && result){
          //              	console.log(result);
                        	$('#addMenuModal').modal('toggle');
                        	menu_manage.getTreeData();
                        }else{
                        	$(".warning_p.add").text(data.resultMessage);
                        	$('.alert-warning.add').slideDown(500).delay(2000).slideUp(1000);
                        }
                    },
                    error:function(xmlHttpRequest,errormsg,errorThrown){
                    	if(errorThrown != 'abort'){
                            //ajax被调用abort后执行的方法
                            alert('您的ajax方法被停止了');
                        }
                    }
        	});
        },
        /**
         * 节点被选中时，加载对应的数据
         */
        onSelectNode:function(nodeValue){
        //	console.log(nodeValue);
        	 $.ajax({
      		 	url:'/menu/findMenuList',
            		dataType:'json',
            		type:'POST',
            		contentType:'application/x-www-form-urlencoded',
            		data:$.param({menuId:nodeValue}),
            		success:function(data){
            		  var resultList = data.resultData;
                      if(data.resultCode >= 0 && resultList){
                    	  var result = resultList[0];
                       	 $('#menuName_detail').val(result.menuName);
                       	 $('#menuId_detail').val(result.menuId);
                       	 $('#menuUrl_detail').val(result.menuUrl);
                       	
                       	 if(result.parentMenuId != undefined || result.parentMenuId != null || result.parentMenuId != '' ){
                       		 $('#parentMenu_detail').val(result.parentMenuId);
                       	 }else{
                       		 $('#parentMenu_detail').val("无");
                       	 }
                       	
                       	 $('#menuLevel_detail').val(result.menuLevel);
                       	 $('#enable_detail').val(result.enable == 1? "可用":"不可用");
                       }
            		}
     		 });
        },
        
        /**
         * 更新菜单信息
         */
        updateMenuInfo:function(){
        	var updateMenuName = $('#updateMenuName').val();
        	var updateMenuId = $('#updateMenuId').val();
        	var parentMenuId = $('#parentMenuId').val();
        	var updateMenuUrl = $('#updateMenuUrl').val();
        	var updateMenuLevel = $('#updateMenuLevel').val();
        	var updateEnable = $('#updateEnable').val()[0];
        	var updateId = $('#updateId').val();
        	
        	if( updateMenuName ==undefined || updateMenuName == null || updateMenuName == ''){
        		$(".warning_p.updateMenuAlert").text("菜单名称必须填写");
            	$('.alert-warning.updateMenuAlert').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	if( updateMenuId ==undefined || updateMenuId == null || updateMenuId == ''){
        		$(".warning_p.updateMenuAlert").text("菜单ID必须填写");
            	$('.alert-warning.updateMenuAlert').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	if( updateMenuUrl ==undefined || updateMenuUrl == null || updateMenuUrl == ''){
        		$(".warning_p.updateMenuAlert").text("菜单URL路径必须填写");
            	$('.alert-warning.updateMenuAlert').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	if( updateMenuLevel ==undefined || updateMenuLevel == null || updateMenuLevel == ''){
        		$(".warning_p.updateMenuAlert").text("菜单级别必须选择");
            	$('.alert-warning.updateMenuAlert').slideDown(500).delay(2000).slideUp(1000);
        		return false;
        	}
        	
    //    	var params = $('#updateUserForm').serialize()+"&"+$.param({timestamp:new Date().getTime()});
        	console.log(updateEnable);
        	$.ajax({
      		 	url:'/menu/updateMenuInfo?timestamp='+new Date().getTime(),
            		dataType:'json',
            		type:'POST',
            		contentType:'application/x-www-form-urlencoded',
            		data:{menuId:updateMenuId,menuName:updateMenuName,parentMenuId:parentMenuId,menuUrl:updateMenuUrl,menuLevel:updateMenuLevel,id:updateId,enable:updateEnable},
            		success:function(data){
            		  var resultList = data.resultData;
                      if(data.resultCode >= 0 && resultList){
                    	  $("#updateMenuModal").modal('toggle');
                    	  menu_manage.getTreeData();
                       }else{
                    		$(".warning_p.updateMenuAlert").text("菜单更新失败");
                    		$('.alert-warning.updateMenuAlert').slideDown(500).delay(2000).slideUp(1000);
               		   }
            		},
            		error:function(){
            			$(".warning_p.updateMenuAlert").text("菜单更新失败,网络错误.");
                		$('.alert-warning.updateMenuAlert').slideDown(500).delay(2000).slideUp(1000);
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
    return menu_manage;
});