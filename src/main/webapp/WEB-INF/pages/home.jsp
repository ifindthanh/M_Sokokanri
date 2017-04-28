<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
</head>
<body style="background:#F7F7F7;">
	<jsp:include page="/WEB-INF/pages/common/header.jsp" />
	<div class="modal fade" id="login-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="loginmodal-container">
				<h1>Login to Your Account</h1>
				<p id= "errorMessage" class="error"></p>
				
				<br>
				<form name='f' class ="login_form" id ="login_form" action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
					<input type="text" name="email" placeholder="Email"> 
					<input type="password" name="password" placeholder="Password"> 
					<input type="button" onclick="login()" class="login loginmodal-submit" value="Login">
				</form>

				<div class="login-help">
					<a href="#" onclick="login()">Register</a> - <a href="#">Forgot Password</a>
				</div>
				
			</div>
		</div>
	</div>
</body>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script>
function login(){
	$.ajax({
		type : "POST",
		url : "j_spring_security_check",
		data: $("#login_form").serialize(),
		success : function(result) {
			window.location.href = window.location.href.split("#")[0];
		},
		error : function() {
			$("#errorMessage").html("User name hoac password khong dung");
		}
	
	});
}
</script>
</html>