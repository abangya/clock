/**
 * ajax异常
 * */
function ajaxError(){
	/*$.get("/error/ajaxError",function(data,status){
		console.log("status:"+status);
		console.log("data:"+data);
		layer.alert(data);
	});*/
	
	$.ajax({
		url: "/error/ajaxError",
		data:"",
		type: "GET",
		success:function(data){
			//异常过滤处理
			if(isError(data)){
				alert(data);
			}
		},
		error:function(e){
			//也可通过error控制请求失败的情况
			console.log("e:"+e);
		}
	});
}


$(function(){
	$.ajaxSetup({
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR)
			//$.messager.show({title: '提示信息',msg: '<center style="color:red">网络异常，请稍后再试</center>',timeout: 2000,showType: 'slide'});
            switch(jqXHR.status) {
              case(500):
                alert("服务器系统内部错误！");
                break;
               case(401):
				   layer.msg('抱歉，您没有此权限！');
                break;
               case(403):
				   layer.confirm('登录已失效，请重新登录！', {
					   btn: ['确定'] //按钮
				   }, function(){
					   if (window != top){
						   top.location.href="/login";
					   }else{
						   window.location.href="/login";
					   }
				   });
                break;
               case(408):
                alert("请求超时！");
                break;
              default:
                alert("未知错误！");
             }
		}
	});
});









