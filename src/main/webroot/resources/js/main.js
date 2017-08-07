requirejs.config({
	paths: {
		jquery: 'libs/jquery.min',
		underscore: 'libs/underscore',
		bootstrap: 'libs/bootstrap.min',
		backbone: 'libs/backbone',
		d3: 'libs/d3.min',
		risk: 'app/models/risk',
		matrix: 'matrix',
		graph: 'graph',
		riskitem: 'app/views/riskitem',
		riskentry: 'app/views/riskentry',
		router: 'router',
		text: 'libs/text'
		
	},
	
	shim: {
        'backbone': {
            deps: ['jquery', 'underscore'],
            exports: 'Backbone'
        },
        
        'underscore': {
        	exports: '_'
        }
    }
});

require([
         'jquery',
         'backbone',
         'router'
     ], function($,b,r){
		$(document).on('.tooltip.data-api');
		$(document).on('.popover.data-api');
     });


function formatCurrency(amount)
{
	var delimiter = ","; // replace comma if desired
	amount = new String(amount.toFixed(2));
	var a = amount.split('.',2);
	var d = a[1];
	var i = parseInt(a[0]);
	if(isNaN(i)) { return ''; }
	var minus = '';
	if(i < 0) { minus = '-'; }
	i = Math.abs(i);
	var n = new String(i);
	var a = [];
	while(n.length > 3)
	{
		var nn = n.substr(n.length-3);
		a.unshift(nn);
		n = n.substr(0,n.length-3);
	}
	if(n.length > 0) { a.unshift(n); }
	n = a.join(delimiter);
	if(d.length < 1) { amount = n; }
	else { amount = n + '.' + d; }
	amount = minus + amount;
	return "$" + amount;
}