<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form data-dojo-type="dijit/form/Form" id="formCreate">     
<table>
    <tr>
        <td><label for="name">名称</label></td>
        <td><input type="text" name="defaultSku.name" data-dojo-type="dijit/form/ValidationTextBox" value="${product.defaultSku.name}" required="true" /></td>
        <td><label for="parentCategory">默认分类</label></td>
        <td>
            <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="defaultCategoryStore" url="${pageContext.request.contextPath}/domain/category/tree"></div>
            <input name="parentCategory" data-dojo-type="dijit/form/FilteringSelect" data-dojo-id="categorySelect" store="defaultCategoryStore" value="${product.defaultCategory.id}" required="true" />
        </td>
    </tr>
    <tr>
        <td><label for="description">简介</label></td>
        <td colspan="3">
            <textarea name="defaultSku.description" row="4" cols="50" data-dojo-type="dijit/form/SimpleTextarea">${product.defaultSku.description}</textarea>
        </td>
    </tr>
    <tr>
        <td><label for="defaultSku.longDescription">说明</label></td>
        <td colspan="3">
            <textarea name="defaultSku.longDescription" row="4" cols="50" data-dojo-type="dijit/form/SimpleTextarea">${product.defaultSku.longDescription}</textarea>
        </td>
    </tr>
    <tr>
        <td><label for="featuredProduct">是否特色产品</label></td>
        <td>
            <select name="featuredProduct" data-dojo-type="dijit/form/FilteringSelect" value="false">
                <option value="true">是</option>
                <option value="false">否</option>
            </select>
        </td>
        <td><label for="canSellWithoutOptions">无配置项时可销售</label></td>
        <td>
            <select name="canSellWithoutOptions" data-dojo-type="dijit/form/FilteringSelect" value="false">
                <option value="true">是</option>
                <option value="false">否</option>
            </select>
        </td>
    </tr>   
    <tr>
        <td><label for="manufacturer">生产厂家</label></td>
        <td><input type="text" name="manufacturer" data-dojo-type="dijit/form/ValidationTextBox" value="${product.manufacturer}" /></td>
        <td><label for="model">品牌</label></td>
        <td><input type="text" name="model" data-dojo-type="dijit/form/ValidationTextBox" value="${product.model}" /></td>
    </tr>
    <tr>
        <td colspan="4">SEO</td>
    </tr>
    <tr>
        <td><label for="url">URL</label></td>
        <td><input type="text" name="url" data-dojo-type="dijit/form/ValidationTextBox" value="${product.url}" /></td>
        <td><label for="urlKey">URL Key</label></td>
        <td><input type="text" name="urlKey" data-dojo-type="dijit/form/ValidationTextBox" value="${product.urlKey}" /></td>
    </tr>           
    <tr>
        <td colspan="4">价格</td>
    </tr>
    <tr>
        <td><label for="defaultSku.retailPrice">原价</label></td>
        <td><input type="text" name="defaultSku.retailPrice" value="0.0"
                    data-dojo-type="dijit/form/CurrencyTextBox" required="true" 
                    data-dojo-props="constraints:{ fractional:true }, currency:'CNY', invalidMessage:'格式错误，正确的格式：1.25'" />
        </td>   
        <td><label for="defaultSku.salePrice">促销价</label></td>
        <td><input type="text" name="defaultSku.salePrice"
                    data-dojo-type="dijit/form/CurrencyTextBox"
                    data-dojo-props="constraints:{ fractional:true }, currency:'CNY', invalidMessage:'格式错误，正确的格式：1.25'" />
        </td>   
    </tr> 
    <tr>
        <td><label for="taxable">是否含税</label></td>
        <td>
            <select name="defaultSku.taxable" data-dojo-type="dijit/form/FilteringSelect" value="false">
                <option value="true">是</option>
                <option value="false">否</option>
            </select>
        </td>
        <td><label for="discountable">是否可以打折</label></td>
        <td>
            <select name="defaultSku.discountable" data-dojo-type="dijit/form/FilteringSelect" value="true">
                <option value="true">是</option>
                <option value="false">否</option>
            </select>
        </td>
    </tr>   
    <tr>
        <td colspan="4">有效期</td>
    </tr>
    <tr>
        <td><label for="activeStartDate">开始日期</label></td>
        <td>
            <fmt:formatDate value="${product.activeStartDate}" var="activeStartDate" pattern="yyyy-MM-dd" />
            <input value="${activeStartDate}" name="defaultSku.activeStartDate" type="text" data-dojo-type="dijit/form/DateTextBox" required="true" />
        </td>
        <td><label for="activeEndDate">结束日期</label></td>
        <td>
            <fmt:formatDate value="${product.activeEndDate}" var="activeEndDate" pattern="yyyy-MM-dd" />
            <input value="${activeEndDate}" name="defaultSku.activeEndDate" type="text" data-dojo-type="dijit/form/DateTextBox" />
        </td>
    </tr>
    <tr><td colspan="4">库存信息</td></tr>
    <tr>
        <td><label for="available">是否有货</label></td>
        <td>
            <select name="defaultSku.available" data-dojo-type="dijit/form/FilteringSelect" value="true">
                <option value="true">有货</option>
                <option value="false">无货</option>
            </select>
        </td>
        <td><label for="inventoryType">库存类型</label></td>
        <td>
            <select name="inventoryType" data-dojo-type="dijit/form/FilteringSelect" value="none">
                <option value="none">无</option>
                <option value="basic">基本库存</option>
            </select>                   
        </td>
    </tr>
    <tr>
        <td><label for="fulfillmentType">Fulfillment Type</label></td>
        <td>
            <select name="fulfillmentType" data-dojo-type="dijit/form/FilteringSelect" value="2">
                <option value="1">Digital</option>
                <option value="2">Physical Ship</option>
                <option value="3">Physical Pickup or Ship</option>
                <option value="4">Shipping</option>
            </select>
        </td>
    </tr>
    <tr>
        <td colspan="4">
        <center>
            <button data-dojo-type="dojox.form.BusyButton" busyLabel="正在保存..." timeout="5">保存
            <script type="dojo/on" data-dojo-event="click" data-dojo-args="object">
                require(["dojo/request", "dojo/dom-form", "dijit/registry", "dojo/_base/connect"], function(request, form, registry, connect){
                    var formCreate = registry.byId('formCreate');
                    if(formCreate.validate()) {
                        request.post('${pageContext.request.contextPath}/domain/product/create',{
                            data: form.toObject('formCreate'),
                            handleAs: 'json'
                        }).then(function(text){
                            console.log("The server returned: ", text);
                            if(text.hasError) {//Has error when saving
                                alert(text.message);
                                return;
                            } 
                            connect.publish("product.create.success", [{
                                name: text.message
                            }]);    
                            alert(text.message);
                            registry.byId("dialog1").hide();
                        });
                    }
                });
            </script>          
            </button>
        </center>
        </td>
    </tr>
</table>
</form>