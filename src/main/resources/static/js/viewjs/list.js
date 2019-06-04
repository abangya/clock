layui.use(['table','element'], function(){
    var table = layui.table;
    table.render({
        id:'contenttable'
        ,elem: '#test'
        ,url:'/clock/clockAllUser'
        ,method:"post"
        ,height: 'full-40'
        ,contentType: 'application/json'
        ,limit:20
        ,page:true
        ,request: {
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
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
            {type:'checkbox',width:'5%'},
            {type:'numbers',title:'序号',width:'5%'},
            {field:'clockCreateTime', width:"12%", title: '日期时间',align:'center',
                templet:function (row) {
                    if(!row.clockCreateTime){
                        return "<span style='color:red'>暂无打卡记录</span>";
                    }
                    return DateUtils.dateFormat("yyyy-MM-dd",new Date(row.clockCreateTime));
                }
            },
            {field:'userName', width:"8%", title: '用户名',align:'center'},
            {field:'level', width:"8%", title: '打卡次数',align:'center'},
            {field:'time', width:"45%", title: '考核区间',
                templet:function (row) {
                    var str = '';
                for(var i = 0 ;i<row.dimensionVoList.length;i++){
                    str+= row.dimensionVoList[i].startTime+"-"+row.dimensionVoList[i].endTime+"  ";
                }
                    return str;
                }},

            {fixed: 'right', width:"17%", align:'center', toolbar: '#barDemo'}
        ]]
    });
    //监听表格复选框选择
    table.on('checkbox(demo)', function(obj){
        console.log(obj)
    });
    //监听工具条
    table.on('tool(demo)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            $.ajax({
                url: '/clock/userClock',
                type: 'POST',
                data: JSON.stringify({id:data.id,searchTime:data.clockCreateTime}),
                contentType:'application/json; charset=utf-8',
                dataType : "json",
                success: function (result) {
                    layer.open({
                        type: 1,
                        anim: 0,
                        title: data.userName+"打卡记录",
                        area: ['60%', '60%'],
                        btn: ['关闭'],
                        content: "<div style='height: 100%;'><table id=\"templateTable\"></table></div>",
                        success : function(index, layero) {
                            table.render({
                                elem: '#templateTable'
                                ,data:result.data
                                ,height: 'full-450'
                                ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                                ,cols: [[
                                    {type:'numbers',title:'序号'},
                                    {field:'clockTime',title:'正常上班区间',
                                        templet:function (row) {
                                            return row.startTime +" - "+ row.endTime;
                                        }
                                    },
                                    {field:'workTime', title: '上班打卡时间',
                                        templet:function (row) {
                                            if(!row.workTime){
                                                return "<span style='color:red'>"+row.workTimeMsg+"</span>";
                                            }
                                            var str = '';
                                            if("正常打卡"==row.workTimeMsg){
                                                str = "(<span style='color:green'>"+row.workTimeMsg+"</span>)";
                                            }else{
                                                str = "(<span style='color:red'>"+row.workTimeMsg+"</span>)";
                                            }
                                            return DateUtils.dateFormat("yyyy-MM-dd hh:mm:ss",new Date(row.workTime))+str;


                                        }
                                    },
                                    {field:'afterTime', title: '下班打卡时间',
                                        templet:function (row) {
                                            if(!row.afterTime){
                                                return "<span style='color:red'>"+row.afterTimeMsg+"</span>";
                                            }
                                            var str = '';
                                            if("正常打卡"==row.afterTimeMsg){
                                                str = "(<span style='color:green'>"+row.afterTimeMsg+"</span>)";
                                            }else{
                                                str = "(<span style='color:red'>"+row.afterTimeMsg+"</span>)";
                                            }
                                            return DateUtils.dateFormat("yyyy-MM-dd hh:mm:ss",new Date(row.afterTime))+str;

                                        }
                                    }
                                ]]
                            });
                        },
                    });
                }
            });
        } else if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
            layer.alert('编辑行：<br>'+ JSON.stringify(data))
        }
    });
    var $ = layui.$, active = {
        getCheckData: function(){ //获取选中数据
            var checkStatus = table.checkStatus('contenttable')
                ,data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
        ,getCheckLength: function(){ //获取选中数目
            var checkStatus = table.checkStatus('contenttable')
                ,data = checkStatus.data;
            layer.msg('选中了：'+ data.length + ' 个');
        }
        ,isAll: function(){ //验证是否全选
            var checkStatus = table.checkStatus('contenttable');
            layer.msg(checkStatus.isAll ? '全选': '未全选')
        },reload: function(){
            var demoReload = $('#demoReload');
            var searchTime = $('#searchTime');
            console.log(searchTime.val())
            //执行重载
            table.reload('contenttable', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    userName: demoReload.val(),
                    searchTime:searchTime.val()
                }
            });
        },open:function () {
            layui.use('layer', function(){
                var layer = layui.layer;
                /*$(document).on('click','#open',function(){
                    layer.msg('hello');
                });*/
                layer.open({
                    title: '设置页面',
                    type: 2,
                    area: ["1000px","500px"],
                    content: '/'
                });
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