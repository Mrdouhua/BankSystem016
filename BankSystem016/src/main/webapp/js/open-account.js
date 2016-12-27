$(function(){
	$('#subBtn').click(function(){
		var data = {
			"name": $('#name').val(),
			"idnumber": $('#idnumber').val(),
			"password": $('#password').val(),
		};
		console.log(data);
		$.ajax({
			type: "post",
			data: data,
			url: "/BankSystem_16/index/user/register_rmb.do",
			success: function(data){
				if(parseInt(data.status)==1) {
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
})