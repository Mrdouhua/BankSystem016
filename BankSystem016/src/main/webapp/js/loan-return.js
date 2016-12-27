$(function(){
	$('#subBtn').click(function(){
		$.ajax({
			type: "post",
			data: $('.explain').val(),
			url: "",
			success: function(data) {
				if (data.status ===1) {
					alert("提交成功");
				} else {
					alert("提交失败");
				}
			},
			error: function() {
				alert("出错了");
			}
		})
	})
	return false;
})