<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="chkbox2" uri="/WEB-INF/taglibs/checkboxStatusTaglib.tld" %>

<html>
<head>
<title>Danh sách nhà cung cấp</title>
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap-select.min.css" />" rel="stylesheet">

</head>
<body>
	<div class="header_bar">
		<jsp:include page="/WEB-INF/pages/common/header.jsp" />
	</div>
	<div id="page_content">
		<p class="error">${message }</p>
		<form action="tat-ca" method="POST">
			<div class="col-sm-12 row">
				<div class="col-xs-12 row">
					<label class="col-xs-2 right_align top_margin_5">Email: </label>
					<div class="col-xs-4">
						<input type="text" class="form-control" value="${searchCondition.email }" name="email"/>
					</div>
					<div>
						<button type="submit" class="btn btn-primary">
							<i class="fa fa-search" aria-hidden="true"> Tìm kiếm</i>
						</button>
					</div>
				</div>
				<div class="col-xs-12 row">
					<label class="col-xs-2 right_align top_margin_5">Tên nhà cung cấp: </label>
					<div class="col-xs-4">
						<input type="text" class="form-control" value="${searchCondition.name }" name="name"/>
					</div>
				</div>
				<div class="col-xs-12 row">
					<label class="col-xs-2 right_align top_margin_5">Số điện thoại: </label>
					<div class="col-xs-4">
						<input type="text" class="form-control" value="${searchCondition.phoneNumber }"
							   name="phoneNumber"/>
					</div>
				</div>
			</div>
			<sec:authorize access="hasAnyRole('ROLE_A')">
				<div class="col-sm-12 action_container">
					<div class="left_10">
						<a href = "them-moi" class="btn btn-default">Thêm nhà cung cấp <i class="fa fa-plus"></i></a>
					</div>
					<div class="left_10">
						<div class="dropdown">
						  <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Action
						  <span class="caret"></span></button>
							<ul class="dropdown-menu">
								<li><a onclick="deleteProviders()">Xóa</a></li>
							</ul>
						</div>
						
					</div>
					<div class="left_10">
						<label id="selectedRecords" style="padding-top: 15px">(Đã chọn ${selectedItems.size()})</label>
					</div>
				</div>
			</sec:authorize>
			<div class="table_container">
				<table id="tableList" class="listBusCard table" style="width: 1000px">
					<thead>
						<tr class="headings" role="row">
							<th>
								<input type="checkbox" id="selectAll" onchange="selectAllItems(this, '../provider')" />
							</th>
							<th>Tên gợi nhớ</th>
							<th>Địa chỉ email</th>
							<th style="width: 100px">Họ và tên</th>
							<th style="width: 180px">Số điện thoại</th>
							<th style="width: 150px">Địa chỉ</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="sum" value="0" scope="page"></c:set>
						<c:forEach var="item" items="${allProvides}" varStatus="status">
							<tr class="table-row" provider_id="${item.id }">
								<td>
									<chkbox2:chbox item="${item.id }" selectedItems="${selectedItems}" action="../provider"/>
								</td>
								<td>
										${item.name}
								</td>
								<td>
									<div><a href="${item.id }">${item.email}</a></div>
								</td>
								<td>
									${item.representor}
								</td>
								<td>
									${item.phoneNumber }
								</td>
								<td>${item.address }
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="div-bottom">
				<tag:paginate offset="${offset}" count="${count}"
					steps="${maxResult}"
					uri="${pageContext.request.contextPath}/provider/tat-ca"
					next="&raquo;" previous="&laquo;" />
			</div>
		</form>
	</div>
<script src="<c:url value="/resources/js/datePicker.custom.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap-select.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/common.js"/>"></script>
<script>
	$(document).ready(function(){
		checkSelectAll();
		$('.table-row').on('dblclick', function() {
			$('#ajax-overlay').show();
			window.location.href = $(this).attr("provider_id");
		});
	});
	function deleteProviders() {
		if ($('.order_id:checkbox:checked').length == 0) {
			alert("Vui lòng chọn nhà cung cấp.");
			return;
		}
		var check = confirm("Bạn có chắc muốn xóa nhà cung cấp đã chọn?");
		if (check) {
			$('#ajax-overlay').show();
			$.ajax({
				type : "GET",
				url : "xoa-provider",
				success : function(result) {
					$('#ajax-overlay').hide();
					if (result.status == 0) {
						alert(result.message);
						return;
					}
					alert("Xóa nhà cung cấp thành công");
					window.location.href = window.location.href.split("#")[0];
				},
				error : function() {
					alert("Xóa nhà cung cấp thất bại, vui lòng liên hệ với quản trị viên");
					$('#ajax-overlay').hide();
				}
			});
		}
	}
	
</script>
</body>
</html>