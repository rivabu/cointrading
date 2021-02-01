package com.rients.downloadfile.model;

import lombok.Data;
import lombok.ToString;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.rients.downloadfile.StaticData.SEP;

@Data
@ToString
public class DayQuote {
	

	public DayQuote(String line) {
		String[] fields = line.split(SEP);
		this.setDate(fields[0]);
		this.setOpen(Double.parseDouble(fields[1]));
		this.setHigh(Double.parseDouble(fields[2]));
		this.setLow(Double.parseDouble(fields[3]));
		this.setClose(Double.parseDouble(fields[4]));
		this.setVolume(Long.parseLong(fields[5]));
	}

	public DayQuote(JSONObject jsonObject) {
		JSONObject data = jsonObject.getJSONObject("quote").getJSONObject("USD");
		String dateTime = data.get("timestamp").toString();
		if (dateTime.length() > 10) {
			dateTime = dateTime.substring(0, 10);
		} else {
			System.out.println(dateTime);
		}
		this.setDate(LocalDate.parse(dateTime).format(DateTimeFormatter.ISO_DATE));
		this.setOpen(data.getDouble("open"));
		this.setHigh(data.getDouble("high"));
		this.setLow(data.getDouble("low"));
		this.setClose(data.getDouble("close"));
		this.setVolume(data.getLong("volume"));
	}

	Double open;
	Double high;
	Double low;
	Double close;
	Long volume;
	String date;

	public String toCsv() {
		return this.getDate() + SEP +
		this.getOpen() + SEP +
		this.getHigh() + SEP +
		this.getLow() + SEP +
		this.getClose() + SEP +
		this.getVolume();

	}
}
