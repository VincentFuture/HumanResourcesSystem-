$(document).ready(function(){
	//初始化表格
	initTable();
	//初始化按钮事件
	btnOnEvent();
});
//按钮事件绑定函数
function btnOnEvent(){
	$("#btnSave").on("click",saveData);//模态框保存按钮
	$("#btnSearch").on("click",searchDept);//查找按钮
	$("#btnMod").on("click",upDept)//修改按钮
	$("#btnDel").on("click",delData);//删除按钮
	$("#btnAdd").on("click",addData);//添加按钮
}
//表格初始化
function initTable(){
	//设置表格的列属性
	//field 对应
	let colArr=[
		{checkbox:true},
		{field:"dept_id",
		title:"编号"},
		{field:"name",
		title:"部门"},
		{field:"count",
		title:"定编"},
		{title:"操作",
			width:"300px",
			formatter:function(value,row){
				//console.log(value);
				return "<div class='row' style='width:100%;'>"+
							"<div class='col-sm-2'>"+
								"<a  onclick='upDept(this,"+row.dept_id+")' class='btn btn-warning btn-sm' >修改</a>" +
							" </div>"+
							"<div class='col-sm-9 col-sm-offset-1' style='visibility:hidden;padding-right:0px;'>"+
								"<div class='input-group'>"+
									"<input type='text' class='form-control' placeholder='部门定编' >"+
									"<span class='input-group-btn' >"+
										"<button class='btn btn-default' type='button'>确定</button>"+
										"<button class='btn btn-default' type='button'>取消</button>"+
									"</span>"+
								"</div>"+
							"</div>"+
						"</div>";
			}}
	];
	//	
	//设置表格的属性
	$("#table").bootstrapTable({
		url:"../searchDept",//设置后端接口url
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
function searchDept() {
	//console.log("调试信息:部门查询按钮事件触发");
	//通过ajax进行操作
	$.post("../searchDept",$("#frmSearch").serialize(),function(rs){
		//根据返回的数据rs重新加载表格数据
		$("#table").bootstrapTable("load",rs);
	},"json")
	//重置查询表单数据
	$("#frmSearch")[0].reset();
}
//修改按钮
function upDept(self,id){
	//console.log("调试信息:部门修改按钮触发");
	//解除按钮点击事件(重复点击导致修改框确定按钮点击后多次弹窗的bug)
	$(self).removeAttr("onclick");
	//获取隐藏的修改输入框并显示
	var moddiv = $(self).parent().next();
	//清除输入框值
	$(self).parent().next().find("input").val("");
	//显示修改框
	moddiv.css("visibility","visible");
	//输入框确定按钮点击事件
	$(moddiv).find("button").eq(0).click(function() {
		//获取输入框值
		var newcount = $(this).parent().prev().val();
		//验证数据
		if(!$.isNumeric(newcount) ){
			alert("需输入数字");
			return;
		}
		if( Number.parseInt(newcount)<=0){
			alert("定编数量错误");
			return;
		}
		//传输数据
		$.post("../updateDept",{ncount:newcount,did:id},function(rs){
			//重新加载表格数据
			$("#table").bootstrapTable("refresh");
			//提醒消息
			alert(rs.msg)
			//隐藏输入框
			moddiv.css("visibility","hidden");
		},"json");
	});
	//输入框取消按钮点击事件
	$(moddiv).find("button").eq(1).click(function(){
		//恢复修改按钮的点击事件
		$(self).attr("onclick","upDept(this,"+id+")");
		
		//隐藏修改框
		moddiv.css("visibility","hidden");
		
		//重新加载表格数据(解决修改按钮与取消按钮反复点击导致最后点击确定按钮多次弹窗的bug)
		$("#table").bootstrapTable("refresh");
	});
}
//添加
function addData() {
	//初始化提示标签
	$("#prompt").css("display","none");
	//清空表单数据
	$("#frmUser")[0].reset();
	//显示模态框
	$("#myModal").modal("show");
}
//添加部门模态框保存按钮事件方法
function saveData() {
	//console.log("调试信息:保存按钮点击事件发生");//调试信息
	//验证表单数据
	var inputObj =$("#frmUser").find("input.form-control");
	for(var i=0;i<inputObj.length;i++){
		if(inputObj[i].value==""){
			//显示提示标签
			$("#prompt").css("display","inline");
			return ;
		}
	}
	//验证部门定编输入框为数字
	if(!$.isNumeric(inputObj.eq(1).val())){
		alert("部门定编数量不合法");
		return;
	}
	//发送数据
	$.post("../addDept",$("#frmUser").serialize(),function(rs){
		alert(rs.msg);
		if(rs.flag){//数据保存成功
			//重置form表单的值
			$("#frmUser").get(0).reset();
			//模态框隐藏
			$("#myModal").modal("hide");
			//刷新表格数据
			$("#table").bootstrapTable("refresh");
		}
	},"json");
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
	if(objs.length==0){
		alert("请勾选要删除的部门");
		return ;
	}
	if(!confirm("你确定要删除这些部门吗？"))
		return;
	var checkids="";//已选择的对象id
	for(let i =0;i<objs.length;i++){
		//遍历对象每个对象的id值
		checkids += objs[i].dept_id;
		if(i!=(objs.length-1)){
			checkids += ",";
		}
	}
	//console.log("调试信息:"+checkids);//控制台调试信息
	$.post("../delDept",{checkids:checkids},function(rs){
		alert(rs.msg);//弹出操作结果消息
		if(rs.flag){//数据删除成功
			//刷新表格数据
			$("#table").bootstrapTable("refresh");
		}
	},"json");
}
