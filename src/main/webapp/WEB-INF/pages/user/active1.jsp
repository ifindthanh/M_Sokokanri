<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- dung thu vien spring thong qua Prefix = "form" -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- su dung CSS qua Prefix = "c" -->

<html>
<head>
<title>パスワードの更新</title>
<link rel="shortcut icon"
	href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/custom.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script>
	function checkPrtScn(evt) {
		var charCode = (evt.which) ? evt.which : evt.keyCode;
		if (charCode == 42 || charCode == 32) {
			evt.preventDefault();
		}
	}
</script>
</head>
<body style="background: #F7F7F7;">
	<div class="">
		<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
			id="tologin"></a>
		<div id="wrapper">
			<div id="login" class="animate form" style="margin-top: 20px">
				<section class="login_content">
					<form:form modelAttribute="userForm" method="POST" action="active2"
						class="form-horizontal">
						<h1>パスワードの更新</h1>
						<div class="form-group">
							<div class="col-md-12 col-sm-12 col-xs-12 ">
								<p style="color: red;text-align: left;">
									<c:forEach items="${result}" var="item">
											* ${item}
										<br>
									</c:forEach>
								
								</p>
							</div>
						</div>
						<div class="form-group">
							<input name="oldPassword" type="password"
								class="form-control" placeholder="旧パスワード" maxlength="20"
								onkeypress="checkPrtScn(event)" />
						</div>
						<div class="form-group">
							<input name="password" type="password"
								class="form-control" placeholder="新しいパスワード" maxlength="20"
								onkeypress="checkPrtScn(event)" />
						</div>
						<div class="form-group">
							<input name="confirmPassword" type="password"
								class="form-control" placeholder="新しいパスワードの確認" maxlength="20"
								onkeypress="checkPrtScn(event)" />
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-primary">変更</button>
						</div>
						<div class="clearfix"></div>
						<div class="separator">
							<div class="clearfix"></div>
							<br />
							<div>
								<h1>
									<i class="fa fa-credit-card" style="font-size: 26px;"></i>
									倉庫管理システム
								</h1>
								<p>©2016 All Rights Reserved.</p>
							</div>
						</div>
					</form:form>
				</section>
			</div>
		</div>
	</div>
	<script src="<c:url value="/resources/js/custom.js" />"></script>
</body>
</html>