package com.rients.downloadfile;

import com.rients.downloadfile.model.Coin;
import com.rients.downloadfile.model.DayQuote;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.rients.downloadfile.StaticData.endDate;
import static com.rients.downloadfile.StaticData.startDate;

public class DownloadQuotes {

	StaticData staticData = new StaticData();

	public static void main(String[] args) throws IOException {
		DownloadQuotes downloadQuotes = new DownloadQuotes();
		for (Coin downloadData : downloadQuotes.staticData.getDownloadData()) {
			showRates(downloadData);
		}
	}

	private static void showRates(Coin data) throws IOException {
		String url = String.format(StaticData.DOWNLOAD_URL, data.getCoinId(), startDate, endDate);
		String content = IOUtils.toString(new URL(url));
		List<DayQuote> quotes = new ArrayList<>();
		JSONArray arr = new JSONObject(content)
				.getJSONObject("data")
				.getJSONArray("quotes");
		arr.forEach(j -> quotes
				.add(new DayQuote((JSONObject) j)));
		TradingFileUtils.saveQuote(data, quotes);
	}

}