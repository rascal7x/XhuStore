<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>个人中心</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/simpleCart.min.js"></script>
</head>
<body>

	<jsp:include page="header.jsp"/>
	
	<!--account-->
	<div class="account">
		<div class="container">
			<div class="register">
			
				<c:if test="${msg!=null}"><div class="alert alert-danger">${msg}</div></c:if>
			
				<form action="my.action" method="post" onsubmit="return checkAll()"> 
					<input type="hidden" name="user.id" value="${user.id}">
					<div class="register-top-grid">
						<h3>个人中心</h3>
						
						<h4>收货信息</h4>
						<div class="input">
							<span>收货人<label style="color:red;">*</label></span>
							<input type="text" id="user.name" onblur="checkName()" name="user.name" value="${user.name}" placeholder="请输入收货人姓名" required="required"> 
						</div>
						<span  style="font-size:13px" id="namespan"></span> 
						<div class="input">
							<span>收货电话<label style="color:red;">*</label></span>
							<input type="text" id="user.phone" onblur="checkPhone" name="user.phone" value="${user.phone}" placeholder="请输入收货电话" required="required"> 
						</div>
						<span  style="font-size:13px" id="phonespan"></span>
						<div class="input">
							<span>收货地址<label style="color:red;">*</label></span>
							<input type="text" id="user.address" onblur="checkAddress" name="user.address" value="${user.address}" placeholder="请输入收货地址" required="required"> 
						</div>
						<span  style="font-size:13px" id="addressspan"></span>
						<div class="register-but text-center">
						   <input type="submit" value="提交">
						</div>	
						<hr>
						<h4>安全信息</h4>
						<div class="input">
							<span>原密码</span>
							<input type="password" name="user.password" placeholder="请输入原密码" style="width:97%;heigth:50%"> 
						</div>
						<div class="input">
							<span>新密码</span>
							<input type="password" name="user.passwordNew" placeholder="请输入新密码" style="width:97%;heigth:50%"> 
						</div>
						<div class="clearfix"> </div>
						<div class="register-but text-center">
						   <input type="submit" value="提交">
						</div>	
					</div>
				</form>
				<div class="clearfix"> </div>
			</div>
	    </div>
	</div>
	<script type="text/javascript">
	function checkName(){
		var name=document.getElementById("user.name").value;
		var na=document.getElementById("namespan");
		if(name.length>=2)
			return true;
		else{
			na.innerHTML="姓名长度必须大于等于2".fontcolor("red");
			return false;
		}
	}
	function checkPhone(){
		var phone=document.getElementById("user.phone").value;
		var ph=document.getElementById("phonespan");
		if(!(/^1[3456789]\d{9}$/.test(phone))){
			ph.innerHTML="电话号码不合法".fontcolor("red");
			return false;
		}
		else{
			ph.innerHTML="";
			return true;
		}
			
	}
	function checkAddress(){
		var address=document.getElementById("user.address").value;
		var ad=document.getElementById("addressspan");
		if(address.length>6){
			return true;
		}else{
			ad.innerHTML="地址长度必须大于6".fontcolor("red");
			return false;
		}
			
	}
	function checkAll(){
		var name=checkName();
		var phone=checkPhone();
		var address=checkAddress();
		if(name&&phone&&address){
			return true;
		}else{
			alert("提交失败，请检查输入信息");
			return false;
		}
			
	}
	</script>

	<jsp:include page="footer.jsp"/>
</body>
</html>