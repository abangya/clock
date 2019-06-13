layui.use(['table','element','jquery'], function(){
    var table = layui.table;
    $= layui.$;
    table.render({
        id:'checkInStatisticsTableID'
        ,elem: '#checkInStatisticsTable'
        ,url:'/checkInStatistics/getDayCountList'
        ,method:"get"
        ,height: 'full-40'
        ,limit:20
        ,page:true
        ,where : {
            'type':$('#typeRadio input[name="type"]:checked').val()
        }
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
            {field:'name', title: '姓名',align:'center'},
            {field:'countDate',title: '打卡日期',align:'center',
                templet:function (row) {
                    if(row.countDate){
                        return row.countDate;
                    }
                    return row.startTime +' -  '+row.endTime;
                }
            },
            {field:'checkInTimes',title: '应打卡次数',align:'center'},
            {field:'actualCheckInTimes',title: '实际打卡次数',align:'center'},
            {field:'normal',title: '正常打卡次数',align:'center'},
            {field:'late',title: '迟到次数',align:'center'},
            {field:'leaveEarly',title: '早退次数',align:'center'},
            {field:'unsignedTimes',title: '缺卡次数',align:'center'}
        ]]
    });
    var $ = layui.$, active = {
        reload: function(){
            var name = $('#name');
            var searchTime = $('#searchTime');
            //执行重载
            table.reload('checkInStatisticsTableID', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    name: name.val(),
                    //searchTime:searchTime.val(),
                    'type':$('#typeRadio input[name="type"]:checked').val()
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