$(function(){
	$("#subBtn").click(function(){
		$.ajax({
			type: "POST",
			url: "/BankSystem_16/index/clerk/password_updata.do",
			data: {
				"raccountid": $("#idCard").val(),
				"old_password": $("#oldPassword").val(),
				"new_password": $("#newPassword").val(),
			},
			success: function(data){
				if(parseInt(data.status) == 1) {
					alert(data.info);
					window.location.href = "/BankSystem_16/clerk/manageaccount.do";
				} else {
					alert(data.info);
				}
			},
			error: function(){
				alert("出错啦！");
			}
		})
	})
	
})