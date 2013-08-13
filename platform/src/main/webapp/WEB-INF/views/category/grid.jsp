<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form dojoType="dijit.form.Form" id="formCreate">    
    <table>
        <tr>
            <td><label for="name">分类ID*</label></td>
            <td>
                <input type="text" name="categoryIds" data-dojo-type="dijit/form/ValidationTextBox" required="true" invalidMessage="请输入分类ID，多个ID使用半角分号（;）分割"/>
            </td>
        </tr>
        <tr>
            <td colspan="4">
            <center>
            <button data-dojo-type="dojox/form/BusyButton" busyLabel="正在保存..." timeout="5">Save
                <script type="dojo/on" data-dojo-event="click">
                    require(["dojo/request","dojo/dom-form", "dijit/registry"], function(request, domForm, registry){
                        var form = registry.byId('formCreate');
                        console.warn(domForm.toObject('formCreate'));
                        console.warn(form);
                        if(form.validate()) {
                            request.post('${pageContext.request.contextPath}/domain/product/category/${id}',{
                                data: domForm.toObject('formCreate'),
                                handleAs: 'json'
                            }).then(function(text){
                                if(text.hasError) {//Has error when saving
                                    alert(text.message);
                                    return;
                                }
                                categoryGrid.setQuery();
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
