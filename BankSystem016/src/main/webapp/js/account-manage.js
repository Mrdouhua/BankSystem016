$(function(){
	// 设置全局命名空间
	var MO = {} || MO;

	// 请求页面资源
	MO.askData = (function(){
		var current = 1,
			pageNum = 1,
			min = 0,
			max = 5;

		// 包装请求后台页面内容ajax的函数
		function askDataAjax() {
			var myPromise = new Promise(function(resolve,reject){
				$.ajax({
					type: "post",
					url: "/BankSystem_16/index/clerk/account_Control.do",
					data: {
						"current": current
					},
					dataType: "json",
					success: function(data) {
						resolve(data);
					},
					error: function(){
						reject(new Error("错啦"));
					}
				})
			})

			myPromise.then(function(data){
				var oData = data,
				    currentData = oData.datas,
				    str = '';
				pageNum = oData.pagenum;

				currentData.map(function(item){
					str +=  '<tr class="tr">' + 
							'	<td class="td150">' +
							'		<input type="radio" name="style" class="manage-style" value="注销">' +
							'		<span>'
										+"注销"+
							'		</span>' +
							'		<input type="radio" name="style" class="manage-style" value="挂失">' +
							'		<span>'
										+"挂失"+
							'		</span>' +
							'	</td>' +
							'	<td class="td150">'
									+item.name+
							'	</td>' +
							'	<td class="td150">'
									+item.raccountid+
							'	</td>' +
							'	<td class="td150">'
									+item.creattime+
							'	</td>' +
							'	<td class="td150">' +
							'		<a href="#" class="audit-state">'
										+item.status+
							'		</a>' +
							'	</td>' +
							'</tr>'			
				})
				$('tbody').html(str);

				var aStr = '';
				for(var i = 1;i <= pageNum;i++){
					aStr += "<li>" +i+ "</li>";
				}
				// 添加页码
				$('ul.pageNum').html(aStr);
				$('ul.pageNum li').hide();
				isLight(min,max,current-1);
				$("ul.pageNum li").eq(current - 1).addClass('active').siblings().removeClass('active');
				// 添加共多少页
				$('.allNum').html("共" +pageNum+ "页");
			}).then(function(){
				// 点击页码切换
				$('ul.pageNum li').click(function(){
					current = parseInt($(this).text());
					isLight(min,max,current-1);
					$("ul.pageNum li").eq(current - 1).addClass('active').siblings().removeClass('active');
					askDataAjax();
				})
				// 点击激活按钮
				$('.audit-state').click(function(){
					var cardID = $(this).parent().siblings().eq(2).text().trim();
					var aa = "激活";
					var oAtatus;
					var current = 1;
					var t = $(this).text().trim().toString();
					if(t != aa){
						switch (t) {
							case '未激活': oAtatus = 0;
										break;
							case '冻结': oAtatus = 3;
										break;
						};
						$.ajax({
							type: "post",
							data: {
								"raccountid": cardID,
								"status": oAtatus
							},
							url: "/BankSystem_16/clerk/editCardStatus.do",
							success: function(data){
								if(parseInt(data.status) == 1){
									alert(data.info);
									askDataAjax();
								} else {
									alert(data.info);
								}
							},
							error: function(){
								alert("出错啦");
								askDataAjax();
							}
						})
					} else {
						alert("已经是激活");
					}
					return false;
				})
			}).catch(function(value){
				alert(value,"出错啦!");
			})
		}

		askDataAjax();

		// 高亮显示当前页码
		function isLight(min, max, current){
			if(current <= max/2) {
				min = 0;
				max = max;
			} else if(pageNum - current <= Math.ceil(max/2)) {
				min = pageNum - max;
				max = pageNum;
			} else {                               
				min = Math.round(current - max/2);
				max = Math.round(current + max/2);
			}
			$('ul.pageNum li').hide();
			for(var i = min; i < max; i++){
				$('ul.pageNum li').eq(i).show();
			}
		}

		// 实现管理功能
		$('.subBtn').click(function(){
			var arr = [];
			var oAtatus;
			var current = 1;
			$('input[name="style"]:checked').each(function(){
				// 选择银行卡号
				arr.push($(this).parent().siblings().eq(1).text().trim());
				switch ($(this).val().trim()) {
					case '注销': oAtatus = 4;
								 break;
					case '挂失': oAtatus = 2;
								 break;
				};
				arr.push(oAtatus);
			})
			var current = 1;

			var sendData = {
				"status": arr[1],
				"raccountid": arr[0],
				"current": current
			};
			console.log(sendData);

			$.ajax({
				type: "post",
				url: "/BankSystem_16/clerk/editCardStatus.do",
				data: sendData,
				success: function(data){
					if(parseInt(data.status) == 1){
						alert(data.info);
						askDataAjax();
					} else {
						alert(data.info);
						askDataAjax();
					}
				},
				error: function(){
					alert("网络出错");
				}
			})
		})

		// 点击下一页切换
		$(".next").click(function(){
			if(current == pageNum){
				alert("到达尾页了！");
				return false;
			}
			current +=1;
			askDataAjax();
		})

		// 点击上一页切换
		$(".prev").click(function(){
			if(current == 1){
				alert("到达首页了！");
				return false;
			}
			current -=1;
			askDataAjax();
		})

		// 点击首页切换
		$(".start").click(function(){
			current = 1;
			askDataAjax();
		})

		// 点击尾页切换
		$(".last").click(function(){
			current = pageNum;
			askDataAjax();
		})
	})()
})