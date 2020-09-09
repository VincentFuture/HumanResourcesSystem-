$(document).ready(function(){
	
	//调用表格初始化函数
	initTable();
	//初始化下拉选项
	initSelect();
	//调用负责按钮绑定事件的函数
	btnOnEvent();
});
//按钮事件绑定函数
function btnOnEvent(){
		$("#btnSearch").on("click",searchAttend);
		$("#addattend").on("click",addData);//生成打卡记录按钮
}
//下拉框初始化
function initSelect(){
	$("#enameList").empty();
	$("<option></option>").val(0).text("请选择").appendTo("#enameList");
	$.post("../searchEmployee",function(rs){
		$.each(rs,function(i,n){
			$("<option></option>").val(n.employee_id).text(n.name)
			.appendTo("#enameList");
		})
		
	});
}
//表格初始化
function initTable(){
	//设置表格的列属性
	let colArr = [
			{
				checkbox : true
			},
			{
				field : "attend_id",
				title : "编号"
			},
			{
				field : "empid.name",
				title : "雇员姓名"
			},
			{
				field : "attend_date",
				title : "打卡日期"
			},
			{
				field : "isLeave",
				title : "是否请假",
				formatter:function(value){
					switch (value) {
					case 1:
						return "<span style='color:red;'>是<span>";
					case 2:
						return '否';
					default:
						return '未知';
					}
				}
			},
			{
				field : "clock_in",
				title : "上班时间"
			},
			{
				field : "clock_out",
				title : "下班时间"
			}];
	//设置表格的属性
	$("#table").bootstrapTable({
		url:"../searchAttend",//设置后端接口url
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
function searchAttend() {
	//通过ajax进行操作
	//console.log("searchUserEvent");
	$.post("../searchAttend",$("#frmSearch").serialize(),function(rs){
		//根据返回的数据rs重新加载表格数据
		$("#table").bootstrapTable("load",rs);
	},"json");
	//重置查询表单数据
	$("#frmSearch")[0].reset();
}
//生成打卡记录
function addData(){
	//console.log("调试信息:生成");
	if($("#enameList").val()==0){
		alert("请选择用户");
		return ;
	}
	if($("#month").val()>12||$("#month").val()<1){
		alert("月份不正确");
		return ;
	}
	$.post("../genAttend",$("#frmAdd").serialize(),function(rs){
		if(rs.msg!=null){
			alert(rs.msg);
		}else{
			alert("签到成功");
		}
		
		$("#table").bootstrapTable("load",rs);
		//$("#table").bootstrapTable("refresh");
	},"json");
	
}