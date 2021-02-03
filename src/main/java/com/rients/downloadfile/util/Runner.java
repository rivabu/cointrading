package com.rients.downloadfile.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Runner {
	public static void main(String[] args) {
		Double myvalue = 1234567890.988675;

		//Option 1 Print bare double.
		System.out.println(myvalue);

		//Option2, use decimalFormat.
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(8);
		System.out.println(df.format(myvalue));

		//Option 3, use printf.
		System.out.printf("%.9f", myvalue);
		System.out.println();

		//Option 4, convert toBigDecimal and ask for toPlainString().
		System.out.print(new BigDecimal(myvalue).toPlainString());
		System.out.println();

		//Option 5, String.format
		System.out.println(String.format("%.2f", myvalue));


		Double firstNumber = 12345678d;
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.0000000000");
		String firstNumberAsString = decimalFormat.format(firstNumber);
		System.out.println(firstNumberAsString);
	}
}

