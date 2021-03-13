package com.rients.downloadfile.model;

import com.rients.downloadfile.services.TradingIOService;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class AllCoinPrices {

	private static String startDate;

	private static String endDate;

	private static Map<String, List<Double>> masterCSV;

	public static void load() {
		masterCSV = TradingIOService.readMasterCSV();
	}

	public static void load(String myStartDate, String myEndDate) {
		masterCSV = TradingIOService.readMasterCSV(myStartDate, myEndDate);
		startDate = myStartDate;
		endDate = myEndDate;
	}

	public static String getStartDate() {
		return startDate;
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
