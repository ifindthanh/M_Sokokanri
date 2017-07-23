<%@page import="vn.com.nsmv.common.Utils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="rate" uri="/WEB-INF/taglibs/moneyExchange.tld" %>
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>

<div>
	<div class="top-block"
		style="height: 50px; width0: 100%; background-color: #9e9595">
		<sec:authorize access="isAuthenticated()">
			<div class="block"
				style="float: left; padding-top: 15px; padding-left: 20px;">
				Tỷ giá
				<sec:authorize access="hasRole('ROLE_A')">
					<a id='moneyRate' onclick="updateMoneyRate()"><rate:money/></a>
				</sec:authorize>
				<sec:authorize access="!hasRole('ROLE_A')">
					<span id='moneyRate'><rate:money/></span>
					
				</sec:authorize>
			</div>
		</sec:authorize>
		<div class="menu-header block">
			<ul>
				<sec:authorize access="!isAuthenticated()">
					<li><a href="#" data-toggle="modal" data-target="#user-modal"><i class="fa fa-sign-in"
							aria-hidden="true"></i> Đăng nhập</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li>
						<a href="<c:url value= "/logout"/>"><sec:authentication property="principal.displayName"/> <i class="fa fa-sign-out"
						aria-hidden="true"></i>
						</a>
					</li>
				</sec:authorize>
			</ul>
		</div>
	</div>
	<div class="extra-content"
		style="height: 150px; width: 100%; background-color: white"></div>
	<nav class="navbar navbar-inverse">
		<ul class="nav navbar-nav">
			<li class="active"><a href="#">Home</a></li>
			<li><a href="#">Giới thiệu</a></li>
			<li><a href="#">Báo giá</a></li>
			<sec:authorize access="isAuthenticated()">
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Đơn hàng</a>
					<ul class="dropdown-menu">
						<sec:authorize access="hasRole('ROLE_U')">
							<li><a href="<c:url value= "/donhang/tao-moi"/>">Tạo đơn hàng</a></li>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_U','ROLE_A')">
							<li><a href="<c:url value= "/donhang/tat-ca/0"/>">Tất cả đơn hàng</a></li>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_C','ROLE_A','ROLE_U')">
				    		<li><a href="<c:url value= "/donhang/cho-duyet/0"/>">Chờ duyệt</a></li>
				    	</sec:authorize>
				    	<sec:authorize access="hasAnyRole('ROLE_B','ROLE_A','ROLE_U')">
				        	<li><a href="<c:url value= "/donhang/cho-mua/0"/>">Đã duyệt</a></li>
				        </sec:authorize>
				        <sec:authorize access="hasAnyRole('ROLE_T1','ROLE_A','ROLE_U')">
				        	<li><a href="<c:url value= "/donhang/da-mua/0"/>">Đã mua</a></li>
				        </sec:authorize>
				        <sec:authorize access="hasAnyRole('ROLE_T2','ROLE_A','ROLE_U')">
				        	<li><a href="<c:url value= "/donhang/da-chuyen/0"/>">Đã nhận hàng tại nước ngoài</a></li>
				        </sec:authorize>
				        <sec:authorize access="hasAnyRole('ROLE_K','ROLE_A','ROLE_U')">
				        	<li><a href="<c:url value= "/donhang/da-chuyen-vn/0"/>">Đã chuyển về Việt Nam</a></li>
				        </sec:authorize>
				        <sec:authorize access="hasAnyRole('ROLE_BG','ROLE_A','ROLE_U')">
				        	<li><a href="<c:url value= "/donhang/da-nhap-kho/0"/>">Đã nhập kho</a></li>
				        </sec:authorize>
				        <sec:authorize access="hasAnyRole('ROLE_A','ROLE_U')">
				        	<li><a href="<c:url value= "/donhang/da-xuat-hd/0"/>">Đã xuất hóa đơn</a></li>
				        </sec:authorize>
				        <sec:authorize access="hasAnyRole('ROLE_A','ROLE_U')">
				        	<li><a href="<c:url value= "/donhang/giao-hang/0"/>">Giao hàng</a></li>
				        </sec:authorize>
				        <sec:authorize access="hasAnyRole('ROLE_A')">
				        	<li><a href="<c:url value= "/donhang/da-hoan-thanh/0"/>">Đã hoàn thành</a></li>
				        </sec:authorize>
				    </ul>
				</li>
			</sec:authorize>
		</ul>
			</nav>
