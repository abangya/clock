layui.use(['table','element'], function(){
    var table = layui.table;
    table.render({
        id:'checkInStatisticsTableID'
        ,elem: '#checkInStatisticsTable'
        ,url:'/clock/clockAllUser'
        ,method:"post"
        ,height: 'full-40'
        ,contentType: 'application/json'
        ,limit:20
        ,page:true
        ,request: {
            pageName: 'startNum' //页码的参数名称，默认：page
            ,limitName: 'size' //每页数据量的参数名，默认：limit
        }
        , response:{
            statusName: 'code' //规定数据状态的字段名称，默认：code
            ,statusCode: 200 //规定成功的状态码，默认：0
            ,msgName: 'message' //规定状态信息的字段名称，默认：msg
            ,countName: 'total' //规定数据总数的字段名称，默认：count
            ,dataName: 'data' //规定数据列表的字段名称，默认：data
        },
        parseData: function(res){ //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "message": res.message, //解析提示文本
                "total": res.data.total, //解析数据长度
                "data": res.data.list //解析数据列表
            };
        }
        ,cols: [[
            {type:'numbers',title:'序号'},
            {field:'userName', title: '姓名',align:'center'},
            {field:'level',title: '打卡次数',align:'center'},
            {field:'level',title: '迟到次数',align:'center'},
            {field:'level',title: '早退次数',align:'center'},
            {field:'time',title: '考核区间',
                templet:function (row) {
                    var str = '';
                    for(var i = 0 ;i<row.dimensionVoList.length;i++){
                        str+= row.dimensionVoList[i].startTime+"-"+row.dimensionVoList[i].endTime+"  ";
                    }
                    return str;
                }},

        ]]
    });
    var $ = layui.$, active = {
        reload: function(){
            var userName = $('#userName');
            var searchTime = $('#searchTime');
            //执行重载
            table.reload('contenttable', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    userName: userName.val(),
                    searchTime:searchTime.val()
                }
            });
        }
    };

    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    var element = layui.element;
});
layui.use('laydate', function(){
    var laydate = layui.laydate;
    //执行一个laydate实例
    laydate.render({
        elem: '#searchTime', //指定元素
        type: 'date',
        range: '~'
    });
});