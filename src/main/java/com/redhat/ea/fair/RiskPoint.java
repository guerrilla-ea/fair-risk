package com.redhat.ea.fair;

public class RiskPoint {
	public double x;
	public double y;
	
	public RiskPoint(){}

	public RiskPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return String.format("%f\t%f", this.x, this.y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}