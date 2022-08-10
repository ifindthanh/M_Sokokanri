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
	
	function confirmSave(checkRole) {
		var saveConfirm = window.confirm("Lưu thông tin về nhà cung cấp?");
		if (saveConfirm) {
			$("#viewUser").submit();
		}
	}
	function confirmClose(evt) {
		location.href = "../" + evt;
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
														<h3>CHỈNH SỬA</h3>
													</div>
													<c:set var="form_action" value="../chinh-sua/luu-thong-tin" scope="page"></c:set>
													<form:form modelAttribute="provider" method="POST"
														action="${form_action }" enctype="multipart/form-data"
														class="form-horizontal div-center" id="viewUser">
														<p class="error">${errorMessage }</p>
														<div class="row">
															<div class="col-md-12">
																<div class="x_panel">
																	<div class="x_content">
																		<div class="row">
																			<input type="hidden" value="${provider.id }" name="id"/>
																			<div class="form-group">
																				<label class="control-label col-md-3 col-sm-3 col-xs-12"> </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<h2 class="pull-right" style="margin-left: 20px">
																						<strong><i class="fa fa-user">
																								&nbsp;&nbsp;</i>${provider.name}</strong>

																					</h2>
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																						class="control-label col-md-3 col-sm-3 col-xs-12"
																						for="name">Tên nhà cung cấp </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="input" class="form-control has-feedback-left pointer-events: none;" name="name" maxlength="30" id="name"
																						   value="${provider.name }" />
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="representor" data-toggle="tooltip"
																					data-placement="right" title="">Tên người liên hệ</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text" class="form-control has-feedback-left pointer-events: none;"
																						name="representor" maxlength="30" id="representor"
																						value="${provider.representor }">
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																						class="control-label col-md-3 col-sm-3 col-xs-12"
																						for="email">Email </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="input" class="form-control has-feedback-left pointer-events: none;" name="email" maxlength="30" id="email"
																						   value="${provider.email }" />
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="phoneNumber">Số điện thoại</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input type="text"
																						class="form-control has-feedback-left"
																						name="phoneNumber" maxlength="30" id="phoneNumber"
																						value="${provider.phoneNumber }">
																				</div>
																			</div>
																			
																			<div class="form-group">
																				<label
																					class="control-label col-md-3 col-sm-3 col-xs-12"
																					for="address">Địa chỉ</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<textarea class="form-control description"
																						name="address" maxlength="30" id="address">${provider.address } </textarea>
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																						class="control-label col-md-3 col-sm-3 col-xs-12"
																						for="description">Mô tả chi tiết</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<textarea class="form-control description"
																							  name="description" maxlength="30" id="description">${provider.description } </textarea>
																				</div>
																			</div>
																			
																			<div class="ln_solid"></div>
																			<div class="form-group">
																				<div
																					class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
																					<c:if test="${selfEdit eq true }">
																						<a class="btn btn-primary btn-lg" onclick="confirmSave(false);"> <i
																							class="fa fa-edit "></i> Lưu thông tin
																						</a>
																						<a class="btn btn-default btn-lg" href="xem-thong-tin"> <i
																							class="fa fa-close m-right-xs"></i> Hủy bỏ
																						</a>
																					</c:if>
																					<c:if test="${selfEdit ne true }">
																						<a class="btn btn-primary btn-lg" onclick="confirmSave(true);"> <i
																							class="fa fa-edit "></i> Lưu thông tin
																						</a>
																						<button type="button" name="update"
																							class="btn btn-default btn-lg"
																							onclick="confirmClose('${provider.getId()}');">
																							<i class="fa fa-close m-right-xs"></i> Hủy bỏ
																						</button>
																					</c:if>
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
			} else {
				$("#" + roleId).removeAttr('selected');
			}
			
		} 
	</script>
</body>
</html>