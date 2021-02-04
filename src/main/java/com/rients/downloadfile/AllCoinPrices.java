package com.rients.downloadfile;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class AllCoinPrices {

	private static Map<String, List<Double>> masterCSV;

	public static void load() {
		masterCSV = TradingFileUtils.readMasterCSV();
	}

	public static Map<String, List<Double>> getAllCoinPrices() {
		return masterCSV;
	}

	public static Double getPrice(String date, int index) {
		return masterCSV.get(date).get(index - 1);
	}

	public static List<String> getAllDates() {
		List<String> dates = new ArrayList<String>();
		dates.addAll(masterCSV.keySet());
		return dates;
	}
}
