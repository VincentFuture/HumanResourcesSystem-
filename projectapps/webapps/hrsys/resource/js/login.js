$(document).ready(function(){
	//绑定登陆按钮的点击事件
	$("#btnLG").click(login);
	//回车触发登录按钮
	$("input").keyup(function(key){
		key.preventDefault();//阻止元素发生默认的行为
		if(key.keyCode==13) //若为回车调用登录函数
			login().call(this);
	});
	//
});
//验证表单是否为空的函数
function login() {
	//调用isInputEmpty保证数据不为空
	let flag1=isInputEmpty("uname","用户名不能为空");//用户名
	if(!flag1) return;
	let flag2=isInputEmpty("upass","密码不能为空");//密码
	if(flag1&&flag2){
		//AJAX链接后端接口
		//rs为返回数据
		$.post("login",$("#frmLogin").serialize(),function(rs){
			if(rs==null){
				alert("用户名或密码错误");
			}else{
				//将返回的数据存在本地的sessionStorage对象中
				let temp = JSON.stringify(rs);//将返回的数据对象(js对象)转成json格式的字符串
				sessionStorage.setItem("uinfo",temp);//将JSON格式的字符串存储在sessionStorage
				//获取userid并存储到session中
				sessionStorage.setItem("userid",JSON.parse(sessionStorage.getItem("uinfo")).user_id);
				//console.log(sessionStorage.getItem("userid"));
				$(location).attr("href","index.html");//跳转首页
			}
		},"json");
	}
}
//空表单判断函数
function isInputEmpty(txt,msg){
	//获取表单元素的value属性值
	let v2 = $("input[name='"+txt+"']").val();
	//判断v2是否为空
	if(v2.length==0 || v2==null || v2==undefined){
		//返回为空
		alert(msg);
		return false;
	}else{
		return true;
	}
}