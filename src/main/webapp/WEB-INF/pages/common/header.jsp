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
						<li><a href="#">Tạo đơn hàng</a></li>
				    	<li><a href="#">Chờ duyệt</a></li>
				        <li><a href="#">Đã duyệt</a></li>
				        <li><a href="#">Đã mua</a></li>
				    </ul>
				</li>
			</sec:authorize>
		</ul>
			</nav>
</div>
