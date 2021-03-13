package com.rients.downloadfile.main;

import com.rients.downloadfile.model.Coin;
import com.rients.downloadfile.model.Result;
import com.rients.downloadfile.services.ModelExecuterService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MoneyMakerSelectedPeriod {

	public static void main(String[] args) throws IOException {
		List<Coin> coins = Arrays.asList(
				new Coin(1, 1, "Bitcoin", "BTC", 1),
				new Coin(2, 2, "Ethereum", "ETH", 1027),
			//	new Coin(3, 4, "Dot", "DOT", 1027),
				new Coin(3, 3, "Tether", "USDT", 825),
				new Coin(4, 5, "XRP", "XRP", 52));
		String startDate = "2015-01-01";
		String endDate = "2021-12-31";

		ModelExecuterService mes = new ModelExecuterService();
		Result result = mes.readMasterCSV(coins, 6, true, startDate, endDate);
		result.print();
	}

}
