<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="contact" uri="/WEB-INF/taglibs/contactColumnTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="chkbox" uri="/WEB-INF/taglibs/commonCheckbox.tld" %>
<%@ taglib prefix="order" uri="/WEB-INF/taglibs/orderStatusTaglib.tld" %>

<html>
<head>
<title>Tất cả đơn hàng</title>
<META http-equiv="Pragma" content="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<meta http-equiv="cache-control" content="no-cache" />
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/icheck/green.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dataTables.bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap-select.min.css" />" rel="stylesheet"><style>
	.ui-datepicker-next::before {
	    content: " - ";
	}
	.ui-datepicker-prev, .ui-datepicker-next {
		cursor: pointer;
	}
	#ui-datepicker-div { 
		display: none; 
	}
</style>
</head>
<body>
	<div class="header_bar">
		<jsp:include page="/WEB-INF/pages/common/header.jsp" />
	</div>
	<div id="page_content">
		<p class="error">${message }</p>
		<form action="tat-ca" method="POST">
			<div class="col-sm-12">
				<div style="position: absolute; float: left; left: 650px; margin-top: 20px">
					<c:set var = "cancelLink" scope = "session" value = ""></c:set>
				<c:if test="${listType == 1}">
					<c:set var = "cancelLink" value = "../tat-ca"></c:set>
				</c:if>
				<c:if test="${listType == 2}">
					<c:set var = "cancelLink" value = "../cho-duyet"></c:set>
				</c:if>
				<c:if test="${listType == 3}">
					<c:set var = "cancelLink" value = "../cho-mua"></c:set>
				</c:if>
				<c:if test="${listType == 4}">
					<c:set var = "cancelLink" value = "../da-mua"></c:set>
				</c:if>
				<c:if test="${listType == 5}">
					<c:set var = "cancelLink" value = "../da-chuyen"></c:set>
				</c:if>
				<c:if test="${listType == 6}">
					<c:set var = "cancelLink" value = "../da-chuyen-vn"></c:set>
				</c:if>
				<c:if test="${listType == 7}">
					<c:set var = "cancelLink" value = "../da-nhap-kho"></c:set>
				</c:if>
				<c:if test="${listType == 8}">
					<c:set var = "cancelLink" value = "../da-xuat-hd"></c:set>
				</c:if>
				<a class="btn btn-default" href="${ cancelLink}">Quay lại</a>
				</div>
				<table id="tableList" class="listBusCard table" width="600px">
					<thead>
						<tr>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr class="headings" role="row">
							<td>Mã đơn hàng</td>
							<td>${category.formattedId}</td>
						</tr>
						<tr class="headings" role="row">
							<td>Tên khách hàng</td>
							<td>${category.user.fullname}</td>
						</tr>
						<tr class="headings" role="row">
							<td>Trạng thái</td>
							<td><order:status status="${category.status }" /></td>
						</tr>
						<tr class="headings" role="row">
							<td>Tổng tiền</td>
							<td>${category.getTotal() }</td>
						</tr>
						<tr class="headings" role="row">
							<td>Người duyệt đơn hàng</td>
							<td>${category.getApprover() }</td>
						</tr>
						<tr class="headings" role="row">
							<td>Ngày duyệt đơn hàng</td>
							<td><fmt:formatDate value="${category.getApprovedDate()}" />
							</td>
						</tr>
						<tr class="headings" role="row">
							<td>Người mua hàng</td>
							<td>${category.getBuyer() }</td>
						</tr>
						<tr class="headings" role="row">
							<td>Ngày mua hàng</td>
							<td><fmt:formatDate value="${category.getBoughtDate()}" /></td>
						</tr>
						<tr class="headings" role="row">
							<td>Người chuyển hàng tại nước ngoài</td>
							<td>${category.getTransported() }</td>
						</tr>
						<tr class="headings" role="row">
							<td>Ngày chuyển hàng tại nước ngoài</td>
							<td><fmt:formatDate value="${category.getTransportedDate()}" />
							</td>
						</tr>
						<tr class="headings" role="row">
							<td>Người chuyển hàng về VN</td>
							<td>${category.getTransporterVn() }</td>
						</tr>
						<tr class="headings" role="row">
							<td>Ngày chuyển hàng về VN</td>
							<td><fmt:formatDate
									value="${category.getTransportedVnDate()}" /></td>
						</tr>
						<tr class="headings" role="row">
							<td>Vận đơn</td>
							<td>${category.transferId }</td>
						</tr>
						<tr class="headings" role="row">
							<td>Người kiểm hàng</td>
							<td>${category.getChecker() }</td>
						</tr>
						<tr class="headings" role="row">
							<td>Ngày kiểm hàng</td>
							<td><fmt:formatDate value="${category.getCheckedDate()}" />
							</td>
						</tr>
						<tr class="headings" role="row">
							<td>Người báo giá</td>
							<td>${category.getInformer() }</td>
						</tr>
						<tr class="headings" role="row">
							<td>Ngày kiểm hàng</td>
							<td><fmt:formatDate value="${category.getInformedDate()}" />
							</td>
						</tr>
						<tr class="headings" role="row">
							<td>Mã hóa đơn</td>
							<td>${category.bill.getFormattedId() }</td>
						</tr>
					</tbody>
				</table>

			</div>
		</form>
	</div>

	<script src="<c:url value="/resources/js/jquery.dataTables.min.js" />"></script>
	<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	
    <script src="<c:url value="/resources/js/bootstrap-select.min.js"/>"></script>
    <script src="<c:url value="/resources/js/dialogbox.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.freezeheader.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
	
	<!-- daterangepicker -->
    <script src="<c:url value="/resources/js/datepicker/daterangepicker.js"/>"></script>
	<script src="<c:url value="/resources/js/datePicker.custom.js"/>"></script>
    <script>
    var table;
    $(document).ready(function(){
		//init datatables
          table = $('#tableList').DataTable({
     		destroy: true,
     		"paging":   false,
     		"searching":   false,
 	   		"aLengthMenu" : [
 	   			[25, 50, 100, 200, -1],
 	   			[25, 50, 100, 200, "All"]],
 	   		"order": [[ 1, 'asc' ]],
 	   		"iDisplayLength" : 100,
	 	   	"ordering": false,
	        "info":     false
 	   	});
    });
    </script>
</body>
</html>