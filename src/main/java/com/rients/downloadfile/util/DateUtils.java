package com.rients.downloadfile.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
}