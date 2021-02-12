package com.rients.downloadfile.main;

import com.rients.downloadfile.model.Coin;
import com.rients.downloadfile.model.Result;
import com.rients.downloadfile.services.ModelExecuterService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MoneyMakerAll {

	public static void main(String[] args) throws IOException {
		List<Coin> coins = Arrays.asList(
				new Coin(1, 1, "Bitcoin", "BTC", 1),
				new Coin(2, 2, "Ethereum", "ETH", 1027),
				new Coin(3, 3, "Tether", "USDT", 825),
				new Coin(4, 4, "Polkadot", "DOT", 6636),
				new Coin(5, 5, "XRP", "XRP", 52),
				new Coin(6, 6, "Cardano", "ADA", 2010),
				new Coin(7, 7, "Chainlink", "LINK", 1975),
				new Coin(8, 8, "Litecoin", "LTC", 2),
				new Coin(9, 9, "Bitcoin Cash", "BCH", 1831),
				new Coin(10, 10, "Binance Coin", "BNB", 1839));

		ModelExecuterService mes = new ModelExecuterService();
		Result result = mes.readMasterCSV(coins, 9, true, null, null);
		result.print();	}

}
