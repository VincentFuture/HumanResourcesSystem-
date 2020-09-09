/**
 * 
 */
$(document).ready(function(){
	//调用表格初始化函数
	initTable();
	//调用负责按钮绑定时间的函数
	btnOnEvent();
	//初始化数据列表
	initDataList();
});
//按钮事件绑定函数
function btnOnEvent(){
		$("#btnSearch").on("click",searchDict);//查询按钮事件
		$("#btnSave").on("click",saveData);//模态框保存按钮事件
		$("#adddict").on("click",addData);//添加字典按钮事件绑定
		$("#deldict").on("click",delData);//删除字典按钮事件绑定
		$("#moddict").on("click",upData);//修改字典按钮事件绑定
}
//数据列表(dictType)初始化
function initDataList() {
	//dictType
	//查询数据
	$("#dictType").empty();
	$.post("../searchDT",function(rs){
    	$.each(rs,function(i,n){//i元素下标，n元素
        	var optionTemp = $("<option/>").attr("value",n.type_id)
        	.text(n.type_name);
			optionTemp.appendTo("#search_dictTypeList,#add_dictTypeList");
        	//optionTemp.appendTo("#add_dictTypeList");
        	
    	});
    },"json");
	
}
//表格初始化
function initTable(){
	//设置表格的列属性
	let colArr = [ {
		checkbox : true
		}, {
			field : "dct_id",
			title : "编号"
		}, {
			field : "type_name",
			title : "字典类目",
		}, {
			field : "title",
			title : "字典值"
		}
	];
	//	
	//设置表格的属性
	$("#table").bootstrapTable({
		url:"../searchDict",//设置后端接口url
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
function searchDict() {
	//通过ajax进行操作
	//console.log("searchUserEvent");
	$.post("../searchDict",$("#frmSearch").serialize(),function(rs){
		//根据返回的数据rs重新加载表格数据
		$("#table").bootstrapTable("load",rs);
	},"json");
	//重置查询表单数据
	$("#frmSearch")[0].reset();
	
}



//添加 修改 删除
var path = "";//声明后端接口地址的全局变量
//保存按钮事件函数
function saveData() {
	//console.log("保存按钮点击事件发生");//调试信息
	//验证表单数据不为空
	var inputObj =$("#frmDict").find("input.form-control");
	for(var i=0;i<inputObj.length;i++){
		if(inputObj[i].value==""){
			//显示提示标签
			$("#prompt").css("display","inline");
			return ;
		}
	}
	//发送表单
	$.post(path,$("#frmDict").serialize(),function(rs){
		alert(rs.msg);//弹出操作结果消息
		if(rs.flag){//数据保存成功
			//重置form表单的值
			$("#frmDict").get(0).reset();
			//模态框隐藏
			$("#myModal").modal("hide");
			//刷新表格数据
			$("#table").bootstrapTable("refresh");
		}
	},"json");
}
//增加数据
function addData() {
	//初始化提示标签
	$("#prompt").css("display","none");
	//清空表单数据
	$("#frmDict")[0].reset();
	//启用typeid值的修改
	$("#frmDict select[name='dictTypeId']").removeAttr("disabled");
	//设置后端接口路径
	path = "../addDict";
	//修改模态框标题
	$("#myModalLabel").text("添加字典");
}
//删除
function delData(){
	//检查用户权限
	if(sessionStorage.getItem("userid")!=1){
		alert("无操作权限");
		return ;
	}
	//获取已选择的记录
	var objs = $("#table").bootstrapTable("getAllSelections");
	if(objs.length==0){
		alert("请勾选要删除的条目");
		return ;
	}
	
	if(!confirm("你确定要删除这些条目吗,这可能会造成巨大影响"))
		return;
	//设置后端接口路径
	path = "../delDict";
	
	var checkids="";//已选择的对象id
	//console.log(objs);//控制台调试信息
	for(let i =0;i<objs.length;i++){
		//遍历对象每个对象的id值
		checkids += objs[i].dct_id;
		if(i!=(objs.length-1)){
			checkids += ",";
		}
	}
	//console.log("调试信息:chekids:"+checkids);//控制台调试信息
	$.post(path,{checkids:checkids},function(rs){
		alert(rs.msg);//弹出操作结果消息
		if(rs.flag){//数据删除成功	
			//刷新表格数据
			$("#table").bootstrapTable("refresh");
		}
	},"json");
}
//修改
function upData() {
	//获取表格选取行
	var row = $("#table").bootstrapTable("getSelections");
	//console.log("调试信息:"+row);//调试信息
	//判断选取行数
	if(row.length!=1){
		alert("请选择一行数据");
		return;
	}
	//修改模态框名称
	$("#myModalLabel").text("修改字典");
	//禁用id值的修改
	$("#frmDict select[name='dictTypeId']").attr("disabled",true);
	//填充表单
	$("#frmDict input[name='dictId']").val(row[0].dct_id);//填充dict_id
	//填充字典条目
	$("#frmDict select[name='dictTypeId'] option").removeAttr('selected');//初始化option selected属性
	$("#frmDict select[name='dictTypeId'] option:contains("+row[0].type_name+")").attr('selected','selected');
	$("#frmDict input[name='dictTitle']").val(row[0].title);//填充字典值
	//
	//绑定焦点事件
	$("#frmDict input[name='dictTitle']").focusin(function(){
		$("#frmDict input[name='dictTitle']").val("");//获取焦点时清空输入框
	});
	//
	//设置后端接口地址
	path = "../updateDict";
	//显示模态框
	$("#myModal").modal("show");
}