$(function(){
    $(".layui-nav a").on("click",function(){
        var address =$(this).attr("data-src");
        $("iframe").attr("src",address);
    });
});
