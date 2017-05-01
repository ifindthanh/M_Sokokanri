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
						<c:forEach var="item" items="${order.allItems}" varStatus="status">
							<tr>
								<form:input type="hidden" path="allItems[${status.index}].id" value="${item.id }"/>
								<td></td>
								<td>
									<form:input class="form-control" path="allItems[${status.index}].name" value="${item.name }" />
								</td>
								<td><form:input class="form-control" path="allItems[${status.index}].brand" type="text" /></td>
								<td><form:input class="form-control" path="allItems[${status.index}].link" type="text" /></td>
								<td><form:textarea class="description form-control" path="allItems[${status.index}].description" /></td>
								<td></td>
								<td></td>
								<td><input class="form-control" type="text" disabled="disabled" /></td>
								<td><a href="#"><i class="fa fa-save"
										aria-hidden="true"></i>/<i class="deleteItem fa fa-trash-o"
										aria-hidden="true"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<button id="addRow" type="button" class="btn btn-default">
					<i class="fa fa-plus" aria-hidden="true" ></i> Thêm sản phẩm
				</button>
				<input type="submit" class="btn btn-primary" value="Tạo đơn hàng"/>
				<input type="hidden" value="${order.allItems.size()}" id="item_size"/>
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
       		table.column(0, {}).nodes().each( function (cell, i) {
       			console.log(cell);
    			cell.innerHTML = i+1;
    		}).draw();
       	});
       	
       	$('#addRow').on( 'click', function () {
       		var index = parseInt($("#item_size").val());   
       		
    		$("#item_size").val(index + 1);
            table.row.add( [
            	"",
            	"<input class=\"form-control\" type=\"text\" name=\"allItems[" + index + "].name\" value=\"\" />",
        		"<input class=\"form-control\" type=\"text\" name=\"allItems[" + index + "].brand\" />",
        		"<input class=\"form-control\" type=\"text\" name=\"allItems[" + index + "].link\" />",
        		"<textarea class=\"description form-control\" name=\"allItems[" + index + "].description\"> </textarea>",
        		"",
        		"",
        		"<input class=\"description form-control\" type=\"text\" disabled=\"disabled\" name=\"allItems[" + index + "].total\"/>",
        		"<a href=\"#\"><i class=\"fa fa-save\" aria-hidden=\"true\"></i>/<i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></a>"
            ] ).draw( false );
            table.column(0, {}).nodes().each( function (cell, i) {
    			cell.innerHTML = i+1;
    		} ).draw( false );
        } );
       	
		table.column(0, {}).nodes().each( function (cell, i) {
			cell.innerHTML = i+1;
		} );
         
    });
    
    </script>
</body>
</html>