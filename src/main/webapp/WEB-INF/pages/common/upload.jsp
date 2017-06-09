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
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-filestyle.min.js" />"></script>
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
									<div class="x_panel table-panel">
										<div class="main">
											<h3>Tạo đơn  hàng từ file</h3>
										</div>
										<div class="x_panel">
											<div class="row">
												<div class="form-group">
													<!-- MyUploadForm -->
													<form:form modelAttribute="uploadBean" method="POST"
														action="tao-tu-file" enctype="multipart/form-data">
														<div class="form-group">
															<p class=errorblock>${message}</p>
														</div>
														<div class="form-group">
															<div class="row form-horizontal">
																<div style="width: 800px; text-align: center">
																	<label class="control-label col-md-2 col-sm-3 col-xs-3"
																		for="uploadFile">Chọn file (*.xlsx) </label>
																	<form:input id="uploadFile" path="uploadFile"
																		type="file" data-buttonName="btn-primary"
																		class="filestyle" />
																</div>
															</div>
														</div>
														<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
															<button type="submit" class="btn btn-success">
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
			</div>
		</div>
	</div>
</body>
</html>