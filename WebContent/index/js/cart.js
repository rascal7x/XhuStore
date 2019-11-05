


/**
 * 加入购物车
 */
function buy(goodid){
	
	// 方法裹挟着goodid奔向了buy.action这个方法，buy.action这个方法存在于UserAction.java中，goodid传入进去之后，查询内存够否，够的话后台先执行增加订单，然后返回ok，否则返回empty
	$.post("buy.action", {goodid:goodid}, function(data){
		if(data=="ok"){
			layer.msg("操作成功!", {time:800}, function(){ // 弹出框操作成功，0.8秒后消失，执行当前页面刷新函数
				location.reload();
			});
		}else if(data=="empty"){
			alert("库存不足!");    //事实证明并不会提示库存不足，只会一直让你添加成功，也就是说代码并不会运行到这一步，wtf?
			location.reload();
		}else{
			alert("请求失败!");
		}
	});
	
}




/**
 * 购物车减去
 */
function lessen(goodid){
	$.post("lessen.action", {goodid:goodid}, function(data){
		if(data=="ok"){
			layer.msg("操作成功!", {time:800}, function(){
				location.href="cart.action";
			});
		}else if(data=="login"){
			alert("请登录后操作!");
			location.href="login.jsp";
		}else{
			alert("请求失败!");
		}
	});
}


/**
 * 购物车删除
 */
function deletes(goodid){
	$.post("delete.action", {goodid:goodid}, function(data){
		if(data=="ok"){
			layer.msg("删除成功!", {time:800}, function(){
				location.href="cart.action";
			});
		}else if(data=="login"){
			alert("请登录后操作!");
			location.href="login.jsp";
		}else{
			alert("请求失败!");
		}
	});
}