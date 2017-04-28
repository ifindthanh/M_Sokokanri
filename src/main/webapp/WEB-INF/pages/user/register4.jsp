<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<title>アカウント登録</title>
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/custom.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<link
	href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />"
	rel="stylesheet">

</head>

<div class="container body">
	<div class="main_container">
		<div class="right_col width-70" role="main">
			<!-- top tiles -->
			<div class="row tile_count">
				<div class="page-title">
					<div class="title_left">
						<h3>アカウント登録しました</h3>
					</div>
				</div>
			</div>
			<!-- /top tiles -->
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="x_panel" style="min-height:300px;padding-top:80px">
						<div class="x_content">
							<div class="row" style="text-align: center">
								<p>社員番号「${userCd}」で受付けました</p>
								<p>${email}へログイン情報を送付しましたのでご確認ください。</p>
								<div class="div-bottom">
									<a class="btn btn-primary " href="../user/list"
									style="margin-top:20px"><i
									class="fa fa-sign-in m-right-xs"></i> ユーザー一覧</a>
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