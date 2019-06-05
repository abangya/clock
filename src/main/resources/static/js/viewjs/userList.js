layui.use(['table','element'], function(){
    var table = layui.table,
        form = layui.form;
    table.render({
        id:'userListTable'
        ,elem: '#userTable'
        ,url:'/user/allUser'
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
            {type:'checkbox',width:'5%'},
            {type:'numbers',title:'序号',width:'5%'},
            {field:'realName', width:"8%", title: '真实姓名',align:'center',edit: 'text'},
            {field: 'gender', title: '性别', width: '6%',align: 'center', templet:'#genderSelect'},
            {field:'tel', width:"8%", title: '手机号码',align:'center'},
            {field:'userName', width:"8%", title: '用户名',align:'center'},
            {field:'nickName', width:"8%", title: '昵称',align:'center'},
            {field:'status', width:"8%", title: '状态',align:'center',
                templet:function (row) {
                    if(row.status == 1){
                        return "<span style='color:green'>正常</span>";
                    }else{
                        return "<span style='color:red'>异常</span>";
                    }
                }
            },
            {field:'headImg', width:"8%", title: '头像',align:'center'},
            {field:'photo', width:"8%", title: '绑定照片',align:'center'},
            {field:'lastLoginTime', width:"8%", title: '上次登录时间',align:'center'},
            {fixed: 'right', width:"17%", align:'center', toolbar: '#userTableBar'}
        ]],
        done:function (res, curr, count) {
            layui.each($('select'),function (index,item) {
                var elem = $(item);
                elem.val(elem.data('value')).parents('div.layui-table-cell').css('overflow', 'visible');
            })
            form.render();
        }
    });
    form.on('select(gender)', function(obj){
        var elem = $(obj.elem);
        var trElem = elem.parents('tr');
        var tableData = table.cache['userListTable'];
        // 更新到表格的缓存数据中，才能在获得选中行等等其他的方法中得到更新之后的值
        tableData[trElem.data('index')][elem.attr('name')] = obj.value;
    });
    //监听单元格编辑
    table.on('edit(userTableFilter)', function(obj){
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field; //得到字段
        layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
    });
    //监听表格复选框选择
    table.on('checkbox(userTableFilter)', function(obj){
        console.log(obj)
    });
    //监听工具条
    table.on('tool(userTableFilter)', function(obj){
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
            var userTableReload = $('#userTableReload');
            var searchTime = $('#searchTime');
            console.log(searchTime.val())
            //执行重载
            table.reload('userListTable', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    searchName: userTableReload.val(),
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
//监听性别操作
    form.on('switch(sexDemo)', function(obj){
        layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    });

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