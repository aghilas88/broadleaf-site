<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <script src="${pageContext.request.contextPath}/static/app/product/run.js"></script>
</head>
<body class="claro">

<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='region:"center", title:"Various Dijits", style:"padding:10px; display:true;"'>
<div data-dojo-type="dijit/TitlePane" data-dojo-props="title:'修改产品信息'">	
	<form data-dojo-type="dijit/form/Form" id="formEdit" url="${pageContext.request.contextPath}/domain/product/edit/${productId}">		
		<table>
			<tr>
				<td>
					<label for="name">名称<input type="hidden" value="${product.id}" name="id" /></label>
				</td>
				<td>
					<input type="text" name="defaultSku.name" data-dojo-type="dijit/form/ValidationTextBox" value="${product.defaultSku.name}" />
				</td>
	        	<td><label for="defaultCategory">默认分类</label></td>
	        	<td>
	      			<div data-dojo-type="dojo/store/JsonRest" data-dojo-id="defaultCategoryStore" target="${pageContext.request.contextPath}/domain/category/filteringselect/"></div>
					<input name="defaultCategoryId" data-dojo-type="dijit/form/FilteringSelect" data-dojo-id="categorySelect" store="defaultCategoryStore" value="${product.defaultCategory.id}" />
				</td>
			</tr>
			<tr>
				<td>
					<label for="description">简介</label>
				</td>
				<td colspan="3">
					<textarea name="defaultSku.description" row="4" cols="50" data-dojo-type="dijit/form/SimpleTextarea">${product.defaultSku.description}</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<label for="defaultSku.longDescription">说明</label>
				</td>
				<td colspan="3">
					<textarea name="defaultSku.longDescription" row="4" cols="50" data-dojo-type="dijit/form/SimpleTextarea">${product.defaultSku.longDescription}</textarea>
				</td>
			</tr>
	      	<tr>
	        	<td><label for="featuredProduct">是否特色产品</label></td>
	        	<td>
	          		<select name="featuredProduct" data-dojo-type="dijit/form/FilteringSelect" value="${product.featuredProduct}">
	            		<option value="true">是</option>
	            		<option value="false">否</option>
	          		</select>
	          	</td>
	        	<td><label for="canSellWithoutOptions">无配置项时可销售</label></td>
	        	<td>
	          		<select name="canSellWithoutOptions" data-dojo-type="dijit/form/FilteringSelect" value="${product.canSellWithoutOptions}">
	            		<option value="true">是</option>
	            		<option value="false">否</option>
	          		</select>
	          	</td>
	      	</tr>	
	      	<tr>
	        	<td><label for="manufacturer">生产厂家</label></td>
	        	<td>
					<input type="text" name="manufacturer" data-dojo-type="dijit/form/ValidationTextBox" value="${product.manufacturer}" />
	        	</td>
				<td>
					<label for="model">品牌</label>
				</td>
				<td>
					<input type="text" name="model" data-dojo-type="dijit/form/ValidationTextBox" value="${product.model}" />
				</td>
			</tr>
	      	<tr>
	      		<td colspan="2">SEO</td>
	      	</tr>
			<tr>
				<td>
					<label for="url">URL</label>
				</td>
				<td>
					<input type="text" name="url" data-dojo-type="dijit/form/ValidationTextBox" value="${product.url}" />
				</td>
				<td>
					<label for="urlKey">URL Key</label>
				</td>
				<td>
					<input type="text" name="urlKey" data-dojo-type="dijit/form/ValidationTextBox" value="${product.urlKey}" />
				</td>
			</tr>	      	
	      	<tr>
	      		<td colspan="2">价格</td>
	      	</tr>
			<tr>
				<td><label for="defaultSku.retailPrice">原价</label></td>
				<td><input type="text" name="defaultSku.retailPrice" value="${product.defaultSku.retailPrice}"
					data-dojo-type="dijit/form/CurrencyTextBox"
					data-dojo-props="constraints:{ fractional:true }, currency:'CNY', invalidMessage:'格式错误，正确的格式：1.25'" />
				</td>	
				<td><label for="defaultSku.salePrice">促销价</label></td>
				<td><input type="text" name="defaultSku.salePrice" value="${product.defaultSku.salePrice}"
					data-dojo-type="dijit/form/CurrencyTextBox"
					data-dojo-props="constraints:{ fractional:true }, currency:'CNY', invalidMessage:'格式错误，正确的格式：1.25'" />
				</td>	
			</tr> 
	      	<tr>
	        	<td><label for="taxable">是否含税${product.defaultSku.taxable}</label></td>
	        	<td>
	          		<select name="defaultSku.taxable" data-dojo-type="dijit/form/FilteringSelect" value="${product.defaultSku.taxable}">
	            		<option value="true">是</option>
	            		<option value="false">否</option>
	          		</select>
	          	</td>
	        	<td><label for="discountable">是否可以打折${product.defaultSku.discountable}</label></td>
	        	<td>
	          		<select name="defaultSku.discountable" data-dojo-type="dijit/form/FilteringSelect" value="${product.defaultSku.discountable}">
	            		<option value="true">是</option>
	            		<option value="false">否</option>
	          		</select>
				</td>
	      	</tr>	
	      	<tr>
	      		<td colspan="2">有效期</td>
	      	</tr>
	      	<tr>
	        	<td><label for="activeStartDate">开始日期</label></td>
	        	<td>
	        		<fmt:formatDate value="${product.activeStartDate}" var="activeStartDate" pattern="yyyy-MM-dd" />
	        		<input value="${activeStartDate}" name="defaultSku.activeStartDate" type="text" data-dojo-type="dijit/form/DateTextBox" />
	        	</td>
	        	<td><label for="activeEndDate">结束日期</label></td>
	        	<td>
	        		<fmt:formatDate value="${product.activeEndDate}" var="activeEndDate" pattern="yyyy-MM-dd" />
	        		<input value="${activeEndDate}" name="defaultSku.activeEndDate" type="text" data-dojo-type="dijit/form/DateTextBox" />
	        	</td>
	      	</tr>
	      	<tr>
	      		<td colspan="4">库存信息</td>
	      	</tr>
	      	<tr>
	        	<td><label for="available">是否有货</label></td>
	        	<td>
	          		<select name="defaultSku.available" data-dojo-type="dijit/form/FilteringSelect" value="${product.defaultSku.available}">
	            		<option value="true">有货</option>
	            		<option value="false">无货</option>
	          		</select>
	          	</tr>
	        	<td><label for="inventoryType">库存类型</label></td>
	        	<td>
	          		<select name="inventoryType" data-dojo-type="dijit/form/FilteringSelect" value="${product.defaultSku.inventoryType}">
	            		<option value="none">无</option>
	            		<option value="basic">基本库存</option>
	          		</select>	        		
	        	</td>
	      	</tr>
	      	<tr>
	        	<td><label for="fulfillmentType">Fulfillment Type</label></td>
	        	<td>
	          		<select name="fulfillmentType" data-dojo-type="dijit/form/FilteringSelect" value="${product.defaultSku.fulfillmentType}">
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
					<button data-dojo-type="dojox/form/BusyButton" busy-label="正在保存..." timeout="5">保存
						<script type="dojo/on" data-dojo-event="click">
							require(["dojo/topic"], function(topic){
    							topic.publish("product/button/edit/click");
    						});
		    			</script>
					</button>
					<button data-dojo-type="dojox/form/BusyButton" busy-label="正在保存..." timeout="5">发布
						<script type="dojo/on" data-dojo-event="click">
							require(["dojo/topic"], function(topic){
    							topic.publish("product/button/publish/click", '${pageContext.request.contextPath}/domain/product/publish/${productId}');
    						});
		    			</script>
					</button>					
	      		 	</center>
	      		</td>
	      	</tr>
		</table>
	</form>
