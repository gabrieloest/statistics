package com.n26.statistics;

import java.time.ZonedDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.n26.statistics.dataaccessobject.TransactionRepository;
import com.n26.statistics.domainobject.Transaction;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public void run(ApplicationArguments args) {
		ZonedDateTime now = ZonedDateTime.now();

		double leftLimit = 1D;
		double rightLimit = 100D;

		for (int i = 0; i < 120; i++) {
			double amount = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
			ZonedDateTime pastLocalDateTime = now.minusSeconds(i);
			transactionRepository.save(new Transaction(pastLocalDateTime, amount));
		}

		for (int i = 0; i < 120; i++) {
			double amount = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
			ZonedDateTime futureLocalDateTime = now.plusSeconds(i);
			transactionRepository.save(new Transaction(futureLocalDateTime, amount));
		}

	}
}