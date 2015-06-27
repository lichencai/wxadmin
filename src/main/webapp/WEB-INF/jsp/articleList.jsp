<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>articleList</title>
<link rel="stylesheet" type="text/css" href="/wxadmin/js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/wxadmin/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/wxadmin/js/jquery-easyui/themes/color.css">
<script type="text/javascript" src="/wxadmin/js/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="/wxadmin/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/wxadmin/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

</head>
<body>
    <h2>文章管理</h2>
    
    <table id="dg" title="文章" class="easyui-datagrid"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
            	<th field="id" hidden=true></th>
            	<th field="price" hidden=true></th>
            	<th field="status" hidden=true></th>
                <th field="title" width="50">标题</th>
                <th field="summary" width="50">简介</th>
                <th field="type" width="50">类型</th>
                <th field="createtime" width="50">创建时间</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
        <br />
        <label>标题:</label><input name="title" id="sTitle"/>
        <label>类型:</label>
        <select name="type" id="sType">
        	<option value="">全部</option>
         	<option value="01">新闻类</option>
         	<option value="02">财经类</option>
         	<option value="03">今日资讯</option>
         	<option value="04">首页新闻</option>
         </select>
         <label>创建时间:</label><input id="dd" type="text" class="easyui-datebox" name="createtime">
         <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="searchdata()">查询</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:800px;height:480px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" novalidate>
        	
            <div class="fitem">
                <label>文章标题</label>
                <input name="title" class="easyui-textbox">
                <input name="id" style="display:none">
            </div>
            <div class="fitem">
                <label>文章简介</label>
                <textarea name="summary" rows="3" cols="80" style="resize: none"></textarea>
            </div>
            <div class="fitem">
                <label>文章类型</label>
                <select name="type">
                	<option value="01">新闻类</option>
                	<option value="02">财经类</option>
                	<option value="03">今日资讯</option>
                	<option value="04">首页新闻</option>
                </select>
            </div>
            <div class="fitem">
                <label>价格</label>
                <input name="price" class="easyui-textbox">
            </div>
            <div class="fitem">
            	<label>内容</label>
            </div>
            <div>
                <textarea name="content" rows="30" cols="100" style="resize: none"></textarea>
            </div>
        </form>
    </div>
    
    
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
    
    
    <script type="text/javascript">
        
        $(function(){
        	$('#dg').datagrid({
        		url : "<%=pageContext.getServletContext().getContextPath() %>/article/getData.op"
        	});
        });
        
        
        
        function newUser(){
            $('#dlg').dialog('open').dialog('setTitle','新增');
            $('#fm').form('clear');
        }
        
        
        function editUser(){
            var row = $('#dg').datagrid('getSelected');
            console.log(row);
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','编辑');
                $('#fm').form('load',row);
            }
        }
        
        
        
        function saveUser(){
            $('#fm').form('submit',{
                url: "<%=pageContext.getServletContext().getContextPath() %>/article/savedata.op",
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.errorMsg){
                        $.messager.show({
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#dlg').dialog('close'); 
                        $('#dg').datagrid('reload');
                    }
                }
            });
        }
        
        
        
        function destroyUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('提示','确定删除?',function(r){
                    if (r){
                        $.post('<%=pageContext.getServletContext().getContextPath() %>/article/del.op',{id:row.id},function(result){
                            if (result.success){
                                $('#dg').datagrid('reload');
                            } else {
                                $.messager.show({
                                    title: 'Error',
                                    msg: result.errorMsg
                                });
                            }
                        },'json');
                        
                        
                        
                    }
                });
            }
        }
        
        
        function searchdata(){
        	console.log($('#dd').datebox('getValue'));
        	$('#dg').datagrid('reload',{
       			title: $("#sTitle").val(),
       			type : $("#sType").val(),
       			createtime : $('#dd').datebox('getValue')
        	});
        	
        }
        
        
    </script>
    
    
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .fitem input{
            width:160px;
        }
    </style>
</body>
</html>