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
<title>倉庫一覧</title>
<META http-equiv="Pragma" content="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<meta http-equiv="cache-control" content="no-cache" />
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<link href="<c:url value="/resources/css/icheck/green.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dataTables.bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap-select.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/custom.css" />" rel="stylesheet">
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
	<div class="rightContent" align="center">
		<div class="header_bar">
			<jsp:include page="/WEB-INF/pages/common/header.jsp" />
		</div>
		<div id="wrapper">
			<div class="body_container">
				<div class="container body">
					<div class="main_container">
						<div class="">
							<form id="importedItems" onsubmit="return form_validate();"
								class="div-center form-horizontal form-label-left" action="items"
								method="POST" enctype="multipart/form-data">
								<div class="row">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<sec:authorize access="hasRole('ROLE_S')">
												<div class="x_panel sokoCdContainer" id="sokoCdContainer">
													<div class="form-group">
														<label class="control-label col-xs-6 left-group" style="padding-right: 0px"
															for="first-name">倉庫</label>
														<div class="col-xs-2">
															<select class="selectpicker" name="sokoCd" id="sokoCd">
																<option value="">&nbsp;</option>
																<c:forEach var="item" items="${sokoCds}">
																	<option value="${item.getSokoCd()}"
																	<c:if test="${searchCondition.sokoCd == fn:trim(item.getSokoCd())}">
																		selected="selected"
																	</c:if>
																	>${item.getSokoName()}</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>
										</sec:authorize>	
										<div class="x_panel input-panel">
											<div class="x_content">
												<input type="hidden" name="offset" value="0" id="hid_offset">
												<div class="row">
													<div class="form-group">
														<label class="control-label col-xs-1 left-group"
															for="first-name">送状番号 </label>
														<div class="col-xs-2">
															<input type="text" class="form-control"
																style="ime-mode: disabled" name="sojoNo" maxlength="9"
																id="sojoNo" value="${searchCondition.getSojoNo()}">
														</div>
														<label class="control-label col-xs-1 left-group"
															for="first-name">製造番号 </label>
														<div class="col-xs-2">
															<input type="text" class="form-control"
																style="ime-mode: disabled" name="szNo" maxlength="15"
																id="szNo" value="${searchCondition.getSzNo()}">
														</div>
														<label class="control-label col-xs-1" for="first-name">長さ
														</label>
														<div class="col-xs-2">
															<input type="text" class="form-control"
																style="ime-mode: disabled" name="nagasa" maxlength="5"
																id="nagasa" value="${searchCondition.getNagasa()}">
														</div>
													</div>

													<div class="form-group">
														<label class="control-label col-xs-1" for="first-name">部材番号
														</label>
														<div class="col-xs-2">
															<select name="buzaiKigo" class="selectpicker" title="">
																<option value="">&nbsp;</option>
																<c:forEach var="item" items="${buzaiKigos}">
																	<option value="${item.getBuzaiKigo()}"
																	<c:if test="${searchCondition.buzaiKigo == fn:trim(item.getBuzaiKigo())}">
																		 selected="selected"
																	</c:if>
																	>${item.getBuzaiKigo()}</option>
																</c:forEach>
															</select>
														</div>
														<label class="control-label col-xs-1" for="first-name">材質
														</label>
														<div class="col-xs-2">
															<select name="zaisitu" class="selectpicker" title="">
																<option value="">&nbsp;</option>
																<c:forEach var="item" items="${zaisitus}">
																	<option value="${item.getZaisitu()}"
																	<c:if test="${searchCondition.zaisitu == fn:trim(item.getZaisitu())}">
																		 selected="selected"
																	</c:if>
																	>${item.getZaisitu()}</option>
																</c:forEach>
															</select>
														</div>
														<label class="control-label col-xs-1" for="first-name">加工区分
														</label>
														<div class="col-xs-2">
															<select name="kakoMongon" class="selectpicker" title="">
																<option value="">&nbsp;</option>
																<c:forEach var="item" items="${kakoMongons}">
																	<option value="${item.getKakoMongon()}"
																	<c:if test="${searchCondition.kakoMongon == fn:trim(item.getKakoMongon())}">
																		 selected="selected"
																	</c:if>
																	>${item.getKakoMongon()}</option>
																</c:forEach>
															</select>
														</div>
													</div>

													<div class="form-group">
														
														<label class="control-label col-xs-1" for="first-name">断面寸ホ法
														</label>
														<div class="col-xs-2">
															<input type="text" class="form-control"
																style="ime-mode: disabled" name="dansunMongon" maxlength="20"
																id="dansunMongon" value="${searchCondition.getDansunMongon()}">
														</div>
													
														<label class="control-label col-xs-1" for="first-name">入庫日</label>
														<div class="col-xs-2">
															<input name="dateStart" type="text"
																class="form-control has-feedback-left testDate"
																style="ime-mode: disabled" id="dateStart"
																placeholder="YYYY/MM/DD"
																value="${searchCondition.getDateStart()}"> <span
																class="fa fa-calendar form-control-feedback left"
																aria-hidden="true"></span>
														</div>
														<label class="control-label col-xs-1" for="first-name"
															style="text-align: center">~</label>
														<div class="col-xs-2">
															<input name="dateEnd" type="text"
																class="form-control has-feedback-left testDate"
																style="ime-mode: disabled" id="dateEnd"
																placeholder="YYYY/MM/DD"
																value="${searchCondition.getDateEnd()}"> <span
																class="fa fa-calendar form-control-feedback left"
																aria-hidden="true"></span>
														</div>
													</div>
													<div class="form-group" style="margin-bottom: 0; display: inline;">
														<div class="col-xs-5" style="text-align: left">
															<a class="btn left" href=""
																style="margin-left: 5px; text-decoration: underline !important;" onclick="exportFile(event)">
																<i class="fa fa-download" aria-hidden="true" ></i> QJ1をエクスポート
															</a>
															<div style="padding-top: 4px; float: left; margin-left: 20px;">
																<chkbox:chbox checked="${ includeExportedItems}" onchange="includeExported(this)" id="includeExportedItems"/>
																<label for="includeExportedItems" style="font-weight: normal">エクスポートした製品</label>
															</div>
														</div>
														<div class="col-xs-2">
														<button type="submit" name="shareCard"
															class="btn btn-primary left"
															style="color: white; margin-left: 5px; width: 80px" onclick="">
															<i class="fa fa-search" aria-hidden="true"></i> 検索
														</button>	
														</div>
													</div>

												</div>

											</div>

										</div>

										<div class="x_panel table-panel">
											<div class="x_content">

												<div class="row">

													<div class="col-sm-12">

															<table id="tableList"
																class="listBusCard table">
																<thead>
																	<tr class="headings" role="row">
																		<th>
																			受入<br/>指示日
																		</th>
																		<th>入庫日</th>
																		<th>物管０９分類</th>
																		<th>
																			部材記号
																		</th>
																		<th>材質</th>
																		<th><div style="width:90px">加工区分</div></th>
																		<th>
																			断面寸法文言
																		</th>
																		<th>長さ</th>
																		<th>員数</th>
																		<th>送状番号</th>
																		<th>
																			製造番号
																		</th>
																	</tr>
																</thead>
																<tbody>
																<% String class_ex = "even"; %>
																<c:forEach var="item" items="${importItems}">
																<% if (class_ex == "even") 
																	class_ex = "odd";
																else 
																	class_ex = "even";
																%>
																	<tr class="pointer <%=class_ex %>" style="height: 30px;"
																		role="row">
																		<td><div style="width:80px">
																		<fmt:formatDate value="${item.sagyoDate}" pattern="yyyy/MM/dd" />
																		</div></td>
																		<td><div style="width:80px">
																		<fmt:formatDate value="${item.nykoDate}" pattern="yyyy/MM/dd" />
																		</div></td>															
																		<td><div style="width:112px">${item.bkn09}</div></td>
																		<td><div style="width:100px">${item.buzaiKigo}</div></td>
																		<td><div style="width:100px">${item.zaisitu}</div></td>
																		<td><div>${item.kakoMongon}</div></td>
																		<td><div style="width:112px">${item.dansunMongon}</div></td>
																		<td class="tdNumber"><div style="width:60px">${item.nagasa}</div></td>
																		<td class="tdNumber"><div style="width:60px">${item.zaikoInzu}</div></td>
																		<td><div style="width:80px">${item.sojoNo}</div></td>
																		<td><div style="width:112px">${item.id.szNo}</div></td>																												
																	</tr>
																</c:forEach>
																</tbody>
															</table>

													</div>
												</div>
												<div class="div-bottom">
