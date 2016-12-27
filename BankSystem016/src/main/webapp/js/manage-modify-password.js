$(function(){
	$('#subBtn').click(function(){
		var formData = {
			"loginName": $('#roleAccount').val(),
			"password": $('#oldPassword').val(),
			"newpassword": $('#newPassword').val(),
		};
		console.log(formData);
		$.ajax({
			type: "post",
			data: formData,
			url: "/BankSystem_16/admin/updatepassword.do",
			success: function(data){
				if(parseInt(data.status) === 1) {
					alert(data.info);
					window.location.href=data.url;
				} else {
					alert(data.info);
					window.location.href=data.url;
					
				}
			},
			error: function() {
				alert("网络错误");
			}
		})
		return false;
	})
})