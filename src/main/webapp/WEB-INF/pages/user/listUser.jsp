<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>ユーザー一覧</title>
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<link href="<c:url value="/resources/css/icheck/green.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dataTables.bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/custom.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap-select.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/dialogbox.js" />"></script>
<script src="<c:url value="/resources/js/jquery.freezeheader.js" />"></script>

<script type="text/javascript">
	function registerAnUser(){
		window.location.href = "registerAnUser";		
	}
	function validateDelete() {
		var tableList = document.getElementById('tableList');
		var remember = tableList.getElementsByTagName('input');
		var check = false;
		for (i = 0; i < remember.length; i++) {
			if (remember[i].checked) {
				check = true;
				break;
			}
		}
		if (check) {
			var data = $("#listUser").serializeArray();
			data.push({
				name : "deleteUser",
				value : "deleteUser"
			});
			$.dialogbox({
				type : 'msg',
				title : '確認',
				content : "選されたユーザーが削除されます。宜しいですか？",
				closeBtn : true,
				btn : [ 'はい', 'いいえ' ],
				call : [ function() {
					$("#dialogbox").css("z-index", "0");
					$(".dialogboxOverlay").css("cursor", "wait");
					actionUser(data);
				}, function() {
					$.dialogbox.close();
				} ]

			});
		} else {
			$.dialogbox({
				type : 'msg',
				title : '確認',
				content : "削除したいユーザーを選択してください。",
				btn : [ 'OK' ],
				call : [ function() {
					$.dialogbox.close();
				} ]
			});
			return false;
		}
	}

	function resetUser(e) {
		$.dialogbox({
			type : 'msg',
			title : '確認',
			content : 'リセットしても宜しいですか？',
			closeBtn : true,
			btn : [ 'はい', 'いいえ' ],
			call : [ function() {
				location.href = 'resetUser/' + e;
				$.dialogbox({
					type : 'msg',
					title : '確認',
					content : 'リセットしても宜しいですか？',
					closeBtn : true,
					btn : [ 'はい', 'いいえ' ],
					call : [ function() {
						$.dialogbox.close();
						location.href = 'resetUser/' + e;
						e.preventDefault();

					}, function() {
						$.dialogbox.close();
					} ]

				});
			}, function() {
				$.dialogbox.close();
			} ]

		});

	}
	function actionUser(myData) {
		$.ajax({
			type : "POST",
			url : "list",
			data : myData,
			success : function() {
				$.dialogbox.close();
				$(".dialogboxOverlay").css("cursor", "default");
				$("#dialogbox").css("z-index", "200000000");
				window.location.href = "list";
			},
			error: function(){
				$.dialogbox.close();
				$(".dialogboxOverlay").css("cursor", "default");
				$("#dialogbox").css("z-index", "200000000");
			}

		});
	}
</script>

<script>
// $(document).ready(function () {
//     $("#tableList").freezeHeader({ 'offset': '90px' });
// })
</script>

</head>
<body>
<div class="rightContent" align="center">
	<div class="header_bar">
		<jsp:include page="/WEB-INF/pages/common/header.jsp" /></div>
	<div id="wrapper">
	<div class="body_container">
	<div class="container body">
	<div class="main_container">
		<form:form modelAttribute="userManagerForm" method="POST"
			action="list" enctype="multipart/form-data" class="form-horizontal"
			id="listUser" style="margin-bottom: 0">
			<!-- /top tiles -->
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div style="float: right;">
						<button type="button" name="registerUser" onclick="registerAnUser()"
							class="btn btn-warning pull-right"
							style="color: white; margin-right: 18px">
							<i class="fa fa-plus"></i> ユーザー登録
						</button>
					</div>
					<div class="title_right">
						<div class="col-md-3 col-sm-3 col-xs-12 form-group pull-right"></div>
						<div class="col-md-4 col-sm-4 col-xs-12 form-group pull-right top_search" style="margin-bottom: -5px;">
							<div class="input-group">
								<input id="search" type="text" name="txtSearch"
									class="form-control" placeholder="検索条件を入力する "
									value="${userBean.getTxtSearch()}"> <span
									class="input-group-btn">
									<button type="submit" name="search" class="btn btn-primary"
										style="color: white;"><i class="fa fa-search" aria-hidden="true"></i> 検索</button>
								</span>
							</div>
						</div>
					</div>
				
					<div class="x_title">
						<button type="button" name="deleteUser"
							class="btn btn-danger left" style="color: white;"
							onclick="return validateDelete();">
							<i class="fa fa-trash-o"></i> 削除
						</button>
