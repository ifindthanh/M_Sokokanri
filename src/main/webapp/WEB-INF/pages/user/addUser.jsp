<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/rolesDisplayingTaglib.tld" prefix="roles" %>

<html>
<head>
<title>Chỉnh sửa user</title>
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<script>
	
	function confirmSave() {
		if (!$('#allRoles').val()) {
			alert("Vui lòng chọn ít nhật một role.");
			return;
		}
		$("#viewUser").submit();
	}
	function confirmClose(evt) {
		location.href = "../user/" + evt;
	}
</script>
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
													<div class="main">
														<h3>CHỈNH SỬA USER</h3>
													</div>
													<form:form modelAttribute="userForm" method="POST"
														action="../user/them-moi" enctype="multipart/form-data"
														class="form-horizontal div-center" id="viewUser">
														<p class="error">${errorMessage }</p>
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
																					<input type="text" name="email" class="form-control has-feedback-left" maxlength="30"
																						value="${user.email }" />
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name" data-toggle="tooltip"
																					data-placement="right" title="">Họ và tên</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text" class="form-control has-feedback-left pointer-events: none;"
																						name="fullname" maxlength="30" id="fullname"
																						value="${user.fullname }">
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12">Giới tính</label>
																				<div class="col-md-6 col-sm-6 col-xs-12" style="text-align: left">
																					<input id="rd_male" type="radio" name="gender" value="M" <c:if test="${user.gender == 'M'}"> checked </c:if> /> <label class="gender" for="rd_male"> Nam </label>
																					<input id="rd_female" type="radio" name="gender" value="F" <c:if test="${user.gender == 'F'}"> checked </c:if>/> <label class="gender" for="rd_female"> Nữ</label>
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
																						name="address" maxlength="30" id="address">${user.address } </textarea>
																				</div>
																			</div>
																			
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="first-name">Roles</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<roles:roles disable="false" allRoles="${roles }"/>
																				</div>
																			</div>
															
																			<div class="ln_solid"></div>
																			<div class="form-group">
																				<div
																					class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
																					<a class="btn btn-primary btn-lg" onclick="confirmSave();"> <i
																						class="fa fa-edit "></i> Thêm user
																					</a>
																					
																					<a class="btn btn-default btn-lg"
																						href="tat-ca">
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
	<script type="text/javascript">
		function selectRole(element, roleId) {
			if ($(element).is(':checked')) {
				$("#" + roleId).prop('selected', true);	
				console.log($('#allRoles').val());
			} else {
				$("#" + roleId).removeAttr('selected');
			}
			
		} 
	</script>
</body>
</html>