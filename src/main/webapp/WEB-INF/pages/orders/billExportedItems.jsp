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
<%@ taglib prefix="chkbox2" uri="/WEB-INF/taglibs/checkboxStatusTaglib.tld" %>

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
		<form action="da-xuat-hd" method="POST">
			<div class="row" style="height: 150px">
				<div class="col-xs-12 row">
					<label class="col-xs-2 right_align top_margin_5" >Mã hóa đơn: </label>
					<div class="col-xs-4">
						<input type="hidden" name="status" value="${ searchCondition.status}" />
						<input type="text" name= "billId" value="${ searchCondition.billId}" class="form-control">
					</div>
					<div>
						<button type="submit" class="btn btn-primary">
							<i class="fa fa-search" aria-hidden="true"> Tìm kiếm</i>
						</button>
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<table id="tableList" class="listBusCard table">
					<thead>
						<tr class="headings" role="row">
							<th><input type="checkbox" onchange="selectAllItems(this)" /></th>
							<th>Mã hóa đơn</th>
							<th>Mã đơn hàng</th>
							<th>Vận đơn</th>
							<th>Tên khách hàng</th>
							<th>Trạng thái</th>
							<th>Tổng tiền thực tế</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${allBills}" varStatus="status">
							<tr>
								<td>
									<chkbox2:chbox item="${item.id }" selectedItems="${selectedItems}"/>
								</td>
								<td rowspan="${item.categories.size()+1}">${item.getFormattedId()}</td>
								<td>
									<button type="button" class="btn btn-primary" onclick="approval(${item.id})">
										<i class="fa" aria-hidden="true" ></i> Xem hóa đơn
									</button>
								</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<c:forEach var="cart" items="${item.categories}" varStatus="cartstatus">
								<tr>
									<td style="display:none"></td>
									<td></td>
									<td>
										${cart.formattedId}
									</td>
									<td>${cart.transferId}</td>
									<td>
										${cart.user.fullname}
									</td>
									<td>
										<order:status status="${cart.status }"/>
									</td>
									<td>
										${cart.getOrderRealPrice() }
									</td>
									<td>
										<a href="${cart.id }"><i class="fa fa-info"
											aria-hidden="true"></i> View</a>
										<sec:authorize access="hasRole('ROLE_A')">
											/ <a href="admin/${item.id }"><i class="fa"
											aria-hidden="true"></i> Detail </a>
										</sec:authorize>
									</td>
									
								</tr>
							</c:forEach>
						</c:forEach>
					</tbody>
				</table>
				
			</div>
			<div class="div-bottom">
				<tag:paginate offset="${offset}" count="${count}"
					steps="${maxResult}"
					uri="${pageContext.request.contextPath}/donhang/da-xuat-hd"
					next="&raquo;" previous="&laquo;" />
			</div>
			
			<div class="col-sm-12" style="margin-bottom: 20px;">
				<sec:authorize access="hasAnyRole('ROLE_A')">
					<button id="addRow" type="button" class="btn btn-primary" onclick="alreadyToSend()">
						<i class="fa" aria-hidden="true" ></i> Sẵn sàng để giao
					</button>
				</sec:authorize>
			</div>
		</form>
	</div>
	
	
	<!-- Modal -->
	<div class="modal fade" id="billContent" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Thông tin hóa đơn</h4>
				</div>
				<div class="modal-body">
					<div id="content" ></div>
					<input type="hidden" id="selectedBill"/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="exportBill()">Xuất ra file</button>
					<button type="button" class="btn btn-default" onclick="printBill()">In hóa đơn</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	
	<script src="<c:url value="/resources/js/jquery.dataTables.min.js" />"></script>
	<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	
    <script src="<c:url value="/resources/js/bootstrap-select.min.js"/>"></script>
    <script src="<c:url value="/resources/js/dialogbox.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.freezeheader.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
	
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
    
    function selectItem(id, element) {
    	var chkbox = $(element);
    	if (chkbox.is(':checked')) {
    		$.ajax({
    			type : "GET",
    			url : "da-xuat-hd/chon-don-hang?id=" + id,
    			success : function(result) {
    			},
    			error : function() {
    			}
    		});
    	} else {
    		$.ajax({
    			type : "GET",
    			url : "da-xuat-hd/bo-chon-don-hang?id=" + id,
    			success : function(result) {
    			},
    			error : function() {
    			}
    		});
    	}
    }
    
    function selectAllItems(element) {
		var chkbox = $(element);
		var ids = "";
		$(".order_id").each(function (){
			$(this).prop('checked', chkbox.is(':checked'));
			ids += $(this).attr("order_id")+",";
		})
    	if (chkbox.is(':checked')) {
    		$.ajax({
    			type : "GET",
    			url : "da-xuat-hd/chon-tat-ca?ids="+ids,
    			success : function(result) {
    				
    			},
    			error : function() {
    			}
    		});
    	} else {
    		$.ajax({
    			type : "GET",
    			url : "da-xuat-hd/bo-chon-tat-ca?ids="+ids,
    			success : function(result) {
    				
    			},
    			error : function() {
    				
    			}
    		});
    	}
    }
    
	function approval(id){
		$.ajax({
			type : "GET",
			url : "xuat-hoa-don?id="+id,
			success : function(result) {
				if (result.status == 0) {
					alert(result.message);
					return;
				}
				$("#content").html(result.result);
				$("#billContent").modal('show');
				$("#selectedBill").val(id);
			},
			error : function() {
			}
		});
	}
	
	function exportBill() {
		$("#billContent").modal('hide');
		window.location.href = "xuat-hoa-don-file?id="+$("#selectedBill").val();
	}
	
	function printBill() {
		var mywindow = window.open('', 'PRINT', 'height=400,width=600');

		mywindow.document.write('<html><head><title>' + document.title  + '</title>');
		mywindow.document.write('</head><body >');
		mywindow.document.write('<h1>' + document.title  + '</h1>');
		mywindow.document.write($("#content").html());
		mywindow.document.write('</body></html>');

		mywindow.document.close(); // necessary for IE >= 10
		mywindow.focus(); // necessary for IE >= 10*/

		mywindow.print();
		mywindow.close();

		return true;

	}
	
	function alreadyToSend() {
		if ($('.order_id:checkbox:checked').length == 0) {
			alert("Vui lòng chọn một đơn hàng.");
			return;
		}
		var check = confirm("Bạn đang chuyển đơn hàng sang sẵn sàng để giao, hãy chắc chắn rằng đơn hàng đã được thanh toán đầy đủ?");
    	if (check) {
    		window.location.href = "san-sang-de-giao"; 
    	}
		
	}
	
</script>
</body>
</html>