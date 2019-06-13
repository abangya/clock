var pageCurr,userTableReload,searchTime,table;
layui.use(['table','element','form'], function(){
        table = layui.table;
        var form = layui.form,
        element = layui.element;
        table.render({
        id:'userListTable'
        ,elem: '#userTable'
        ,url:'/user/allUser'
        ,method:"post"
        ,height: 'full-40'
        ,contentType: 'application/json'
        ,limit:10
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
            {field:'userRole', width:"8%", title: '角色',align:'center',
                templet: function (row) {
                    var str = '';
                    for(let  i of row.userRoleVoList){
                        str += i.description+',';
                    }
                    if(str.endsWith(",")){
                        str = str.substring(0,str.length-1)
                    }
                    return str;
                }
            },
            {field: 'gender', title: '性别', width: '6%',align: 'center', templet:'#genderSelect'},
            {field:'tel', width:"8%", title: '手机号码',align:'center',edit: 'text'},
            {field:'userName', width:"8%", title: '用户名',align:'center'},
            {field:'nickName', width:"8%", title: '昵称',align:'center',edit: 'text'},
            {field:'status', title:'状态', width:"6%", align:'center',templet: '#status'},
            {field:'headImg', width:"8%", title: '头像',align:'center',
                templet:function (row) {
                    return '<div onclick="show_img(this)" ><img src="'+row.headImg+'" alt="" onerror="this.style.display=\'none\'" width="50px" height="50px"></a></div>';
                }
            },
            {field:'photo', width:"8%", title: '绑定照片',align:'center',
                templet:function (row) {
                    return '<div onclick="show_img(this)" ><img src="'+row.photo+'" alt="" onerror="this.style.display=\'none\'" width="50px" height="50px"></a></div>';
                }
            },
            {field:'lastLoginTime', width:"10%", title: '上次登录时间',align:'center'},
            {fixed: 'right', width:"12%", align:'center', toolbar: '#userTableBar'}
        ]],
        done:function (res, curr, count) {
            layui.each($('select'),function (index,item) {
                var elem = $(item);
                if(elem.attr("name") == 'gender' || elem.attr("name") == 'role'){
                    elem.val(elem.data('value')).parents('div.layui-table-cell').css('overflow', 'visible');
                }
            })
            form.render();
            //得到数据总量
            pageCurr=curr;
        }
    });
     //监听下拉框
    form.on('select(gender)', function(obj){
        var elem = $(obj.elem);
        var trElem = elem.parents('tr');
        var tableData = table.cache['userListTable'];
        // 更新到表格的缓存数据中，才能在获得选中行等等其他的方法中得到更新之后的值
        tableData[trElem.data('index')][elem.attr('name')] = obj.value;
    });
    //监听账号状态操作
    form.on('switch(statusFilter)', function(obj){
        var elem = $(obj.elem);
        var trElem = elem.parents('tr');
        var tableData = table.cache['userListTable'];
        var str = 2;
        if(obj.elem.checked){
            str = 1;
        }
        tableData[trElem.data('index')][elem.attr('name')] = str;
    });
    //监听单元格编辑
    table.on('edit(userTableFilter)', function(obj){
        /*var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field; //得到字段
        layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);*/
    });
    //监听表格复选框选择
    table.on('checkbox(userTableFilter)', function(obj){
        //console.log(obj)
    });

    //监听工具条
    table.on('tool(userTableFilter)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            layer.confirm(JSON.stringify(data), {
                btn: ['确定'] //按钮
            }, function(index){
                layer.close(index)
            });
        } else if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
               /* obj.del();*/
               /* layer.close(index);*/
                /*layer.msg(JSON.stringify(data))*/
                ajaxGet("/user/deleteUser",data);
            });
        } else if(obj.event === 'edit'){
            ajaxPost("/user/updateUser",data);
            /*layer.alert('编辑行：<br>'+ JSON.stringify(data))*/
        }
    });
    //监听button按钮点击事件
    var $ = layui.$, active = {
        getCheckData: function(){ //获取选中数据
            var checkStatus = table.checkStatus('userListTable')
                ,data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
        ,getCheckLength: function(){ //获取选中数目
            var checkStatus = table.checkStatus('userListTable')
                ,data = checkStatus.data;
            layer.msg('选中了：'+ data.length + ' 个');
        }
        ,isAll: function(){ //验证是否全选
            var checkStatus = table.checkStatus('userListTable');
            layer.msg(checkStatus.isAll ? '全选': '未全选')
        }
        ,reload: function(){
            userTableReload = $('#userTableReload').val();
            searchTime = $('#searchTime').val();
            pageCurr = 1;
            load();
        }
    };
    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    //渲染多选下拉框
    formSelects.data('roleSelect', 'server', {
        url: '/role/roles',
        type:'get',
        response: {
            statusCode: 200,          //成功状态码
            statusName: 'code',     //code key
            msgName: 'message',         //msg key
            dataName: 'data'        //data key
        },
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'value',
        success: function(id, url, searchVal, result){      //使用远程方式的success回调
            console.log(result);    //返回的结果
        },
        error: function(id, url, searchVal, err){           //使用远程方式的error回调
            //同上
            console.log(err);   //err对象
        }
    });
});
//日期下拉框组件
layui.use(['laydate','form','upload'], function(){
    var form = layui.form,
        laydate = layui.laydate,
        upload = layui.upload;
    //执行一个laydate实例
    laydate.render({
        elem: '#searchTime', //指定元素
        type: 'date',
        range: '~'
    });
    //监听提交
    form.on('submit(userSubmit)', function(data){
        // TODO 校验
        if(data.field.status == "on") {
            data.field.status = "1";
        } else {
            data.field.status = "0";
        }
        formSubmit(data);
        return false;
    });
    //执行实例
    var uploadInst = upload.render({
        elem: '#photoId' //绑定元素
        ,url: '/imageUpload/' //上传接口
         ,before: function(obj){
             //预读本地文件示例，不支持ie8
             obj.preview(function(index, file, result){
                 $('#demo1').attr('src', result); //图片链接（base64）
             });
         }
         ,done: function(res){
             //如果上传失败
             if(res.code !=200){
                 return layer.msg('上传失败');
             }
             //上传成功
            debugger;
            $("input[name='file']").val()
         }
         ,error: function(){
             //演示失败状态，并实现重传
             var demoText = $('#demoText');
             demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
             demoText.find('.demo-reload').on('click', function(){
                 uploadInst.upload();
             });
         }
    });
});
//提交表单
function formSubmit(data){
    console.log(JSON.stringify(data.field))
    $.ajax({
        type: "POST",
        contentType:'application/json; charset=utf-8',
        dataType : "json",
        data: JSON.stringify(data.field),
        url: "/user/setUser",
        success: function (data) {
            if (data.code == 200) {
                layer.alert(data.message,function(){
                    layer.closeAll();
                    load();
                });
            } else {
                layer.alert(data.message);
            }
        }
    });
}
//显示大图片
function show_img(t) {
    var t = $(t).find("img");
    //页面层
    layer.open({
        title:'图片详情',
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['40%', '50%'], //宽高
        shadeClose: true, //开启遮罩关闭
        end: function (index, layero) {
            return false;
        },
        content: '<div style="text-align:center"><img src="' + $(t).attr('src') + '" /></div>'
    });
}
function ajaxPost(url,data) {
  data = JSON.stringify(data);
    $.ajax(
        {
        url: url,
        type: 'POST',
        data:data,
        contentType:'application/json; charset=utf-8',
        dataType : "json",
        success: function (data) {
            if(data.code == 200){
                load();
                layer.msg(data.message,{
                    time:1000
                })
            }else{
                layer.alert(data.message,function(){
                    layer.closeAll();//关闭所有弹框
                });
            }
        },
        error: function (err) {
            console.log(err)
        }
     })
}
function ajaxGet(url,data) {
    $.ajax({
        url: url+'/'+data.id,
        type: 'Get',
        contentType:'application/json; charset=utf-8',
        dataType : "json",
        success: function (data) {
            if(data.code == 200){
                load()
                layer.msg(data.message,{
                    time:1000
                })
            }else{
                layer.alert(data.message,function(){
                    layer.closeAll();//关闭所有弹框
                });
            }
        }
    })
}
//重新加载表格
function load(){
    let data={
        'searchName': userTableReload,
        'searchTime':searchTime,
        'searchRoleStr':formSelects.value('roleSelect', 'valStr')
    }
    //执行重载
    table.reload('userListTable', {
        page: {
            curr: pageCurr //重新从第 1 页开始
        }
        ,where: data
    });
}
function addUser() {
    openUser(null,"新增用户");
}
function openUser(data,title) {
    if(data==null || data==""){
        $("#id").val("");
    }
    formSelects.data('formRoleSelect', 'server', {
        url: '/role/roles',
        type:'get',
        response: {
            statusCode: 200,          //成功状态码
            statusName: 'code',     //code key
            msgName: 'message',         //msg key
            dataName: 'data'        //data key
        },
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'value',
        success: function(id, url, searchVal, result){      //使用远程方式的success回调
            console.log(result);    //返回的结果
        },
        error: function(id, url, searchVal, err){           //使用远程方式的error回调
            console.log(err);   //err对象
        }
    });
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setUser'),
        end:function(){
            cleanUser();
        }
    });
}
function cleanUser(){
    $("#userName").val("");
    $("#tel").val("");
    $("#password").val("");
    $('#roleId').html("");
}