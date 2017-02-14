package com.redhat.ea.fair;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public abstract class LossEvent {
	private double low, high, likely;
	private MonteCarloSimulation mcs;
	private DescriptiveStatistics ds = new DescriptiveStatistics();

	public LossEvent() {
		this.low = 0d;
		this.high = 0d;
		this.likely = 0d;
	}

	public LossEvent(double low, double high, double likely) {
		this.low = low;
		this.high = high;
		this.likely = likely;
		mcs = new MonteCarloSimulation(low, high, likely);
	}

	public void runSimulation() throws RuntimeException {
		if (mcs != null) {
			mcs.runSimulation();
		}
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLikely() {
		return likely;
	}

	public void setLikely(double likely) {
		this.likely = likely;
	}

	public double getSample() {
		double s = mcs.getSample();
		ds.addValue(s);
		return s;
	}

	public double getMinimum() {
		return ds.getMin();
	}

	public double getMaximum() {
		return ds.getMax();
	}

	public double getMean() {
		return ds.getMean();
	}

	public double getMode() {
		return ((ds.getMean() * 6) - ds.getMin() - ds.getMax()) / 4;
	}
}
