<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
</head>
<body>

	<!--header-->
	<div class="header">

		<div class="container"><!--导航栏容器 -->
		
			<nav class="navbar navbar-default" role="navigation">
			
				<!--导航栏头部图片 -->
				<div class="navbar-header">
					<h1 class="navbar-brand"><a href="index.action">XhuStore</a></h1>
				</div>
				
				<!--halamadrid后面一大坨分类-->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				
					<ul class="nav navbar-nav">
					
<!-- 						首页标签 -->
						<li><a href="index.action" <c:if test="${flag==1}">class="active"</c:if>>首页</a></li>
						
<!-- 						多级分类标签 -->
						<li class="dropdown">
						
<%-- 							if进行单一的判断，判断为true时 执行<c:if>...</c:if> 中的语句，但其没有配对的else --%>
<!-- 								class="active"其实就是记录当前所在位置，然后变成标记色 -->
							<a href="#" class="dropdown-toggle <c:if test="${flag==2}">active</c:if>" data-toggle="dropdown">商品分类<b class="caret"></b></a>
<!-- 							具体分类 -->
							<ul class="dropdown-menu multi-column columns-2">
								<li>
									<div class="row">
										<div class="col-sm-12">
											<ul class="multi-column-dropdown">
												<c:forEach var="type" items="${typeList}"> <!-- 返回结果库 -->
													<li><a class="list" href="goods.action?typeid=${type.id}">${type.name}</a></li>
												</c:forEach>
											</ul>
										</div>	
									</div>
								</li>
							</ul>
							
						</li>
						
<!-- 						其他标签 -->
						<li><a href="top.action?typeid=2" <c:if test="${flag==7}">class="active"</c:if>>热销</a></li>
						<li><a href="top.action?typeid=3" <c:if test="${flag==8}">class="active"</c:if>>新品</a></li>
						
						<!-- 						如果没登陆，显示如下 -->
						<c:if test="${sessionScope.user==null}">
							<li><a href="register.action?flag=-1" <c:if test="${flag==5}">class="active"</c:if>>注册</a></li>
							<li><a href="login.action?flag=-1" <c:if test="${flag==6}">class="active"</c:if>>登录</a></li>
						</c:if>
						
						<!-- 						如果登陆了 -->
						<c:if test="${sessionScope.user!=null}">
							<li><a href="order.action" <c:if test="${flag==3}">class="active"</c:if>>我的订单</a></li>
							<li><a href="my.action" <c:if test="${flag==4}">class="active"</c:if>>个人中心</a></li>
							<li><a href="logout.action">退出</a></li>
						</c:if>
						
						<!-- 						后台管理 -->
						<li><a href="../admin.jsp" target="_blank">后台管理</a></li>
					</ul> 
				</div>
			</nav>
			
			
			
<!-- 			最右侧的三个功能选项 -->
			<div class="header-info">
			
				<!-- 				查找 -->
				<div class="header-right search-box">
					<a href="javascript:;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>				
					<div class="search">
						<form class="navbar-form" action="search.action">
							<input type="text" class="form-control" name="name">
							<button type="submit" class="btn btn-default" aria-label="Left Align">搜索</button>
						</form>
					</div>	
				</div>
				
				<!-- 	购物车购物车购物车购物车购物车 -->
				<div class="header-right cart">
					<a href="cart.action">
<!-- 						购物车有商品的话就在图标旁边显示数量，下同。 -->
						<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"><span class="card_num">${order.amount==null ? '' :order.amount}</span></span>
					</a>
				</div>
				
				<!-- 				登陆 -->
				<div class="header-right login">
					<a href="my.action"><span class="glyphicon glyphicon-user" aria-hidden="true">${sessionScope.user.username}</span></a>
				</div>
				
				<!--				清除浮动 -->
				<div class="clearfix"> </div>
			</div>    <!-- 三个功能选项结束 -->
			
			<!-- 			清除浮动 -->
			<div class="clearfix"> </div>
			
		</div>  <!--导航栏结束 -->
		
	</div>
	<!--头部结束-->

</body>
</html>