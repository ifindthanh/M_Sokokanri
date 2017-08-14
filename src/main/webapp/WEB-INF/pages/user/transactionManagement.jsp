<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="transaction" uri="/WEB-INF/taglibs/transactionTypeTaglib.tld" %>
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
		<form>
			<div class="col-sm-12 row">
				<div class="col-xs-2"><h2>${user.fullname}</h2></div>
			</div>
			<div class="col-sm-12">
				<table id="tableList" class="listBusCard table" style = "width: 1000px">
					<thead>
						<tr class="headings" role="row">
							<th style="width: 20px">No.</th>
							<th>Mã giao dịch</th>
							<th>Loại giao dịch</th>
							<th>Số tiền</th>
							<th>Ngày giao dịch</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${transactions}" varStatus="status">
							<tr>
								<td class="fixed"></td>
								<td class="fixed">
									${item.getFormattedId()}
								</td>
								<td>
									<transaction:type type="${item.transactionType}" disable="true"/> 
								</td>
								<td>
									${item.amount}
								</td>
								<td>
									<formatter:date date="${item.createDate}"/>
								</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
	
			</div>
		</form>
		<div class="col-sm-12 row footer" >
			<div class="col-xs-2"><button class="btn btn-default" onclick="window.history.back();">Quay lại</button></div>
		</div>
		<div class="col-sm-12 row"></div>
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
	<script src="<c:url value="/resources/js/common.js"/>"></script>
    <script>
    var table;
    $(document).ready(function(){
		//init datatables
          table = $('#tableList').DataTable({
     		destroy: true,
     		"paging":   false,
     		"searching":   false,
 	   		"order": [[ 1, 'asc' ]],
 	   		"iDisplayLength" : 100,
	 	   	"ordering": false,
	        "info":     false
 	   	});
         table.column(0, {}).nodes().each( function (cell, i) {
 			cell.innerHTML = i+1;
 		} );
         
         $("#dateStart").datepicker({	
         	dateFormat: 'yy/mm/dd',
         	onSelect: function (selected) {
                 var dt = new Date(selected);
                 console.log(dt);
                 $("#dateEnd").datepicker("option", "minDate", dt);
             }
         });
         $("#dateEnd").datepicker({
         	dateFormat: 'yy/mm/dd',
         	onSelect: function (selected) {
                 var dt = new Date(selected);
                 $("#dateStart").datepicker("option", "maxDate", dt);
             }
         });
         // Change events
         $("#dateStart").change(function() {
         	var dateStart = new Date($("#dateStart").val());
         	console.log(dateStart);
         	if ((dateStart == "") || (!isValidDate(dateStart)))
        		{
         		$("#dateEnd").datepicker("option", "minDate", dateStart);
        		}
         });
         $("#dateEnd").change(function() {
         	var dateEnd = new Date($("#dateEnd").val());
         	if ((dateEnd == "") || (!isValidDate(dateEnd)))
        		{	
         		$("#dateStart").datepicker("option", "maxDate", dateEnd);
        		}
         });
    });
    
    
    </script>
</body>
</html>