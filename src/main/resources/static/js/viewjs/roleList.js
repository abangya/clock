layui.use('table', function(){
    var table = layui.table;

    //第一个实例
    table.render({
        id:'roleList'
        ,elem: '#roleList'
        ,height: 'full-40'
        ,url: '/role/roleList' //数据接口
        ,page: true //开启分页
        ,limit:20
        ,cols: [[ //表头
            {type:'numbers',title:'序号',width:80}
            ,{field: 'username', title: '用户名', width:80}
            ,{field: 'sex', title: '性别', width:80, sort: true}
            ,{field: 'city', title: '城市', width:80}
            ,{field: 'sign', title: '签名', width: 177}
            ,{field: 'experience', title: '积分', width: 80, sort: true}
            ,{field: 'score', title: '评分', width: 80, sort: true}
            ,{field: 'classify', title: '职业', width: 80}
            ,{field: 'wealth', title: '财富', width: 135, sort: true}
        ]]
    });

});