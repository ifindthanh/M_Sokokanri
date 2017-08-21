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
		<form action="cho-mua" method="POST" id="approvedOrders">
			<div class="col-sm-12 row">
				<label class="col-xs-2 right_align top_margin_5" >Nhãn hàng: </label>
				<div class="col-xs-4">
					<select name="brands" multiple="multiple" class="selectpicker form-control inputstl" onchange="search()">
						<c:forEach var="brand" items="${allBrands}" varStatus="status">
							<option value="${brand }" <c:if test="${searchCondition.brands.contains(brand)}">selected</c:if>>${brand }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<input type="hidden" name="status" value = "${searchCondition.status }" />
			<div class="col-sm-12 action_container">
				<div class="left_10">
					<div class="dropdown">
					  <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Action
					  <span class="caret"></span></button>
						<ul class="dropdown-menu">
							<sec:authorize access="hasAnyRole('ROLE_B', 'ROLE_A')">
								<li><a onclick="approval()">Đã mua hàng</a></li>
								<li><a onclick="noteAnOrder()">Ghi chú đơn hàng</a></li>
							</sec:authorize>
							<li><a onclick="cancelOrders('cho-mua/huy-don-hang')">Hủy đơn hàng</a></li>
							<li><a onclick="deleteOrders('cho-mua/xoa-don-hang')">Xóa đơn hàng</a></li>
						</ul>
					</div>
				</div>
				<div class="left_10">
					<label id="selectedRecords" style="padding-top: 15px">(Đã
						chọn ${selectedItems.size()})</label>
				</div>
			</div>
			<div class="table_container">
				<table id="tableList" class="listBusCard table" style="width: 2000px !important;">
					<thead>
						<tr class="headings" role="row">
							<th><input type="checkbox" id="selectAll" onchange="selectAllItems(this, 'cho-mua')" /></th>
							<th>Mã đơn hàng</th>
							<th>Tên khách hàng</th>
							<th style="width: 180px">Tên sản phẩm</th>
							<th style="width: 150px">Nhà phân phối</th>
							<th style="width: 150px">Link</th>
							<th style="width: 180px">Mô tả thêm</th>
							<th style="width: 50px">Đơn giá</th>
							<th style="width: 50px">Số lượng</th>
							<th style="width: 100px">Thành tiền</th>
							<th style="width: 50px">Đơn giá mua</th>
							<th style="width: 50px">Số lượng mua</th>
							<th style="width: 100px">Thành tiền</th>
							<sec:authorize access="hasAnyRole('ROLE_B', 'ROLE_A')">
								<th style="width: 50px">Thực mua</th>
								<th style="width: 100px">Thành tiền</th>
							</sec:authorize>
							<th style="width: 100px">Mã mua hàng</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:set var="sum" value="0" scope="page"></c:set>
						<c:set var="realSum" value="0" scope="page"></c:set>
						<c:set var="computeSum" value="0" scope="page"></c:set>
						<c:forEach var="item" items="${allOrders}" varStatus="status">
							<tr>
								<td class="fixed"><chkbox2:chbox item="${item.id }"
										selectedItems="${selectedItems}" action="cho-mua" /></td>
								<td class="fixed">
									<c:set var="link_action" scope="page" value="onclick = 'viewOrder(${item.id })' style='cursor: pointer'"></c:set>
										<sec:authorize access="hasRole('ROLE_A')">
											<c:set var="link_action" scope="page" value="href = 'admin/${item.id }'"></c:set>
										</sec:authorize>
										<a <c:if test="${item.status eq -1 }">class = "noted"</c:if> ${link_action }>${item.getFormattedId() }</a>
								</td>
								<td>${item.user.fullname}</td>
								<td>
									<div class="lblName">${item.name }</div> 
									<input type="text" value="${item.name }" class="form-control hiddenAction txtName" />
								</td>
								<td>
									<div class="lblBrand">${item.brand }</div> 
									<input type="text" value="${item.brand }" class="form-control hiddenAction txtBrand" />
								</td>
								<td>
									<div class="lblLink">${item.link }</div> 
									<input type="text" value="${item.link }" class="form-control hiddenAction txtLink" />
								</td>
								<td>
									<div class="lblDesc">${item.description }</div> 
									<textarea class="form-control hiddenAction description">${item.description } </textarea>
								</td>
								<td>
									<div class="lblCost">${item.cost }</div> 
									<input type="number" value="${item.cost }" onchange="computeMoney(this)" class="small_width form-control hiddenAction txtCost" />
								</td>
								<td>
									<div class="lblQuantity">${item.quantity }</div> 
									<input type="number" value="${item.quantity }" onchange="computeMoney(this)"
										class="small_width form-control hiddenAction txtQuantity" />
								</td>
								<td>
									<div class="lblTotal">${item.total }</div> <input type="text"
									value="${item.total }"
									class="form-control hiddenAction txtTotal" disabled="disabled" />
									<c:set var="sum" value="${sum + item.total}" scope="page"></c:set>
								</td>
								<td>
									<div class="lblComputeCost">${item.computeCost }</div> <input
									type="number" value="${item.computeCost }"
									onchange="computeMoneyFromRealCost(this)"
									class="small_width form-control hiddenAction txtComputeCost" />
								</td>
								<td>
									<div class="lblRealQuantity">${item.realQuantity }</div> <input
									type="number" value="${item.realQuantity}"
									onchange="computeMoneyFromRealQuantity(this)"
									class="small_width form-control hiddenAction txtRealQuantity" />
								</td>
								<td>
									<div class="lblComputePrice">${item.computePrice }</div> <input
									type="text" value="${item.computePrice}"
									class="form-control hiddenAction txtComputePrice"
									disabled="disabled" /> 
									<c:set var="computeSum" value="${computeSum + item.computePrice}" scope="page"></c:set>
								</td>
								
								<sec:authorize access="hasAnyRole('ROLE_B','ROLE_A')">
									<td>
										<div class="lblRealCost">${item.realCost }</div> <input
										type="number" value="${item.realCost }" onchange="computeRealMoney(this)"
										class="small_width form-control hiddenAction txtRealCost" />
									</td>
									<td>
										<div class="lblRealPrice">${item.realPrice }</div> 
										<input type="text" value="${item.realPrice }"
										class="form-control hiddenAction txtRealPrice"
										disabled="disabled" />
										<c:set var="realSum" value="${realSum + item.realPrice}" scope="page"></c:set>
									</td>
								</sec:authorize>
								<td>
									<div class="lblBuyingCode">${item.buyingCode }</div> 
									<input type="text" value="${item.buyingCode }" class="form-control hiddenAction txtBuyingCode" />	
								</td>
								<td class="fixed">
									<c:if test="${item.isReadonly() ne true}">
										<a onclick="edit(this)" class="myBtn origin btnEdit"><i
											class="fa fa-edit icon-resize-small" aria-hidden="true"></i></a>
										<div class="action">
											<a onclick="save(this)" class="myBtn" item="${item.id }"><i
												class="fa fa-save icon-resize-small" aria-hidden="true"></i></a>
											<a onclick="cancel(this)" class="myBtn"><i
												class="fa fa-ban icon-resize-small" aria-hidden="true"></i></a>
										</div>
									</c:if> 
									<sec:authorize access="hasRole('ROLE_A')">
										<a class="myBtn origin" href="admin/${item.id }"><i
											class="fa fa-cogs" aria-hidden="true"></i> </a>
									</sec:authorize>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
			</div>
			<div class="col-sm-12">
			 	<div class="total_container">
					<label>Tổng tiền : <span id="total_price"><fmt:formatNumber value="${sum}" minFractionDigits="0" maxFractionDigits="4"/>
					</span></label>
					<br/>
					<label>Tổng tiền mua: <span id="compute_price">
						<fmt:formatNumber value="${computeSum}" minFractionDigits="0" maxFractionDigits="4"/>
					</span></label>
					
					<sec:authorize access="hasAnyRole('ROLE_B', 'ROLE_A')">
						<br/>
						<label>Tổng tiền thực tế mua: <span id="real_price">
						<fmt:formatNumber value="${realSum}" minFractionDigits="0" maxFractionDigits="4"/></span></label>	
					</sec:authorize>
				</div>
			</div>
			<div class="div-bottom">
				<tag:paginate offset="${offset}" count="${count}"
					steps="${maxResult}"
					uri="${pageContext.request.contextPath}/donhang/cho-mua"
					next="&raquo;" previous="&laquo;" />
			</div>
			
		</form>
	</div>
	
	
	<!-- Modal -->
	<div id="addNoteModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Ghi chú đơn hàng</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="action" />
					<textarea id="error_information" class="description form-control" placeholder="Điền thông tin sai sót của đơn hàng" style="width: 100%; height: 150px"></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="addNote()">Ghi chú</button>
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
	<script src="<c:url value="/resources/js/tableHeadFixer.js"/>"></script>
	<script src="<c:url value="/resources/js/common.js"/>"></script>
	
	<!-- daterangepicker -->
    <script src="<c:url value="/resources/js/datepicker/daterangepicker.js"/>"></script>
	<script src="<c:url value="/resources/js/datePicker.custom.js"/>"></script>
	
    <script>
    $(document).ready(function(){
    	checkSelectAll();
    	$("#tableList").tableHeadFixer({"head" : false, "left" : 2, "right": 1}); 
    });
    
    function search() {
    	$("#approvedOrders").submit();
    }
    
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
    		window.location.href = "cho-mua/bo-ghi-chu?id="+element.getAttribute("item");
    	}
    }
    
    function addNote() {
    	if (!$("#error_information").val() || $("#error_information").val() == "") {
    		alert("Vui lòng nhập thông tin sai sót của đơn hàng");
    		return;
    	}
    	$.ajax({
			type : "GET",
			url : "ghi-chu-mua?content=" + $("#error_information").val(),
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
    
    function approval(){
		if ($('.order_id:checkbox:checked').length == 0) {
			alert("Vui lòng chọn đơn hàng.");
			return;
		}
		var check = confirm("Các thông tin về đơn hàng sẽ không thể thay đổi nữa. Bạn có thật sự muốn phê duyệt đơn hàng này?");
    	if (check) {
    		window.location.href = "mua-nhieu-don-hang";
    	}
	}
    
    </script>
</body>
</html>