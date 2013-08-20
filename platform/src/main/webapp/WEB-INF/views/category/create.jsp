<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <form dojoType="dijit.form.Form" id="formCreate">    
        <table>
            <tr>
                <td>
                    <label for="name">名称*</label><input type="hidden" name="id" value="" />
                </td>
                <td>
                    <input type="text" name="name" data-dojo-type="dijit/form/ValidationTextBox" required="true"  ucfirst="true" invalidMessage="请输入分类名称"/>
                </td>
                <td><label for="parentCategory">上级分类</label></td>
                <td>
                    <input name="parentCategory" type="hidden" value="${category.id}" />${category.name}
                </td>
            </tr>
            <tr>
                <td>
                    <label for="description">简介</label>
                </td>
                <td colspan="3">
                    <textarea name="description" row="4" cols="50" data-dojo-type="dijit/form/SimpleTextarea"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="longDescription">说明</label>
                </td>
                <td colspan="3">
                    <textarea name="longDescription" row="4" cols="50" data-dojo-type="dijit/form/SimpleTextarea"></textarea>
                </td>
            </tr>
            <tr>
                <td><label for="displayTemplate">显示模板</label></td>
                <td><input type="text" name="displayTemplate" data-dojo-type="dijit.form.ValidationTextBox" /></td>
            </tr>
            <tr>
                <td colspan="4">SEO</td>
            </tr>
            <tr>
                <td>
                    <label for="url">URL</label>
                </td>
                <td>
                    <input type="text" name="url" dojoType="dijit.form.ValidationTextBox" ucfirst="true" invalidMessage=""/>
                </td>
                <td>
                    <label for="urlKey">URL Key</label>
                </td>
                <td>
                    <input type="text" name="urlKey" dojoType="dijit.form.ValidationTextBox" />
                </td>
            </tr>  
            <tr>
                <td colspan="4">有效期</td>
            </tr>
            <tr>
                <td><label for="activeStartDate">开始日期</label></td>
                <td><input name="defaultSku.activeStartDate" type="text" data-dojo-type="dijit/form/DateTextBox" /></td>
                <td><label for="activeEndDate">结束日期</label></td>
                <td><input name="defaultSku.activeEndDate" type="text" data-dojo-type="dijit/form/DateTextBox" /></td>
            </tr> 
            <tr>
                <td colspan="4">
                    <center>
                    <button dojoType="dojox.form.BusyButton" busyLabel="正在保存..." timeout="5">Save
                        <script type="dojo/on" data-dojo-event="click">

                            require(["dojo/request","dojo/dom-form", "dijit/registry"], function(request, domForm, registry){
                                var form = registry.byId('formCreate');
                                console.warn(domForm.toObject('formCreate'));
                                console.warn(form);
                                if(form.validate()) {
                                    request.post('${pageContext.request.contextPath}/domain/category/create',{
                                        data: domForm.toObject('formCreate'),
                                        handleAs: 'json'
                                    }).then(function(text){
                                        if(text.hasError) {//Has error when saving
                                            alert(text.message);
                                            return;
                                        }
                                        var tree = registry.byId('tree');
                                        var selectedObject = tree.get("selectedItems")[0];
                                        tree.model.get(selectedObject.id).then(function(selectedObject){
                                            tree.model.put(selectedObject);
                                        });
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