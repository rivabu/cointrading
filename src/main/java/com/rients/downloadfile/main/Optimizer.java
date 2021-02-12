package com.rients.downloadfile.main;

import com.rients.downloadfile.model.Coin;
import com.rients.downloadfile.model.Result;
import com.rients.downloadfile.services.ModelExecuterService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Optimizer {

	public static void main(String[] args) throws IOException {
		List<Coin> coins = Arrays.asList(
				new Coin(1, 1, "Bitcoin", "BTC", 1),
				new Coin(2, 2, "Ethereum", "ETH", 1027),
				new Coin(3, 4, "Dot", "DOT", 1027),
				new Coin(4, 3, "Tether", "USDT", 825),
				new Coin(5, 5, "XRP", "XRP", 52),
				new Coin(6, 6, "ADA", "ADA", 52));
		String startDate = "2015-01-01";
		String endDate = "2021-12-31";

		ModelExecuterService mes = new ModelExecuterService();

		Result maxScore = Result.builder().numberOfTransactions(0).score(0d).build();

		IntStream.range(1, 50).forEach(number -> {
			Result result = mes.readMasterCSV(coins, number, false, startDate, endDate);
			result.setDaysBack(number);
			result.print();
			updateScore(result, maxScore);
		});
		System.out.println("----");
		System.out.println("MAXSCORE " + maxScore);
		mes.readMasterCSV(coins, maxScore.getDaysBack(), true, startDate, endDate);

	}

	private static void updateScore(Result result, Result maxScore) {
		if (result.getScore() > maxScore.getScore()) {
			maxScore.setScore(result.getScore());
			maxScore.setDaysBack(result.getDaysBack());
			maxScore.setNumberOfTransactions(result.getNumberOfTransactions());
		}

	}

}