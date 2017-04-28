<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="contact" uri="/WEB-INF/taglibs/contactColumnTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dataTables.bootstrap.min.css" />"
	rel="stylesheet">
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
							<form id="exportingItems"
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
																style="ime-mode: disabled" name="sojoNo" maxlength="30"
																id="sojoNo" value="${searchCondition.getSojoNo()}">
														</div>
													
														<label class="control-label col-xs-1 left-group"
															for="first-name">出荷予定日 </label>
														<div class="col-xs-2">
															<input name="sukkaYoteibi" type="text" 
																class="form-control has-feedback-left testDate"
																style="ime-mode: disabled" id="sukkaYoteibi"
																placeholder="YYYY/MM/DD"
																value="${searchCondition.getSukkaYoteibi()}"> <span
																class="fa fa-calendar form-control-feedback left"
																aria-hidden="true"></span>
														</div>
														
														<label class="control-label col-xs-1 left-group"
															for="first-name">需要家／向先 </label>
														<div class="col-xs-2">
															<input type="text" class="form-control"
																name="jyuoka" maxlength="30"
																id="jyuoka" value="${searchCondition.getJyuoka()}">
														</div>
													</div>
													<div class="form-group pull-center"
														style="margin-bottom: 0; display: inline;">
														<div class="col-xs-5" >
															<a class="btn left" href="upload" onclick="checkSokoCdSelected(event)"
																style="margin-left: 5px; width: 80px; text-decoration: underline !important;">
																<i class="fa fa-upload" aria-hidden="true" ></i> QS2をインポート
															</a>
														</div>
														<button type="submit" name="shareCard"
															class="btn btn-primary left"
															style="color: white; margin-left: 5px; width: 80px"
															onclick="">
															<i class="fa fa-search" aria-hidden="true"></i> 検索
														</button>
													</div>

												</div>

											</div>

										</div>

										<div class="x_panel table-panel">
											<div class="x_content">

												<div class="row">

													<div class="col-sm-12">

														<table id="tableList" class="listBusCard table">
															<thead>
																<tr class="headings" role="row">
																	<th>送状番号</th>
																	<th>出荷予定日</th>
																	<th>需要家／向先</th>
																	<th>物管０９分類</th>
																	<th>品種（部材記号）</th>
																	<th>材質</th>
																	<th>断面寸法</th>
																	<th>長さ(mm)</th>
																	<th>連番</th>
																	<th>予備</th>
																</tr>
															</thead>
															<tbody>
																<%
																	String class_ex = "even";
																%>
																<c:forEach var="item" items="${exportItems}">
																	<%
																		if (class_ex == "even")
																				class_ex = "odd";
																			else
																				class_ex = "even";
																	%>
																	<tr class="pointer <%=class_ex%>" style="height: 30px;"
																		role="row">
																		<td><div style="width:80px">
																			${item.getShukkaHeadBean().getId().getSojoNo()}
																		</div></td>
																		<td><div style="width:80px">
																			<fmt:formatDate value="${item.getShukkaHeadBean().getSukkaYoteibi()}" pattern="yyyy/MM/dd" />
																		</div></td>
																		<td colspan="8" style="text-align:left">${item.getShukkaHeadBean().getJyuoka()}</td>
																		<td style="display: none"></td>
																		<td style="display: none"></td>
																		<td style="display: none"></td>
																		<td style="display: none"></td>
																		<td style="display: none"></td>
																		<td style="display: none"></td>
																		<td style="display: none"></td>
																	</tr>
																	<%
																		int number = 1;
																	%>
																	<c:forEach var="itemBody"
																		items="${item.getShukkaBodyBeans()}">
																		<tr class="pointer <%=class_ex%>"
																			style="height: 30px;" role="row">
																			<td></td>
																			<td></td>
																			<td class="tdNumber"><%=number++%></td>
																			<td>${itemBody.bkn09}</td>
																			<td>${itemBody.buzaiKigo}</td>
																			<td>${itemBody.zaisitu}</td>
																			<td>${itemBody.dansunMongon}</td>
																			<td class="tdNumber">${itemBody.nagasa}</td>
																			<td class="tdNumber">${itemBody.renban}</td>
																			<td>${itemBody.yobi}</td>
																		</tr>
																	</c:forEach>
																</c:forEach>
															</tbody>
														</table>

													</div>
												</div>
												<div class="div-bottom">
													<%-- 													<tag:paginate offset="${offset}" count="${count}" --%>
													<%-- 														uri="${pageContext.request.contextPath}/import/items" next="&raquo;" previous="&laquo;" /> --%>
													<tag:paginate offset="${offset}" count="${count}"
														steps="${maxResult}"
														uri="${pageContext.request.contextPath}/export/items"
														next="&raquo;" previous="&laquo;" />
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
	
	function getMyTableBodyHeight()
    {
		var viewSize = getBrowserViewSize();
        var height = viewSize.height;
        height -= 100; // height of search input area
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
    
	$(document).ready(function() {
	 	var height = getMyTableBodyHeight();
		
		//init datatables
		var table = $('#tableList').dataTable({
			destroy : true,
			"scrollY" : height + "px",
			"aLengthMenu" : [ [ 25, 50, 100, 200, -1 ],
					[ 25, 50, 100, 200, "All" ] ],
			"iDisplayLength" : 100,
			"order" : [],
			"bSort": false
		//"order": [[ sort, "desc" ]]
		});

		makeDatePickerJP();
		
		$("#sukkaYoteibi").datepicker({
			dateFormat : 'yy/mm/dd'
		});
	});
	
	$("#sokoCd").on("change", function(){
    	$("#exportingItems").submit();
    });
	</script>
</body>
</html>