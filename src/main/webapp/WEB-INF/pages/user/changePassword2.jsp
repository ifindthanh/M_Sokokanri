<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Thay đổi mật khẩu</title>
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/custom.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script>
function goBack() {
    window.history.back()
}
</script>
</head>
<body>
	<div class="container body">
		<div class="main_container">
			<div class="right_col" role="main">
				<!-- top tiles -->
				<div class="row tile_count">
					<div class="page-title">
						<div class="title_left">
							<h3>Thay đổi mật khẩu</h3>
						</div>
					</div>
				</div>
				<!-- /top tiles -->
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="x_panel">
							<div class="x_content">
								<div class="row">
									<div class="form-group">
										<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
											<c:forEach items="${result}" var="item">
												${item}<br>
											</c:forEach>
											<br>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-12 col-sm-12 col-xs-12 col-md-offset-3">
											 <button onclick="goBack()" class="btn btn-primary">Back</button>    
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
	<script src="<c:url value="/resources/js/custom.js" />"></script>
</body>
</html>