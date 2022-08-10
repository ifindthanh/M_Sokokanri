<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="sec" %>
<%@ taglib uri="/WEB-INF/taglibs/rolesDisplayingTaglib.tld" prefix="roles" %>
<html>
<head>
    <title>Thông tin user</title>
    <!-- Bootstrap core CSS -->
    <link rel="shortcut icon"
          href="<c:url value="/resources/img/favicon.ico" />"/>
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <link href="<c:url value="/resources/css/fonts/css/font-awesome.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/dialogbox.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/dialogbox.js" />"></script>

</head>
<body>
<div class="rightContent" align="center">
    <div class="header_bar">
        <jsp:include page="/WEB-INF/pages/common/header.jsp"/>
    </div>
    <div id="wrapper">
        <div class="body_container">
            <div class="container body">
                <div class="main_container">
                    <div class="">
                        <div class="row">
                            <div class="col-md-12 col-sm-12 col-xs-12">
                                <div class="x_panel table-panel">
                                    <div class="x_content">
                                        <div class="row">
                                            <div>
                                                <!-- top tiles -->

                                                <div class="main">

                                                    <h3>THÔNG TIN</h3>
                                                </div>
                                                <form:form modelAttribute="viewProviderForm" method="POST"
                                                           action="viewProvider" enctype="multipart/form-data"
                                                           class="form-horizontal div-center" id="viewUser">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="x_panel">

                                                                <div class="x_content">

                                                                    <div class="row">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12"> </label>
                                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                                <h2 class="pull-right"
                                                                                    style="margin-left: 20px">
                                                                                    <strong><i class="fa fa-user">
                                                                                        &nbsp;&nbsp;</i>${tree.getName()}
                                                                                    </strong>

                                                                                </h2>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group">
                                                                            <label
                                                                                    class="control-label col-md-3 col-sm-3 col-xs-12"
                                                                                    for="name" 　data-toggle="tooltip"
                                                                                    data-placement="right" title="">Tên cây</label>
                                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                                <input type="text"
                                                                                       class="form-control has-feedback-left pointer-events: none;"
                                                                                       name="representor" maxlength="30"
                                                                                       id="name"
                                                                                       disabled="disabled"
                                                                                       readonly="readonly"
                                                                                       value="${tree.name }">
                                                                            </div>
                                                                        </div>

                                                                        <div class="form-group">
                                                                            <label
                                                                                    class="control-label col-md-3 col-sm-3 col-xs-12"
                                                                                    for="treeGroupId" 　data-toggle="tooltip"
                                                                                    data-placement="right" title="">Nhóm loại cây</label>
                                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                                <input type="text"
                                                                                       class="form-control has-feedback-left pointer-events: none;"
                                                                                       name="treeGroupId" maxlength="30"
                                                                                       id="treeGroupId"
                                                                                       disabled="disabled"
                                                                                       readonly="readonly"
                                                                                       value="${tree.treeGroup.name }">
                                                                            </div>
                                                                        </div>

                                                                        <div class="form-group">
                                                                            <label
                                                                                    class="control-label col-md-3 col-sm-3 col-xs-12"
                                                                                    for="age" 　data-toggle="tooltip"
                                                                                    data-placement="right" title="">Tuổi cây (ngày)</label>
                                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                                <input type="text"
                                                                                       class="form-control has-feedback-left pointer-events: none;"
                                                                                       name="age" maxlength="30"
                                                                                       id="age"
                                                                                       disabled="disabled"
                                                                                       readonly="readonly"
                                                                                       value="${tree.age }">
                                                                            </div>
                                                                        </div>

                                                                        <div class="form-group">
                                                                            <label
                                                                                    class="control-label col-md-3 col-sm-3 col-xs-12"
                                                                                    for="description">Mô tả chi tiết</label>
                                                                            <div class="col-md-6 col-sm-6 col-xs-12">
																					<textarea
                                                                                            class="form-control description"
                                                                                            disabled="disabled"
                                                                                            readonly="readonly"
                                                                                            name="description"
                                                                                            maxlength="30"
                                                                                            id="description">${tree.description } </textarea>
                                                                            </div>
                                                                        </div>

                                                                        <div class="ln_solid"></div>
                                                                        <div class="form-group">
                                                                            <div
                                                                                    class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                                                <a href="chinh-sua/${tree.getId()}"
                                                                                   class="btn btn-primary btn-lg"
                                                                                   onclick=""> <i
                                                                                        class="fa fa-edit "></i> Chỉnh
                                                                                    sửa
                                                                                </a>
                                                                                <a href="tat-ca"
                                                                                   class="btn btn-default btn-lg">
                                                                                    <i class="fa fa-close m-right-xs"></i>
                                                                                    Quay lại
                                                                                </a>

                                                                            </div>
                                                                        </div>
                                                                    </div>
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
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>