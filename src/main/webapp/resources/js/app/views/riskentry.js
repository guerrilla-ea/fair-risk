define(
		[ "jquery", "require", "backbone", "risk",
				"text!../templates/input.html", "riskitem" ],

		function($, backbone, require, Risk, riskEntryTemplate, RiskItem) {

			var RiskEntry = Backbone.View
					.extend({
						el : '#entry',
						render : function() {
							var pass = new RiskItem()
							pass.attributes = pass.defaults
							var pv = pass.attributes
							if(this.attributes && this.attributes.title){
								pv = this.attributes
							}
							var template = _.template(riskEntryTemplate, { risk : pv });
							this.$el.html(template);
						},

						events : {
							'submit #risk-submit-form' : 'submitRisk'
						},

						submitRisk : function submitRisk(ev) {
							$('#submit').button('loading');
							var valCheck = this.validation();
							if (valCheck.ret) {
								var r = new Risk();
								r.set('title', $('#title').val());
								r.set('description', $('#description').val());
								r.set('primaryLossTitle',
										$('#primaryLossTitle').val());
								r.set('lefPrimary', {
									'low' : $('#plefMin').val(),
									'high' : $('#plefMax').val(),
									'likely' : $('#plefLikely').val()
								});

								r.set('lemPrimary', {
									'low' : $('#plemMin').val(),
									'high' : $('#plemMax').val(),
									'likely' : $('#plemLikely').val()
								});

								// set secondary only if entered
								if ($('#secondaryLossTitle').val()) {
									r.set('lefSecondary', {
										'low' : $('#slefMin').val(),
										'high' : $('#slefMax').val(),
										'likely' : $('#slefLikely').val()
									});
									r.set('lemSecondary', {
										'low' : $('#slemMin').val(),
										'high' : $('#slemMax').val(),
										'likely' : $('#slemLikely').val()
									});
									r.set('secondaryLossTitle', $('#secondaryLossTitle').val());
								}

								r.save();
								return false;
							} else {
								// alert(valCheck.msg);
								if (valCheck.msg == 'none') {
									$('#dialog-modal').modal();
								} else {
									$('#dialog-modal-secondary').modal();
								}

								$('#submit').button('reset');
								return false;
							}
						},

						validation : function validateData() {
							vals = [ $('#title').val(),
									$('#description').val(),
									$('#primaryLossTitle').val(),
									$('#plefMin').val(), $('#plefMax').val(),
									$('#plefLikely').val(),
									$('#plemMin').val(), $('#plemMax').val(),
									$('#plemLikely').val(), ];
							for (var i = 0; i < vals.length; i++) {
								if (vals[i] == "" || vals[i] == null) {
									return {
										ret : false,
										msg : 'none'
									};
								}
							}

							// check secondary
							if ($('#secondaryLossTitle').val() != ""
									&& $('#secondaryLossTitle').val() != null) {
								vals = [ $('#slefMin').val(),
										$('#slefMax').val(),
										$('#slefLikely').val(),
										$('#slemMin').val(),
										$('#slemMax').val(),
										$('#slemLikely').val() ];
								for (var i = 0; i < vals.length; i++) {
									if (vals[i] == "" || vals[i] == null) {
										return {
											ret : false,
											msg : 'two'
										};
									}

								}

							}

							return {
								ret : true,
								msg : null
							};
						}
					});

			return RiskEntry;

		});