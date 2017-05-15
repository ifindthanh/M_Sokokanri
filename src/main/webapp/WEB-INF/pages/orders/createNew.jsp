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
			<form:form method="POST" action="tao-moi" modelAttribute="order">
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
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${order.items}" varStatus="status">
							<tr>
								<form:input type="hidden" path="items[${status.index}].id" value="${item.id }"/>
								<td></td>
								<td>
									<form:input class="form-control" path="items[${status.index}].name" value="${item.name }" />
								</td>
								<td><form:input class="form-control" path="items[${status.index}].brand" type="text" /></td>
								<td><form:input class="form-control" path="items[${status.index}].link" type="text" /></td>
								<td><form:textarea class="description form-control" path="items[${status.index}].description" /></td>
								<td><form:input class="txtCost form-control" path="items[${status.index}].cost" type="text" onchange="computeMoney(this)" /></td>
								<td><form:input class="txtQuantity form-control" path="items[${status.index}].quantity" type="text" onchange="computeMoney(this)"/></td>
								<td><form:input class="txtTotal form-control" path="items[${status.index}].total" type="text" readonly="true"/></td>
								<td><a class="myAction" href="#"><i class="fa fa-save"
										aria-hidden="true"></i></a>
										/
									<a class="deleteItem myAction"><i class="fa fa-trash-o"
										aria-hidden="true"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<button id="addRow" type="button" class="btn btn-default">
					<i class="fa fa-plus" aria-hidden="true" ></i> Thêm sản phẩm
				</button>
				
				<hr/>
				
				<div class="col-xs-12">
					<h4><label>Thông tin khách hàng</label></h4>
					<div class = "row">
						<label class="col-xs-2">Họ và tên: </label>
						<div class="col-xs-4"><form:input path="fullName" type="text" class="form-control"/></div>
					</div>
					<div class = "row">
						<label class="col-xs-2">Email: </label>
						<div class="col-xs-4"><form:input path="email" type="text"  class="form-control"/></div>
					</div>
					<div class = "row">
						<label class="col-xs-2">Số điện thoại: </label>
						<div class="col-xs-4"><form:input path="phone" type="text"  class="form-control"/></div>
					</div>
					<div class = "row">
						<label class="col-xs-2">Địa chỉ: </label>
						<div class="col-xs-4"><form:input path="address" type="text"  class="form-control"/></div>
					</div>
					<div class = "row">
						<label class="col-xs-2">Thành phố: </label>
						<div class="col-xs-4"><form:input path="city" type="text"  class="form-control"/></div>
					</div>
					<div class = "row">
						<label class="col-xs-2">Ghi chú: </label>
						<div class="col-xs-4"><form:textarea path="note" class="form-control description" rows="4"/></div>
					</div>
				</div>
				<br/>
				<input type="submit" class="btn btn-primary" value="Tạo đơn hàng"/>
				<input type="hidden" value="${order.items.size()}" id="item_size"/>
				<form:input type="hidden" path = "userId" value="${order.userId}"/>
			</form:form>

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
       	$(".deleteItem").click(function (){
       		var agreeToDelete = confirm("Bạn có thực sự muốn xóa mặt hàng đã chọn?");
       		if (!agreeToDelete) {
       			return;
       		}
       		table
            .row( $(this).parents('tr') )
            .remove()
            .draw();
       		table.column(0, {}).nodes().each( function (cell, i) {
    			cell.innerHTML = i+1;
    		}).draw();
       	});
       	
       	
       	
       	$('#addRow').on( 'click', function () {
       		var index = parseInt($("#item_size").val());   
       		
    		$("#item_size").val(index + 1);
            table.row.add( [
            	"",
            	"<input class=\"form-control\" type=\"text\" name=\"items[" + index + "].name\" value=\"\" />",
        		"<input class=\"form-control\" type=\"text\" name=\"items[" + index + "].brand\" />",
        		"<input class=\"form-control\" type=\"text\" name=\"items[" + index + "].link\" />",
        		"<textarea class=\"description form-control\" name=\"items[" + index + "].description\"> </textarea>",
        		"<input class=\"txtCost form-control\" type=\"text\" name=\"items[" + index + "].cost\" onchange=\"computeMoney(this)\" />",
        		"<input class=\"txtQuantity form-control\" type=\"text\" name=\"items[" + index + "].quantity\" onchange=\"computeMoney(this)\" />",
        		"<input class=\"txtTotal form-control\" type=\"text\" readonly=\"true\" name=\"items[" + index + "].total\"/>",
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
    }
    
    </script>
    
    
</body>
</html>