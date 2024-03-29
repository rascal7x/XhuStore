<%@ page language="java" contentType="text/html; charset=utf-8"%>

<!-- jstl标准标签库 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>	XhuStore </title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!-- 设置css,js的地址 -->
<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
<link type="text/css" rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript" src="js/cart.js"></script>

</head>


<body>

	<jsp:include page="header.jsp" />

	<!--首页banner，迭代top1List，把每次读取到的数据存到var中，end为零，就迭代一次-->
	<c:forEach var="top" items="${top1List}" end="0">
	
		<div class="banner">
			<div class="container">
				
				<!-- 最大的乱码名字，哈哈哈哈哈，点击连接可以到商品详情页面 -->
				<h2 class="hdng">
					
					<!-- 上面top已经获取到了， -->
					<a href="detail.action?goodid=${top.good.id}">  ${top.good.name}  </a>
				</h2>
				
				<p>精选推荐</p>
				
				<!-- 				加入购物车按钮 -->
				<a class="banner_a" href="javascript:;" onclick="buy(${top.good.id})">加入购物车</a>
				
				<!-- 				右侧展示图 -->
				<div class="banner-text">
					<a href="detail.action?goodid=${top.good.id}"> <img	src="../${top.good.cover}" alt="${top.good.name}" width="350"
						height="350">
						<!-- 照片图片的数据库存储格式都是/picture/12-1.jpg这种形式，所以只需要设置上级路径../即可 -->
					</a>
				</div>

			</div>
		</div>


	</c:forEach>
	<!--//banner-->



	<div class="subscribe2"></div>

	<!--陈列推荐，包括 热销、新品-->
	<div class="gallery">
		<div class="container">


			<!-- 		热销 -->
			<div class="alert alert-danger">热销推荐</div>
			
			<!-- 		热销网格展示 -->
			<div class="gallery-grids">
				<!-- 				展示六个商品 -->
				<c:forEach var="top" items="${top2List}" end="5">
					<div class="col-md-4 gallery-grid glry-two">
					
						<a href="detail.action?goodid=${top.good.id}"> <img	src="../${top.good.cover}" class="img-responsive" alt="${top.good.name}" width="350" height="350" />
						</a>
						
						<div class="gallery-info galrr-info-two">
							<p>
								<!-- 图标仅仅作为展示使用 -->
								<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
								<a href="detail.action?goodid=${top.good.id}">查看详情</a>
							</p>
							
							<a class="shop" href="javascript:;" onclick="buy(${top.good.id})">加入购物车</a>
							<div class="clearfix"></div>
						</div>
						
						<div class="galy-info">
							<p>${top.good.type.name}>  ${top.good.name}</p>
							<div class="galry">
								<div class="prices">
									<h5 class="item_price">¥ ${top.good.price}</h5>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>


			<!-- 			新品 ，首先清除浮动-->
			<div class="clearfix"></div>
			<div class="alert alert-info">新品推荐</div>
			<div class="gallery-grids">
				<c:forEach var="top" items="${top3List}" end="7">
					<div class="col-md-3 gallery-grid ">
						<a href="detail.action?goodid=${top.good.id}"> <img	src="../${top.good.cover}" class="img-responsive"
							alt="${top.good.name}" />
						</a>
						<div class="gallery-info">
							<p>
								<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
								<a href="detail.action?goodid=${top.good.id}">查看详情</a>
							</p>
							<a class="shop" href="javascript:;" onclick="buy(${top.good.id})">加入购物车</a>
							<div class="clearfix"></div>
						</div>
						<div class="galy-info">
							<p>${top.good.type.name}> ${top.good.name}</p>
							<div class="galry">
								<div class="prices">
									<h5 class="item_price">¥ ${top.good.price}</h5>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
				</c:forEach>

			</div>
			
		</div>
	</div>
	<!--//gallery-->



	<!--subscribe-->
	<div class="subscribe"></div>
	<!--//subscribe-->

	<!-- 	底部版权声明 -->
	<jsp:include page="footer.jsp" />

</body>
</html>