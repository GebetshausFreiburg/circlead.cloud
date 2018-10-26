package org.rogatio.circlead.model.data;

import java.util.Calendar;

import org.dmfs.rfc5545.recur.Freq;

/**
 * The Class Timeslice.
 */
public class Timeslice {
	
	/** The freq. */
	private String freq;
	
	/** The start. */
	private Calendar start;
	
	/** The end. */
	private Calendar end;
	
	/** The slice start. */
	private String sliceStart;
	
	/** The unit value. */
	private int unitValue;
	
	/** The allokation. */
	private double allokation;
	
	/**
	 * Gets the unit value.
	 *
	 * @return the unit value
	 */
	public int getUnitValue() {
		return unitValue;
	}

	/**
	 * Sets the unit value.
	 *
	 * @param unitValue the new unit value
	 */
	public void setUnitValue(int unitValue) {
		this.unitValue = unitValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return sliceStart + "=" + allokation;
	}

	/**
	 * Gets the freq.
	 *
	 * @return the freq
	 */
	public String getFreq() {
		return freq;
	}

	/**
	 * Sets the freq.
	 *
	 * @param freq the new freq
	 */
	public void setFreq(String freq) {
		this.freq = freq;
	}

	/**
	 * Sets the freq.
	 *
	 * @param freq the new freq
	 */
	public void setFreq(Freq freq) {
		this.freq = freq.name();
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Calendar getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(Calendar start) {
		this.start = start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public Calendar getEnd() {
		return end;
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	public void setEnd(Calendar end) {
		this.end = end;
	}

	/**
	 * Gets the slice start.
	 *
	 * @return the slice start
	 */
	public String getSliceStart() {
		return sliceStart;
	}

	/**
	 * Sets the slice start.
	 *
	 * @param sliceStart the new slice start
	 */
	public void setSliceStart(String sliceStart) {
		this.sliceStart = sliceStart;
	}

	/**
	 * Gets the allokation.
	 *
	 * @return the allokation
	 */
	public double getAllokation() {
		return allokation;
	}

	/**
	 * Sets the allokation.
	 *
	 * @param allokation the new allokation
	 */
	public void setAllokation(double allokation) {
		this.allokation = allokation;
	}

}