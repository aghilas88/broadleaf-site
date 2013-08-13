<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>BLC Platform</title>
    <!-- Application-specific CSS should be stored in your application’s package to ensure portability and to allow
         the build system to combine & minify it. -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/gridx/resources/claro/Gridx.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/app/resources/app.css">
    <script data-dojo-config="async: 1, tlmSiblingOfDojo: 0, isDebug: 1" src="${pageContext.request.contextPath}/static/dojo/dojo.js"></script>
    <script src="${pageContext.request.contextPath}/static/app/run.js"></script>
</head>
<body class="claro">

<div>

<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div data-dojo-type="dijit/TitlePane" data-dojo-props="title:'搜索产品'">
    <form data-dojo-type="dijit/form/Form" id="formSearch">
        <table>
            <tr>
                <td><label for="name">产品名称</label></td>
                <td><input type="text" name="defaultSku.name" data-dojo-type="dijit/form/ValidationTextBox" /></td>
                <td><label for="defaultCategoryId">默认分类</label></td>
                <td>
                    <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="defaultCategoryStore" url="${pageContext.request.contextPath}/domain/category/tree"></div>
                    <input name="defaultCategoryId" data-dojo-type="dijit/form/FilteringSelect" data-dojo-id="categorySelect" store="defaultCategoryStore" />
                </td>
                <td><label for="featuredProduct">是否特色产品</label></td>
                <td>
                    <select name="featuredProduct" data-dojo-type="dijit/form/FilteringSelect">
                        <option value="">全部</option>
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="defaultSku.available">是否可用</label></td>
                <td>
                    <select name="defaultSku.available" data-dojo-type="dijit/form/FilteringSelect">
                        <option value="">全部</option>
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <td><label for="defaultSku.discountable">是否可打折</label></td>
                <td>
                    <select name="defaultSku.discountable" data-dojo-type="dijit/form/FilteringSelect">
                        <option value="">全部</option>
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <td><label for="defaultSku.taxable">是否含税</label></td>
                <td>
                    <select name="defaultSku.taxable" data-dojo-type="dijit/form/FilteringSelect">
                        <option value="">全部</option>
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="6">
                    <center>
                        <button data-dojo-type="dojox/form/BusyButton" busyLabel="正在搜索..." timeout="5" 
                              onclick="console.info(dojo.formToObject('formSearch'));console.warn(enhancedGrid);enhancedGrid.plugins.pagination._currentPage = 1;enhancedGrid.setQuery(dojo.formToObject('formSearch'))">搜索</button>
                    </center>
                </td>
            </tr>
        </table>    
    </form>

</div><!-- end title pane -->

<div class="heading">搜索结果</div> 

<!--
<div id='grid1' jsid='grid1' data-dojo-type='gridx/Grid' data-dojo-props='
    cacheClass: "gridx/core/model/cache/Sync",
    store: store,
    barTop: grid1Top,
    autoHeight: true,
    structure: layout,
    modules: [
        "gridx/modules/IndirectSelect",
        "gridx/modules/extendedSelect/Row",
        "gridx/modules/ColumnResizer",
        "gridx/modules/RowHeader",
        "gridx/modules/Pagination",
        "gridx/modules/pagination/PaginationBar",
        "gridx/modules/VirtualVScroller"
    ]
'></div>
-->

<div id="gridContainer" ></div>


</div>

<div class="dijitHidden">
    <!-- dialog that gets its content via ajax, uses loading message -->
    <div data-dojo-type="dijit/Dialog" style="width:600px;" data-dojo-props="title:'Ajax Create Dialog',href:'${pageContext.request.contextPath}/page/product/create',loadingMessage:'Loading dialog content...'" id="ajaxCreateDialog" parseOnLoad="true"></div>
</div>


<div class="dijitHidden">
    <!-- dialog that gets its content via ajax, uses loading message -->
    <div data-dojo-type="dijit/Dialog" style="width:600px;" data-dojo-props="title:'Edit Product', href:'${pageContext.request.contextPath}/page/product/edit/',loadingMessage:'Loading dialog content...'" id="ajaxEditDialog" parseOnLoad="true"></div>
</div>

</body>
</html>