<!-- 						<button type="button" name="registerUser" onclick="registerAnUser()" -->
<!-- 							class="btn btn-warning pull-right" style="color: white; margin-right: 0px"> -->
<!-- 							<i class="fa fa-plus"></i> ユーザー登録 -->
<!-- 						</button> -->
					</div>
					<div class="x_content">

						<div class="table-responsive">
							<table
								class="table table-striped responsive-utilities bulk_action no-footer table-bordered listUser"
								id="tableList">
								<thead>
									<tr class="headings">
										<th style="cursor: hand;" class="col-thead" data-sortable="false" id="checkAll">
											<div class="col1"><input
											type="checkbox" id="check-all" class="flat"></div></th>
										<th id = "userIdHeader" style="cursor: hand;" class="col-thead"><div class="col2">ID</div></th>
										<th id = "nameHeader" style="cursor: hand;" class="col-thead"><div class="col3">氏名</div></th>
										<th id = "contactHeader" style="cursor: hand;" class="col-thead"><div class="col4">連絡先</div></th>
										<th id = "companyHeader" style="cursor: hand;" class="col-thead"><div class="col5">会社名</div></th>
										<th id = "jobTitleHeader" style="cursor: hand;" class="col-thead"><div class="col6">職位.職責</div></th>
										<th id = "departmentHeader" style="cursor: hand;" class="col-thead"><div class="col7">部署</div></th>										
										<th id = "createDateHeader" style="cursor: hand;" class="col-thead"><div class="col8">登録日</div></th>
										<th class="no-link last col-thead" data-sortable="false"><div class="col1">&nbsp;</div></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="item" varStatus="itr">
										<tr class="even pointer">
											<td>
												<input type="checkbox"
												id="checkBox" class="flat" name="table_records"
												value="${item.getId()}">
											</td>
											<td>
												<div>
													<a href="viewUser/${item.getUserCd()}">
														<c:if
															test="${item.getStatus() == 1}">
															<p style="color: red;">${item.getUserCd()}</p>
														</c:if> <c:if test="${item.getStatus() == 2}">${item.getUserCd()}</c:if>
													</a>
												</div>
											</td>
											
											<td>
												<a href="viewUser/${item.getUserCd()}">
												    ${item.getLastName()}
													${item.getFirstName()}
												</a>
											</td>
											<td>
												<c:set var="phone" scope="session"
													value="${item.getPhone()}" /> <c:if
													test="${not empty phone}">
													<i class="fa fa-mobile user-profile-icon green"
														style="font-size: 25px; margin-right: 10px;"></i> ${phone}<br />
												</c:if> <a href="mailto:${item.getEmail()}"> <c:set
															var="email" value="${item.getEmail()}" /> <c:set
															var="lenghEmail" value="${fn:length(email)}" /> <c:if
															test="${lenghEmail > 30}">
															<c:set var="emailSub"
																value="${fn:substring(email, 0, 30)}" />
															<i class="fa fa-envelope-o user-profile-icon dark"
																style="margin-right: 3px;"></i> ${emailSub}...
															</c:if> <c:if test="${lenghEmail <= 30 && lenghEmail > 0}">
															<i class="fa fa-envelope-o user-profile-icon dark"
																style="margin-right: 3px;"></i> ${email}
															</c:if>
												</a>
											</td>
											<td>${item.getCompanyName()}</td>
											<td>${item.getTitle()}</td>
											<td>${item.getDept()}</td>
											<td><fmt:formatDate
													value="${item.getCreateDate()}" pattern="yyyy/MM/dd" /></td>
											<td>
												<div class="aligncenter">
													<div style="width:160px;">
													<a href="editUser/${item.getUserCd()}"
														class="btn btn-info btn-xs" style="width: 62px; margin-top: 5px;"><i
															class="fa fa-pencil"></i> 編集 </a>
													<a class="btn btn-warning btn-xs" style="width: 72px; margin-top: 5px;"
														onclick="return resetUser('${item.getUserCd()}');"><i
															class="fa fa-refresh"></i> リセット </a> <input type="hidden"
														value="${item.getUserCd()}" id="usersCd" />
													</div>
												</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="div-bottom">
							<tag:paginate offset="${offset }" count="${count }" steps="${maxResult }"
 								uri="${pageContext.request.contextPath}/user/list" next="&raquo;" previous="&laquo;" />
						</div>									
					</div>
				</div>
			</div>
		</form:form>
	</div>
	</div>
	</div>
	</div>
