<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="contact" uri="/WEB-INF/taglibs/contactColumnTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<html>
<head>
<title>Tạo đơn hàng</title>
<META http-equiv="Pragma" content="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<meta http-equiv="cache-control" content="no-cache" />
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">

</head>
<body>
	<div class="rightContent" align="center">
		<div class="header_bar">
			<jsp:include page="/WEB-INF/pages/common/header.jsp" />
		</div>
		<div id="wrapper">
			<div class="body_container">
				<div class="container body">
					<div class="main_container">
						<div class="">
							<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12">
										<div class="main">
											<h3>Tạo đơn  hàng từ file</h3>
											<span>Bạn có thể soạn thảo đơn hàng ra file excel sau đó tải file lên để tạo đơn hàng tương ứng</span>
											<br/>
											<span>File bạn chọn phải đúng cấu trúc và định dạng như <a href="../resources/template/template.xlsx" download >file mẫu</a></span>
										</div>
												<div class="form-group">
													<!-- MyUploadForm -->
													<form:form modelAttribute="uploadBean" method="POST"
														action="tao-tu-file" enctype="multipart/form-data">
														<div class="form-group">
															<p class="error">${message}</p>
														</div>
														<div class="form-group">
															<div class="row form-horizontal">
																<div class = "row">
																	<div class="col-xs-12" style="margin-bottom: 20px">
																		<div class = "row">
																			<label class="control-label col-md-2 col-sm-3 col-xs-3"
																			for="uploadFile">Chọn file (*.xlsx) </label>
																			<form:input id="uploadFile" path="uploadFile"
																			type="file" data-buttonName="btn-primary"
																			class="filestyle col-xs-4" />
																		</div>
																		<div class = "row">
																			<label class="col-xs-2">Họ và tên:<span class="red_text">*</span> </label>
																			<div class="col-xs-4"><form:input id="fullName" path="fullName" type="text" class="form-control" readonly="${read_only }"/></div>
																		</div>
																		<div class = "row">
																			<label class="col-xs-2">Email:<span class="red_text">*</span> </label>
																			<div class="col-xs-4"><form:input id="email" path="email" type="text"  class="form-control" readonly="${read_only }"/></div>
																		</div>
																		<div class = "row">
																			<label class="col-xs-2">Số điện thoại:<span class="red_text">*</span> </label>
																			<div class="col-xs-4"><form:input id="phone" path="phone" type="text"  class="form-control" readonly="${read_only }"/></div>
																		</div>
																		<div class = "row">
																			<label class="col-xs-2">Địa chỉ:<span class="red_text">*</span> </label>
																			<div class="col-xs-4"><form:input id="address" path="address" type="text"  class="form-control" readonly="${read_only }"/></div>
																		</div>
																		<div class = "row">
																			<label class="col-xs-2">Thành phố: </label>
																			<div class="col-xs-4"><form:input path="city" type="text"  class="form-control" readonly="${read_only }"/></div>
																		</div>
																		<div class = "row">
																			<label class="col-xs-2">Ghi chú: </label>
																			<div class="col-xs-4"><form:textarea path="note" class="form-control description" rows="4" readonly="${read_only }"/></div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3" style= "padding: 20px">
															<button type="submit" class="btn btn-success" onclick="return validateForm()">
																<i class="fa fa-upload m-right-xs"></i> Tạo đơn hàng
															</button>
															<a href="tao-moi" class="btn btn-default"><i
																class="fa fa-close m-right-xs"></i> Trở về</a>
														</div>
													</form:form>
												</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-filestyle.min.js" />"></script>
<script type="text/javascript">
	function validateForm() {
		if (!$("#uploadFile").val() || $("#uploadFile").val() == "") {
			alert("Vui lòng chọn file");
			$("#uploadFile").focus();
			return false;
		}
		
		if (!$("#fullName").val() || $("#fullName").val() == "") {
			alert("Vui lòng điền họ và tên của bạn");
			$("#fullName").focus();
			return false;
		}
		
		if (!$("#email").val() || $("#email").val() == "") {
			alert("Vui lòng điền địa chỉ email");
			$("#email").focus();
			return false;
		}
		
		if (!$("#phone").val() || $("#phone").val() == "") {
			alert("Vui lòng điền số điện thoại");
			$("#phone").focus();
			return false;
		}
		
		if (!$("#address").val() || $("#address").val() == "") {
			alert("Vui lòng điền địa chỉ nhận hàng");
			$("#address").focus();
			return false;
		}
			
		return true;
	}
</script>
</html>