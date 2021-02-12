package com.rients.downloadfile.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Result {
	private Double score;
	private int numberOfTransactions;
	private int daysBack;

	public void print() {
		System.out.println("daysBack: " + daysBack);
		System.out.println("number of transactions: " + numberOfTransactions);
		System.out.println("portfolioAmount: " + score);
		System.out.println("-----");

	}
}
