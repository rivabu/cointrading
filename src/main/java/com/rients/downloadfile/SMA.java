package com.rients.downloadfile;

import java.math.BigDecimal;
import java.util.LinkedList;

/*
 SMA smaCalculator = new SMA(DAGENTERUG);
 BigDecimal sma = smaCalculator.compute(new BigDecimal(rates.get(j).closekoers));
 */

public class SMA {

	private final LinkedList<Double> values = new LinkedList();
	private final int length;
	private Double sum = 0d;
	private Double average = 0d;

	public SMA(final int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("length must be greater than zero");
		}
		this.length = length;
	}

	public Double currentAverage() {
		return average;
	}

	public Double compute(final Double newValue) {
		if ((values.size() == length) && (length > 0)) {
			sum = sum - values.getFirst();
			values.removeFirst();
		}
		sum = sum + newValue;
		values.addLast(newValue);
		average = sum / values.size();
		return average;
	}
}