</div>
<hr class="spacer" />
<div data-dojo-type="dijit/TitlePane" data-dojo-props="title:'所属分类'"> 
    <div>
		<button type="button" data-dojo-type="dijit/form/Button">添加分类			
		    <script type="dojo/on" data-dojo-event="click">
		        var dlg = dijit.byId('dialog1');
		        dlg.show();
		    </script>   
		</button>
		<button type="button" data-dojo-type="dijit/form/Button">删除选中分类
			<script type="dojo/on" data-dojo-event="click">
				require(["dojo/topic"], function(topic){
					topic.publish("product/button/categorydelete/click", 
						'${pageContext.request.contextPath}/domain/product/category/${productId}');
				});
			</script>
		</button>	
    </div>
    <div id="categoryGrid" url="${pageContext.request.contextPath}/domain/product/categories/${productId}"></div>
</div>
<hr/>
<div data-dojo-type="dijit/TitlePane" data-dojo-props="title:'SKU'"> 
    <div>
		<button type="button" data-dojo-type="dijit/form/Button">添加分类			
		    <script type="dojo/on" data-dojo-event="click">
		        var dlg = dijit.byId('dialog1');
		        dlg.show();
		    </script>   
		</button>
		<button type="button" data-dojo-type="dijit/form/Button">删除选中分类
			<script type="dojo/on" data-dojo-event="click">
				require(["dojo/topic"], function(topic){
					topic.publish("product/button/categorydelete/click", 
						'${pageContext.request.contextPath}/domain/product/category/${productId}');
				});
			</script>
		</button>	
    </div>
    <div id="categoryGrid" url="${pageContext.request.contextPath}/domain/product/categories/${productId}"></div>
</div>


<div id="dialog1" data-dojo-type="dijit/Dialog" style="display:none;width:640px;" data-dojo-props="title:'添加分类',
    href:'${pageContext.request.contextPath}/page/category/grid/prd-${productId}', refreshOnShow:true">
</div>

</body>
</html>
