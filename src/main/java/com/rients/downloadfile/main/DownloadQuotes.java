package com.rients.downloadfile.main;

import com.rients.downloadfile.services.TradingIOService;
import com.rients.downloadfile.model.Coin;
import com.rients.downloadfile.services.CreateMasterCSVService;

import java.io.IOException;

public class DownloadQuotes {

	StaticData staticData = new StaticData();

	public static void main(String[] args) throws IOException {
		DownloadQuotes downloadQuotes = new DownloadQuotes();
		for (Coin downloadData : downloadQuotes.staticData.getDownloadData()) {
			TradingIOService.downloadCoin(downloadData);
		}
		new CreateMasterCSVService().generateMasterCSV();
	}



}