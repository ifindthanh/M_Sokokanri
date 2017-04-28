<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<div class="main_container">
	<div class="left_col">
		<div class="left_col scroll-view">
			<div>
				<a href="${pageContext.request.contextPath}/card/list/0"
					class="site_title"><i class="fa fa-credit-card"></i> <span>倉庫管理システム</span></a>
			</div>
			<div class="profile">

				<div class="profile_info">
					ようこそ、${lastName} ${firstName}さん
				</div>
			</div>
			<div class="clearfix"></div>

			<!-- sidebar menu -->
			<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
				<div class="menu_section">
					<!-- 					<h3>アドミニストレイター</h3> -->
					<div class="menu_section">
						<ul class="nav side-menu">
							<li id="li1"><a style="cursor: pointer;"><i
									class="fa fa-credit-card"></i> 倉庫 <span
									class="fa fa-chevron-down"></span></a>
								<ul class="nav child_menu" style="display: none;">
									<li id="li2"><a href="<c:url value= "/card/list/0"/> ">倉庫一覧</a></li>
									<li id="li2"><a href="<c:url value= "/card/share/0"/> ">シェアリング</a></li>
									<li id="li2"><a href="<c:url value= "/card/recycle/0"/> ">ごみ箱</a></li>
								</ul></li>
							<li id="li1"><a style="cursor: pointer;"> <i
									class="fa fa-edit"></i> 登録 <span class="fa fa-chevron-down"></span></a>
								<ul class="nav child_menu" style="display: none;">
									<li id="li2"><a
										href="<c:url value= "/card/registerCard"/> ">倉庫登録</a></li>
									<li id="li2"><a href="<c:url value= "/card/upload"/> ">倉庫アップロード</a></li>
								</ul></li>
							<li id="li1"><a style="cursor: pointer;"> <i
									class="fa fa-cogs"></i> 設定 <span class="fa fa-chevron-down"></span></a>
								<ul class="nav child_menu" style="display: none;">
									<sec:authorize access="hasAnyRole('ROLE_A','ROLE_S')">
										<li id="li2"><a href="<c:url value= "/user/list/0"/> ">ユーザ一覧</a></li>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_I')">
										<li id="li2"><a
											href="<c:url value= "/user/viewUser/${pageContext.request.userPrincipal.name}"/> ">個人設定</a></li>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_U')">
										<li id="li2"><a
											href="<c:url value= "/user/viewUser/${pageContext.request.userPrincipal.name}"/> ">個人設定</a></li>
									</sec:authorize>
									<li id="li2"><a
										href="<c:url value= "/profile/change_pass"/> ">パスワード変更</a></li>
								</ul></li>
						</ul>
					</div>
				</div>
				<!-- /sidebar menu -->
			</div>
		</div>
	</div>
</div>

