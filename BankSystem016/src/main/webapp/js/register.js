$(function(){
	// 获取上传的头像
	var oFile = document.getElementById("file");
	var picShow = document.getElementsByClassName("pic-show")[0];
	var image = '';

	function getImage() {
	    if(!/image\/\w+/.test(oFile.files[0].type)){
	    	alert(oFile.files[0].name+"不是图片!");
	    } else {
		    if(oFile.files[0] && oFile.files[0]){
		    	var reader = new FileReader();
		    	reader.onload = function(evt){
		    		var newImg = document.createElement('img');
		    		newImg.src = evt.target.result;
		    		image = evt.target.result;
		    		picShow.innerHTML = '';
		    		picShow.appendChild(newImg);
		    	}
		    	reader.readAsDataURL(file.files[0]);
		    }
	    }       
	}

	oFile.onchange = function(){
		getImage();
	}
	$('#subBtn').click(function(){
		var data = new FormData($('#account')[0]);
		$.ajax({
			url: "/BankSystem_16/index/register.do",
			type: "post",
			data: data,
			dataType:"json",
			async:false,
			cache:false,
			contentType:false,
			processData:false,
			success: function(data){
				if(parseInt(data.status) == 1) {
					alert(data.info);
					window.location.href = data.url;
				} else {
					alert("注册失败");
				}
			},
			error: function(){
				alert("出错啦！");
			}
		})
		return false;
	})
})