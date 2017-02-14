package com.redhat.ea.fair;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class MonteCarloSimulation {
	
	private double minimum, maximum, likely;
	private double simulationMin, simulationMax, simulationMean;
	
	private static final int NUM = 1000;
	
	private DescriptiveStatistics ds = new DescriptiveStatistics();
	private BetaDistribution bd = null;
	
	private Map<Double, Integer> histogram = new TreeMap<Double, Integer>();
	
	public MonteCarloSimulation(double min, double max, double likely){
		
		this.setMinimum(min);
		this.setMaximum(max);
		this.setLikely(likely);

	}
	
	private double getAlpha(double min, double max, double mean, double stdDev){
		
		double a =  
				(
					(
							((mean- min)-1)
							/
							((max - min)-1)
					)
				)
				*
				(
						((
							(mean-min)
							*
							(max-mean)
						)-1)
						/
						(stdDev*stdDev-1)
				)
				
				;
		
		return a;
	}
	
	private double getBeta(double min, double max, double mean, double stdDev){		
		double b = 
				(
						(max-mean)
						/
						(mean-min)
				)
				*
				getAlpha(min, max, mean, stdDev);
		return b;
	}
	
	private double getMean(double min, double max, double likely){
		return (min + (4*likely) + max)/6;
	}
	
	public double getMode(double min, double max, double mean){
		return ((mean*6)-min-max)/4;
	}
	
	private double getStdDev(double min, double max){
		return (max-min)/6;
	}
	
	public void runSimulation(){
		double min = getMinimum();
		double max = getMaximum();
		double likely = getLikely();
		double mean = getMean(min, max, likely);
		double stdDev = getStdDev(min, max);
		
		BetaDistribution bdPrime = new BetaDistribution(getAlpha(min, max, mean, stdDev), getBeta(min, max, mean, stdDev));
		
		for(int i=0; i<NUM; i++){
			double rand = bdPrime.sample();
			double val = (min + (max-min)* rand);
			ds.addValue(Math.round(val*100d)/100d);
		}
		
		//now populate the distribution based on the simulation values
		min = ds.getMin();
		max = ds.getMax();
		mean = ds.getMean();
		likely = getMode(min, max, mean);
		stdDev = getStdDev(min,max);
		
		bd = new BetaDistribution(getAlpha(min, max, mean, stdDev), getBeta(min, max, mean, stdDev));
		
		this.simulationMax = ds.getMax();
		this.simulationMin = ds.getMin();
		this.simulationMean = ds.getMean();
		populateHistogram();
	}
	
	public double getSample(){
		return (this.getSimulationMin() + (this.getSimulationMax()-this.getSimulationMin()) * bd.sample());
	}
	
	public void printResults(){
		System.out.println("\nSIMULATION RESULTS");
		System.out.printf("Initial Inputs:\n");
		System.out.printf("Initial Values:\t\tLikely: %f\tMin: %f\tMean: %f\tMax: %f\n", this.getLikely(), this.getMinimum(), this.getMean(this.getMinimum(), this.getMaximum(), this.getLikely()), this.getMaximum());

		System.out.printf("Results:\n");
		System.out.printf("Simulation Values:\tLikely: %f\tMin: %f\tMean: %f\tMax: %f\n", this.getMode(this.getSimulationMin(), this.getSimulationMax(), this.getSimulationMean()), this.getSimulationMin(), this.getSimulationMean(), this.getSimulationMax());


	}
	
	public void printConfidence(){
		System.out.println("\nConfidence Values");
		double xVals[] = {.10d, .25d, .50d, .75d, .90d, .95d};
		for(double x: xVals){
			System.out.printf("%f pct\t%f\n", (x*100d), getValueAtPercentConfidence(x));
		}
	}
	
	public void printProbability(){
		System.out.println("\nProbability of Values");
		for(double x=0;x<=1;x+=.1){
			System.out.printf("%f\t%f\n", x, bd.cumulativeProbability(x));	
		}
	}
	
	public void printDistribution(){
		System.out.println("\nDistribution of Values");
		for(double x=0;x<=1;x+=.05){
			System.out.printf("%f\t%f\n", x, bd.density(x));
		}
	}
	
	public void printPercentiles(){
		System.out.println("\nDensity Values");
		System.out.printf("1\t\t%f\n", ds.getPercentile(1d));
		for(double x=10;x<=100;x+=10){
			System.out.printf("%f\t%f\n", x, ds.getPercentile(x));
		}
	}
	
	public void printHistogram(){
		System.out.println("\nHistogram Buckets: " + histogram.size());
		for(double d: histogram.keySet()){
			System.out.printf("%f\t%d\n", d, histogram.get(d));
		}
	}
	
	public double getValueAtPercentConfidence(double pct){
		return calcVal(bd.inverseCumulativeProbability(pct));
	}
	
	private double calcVal(double probVal){
		return (((this.getMaximum()-this.getMinimum()))*(probVal))+this.getMinimum();
	}

	public double getMinimum() {
		return minimum;
	}

	private void setMinimum(double minimum) {
		this.minimum = minimum;
	}

	public double getMaximum() {
		return maximum;
	}

	private void setMaximum(double maximum) {
		this.maximum = maximum;
	}

	public double getLikely() {
		return likely;
	}

	private void setLikely(double likely) {
		this.likely = likely;
	}

	private void populateHistogram(){
		for(double v:ds.getValues()){
			if(histogram.containsKey(v)){
				int p = histogram.get(v);
				histogram.put(v, ++p);
			}else{
				histogram.put(v, 1);
			}
		}
	}
	
	public static void main(String[] args){
		MonteCarloSimulation mcs = new MonteCarloSimulation(0.01923076923077d, 0.5d, 0.15384615384615d);
		mcs.runSimulation();
		mcs.printResults();
		mcs = new MonteCarloSimulation(30d, 360d, 240d);
		mcs.runSimulation();
		mcs.printResults();
		
		//mcs.printConfidence();
		//mcs.printPercentiles();
		//mcs.printProbability();
		//mcs.printHistogram();
		//mcs.printDistribution();
		
	}

	public double getSimulationMin() {
		return simulationMin;
	}

	public double getSimulationMax() {
		return simulationMax;
	}

	public double getSimulationMean() {
		return simulationMean;
	}

}
