<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="contact" uri="/WEB-INF/taglibs/contactColumnTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="formatter" uri="/WEB-INF/taglibs/idFormatterTaglib.tld" %>

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
		<h2 style="margin-left: 10px "> Lịch sử đơn hàng ${orderId }</h2>
		<p class="error">${message }</p>
		<form action="tat-ca" method="POST">
			<div class="table_container">
				<table id="tableList" class="listBusCard table">
					<thead>
						<tr class="headings" role="row">
							<th style="width: 180px">Tên sản phẩm</th>
							<th style="width: 150px">Nhà phân phối</th>
							<th style="width: 150px">Link</th>
							<th style="width: 180px">Mô tả thêm</th>
							<th style="width: 50px">Đơn giá</th>
							<th style="width: 50px">Số lượng</th>
							<th style="width: 100px">Thành tiền</th>
							<th style="width: 80px">Ngày thay đổi</th>
							<th style="width: 80px">Người thay đổi</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${allOrders}" varStatus="status">
							<tr>
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
									<formatter:date date="${item.updateDate}"/>
								</td>
								<td>
									${item.updateBy.email} ${item.updateBy.fullname} 
								</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<%-- <div class="div-bottom">
				<tag:paginate offset="${offset}" count="${count}"
					steps="${maxResult}"
					uri="${pageContext.request.contextPath}/donhang/xem-lich-su/${orderId }"
					next="&raquo;" previous="&laquo;" />
			</div> --%>
		</form>
	</div>
	<div class="col-sm-12 row footer" >
			<div class="col-xs-2"><button class="btn btn-default" onclick="window.history.back();">Quay lại</button></div>
		</div>
	<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	
    <script src="<c:url value="/resources/js/bootstrap-select.min.js"/>"></script>
    <script src="<c:url value="/resources/js/dialogbox.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.freezeheader.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
	<script src="<c:url value="/resources/js/tableHeadFixer.js"/>"></script>
	
	<!-- daterangepicker -->
    <script src="<c:url value="/resources/js/datepicker/daterangepicker.js"/>"></script>
	<script src="<c:url value="/resources/js/datePicker.custom.js"/>"></script>
	
	
	<script src="<c:url value="/resources/js/common.js"/>"></script>
    <script>
    var table;
    $(document).ready(function(){
    	//check the check boxx
    	checkSelectAll();
		//init datatables
		$("#tableList").tableHeadFixer({"head" : false, "left" : 2, "right": 1}); 
		
    });
    
    function noteAnOrder() {
    	if ($('.order_id:checkbox:checked').length != 1) {
    		alert("Vui lòng chọn một đơn hàng.");
    		return;
    	}
        $('#addNoteModal').modal('show');
    }
    
    function removeNote(element) {
    	var check = confirm("Bạn có chắc muốn bỏ ghi chú cho đơn hàng này?");
    	if (check) {
    		window.location.href = "cho-duyet/bo-ghi-chu?id="+element.getAttribute("item");
    	}
    }
    
    
	function approval(){
		if ($('.order_id:checkbox:checked').length == 0) {
			alert("Vui lòng chọn đơn hàng.");
			return;
		}
		var check = confirm("Các thông tin về đơn hàng sẽ không thể thay đổi nữa. Bạn có thật sự muốn phê duyệt đơn hàng này?");
    	if (check) {
    		window.location.href = "duyet-nhieu-don-hang";
    	}
	}
	
	function approveAnOrder(element) {
    	var check = confirm("Các thông tin về đơn hàng sẽ không thể thay đổi nữa. Bạn có thật sự muốn phê duyệt đơn hàng này?");
    	if (check) {
    		window.location.href = "duyet-don-hang?id="+element.getAttribute("orderId");
    	}
    }
	
	function addNote() {
    	if (!$("#error_information").val() || $("#error_information").val() == "") {
    		alert("Vui lòng nhập thông tin sai sót của đơn hàng");
    		return;
    	}
    	$.ajax({
			type : "GET",
			url : "ghi-chu?content=" + $("#error_information").val(),
			success : function(response) {
				if (response.status == 0) {
					alert(response.message);
					return;
				}
				window.location.href = window.location.href.split("#")[0];
			},
			error : function() {
			}

		});
    }
    </script>
</body>
</html>