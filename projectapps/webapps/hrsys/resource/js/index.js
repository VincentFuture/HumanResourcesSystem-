$(document).ready(function(){
	//调用登录判断函数，判断是否登录
	isLogin();//若未登录 跳转登录页面
	//自适应高度
//    window.addEventListener('resize',function(){
//		$("body").css("height",window.innerHeight);
//		/*
//		 console.log("调试信息:窗口宽度:"+window.innerWidth+"\n"+
//				 "调试信息:窗口高度:"+window.innerHeight+"\n"+
//				 "调试信息:body高度:"+$("body").css("height"));
//				 */
//    });
    //
	//执行时钟
	setInterval(function() {
		var date = new Date();
		$("#showtime").html(date.toLocaleTimeString());
	}, 1000);
	//
	
	//给安全退出按钮绑定事件
	$("#btnExit").on("click",exit);
	//左侧菜单图标样式变换
	$("#menu a span").not("#index_span").addClass("glyphicon glyphicon-chevron-right");//初始化图标样式
	$("#menu a").not("#index_a").on("click",function(){//图标样式变换
		var sp = $(this).children();//sp 即span标签
		if(sp.attr("class")=="glyphicon glyphicon-chevron-right"){
			sp.removeClass();
			sp.addClass("glyphicon glyphicon-chevron-down")
		}else{
			sp.removeClass();
			sp.addClass("glyphicon glyphicon-chevron-right")
		}
	});
	//
	//菜单样式初始化
	$("#menu a:gt(0)").css({"background-color":"#DB8AF0",//菜单一级选项
	 					"border":"0px",
	 					"margin-bottom":"1px"});
	$("#menu div a").css({"background-color":"#E6BAF1",//菜单二级选项
	 		"margin":"1px"});
	//
	//导航路径生成函数
	addBreadcrumb();
});
//登陆判断函数
function isLogin() {
	let info=sessionStorage.getItem("uinfo");
	if(info==null || info==undefined || info.length==0){
		//清空页面元素
		$(this).remove();
		//更新页面元素
		$("body").html("<h1 style='text-align:center;color:white;" +
				"margin-top:200px;'>" +
				"您还未登录,将在" +
				"<span>3</span>" +
				"秒后跳转" +
				"<a href='login.html' style='color:red;'>" +
				"登陆页</a></h1>").css("background-color","black");
		//console.log("调试消息：：：：页面修改");
		//alert("请先登录");
		//$(location).attr("href","login.html");//重定位到登陆页面
		var m=3;//临时变量：计时数字
		var mTime = setInterval(() => {//设置延时：1s
			m--;
			$("span").text(m);
		}, 1000);
		
		setTimeout(function(){
			$(location).attr("href","login.html");
			clearInterval(mTime);//停止计时事件
		}, 4000);
		return ;
	}
	//将字符串转换成JSON对象
	let temp = JSON.parse(info);
	$("#uinfo").html(temp.username+" 您好！");
}
//安全退出函数
function exit() {
	if(confirm("确认退出吗？")){//确定
		//删除sessionStorage记录的信息
		sessionStorage.clear();
		$(location).attr("href","login.html");//退出后重定向到登陆页
	}
}
//路径导航生成函数
function addBreadcrumb(){
	//给左侧导航菜单中所有a标签添加点击事件
	$("#pcenter a,#menu div a,#index_a").on("click",function(){
		//清空路径导航已存在的内容
		$("#breadcrumb").empty();
		//重新创建首页路径
		$("<li/>").append($("<a/>").text("首页").attr({
			"href":"welcome.html",
			"target":"frmContent"
		})).appendTo("#breadcrumb");
		let url = $(this).attr("href");//获取跳转路径
		let txt=$(this).text();
		if(url=="welcome.html") return;//过滤首页跳转路径
		//向首页路径后追加子路径
		$("<li/>").append($("<a/>").text(txt).attr({
			"href":url,
			"target":"frmContent"
		})).appendTo("#breadcrumb");
	});
}