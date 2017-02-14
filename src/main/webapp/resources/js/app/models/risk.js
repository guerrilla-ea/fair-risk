define([ "jquery", "require", "backbone" ],

function($, require, backbone) {

	/**
	 * Module for the Risk model
	 */
	var Risk = Backbone.Model.extend({
		urlRootOld : 'https://fair-risk.itos.redhat.com/'
				+ 'services/risk/calculate',
				
		urlRoot : '/services/risk/calculate',

		save : function() {
			$.ajax({
				type : "POST",
				url : this.urlRoot,
				data : JSON.stringify(this.attributes),
				processData : false,
				dataType : "json",
				contentType : "application/json",
				success : function(data, status, xhr) {
					riskItem.attributes = data;
					router.navigate('analysis/' + Math.floor((Math.random() * 100) + 1), {
						trigger : true
					});
					$('#submit').button('reset');
				},
				error : function(jqXHR, textStatus, errorThrown) {
					$('#dialog_error').modal();
					$('#submit').button('reset');
					alert(JSON.stringify(errorThrown));
				}
			});
		}

	});

	return Risk;
});