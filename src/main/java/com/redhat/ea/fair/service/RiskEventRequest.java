package com.redhat.ea.fair.service;

import com.redhat.ea.fair.LossEventFrequency;
import com.redhat.ea.fair.LossEventMagnitude;

public class RiskEventRequest {

	private String title;
	private String description;
	private LossEventFrequency lefPrimary;
	private LossEventFrequency lefSecondary;
	private LossEventMagnitude lemPrimary;
	private LossEventMagnitude lemSecondary;
	private String secondaryLossTitle;
	private String primaryLossTitle;
	
	private int iterations = 3000;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LossEventFrequency getLefPrimary() {
		return lefPrimary;
	}

	public void setLefPrimary(LossEventFrequency lefPrimary) {
		this.lefPrimary = lefPrimary;
	}

	public LossEventFrequency getLefSecondary() {
		return lefSecondary;
	}

	public void setLefSecondary(LossEventFrequency lefSecondary) {
		this.lefSecondary = lefSecondary;
	}

	public LossEventMagnitude getLemPrimary() {
		return lemPrimary;
	}

	public void setLemPrimary(LossEventMagnitude lemPrimary) {
		this.lemPrimary = lemPrimary;
	}

	public LossEventMagnitude getLemSecondary() {
		return lemSecondary;
	}

	public void setLemSecondary(LossEventMagnitude lemSecondary) {
		this.lemSecondary = lemSecondary;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	
	public RiskEventRequest(String title, String description,
			LossEventFrequency lefPrimary, LossEventFrequency lefSecondary,
			LossEventMagnitude lemPrimary, LossEventMagnitude lemSecondary,
			String secondarylossTitle, String primaryLossTitle, int iterations) {
		super();
		this.title = title;
		this.description = description;
		this.lefPrimary = lefPrimary;
		this.lefSecondary = lefSecondary;
		this.lemPrimary = lemPrimary;
		this.lemSecondary = lemSecondary;
		this.secondaryLossTitle = secondarylossTitle;
		this.primaryLossTitle = primaryLossTitle;
		this.iterations = iterations;
	}

	public RiskEventRequest(){
		
	}

	public String getSecondaryLossTitle() {
		return secondaryLossTitle;
	}

	public void setSecondaryLossTitle(String secondaryLossTitle) {
		this.secondaryLossTitle = secondaryLossTitle;
	}

	public String getPrimaryLossTitle() {
		return primaryLossTitle;
	}

	public void setPrimaryLossTitle(String primaryLossTitle) {
		this.primaryLossTitle = primaryLossTitle;
	}
	

}
