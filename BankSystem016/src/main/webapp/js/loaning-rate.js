$(function(){
	(function(){
		$.ajax({
			type: "get",
			data: {
				"time": "2015-2016"
			},
			url: "/BankSystem_16/loanInterestTableQuery.do",
			success: function(data){
					var oData = data;
					oData.map(function(item){
						if(item["deposittype"] == "LA" && item["cycle"] == "L1") {
							$("#short-loan").html(item["interestvalue"]);
						} else if(item["deposittype"] == "LB" && item["cycle"] == "L2") {
							$("#long-loan1").html(item["interestvalue"]);
						} else if(item["deposittype"] == "LB" && item["cycle"] == "L3") {
							$("#long-loan2").html(item["interestvalue"]);
						}
					})
			},
			error: function() {
				alert("网络错误");
			}
		})
	})()
	
	$("#date").change(function(){
		console.log($("#date").val());
		$.ajax({
			type: "get",
			data: $("#date").val(),
			url: "/BankSystem_16/loanInterestTableQuery.do",
			success: function(data){
					var oData = data;
					oData.map(function(item){
						if(item["deposittype"] == "LA" && item["cycle"] == "L1") {
							$("#short-loan").html(item["interestvalue"]);
						} else if(item["deposittype"] == "LB" && item["cycle"] == "L2") {
							$("#long-loan1").html(item["interestvalue"]);
						} else if(item["deposittype"] == "LB" && item["cycle"] == "L3") {
							$("#long-loan2").html(item["interestvalue"]);
						}
					})
			},
			error: function() {
				alert("网络错误");
			}
		})
	})
	
	$('#subBtn').click(function(){
		var tdData = {
			"v1": $("#short-loan").text().trim(),
			"v2": $("#long-loan1").text().trim(),
			"v3": $("#long-loan2").text().trim()
		};

		console.log(tdData);
		$.ajax({
			type: "post",
			data: tdData,
			url: "/BankSystem_16/admin/editloan.do",
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
