<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="contact" uri="/WEB-INF/taglibs/contactColumnTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="chkbox" uri="/WEB-INF/taglibs/commonCheckbox.tld" %>

<html>
<head>
<title>Tất cả đơn hàng</title>
<META http-equiv="Pragma" content="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<meta http-equiv="cache-control" content="no-cache" />
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<link href="<c:url value="/resources/css/icheck/green.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dataTables.bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap-select.min.css" />" rel="stylesheet">
<style>
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
		<div class="col-sm-12">
			<p class="error">${message }</p>
			<form:form method="POST" action="luu-don-hang" modelAttribute="category" id="order_form">
				<c:set var = "real_price_access" scope = "page" value = "true"></c:set>
				<c:if test="${category.status eq 1 or category.status eq -2}">
					<sec:authorize access="hasAnyRole('ROLE_B', 'ROLE_A')">
						<c:set var = "real_price_access" scope = "page" value = "false"></c:set>
					</sec:authorize>
				</c:if>
				<table id="tableList" class="order_table">
					<thead>
						<tr class="headings" role="row">
							<th>No</th>
							<th>Tên sản phẩm</th>
							<th>Nhà phân phối</th>
							<th>Link</th>
							<th>Mô tả thêm</th>
							<th>Đơn giá</th>
							<th>Số lượng</th>
							<th>Thành tiền</th>
							<th>Thực tế mua</th>
							<c:if test="${read_only ne true}"><th></th></c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${category.items}" varStatus="status">
							<tr>
								<form:input type="hidden" path="items[${status.index}].id" value="${item.id }" readonly="${read_only }"/>
								<td></td>
								<td>
									<form:input class="txtName form-control" path="items[${status.index}].name" value="${item.name }" readonly="${read_only }"/>
								</td>
								<td><form:input class="form-control" path="items[${status.index}].brand" type="text" readonly="${read_only }"/></td>
								<td><form:input class="txtLink form-control" path="items[${status.index}].link" type="text" readonly="${read_only }"/></td>
								<td><form:textarea class="description form-control" path="items[${status.index}].description" readonly="${read_only }"/></td>
								<td><form:input class="txtCost form-control" path="items[${status.index}].cost" type="text" onchange="computeMoney(this)" readonly="${read_only }"/></td>
								<td><form:input class="txtQuantity form-control" path="items[${status.index}].quantity" type="text" onchange="computeMoney(this)" readonly="${read_only }"/></td>
								<td><form:input class="txtTotal form-control" path="items[${status.index}].total" type="text" readonly="true"/></td>
								<td>
									<form:input class="real_price form-control" path="items[${status.index}].realPrice" type="text" readonly="${real_price_access }" onchange="saveRealPrice(this, ${item.id })"/>
								</td>
								<c:if test="${read_only ne true}">
									<td>
										<a class="myAction" href="#"><i class="fa fa-save"
											aria-hidden="true"></i></a>
											/
										<a class="deleteItem myAction" item = "${item.id }"><i class="fa fa-trash-o"
											aria-hidden="true"></i></a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div style="position: relative">
					<div>
						<c:if test="${read_only ne true}">
							<button id="addRow" type="button" class="btn btn-default">
								<i class="fa fa-plus" aria-hidden="true" ></i> Thêm sản phẩm
							</button>
						</c:if>
						
						<c:if test="${category.status eq 0 or category.status eq -1}">
							<sec:authorize access="hasAnyRole('ROLE_C', 'ROLE_A')">
								<input type="button" class="btn btn-primary"
									value="Duyện đơn hàng" onclick="approve()"/>
								<input type="button" class="btn btn-default openNote" data-id="-1"
									value="Ghi chú đơn hàng"/>	
							</sec:authorize>
						</c:if>
						<c:if test="${category.status eq 1 or category.status eq -2}">
							<sec:authorize access="hasAnyRole('ROLE_B', 'ROLE_A')">
								<input type="button" class="btn btn-primary" value="Mua hàng xong" onclick="finishBuying()"/>
								<input type="button" class="btn btn-default openNote" value="Ghi chú đơn hàng" data-id="-2"/>
							</sec:authorize>
						</c:if>
						<c:if test="${category.status eq 2}">
							<sec:authorize access="hasAnyRole('ROLE_T1', 'ROLE_A')">
								<input type="button" class="btn btn-primary" value="Đã chuyển hàng ở nước ngoài" data-toggle="modal" data-target="#transferModal"/>
							</sec:authorize>
						</c:if>
						<c:if test="${category.status eq 3}">
							<sec:authorize access="hasAnyRole('ROLE_T2', 'ROLE_A')">
								<input type="button" class="btn btn-primary" value="Chuyển về Việt Nam" onclick="transferToVN()"/>
							</sec:authorize>
						</c:if>
					</div>
					<div style="float:right; position: absolute; right: 30px; top: 0px;">
						<label>Tổng tiền : <span id="total_price">${category.getOrderPrice() }</span></label>
						<br/>
						<label>Tổng tiền thực mua: <span id="total_real_price">0</span></label>
					</div>
					
				</div>
				
				<hr/>
				
				<div class="col-xs-12" style="margin-bottom: 20px">
					<h4><label>Thông tin khách hàng</label></h4>
					<div class = "row">
						<label class="col-xs-2">Họ và tên: </label>
						<div class="col-xs-4"><form:input id="fullName" path="fullName" type="text" class="form-control" readonly="${read_only }"/></div>
					</div>
					<div class = "row">
						<label class="col-xs-2">Email: </label>
						<div class="col-xs-4"><form:input id="email" path="email" type="text"  class="form-control" readonly="${read_only }"/></div>
					</div>
					<div class = "row">
						<label class="col-xs-2">Số điện thoại: </label>
						<div class="col-xs-4"><form:input id="phone" path="phone" type="text"  class="form-control" readonly="${read_only }"/></div>
					</div>
					<div class = "row">
						<label class="col-xs-2">Địa chỉ: </label>
						<div class="col-xs-4"><form:input id="address" path="address" type="text"  class="form-control" readonly="${read_only }"/></div>
					</div>
					<div class = "row">
						<label class="col-xs-2">Thành phố: </label>
						<div class="col-xs-4"><form:input path="city" type="text"  class="form-control" readonly="${read_only }"/></div>
					</div>
					<div class = "row">
						<label class="col-xs-2">Ghi chú: </label>
						<div class="col-xs-4"><form:textarea path="note" class="form-control description" rows="4" readonly="${read_only }"/></div>
					</div>
				</div>
				<br/>
				<c:if test="${read_only ne true}">
					<input type="submit" class="btn btn-primary" value="Lưu thông tin" onclick="return validateForm()"/>
				</c:if>

				<input type="hidden" value="${category.items.size()}" id="item_size"/>
				<form:input type="hidden" path = "userId" value="${category.userId}"/>
				<form:input type="hidden" path = "id" value="${order.id}" id="orderId"/>
				<form:input type="hidden" path = "status" value="${order.status}"/>
			</form:form>

		</div>
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
					<button type="button" class="btn btn-primary" onclick="transfer()">Ghi chú</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	
	<!-- Modal -->
	<div id="transferModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Ghi chú vận đơn</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="transferId" class="description form-control" placeholder="Ghi chú vận đơn" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="transfer()">Ghi chú</button>
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
    $(".real_price").each(function (){
    	if (!$(this).val() || $(this).val() == "") {
    		return;
    	}
    	$("#total_real_price").html(parseFloat($("#total_real_price").html()) + parseFloat($(this).val()));
    })
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
       	$(".deleteItem").click(function (){
       		var agreeToDelete = confirm("Bạn có thực sự muốn xóa mặt hàng đã chọn?");
       		if (!agreeToDelete) {
       			return;
       		}
       		var currentRow = $(this).parents('tr');
       		$.ajax({
				type : "GET",
				url : "xoa-item?id=" + $(this).attr("item"),
				success : function(response) {
					if (response.status == 0) {
						alert(response.message);
						return;
					}
					table
		            .row(currentRow)
		            .remove()
		            .draw();
		       		table.column(0, {}).nodes().each( function (cell, i) {
		    			cell.innerHTML = i+1;
		    		}).draw();
				},
				error : function() {
				}
	
			});
       	});
       	
       	
       	
       	$('#addRow').on( 'click', function () {
       		var index = parseInt($("#item_size").val());   
       		
    		$("#item_size").val(index + 1);
            table.row.add( [
            	"",
            	"<input class=\"txtName form-control\" type=\"text\" name=\"items[" + index + "].name\" value=\"\" />",
        		"<input class=\"form-control\" type=\"text\" name=\"items[" + index + "].brand\" />",
        		"<input class=\"txtLink form-control\" type=\"text\" name=\"items[" + index + "].link\" />",
        		"<textarea class=\"description form-control\" name=\"items[" + index + "].description\"> </textarea>",
        		"<input class=\"txtCost form-control\" type=\"text\" name=\"items[" + index + "].cost\" onchange=\"computeMoney(this)\" />",
        		"<input class=\"txtQuantity form-control\" type=\"text\" name=\"items[" + index + "].quantity\" onchange=\"computeMoney(this)\" />",
        		"<input class=\"txtTotal form-control\" type=\"text\" readonly=\"true\" name=\"items[" + index + "].total\"/>",
        		"<input class=\"real_price form-control\" type=\"text\" readonly=\"true\" name=\"items[" + index + "].realPrice\" />",
        		"<a class= \"myAction\" href=\"#\"><i class=\"fa fa-save\" aria-hidden=\"true\"></i></a>/<a class= \"deleteItem myAction\" onclick=\"deleteRow(this)\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\" ></i></a>"
            ] ).draw( false );
            table.column(0, {}).nodes().each( function (cell, i) {
    			cell.innerHTML = i+1;
    		} ).draw( false );
        } );
       	
		table.column(0, {}).nodes().each( function (cell, i) {
			cell.innerHTML = i+1;
		} );
         
    });
    function deleteRow(element){
    	var agreeToDelete = confirm("Bạn có thực sự muốn xóa mặt hàng đã chọn?");
   		if (!agreeToDelete) {
   			return;
   		}
   		table
        .row( $(element).parents('tr') )
        .remove()
        .draw();
   		table.column(0, {}).nodes().each( function (cell, i) {
			cell.innerHTML = i+1;
		}).draw();
   	}
    
    function computeMoney(element) {
    	var currentElement = $(element);
    	var txtTotal = currentElement.closest('tr').find(".txtTotal");
    	var txtCost = currentElement.closest('tr').find(".txtCost");
    	var txtQuantity = currentElement.closest('tr').find(".txtQuantity");
    	if (txtCost.val() && txtCost.val() != "" 
    			&& txtQuantity.val() && txtQuantity.val() != "") {
    		txtTotal.val(parseInt(txtQuantity.val())* parseFloat(txtCost.val()));
    	} else {
    		txtTotal.val("");
    	}
    	var total_price = 0;
    	$(".txtTotal").each(function (){
    		if ($(this).val() && $(this).val() != "")
    			total_price += parseFloat($(this).val());
    	});
    	$("#total_price").html(total_price);
    }
    
    function approve() {
    	var check = confirm("Các thông tin về đơn hàng sẽ không thể thay đổi nữa. Bạn có thật sự muốn phê duyệt đơn hàng này?");
    	if (check) {
    		window.location.href = "duyet-don-hang?id="+$("#orderId").val();
    	}
    }
    
    function addNote() {
    	if (!$("#error_information").val() || $("#error_information").val() == "") {
    		alert("Vui lòng nhập thông tin sai sót của đơn hàng");
    		return;
    	}
    	var base_url;
    	if ($("#action").val() == -1) {
    		base_url = "ghi-chu";
    	} else if ($("#action").val() == -2) {
    		base_url = "ghi-chu-mua";
    	} else {
    		return;
    	}
    	$.ajax({
			type : "GET",
			url : base_url+"?id=" + $("#orderId").val()+"&content=" + $("#error_information").val(),
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
    
    function finishBuying(){
    	var submitForm = true;
    	$(".real_price").each(function (){
    		console.log($(this).val());
    		if (!$(this).val() || $(this).val() == "") {
    			alert("Tồn tại mặt hàng chưa mua được, vui lòng ghi chú đơn hàng cho khách hàng");
    			submitForm = false;
    			return false;
    		}
    	});
    	if (submitForm) {
    		$("#order_form").attr("action", "mua-hang");
    		$("#order_form").submit();
    	}
    }
    
    function transfer(){
    	var check = confirm("Bạn có thật sự muốn chuyển đơn hàng này sang đã chuyển tại nước ngoài?");
    	if (check) {
    		if (!$("#transferId") || $("#transferId").val() == "") {
    			alert("Vui lòng ghi chú vận đơn.");
    			return;
    		}
    		window.location.href = "ghi-chu-van-don?id="+$("#orderId").val()+"&transferID=" + $("#transferId").val();
    	}
    }
    
    function saveRealPrice(element, id) {
    	$.ajax({
			type : "GET",
			url : "mua-mat-hang?id=" + id +"&value=" + $(element).val(),
			success : function(response) {
				if (response.status == 0) {
					alert(response.message);
					return;
				}
				$("#total_real_price").html(0);
				$(".real_price").each(function (){
			    	if (!$(this).val() || $(this).val() == "") {
			    		return;
			    	}
			    	$("#total_real_price").html(parseFloat($("#total_real_price").html()) + parseFloat($(this).val()));
			    })
			},
			error : function() {
			}

		});
    }
    
    $(".openNote").on("click", function () {
        var action = $(this).data('id');
        $("#action").val( action );
        $('#addNoteModal').modal('show');
   });
    
    function transferToVN () {
    	var check = confirm("Bạn có thật sự muốn phê duyệt đơn hàng này?");
    	if (check) {
    		window.location.href = "chuyen-don-hang-vn?id="+$("#orderId").val(); 
    	}
    }
    
    function validateForm() {
    	var BreakException = {};
    	var errorMessage = "";
    	try {
	    	$("#order_form table tr").each(function(){
	    		var name = $(this).find(".txtName").val();
	    		var link = $(this).find(".txtLink").val();
	    		var quantity = $(this).find(".txtQuantity").val();
	    		var cost = $(this).find(".txtCost").val();
	    		if ((!name || name == "") && (!link || link == "")
	    				&& (!quantity || quantity == "") && (!cost || cost == "")) {
	    			return;
	    		}
	    		
	    		if (!name || name == "") {
	    			errorMessage = "Tên mặt hàng không được để trống";
	    			$(this).find(".txtName").focus();
	    			throw BreakException;
	    		}
	    		
	    		if (!link || link == "") {
	    			errorMessage = "Đường dẫn đến mặt hàng không được để trống";
	    			$(this).find(".txtLink").focus();
	    			throw BreakException;
	    		}
	    		
	    		if (!quantity || quantity == "") {
	    			errorMessage = "Số lượng không được để trống";
	    			$(this).find(".txtQuantity").focus();
	    			throw BreakException;
	    		}
	    		
	    		if (parseInt(quantity) < 1) {
	    			errorMessage = "Số lượng phải lớn hơn 0";
	    			$(this).find(".txtQuantity").focus();
	    			throw BreakException;
	    		}
	    		
	    		if (!cost || cost == "") {
	    			errorMessage = "Đơn giá không được để trống";
	    			$(this).find(".txtCost").focus();
	    			throw BreakException;
	    		}
	    		
	    		if (parseFloat(cost) <= 0) {
	    			errorMessage = "Đơn giá phải lớn hơn 0";
	    			$(this).find(".txtCost").focus();
	    			throw BreakException;
	    		}
	    	})
    	} catch (e) {
    		if (e !== BreakException) throw e;
    	}
    	
    	if (errorMessage != "") {
    		alert (errorMessage);
        	//return false;
    	}
    	
    	if (!$("#fullName").val() || $("#fullName").val() == "") {
    		alert("Vui lòng điền họ và tên của bạn");
    		return false;
    	}
    	
    	if (!$("#email").val() || $("#email").val() == "") {
    		alert("Vui lòng điền địa chỉ email");
    		return false;
    	}
    	
    	if (!$("#phone").val() || $("#phone").val() == "") {
    		alert("Vui lòng điền số điện thoại");
    		return false;
    	}
    	
    	if (!$("#address").val() || $("#address").val() == "") {
    		alert("Vui lòng điền địa chỉ nhận hàng");
    		return false;
    	}
    		
    	return true;
    	
    }
    </script>
    
    
</body>
</html>