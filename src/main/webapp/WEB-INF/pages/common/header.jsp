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
	<div id="ajax-overlay"></div>
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
					<li><a href="#forgotPassword" data-toggle="modal" data-target="#user-modal" ><i class="fa fa-lock" 
							aria-hidden="true"></i> Quên mật khẩu</a></li>
					<li><a href="#login" data-toggle="modal" data-target="#user-modal"><i class="fa fa-sign-in" 
							aria-hidden="true"></i> Đăng nhập</a></li>
					<li><a href="#register" data-toggle="modal" data-target="#user-modal"><i class="fa fa-user-plus"
							aria-hidden="true"></i> Đăng ký</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li><a href="<c:url value= "/thay-doi-mat-khau"/>">Đổi mật khẩu <i class="fa fa-key"
							aria-hidden="true"></i></a>
					</li>
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
			<sec:authorize access="hasRole('ROLE_A')">
				<li><a href="<c:url value= "/user/tat-ca/0"/>">Danh sách người dùng</a></li>
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
              <li> <a href="#register" data-toggle="tab">Đăng ký</a></li>
              <li> <a href="#forgotPassword" data-toggle="tab">Quên mật khẩu</a></li>
            </ul>
        </div>
      <div class="modal-body">
        <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in" id="forgotPassword">
        	<p id= "reset_errorMessage" class="error"></p>
        		<h1>Nhập địa chỉ email</h1>
        		<br>
        		<form name='f' id ="reset_form">
					<input type="text" id="reset_email" class="form-control" name="email" placeholder="Email"> 
					<input type="button" id="resetPwd" onclick="resetPw()" class="login loginmodal-submit" value="Forgot Password">
				</form>
        </div>
        <div class="tab-pane fade active in" id="login">
				<p id= "errorMessage" class="error"></p>
				
				<h1>Nhập email và mật khẩu</h1>
				<br>
				<form name='f' class ="login_form" id ="login_form">
					<input type="text" id="email" name="email" onkeyup="onEnter(event)" placeholder="Email"> 
					<input type="password" id="password" name="password" onkeyup="onEnter(event)" placeholder="Password"> 
					<input type="button" id="loginBtn" onclick="login()" class="login loginmodal-submit" value="Login">
				</form>
        </div>
        <div class="tab-pane fade" id="register">
        	<p id= "reg_errorMessage" class="error"></p>
            <h1>Điền thông tin tài khoản</h1>
            <br/>
            <fieldset>
            	<form name='f' id ="reg_form">
	                <input id="reg_email" name="email" class="form-control" type="text" placeholder="Email" class="input-large">
	                <input id="reg_name" name="name" class="form-control" type="text" placeholder="Full name" class="input-large">
	                <input id="reg_password" name="password" class="form-control" type="password" placeholder="Mật khẩu" class="input-large">
	                <input id="reg_reenterpassword" class="form-control" placeholder="Nhập lại mật khẩu" type="password" class="input-large">
	                <input id="reg_phone" class="form-control" name="phone" placeholder="Số điện thoại" class="input-large">
	                <input id="reg_male" type="radio" name="gender"  value="M" ><label class="gender" for="reg_male">Nam</label>
	                <input id="reg_female"type="radio" name="gender" value="F"><label class="gender" for="reg_female">Nữ</label>
					<input type="button" id="reg_button" class="login loginmodal-submit" value="Đăng ký" onclick="register()">
				</form>
            </fieldset>
            <br/>
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
		var mode = 1;
		$(document).ready(function() {
			$('#user-modal').on('shown.bs.modal', function(e) {
			    var tab = e.relatedTarget.hash;
			    $('.nav-tabs a[href="'+tab+'"]').tab('show')
			})    
		});
		
		function login(){
			$("#errorMessage").html("");
			if ($("#email").val() =="") {
				alert("Email không được để trống");
				$("#email").focus();
				return;
			}
			if ($("#password").val() =="") {
				alert("Mật khẩu không được để trống");
				$("#password").focus();
				return;
			}
			$('#ajax-overlay').show();
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/j_spring_security_check",
				data: $("#login_form").serialize(),
				success : function(result) {
					$('#ajax-overlay').hide();
					window.location.href = window.location.href.split("#")[0];
				},
				error : function() {
					$("#errorMessage").html("User name hoac password khong dung");
					$('#ajax-overlay').hide();
				}
			
			});
		}
		
		function register(){
			$("#errorMessage").html("");
			if ($("#reg_email").val() =="") {
				alert("Email không được để trống");
				$("#reg_email").focus();
				return;
			}
			
			if ($("#reg_password").val() =="") {
				alert("Mật khẩu không được để trống");
				$("#reg_password").focus();
				return;
			}
			
			if ($("#reg_password").val().length < 6) {
				alert("Mật khẩu phải có ít nhất 6 ký tự");
				$("#reg_password").focus();
				return;
			}
			
			if ($("#reg_reenterpassword").val() =="") {
				alert("Mật khẩu xác nhận không được để trống");
				$("#reg_reenterpassword").focus();
				return;
			}
			
			if ($("#reg_password").val() != $("#reg_reenterpassword").val()) {
				alert("Mật khẩu xác nhận không khớp");
				$("#reg_reenterpassword").focus();
				return;
			}
			
			if ($("#reg_name").val() =="") {
				alert("Tên người dùng không được để trống");
				$("#reg_name").focus();
				return;
			}
			
			if ($("#reg_phone").val() =="") {
				alert("Số điện thoại không được để trống");
				$("#reg_phone").focus();
				return;
			}
			
			if (!$('input:radio[name=gender]:checked').val()) {
				alert("Vui lòng nhập giới tính");
				return;
			}
			
			$('#ajax-overlay').show();
			var data = {
				"email": $("#reg_email").val(),
				"password": $("#reg_password").val(),
				"confirmPassword": $("#reg_reenterpassword").val(),
				"fullName": $("#reg_name").val(),
				"phone": $("#reg_phone").val(),
				"sex": $('input:radio[name=gender]:checked').val()
			};
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "${pageContext.request.contextPath}/dang-ky",
				data : JSON.stringify(data),
				dataType : 'json',
				success : function(result) {
					if (result.status == 0) {
						$('#ajax-overlay').hide();
						$("#reg_errorMessage").html(result.message);
						return;
					}
					alert("Đăng ký thành công");
					$('#ajax-overlay').hide();
					window.location.href = window.location.href.split("#")[0];
				},
				error : function() {
					$("#reg_errorMessage").html("Đã có lỗi xảy ra, vui lòng liên hệ với quản trị viên.");
					$('#ajax-overlay').hide();
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
			$('#ajax-overlay').show();
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
					$('#ajax-overlay').hide();
				},
				error : function(response) {
					$("#saveError").html(response);
					$('#money_rate').forcus();
					$('#ajax-overlay').hide();
				}
			
			});
		}
		
		function resetPw() {
			$("#reset_errorMessage").html("");
			if ($("#reset_email").val() =="") {
				alert("Email không được để trống");
				$("#reg_email").focus();
				return;
			}
			
			$('#ajax-overlay').show();
			
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/quen-mat-khau",
				data: $("#reset_form").serialize(),
				success : function(result) {
					if (result.status == 0) {
						$("#reset_errorMessage").html(result.message);
						$("#reset_email").val("");
						$("#reset_email").focus();
						$('#ajax-overlay').hide();
						return;
					}
					alert("Yêu cầu đã được gửi đến địa chỉ email của bạn, vui lòng kiểm tra hòm thư và tiến hành thay đổi mật khẩu của bạn trong thời hạn 1 ngày.");
					$('#ajax-overlay').hide();
					window.location.href = window.location.href.split("#")[0];
				},
				error : function() {
					$("#reset_errorMessage").html("Đã có lỗi xảy ra, vui lòng liên hệ với quản trị viên.");
					$('#ajax-overlay').hide();
				}
			
			});
		}
	</script>
