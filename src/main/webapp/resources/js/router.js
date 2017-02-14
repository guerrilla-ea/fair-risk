define ( 
		[ "jquery", 
		  "underscore", 
		  "bootstrap", 
		  "backbone", 
		  "d3",
		  "risk", 
		  "graph",
		  "riskitem",
		  "riskentry",
		  "matrix"],

function($, underscore, bootstrap, backbone, d3, Risk, graph, RiskItem, RiskEntry, matrix) {

			

			
	var Router = Backbone.Router.extend({

		routes : {
			'' : 'home',
			'analysis/:num' : 'analysis',
			'home' : 'home'
		}

	});

	riskItem = new RiskItem();
	riskEntry = new RiskEntry();

	router = new Router();

	router.on("route:home", function() {
		riskEntry.render();
	});

	router.on("route:analysis", function(num) {
		riskItem.render();
		riskEntry.attributes = riskItem.attributes;
		riskEntry.render();
	});

	Backbone.history.start();

	return router;
});