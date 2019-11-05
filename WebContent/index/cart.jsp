<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
	<title>购物车</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="js/cart.js"></script>
</head>


<body>
	
	<jsp:include page="header.jsp"/>
	
	<!--cart-items-->
	<div class="cart-items">
		<div class="container">
		
		<c:if test="${msg!=null}"><div class="alert alert-success">${msg}</div></c:if>
		
		<!-- 		如果订单不为空 -->
		<c:if test="${order!=null}">
			<h2>我的购物车</h2>
			
			<!-- 			展示所有购物车内商品 -->
			<c:forEach var="item" items="${order.itemList}">
				<div class="cart-header col-md-6">
					<div class="cart-sec simpleCart_shelfItem">
					
					
						<div class="cart-item cyc">
							<a href="detail.action?goodid=${item.good.id}">
								<img src="../${item.good.cover}" class="img-responsive">
							</a>
						</div>
						
						<div class="cart-item-info">
							<h3><a href="detail.action?goodid=${item.good.id}">${item.good.name}</a></h3>
							<h3><span>单价: ¥ ${item.good.price}</span></h3>
							<h3><span>数量: ${item.amount}</span></h3>
							
							<%--  分别是增加、减少、删除的算法  --%>
							<a class="btn btn-info" href="javascript:buy(${item.good.id});">增加</a>     
							<a class="btn btn-warning" href="javascript:lessen(${item.good.id});">减少</a>
							<a class="btn btn-danger" href="javascript:deletes(${item.good.id});">删除</a>
						</div>
						
						<div class="clearfix"></div>
						
						
					</div>
				</div>
			</c:forEach>
			
			
			<div class="cart-header col-md-12">
				<hr>
				<h3>订单总金额: ¥ ${order.total}</h3>
				
				<%-- 点击提交订单按钮，自动跳跳转到save.action界面，action界面判断是否登录，登陆的话自动跳转到topay.action界面，否则登录  --%>
				<a class="btn btn-success btn-lg" style="margin-left:74%" href="save.action">提交订单</a>
			</div>
		</c:if>
			
		<!-- 		如果订单为空	 -->
		<c:if test="${order==null}"><div class="alert alert-info">空空如也</div></c:if>
			
		</div>
	</div>
	<!--//cart-items-->	
	
	<jsp:include page="footer.jsp"/>

</body>
</html>