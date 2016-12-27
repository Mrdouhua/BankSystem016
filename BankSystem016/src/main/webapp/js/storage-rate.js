$(function(){
	alert("hhhh");
	// 显示当前时间
	(function time(){
		var oTime = $(".time-show"),
			now = new Date(),
			year = now.getFullYear(),
			month = now.getMonth() + 1,
			day = now.getDate(),
			timer = null;

		// month = check(month);
		day = check(day);

		var str = year+"."+month+"."+day;
		oTime.html(str);

		function check(t) {
			return t < 10 ? "0"+t : t; 
		}

		timer = setInterval(time, 60000);
	})();
	
	var formData = {
			"time": "2015-2016"
	};
	
	function getData(formData){
		$.ajax({
			type: "get",
			data: formData,
			url: "/BankSystem_16/index/user/interest_show.do",
			success: function(data){
					console.log(data);
					var oData = data;
					oData.map(function(item){
						if(item["deposittype"] == "DA" && item["cycle"] == "D1") {
							$(".setData1").html(item["interestvalue"]);
						} else if(item["deposittype"] == "DA" && item["cycle"] == "D2") {
							$(".setData2").html(item["interestvalue"]);
						} else if(item["deposittype"] == "DA" && item["cycle"] == "D3") {
							$(".setData3").html(item["interestvalue"]);
						} else if(item["deposittype"] == "DA" && item["cycle"] == "D4") {
							$(".setData4").html(item["interestvalue"]);
						} else if(item["deposittype"] == "DA" && item["cycle"] == "D5") {
							$(".setData5").html(item["interestvalue"]);
						} else if(item["deposittype"] == "DA" && item["cycle"] == "D6") {
							$(".setData6").html(item["interestvalue"]);
						} else if(item["deposittype"] == "DB" && item["cycle"] == "D1") {
							$(".setData7").html(item["interestvalue"]);
						} else if(item["deposittype"] == "DB" && item["cycle"] == "D2") {
							$(".setData8").html(item["interestvalue"]);
						} else if(item["deposittype"] == "DB" && item["cycle"] == "D3") {
							$(".setData9").html(item["interestvalue"]);
						} else if(item["deposittype"] == "DC" && item["cycle"] == "nu") {
							$(".setData10").html(item["interestvalue"]);
						}
					})
			},
			error: function() {
				alert("网络错误");
			}
		})
	}
	getData(formData);
	
	$(".date").change(function(){
		var formData = $(".date").val();
		getData(formData);
	})
	
	$('#subBtn').click(function(){
		var tdData = {
			"v1": $('.setData1').text().trim(),
			"v2": $('.setData2').text().trim(),
			"v3": $('.setData3').text().trim(),
			"v4": $('.setData4').text().trim(),
			"v5": $('.setData5').text().trim(),
			"v6": $('.setData6').text().trim(),
			"v7": $('.setData7').text().trim(),
			"v8": $('.setData8').text().trim(),
			"v9": $('.setData9').text().trim(),
			"v10": $('.setData10').text().trim()
		};
		console.log(tdData);
		$.ajax({
			type: "post",
			data: tdData,
			url: "/BankSystem_16/admin/editloaninterest.do",
			success: function(data){
				if(parseInt(data.status) == 1) {
					alert(data.info);
					window.location.reload();
				} else {
					alert(data.info);
					window.location.reload();
				}
			},
			error: function() {
				alert("网络错误");
				window.location.reload();
			}
		})
	})

})