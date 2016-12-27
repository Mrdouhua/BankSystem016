$(function(){
	var oFile = document.getElementById("file");

	oFile.onchange = function() {
		var validExt = [".doc", ".docx", ".pdf", ".xls", ".xlsx"];
		var fileExt = this.value;
		fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
	    if(validExt.indexOf(fileExt) < 0){
	    	alert(oFile.files[0].name+"是不合格文件!");
	    } else {
		    if(oFile.files[0] && oFile.files[0]){
		    	var reader = new FileReader();
		    	reader.readAsDataURL(file.files[0]);
		    }
	    }      
	}

	$('#subBtn').click(function(){
		var data = new FormData($('#loanMy')[0]);
		console.log(data);
		$.ajax({
			type: "post",
			data: data,
			dataType:"json",
			async:false,
			cache:false,
			contentType:false,
			processData:false,
			url: "/BankSystem_16/loanApplySubmit.do",
			success: function(data) {
				if (parseInt(data.status) ===1) {
					alert(data.info);
					window.location.href = "/BankSystem_16/index/user.do";
				} else {
					alert(data.info);
				}
			},
			error: function() {
				alert("出错了");
			}
		})
		return false;
	})
})