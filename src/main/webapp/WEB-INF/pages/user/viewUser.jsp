<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="/WEB-INF/taglibs/rolesDisplayingTaglib.tld" prefix="roles" %>
<html>
<head>
<title>Thông tin user</title>
<!-- Bootstrap core CSS -->
<link rel="shortcut icon"
	href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
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

														<h3>THÔNG TIN USER</h3>
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
																					<h2 class="pull-right" style="margin-left: 20px">
																						<strong><i class="fa fa-user">
																								&nbsp;&nbsp;</i>${user.getFullname()}</strong>

																					</h2>
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">Email </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text"
																						class="form-control has-feedback-left"
																						style="ime-mode: disabled" disabled="disabled"
																						readonly="readonly" maxlength="30"
																						value="${user.email }">
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name" 　data-toggle="tooltip"
																					data-placement="right" title="">Họ và tên</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text" class="form-control has-feedback-left pointer-events: none;"
																						name="fullname" maxlength="30" id="fullname"
																						disabled="disabled" readonly="readonly"
																						value="${user.fullname }">
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12">Giới tính</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text"
																						class="form-control has-feedback-left"
																						style="ime-mode: disabled" disabled="disabled"
																						readonly="readonly" maxlength="30"
																						<c:if test="${user.gender == 'M' }">value = "Nam"</c:if>
																						<c:if test="${user.gender == 'F' }">value = "Nữ"</c:if>
																						>
																				</div>
																				<div class="col-md-6 col-sm-6 col-xs-12"
																					style="text-align: left">
																					
																					
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">Số điện thoại</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text"
																						class="form-control has-feedback-left"
																						disabled="disabled" readonly="readonly"
																						name="phone" maxlength="30" id="phone"
																						value="${user.phone }">
																				</div>
																			</div>
																			
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">Địa chỉ</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<textarea class="form-control description"
																						disabled="disabled" readonly="readonly"
																						name="address" maxlength="30" id="address">${user.address } </textarea>
																				</div>
																			</div>
																			
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">Roles</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<roles:roles disable="true" allRoles="${roles }"/>
																				</div>
																			</div>
															
																			<div class="ln_solid"></div>
																			<div class="form-group">
																				<div
																					class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
																					<a href="../editUser/${user.getId()}"
																						class="btn btn-primary btn-lg" onclick=""> <i
																						class="fa fa-edit "></i> Chỉnh sửa
																					</a>
																					<a href="tat-ca"
																						class="btn btn-default btn-lg">
																						<i class="fa fa-close m-right-xs"></i> Quay lại
																					</a>
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
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>