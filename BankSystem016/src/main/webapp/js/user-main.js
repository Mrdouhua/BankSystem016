$(function(){
	(function(){
		$.ajax({
			type: "get",
			url: "/BankSystem_16/index/getuserinfo.do",
			success: function(data){
				$(".user-pic-img")[0].src = "/BankSystem_16/"+data.photo;
				$(".username-a").html(data.name);
			}
		})
	})()
	
	// 鼠标移入导航栏显示相应内容
	$('.nav-li').hover(function(){
		$(this).children('.inner-nav').css("display","block");
		$(this).children('.inner-nav').stop().animate({"height":"300px"},200);
	},function(){
		$(this).children('.inner-nav').stop(true,true).animate({"height":"0"},200,function(){
			$(this).css("display","none");
		});
	})
})