
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Thay đổi mật  khẩu</title>
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<link href="<c:url value="/resources/css/icheck/green.css" />"
	rel="stylesheet">
<link
	href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/dialogbox.js" />"></script>
<script type="text/javascript">
	function close_window() {
		this.close();
	}
	function confirmChange(){
		
		var	pOld = document.getElementById('oldPassword').value;
		var pNew1 = document.getElementById('new_password').value;
		var pNew2 = document.getElementById('confirmPassword').value;
		if(pOld == ""){
			$("#errorMessenger").html("Vui lòng nhập mật khẩu hiện tại.");
			return;
		}
		if(pNew1 == ""){
			$("#errorMessenger").html("Vui lòng nhập mật khẩu mới.");
			return;
		}
		
		if (pNew1.length < 6) {
			$("#errorMessenger").html("Mật khẩu phải bao gồm ít nhất 6 ký tự.");
			return;
		}
		
		if(pNew2 == ""){
			$("#errorMessenger").html("Vui lòng nhập xác nhận mật khẩu mới.");
			return;
		}
		
		if (pNew1 != pNew2) {
			$("#errorMessenger").html("Mật khẩu xác nhận không khớp.");
			return;
		}
		var myConfirm = window.confirm("Bạn có chắc muốn thay đổi mật khẩu?");
		if (myConfirm) {
			$("#changePass").submit();
		}
	}
	
	function checkPrtScn(evt){
		var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode == 42 || charCode == 32) {
	    	evt.preventDefault();
	    }
	}
	
</script>
</head>
<body>
	<div class="rightContent" align="center">
		<c:if test="${resetPw ne true }">
			<div class="header_bar">
				<jsp:include page="/WEB-INF/pages/common/header.jsp" /></div>
		</c:if>
		<div id="wrapper">
			<div class="body_container">
				<div class="container body">
					<div class="main_container">
				<div id="login" class="animate form" style= "padding-top: 30px; padding-bottom: 30px;
					border-radius:4px; background: #fff">
						<section class="login_content">
						<form:form modelAttribute="user" method="POST"
							action="thay-doi-mat-khau" class="form-horizontal" id = "changePass">
									<div class="block-eff-no-margin">
										<h1>Thay đổi mật khẩu</h1>
										<div id="error_message">
											<div class="row">
												<p style="color: red; text-align: center;">${errorMessage }</p>
											</div>
											<div class="row">
												<p style="color: red; text-align: left;" id="errorMessenger">
												</p>
											</div>
										</div>
										<div class="form-group">
											<input type="text" value="${user.email }"
												class="form-control" disabled="disabled"> <input
												type="hidden" name="email" value="${user.email }" />
										</div>
										<div class="form-group">
											<c:if test="${resetPw ne true }">
												<input name="oldPassword" type="password"
													class="form-control" id="oldPassword"
													placeholder="Mật khẩu hiện tại" required maxlength="20"
													onkeypress="checkPrtScn(event)" />
											</c:if>
										</div>

										<div class="form-group">
											<input name="password" type="password" class="form-control"
												id="new_password" required placeholder="Mật khẩu mới"
												maxlength="20" onkeypress="checkPrtScn(event)" />
										</div>
										<div class="form-group">
											<input name="confirmPassword" type="password"
												id="confirmPassword" class="form-control"
												placeholder="Nhập lại mật khẩu" maxlength="20"
												onkeypress="checkPrtScn(event)" />
										</div>

										<div class="form-group" style="margin-top: 30px;">
											<button type="button" class="btn btn-primary btn-lg"
												onclick="confirmChange()">
												<i class="fa fa-save m-right-xs"></i> Đổi mật khẩu
											</button>
											<a class="btn btn-default btn-lg" href="<c:url value= "/"/>"> <i
												class="fa fa-close m-right-xs"></i> Hủy bỏ
											</a>
										</div>
									</div>
									<div class="clearfix"></div>
							
						</form:form>
					</section>
					</div>
				
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</html>