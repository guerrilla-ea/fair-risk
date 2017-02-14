package com.redhat.ea.fair;

//import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Risk {

	String title;
	LossEventFrequency frequency;
	LossEventMagnitude magnitude;
	RiskPoint[] samples;
	RiskType rType;

	public Risk() {
	}

	public Risk(String description, RiskType rt) {
		this.title = description;
		this.rType = rt;
	}

	public void setLossFrequency(double min, double max, double ml) {
		if (min > 0 && max > 0 && ml > 0) {
			frequency = new LossEventFrequency(min, max, ml);
		} else {
			frequency = null;
		}
	}

	public void setLossMagnitude(double min, double max, double ml) {
		if (min > 0 && max > 0 && ml > 0) {
			magnitude = new LossEventMagnitude(min, max, ml);
		} else {
			magnitude = null;
		}
	}

	public void runSimulation(int iterations) throws IllegalStateException {
		boolean goodtogo = false;
		if (frequency == null && magnitude == null) {
			throw new IllegalStateException(
					"Unable to run simulation. LEF and LEM not set.");
		} else {
			if (frequency == null) {
				throw new IllegalStateException(
						"Unable to run simulation. LEF is not set.");
			} else if (magnitude == null) {
				throw new IllegalStateException(
						"Unable to run simulation. LEM is not set.");
			} else {
				// everything is ok
				goodtogo = true;
			}
		}

		try {
			if (goodtogo) {
				frequency.runSimulation();
				magnitude.runSimulation();
				samples = new RiskPoint[iterations];
				for (int i = 0; i < iterations; i++) {
					samples[i] = new RiskPoint(frequency.getSample(),
							magnitude.getSample());
				}
			} else {
				throw new IllegalStateException(
						"Unable to run simulation. Unknown initialization problem.");
			}
		} catch (Exception e) {
			// some error in the processing
			throw new IllegalStateException(e);
		}
	}

	public RiskPoint[] getSamples() throws IllegalStateException {
		return this.samples;
	}

	public LossEventFrequency getLossFrequency() {
		return this.frequency;
	}

	public LossEventMagnitude getLossMagnitude() {
		return this.magnitude;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public static void main(String[] args){
		
		Risk rp = new Risk();
		rp.setLossFrequency(0.01923076923077d, 0.5d, 0.15384615384615d);
		rp.setLossMagnitude(30d, 360d, 240d);
		try {
			rp.runSimulation(3000);
			for(int i=0; i<rp.getSamples().length; i++){
				System.out.printf("%.2f, %.2f\n", rp.getSamples()[i].getX(), rp.getSamples()[i].getY());
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		
		
	}

}