// 主页login表单验证
$(function(){
	let oUsername = $('#username'),
		oPassword = $('#password'),
		oBtn = $('#submit-btn'),
		oTip1 = $('.user-tip'),
		oTip2 = $('.pass-tip'),
		status1 = false, // 用户名框状态
		// 密码框状态,当二者都为true时才能点击提交按钮发送ajax
		status2 = false;

	// 验证用户名与密码
	// 用户名为2到12位大小写字母或数字
	let reg1 = /^[A-Za-z0-9]{2,12}$/;
	// 密码为6-16位大小写字母或数字
	let reg2 = /^[A-Za-z0-9]{6,16}$/;

	// 用户名框失去焦点
	oUsername.blur(function() {
		// 用户名格式验证不正确，在密码框下面反馈
		if(!reg1.test(oUsername.val())){
			oTip1.css({"color":"red","display":"inline-block"});
			oTip1.html("用户名为2-12位大小写字母或数字!");
			return false;
		}else {
			oTip1.css({"color":"green","display":"inline-block"});
			oTip1.html("用户名输入合格!");
			status1 = true;
		}
	})

	// 密码框失去焦点
	oPassword.blur(function(){
		// 密码格式验证不正确，在密码框下面反馈
		if(!reg2.test(oPassword.val())){
			oTip2.css({"color":"red","display":"inline-block"});
			oTip2.html("密码为6-16位大小写字母或数字!");
			return false;
		}else {
			oTip2.css({"color":"green","display":"inline-block"});
			oTip2.html("密码输入合格!");
			status2 = true;
		}
	})

	// 提交表单
	oBtn.click(function(){
		// 如果用户名和密码格式没正确，报错
		if(!status1 && !status2) {
			oTip1.html("用户名为2-12位大小写字母或数字!")
			.css({"color":"red","display":"inline-block"});

			oTip2.html("密码为6-16位大小写字母或数字!")
			.css({"color":"red","display":"inline-block"});
			return false;
		}else {
			
		var formData = {
			"username": oUsername.val(),
			"password": oPassword.val()
		};
	
		$.ajax({
			type: "post",
			url: "/BankSystem_16/index/login.do",
			data: formData,
			dataType: "json",
			success: function(obj) {
				console.log(obj);
				// 用户名密码验证正确
				if(obj.status != "0"){
					window.location.href = obj.url;
				}
				// 用户名密码后台验证不正确
				else {
					alert(obj.info);
				}
			},
			// ajax没发送成功
			error: function() {
				alert("网络不好，登陆失败!");
				location.reload();
			}
		})
		return false;
		}
	})
})