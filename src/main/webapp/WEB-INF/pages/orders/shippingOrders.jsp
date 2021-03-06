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
<%@ taglib prefix="id" uri="/WEB-INF/taglibs/idFormatterTaglib.tld" %>


<html>
<head>
<title>Sẵn sàng để giao</title>
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
		<form action="giao-hang" method="POST" id="shippingOrders">
			<div class="row">
				<label class="col-xs-2 right_align top_margin_5">Mã hóa đơn: </label>
				<div class="col-xs-4">
					<select name="bills" multiple="multiple"
						class="selectpicker form-control inputstl" onchange="search()">
						<option value="" data-hidden = "true">Chọn mã mua hàng</option>
						<c:forEach var="billId" items="${billIDs}" varStatus="status">
							<option value="${billId }"
								<c:if test="${searchCondition.bills.contains(billId)}">selected</c:if>>
								<id:formatter id="${billId }" />
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<input type="hidden" name="status" value = "${searchCondition.status }" />
			<sec:authorize access="hasRole('ROLE_A')">
				<div class="col-sm-12 action_container">
					<div class="left_10">
						<div class="dropdown">
						  <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Action
						  <span class="caret"></span></button>
							<ul class="dropdown-menu">
								<sec:authorize access="hasRole('ROLE_A')">
									<li><a onclick="sendToUser()">Giao hàng</a></li>
								</sec:authorize>
							</ul>
						</div>
					</div>
					<div class="left_10">
						<label id="selectedRecords" style="padding-top: 15px">(Đã
							chọn ${selectedItems.size()})</label>
					</div>
				</div>
			</sec:authorize>
			<div class="col-sm-12">
				<table id="tableList" class="listBusCard table">
					<thead>
						<tr class="headings" role="row">
							<sec:authorize access="hasRole('ROLE_A')">
								<th><input type="checkbox" id="selectAll" onchange="selectAllItems(this, 'giao-hang')" /></th>
							</sec:authorize>
							<th>Mã hóa đơn</th>
							<th>Mã đơn hàng</th>
							<th>Vận đơn</th>
							<th>Tên khách hàng</th>
							<th>Tổng tiền thực tế</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="bill" items="${allBills}" varStatus="status">
							<tr>
								<sec:authorize access="hasRole('ROLE_A')">
									<td>
										<chkbox2:chbox item="${bill.id }" selectedItems="${selectedItems}" action="giao-hang"/>
									</td>
								</sec:authorize>
								<td rowspan="${bill.items.size()+1}">${bill.getFormattedId()}</td>
								<td>
									<sec:authorize access="hasAnyRole('ROLE_A')">
										<button type="button" class="btn btn-primary" onclick="approval(${bill.id})">
											<i class="fa" aria-hidden="true" ></i> Xem hóa đơn
										</button>
									</sec:authorize>
								</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<c:forEach var="item" items="${bill.items}" varStatus="itemstatus">
								<tr>
									<td style="display:none"></td>
									<sec:authorize access="hasRole('ROLE_A')">
										<td></td>
									</sec:authorize>
									<td>
										<c:set var="link_action" scope="page" value="onclick = 'viewOrder(${item.id })' style='cursor: pointer'"></c:set>
										<sec:authorize access="hasRole('ROLE_A')">
											<c:set var="link_action" scope="page" value="href = 'admin/${item.id }'"></c:set>
										</sec:authorize>
										<a ${link_action }>${item.getFormattedId() }</a>
									</td>
									<td>${item.transferId}</td>
									<td>
										${item.user.fullname}
									</td>
									<td>
										${item.getComputePrice() }
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
					uri="${pageContext.request.contextPath}/donhang/giao-hang"
					next="&raquo;" previous="&laquo;" />
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
	
	<div class="modal fade" id="billContent" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Thông tin đơn hàng</h4>
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
	
	<!-- Modal -->
	<div class="modal fade" id="orderDetailModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Thông tin đơn hàng</h4>
				</div>
				<div class="modal-body">
					<div id="order_detail"></div>
					<input type="hidden" id="modal_orderId">
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" onclick="viewOrderHistory()">Xem lịch sử</button>
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
	<script src="<c:url value="/resources/js/common.js"/>"></script>
	
	<!-- daterangepicker -->
    <script src="<c:url value="/resources/js/datepicker/daterangepicker.js"/>"></script>
	<script src="<c:url value="/resources/js/datePicker.custom.js"/>"></script>
    <script>
    var table;
    $(document).ready(function(){
    	checkSelectAll();
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
	
	function sendToUser() {
		if ($('.order_id:checkbox:checked').length == 0) {
			alert("Vui lòng chọn một đơn hàng.");
			return;
		}
		var check = confirm("Đơn hàng sẽ được chuyển cho khách?");
    	if (check) {
    		window.location.href = "da-giao-hang"; 
    	}
		
	}
	
	function search() {
		$("#shippingOrders").submit();
	}
	
</script>
</body>
</html>