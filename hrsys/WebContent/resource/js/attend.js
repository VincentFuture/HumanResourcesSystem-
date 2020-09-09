$(document).ready(function() {
	dataForSelect();
	//下拉列表绑定选择事件
	$("#eid").on("change",createChart);
});

//下拉列表数据填充
function dataForSelect(){
	$.post("../searchEmployee",function(rs){
		$.each(rs,function(i,n){//i元素下标，n元素
			$("<option/>").attr("value",n.employee_id)
			.html(n.name)
			.appendTo("#eid");
		});
	},"json");
}
//下拉列表的选择事件
function createChart() {
	console.log("调试信息:下拉列表选项改变事件触发");
	//console.log($("#eid").val());
	let geteid = $("#eid").val();//获取选择的eid
	if(geteid==0) return ;
	let data1 = [];//存储日期
	let data2 = [];//存储上班打卡时间
	let data3 = [];//存储下班打卡时间
	
	//调用ajax  给data赋值
	$.ajax({
		async:false,
		url:"../dataForEchars",
		data:{"eid":geteid},
		type:"post",
		dataType:"json",
		success:function(rs){
			data1=rs.data;
			$.each(rs.intime,function(i,n){
				let arr = n.split(":");//分割数据
				let str = arr[0]+"."+arr[1];
				data2.push(parseFloat(str));
			});
			$.each(rs.outtime,function(i,n){
				let arr = n.split(":");//分割数据
				let str = arr[0]+"."+arr[1];
				data3.push(parseFloat(str));
			});
		}
	});
	//设置图像
//	{"isleave":["2","2","1","1","2"],
//	"intime":["08:00:00","08:20:00","08:15:00","08:05:00","08:00:00"],
//	"data":["2020-06-01","2020-06-02","2020-06-03","2020-06-04","2020-06-05"],
//	"outtime":["17:00:00","17:10:00","18:00:00","17:10:00","17:10:00"]};
	
//	{"attend_id":1,
//	"attend_date":"2020-06-01",
//	"isLeave":1,
//	"clock_in":"08:00:00",
//	"clock_out":"17:00:00",
//	"empid":{"employee_id":1,"name":"bob","age":0}}
	

	let myChart = echarts.init($("#myChart").get(0));
	let option = {
            title: {
                text: '出勤打卡情况统计'
            },
            legend: {
                data: ['上班时间', '下班时间']
            },
            xAxis: {
            	type:'category',
                data: data1
            },
            yAxis: {
            	type:'value'
            },
            series: [{
            	name: '上班时间',
                type: 'line',
                data: data2
            },
            {
            	name: '下班时间',
            	type: 'line',
                data: data3
            }]
        };
	// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}