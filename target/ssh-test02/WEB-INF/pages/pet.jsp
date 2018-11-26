<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="<%=request.getContextPath()%>/getAllPet.html" method="post" id="search-pet" class="layui-form">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">宠物名：</label>
            <div class="layui-input-inline">
                <input type="text" name="pet.name" autocomplete="off" placeholder="请输入名称" class="layui-input">
            </div>
            <label class="layui-form-label">手机号：</label>
            <div class="layui-input-inline">
                <input type="text" name="pet.breederPhone" autocomplete="off" placeholder="饲养员手机号" class="layui-input">
            </div>
            <button class="layui-btn" lay-submit lay-filter="search-pet">搜索</button>
            <button class="layui-btn" type="reset">重置</button>
        </div>
    </div>
</form>
<script type="text/html" id="pet-head-bar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="goEdit"><i class="layui-icon">&#xe654;</i>挂号建档</button>
    </div>
</script>
<table id="pet-table" lay-filter="pet-table"></table>
<script>
    var pet_id;
    layui.use(['table', 'form', 'laydate'], function () {
        var table = layui.table;
        var form = layui.form;
        var laydate = layui.laydate;



        //第一个实例
        table.render({
            elem: '#pet-table'
            , toolbar: '#pet-head-bar'
            , url: 'getAllPet.html' //数据接口
            , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['prev', 'page', 'next', 'count', 'skip', 'limit'] //自定义分页布局
                , groups: 5
                , first: false //不显示首页
                , last: false //不显示尾页,
                , limits: [1, 2, 35, 10, 15, 20]
                , limit: 2

            }
            , cols: [[ //表头
                {field: 'id', title: 'ID', width: 80, sort: true, fixed: 'left'}
                , {field: 'name', title: '宠物名'}
                , {field: 'breeder', title: '饲养员'}
                , {field: 'breederPhone', title: '手机'}
                , {
                    field: 'breed', title: '品种', width: 100, templet: function (data) {
                        return (data.breed === 1) ? '<span class="layui-badge layui-bg-green">小狗</span>' : '<span class="layui-badge layui-bg-red" >大猫</span>'
                    }
                }
                , {field: 'birthDay', title: '宠物生日'}
                , {field: 'balance', title: '余额'}
                , {title: '操作', toolbar: '#pet-row-bar'}

            ]]
        });
        //头工具栏事件
        table.on('toolbar(pet-table)', function (obj) {
            switch (obj.event) {
                case 'goEdit':
                    openEditPetWindow();
                    form.render();
                    break;
            }
        });

        table.on('tool(pet-table)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            pet_id = data.id;
            switch (obj.event) {
                case 'edit-pet':
                    openEditPetWindow();
                    form.val("pet-edit-form", {
                        "id": data.id,
                        "name": data.name,
                        "birthDay": data.birthDay,
                        "breed": data.breed +"",
                        "breeder": data.breeder,
                        "breederPhone": data.breederPhone,
                        "balance": data.balance
                    });
                    form.render();
                    break;
                case  'consume-record':
                    layer.open({
                        type: 1,
                        content: $("#record-layer").html()
                        , area: ['1200px', '500px']
                        , btn: ['确认']
                        , yes: function (index, layero) {
                            layer.close(index);
                            table.reload('pet-table',{})
                        }, success: function () {
                            $("#petId").val(pet_id);
                            laydate.render({
                                elem:'#date_start'
                            });
                            laydate.render({
                                elem:'#date_end'
                            });
                            table.render({
                                elem: '#record-table'
                                , url: 'getAllRecord.html?petId=' + data.id //数据接口
                                , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                                    layout: ['prev', 'page', 'next', 'count', 'skip', 'limit'] //自定义分页布局
                                    , groups: 5
                                    , first: false //不显示首页
                                    , last: false //不显示尾页,
                                    , limits: [1, 2, 35, 10, 15, 20]
                                    , limit: 2
                                }
                                , toolbar: '#record-bar'
                                , cols: [[ //表头
                                    {field: 'petName', title: '宠物名', fixed: 'left'}
                                    , {field: 'consumeDate', title: '交易日期', sort: true}
                                    , {
                                        field: 'consumeType', title: '消费类型', width: 100, templet: function (data) {
                                            return (data.consumeType === 0) ? '<span class="layui-badge layui-bg-green">充值</span>' : '<span class="layui-badge layui-bg-red" >消费</span>'
                                        }
                                    }
                                    , {field: 'consumeMoney', title: '交易金额'}
                                    , {field: 'balance', title: '余额'}
                                    , {field: 'remark', title: '明细'}
                                ]]
                            });

                        }
                    })
            }
        });
        table.on('toolbar(record-table)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            switch (obj.event) {
                case 'add-record':
                    openConsumeLayer(0);
                    break;
                case 'minus-record':
                    openConsumeLayer(1);
            }
        });

        function openConsumeLayer(type) {
            layer.open({
                type: 1,
                content: $("#record-consume-layer").html()
                , btn: ['确认', '取消']
                , yes: function (index, layero) {
                    $.ajax({
                        url: '<%=request.getContextPath()%>/addConsumeRecord.html',
                        data: $("#record-consume-form").serialize(),
                        method: 'post',
                        success: function (result) {

                            if (result.status) {
                                table.reload('record-table', {});
                                layer.close(index);
                            } else {
                                alert(result.message)
                            }
                        }
                    });
                }, success: function () {
                    form.render();
                    form.val("record-consume-form", {
                        "petId": pet_id,
                        "consumeType": type
                    })
                }
            })

        }

        function openEditPetWindow() {
            layer.open({
                type: 1,
                content: $('#pet-edit-layer').html(),
                title: '编辑角色',
                area: ['600px', '500px'],
                btn: ['提交', '取消'],
                yes: function (index, layero) {
                    $.ajax({
                        url: '<%=request.getContextPath()%>/editPet.html',
                        data: $("#pet-edit-form").serialize(),
                        method: 'post',
                        success: function (result) {

                            if (result.status) {
                                table.reload('pet-table', {});
                                layer.close(index);
                            } else {
                                alert(result.message)
                            }
                        }
                    })
                },
                success :function () {
                    laydate.render({
                        elem: '#birthDay'
                    });
                }
            });
        }

        form.on('submit(search-pet)', function (data) {
            table.reload('pet-table', {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: data.field
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
        form.on('submit(search-record)', function (data) {
            table.reload('record-table', {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: data.field
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });

    });
</script>
<script type="text/html" id="pet-row-bar">
    <a class="layui-btn layui-btn-xs " lay-event="edit-pet">修改</a>
    <a class="layui-btn layui-btn-xs" lay-event="consume-record">消费记录</a>
</script>

<script type="text/html" id="pet-edit-layer">
    <form id="pet-edit-form" style="width: 80%" class="layui-form" lay-filter="pet-edit-form">
        <input type="hidden" name="pet.id">
        <div class="layui-form-item">
            <label class="layui-form-label">宠物名</label>
            <div class="layui-input-block">
                <input type="text" name="pet.name" required lay-verify="required" placeholder="请输入宠物名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <%-- todo --%>
            <label class="layui-form-label">宠物品种</label>
            <div class="layui-input-block">
                <input type="radio" name="pet.breed" value="0" title="猫">
                <input type="radio" name="pet.breed" value="1" title="狗">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">宠物生日</label>
            <div class="layui-input-block">
                <input type="text" name="pet.birthDay" id="birthDay" class="layui-input" placeholder="yyyy-MM-dd">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">主人姓名</label>
            <div class="layui-input-block">
                <input type="text" name="pet.breeder" required lay-verify="required" placeholder="请输入主人姓名"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">主人手机</label>
            <div class="layui-input-block">
                <input type="text" name="pet.breederPhone" required lay-verify="required" placeholder="请输入主人手机"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>

    </form>
</script>

<script type="text/html" id="record-layer">
    <form action="#" id="search-record" class="layui-form">
        <input type="hidden" name="petId" id="petId">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">消费日期:</label>
                <div class="layui-inline">
                    <div class="layui-input-inline">
                        <input type="text" name="startTime" id="date_start" class="layui-input" placeholder="开始时间">
                    </div>
                </div>
                <div class="layui-inline">
                    <div class="layui-input-inline">
                        <input type="text" name="endTime" id="date_end" class="layui-input" placeholder="结束时间">
                    </div>
                </div>
                <button class="layui-btn" lay-submit lay-filter="search-record">搜索</button>
                <button class="layui-btn" type="reset">重置</button>
            </div>
        </div>
    </form>
    <table id="record-table" lay-filter="record-table"></table>
</script>
<script type="text/html" id="record-bar">
    <a class="layui-btn layui-btn-xs " lay-event="add-record">充值</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="minus-record">消费</a>
</script>
<script type="text/html" id="record-consume-layer">
    <form class="layui-form" action="" id="record-consume-form" lay-filter="record-consume-form">
        <input type="hidden" name="petId"/>
        <input type="hidden" name="consumeType">
        <div class="layui-form-item">
            <label class="layui-form-label">金额</label>
            <div class="layui-input-block">
                <input type="text" name="consumeMoney" required lay-verify="required" placeholder="请输入标题"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <input type="text" name="remark" required lay-verify="required" placeholder="请输入标题" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
    </form>
</script>
