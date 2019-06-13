/**
 * 修改用户密码
 * */
$(function(){
    layui.use(['form' ,'layer'], function() {
        var form = layui.form;
        form.verify({
            password: function(value) {
                if (value === "")
                    return "密码不能为空！";
            },
            isPassword: function(value) {
                if (value === ""){
                    return "请输入二次密码！";
                }
                var pwd = $('input[name=password').val();
                if (pwd !== value){
                    return "二次输入的密码不一致！";
                }
            },

        });
        //确认修改密码
        form.on("submit(setPwd)",function () {
            setPwd();
            return false;
        });
    })
})

function setPwd(){
    var password=$("#password").val();
    $.ajax({
        url: '/user/setPwd',
        type: 'POST',
        data:{'password':password},
        contentType:'application/x-www-form-urlencoded',
        dataType : "json",
        success: function (data) {
            if(data.code==200){
                layer.alert("操作成功",function () {
                    layer.closeAll();
                    window.location.href="/logout";
                });
            }else{
                layer.alert(data.message,function () {
                    layer.closeAll();
                });
            }
        }
    })
}
