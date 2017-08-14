<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="contact" uri="/WEB-INF/taglibs/contactColumnTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="chkbox" uri="/WEB-INF/taglibs/commonCheckbox.tld" %>

<html>
<head>
<title>Đã có lỗi xảy ra</title>
<META http-equiv="Pragma" content="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<meta http-equiv="cache-control" content="no-cache" />
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<link href="<c:url value="/resources/css/icheck/green.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dataTables.bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap-select.min.css" />" rel="stylesheet">
</head>
<body>
	<div class="header_bar">
		<jsp:include page="/WEB-INF/pages/common/header.jsp" />
	</div>
	<div id="page_content">
		<div class="col-sm-12">
			
			<c:set var="cancelLink" scope="session" value=""></c:set>
			<c:if test="${listType == 1}">
				<c:set var="cancelLink" value="/donhang/tat-ca"></c:set>
			</c:if>
			<c:if test="${listType == 2}">
				<c:set var="cancelLink" value="/donhang/cho-duyet"></c:set>
			</c:if>
			<c:if test="${listType == 3}">
				<c:set var="cancelLink" value="/donhang/cho-mua"></c:set>
			</c:if>
			<c:if test="${listType == 4}">
				<c:set var="cancelLink" value="/donhang/da-mua"></c:set>
			</c:if>
			<c:if test="${listType == 5}">
				<c:set var="cancelLink" value="/donhang/da-chuyen"></c:set>
			</c:if>
			<c:if test="${listType == 6}">
				<c:set var="cancelLink" value="/donhang/da-chuyen-vn"></c:set>
			</c:if>
			<c:if test="${listType == 7}">
				<c:set var="cancelLink" value="/donhang/da-nhap-kho"></c:set>
			</c:if>
			<c:if test="${listType == 8}">
				<c:set var="cancelLink" value="/donhang/da-xuat-hd"></c:set>
			</c:if>
			<c:if test="${listType == 9}">
				<c:set var="cancelLink" value="/donhang/da-hoan-thanh"></c:set>
			</c:if>
			<c:if test="${listType == 10}">
				<c:set var="cancelLink" value="/user/tat-ca"></c:set>
			</c:if>
				<p class="message">${message}
				<a class="btn btn-default" href='<c:url value="${cancelLink}"></c:url>'>Quay lại</a></p>
		</div>
	</div>
	<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	
    
    
</body>
</html>