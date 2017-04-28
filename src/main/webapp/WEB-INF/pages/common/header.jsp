<%@page import="vn.com.nsmv.common.Utils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<div>
	<div class="top-block"
		style="height: 50px; width: 100%; background-color: #9e9595">
		<div class="block"
			style="float: left; padding-top: 15px; padding-left: 20px;">Tỷ
			giá</div>
		<div class="menu-header block">
			<ul>
				<sec:authorize access="!isAuthenticated()">
					<li><a href="#news"><i class="fa fa-lock"
							aria-hidden="true"></i> Quên mật khẩu</a></li>
					<li><a href="#" data-toggle="modal" data-target="#login-modal"><i class="fa fa-sign-in"
							aria-hidden="true"></i> Đăng nhập</a></li>
					<li><a href="#about"><i class="fa fa-user-plus"
							aria-hidden="true"></i> Đăng ký</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li><a href="<c:url value= "/logout"/>"><i class="fa"
						aria-hidden="true"></i> <sec:authentication property="principal.displayName"/></a></li>
				</sec:authorize>
			</ul>
		</div>
	</div>
	<div class="extra-content"
		style="height: 150px; width: 100%; background-color: white"></div>
	<nav class="navbar navbar-inverse" style="border-radius: 0">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">WebSiteName</a>
			</div>
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#">Page 1</a></li>
				<li><a href="#">Page 2</a></li>
				<li><a href="#">Page 3</a></li>
			</ul>
		</div>
	</nav>
</div>
