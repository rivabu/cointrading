package com.rients.downloadfile.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DateUtils {
	final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static void main(String[] args) throws IOException {
		final String firstInput = "2021-01-01";
		final String secondInput = "2021-02-02-";
		final LocalDate firstDate = LocalDate.parse(firstInput, formatter);
		final LocalDate secondDate = LocalDate.parse(secondInput, formatter);
		final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
		System.out.println("Days between: " + days);
	}

	public static long calculateDays(String firstInput, String secondInput) {
		final LocalDate firstDate = LocalDate.parse(firstInput, formatter);
		final LocalDate secondDate = LocalDate.parse(secondInput, formatter);
		final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
		return days;
	}

	public static boolean isDateIn(String startDate, String endDate, String sourceDate) {
		if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
			int sourceDateInt = Integer.parseInt(sourceDate.replaceAll("-", ""));

			int startDateInt = Integer.parseInt(startDate.replaceAll("-", ""));
			int endDateInt = Integer.parseInt(endDate.replaceAll("-", ""));
			return sourceDateInt >= startDateInt && sourceDateInt <= endDateInt;
		} else {
			return true;
		}
	}

	public static boolean firstDateEqualsOrAfterSecondDate(String firstDate, String secondDate) {
		if (Objects.nonNull(firstDate) && Objects.nonNull(secondDate)) {
			int firstDateInt = Integer.parseInt(firstDate.replaceAll("-", ""));
			int secondDateInt = Integer.parseInt(secondDate.replaceAll("-", ""));
			return firstDateInt >= secondDateInt;
		} else {
			System.out.println("wrong date: " + firstDate + " or " + secondDate) ;
			return true;
		}
	}
}