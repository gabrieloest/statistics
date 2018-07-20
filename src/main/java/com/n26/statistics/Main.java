package com.n26.statistics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

class Main {
	public static void main(String[] args) {
		// Declare DateTimeFormatter with desired format
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Save current LocalDateTime into a variable
		LocalDateTime localDateTime = LocalDateTime.now();

		double leftLimit = 1D;
		double rightLimit = 100D;

		for (int i = 0; i < 120; i++) {
			double generatedDouble = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
			LocalDateTime futureLocalDateTime = localDateTime.minusSeconds(i);
			String formattedFutureLocalDateTime = futureLocalDateTime.format(dateTimeFormatter);
			System.out.println("insert into transaction (id, date_created, timestamp, amount) values(" + i + ", '"
					+ formattedFutureLocalDateTime + "', '" + formattedFutureLocalDateTime + "', " + generatedDouble
					+ ");");
		}

	}
}