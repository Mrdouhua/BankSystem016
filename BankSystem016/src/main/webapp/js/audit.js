$(function(){
	//字符串查询
	function getSearchString(key) {
	    // 获取URL中?之后的字符
	    var str = location.search;
	    str = str.substring(1,str.length);
	       
	    // 以&分隔字符串，获得类似name=xiaoli这样的元素数组
	    var arr = str.split("&");
	    var obj = {};
	       
	    // 将每一个数组元素以=分隔并赋给obj对象    
	    for(var i = 0; i < arr.length; i++) {
	        var tmp_arr = arr[i].split("=");
	        obj[decodeURIComponent(tmp_arr[0])] = decodeURIComponent(tmp_arr[1]);
	    }
	    return obj[key];
	}
	var idNumber = getSearchString('idNumber');
	console.log(idNumber);

	$.ajax({
			type: "get",
			data: {
				"idnumber": idNumber
			},
			url: "/BankSystem_16/loanApproval.do",
			success: function(data){
				console.log(data);
				var oData = data[0];
				$('#name').html(oData.name);
				$('#loan-type').html(oData.loanType);
				$("#amount").html(oData.capital);
				$("#card-num").html(oData.cardID);
				$("#download-a").attr("href", "/BankSystem_16"+oData.proveFile);
			},
			error: function() {
				alert("网络错误");
			}
		})
	$('#subBtn').click(function(){
		var formData = {
			"idNumber": idNumber,
			"audit": $('input[type="radio"]:checked').val(),
			"agreement": $(".agreement").val()
		};
		$.ajax({
			type: "get",
			data: formData,
			url: "/BankSystem_16/loanApprovalSubmit.do",
			success: function(data){
				if(data.status === 1) {
					alert("审核成功");
					window.location.href = data.url;
				} else {
					alert("审核失败");
				}
			},
			error: function() {
				alert("网络错误");
			}
		})
		return false;
	})
})