define([
        'jquery',
        'require',
        'backbone',
        'text!../templates/analysis.html' ], 
    
function(jquery, require, backbone, riskAnalysisTemplate) {
	
	var RiskItem = Backbone.View.extend({
		el : '#content',
		
		render : function() {
			var template = _.template(riskAnalysisTemplate);
			this.$el.html(template({risk: this.attributes}));
			loadGraph(this.attributes);
			
			var matrixAttributes;
			
			if(this.attributes.secondaryRisk && this.attributes.secondaryRisk.title != null){
				matrixAttributes = [{frequency: this.attributes.primaryRisk.lossFrequency.mode, 
						magnitude: this.attributes.primaryRisk.lossMagnitude.mode}, 
						{frequency: this.attributes.secondaryRisk.lossFrequency.mode, 
						magnitude: this.attributes.secondaryRisk.lossMagnitude.mode}]; 
			}else{
				matrixAttributes = [{frequency: this.attributes.primaryRisk.lossFrequency.mode, 
					magnitude: this.attributes.primaryRisk.lossMagnitude.mode}];
			}
			
			buildMatrix(matrixAttributes);
		},
		
		defaults : {
			"title" : "",
			"description" : "",
			"primaryRisk" : {
				"title" : "",
				"lossMagnitude" : {
					"low" : "",
					"high" : "",
					"likely" : ""
				},
				"lossFrequency" : {
					"low" : "",
					"high" : "",
					"likely" : ""
				}
			},
			"secondaryRisk" : {
				"title" : "",
				"lossMagnitude" : {
					"low" : "",
					"high" : "",
					"likely" : ""
				},
				"lossFrequency" : {
					"low" : "",
					"high" : "",
					"likely" : ""
				}
			}
			
		}

	});

	return RiskItem;

});