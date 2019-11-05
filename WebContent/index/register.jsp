<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
	<title>新用户注册</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/simpleCart.min.js"></script>
	<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js" charset="utf-8"></script>
    <style type="text/css">
    .input1{
    display:inline
    }
    </style>
</head>
<body>

	<jsp:include page="header.jsp"/>
	
	<!--account-->
	<div class="account">
		<div class="container">
			<div class="register">
			
				<c:if test="${msg!=null}"><div class="alert alert-danger">${msg}</div></c:if>
				
				<form action="register.action" method="post"> 
					<div class="register-top-grid">
						<h3>注册新用户</h3>
						<div class="input">
							<span>用户名 <label style="color:red;">*</label></span>
							<input type="text" name="user.username" placeholder="请输入用户名" required="required"> 
						</div>
						<div class="input">
							<span>密码 <label style="color:red;">*</label></span>
							<input type="password" name="user.password" placeholder="请输入密码" style="width:97%;heigth:50%" required="required"> 
						</div>
						<div class="input">
							<span>收货人<label style="color:red;">*</label></span>
							<input type="text" name="user.name" placeholder="请输入收货人" required="required"> 
						</div>
						<div class="input">
							<span>收货电话<label style="color:red;">*</label></span>
							<input type="text" name="user.phone" placeholder="请输入收货电话" required="required"> 
						</div>
						<div class="input">
							<span>收货地址<label style="color:red;">*</label></span>
			<div class="layui-form-item" id="x-city">
            <div class="input1">
              <select name="province" lay-filter="province">
                <option value="">请选择省</option>
              </select>
            </div>
            <div class="input1">
              <select name="city" lay-filter="city">
                <option value="">请选择市</option>
              </select>
            </div>
            <div class="input1">
              <select name="area" lay-filter="area">
                <option value="">请选择县/区</option>
              </select>
            </div>
       </div>
						</div>
						<div class="input">
							<span>详细地址 <label style="color:red;">*</label></span>
							<input type="text" name="user.address" placeholder="请输入详细地址" required="required"> 
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="register-but text-center">
					   <input type="submit" value="提交">
					   <div class="clearfix"> </div>
					</div>
				</form>
				
				<div class="clearfix"> </div>
			</div>
	    </div>
	</div>
	<script type="text/javascript" src="./js/xcity.js"></script>
    <script>
      layui.use(['form','code'], function(){
        form = layui.form;

        layui.code();

        $('#x-city').xcity('四川','成都市','双流县');

      });
    </script>
    
	<!--//account-->

	<jsp:include page="footer.jsp"/>
	
</body>
</html>