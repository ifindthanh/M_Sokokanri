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
			<c:if test="${not empty category.note}">
				<p class="error">Lưu ý đơn hàng: ${category.note }</p>
			</c:if>
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
							<th>Nhà cung cấp</th>
							<th>Mô tả thêm</th>
							<th>Đơn giá</th>
							<th>Số lượng</th>
							<th>Thành tiền</th>
							<c:if test="${read_only ne true}"><th></th></c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${category.items}" varStatus="status">
							<tr>
								<form:input type="hidden" path="items[${status.index}].id" value="${item.id }" readonly="${read_only }"/>
								<td></td>
								<td>
									<select id="tree${status.index}" name="items[${status.index}].tree.id" class="selectpicker form-control inputstl checkTree" title="Tên cây">
										<c:forEach var="tree" items="${allTrees}" varStatus="status2">
											<option value="${tree.id }" <c:if test="${tree.id.equals(item.tree.id)}">selected</c:if> >${tree.name }</option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select id="provider${status.index}" name="items[${status.index}].provider.id" class="selectpicker form-control inputstl checkProvider" title="Nhà cung cấp">
										<c:forEach var="provider" items="${allProviders}" varStatus="status3">
											<option value="${provider.id }" <c:if test="${provider.id.equals(item.provider.id)}">selected</c:if> >${provider.name }</option>
										</c:forEach>
									</select>
								</td>
								<td><form:textarea class="description form-control" path="items[${status.index}].description" readonly="${read_only }"/></td>
								<td><form:input class="txtCost form-control" path="items[${status.index}].price" type="text" onchange="computeMoney(this)" readonly="${read_only }"/></td>
								<td><form:input class="txtQuantity form-control" path="items[${status.index}].quantity" type="number" min ="1" max="999" onchange="computeMoney(this)" readonly="${read_only }"/></td>
								<td><form:input class="txtTotal form-control" path="items[${status.index}].total" type="text" readonly="true"/></td>

								<c:if test="${read_only ne true}">
									<td>
										<a class="deleteItem myAction" item = "${item.id }"><i class="fa fa-trash-o"
											aria-hidden="true"></i></a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div style="position: relative">
					<div style="min-height: 30px">
						<c:if test="${read_only ne true}">
							<button id="addRow" type="button" class="btn btn-default">
								<i class="fa fa-plus" aria-hidden="true" ></i> Thêm sản phẩm
							</button>
						</c:if>
					</div>
					<div style="float:right; position: absolute; right: 30px; top: 0px;">
						<label>Tổng tiền : <span id="total_price">${category.getOrderPrice() }</span></label>
					</div>
					
				</div>

				<br/>
				<c:if test="${read_only ne true}">
					<input type="submit" class="btn btn-primary" value="Lưu thông tin" onclick="return validateForm()"/>
					<a href="tat-ca" class="btn btn-default">Xem tất cả đơn hàng</a>
				</c:if>
				<input type="hidden" value="${category.items.size()}" id="item_size"/>
				<form:input type="hidden" path = "userId" value="${category.user.id}"/>
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
					<button type="button" class="btn btn-primary" onclick="addNote()">Ghi chú</button>
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
    	$("#total_real_price").html(parseFloat(parseFloat($("#total_real_price").html()) + parseFloat($(this).val())).toFixed(0));
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
			   var v1 = $("#tree1");
       		console.log(v1.html());
			   var v2 = document.getElementById("tree1");
			console.log(v2.innerHTML);
    		$("#item_size").val(index + 1);
            table.row.add( [
            	"",
				"<select name='items["+index+"].tree.id'>" + $("#tree1").html() + "</select>",
				"<select name='items["+index+"].provider.id'>" + $("#provider1").html() + "</select>",
        		"<textarea class=\"description form-control\" name=\"items[" + index + "].description\"> </textarea>",
        		"<input class=\"txtCost form-control\" type=\"text\" name=\"items[" + index + "].price\" onchange=\"computeMoney(this)\" />",
        		"<input class=\"txtQuantity form-control\" type=\"number\" min =\"1\" max=\"999\" name=\"items[" + index + "].quantity\" onchange=\"computeMoney(this)\" />",
        		"<input class=\"txtTotal form-control\" type=\"text\" readonly=\"true\" name=\"items[" + index + "].total\"/>",
        		"<a class= \"deleteItem myAction\" onclick=\"deleteRow(this)\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\" ></i></a>"
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
    	$("#total_price").html(total_price.toFixed(0));
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
	    		if ((!quantity || quantity == "") && (!cost || cost == "")) {
	    			return;
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

	    	})
    	} catch (e) {
    		if (e !== BreakException) throw e;
    	}
    	
    	if (errorMessage != "") {
    		alert (errorMessage);
        	//return false;
    	}
    		
    	return true;
    	
    }
    </script>
    
    
</body>
</html>