</div>
<script src="<c:url value="/resources/js/datePicker.custom.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap-select.min.js"/>"></script>
<script>
	function getMyTableBodyHeight()
	{
		var viewSize = getBrowserViewSize();
	    var height = viewSize.height;
	    height -= 22; // height of search input area
	    height -= 98;  // height of header
	    height -= 66;  // height of table header
	    height -= 120;  // height of bottom and pagination
	    
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
		var handleDataTableButtons = function() {
			if ($("#tableList-buttons").length) {
				$("#tableList-buttons").DataTable({
					dom : "Bfrtip",
					buttons : [ {
						extend : "copy",
						className : "btn-sm"
					}, {
						extend : "csv",
						className : "btn-sm"
					}, {
						extend : "excel",
						className : "btn-sm"
					}, {
						extend : "pdfHtml5",
						className : "btn-sm"
					}, {
						extend : "print",
						className : "btn-sm"
					}, ],
					responsive : true
				});
			}
		};
		
	/* 	maxRecord = function() {
	        $('#tableList').DataTable({
                "aLengthMenu": [[25, 50, 100, 200, -1],[25, 50, 100, 200, "All"]], 
                "iDisplayLength" : 50 
            });
	    }(); */
	    var height = getMyTableBodyHeight();
	    var table = $('#tableList').dataTable(
				{
					destroy : true,
					"scrollY" : height + "px",
					"aLengthMenu" : [ [ 25, 50, 100, 200, -1 ],
							[ 25, 50, 100, 200, "All" ] ],
					"iDisplayLength" : 100,
					"order" : []
				//"order": [[ sort, "desc" ]]
				});

		TableManageButtons = function() {
			"use strict";
			return {
				init : function() {
					handleDataTableButtons();
				}
			};
		}();

// 		$('#tableList').DataTable( {
// 	        stateSave: true
// 	    } );

		$('#tableList-keytable').DataTable({
			keys : true
		});

		$('#tableList-responsive').DataTable();

		$('#tableList-scroller').DataTable({
			ajax : "js/datatables/json/scroller-demo.json",
			deferRender : true,
			scrollY : 380,
			scrollCollapse : true,
			scroller : true
		});

		$('#tableList-fixed-header').DataTable({
			fixedHeader : true
		});

		var $datatable = $('#tableList-checkbox');

		$datatable.dataTable({
			'order' : [ [ 1, 'asc' ] ],
			'columnDefs' : [ {
				orderable : false,
				targets : [ 0 ]
			} ]
		});
		$datatable.on('draw.dt', function() {
			$('input').iCheck({
				checkboxClass : 'icheckbox_flat-green'
			});
		});

		TableManageButtons.init();
		$("#checkAll").removeClass("sorting_asc")
		
		$(".dataTables_empty").text("ユーザーが存在していません。");
	});
</script>
<script src="<c:url value="/resources/js/jquery.dataTables.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/icheck/icheck.min.js" />"></script>
<script src="<c:url value="/resources/js/custom.js" />"></script>
</body>
</html>