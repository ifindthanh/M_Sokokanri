<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="contact" uri="/WEB-INF/taglibs/contactColumnTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="chkbox" uri="/WEB-INF/taglibs/commonCheckbox.tld" %>
<%@ taglib prefix="order" uri="/WEB-INF/taglibs/orderStatusTaglib.tld"%>

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
<link href="<c:url value="/resources/css/jquery-ui.css" />" rel="stylesheet">
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
				<div class="col-xs-12 row">
					<label class="col-xs-2 right_align top_margin_5" >Trạng thái đơn hàng: </label>
					<div class="col-xs-4">
						<order:search status="${searchCondition.status }"/>
					</div>
					<div>
						<button type="submit" class="btn btn-primary">
							<i class="fa fa-search" aria-hidden="true"> Tìm kiếm</i>
						</button>
					</div>
				</div>
				<div class="col-xs-12 row">
					<label class="col-xs-2 right_align top_margin_5" >Nhãn hàng: </label>
					<div class="col-xs-4">
						<select name="brands" multiple="multiple" class="selectpicker form-control inputstl" onchange="search()">
							<c:forEach var="brand" items="${allBrands}" varStatus="status">
								<option value="${brand }" <c:if test="${searchCondition.brands.contains(brand)}">selected</c:if>>${brand }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<%-- <div class="col-xs-12 row">
					<label class="col-xs-2 right_align top_margin_5" >Từ ngày: </label>
					<div class="col-xs-2">
						<input name="fromDate" type="text"
							class="form-control has-feedback-left testDate"
							style="ime-mode: disabled" id="dateStart"
							placeholder="YYYY/MM/DD"
							value="${searchCondition.fromDate}"> <span
							class="fa fa-calendar form-control-feedback left"
							aria-hidden="true"></span>
					</div>
					<label class="col-xs-1 top_margin_5" >Đến ngày: </label>
					<div class="col-xs-2">
						<input name="toDate" type="text"
							class="form-control has-feedback-left testDate"
							style="ime-mode: disabled" id="dateEnd"
							placeholder="YYYY/MM/DD"
							value="${searchCondition.toDate}"> <span
							class="fa fa-calendar form-control-feedback left"
							aria-hidden="true"></span>
					</div>
				</div> --%>
			</div>
			<div class="col-sm-12">
				<table id="tableList" class="listBusCard table">
					<thead>
						<tr class="headings" role="row">
							<th style="width: 20px">No.</th>
							<th>Mã đơn hàng</th>
							<th>Tên khách hàng</th>
							<th>Trạng thái</th>
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
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${items}" varStatus="status">
							<tr>
								<td class="fixed"></td>
								<td class="fixed">
									${item.formattedId}
								</td>
								<td>
									${item.user.fullname}
								</td>
								<td>
									<order:status status="${item.status }"/>
								</td>
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
									<div class="lblComputeCost">${item.computeCost }</div> 
								</td>
								<td>
									<div class="lblRealQuantity">${item.realQuantity }</div> 
								</td>
								<td>
									<div class="lblComputePrice">${item.computePrice }</div> <input
									type="text" value="${item.computePrice}"
									class="form-control hiddenAction txtComputePrice"
									disabled="disabled" />
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
								</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
	
			</div>
			<div class="div-bottom">
				<tag:paginate offset="${offset}" count="${count}"
					steps="${maxResult}"
					uri="${pageContext.request.contextPath}/donhang/tat-ca"
					next="&raquo;" previous="&laquo;" />
			</div>
		</form>
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