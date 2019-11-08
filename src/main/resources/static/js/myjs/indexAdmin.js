require.config({
    baseUrl : '../js/',
    shim:{
        'bootstrap' :{
            deps:['jquery']
        },
        'ajaxfileupload':{
            deps:['jquery','bootstrap']
        },
        'indexAdmin':{
            deps:['jquery','bootstrap'],
            exports:'indexAdmin'
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
        'My97DatePicker':'common/My97DatePicker/WdatePicker',
        'datetimepicker':'common/bootstrap-datetimepicker/bootstrap-datetimepicker.min',
        'moment':'common/bootstrap-datetimepicker/moment-with-locales',
        'common_share':'myjs/common/common_share',
        'indexAdmin':'myjs/indexAdmin'
    },
	waitSeconds: 0
});

require(['jquery','bootstrap','indexAdmin','ajaxfileupload',"moment",'My97DatePicker','datetimepicker','common_share'],
    function ($, bootstrap, indexAdmin,ajaxfileupload,moment,My97DatePicker,datetimepicker,common_share) {
//	$('#nav').load("top.html");
	
    indexAdmin.messsage();

});

define(['indexAdmin'],function(indexAdmin){
    var indexAdmin = {
        messsage:function () {
            console.log("加载了!");
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
    return indexAdmin;
});