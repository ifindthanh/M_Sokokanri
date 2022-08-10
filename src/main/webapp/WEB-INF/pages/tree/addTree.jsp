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
		$("#addTree").submit();
	}
	function confirmClose(evt) {
		location.href = "../tree/" + evt;
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
														<h3>THÊM NHÓM LOẠI CÂY</h3>
													</div>
													<form:form modelAttribute="tree" method="POST"
														action="../nhom-cay/them-moi" enctype="multipart/form-data"
														class="form-horizontal div-center" id="addTree">
														<p class="error">${errorMessage }</p>
														<div class="row">
															<div class="col-md-12">
																<div class="x_panel">
																	<div class="x_content">
																		<div class="row">
																			<div class="form-group">
																				<label class="control-label col-md-3 col-sm-3 col-xs-12"> </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<h2 class="pull-right" style="margin-left: 20px">
																						<strong><i class="fa fa-user">
																								&nbsp;&nbsp;</i>${tree.getName()}</strong>

																					</h2>
																				</div>
																			</div>
																			<div class="form-group">
																				<label
																						class="control-label col-md-3 col-sm-3 col-xs-12"
																						for="tree-name">Tên nhóm lại cây </label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<input id = "tree-name" type="text" name="name" class="form-control has-feedback-left" maxlength="30"
																						   value="${tree.name }" />
																				</div>
																			</div>


																			<div class="form-group">
																				<label
																						class="control-label col-md-3 col-sm-3 col-xs-12"
																						for="description">Mô tả chi tiết</label>
																				<div class="col-md-6 col-sm-6 col-xs-12">
																					<textarea class="form-control description"
																							  name="description" maxlength="30" id="description">${tree.description } </textarea>
																				</div>
																			</div>

																			<div class="ln_solid"></div>
																			<div class="form-group">
																				<div
																					class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
																					<a class="btn btn-primary btn-lg" onclick="confirmSave();"> <i
																						class="fa fa-edit "></i> Thêm
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
</body>
</html>