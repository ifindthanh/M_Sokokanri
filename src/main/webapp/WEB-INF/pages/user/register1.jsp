<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>アカウント登録</title>

<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/custom.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
</head>
<body>
	<div class="container body">
		<div class="main_container div-center">
			<div class="right_col width-70" role="main">
				<!-- top tiles -->
				<div class="row tile_count">
					<div class="page-title">
						<div class="title_left">
							<h3>ユーザー登録</h3>
						</div>
					</div>
				</div>
				<!-- /top tiles -->
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="x_panel">
							<div class="x_content">
								<div class="row">

									<form:form method="GET" commandName="userBean" action="../register/check" class="form-horizontal">
 										<!-- <div style="margin-left: 40%;">
											<form:errors path="*" cssClass="errorblock" element="div"/>
										</div> -->
										<div class="form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12"
												for="userCd"　data-toggle="tooltip" data-placement="right" title="必須項目です。">&nbsp;
											</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<div style="margin:0" class="errorblock">${message }</div>
											</div>
										</div>
										<div class="form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12"
												for="userCd"　data-toggle="tooltip" data-placement="right" title="必須項目です。">社員番号<span class="required" style=" color: red"> *</span>
											</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<form:input type="text" path="userCd" required=""
 													class="form-control col-md-7 col-xs-12" style="ime-mode: disabled"/> 
											</div>
										</div>
										<div class="ln_solid"></div>
										<div class="form-group">
											<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3" style="text-align:center">
												<button type="submit" class="btn btn-primary">
														<i class="fa fa-save m-right-xs"></i> 入力</button>
												<button type="button" class="btn btn-default"
													onclick="history.back()"><i class="fa fa-close m-right-xs"></i> キャンセル</button>
											</div>
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
	<script src="<c:url value="/resources/js/custom.js" />"></script>
</body>
</html>