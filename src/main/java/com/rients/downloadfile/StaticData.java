package com.rients.downloadfile;

import com.rients.downloadfile.model.Coin;

import java.util.Arrays;
import java.util.List;

public class StaticData {
	public static String PATH = "//Users//rivabu//aaa//other-java-projects//trading//src//main//resources//koersen//%s_%s.csv";
	public static String RESULTPATH = "//Users//rivabu//aaa//other-java-projects//trading//src//main//resources//koersen//RESULT.csv";
	public static String DOWNLOAD_URL = "https://web-api.coinmarketcap.com/v1/cryptocurrency/ohlcv/historical?id=%s&convert=USD&time_start=%s&time_end=%s";
	public static String TRANSACTIONSPATH = "//Users//rivabu//aaa//other-java-projects//trading//src//main//resources//koersen//TRANSACTIONS.csv";
	public static String DAYCHARTPATH = "//Users//rivabu//aaa//other-java-projects//trading//src//main//resources//koersen//DAYCHART-MODEL.csv";
	public static long startDate = 1451603309; //01-01-2016
	public static long endDate = 2612305093l; //Saturday 12 October 2052 00:18:13
	public static String SEP = ",";


	public List<Coin> getDownloadData() {
		return Arrays.asList(
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
	}

	public static boolean contains(List<Coin> coins, int id) {
		return coins.stream().anyMatch(coin -> coin.getLocationInMaster() == id);
	}



}
