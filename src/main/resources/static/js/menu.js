$(function(){
    $(".layui-nav a").on("click",function(){
        var address =$(this).attr("data-src");
        console.log(address)
        $("iframe").attr("src",address);
    });
});
