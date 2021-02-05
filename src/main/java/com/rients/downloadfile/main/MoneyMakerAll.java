package com.rients.downloadfile.main;

import com.rients.downloadfile.model.Coin;
import com.rients.downloadfile.services.ModelExecuterService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MoneyMakerAll {

	public static void main(String[] args) throws IOException {
		List<Coin> coins = Arrays.asList(
				new Coin(1, 1, "Bitcoin", "BTC", 1),
				new Coin(2, 2, "Ethereum", "ETH", 1027),
				new Coin(3, 3, "Tether", "USDT", 825));
//				new Coin(4, 5, "XRP", "XRP", 52));

		new ModelExecuterService().readMasterCSV(coins, null, null);
	}

}
