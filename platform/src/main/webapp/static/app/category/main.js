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
		'dojox/form/BusyButton',	
		"dijit/Tree",
		"dijit/tree/ObjectStoreModel",	
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
		'dojo/domReady!' ], function (parser, registry, request, form, topic, JsonRest, dom, Grid, Toolbar, Button, BusyButton, Tree, ObjectStoreModel) {
    
    parser.parse();

    // create store
	usGov = new JsonRest({
		target: dom.byId('tree').getAttribute('url'),
		getChildren: function(object){
			// object may just be stub object, so get the full object first and then return it's
			// list of children
			return this.get(object.id).then(function(fullObject){
				return fullObject.children;
			});
		}
	});

	// create model to interface Tree to store
	model = new ObjectStoreModel({
		store: usGov,

		getRoot: function(onItem){
			this.store.get("root").then(onItem);
		},
		mayHaveChildren: function(object){
			return "children" in object;
		}
	});

	var tree = new Tree({
		model: model,
		openOnClick: false,
		persist: true
	}, "tree"); // make sure you have a target HTML element with this id
	
	tree.on("click", function(object){
    	//object.name = prompt("Enter a new name for the object");
    	//governmentStore.put(object);
    	console.warn(object);
		request.get(dom.byId('formEdit').getAttribute('url') + object.id,{
			handleAs: 'json'
		}).then(function(text){
			console.info(text);
			/**
			if(text && text.id) {
				var form = registry.byId('formEdit');
				if(form) {
					form.attr('value', text);
					var catId = dom.byId('categoryId')
					if(catId) {
						catId.value = text.id;
					}
				}
			} 
			**/                   
		});    	
	}, true);

	tree.startup();

	mediaGrid = new Grid({
		id: 'mediaGrid',
		autoHeight: true,
		store: new JsonRest({
    		target: dom.byId('mediaGrid').getAttribute('url')
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
	mediaGrid.placeAt('mediaGrid');
	mediaGrid.startup();	


	});
});