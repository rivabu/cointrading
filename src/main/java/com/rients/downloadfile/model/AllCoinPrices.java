package com.rients.downloadfile.model;

import com.rients.downloadfile.services.TradingIOService;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class AllCoinPrices {

	private static Map<String, List<Double>> masterCSV;

	public static void load() {
		masterCSV = TradingIOService.readMasterCSV();
	}

	public static void load(String startDate, String endDate) {
		masterCSV = TradingIOService.readMasterCSV(startDate, endDate);
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
