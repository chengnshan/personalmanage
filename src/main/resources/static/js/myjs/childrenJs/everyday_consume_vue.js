//定义分页组件
var componentpage = {
    template: '#comPage',
    data() {
        return {

        }
    },
    props: ['totalrows','totalpage', 'currentpage'],
    methods: {
        comClickA(){
            this.$emit('parentfunc');
            alert(this.totalrows);
        },
        querySys(count){
            this.$emit('parentquery',count);
        }
    }
};
//菜单组件
var menucom1 = menucom();

var vue = new Vue({
    el: '#wrapper',
    data(){
        return {
            jobName:'',
            clearable: true,

            currentPage: 1,
            totalRows: 0,
            //总页数
            totalPage : 0,
            pageSize : 10 ,

            param_code:'',
            param_name:'',

            menulist: [],
            list: [],
            menu_html : '',
            loginUserNameStr : '',

            //表格当前页数据
            tableData: [],
            //默认数据总数
            totalCount: 200,
            //添加对话框默认可见性
            dialogFormVisible: false,
            //修改对话框默认可见性
            updateFormVisible: false,
            //提交的表单
            form: {
                jobName: '',
                jobGroup: '',
                triggerName: '',
                triggerGroup: '',
                jobClassName: '',
                cronExpression: '',
                jobDescription: ''
            },
            updateform: {
                id : '',
                jobName: '',
                jobGroup: '',
                triggerName: '',
                triggerGroup: '',
                jobClassName: '',
                cronExpression: '',
                jobDescription: ''
            },
            options: [{
                value: '选项1',
                label: '黄金糕'
            }, {
                value: '选项2',
                label: '双皮奶'
            }],
            value: ''
        }
    },
    mounted: function(){
        this.$http.post('/admin/getLoginUser',{}, {emulateJSON: true}).then(function (result) {
            var body = result.body;
            if (body && body.resultCode >= 0){

                var resultData = body.resultData;
                var userInfo = JSON.parse(resultData);

                var menuList = userInfo.menuListByRoleId;
                this.menulist = menuList;

                html = "";
                for (var i =0 ; i< menuList.length; i++){
                    if(menuList[i].childrenMenus.length >0 ){
                        var chilHtml = "";
                        html += '<li class="nav nav-list nav-list-expandable nav-list-expanded">' +
                            '<a><i class="fa fa-user"></i>'+menuList[i].menuName+' <span class="caret"></span></a>' +
                            '<ul class="nav navbar-nav" style="width: 100%; display: block;">';
                        for (var j =0 ; j< menuList[i].childrenMenus.length; j++){
                            var childrenMenus = menuList[i].childrenMenus;
                            console.log(childrenMenus[j].menuName);
                            chilHtml +=
                                '   <li><a href="'+childrenMenus[j].menuUrl+'">' +
                                '<i class="'+ childrenMenus[j].classStyle+'"></i> '+
                                childrenMenus[j].menuName+'</a></li>' ;
                        }
                        html+= chilHtml +'</ul></li>';
                    }else{
                        html += '<li class=""><a href="'+menuList[i].menuUrl+'"><i class="'+menuList[i].classStyle+'"></i> '+menuList[i].menuName+'</a></li>';
                    }
                }
                this.menu_html = html;
                this.loginUserNameStr = userInfo.userInfo.realName;
            }
        });

    },
    methods:{
        pageClick : function(){
            var obj = this;
            var element = $("#page nav li:not(.disabled) a");
            $(element).each(function(){
                var url=$(this).attr("href");
                if(url != "#"){
                    var pageNo= url.substr(url.indexOf("pageNo=") + 7 ,url.length);
                    //绑定click事件
                    $(this).click(function () {
                        this.currentPage = pageNo;
                        obj.queryParam();
                        // user_manage.getUserList(page,parseInt(pageNo));
                        return false;
                    });
                }
            });
        },
        clickA(){
            alert(this.totalRows);
        },
        //从服务器读取数据
        loadData: function(pageNum, pageSize){

        },
        //单行删除
        handleDelete: function(index, row) {

        },
        //暂停任务
        handlePause: function(index, row){

        },
        //恢复任务
        handleResume: function(index, row){

        },
        //搜索
        searchEvery: function(){

        },
        //弹出对话框
        handleadd: function(){
            this.dialogFormVisible = true;
        },
        formatStatus(row, column, cellValue, index){

        },
        //页码变更
        handleCurrentChange: function(val) {

        },
        //每页显示数据量变更
        handleSizeChange: function(val) {

        },
        //更新任务
        update: function(){

        },
        //添加
        addJob: function(){

        },
        clearQueryText(){

        }
    },
    components: {
        componentpage : componentpage ,
        menucomponet : menucom1
    }
});
