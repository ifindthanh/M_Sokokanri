<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<html>
<head>
<title>ユーザー情報</title>
<!-- Bootstrap core CSS -->
<link rel="shortcut icon"
	href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/custom.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/sweet-alert.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/sweet-alert.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<link href="<c:url value="/resources/css/icheck/green.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/dialogbox.js" />"></script>

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
										<div class="x_content">
											<div class="row">
												<div>
													<!-- top tiles -->

													<div class="main">

														<h3>ユーザー情報</h3>
													</div>
													<form:form modelAttribute="viewUserForm" method="POST"
														action="viewUser" enctype="multipart/form-data"
														class="form-horizontal div-center" id="viewUser">
														<div class="row">
															<div class="col-md-12">
																<div class="x_panel">

																	<div class="x_content">

																		<div class="row">
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name"> </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">

																					<h2 class="pull-left">
																						<strong>登録日:</strong>
																						<fmt:formatDate
																							value="${userEntity.getCreateDate()}"
																							pattern="yyyy/MM/dd" />
																					</h2>
																					&nbsp; &nbsp;&nbsp;
																					<h2 class="pull-left" style="margin-left: 20px">
																						<strong>更新日:</strong>
																						<fmt:formatDate
																							value="${userEntity.getUpdateDate()}"
																							pattern="yyyy/MM/dd" />
																					</h2>
																					<h2 class="pull-right" style="margin-left: 20px">
																						<strong><i class="fa fa-user">
																								&nbsp;&nbsp;</i>${userEntity.getLastName()}
																							${userEntity.getFirstName()} </strong>

																					</h2>
																				</div>
																			</div>
																			<div class="form-group">

																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">社員番号 </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input class="form-control has-feedback-left"
																						style="text-align: left; background-color: #eee; line-height: 22px; pointer-events: none;"
																						value="${userEntity.getUserCd()}"
																						disabled="disabled" readonly="readonly"><span
																						class="fa fa-user form-control-feedback left"
																						aria-hidden="true"></span> <input type="hidden"
																						name="userCd" id="userCd"
																						value="${userEntity.getUserCd()}" />
																				</div>
																			</div>
																			<div class="form-group">

																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name" 　data-toggle="tooltip"
																					data-placement="right" title="必須項目です。">姓名 </label>

																				<div class="col-md-3 col-sm-3 col-xs-6">
																					<input type="text"
																						class="form-control has-feedback-left pointer-events: none;"
																						name="lastName" maxlength="30" id="lastName"
																						disabled="disabled" readonly="readonly"
																						value="${userEntity.getLastName()}"
																						placeholder="姓"> <span
																						class="fa fa-user form-control-feedback left"
																						aria-hidden="true"></span>
																				</div>
																				<div class="col-md-3 col-sm-3 col-xs-6">
																					<input type="text"
																						class="form-control has-feedback-left pointer-events: none;"
																						name="firstName" maxlength="30" id="firstName"
																						disabled="disabled" readonly="readonly"
																						value="${userEntity.getFirstName()}"
																						placeholder="名"> <span
																						class="fa fa-user form-control-feedback left"
																						aria-hidden="true"></span>
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12">性別</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<div id="gender" class="btn-group left"
																						data-toggle="buttons">
																						<label class="btn btn-default disabled"
																							data-toggle-class="btn-primary"
																							data-toggle-passive-class="btn-default">
																							&nbsp; 男 &nbsp; <input type="radio" class="flat"
																							name="gender" id="genderM" value="M"
																							<c:if test="${userEntity.getGender()=='M'}"> checked </c:if>
																							disabled required />
																						</label> <label class="btn btn-default disabled"
																							data-toggle-class="btn-primary"
																							data-toggle-passive-class="btn-default">
																							&nbsp; 女 &nbsp; <input type="radio" class="flat"
																							name="gender" id="genderF" value="F"
																							<c:if test="${userEntity.getGender()=='F'}"> checked </c:if>
																							disabled />
																						</label>
																					</div>
																				</div>
																			</div>
																			<div class="form-group">

																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">電話番号 </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text"
																						class="form-control has-feedback-left"
																						style="ime-mode: disabled" disabled="disabled"
																						readonly="readonly" name="phone" maxlength="30"
																						id="phone" value="${userEntity.getPhone()}">
																					<span
																						class="fa fa-phone form-control-feedback left"
																						aria-hidden="true"></span>
																				</div>
																			</div>

																			<div class="form-group">

																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">会社名 </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text"
																						class="form-control has-feedback-left"
																						disabled="disabled" readonly="readonly"
																						name="companyName" maxlength="30" id="companyName"
																						value="${userEntity.getCompanyName()}"> <span
																						class="fa fa-building-o form-control-feedback left"
																						aria-hidden="true"></span>
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">部署 </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text"
																						class="form-control has-feedback-left"
																						disabled="disabled" readonly="readonly"
																						name="dept" maxlength="30" id="dept"
																						value="${userEntity.getDept()}"> <span
																						class="fa fa-users form-control-feedback left"
																						aria-hidden="true"></span>
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">役職/職責 </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text"
																						class="form-control has-feedback-left"
																						name="title" maxlength="30" id="title"
																						disabled="disabled" readonly="readonly"
																						value="${userEntity.getTitle()}"> <span
																						class="fa fa-briefcase form-control-feedback left"
																						aria-hidden="true"></span>
																				</div>
																			</div>
																			<div class="form-group">

																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">住所 </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text"
																						class="form-control has-feedback-left"
																						name="address" maxlength="100" id="address"
																						disabled="disabled" readonly="readonly"
																						value="${userEntity.getAddress()}"> <span
																						class="fa fa-map-marker form-control-feedback left"
																						aria-hidden="true"></span>
																				</div>
																			</div>

																			<div class="form-group">

																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">メールアドレス </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text"
																						class="form-control has-feedback-left"
																						style="ime-mode: disabled" name="email"
																						maxlength="50" id="email" disabled="disabled"
																						readonly="readonly"
																						value="${userEntity.getEmail()}"> <span
																						class="fa fa-envelope-o form-control-feedback left"
																						aria-hidden="true"></span>
																				</div>
																			</div>

																			<div class="ln_solid"></div>
																			<div class="form-group">
																				<div
																					class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">

																					<a href="../editUser/${userEntity.getUserCd()}"
																						class="btn btn-primary btn-lg" onclick=""> <i
																						class="fa fa-edit "></i> 編集
																					</a>
																					<sec:authorize access="hasAnyRole('ROLE_A', 'ROLE_S')">
																						<c:set var="offset" scope="page"
																							value="${offsetInUser}" />

																						<c:set var="maxResults" scope="page"
																							value="${maxResultsInUser}" />

																						<c:if test="${empty offset}">
																							<c:set var="offset" value="0" />
																						</c:if>
																						<c:set var="url" scope="page"
																							value="../list?offset=${offset}" />
																						<c:if test="${not empty maxResults}">
																							<c:set var="url"
																								value="${url}&maxResults=${maxResults}" />
																						</c:if>

																						<a href="${url}" class="btn btn-default btn-lg"
																							onclick=""> <i class="fa fa-arrow-left "></i>
																							戻る
																						</a>
																					</sec:authorize>
																				</div>
																			</div>
																		</div>


																	</div>
																</div>
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
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$("#gender").delegate('a', 'click', function(e){ e.stopImmediatePropagation(); })
		});
	</script>
	<!-- /page content -->
	<!-- <div id="custom_notifications" class="custom-notifications dsp_none"> -->
	<!-- 	<ul class="list-unstyled notifications clearfix" -->
	<!-- 		data-tabbed_notifications="notif-group"> -->
	<!-- 	</ul> -->
	<!-- 	<div class="clearfix"></div> -->
	<!-- 	<div id="notif-group" class="tabbed_notifications"></div> -->
	<!-- </div> -->
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/js/icheck/icheck.min.js" />"></script>
	<script src="<c:url value="/resources/js/custom.js" />"></script>
</body>
</html>