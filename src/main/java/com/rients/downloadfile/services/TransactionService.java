package com.rients.downloadfile.services;

import com.rients.downloadfile.model.AllCoinPrices;
import com.rients.downloadfile.model.Coin;
import com.rients.downloadfile.model.Result;
import com.rients.downloadfile.model.Transaction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.rients.downloadfile.main.StaticData.DAYCHARTPATH;
import static com.rients.downloadfile.main.StaticData.SEP;
import static com.rients.downloadfile.main.StaticData.TRANSACTIONSPATH;

public class TransactionService {

	List<Transaction> transactions = new ArrayList<>();
	Double portfolioAmount;
	Double startAmount;
	Double costs = .003d;

	public TransactionService(Double portfolioAmount) {
		this.startAmount = portfolioAmount;
		this.portfolioAmount = portfolioAmount;
	}

	public Transaction openTransaction(Double rate, final String currentDate, final String currentCoinSymbol) {
		final Transaction transaction;
		transaction = new Transaction();
		transaction.setBuyDate(currentDate);
		transaction.setCoinSymbol(currentCoinSymbol);
		transaction.setBuyRate(rate);
		portfolioAmount = portfolioAmount * (1 - costs);
		transaction.setPieces(portfolioAmount / rate);
		return transaction;
	}

	public void closeTransaction(Double rate, final Transaction transaction, final String currentDate) {
		transaction.setSellRate(rate);
		transaction.setSellDate(currentDate);
		this.transactions.add(transaction);
		portfolioAmount = transaction.getPieces() * rate * (1 - costs);
	}

	public void writeTransactionsToFile() {
		List<String> transactionLines = transactions.stream().map(Transaction::toString).collect(Collectors.toList());
		transactionLines.add(0, Transaction.getHeader());
		TradingIOService.writeToFile(TRANSACTIONSPATH, transactionLines);
	}

	public void writeProgressPerDateToFile(List<Coin> coins, Map<String, Double> cryptoIndex) {
		List<String> dates = AllCoinPrices.getAllDates();
		Map<String, String> allPrices = new TreeMap<>();
		dates.forEach(date -> {
					transactions.stream().filter(transaction -> transaction.hasDate(date)).findFirst().ifPresentOrElse(transaction ->
					{
						if (!date.equals(transaction.getBuyDate())) {
							Double price = AllCoinPrices.getPrice(date, getCoinIndexInMaster(transaction.getCoinSymbol(), coins));
							Double value = price * transaction.getPieces();
//							System.out.println("date: " + date + "coin: " + transaction.getCoinSymbol() + " price: "+ price + " pieces: " + transaction.getPieces() + " value: " + value);
							allPrices.put(date, transaction.getCoinSymbol() + SEP + price + SEP + value);
						}
					}, () -> {
						allPrices.put(date, SEP + SEP + startAmount);
					});
				}
		);
		// write to file here
		List<String> dayChartLines = new ArrayList<>();
		allPrices.forEach((date, value) -> {
			Double index = cryptoIndex.get(date);
			dayChartLines.add(date + SEP + value + SEP + index);
		});
		TradingIOService.writeToFile(DAYCHARTPATH, dayChartLines);
	}

	private int getCoinIndexInMaster(String coinSymbol, List<Coin> coins) {
		return coins.stream().filter(coin -> coin.getCoinSymbol().equals(coinSymbol)).findFirst().get().getLocationInMaster();
	}

	public void printSummary() {
		System.out.println("number of transactions: " + transactions.size());
		System.out.println("portfolioAmount: " + format(portfolioAmount));
	}

	public Result getResult() {
		return Result.builder().numberOfTransactions(transactions.size() - 1).score(portfolioAmount).build();
	}

	public String format(Double d) {
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.0000");
		return decimalFormat.format(d);
	}

}
