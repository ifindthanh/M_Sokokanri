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
				
			</div>
			<div class="col-sm-12">
				<table id="tableList" class="listBusCard table">
					<thead>
						<tr class="headings" role="row">
							<th><input type="checkbox" onchange="selectAllItems(this)" /></th>						
							<th>Mã đơn hàng</th>
							<th>Tên khách hàng</th>
							<th>Trạng thái</th>
							<th>Tổng tiền</th>
							<th>Tổng tiền thực tế</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${allOrders}" varStatus="status">
							<tr>
								<td>
									<input type="checkbox" class="order_id" order_id="${item.id }" onchange="selectItem(${item.id }, this)" />
								</td>
								<td>
									${item.formattedId}
								</td>
								<td>
									${item.user.fullname}
								</td>
								<td>
									<order:status status="${item.status }"/>
								</td>
								<td>
									${item.getOrderPrice() }
								</td>
								<td>
									${item.getOrderRealPrice() }
								</td>
								<td>
									<a href="${item.id }"><i class="fa fa-info"
										aria-hidden="true"></i> View</a>
									<sec:authorize access="hasRole('ROLE_A')">
										/ <a href="admin/${item.id }"><i class="fa"
										aria-hidden="true"></i> Detail </a>
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
					uri="${pageContext.request.contextPath}/donhang/da-mua"
					next="&raquo;" previous="&laquo;" />
			</div>
			<sec:authorize access="hasAnyRole('ROLE_T1', 'ROLE_A')">			
				<div class="col-sm-12" style="margin-bottom: 20px;">
					<button id="addRow" type="button" class="btn btn-primary" onclick="approval()">
						<i class="fa" aria-hidden="true" ></i> Chuyển hàng tại nước ngoài
					</button>
				</div>
			</sec:authorize>
		</form>
	</div>
	
	<!-- Modal -->
	<div id="addNoteModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Ghi chú vận đơn</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="transfer_information" class="description form-control" placeholder="Ghi chú vận đơn" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="addNote()">Ghi chú</button>
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
    			url : "da-mua/chon-don-hang?id=" + id,
    			success : function(result) {
    			},
    			error : function() {
    			}
    		});
    	} else {
    		$.ajax({
    			type : "GET",
    			url : "da-mua/bo-chon-don-hang?id=" + id,
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
    			url : "da-mua/chon-tat-ca?ids="+ids,
    			success : function(result) {
    				
    			},
    			error : function() {
    			}
    		});
    	} else {
    		$.ajax({
    			type : "GET",
    			url : "da-mua/bo-chon-tat-ca?ids="+ids,
    			success : function(result) {
    				
    			},
    			error : function() {
    				
    			}
    		});
    	}
    }
    
	
	function approval(){
		
		if ($('.order_id:checkbox:checked').length == 0) {
			alert("Vui lòng chọn một đơn hàng.");
			return;
		}
		
		var check = confirm("Bạn có thật sự muốn chuyển đơn hàng này sang đã chuyển tại nước ngoài?");
    	if (check) {
    		$('#addNoteModal').modal('show');
    	}
	}
	
	function addNote () {
		if (!$("#transfer_information") || $("#transfer_information").val() == "") {
			alert("Vui lòng ghi chú vận đơn");
			return;
		}
		window.location.href = "chuyen-don-hang?tranferID=" + $("#transfer_information").val(); 
	}
	
    </script>
</body>
</html>