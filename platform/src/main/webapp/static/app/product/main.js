/**
 * This file is the application's main JavaScript file. It is listed as a dependency in run.js and will automatically
 * load when run.js loads.
 *
 * Because this file has the special filename `main.js`, and because we've registered the `app` package in run.js,
 * whatever object this module returns can be loaded by other files simply by requiring `app` (instead of `app/main`).
 *
 * Our first dependency is to the `dojo/has` module, which allows us to conditionally execute code based on
 * configuration settings or environmental information. Unlike a normal conditional, these branches can be compiled
 * away by the build system; see `staticHasFeatures` in app.profile.js for more information.
 *
 * Our second dependency is to the special module `require`; this allows us to make additional require calls using
 * module IDs relative to this module within the body of the define callback.
 *
 * In all cases, whatever function is passed to define() is only invoked once, and the returned value is cached.
 *
 * More information about everything described about the loader throughout this file can be found at
 * <http://dojotoolkit.org/reference-guide/loader/amd.html>.
 */
define([ 'dojo/has', 'require' ], function (has, require) {
	require([ 'dojo/parser', 
		'dijit/registry',
		'dojo/request', 
		'dojo/dom-form',
		'dojo/topic',
		'dojo/store/JsonRest',
		'dojo/dom',
		'gridx/Grid',
		'dijit/Toolbar',
		'dijit/form/Button',
		'dijit/form/ToggleButton', 
		'dojox/form/BusyButton',		
		//'gridx/core/model/cache/Sync',
		'gridx/core/model/cache/Async',
        "gridx/modules/IndirectSelect",
        "gridx/modules/extendedSelect/Row",
        "gridx/modules/ColumnResizer",
        "gridx/modules/RowHeader",
        "gridx/modules/Pagination",
        "gridx/modules/pagination/PaginationBar",
        "gridx/modules/VirtualVScroller",
        "gridx/modules/Bar",       
		'dojo/domReady!' ], function (parser, registry, request, form, topic, JsonRest, dom, Grid, Toolbar, Button, ToggleButton) {
    
    parser.parse();

	grid = new Grid({
		id: 'grid',
		autoHeight: true,
		store: new JsonRest({
    		target: dom.byId('categoryGrid').getAttribute('url')
		}),
		structure: [
			{id: 'id', field: 'id', name: '分类ID'},
			{id: 'name', field: 'name', name: '分类名称'},
			{id: 'url', field: 'url', name: 'URL'},
			{id: 'displayOrder', field: 'displayOrder', name: '显示顺序'}
		],
		cacheClass: "gridx/core/model/cache/Async",
		modules: [
			"gridx/modules/IndirectSelect",
			"gridx/modules/extendedSelect/Row",
		    "gridx/modules/ColumnResizer",
		    "gridx/modules/RowHeader",
		    "gridx/modules/VirtualVScroller"
		]
	});
	grid.placeAt('categoryGrid');
	grid.startup();	

	topic.subscribe('product/button/edit/click', function() {
		request.post(dom.byId('formEdit').getAttribute('url'),{
			data: form.toObject('formEdit'),
			handleAs: 'json'
		}).then(function(text){
			console.log("The server returned: ", text);
			if(text && text.hasError) {
				alert(text.message);
			} else {
				alert(text.message);
			}
		});		
	});

	topic.subscribe('product/button/categorydelete/click', function(url) {
		console.warn(grid.select.row.getSelected().join(','), url);
		if(confirm("确认删除这些条目？")){
			request.del(url,{
				query: {categoryIds: grid.select.row.getSelected().join(',')},
				handleAs: 'json'
			}).then(function(text){
				if(text && text.hasError) {
					alert(text.message);
					return;
				} else {
					grid.model.clearCache();
					grid.body.refresh();
					alert(text.message);
				}
			});			
		}
	});

	topic.subscribe('product/button/categoryaddsave/click', function(url, data){
		request.post(url,{
			data: data,
			handleAs: 'json'
		}).then(function(text){
			if(text.hasError) {//Has error when saving
				alert(text.message);
				return;
			}
			grid.model.clearCache();
			grid.body.refresh();
			registry.byId("dialog1").hide();
		});
	}); 



	});
});