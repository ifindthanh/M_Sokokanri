
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>パスワードの更新</title>
<!-- Bootstrap core CSS -->
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" />
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<link href="<c:url value="/resources/css/icheck/green.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
<link
	href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/dialogbox.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/custom.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/dialogbox.js" />"></script>
<script type="text/javascript">
	function close_window() {
		this.close();
	}
	function confirmChange(){
		var pOld = document.getElementById('oldPassword').value;
		var pNew1 = document.getElementById('password').value;
		var pNew2 = document.getElementById('confirmPassword').value;
		
		if(pOld == ""){
			$("#errorMessenger").html("旧パスワードを入力してください。");
		}else if(pNew1 == ""){
			$("#errorMessenger").html("新しいパスワードを入力してください。");
		}else if(pNew2 == ""){
			$("#errorMessenger").html("新しいパスワードの確認を入力してください。");
		}else{
			$.dialogbox({
				type : 'msg',
				title : '確認',
				content : 'パスワードが変更されます。宜しいですか？',
				closeBtn : true,
				btn : [ 'はい', 'いいえ' ],
				call : [ function() {
					$("#changePass").submit();
					$.dialogbox.close();

				}, function() {
					$.dialogbox.close();
				} ]

			});
		}
	}
	
	function checkPrtScn(evt){
		var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode == 42 || charCode == 32) {
	    	evt.preventDefault();
	    }
	}
	
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



				<div id="login" class="animate form" style= "padding-top: 30px; padding-bottom: 30px;
					border-radius:4px; background: #fff">
						<section class="login_content">
						<form:form modelAttribute="userForm" method="POST"
							action="changePassword2" class="form-horizontal" id = "changePass">
							<div class="block-eff-no-margin">
							<h1>パスワードの更新</h1>
							<div id="error_message">
								<div class="row">
									<p style="color: red; text-align: center;"></p>
								</div>
								<div class="row">
									<p style="color: red; text-align: left;" id="errorMessenger">
										<c:forEach var="msg" items="${result}">
											${msg}<br/>
										</c:forEach>
									</p>
								</div>
							</div>
							<div class="form-group">
								<input name="oldPassword" type="password" class="form-control" id="oldPassword"
									placeholder="旧パスワード" required maxlength="20" onkeypress="checkPrtScn(event)"/>
							</div>
							<div class="form-group">
								<input name="password" type="password" class="form-control" id="password"
									required placeholder="新しいパスワード" maxlength="20" onkeypress="checkPrtScn(event)"/>
							</div>
							<div class="form-group">
								<input name="confirmPassword" type="password"  id="confirmPassword"
									class="form-control" placeholder="新しいパスワードの確認" maxlength="20" onkeypress="checkPrtScn(event)"/>
							</div>

							<div class="form-group" style="margin-top:30px;">
								<button type="button" class="btn btn-primary btn-lg" onclick="confirmChange()" ><i class="fa fa-save m-right-xs"></i> 変更</button>
							</div>
							</div>
							<div class="clearfix"></div>
							
						</form:form>
					</section>
					</div>
				
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/icheck/icheck.min.js" />"></script>
<script src="<c:url value="/resources/js/custom.js" />"></script>
</html>