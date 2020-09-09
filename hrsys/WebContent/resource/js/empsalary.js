$(document).ready(function(){
	//初始化表格
	initTable();
	//初始化按钮事件
	btnOnEvent();
});
//按钮事件绑定函数
function btnOnEvent(){
	$("#btnSearch").on("click",searchDept);
}
//表格初始化
function initTable(){
	//设置表格的列属性
	//field 对应实体类中封装的字段名称
	let colArr = [ {
		checkbox : true,
		checkboxEnabled:false
	}, {
		field : "emp.employee_id",
		title : "编号"
	}, {
		field : "emp.name",
		title : "雇员姓名"
	}, {
		field : "sStandard",
		title : "薪资标准",
		formatter:function(value){
			if(value==0)
				return "无薪资信息";
			else return value;
		}
	}, {
		title:"操作",
		width:"300px",
		formatter:function(value,row){
			//console.log(value);
			return "<div class='row' style='width:100%;'>"+
						"<div class='col-sm-2'>"+
							"<a  onclick='upES(this,"+"["+row.emp.employee_id+","+row.esId+"])' class='btn btn-warning btn-sm' >修改</a>" +
						" </div>"+
						"<div class='col-sm-9 col-sm-offset-1' style='visibility:hidden;padding-right:0px;'>"+
							"<div class='input-group'>"+
								"<input type='text' class='form-control' placeholder='薪资标准' >"+
								"<span class='input-group-btn' >"+
									"<button class='btn btn-default' type='button'>确定</button>"+
									"<button class='btn btn-default' type='button'>取消</button>"+
								"</span>"+
							"</div>"+
						"</div>"+
					"</div>";
		}
	}];
	//设置表格的属性
	$("#table").bootstrapTable({
		url:"../searchEmpSalary",//设置后端接口url
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
	//通过ajax进行操作
	console.log("searchUserEvent");
	$.post("../searchEmpSalary",$("#frmSearch").serialize(),function(rs){
		//根据返回的数据rs重新加载表格数据
		$("#table").bootstrapTable("load",rs);
	},"json")
	//重置查询表单数据
	$("#frmSearch")[0].reset();
}
//修改(薪资标准)按钮
function upES(self,ids){
	//console.log("调试信息:薪资标准修改按钮触发");
	//解除按钮点击事件(重复点击导致修改框确定按钮点击后多次弹窗的bug)
	$(self).removeAttr("onclick");
	//获取元素:隐藏的修改输入框
	var moddiv = $(self).parent().next();
	//清除输入框值
	$(self).parent().next().find("input").val("");
	//显示修改框
	moddiv.css("visibility","visible");
	//输入框确定按钮点击事件
	$(moddiv).find("button").eq(0).click(function() {
		//获取输入框值
		var newsalary = $(this).parent().prev().val();
		//验证数据
		if(!$.isNumeric(newsalary) ){//过滤非数字字串
			alert("需输入数字");
			return;
		}
		if( Number.parseInt(newsalary)<=0){//过滤负数
			alert("薪资数量错误");
			return;
		}
		//传输数据
		$.post("../updateEmpSalary",{nsalary:newsalary,eid:ids[0],esid:ids[1]},function(rs){
			//重新加载表格数据
			$("#table").bootstrapTable("refresh");
			//提醒消息
			alert(rs.msg);
			//隐藏输入框
			moddiv.css("visibility","hidden");
		},"json");
	});
	//输入框取消按钮点击事件
	$(moddiv).find("button").eq(1).click(function(){
		//恢复修改按钮的点击事件
		$(self).attr("onclick","upDept(this,"+ids+")");
		//显示修改框
		moddiv.css("visibility","hidden");
		//重新加载表格数据
		$("#table").bootstrapTable("refresh");
	});
}
