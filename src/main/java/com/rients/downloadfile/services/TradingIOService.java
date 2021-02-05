package com.rients.downloadfile.services;

import com.rients.downloadfile.main.StaticData;
import com.rients.downloadfile.model.Coin;
import com.rients.downloadfile.model.DayQuote;
import com.rients.downloadfile.util.DateUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.rients.downloadfile.main.StaticData.SEP;
import static com.rients.downloadfile.main.StaticData.endDate;
import static com.rients.downloadfile.main.StaticData.startDate;

public class TradingIOService {

	final static String header = "date, open, high, low, close, volume";

	public static void saveQuote(Coin data, List<DayQuote> quotes) {
		String filename = String.format(StaticData.PATH, data.getId(), data.getCoinSymbol());
		List<String> lines = quotes
				.stream()
				.map(quote -> quote.toCsv())
				.collect(Collectors.toList());
		lines.add(0, header);
		writeToFile(filename, lines);
		return;
	}

	public static void writeToFile(final String filename, final List<String> lines) {
		try {
			FileUtils.writeLines(new File(filename), lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, DayQuote> getQuotes(Coin data) {
		String filename = String.format(StaticData.PATH, data.getId(), data.getCoinSymbol());
		List<String> lines = getLinesOfFile(filename);
		Map quotes = new TreeMap<String, DayQuote>();
		lines
				.stream()
				.skip(1)
				.forEach(line -> {
					DayQuote quote = new DayQuote(line);
					quotes.put(quote.getDate(), quote);
				});
		return quotes;
	}

	public static Map<String, List<Double>> readMasterCSV(String startDate, String endDate) {
		String filename = String.format(StaticData.PATH, 0, "MASTER");
		List<String> lines = TradingIOService.getLinesOfFile(filename);

		Map allData = new TreeMap<String, List<Double>>();
		lines
				.stream()
				.skip(1)
				.forEach(line -> {
					List<String> items = Arrays.asList(line.split(SEP));
					String date = items.get(0);
					if (DateUtils.isDateIn(startDate, endDate, date)) {
						List<Double> doubles = items.stream().skip(1).map(elem -> Double.parseDouble(elem)).collect(Collectors.toList());
						allData.put(date, doubles);
					}
				});
		return allData;
	}

	public static Map<String, List<Double>> readMasterCSV() {
		return readMasterCSV(null, null);
	}

	public static List<String> getLinesOfFile(final String filename) {
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	public static void downloadCoin(Coin data) throws IOException {
		String url = String.format(StaticData.DOWNLOAD_URL, data.getCoinId(), startDate, endDate);
		String content = IOUtils.toString(new URL(url));
		List<DayQuote> quotes = new ArrayList<>();
		JSONArray arr = new JSONObject(content)
				.getJSONObject("data")
				.getJSONArray("quotes");
		arr.forEach(j -> quotes
				.add(new DayQuote((JSONObject) j)));
		saveQuote(data, quotes);
	}
}
