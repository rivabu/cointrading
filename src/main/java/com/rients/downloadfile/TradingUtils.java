package com.rients.downloadfile;

import com.rients.downloadfile.model.Coin;
import com.rients.downloadfile.model.Tupel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TradingUtils {

	public static void main(String[] args) {
		List<Coin> coins = Arrays.asList(
				new Coin(1, "Bitcoin", "BTC", 1),
				new Coin(2, "Ethereum", "ETH", 1027),
				new Coin(3, "Tether", "USDT", 825));
		System.out.println(generateTupes(coins));
	}

	public static List<Tupel> generateTupes(List<Coin> coins) {
		List<Tupel> tupels = new ArrayList<>();

		for (Coin firstCoin : coins) {
			for (Coin secondCoin : coins) {
				Tupel t = new Tupel(firstCoin, secondCoin);
				if (!tupels.contains(t) && t.isValid()) {
					tupels.add(t);
				}
			}
		}
		return tupels;
	}
}
