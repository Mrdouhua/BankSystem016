$(function(){
	$('#subBtn').click(function(){
		var formData = {
			"loginName": $('#name').val(),
			"password": $('#idnumber').val(),
			"userType": $('#role-power').val(),
		}
		$.ajax({
			type: "post",
			data: formData,
			url: "/BankSystem_16/admin/insert.do",
			success: function(data){
				if(parseInt(data.status) === 1) {
					alert(data.info);
					window.location.href=data.url;
				} else {
					alert(data.info);
				}
			},
			error: function() {
				alert("网络错误");
			}
		})
		return false;
	})
	$('#subBtn').hover(function(){
		$(this).addClass("animated tada");
	},function(){
		$(this).removeClass("animated tada");
	})
})