$(function(){
	var oLi = $('.storage-li');
	var oLists = $('.lists');

	oLi.click(function(){
		var index = $(this).index();
		oLi.eq(index).addClass('tit-active').siblings().removeClass('tit-active');
		oLists.eq(index).addClass('lists-active').siblings().removeClass('lists-active');
	})
})