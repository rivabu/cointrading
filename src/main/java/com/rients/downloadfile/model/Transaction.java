package com.rients.downloadfile.model;

import com.rients.downloadfile.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.util.Precision;

import static com.rients.downloadfile.main.StaticData.SEP;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	private String buyDate;
	private String sellDate;
	private Double buyRate;
	private Double sellRate;
	private Double pieces;
	private Double profitAmount;
	private String coinSymbol;

	private Double getProfitPerc() {
		return ((sellRate - buyRate) / buyRate) * 100;
	}
	private Double getProfitAmount() {
		return pieces * (sellRate - buyRate);
	}
	private Double getTotalValue() {
		return pieces * sellRate;
	}
	private long getDays() {
		return DateUtils.calculateDays(this.buyDate, this.sellDate);
	}

	public static String getHeader() {

		return "buyDate" + SEP + "sellDate" + SEP + "coinSymbol" + SEP + "pieces" + SEP + "days" + SEP + "buyRate" + SEP + "sellRate" + SEP + "ProfitPerc" + SEP + "ProfitAmount" + SEP + "TotalValue";
	}
	public String toString() {

		return buyDate + SEP + sellDate + SEP + coinSymbol + SEP + Precision.round(pieces, 2) + SEP + getDays() + SEP + Precision.round(buyRate, 2) + SEP + Precision.round(sellRate, 2) + SEP + Precision.round(getProfitPerc(), 2) + SEP + Precision.round(getProfitAmount(), 2) + SEP + Precision.round(getTotalValue(),  2);
	}

	public boolean hasDate(String date) {
		int dateInt = Integer.parseInt(date.replaceAll("-", ""));
		int startDate = Integer.parseInt(this.buyDate.replaceAll("-", ""));
		int endDate = Integer.parseInt(this.sellDate.replaceAll("-", ""));
		return dateInt > startDate && dateInt <= endDate;
	}
}
