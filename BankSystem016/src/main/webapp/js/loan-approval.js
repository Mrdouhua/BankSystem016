// 使用fetch请求数据
$(function(){
	// 设置全局命名空间
	var MO = {} || MO;

	// 是否全选功能
	MO.isCheckAll = function() {
		var checkAll = $('#check-all');
		if($('#check-all').is(":checked")) {
			$('tbody').find('input[type="checkbox"]').prop("checked",true);
		} else {
			$('tbody').find('input[type="checkbox"]').prop("checked",false);
		}
	}

	$('#check-all').click(function(){
		MO.isCheckAll();
	})

	// 请求页面资源
	MO.askData = (function(){
		// var askData = MO.askData; //讲全局变量保存为局部变量
		var current = 1,
			formData = {},
			pageNum = 1,
			min = 0,
			max = 5;
		var sendData = {
			"formData": formData,
			"current": current
		};

		// 包装请求后台页面内容ajax的函数
		function askDataAjax(sendData) {
			let url = "/BankSystem_16/loanApprovalQuery.do";
			// 默认发送get请求
			var myPromise = fetch(url).then( (response)=>{
				return response.json();
			});

			myPromise.then(function(data){
				var oData = data,
				    currentData = oData.datas,
				    str = '';
				pageNum = oData.pagenum;

				currentData.map(function(item){
					str +=  '<tr class="tr">' + 
							'	<td class="td150">' +
							'		<input type="checkbox" id="check" class="check" name="cancle">' +
							'		<label class="check-label">'
										+"删除"+
							'		</label>' +
							'	</td>' +
							'	<td class="td150">'
									+item.name+
							'	</td>' +
							'	<td class="td150">'
									+item.idNumber+
							'	</td>' +
							'	<td class="td150">'
									+item.capital+
							'	</td>' +
							'	<td class="td150">'
									+item.cardID+
							'	</td>' +
							'	<td class="td150">' +
							'		<a href="#" id="audit-a" class="audit-state">'
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
				$('ul.pageNum li').click(function(){
					current = parseInt($(this).text());
					isLight(min,max,current-1);
					$("ul.pageNum li").eq(current - 1).addClass('active').siblings().removeClass('active');
					var sendData = {
						"formData": formData,
						"current": current
					};
					console.log(sendData);
					askDataAjax(sendData);
				})
				
				$('.audit-state').click(function(){
					var idNum = $(this).parent().siblings().eq(2).text();
					var oHref = "/BankSystem_16/loanApprovalindex.do" +"?"+ "idNumber="+idNum;
					$(this).attr("href",oHref);
					console.log($(this).attr("href"));
				})

			}).catch(function(value){
				alert(value,"出错啦!");
			})
		}

			askDataAjax(sendData);

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
		$('#search').click(function(){
			var formData = {
				"name": $('.name').val(),
				"idnumber": $('.idNumber').val()
			};
		
			var current = 1;

			var sendData = {
				"name": $('.name').val(),
				"idnumber": $('.idNumber').val(),
				"current": current
			};
			console.log(sendData);
			askDataAjax(sendData);
			return false;
		})

		// 实现删除功能
		$('.subBtn').click(function(){
			var arr = [];
			$('input[name="cancle"]:checked').each(function(){
				// 选择银行卡号
				arr.push($(this).parent().siblings().eq(3).text());
				
			})
			console.log(arr);
			var formData = {
				"cancle": arr
			};
			var current = 1;

			var sendData = {
				"formData": formData,
				"current": current
			};

			$.ajax({
				type: "post",
				url: "/BankSystem_16/loanApprovalDelete.do",
				data: {
					"IDNumber": arr[0]
				},
				success: function(data){
					if(parseInt(data.status) == 1){
						alert(data.info);
						askDataAjax(sendData);
					} else {
						alert(data.info);
						askDataAjax(sendData);
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
			var sendData = {
				"formData": formData,
				"current": current
			};
			console.log(sendData);
			askDataAjax(sendData);
		})

		// 点击上一页切换
		$(".prev").click(function(){
			if(current == 1){
				alert("到达首页了！");
				return false;
			}
			current -=1;
			var sendData = {
				"formData": formData,
				"current": current
			};
			console.log(sendData);
			askDataAjax(sendData);
		})

		// 点击首页切换
		$(".start").click(function(){
			current = 1;
			var sendData = {
				"formData": formData,
				"current": current
			};
			console.log(sendData);
			askDataAjax(sendData);
		})

		// 点击尾页切换
		$(".last").click(function(){
			current = parseInt(pageNum);
			var sendData = {
				"formData": formData,
				"current": current
			};
			console.log(sendData);
			askDataAjax(sendData);
		})

	})()
})