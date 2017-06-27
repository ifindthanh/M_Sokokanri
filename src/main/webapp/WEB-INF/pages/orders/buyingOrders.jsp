<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="contact" uri="/WEB-INF/taglibs/contactColumnTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="chkbox" uri="/WEB-INF/taglibs/commonCheckbox.tld" %>
<%@ taglib prefix="order" uri="/WEB-INF/taglibs/orderStatusTaglib.tld" %>

<html>
<head>
<title>Tất cả đơn hàng</title>
<META http-equiv="Pragma" content="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<meta http-equiv="cache-control" content="no-cache" />
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/icheck/green.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dataTables.bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap-select.min.css" />" rel="stylesheet"><style>
	.ui-datepicker-next::before {
	    content: " - ";
	}
	.ui-datepicker-prev, .ui-datepicker-next {
		cursor: pointer;
	}
	#ui-datepicker-div { 
		display: none; 
	}
</style>
</head>
<body>
	<div class="header_bar">
		<jsp:include page="/WEB-INF/pages/common/header.jsp" />
	</div>
	<div id="page_content">
		<p class="error">${message }</p>
		<form action="tat-ca" method="POST">
			<div class="col-sm-12 row" style="height: 150px">
				
				<input name="brand" value="${searchCondition.brand }" class="form-control" placeholder=""/>
			</div>
			<div class="col-sm-12">
				<table id="tableList" class="listBusCard table">
					<thead>
						<tr class="headings" role="row">
							<th><input type="checkbox" onchange="selectAllItems(this, 'da-duyet')" /></th>
							<th>Mã đơn hàng</th>
							<th>Tên khách hàng</th>
							<th style="width: 180px">Tên sản phẩm</th>
							<th style="width: 150px">Nhà phân phối</th>
							<th style="width: 150px">Link</th>
							<th style="width: 180px">Mô tả thêm</th>
							<th style="width: 50px">Đơn giá</th>
							<th style="width: 50px">Số lượng</th>
							<th style="width: 100px">Thành tiền</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${allOrders}" varStatus="status">
							<tr>
								<td>
									<input type="checkbox" class="order_id" order_id="${item.id }" onchange="selectItem(${item.id }, this, 'da-duyet')" />
								</td>
								<td>
									${item.formattedId}
								</td>
								<td>
									${item.user.fullname}
								</td>
								<td>
									<div class="lblName">${item.name }
									</div>
									<input type="text" value= "${item.name }" class="form-control hiddenAction txtName"/>
								</td>
								<td>
									<div class="lblBrand">${item.brand }
									</div>
									<input type="text" value= "${item.brand }" class="form-control hiddenAction txtBrand"/>
								</td>
								<td>
									<div class="lblLink">${item.link }
									</div>
									<input type="text" value= "${item.link }" class="form-control hiddenAction txtLink"/>
								</td>
								<td>
									<div class="lblDesc">${item.description }
									</div>
									<textarea class="form-control hiddenAction description">${item.description } </textarea>
								</td>
								<td>
									<div class="lblCost">${item.cost }
									</div>
									<input type="number" value= "${item.cost }" onchange="computeMoney(this)" class="small_width form-control hiddenAction txtCost"/>
								</td>
								<td>
									<div class="lblQuantity">${item.quantity }
									</div>
									<input type="number" value= "${item.quantity }" onchange="computeMoney(this)" class="small_width form-control hiddenAction txtQuantity"/>
								</td>
								<td>
									<div class="lblTotal">${item.total }
									</div>
									<input type="text" value= "${item.total }" class="form-control hiddenAction txtTotal" disabled="disabled"/>
								</td>
								
								<td>
									<c:if test="${item.isReadonly() ne true}">
										<a onclick="edit(this)" class = "myBtn origin btnEdit"><i class="fa fa-edit icon-resize-small"
										aria-hidden="true"></i></a>
										<div class= "action">
											<a onclick="save(this)" class="myBtn" item = "${item.id }"><i
												class="fa fa-save icon-resize-small" aria-hidden="true"></i></a> <a onclick="cancel(this)" class="myBtn"><i
												class="fa fa-ban icon-resize-small" aria-hidden="true"></i></a>
										</div>
									</c:if>
									<div>
										
									</div>
									
									<c:if test="${item.status eq 0 or item.status eq -1}">
										<sec:authorize access="hasAnyRole('ROLE_C', 'ROLE_A')">
											<a class="myBtn origin" orderId = "${item.id }"
												title="Duyện đơn hàng" onclick="approveAnOrder(this)">
												<i class="fa fa-thumbs-up icon-resize-small" aria-hidden="true"></i>
											</a>
											<a class="myBtn openNote origin" data-id="${item.id }"
												title="Ghi chú đơn hàng">
												<i class="fa fa-exclamation-circle  icon-resize-small" aria-hidden="true"></i>
											</a>
													
										</sec:authorize>
									</c:if>
									
									<sec:authorize access="hasRole('ROLE_A')">
										<a class="myBtn origin" href="admin/${item.id }"><i class="fa fa-cogs"
										aria-hidden="true"></i> </a>
									</sec:authorize>
								</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
			</div>
			<div class="div-bottom">
				<tag:paginate offset="${offset}" count="${count}"
					steps="${maxResult}"
					uri="${pageContext.request.contextPath}/donhang/cho-mua"
					next="&raquo;" previous="&laquo;" />
			</div>
			
		</form>
	</div>
	
	<script src="<c:url value="/resources/js/jquery.dataTables.min.js" />"></script>
	<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	
    <script src="<c:url value="/resources/js/bootstrap-select.min.js"/>"></script>
    <script src="<c:url value="/resources/js/dialogbox.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.freezeheader.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
	<script src="<c:url value="/resources/js/common.js"/>"></script>
	
	<!-- daterangepicker -->
    <script src="<c:url value="/resources/js/datepicker/daterangepicker.js"/>"></script>
	<script src="<c:url value="/resources/js/datePicker.custom.js"/>"></script>
    <script>
    var table;
    $(document).ready(function(){
		//init datatables
          table = $('#tableList').DataTable({
     		destroy: true,
     		"paging":   false,
     		"searching":   false,
 	   		"aLengthMenu" : [
 	   			[25, 50, 100, 200, -1],
 	   			[25, 50, 100, 200, "All"]],
 	   		"order": [[ 1, 'asc' ]],
 	   		"iDisplayLength" : 100,
	 	   	"ordering": false,
	        "info":     false
 	   	});
    });
    
	
    </script>
</body>
</html>