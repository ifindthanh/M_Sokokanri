<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="transaction" uri="/WEB-INF/taglibs/transactionTypeTaglib.tld" %>

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
	<div id="page_content" style="margin: 0 auto;">
		<p class="info">${inforMessage }</p>
		<p class="error">${message }</p>
		<form:form modelAttribute="transaction" action="them-giao-dich" method="POST" enctype="multipart/form-data">
			<div class="col-sm-2"></div>
			<div class="col-sm-10">
				<div class="row">
					<div class="form-group row">
						<input type="hidden" value="${transaction.user.id }" name="userId">
						<input type="hidden" value="${transaction.user.id }" name="user.id">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"
							for="first-name">Email </label>
						<div class="col-md-6 col-sm-6 col-xs-12">
							<input type="text" class="form-control has-feedback-left"
								style="ime-mode: disabled" disabled="disabled"
								readonly="readonly" maxlength="30" value="${transaction.user.email }">
							<input type="hidden" value="${transaction.user.email }" name="user.email">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"
							for="first-name" 　data-toggle="tooltip" data-placement="right"
							title="">Họ và tên</label>
						<div class="col-md-6 col-sm-6 col-xs-12">
							<input type="text"
								class="form-control has-feedback-left pointer-events: none;"
								maxlength="30" id="fullname" disabled="disabled"
								readonly="readonly" value="${transaction.user.fullname }">
							<input type="hidden" value="${transaction.user.fullname }" name="user.fullname">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"
							for="first-name" 　data-toggle="tooltip" data-placement="right"
							title="">Số dư tài khoản</label>
						<div class="col-md-6 col-sm-6 col-xs-12">
							<a href='<c:url value="/user/vi-tien/tat-ca-giao-dich/${transaction.user.id}"></c:url>'>${transaction.user.accountBalance }</a>
							<input type="hidden" value="${transaction.user.accountBalance }" name="user.accountBalance">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"
							for="first-name">Loại giao dịch</label>
						<div class="col-md-6 col-sm-6 col-xs-12">
							<transaction:type/>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"
							for="first-name">Số tiền (VNĐ)</label>
						<div class="col-md-6 col-sm-6 col-xs-12">
							<input type="number" class="form-control has-feedback-left"
								name="amount"
								maxlength="30" id="amount" >
						</div>
					</div>
					
					<div class="form-group row">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"
							for="first-name">Ghi chú</label>
						<div class="col-md-6 col-sm-6 col-xs-12">
							<textarea class="form-control has-feedback-left description"
								name="comment" id="comment" ></textarea>
						</div>
					</div>


					<div class="ln_solid"></div>
					<div class="form-group">
						<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
							<input type="submit" 
								class="btn btn-primary btn-lg" onsubmit="return validateTransaction()" value="Thêm giao dịch" />
							<a href="../tat-ca" class="btn btn-default btn-lg"> <i
								class="fa fa-close m-right-xs"></i> Quay lại
							</a>
						</div>
					</div>
				</div>

			</div>
		</form:form>
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
	<script type="text/javascript">
		function validateTransaction() {
			return true;
		}
	</script>
</body>
</html>