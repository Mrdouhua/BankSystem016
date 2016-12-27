$(function(){
	alert("kkkkk");
	$.ajax({
		type: "get",
		url: "/BankSystem_16/loanScheduleQuery.do",
		dataType: "json",
		success: function(data){
			var oData = data;
			console.log(oData);
			var  setArr = $(".process-data td");
			$(setArr[0]).html(data.name);
			$(setArr[1]).html(data.loanType);
			$(setArr[2]).html(data.creatTime);
			$(setArr[3]).html(data.status);
		},
		error: function() {
			alert("网络错误");
		}
	})
})
