package com.rients.downloadfile.model;

import java.util.LinkedList;
import java.util.stream.IntStream;

/*
 SMA smaCalculator = new SMA(DAGENTERUG);
 BigDecimal sma = smaCalculator.compute(new BigDecimal(rates.get(j).closekoers));
 */

public class EMA {

	private final LinkedList<Double> values = new LinkedList();
	private final int length;
	private Double sum = 0d;
	private Double average = 0d;
	private Double factor = 0d;


	public EMA(final int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("length must be greater than zero");
		}
		this.factor = 1d / length;
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
		Double totalScore = 0d;
		Double sumFactor = 0d;
		for (int index = 0; index <values.size(); index++) {
			Double myFactor = factor * (index + 1);
			sumFactor = sumFactor + myFactor;
			totalScore = totalScore + (values.get(index) * myFactor);
		}

		average = totalScore / sumFactor;
		return average;
	}
}
