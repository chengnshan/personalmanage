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
        'selectpicker_zh_CN':{
            deps:['jquery','bootstrap','datetimepicker']
        },
        'bootstrap_grid':{
            deps:['jquery','bootstrap','theme'],
            exports:'bootstrap_grid'
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
        'selectpicker':'common/bootstrap-select-1.12.4/js/bootstrap-select.min',
        'selectpicker_zh_CN':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN',
        'My97DatePicker':'common/My97DatePicker/WdatePicker',
        'datetimepicker':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.min',
        'moment':'common/bootstrap-datetimepicker/moment-with-locales',
        'common_share':'myjs/common/common_share',
        'bootstrap_grid':'myjs/bootstrap_grid'
    },
	waitSeconds: 0
});

require(['jquery','bootstrap','bootstrap_grid','ajaxfileupload','theme','selectpicker',"selectpicker_zh_CN","moment",
        'My97DatePicker','datetimepicker','common_share'],
    function ($, bootstrap, bootstrap_grid,ajaxfileupload,theme,selectpicker,selectpicker_zh_CN,moment,My97DatePicker,
              datetimepicker,common_share) {
//		$('#nav').load("top.html");

        common_share.getLoginUser();

        $(document).on("click","#queryRedisDetail",function(){
            bootstrap_grid.queryBtn();
        });

});

define(['bootstrap_grid'],function(bootstrap_grid){
    var bootstrap_grid = {

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
                            if(item.menuId == 'bootstrap_grid'){
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
         *查询redis中的key和值
         */
        queryBtn:function(){
            console.log("查询redis");
            $('#redisKeyBody').html('');
            let redisKey = $('#redisKey').val();
            $.ajax({
                url:'/redis/queryRedisDetail',
                dataType:'json',
                type:'POST',
                data:{timestamp:new Date(), "redisKey": redisKey},
                contentType:'application/x-www-form-urlencoded',
                success:function(data){
                    if (data && data.resultCode >= 0){
                        let resultData = data.resultData;
                        var html = '';
                        html += "<tr>" +
                            '<td class="text-center" >'+ resultData.dataType + '</td>'+
                            '<td class="text-center" >'+ resultData.key + '</td>'+
                            '<td class="text-center" >'+ resultData.value + '</td>'+
                            '<td class="text-center" >'+ resultData.expire + '</td>'+
                            '</tr>'
                    }else {
                        html+="<tr name='trDataBody'><td colspan='4' align='center'><span style='color:#ff0000;' >"
                            +"没有查询合适条件的数据</span></td></tr>";
                    }
                    $('#redisKeyBody').append(html);
                },
                error:function (e) {
                    
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
    return bootstrap_grid;
});