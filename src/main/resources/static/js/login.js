/**
 * 登录
 */
$(function(){
     layui.use(['form' ,'layer'], function() {
         var form = layui.form;
         var layer = layui.layer;
         form.on("submit(login)",function () {
             login();
             return false;
         });
         var path=window.location.href;
         if(path.indexOf("kickout")>0){
             layer.alert("您的账号已在别处登录；若不是您本人操作，请立即修改密码！",function(){
                 window.location.href="/login";
             });
         }
     })
 })

function login(){
    var username=$("#userName").val();
    var password=$("#password").val();
    var rememberMe = $("#rememberMe").val();
    $.ajax({
        url: '/login',
        type: 'POST',
        data:JSON.stringify($("#useLogin").serializeJSON()),
        contentType:'application/json; charonAccessDeniedset=utf-8',
        dataType : "json",
        success: function (data) {
            if(data.code == 200){
                layer.msg('登录成功',{
                    time:1000
                }, function () {
                    window.location.href="/home";
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
    /*$.post(
        "/login",
        $("#useLogin").serialize(),
        function(data){
        if(data.code == 200){
            layer.msg('登录成功',{
                time:1000
            }, function () {
                window.location.href="/home";
            })
        }else{
            layer.alert(data.message,function(){
                layer.closeAll();//关闭所有弹框
            });
        }
    });*/

}
//ifram关闭
if (window != top)
    top.location.href = location.href;