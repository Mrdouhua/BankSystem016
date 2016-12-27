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
					type: "get",
					url: "/BankSystem_16/admin/Loginuser_show.do",
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
				var arr = [];
				var arr1 = [];

				currentData.map(function(item){
					arr.push(item.usertype);
					arr1.push(item.account);
					str +=  `<tr class="tr">  
								<td class="td150"> 
									<input type="radio" name="manage-style" class="cancle" value="删除"> 
									<span>
										删除
									</span> 
									<input type="radio" name="manage-style" value="修改"> 
									<span>
										修改
									</span> 
								</td> 
								<td class="td150" style="display:none;">
									${item.userid}
								</td> 
								<td class="td150">
									<input type="text" value=${item.loginname}>
								</td> +
								<td class="td150"> 
									<select class="select"> 
										<option value="A">系统管理员</option> 
										<option value="B">行长</option> 
										<option value="C">数据操作员</option> 
										<option value="D">出纳</option> 
										<option value="E">贷款发放员</option> 
										<option value="F">信用卡审批员</option> 
										<option value="G">信用卡客服工作人员</option> 
									<select> 
								</td> 
								<td class="td150"> 
									<a href="#" class="audit-state">
										${item.status}
									</a> 
								</td> 
							</tr>`
				})

				// 设置下拉框选中值
				var oP = Promise.resolve();
				oP.then(function(){
					for(let i = 0; i < arr.length; i++) {
						$('.select').eq(i).val(arr[i]);
						$('.select-a').eq(i).val(arr1[i]);
					}
					// 删除
					$('.cancle').click(function(){
						var sure = window.confirm("确认删除？");
						if(sure) {
							var arr = [];
							var current = 1;
							arr.push($(this).parent().siblings().eq(0).text().trim());
							console.log(arr[0]);
							$.ajax({
								type: "get",
								url: "/BankSystem_16/admin/deleteLoginuser.do",
								data: {
									"userid": arr[0]
								},
								success: function(data){
									if(parseInt(data.status) == 1){
										alert(data.info);
										window.location.reload();
									} else {
										alert(data.info);
										window.location.reload();
									}
								},
								error: function(){
									alert("网络出错");
								}
							})
						}
					})
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
					var roleId = $(this).parent().siblings().eq(1).text().trim();
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
						console.log(roleId,oAtatus);
						$.ajax({
							type: "get",
							data: {
								"roleId": roleId,
								"status": oAtatus
							},
							url: "",
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
		

		// 实现修改功能
		$('#submit').click(function(){
			var arr = [];
			var current = 1;
			$('input[name="manage-style"]:checked').each(function(){
				// 选择角色ID，并添加到数组
				arr.push($(this).parent().siblings().eq(0).text().trim());
				arr.push($(this).parent().siblings().eq(1).children().val().trim());
				arr.push($(this).parent().siblings().eq(2).children().val().trim());

			})
			var current = 1;

			var sendData = {
				"userid": arr[0],
				"loginName": arr[1],
				"usertype": arr[2],
				"current": current
			};

			$.ajax({
				type: "post",
				url: "/BankSystem_16/admin/updateLoginuser.do",
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

		// 实现查询功能
		$('#search').click(function(){
			var current = 1;

			var sendData = {
				"loginName": $('#role-select').val(),
				"current": current
			};
			$.ajax({
				type: "get",
				url: "/BankSystem_16/admin/searchbyLoginName.do",
				data: sendData,
				success: function(data){
					askDataAjax();
				}
			})
			return false;
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