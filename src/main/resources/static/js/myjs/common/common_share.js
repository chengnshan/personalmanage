define(['common_share'],function(common_share){
    var common_share = {
        getLoginUser:function(){
            $.ajax({
                url: '/admin/getLoginUser',
                dataType: 'json',
                data: '',
                contentType: 'application/x-www-form-urlencoded',
                success: function (data) {
                    var resultCode = data.resultCode;
                    if ( resultCode >= 0 ){
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
                                        '   <li><a href="'+child.menuUrl+'"><i class="'+child.classStyle+'"></i> '+child.menuName+'</a></li>' ;
                                })
                                html+= chilHtml +'</ul></li>';
                            }else{
                                html += '<li class=""><a href="'+item.menuUrl+'"><i class="'+item.classStyle+'"></i> '+item.menuName+'</a></li>';
                            }

                        });
                        $("#menu_ul").append(html);
                    }else {
                        var resultMessage = data.resultMessage;
                        alert(resultMessage);
                    }
                }
            });
        }
    };
    return common_share;
});