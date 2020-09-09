$(document).ready(function() {
	dataForSelect();
	
	//查询按钮绑定点击事件
	
	$("#btnSearch").on("click",createChart);
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
//查询按钮点击事件
function createChart() {
	//console.log("调试信息:下拉列表选项改变事件触发");
	
	
	//校验数据;
	let geteid = $("#eid").val();//获取选择的eid;
	if(geteid==0){
		$("#btnSearch").popover('show');
		setTimeout(() => {
			$("#btnSearch").popover('destroy');
		}, 1000);
		return ;
	}
	let data1 = [];//存储日期
	let data2 = [];//存储信息
	let data = [];
	let month = $("#date_choose").val();
	if(month==""||month==null){
		$("#btnSearch").popover('show');
		setTimeout(() => {
			$("#btnSearch").popover('destroy');
		}, 1000);
		return ;
	}
	//调用ajax  给data赋值
	$.ajax({
		async:false,
		url:"../dataForEchars",
		data:{"eid":geteid},
		type:"post",
		dataType:"json",
		success:function(rs){
			data1=rs.data;
			data2=rs.isleave;
			for(var i =0;i<data1.length;i++){
				data.push([data1[i],data2[i]]);
			}
			
		}
	});
	
	//设置图像
	let myChart = echarts.init($("#myChart").get(0));
	let option = {
			
            calendar: [{
                left: 'center',
                top: 'middle',
                cellSize: [70, 70],
                yearLabel: {
                	show: true,
                	margin:80
                },
                orient: 'vertical',
                dayLabel: {
                    firstDay: 1,
                    nameMap: 'cn'
                },
                monthLabel: {
                    show: true
                },
                range: month
            }],
            
            series: [{
            	type: 'scatter',
                coordinateSystem: 'calendar',
                symbolSize: 1,
                label: {
                    show: true,
                    formatter: function (params) {
                    	var d = echarts.number.parseDate(params.value[0]);
                    	return d.getDate() ;
                    },
                    color: '#333'
                },
                data: data
            },{
                type: 'scatter',
                coordinateSystem: 'calendar',
                symbolSize: 1,
                label: {
                    show: true,
                    formatter: function (params) {
                        return '\n\n\n' + (params.value[1]==1?"请假":"");
                    },
                    fontSize: 14,
                    fontWeight: 700,
                    color: '#a00'
                },
                data: data
            },{
                type: 'scatter',
                coordinateSystem: 'calendar',
                symbolSize: 1,
                label: {
                    show: true,
                    formatter: function (params) {
                        return '\n\n\n' + (params.value[1]==2?"上班":"");
                    },
                    fontSize: 14,
                    fontWeight: 700,
                    color: '#0a0'
                },
                data: data
            }]
        };
	// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
  
}