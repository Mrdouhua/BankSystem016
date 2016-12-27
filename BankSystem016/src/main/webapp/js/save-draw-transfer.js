$(function(){
	$('#subBtn').click(function(){
		// 获取：金额，账号信息，对方账号信息输入框
		var inputVal = $('input[type="text"]');
		// 将输入框的值赋给data
		var data = {
			"opera_type": $('#storage-type').val(), // 获取储蓄方式
			"money": inputVal.eq(0).val(), // 获取金额
			"raccountid": inputVal.eq(1).val(), // 获取账号信息
			"opp_raccountid": inputVal.eq(2).val() // 获取对方账号信息
		}
		
		$.ajax({
			type: "post",
			url: "/BankSystem_16/index/clerk/money_change.do",
			data: data,
			success: function(data) {
				if(parseInt(data.status) === 1) {
					alert(data.info);
					window.location.href = "/BankSystem_16/index/clerk.do";
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