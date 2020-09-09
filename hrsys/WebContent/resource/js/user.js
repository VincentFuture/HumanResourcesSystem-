$(document).ready(function(){
	//调用表格初始化函数
	initTable();
	//调用负责按钮绑定事件的函数
	btnOnEvent();
});
//按钮事件绑定函数
function btnOnEvent(){
		$("#btnSearch").on("click",searchUser);//查找按钮点击事件绑定
		$("#btnSave").on("click",saveData);//模态框保存按钮点击事件绑定
		$("#addu").on("click",addData);//添加按钮事件绑定
		$("#delu").on("click",delData);//删除按钮事件绑定
		$("#modu").on("click",upUser);//修改按钮事件绑定
		$("#showPwdToggle").on("click",showPwdToggle);//密码显示按钮切换事件绑定
}
//表格初始化
function initTable(){
	//设置表格的列属性
	let colArr=[
		{checkbox:true},
		{field:"user_id",
		title:"编号"},
		{field:"username",
		title:"用户名"},
		{title:"密码",
		formatter:function(){
			return "********";
		}},
		{field:"isused",
		title:"状态",
		formatter:function(value){
			if(value==1){
				return "启用";
			}else{
				return "禁用";
			}
		}},
		{title:"操作",
		field:"isused",
		formatter:function(value,row){
			if(value==1){
				return "<a onclick='isUsed("+row.user_id+","+(++value)+")' class='btn btn-danger btn-sm'>禁用</a>"
			}else{
				return "<a onclick='isUsed("+row.user_id+","+(--value)+")' class='btn btn-success btn-sm'>启用</a>"
			}
		}}
	];
	//
	//设置表格的属性
	$("#table").bootstrapTable({
		url:"../searchUser",//设置后端接口url
		method:"post",//使用post方法提交
		dataType:"json",//返回数据类型
		toolbar:"#tools",//设置表格的工具栏
		pagination:true,//分页显示
		pageSize:8,//设置每页显示的条目数
		pageList:[8,10,12,14],//设置每页显示条目数的选项
		columns:colArr//设置各列的属性
	});
	//
}
//查找按钮事件
function searchUser() {
	//通过ajax进行操作
	//console.log("searchUserEvent");
	$.post("../searchUser",$("#frmSearch").serialize(),function(rs){
		//根据返回的数据rs重新加载表格数据
		$("#table").bootstrapTable("load",rs);
	},"json");
	//重置查询表单数据
	$("#frmSearch")[0].reset();
	
}
//启用和禁用事件:仅超级管理员admin(uid=1)可操作
function isUsed(uid,isused){
	//验证是否为超级管理员
	if(sessionStorage.getItem("userid")!=1){
		alert("抱歉,操作不适用");
		$("#table a").addClass("disabled");
		return ;
	}
	//
	//验证选项是否合法
	if(uid==sessionStorage.getItem("userid"))
	{//操作对象不能是当前用户
		alert("抱歉!操作对象错误");
		return;
	}
	let txt = "";//提示文本
	switch (isused) {
	case 1:
		txt = "确定启用此用户吗";
		break;
	case 2: 
		txt = "确定禁用此用户吗";
		break
	default:
		break;
	}
	if(confirm(txt)){
		let data={
				"uid":uid,
				"isused":isused
		};
		$.post("../userUpdate",data,function(rs){
			alert(rs.msg);//报告操作结果
			if(rs.flag){//刷新表格
				$("#table").bootstrapTable("refresh");
			}
		},"json");
	}
}
//模态框密码输入框密码样式开关
function showPwdToggle(){
	//console.log("调试信息:眼睛开关");
	//获取密码输入框样式
	var type =$("#frmUser input[name='upass']").attr("type");
	if(type=='password'){//密码样式
		//修改输入样式
		$("#frmUser input[name='upass']").attr("type","text");
		//修改眼睛图标
		$(this).children("span").removeClass();
		$(this).children("span").addClass("glyphicon glyphicon-eye-close");
	}
	else {//可见文本样式
		$("#frmUser input[name='upass']").attr("type","password");
		$(this).children("span").removeClass();
		$(this).children("span").addClass("glyphicon glyphicon-eye-open");
	}
}
//添加 修改 删除
var path = "";//后端接口地址的全局变量
//保存按钮事件函数
function saveData() {
	//console.log("保存按钮点击事件发生");//调试信息
	//验证表单数据
	var inputObj =$("#frmUser").find("input.form-control");
	for(var i=0;i<inputObj.length;i++){//遍历表单元素
		if(inputObj[i].value==""){//元素value属性值为空
			//显示提示标签
			$("#prompt").css("display","inline");
			return ;
		}
	}
	//发送表单数据
	$.post(path,$("#frmUser").serialize(),function(rs){
		alert(rs.msg);
		if(rs.flag){//数据保存成功
			//重置form表单的值
			$("#frmUser").get(0).reset();
			//隐藏模态框
			$("#myModal").modal("hide");
			//刷新表格数据
			$("#table").bootstrapTable("refresh");
		}
	},"json");
}
function addData() {
	//初始化提示标签(不显示)
	$("#prompt").css("display","none");
	//清空表单数据
	$("#frmUser")[0].reset();
	//设置后端接口路径
	path = "../addUser";
	//修改模态框标题
	$("#myModalLabel").text("添加用户");
	//初始化密码框样式
	$("#frmUser input[name='upass']").attr("type","password");
	$("#eye").removeClass();
	$("#eye").addClass("glyphicon glyphicon-eye-open");
}
//删除(敏感操作
function delData(){
	//检查用户权限
	if(sessionStorage.getItem("userid")!=1){
		alert("无操作权限");
		return ;
	}
	//获取已选择的记录
	var objs = $("#table").bootstrapTable("getAllSelections");
	if(objs.length==0){//选择项目为0
		alert("请勾选要删除的用户");
		return ;
	}
	if(!confirm("你确定要删除这些用户吗？"))//确认操作
		return;
	//设置后端接口路径
	path = "../delUser";
	var checkids="";//已选择的对象id
	//console.log(objs);//控制台调试信息
	for(let i =0;i<objs.length;i++){
		//遍历每个对象的id值
		if(objs[i].user_id==1 ||
				objs[i].user_id==sessionStorage.getItem("userId")==1){
			//过滤超级管理员用户和当前用户
			alert("操作失败，请检查是否勾选了超级用户或您自己");
			return ;
		}
		//逗号间隔拼接id字串
		checkids += objs[i].user_id;
		if(i!=(objs.length-1)){
			checkids += ",";
		}
	}
	//console.log(checkids);//控制台调试信息
	$.post(path,{checkids:checkids},function(rs){
		alert(rs.msg);//弹出操作结果消息
		if(rs.flag){//数据删除成功
			//刷新表格数据
			$("#table").bootstrapTable("refresh");
		}
	},"json");
}
//修改
function upUser() {
	//获取表格选取行
	var row = $("#table").bootstrapTable("getSelections");
	//console.log(row);//调试信息
	if(row.length!=1){//选取行数不为1
		alert("请选择一行数据");
		return;
	}
	//修改模态框名称
	$("#myModalLabel").text("修改信息");
	//初始化密码框样式
	$("#frmUser input[name='upass']").attr("type","password");
	$("#eye").removeClass();
	$("#eye").addClass("glyphicon glyphicon-eye-open");
	//
	//填充表单
	$("#frmUser input[name='uid']").val(row[0].user_id);
	$("#frmUser input[name='uname']").val(row[0].username);
	$("#frmUser input[name='upass']").val(row[0].password);
	if(row[0].isused==1){
		$("optionsRadios1").attr("checked",true);
	}else{
		$("optionsRadios2").attr("checked",true);
	}
	//
	//设置后端接口地址
	path = "../userUpdate";
	//显示模态框
	$("#myModal").modal("show");
}