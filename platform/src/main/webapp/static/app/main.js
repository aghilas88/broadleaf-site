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
		'dojo/topic',
		'gridx/tests/support/data/MusicData',
		'gridx/tests/support/stores/Memory',
		'gridx/Grid',
		'dijit/Toolbar',
		'dijit/form/Button',
		'dijit/form/ToggleButton', 		
		'gridx/core/model/cache/Sync',
        "gridx/modules/IndirectSelect",
        "gridx/modules/extendedSelect/Row",
        "gridx/modules/ColumnResizer",
        "gridx/modules/RowHeader",
        "gridx/modules/Pagination",
        "gridx/modules/pagination/PaginationBar",
        "gridx/modules/VirtualVScroller",
        "gridx/modules/Bar",       
		'dojo/domReady!' ], function (parser, registry, topic, dataSource, storeFactory, Grid, Toolbar, Button, ToggleButton) {
			
		layout = [
			{id: 'id', field: 'id', name: 'Identity', width: '80px'},
			{id: 'Genre', field: 'Genre', name: 'Genre', width: '100px'},
			{id: 'Artist', field: 'Artist', name: 'Artist', width: '120px'},
			{id: 'Year', field: 'Year', name: 'Year', width: '80px'},
			{id: 'Album', field: 'Album', name: 'Album', width: '160px'},
			{id: 'Name', field: 'Name', name: 'Name', width: '80px'},
			{id: 'Length', field: 'Length', name: 'Length', width: '80px'},
			{id: 'Track', field: 'Track', name: 'Track', width: '80px'},
			{id: 'Composer', field: 'Composer', name: 'Composer', width: '160px'}
		];
		
		var grid1Toolbar = new Toolbar({});

		grid1Toolbar.addChild(new Button({
			label: 'New',
			showLabel:false,
			iconClass:"dijitEditorIcon dijitEditorIconSave",
			onClick: function(){
				onNewButtonClick(grid1);
			}
		}));		

		grid1Toolbar.addChild(new Button({
			label: 'Edit',
			iconClass:"dijitEditorIcon dijitEditorIconCopy",
			showLabel: false,
			onClick: function(){
				onEditButtongClick(grid);
			}
		}));		

		grid1Toolbar.addChild(new Button({
			label: 'Delete',
			showLabel:false,
			iconClass:"dijitEditorIcon dijitEditorIconDelete",
			onClick: function(){
				onDeleteButtonClick(grid);
			}
		}));

		grid1Top = [
			grid1Toolbar
		];	

      	parser.parse();

		grid = new Grid({
			id: 'grid',
			store: storeFactory({
				dataSource: dataSource,
				size: 10
			}),
		    barTop: grid1Top,
		    autoHeight: true,
		    structure: layout,
			cacheClass: "gridx/core/model/cache/Sync",
			modules: [
		        "gridx/modules/IndirectSelect",
		        "gridx/modules/extendedSelect/Row",
		        "gridx/modules/ColumnResizer",
		        "gridx/modules/RowHeader",
		        "gridx/modules/Pagination",
		        "gridx/modules/pagination/PaginationBar",
		        "gridx/modules/VirtualVScroller"
			]
		});

		grid.placeAt('gridContainer');

		grid.connect(grid, "onRowDblClick", function(evt){
			console.warn(evt.rowId, evt.columnId, evt);
			var dialog = registry.byId("ajaxEditDialog");
			/**
			dialog.href = dialog.href + evt.rowId;
			console.warn(dialog);
			dialog.show();
			**/
			evt.stopImmediatePropagation();
			location.href = dialog.href + evt.rowId;
		});

		grid.startup();	

      	onNewButtonClick = function(grid1) {
      		registry.byId("ajaxCreateDialog").show();
      	}

      	onEditButtongClick = function(gridX) {
      		if(gridX) {
      			console.warn(gridX.select.row.getSelected());
      		}
      	}

      	onDeleteButtonClick = function(gridX) {
      		if(gridX) {
      			console.warn(gridX);
      		}
      	}
		// Register the alerting routine with the "alertUser" topic.
		topic.subscribe("product.create.success", function(text){
			alert('Got text: ' + text);
		});      	
	});
});