<%-- 													<tag:paginate offset="${offset}" count="${count}" --%>
<%-- 														uri="${pageContext.request.contextPath}/import/items" next="&raquo;" previous="&laquo;" /> --%>
													<tag:paginate offset="${offset}" count="${count}" steps="${maxResult}"
 														uri="${pageContext.request.contextPath}/imported/items" next="&raquo;" previous="&laquo;" />
												</div>
											</div>
										</div>


									</div>
								</div>
							</form>
						</div>
					</div>
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
    <script src="<c:url value="/resources/js/custom.js"/>"></script>
    <script>

	function exportFile(e) {
		if (!checkSokoCdSelected(e)) {
			return;
		} 
		e.preventDefault();
    	$.dialogbox({
			type : 'msg',
			title : '確認',
			content : "QJ1をエクスポートします、宜しいでしょうか？",
			closeBtn : true,
			btn : [ 'OK', 'キャンセル' ],
			call : [ function() {
				window.location.href = "download?fromDate=" + $("#dateStart").val() + "&toDate=" + $("#dateEnd").val()+"&includeExportedItems=" + $("#includeExportedItems").val();
				$.dialogbox.close();
			}, function() {
				$.dialogbox.close();
			} ]

		});
	}
	
	function form_validate()
    {
    	var dateStart = $("#dateStart").val();
    	if ((dateStart != "") && (!isValidDate(dateStart)))
   		{
    		alert("開始日が正しません！");
    		$("#dateStart").focus();
   			return false;
   		}
    	
    	var dateEnd = $("#dateEnd").val();
    	if ((dateEnd != "") && (!isValidDate(dateEnd)))
   		{
    		alert("終了日が正しません！");
    		$("#dateEnd").focus();
   			return false;
   		}
    }
	
	function getMyTableBodyHeight()
    {
		var viewSize = getBrowserViewSize();
        var height = viewSize.height;
        height -= 188; // height of search input area
        height -= 98;  // height of header
        height -= 66;  // height of table header
        height -= 120;  // height of bottom and pagination
        if ($("#sokoCdContainer").outerHeight(true)) {
        	height -= ($("#sokoCdContainer").outerHeight(true) + 10);
        }
        return height;
    }
    
    $(window).resize(function () {
    	var height = getMyTableBodyHeight();
    	if (height < 36) {
        	height = 36;	
        }
        $(".dataTables_scrollBody").height(height + "px");
    });

    $(document).ready(function(){
    	var height = getMyTableBodyHeight();
         
		//init datatables
         var table = $('#tableList').dataTable({
     		destroy: true,
 	   		"scrollY": height + "px",
 	   		"aLengthMenu" : [
 	   			[25, 50, 100, 200, -1],
 	   			[25, 50, 100, 200, "All"]],
 	   		"iDisplayLength" : 100,
 	   		"order": []
 	   		//"order": [[ sort, "desc" ]]
 	   	});
        
        makeDatePickerJP();
        
        $("#dateStart").datepicker({	
        	dateFormat: 'yy/mm/dd',
        	onSelect: function (selected) {
                var dt = new Date(selected);
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
          
        //unbind sort event, prevent sorting when header is clicked
        //$('#tableList_wrapper th').unbind('click.DT');

        //create your own click handler for the header
       /*  $('#tableList_wrapper th').click(function(e) {
        	location.href='${pageContext.request.contextPath}/import/items?sort=1';
            //alert('Header '+$(this).attr('id')+' clicked');
            //here you can trigger a custom event
        }); */

        //if you afterwards want to restablish sorting triggered by a click event 
        //here header "username" from example above
        //table.fnSortListener(document.getElementById('username'), 1);
    });
    
    function includeExported(item) {
		if (item.checked) {
			$.ajax({
				type : "GET",
				url : "includeExportedItems",
				success : function() {
				},
				error : function() {
				}
	
			});
		} else {
			$.ajax({
				type : "GET",
				url : "excludeExportedItems",
				success : function() {
				},
				error : function() {
				}
	
			});
		}
    }
    
    $("#sokoCd").on("change", function(){
    	$("#importedItems").submit();
    });
    </script>
</body>
</html>