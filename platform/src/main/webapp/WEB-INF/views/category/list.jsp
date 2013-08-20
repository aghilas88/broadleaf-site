<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Dojo Admin Platform</title>
     <!-- Application-specific CSS should be stored in your application’s package to ensure portability and to allow
         the build system to combine & minify it. -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/gridx/resources/claro/Gridx.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/app/resources/app.css">
    <script data-dojo-config="async: 1, tlmSiblingOfDojo: 0, isDebug: 1" src="${pageContext.request.contextPath}/static/dojo/dojo.js"></script>
    <script src="${pageContext.request.contextPath}/static/app/category/run.js"></script>
</head>
<body class="claro">

<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="liveSplitters:false, design:'sidebar'" style="width: 1260px; height: 688px">

    <div data-dojo-type="dijit/layout/AccordionContainer" data-dojo-props="region:'leading', splitter:true, minSize:20" 
        style="width: 300px;height: 600px" id="leftAccordion">
        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"分类列表"'>
            <!-- tree widget -->
            <div id="tree" url="${pageContext.request.contextPath}/domain/category/tree/"></div>    
        </div>
    </div>

    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'">   
        <button type="button" data-dojo-type="dijit/form/Button">新建分类
            <script type="dojo/on" data-dojo-event="click">
                alert('dd');
            </script>   
        </button>
        <button type="button" data-dojo-type="dijit/form/Button">删除分类
            <script type="dojo/on" data-dojo-event="click">
                alert('del');
            </script>   
        </button>

        <div data-dojo-type="dijit/TitlePane" data-dojo-props="title:'分类详情'">      
        <form data-dojo-type="dijit/form/Form" id="formEdit">    
            <table>
                <tr>
                    <td><label for="name">名称</label><input type="hidden" name="id" value="${category.name}" /></td>
                    <td><input type="text" name="name" data-dojo-type="dijit/form/ValidationTextBox" required="true" /></td>
                    <td><label for="displayTemplate">显示模板</label></td>
                    <td><input type="text" name="displayTemplate" data-dojo-type="dijit/form/ValidationTextBox" value="${category.displayTemplate}"/></td>
                </tr>
                <tr>
                    <td><label for="description">简介</label></td>
                    <td colspan="3">
                        <textarea name="description" row="4" cols="50" data-dojo-type="dijit/form/SimpleTextarea">${category.description}</textarea>
                    </td>
                </tr>
                <tr>
                    <td><label for="longDescription">说明</label></td>
                    <td colspan="3">
                        <textarea name="longDescription" row="4" cols="50" data-dojo-type="dijit/form/SimpleTextarea">${category.longDescription}</textarea>
                    </td>
                </tr>

                <tr>
                    <td><label for="url">URL</label></td>
                    <td><input type="text" name="url" data-dojo-type="dijit/form/ValidationTextBox" value="${category.url}" /></td>
                    <td><label for="urlKey">URL Key</label></td>
                    <td><input type="text" name="urlKey" data-dojo-type="dijit/form/ValidationTextBox" value="${category.urlKey}" /></td>
                </tr>  
                <tr>
                    <td colspan="4">有效期</td>
                </tr>
                <tr>
                    <td><label for="activeStartDate">开始日期</label></td>
                    <fmt:formatDate value="${category.activeStartDate}" var="activeStartDate" pattern="yyyy-MM-dd" />
                    <td><input name="activeStartDate" type="text" data-dojo-type="dijit/form/DateTextBox" value="${activeStartDate}" /></td>
                    <td><label for="activeEndDate">结束日期</label></td>
                    <fmt:formatDate value="${category.activeEndDate}" var="activeEndDate" pattern="yyyy-MM-dd" />                
                    <td><input name="activeEndDate" type="text" data-dojo-type="dijit/form/DateTextBox" value="${activeEndDate}" /></td>
                </tr> 
                <tr>
                    <td colspan="4">
                        <center>
                        <button data-dojo-type="dojox/form/BusyButton" busyLabel="正在保存..." timeout="5">保存
                            <script type="dojo/on" data-dojo-event="click" data-dojo-args="object">
                                require(["dojo/request", "dojo/dom-form", "dijit/registry"], function(request, form, registry){
                                    console.warn(form.toObject('formEdit'));
                                    request.post('${pageContext.request.contextPath}/domain/category/edit',{
                                        data: form.toObject('formEdit'),
                                        handleAs: 'json'
                                    }).then(function(text){
                                        if(text && text.hasError) {
                                            alert(text.message);
                                        } else {
                                            alert(text.message);
                                        }
                                    });
                                });
                            </script>          
                        </button>
                        </center>
                    </td>
                </tr>
            </table>
        </form>
        </div>


        <div data-dojo-type="dijit/TitlePane" data-dojo-props="title:'图片'"> 
            <div>
                <button type="button" data-dojo-type="dijit/form/Button">添加图片         
                    <script type="dojo/on" data-dojo-event="click">
                        var dlg = dijit.byId('dialog1');
                        dlg.show();
                    </script>   
                </button>
                <button type="button" data-dojo-type="dijit/form/Button">删除选中图片
                    <script type="dojo/on" data-dojo-event="click">
                        require(["dojo/topic"], function(topic){
                            topic.publish("category/button/mediadelete/click", 
                                '${pageContext.request.contextPath}/domain/category/media/${categoryId}');
                        });
                    </script>
                </button>   
            </div>
            <div id="mediaGrid" url="${pageContext.request.contextPath}/domain/category/medias/${categoryId}"></div>
        </div>

        <div data-dojo-type="dijit/TitlePane" data-dojo-props="title:'所属产品'"> 
            <div>
                <button type="button" data-dojo-type="dijit/form/Button">添加产品         
                    <script type="dojo/on" data-dojo-event="click">
                        var dlg = dijit.byId('dialog1');
                        dlg.show();
                    </script>   
                </button>
                <button type="button" data-dojo-type="dijit/form/Button">删除选中产品
                    <script type="dojo/on" data-dojo-event="click">
                        require(["dojo/topic"], function(topic){
                            topic.publish("category/button/mediadelete/click", 
                                '${pageContext.request.contextPath}/domain/category/product/${categoryId}');
                        });
                    </script>
                </button>   
            </div>
            <div id="productGrid" url="${pageContext.request.contextPath}/domain/category/products/${categoryId}"></div>
        </div>
    </div><!-- Ending center panel-->


</div>
</body>
</html>