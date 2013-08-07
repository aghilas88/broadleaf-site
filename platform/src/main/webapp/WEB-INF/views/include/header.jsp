<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="header" data-dojo-type="dijit/MenuBar" data-dojo-props="region:'top'">
    <div data-dojo-type="dijit/PopupMenuBarItem" id="edit">
        <span>产品目录</span>
        <div data-dojo-type="dijit/Menu" id="editMenu">
            <div data-dojo-type="dijit/MenuItem" data-dojo-props="iconClass:'dijitIconCut'">分类管理
                <script type="dojo/on" data-dojo-event="click">
                  location.href = "${pageContext.request.contextPath}/page/category/list";
                </script>
            </div>
                <div data-dojo-type="dijit/MenuItem" data-dojo-props="iconClass:'dijitIconCut'">产品管理
                    <script type="dojo/on" data-dojo-event="click">
                      location.href = "${pageContext.request.contextPath}/page/product/list";
                    </script>
                </div>                
                <div data-dojo-type="dijit/MenuItem" data-dojo-props="iconClass:'dijitIconCut'">厂家管理
                    <script type="dojo/on" data-dojo-event="click">
                      location.href = "${pageContext.request.contextPath}/page/manufacture/list";
                    </script>
                </div>
                <div data-dojo-type="dijit/MenuSeparator"></div>
                <div data-dojo-type="dijit/MenuItem" data-dojo-props="iconClass:'dijitIconCut'">产品配置
                    <script type="dojo/on" data-dojo-event="click">
                      location.href = "${pageContext.request.contextPath}/page/options/list";
                    </script>
                </div>
            </div>
        </div>
        <div data-dojo-type="dijit/PopupMenuBarItem" id="view">
            <span>内容管理</span>
            <div data-dojo-type="dijit/Menu" id="viewMenu">
                <div data-dojo-type="dijit/MenuItem" data-dojo-props="iconClass:'dijitIconCut'">页面管理
                    <script type="dojo/on" data-dojo-event="click">
                      location.href = "${pageContext.request.contextPath}/page/page/list";
                    </script>
                </div>
                <div data-dojo-type="dijit/MenuItem" data-dojo-props="iconClass:'dijitIconCut'">内容管理
                    <script type="dojo/on" data-dojo-event="click">
                      location.href = "${pageContext.request.contextPath}/page/content/list";
                    </script>
                </div>  
                <div data-dojo-type="dijit/MenuItem" data-dojo-props="iconClass:'dijitIconCut'">文件管理
                    <script type="dojo/on" data-dojo-event="click">
                      location.href = "${pageContext.request.contextPath}/page/asset/list";
                    </script>
                </div>
                <div data-dojo-type="dijit/MenuItem" data-dojo-props="iconClass:'dijitIconCut'">链接管理
                    <script type="dojo/on" data-dojo-event="click">
                      location.href = "${pageContext.request.contextPath}/page/url/list";
                    </script>
                </div>                
            </div>
        </div>
    </div>