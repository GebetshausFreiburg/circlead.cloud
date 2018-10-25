package org.rogatio.circlead.model.data;

import java.util.Calendar;

import org.dmfs.rfc5545.recur.Freq;

public class Timeslice {
	private String freq;
	private Calendar start;
	private Calendar end;
	private String sliceStart;
	private int unitValue;
	private double allokation;
	
	public int getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(int unitValue) {
		this.unitValue = unitValue;
	}

	public String toString() {
		return sliceStart + "=" + allokation;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public void setFreq(Freq freq) {
		this.freq = freq.name();
	}

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

	public String getSliceStart() {
		return sliceStart;
	}

	public void setSliceStart(String sliceStart) {
		this.sliceStart = sliceStart;
	}

	public double getAllokation() {
		return allokation;
	}

	public void setAllokation(double allokation) {
		this.allokation = allokation;
	}

}