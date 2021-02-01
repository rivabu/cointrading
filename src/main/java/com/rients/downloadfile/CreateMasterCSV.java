package com.rients.downloadfile;

import com.rients.downloadfile.model.Coin;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.rients.downloadfile.StaticData.SEP;

public class CreateMasterCSV {
	public static void main(String[] args) throws IOException {
		new CreateMasterCSV().generateMasterCSV();
	}

	public void generateMasterCSV() throws IOException {
		String filename = String.format(StaticData.PATH, 0, "MASTER");
		Map<String, Map<String, Double>> allData = new TreeMap<>();
		List<Coin> allCoins = new StaticData().getDownloadData();
		int[] idx = { 0 };
		allCoins.forEach(
				coin -> {
					TradingFileUtils.getQuotes(coin).forEach((date, quote) -> {
						if (allData.containsKey(date)) {
							allData.get(date).put(coin.getCoinSymbol(), quote.getClose());
						} else {
							// alleen hier indien bitcoin
							if (idx[0] == 0) {
								TreeMap<String, Double> rate = new TreeMap();
								rate.put(coin.getCoinSymbol(), quote.getClose());
								allData.put(date, rate);
							}
						}
					});
					idx[0]++;
				});
		List<String> lines = new ArrayList<>();
		allData.forEach((date, row) -> {
			StringBuffer line = new StringBuffer(date + SEP);
			allCoins.forEach((coin) -> {
				 line.append((row.containsKey(coin.getCoinSymbol()) ? row.get(coin.getCoinSymbol()) :0) + SEP);
			});
			lines.add(line.toString());
		});
		String header = "Date" + SEP + allCoins.stream().map(Coin::getCoinSymbol).collect(Collectors.joining(SEP));
		lines.set(0, header);
		FileUtils.writeLines(new File(filename), lines);

	}
}
