require.config({
    baseUrl : '../../js/',
    shim:{
        'bootstrap' :{
            deps:['jquery']
        },
        'easyui':{
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
        'easyui_table':{
            deps:['jquery','bootstrap'],
            exports:'easyui_table'
        },
        "datetimepicker":{
            deps:["jquery","bootstrap"]
        },
        "moment":{
            deps:["jquery","bootstrap"]
        },
        "defaults":{
            deps:["jquery","bootstrap"]
        }
    },
    paths:{
        'jquery' : 'common/jquery-1.9.1.min',
        'bootstrap':'bootstrap/js/bootstrap.min',
        'easyui':'easyui/jquery-easyui-1.7.0/jquery.easyui.min',
        'ajaxfileupload':'common/ajaxfileupload',
        'theme':'bootstrap/js/theme',
        'My97DatePicker':'common/My97DatePicker/WdatePicker',
        'datetimepicker':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.min',
        'moment':'common/bootstrap-datetimepicker/moment-with-locales',
        'page' : 'common/pagegenerator',
        'easyui_table':'myjs/easyui/easyui_table'
    },
    waitSeconds: 0
});

require(['jquery','bootstrap','easyui_table','ajaxfileupload','theme',"moment","page",'My97DatePicker','datetimepicker','easyui'],
    function ($, bootstrap, easyui_table,ajaxfileupload,theme,moment,page,My97DatePicker,datetimepicker,easyui) {

        easyui_table.messsage();

        easyui_table.getLoginUser();

        easyui_table.getOnlineUserList(page);

        $("#queryOnlieUser").click(function(){
            easyui_table.getOnlineUserList(page);
        });

        $(document).on("click",'#btn_link',function(){
            easyui_table.kickout($(this),page);
        });

        easyui_table.getLocalSessionId();

        easyui_table.datagrid();
    });

define(['easyui_table'],function(easyui_table){
    var easyui_table = {
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
                            if(item.menuId == 'easyui_table'){
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
        getLocalSessionId: function(){
            $.ajax({
                url:'/session/getLocalSessionId',
                data:'',
                dataType:'json',
                type:'post',
                contentType:'application/x-www-form-urlencoded',
                success:function(data){
                    //	console.log(data);
                    $("#hid_sessionId").val(data.sessionId);
                }
            });
        },
        getOnlineUserList : function(page,pageNo){
            var num=0;
            if(pageNo){
                $("#currentPage").val(pageNo);
                num=parseInt(pageNo-1)*10;
            }
            $.ajax({
                url : '/session/findOnlineUserList' ,
                dataType:'json',
                data : $("#formDate").serialize(),
                type:'post',
                contentType:'application/x-www-form-urlencoded',
                success:function(data){
                    var result = data.resultData;
                    var html = "";
                    if(data.resultCode > 0 && result ){
                        var onlineList = result.onlineList;
                        var sessionSize = result.sessionSize;
                        $(".span").text(sessionSize);
                        $.each(onlineList,function(index,item){
                            var userInfo =item.userInfo;
                            html += '<tr class="text-center">'
                                + '<td>'+parseInt(num+1+index)+'</td>'
                                + '<td>'+item.sessionId+'</td>'
                                + '<td>'+item.host+'</td>'
                                + '<td>'+userInfo.userName+'</td>'
                                + '<td>'+userInfo.realName+'</td>'
                                + '<td>' +item.startTimestamp+'</td>'
                                + '<td>'+item.lastAccessTime +'</td>'
                                + '<td >';
                            if($("#hid_sessionId").val() == item.sessionId ){
                                html += '<button type="button" id="btn_link" class="btn btn-link btn-xs" attse="'+item.sessionId+'" disabled="disabled" >删除</button></td></tr>';
                            }else{
                                html += '<button type="button" id="btn_link" class="btn btn-link btn-xs" attse="'+item.sessionId+'">删除</button></td></tr>';
                            }
                        });
                        var totalRows=data.resultCode;console.log(totalRows);
                        totalRows=totalRows ==0 ?1 :totalRows ;
                        var pageHtml=page.pageWithUrl('/session/findOnlineUserList',pageNo,totalRows);

                        $("#page").html(pageHtml);
                        $("#page nav").removeAttr("style");
                        easyui_table.pageClick(page);
                    }else{
                        html+="<tr name='trDataBody'><td colspan='8' align='center'><span style='color:#ff0000;' >"
                            +"没有查询合适条件的数据</span></td></tr>";
                        $("#page").hide();
                    }

                    $("#onlineBody").html(html);
                },
                error:function(){
                    html+="<tr name='trDataBody'><td colspan='8' align='center'><span style='color:#ff0000;' >"
                        +"网络异常</span></td></tr>";
                    $("#onlineBody").html(html);
                }
            });
        },
        kickout:function(obj,page){
            var sessionId =  obj.attr('attse');
            $.ajax({
                url : '/session/kickoutSession' ,
                dataType:'json',
                type:'post',
                data:{sessionId:sessionId},
                contentType:'application/x-www-form-urlencoded',
                success:function(data){
                    if(data.status == 200){
                        easyui_table.getOnlineUserList(page,1);
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
        },
        pageClick : function(page){
            var element = $("#page nav li:not(.disabled) a");
            $(element).each(function(){
                var url=$(this).attr("href");
                if(url != "#"){
                    var pageNo= url.substr(url.indexOf("pageNo=") + 7 ,url.length);
                    //绑定click事件
                    $(this).click(function () {
                        easyui_table.getOnlineUserList(page,parseInt(pageNo));
                        return false;
                    });
                }
            });
        },

        datagrid : function (){
            $('#tab_node').datagrid({
                url: "/session/findOnlineUserListByEasyui",
                pagination: true,//允许分页
                fitColumns: true ,
                rownumbers: true,//行号
                singleSelect: false,//只选择一行
                pageSize: 10,//每一页数据数量
                checkOnSelect: false, //true，当用户点击行的时候该复选框就会被选中或取消选中。
                selectOnCheck: true, //true，单击复选框将永远选择行。
                pageList: [5, 10, 15, 20, 25],
                toolbar: [{
                    iconCls: 'icon-edit',
                    text : '编辑',
                    handler: function(){alert('edit')}
                },'-',{
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function(){easyui_table.deleteRowData();}
                }],
                columns:[[
                    {field:'sessionId',title:'会话ID',width:110},
                    {field:'host',title:'IP地址',width:80},
                    {field:'userName',title:'登录账号',width:60},
                    {field:'realName',title:'登录用户名',width:60},
                    {field:'lastAccessTime',title:'登录时间',width:80},
                    {field:'操作',title:'操作',width:40,
                        formatter: function(value,row,index){
                            return "<a href=''>删除</a>";
                        }
                    }
                ]]
            });
        },
        deleteRowData : function (id) {
            $.messager.alert('提示信息','删除','info');
        }
    };
    return easyui_table;
});