</div>

	
<div class="modal fade bs-modal-sm" id="user-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="loginmodal-container">
        <br>
        <div class="bs-example bs-example-tabs">
            <ul id="myTab" class="nav nav-tabs">
              <li class="active"><a href="#login" data-toggle="tab">Login</a></li>
              <li class=""><a href="#register" data-toggle="tab">Register</a></li>
              <li class=""><a href="#forgotPassword" data-toggle="tab">Forgot Password</a></li>
            </ul>
        </div>
      <div class="modal-body">
        <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in" id="forgotPassword">
        		<br><br>
				<form onkeyup="onEnter(event)" name='f' class ="forgot_password_form" id ="forgot_password_form" action="${pageContext.request.contextPath}/user/forgot_password" method='POST'>
					<input type="text" name="email" onkeyup="onEnter(event)" placeholder="Email"> 
					<input type="button" id="loginBtn" onclick="login()" class="login loginmodal-submit" value="Forgot Password">
				</form>
        </div>
        <div class="tab-pane fade active in" id="login">
				<p id= "errorMessage" class="error"></p>
				
				<br>
				<form onkeyup="onEnter(event)" name='f' class ="login_form" id ="login_form" action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
					<input type="text" name="email" onkeyup="onEnter(event)" placeholder="Email"> 
					<input type="password" name="password" onkeyup="onEnter(event)" placeholder="Password"> 
					<input type="button" id="loginBtn" onclick="login()" class="login loginmodal-submit" value="Login">
				</form>
        </div>
        <div class="tab-pane fade" id="register">
            <form class="form-horizontal">
            <fieldset>
                <input id="Email" name="Email" class="form-control" type="text" placeholder="JoeSixpack@sixpacksrus.com" class="input-large" required="">
                <input id="name" name="name" class="form-control" type="text" placeholder="JoeSixpack" class="input-large" required="">
                <input id="password" name="password" class="form-control" type="password" placeholder="password" class="input-large" required="">
                <input id="reenterpassword" class="form-control" name="reenterpassword" type="password" placeholder="Re-Enter password" class="input-large" required="">
                <input type="radio" name="gender"  value="male" >&nbsp;&nbsp;Male&nbsp;&nbsp;&nbsp;
                <input type="radio" name="gender" value="female">&nbsp;&nbsp;Female
				<input type="button" id="loginBtn" class="login loginmodal-submit" value="Register">
            </fieldset>
            </form>
      </div>
    </div>
      </div>
    </div>
  </div>
</div>	
	
	<div class="modal fade" id="money-rate-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Nhập thông tin tỉ giá</h4>
			</div>
			<div class="modal-body">
				<input type="number" id="money_rate" class="form-control" value='<rate:money/>'/>
				<input type="hidden" id="money_rate_hidden" class="form-control" value='<rate:money/>'/>
			</div>
			<p id="saveError" class="error"></p>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="saveMoneyRate('${pageContext.request.contextPath}/ti-gia/luu-ti-gia')">Lưu</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
	
	<script>
		function login(){
			$("#errorMessage").html("");
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/j_spring_security_check",
				data: $("#login_form").serialize(),
				success : function(result) {
					window.location.href = window.location.href.split("#")[0];
				},
				error : function() {
					$("#errorMessage").html("User name hoac password khong dung");
				}
			
			});
		}
		function onEnter(event) {
		    if(event.keyCode == 13){
		        $("#loginBtn").click();
		    }
		}
		
		function updateMoneyRate() {
			$("#saveError").html("");
			$("#money-rate-modal").modal("show");
			$("#money_rate").val($("#moneyRate").html());
		}
		
		function saveMoneyRate(url) {
			$.ajax({
				type : "GET",
				url : url+'?value='+$('#money_rate').val(),
				success : function(response) {
					if (response.status == 0) {
						$("#saveError").html(response.message);
						$('#money_rate').forcus();
						return;
					}
					$("#moneyRate").html($('#money_rate').val());
					$("#money-rate-modal").modal("hide");
				},
				error : function(response) {
					$("#saveError").html(response);
					$('#money_rate').forcus();
				}
			
			});
		}
	</script>
