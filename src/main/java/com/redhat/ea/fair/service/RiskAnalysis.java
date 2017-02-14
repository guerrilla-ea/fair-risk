package com.redhat.ea.fair.service;

import com.redhat.ea.fair.Risk;

public class RiskAnalysis {
	
	private String description;
	private Risk primaryRisk;
	private Risk secondaryRisk;
	private String riskLevel;
	private String title;
	
	public RiskAnalysis(String title, String description, Risk primaryRisk,
			Risk secondaryRisk, String riskLevel) {
		super();
		this.title = title;
		this.description = description;
		this.primaryRisk = primaryRisk;
		this.secondaryRisk = secondaryRisk;
		this.riskLevel = riskLevel;
	}
	
	public RiskAnalysis(String title, String description, Risk primaryRisk, String riskLevel) {
		super();
		this.title = title;
		this.description = description;
		this.primaryRisk = primaryRisk;
		this.riskLevel = riskLevel;
	}
	
	public RiskAnalysis(){}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Risk getPrimaryRisk() {
		return primaryRisk;
	}
	public void setPrimaryRisk(Risk primaryRisk) {
		this.primaryRisk = primaryRisk;
	}
	public Risk getSecondaryRisk() {
		return secondaryRisk;
	}
	public void setSecondaryRisk(Risk secondaryRisk) {
		this.secondaryRisk = secondaryRisk;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public double[] getExposure(){
		
		double[] exposure = new double[4];
		
		if(secondaryRisk!=null){
			//minimum
			exposure[0] = (primaryRisk.getLossMagnitude().getMinimum() * primaryRisk.getLossFrequency().getMinimum()) + (secondaryRisk.getLossMagnitude().getMinimum() * secondaryRisk.getLossFrequency().getMinimum());
			//average
			exposure[1] = (primaryRisk.getLossMagnitude().getMean() * primaryRisk.getLossFrequency().getMean()) + (secondaryRisk.getLossMagnitude().getMean() * secondaryRisk.getLossFrequency().getMean());
			//mode
			exposure[2] = (primaryRisk.getLossMagnitude().getMode() * primaryRisk.getLossFrequency().getMode()) + (secondaryRisk.getLossMagnitude().getMode() * secondaryRisk.getLossFrequency().getMode());
			//maximum
			exposure[3] = (primaryRisk.getLossMagnitude().getMaximum() * primaryRisk.getLossFrequency().getMaximum()) + (secondaryRisk.getLossMagnitude().getMaximum() * secondaryRisk.getLossFrequency().getMaximum());
		}else{
			//minimum
			exposure[0] = (primaryRisk.getLossMagnitude().getMinimum() * primaryRisk.getLossFrequency().getMinimum());
			//average
			exposure[1] = (primaryRisk.getLossMagnitude().getMean() * primaryRisk.getLossFrequency().getMean());
			//mode
			exposure[2] = (primaryRisk.getLossMagnitude().getMode() * primaryRisk.getLossFrequency().getMode());
			//maximum
			exposure[3] = (primaryRisk.getLossMagnitude().getMaximum() * primaryRisk.getLossFrequency().getMaximum());
		}
		
		return exposure;
		
	}
	

}
