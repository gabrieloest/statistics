package com.n26.statistics.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.n26.statistics.dataaccessobject.TransactionRepository;
import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.exception.ConstraintsViolationException;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Transaction create(Transaction transaction) throws ConstraintsViolationException {
		transactionRepository.save(transaction);
		return null;
	}

	@Override
	public List<Transaction> findAllLast60Seconds() {
		ZonedDateTime now = ZonedDateTime.now();
		return transactionRepository.findAllByTimestampBetween(now.minusSeconds(60l), now);
	}

	@Override
	public List<Transaction> findAll() {
		return (List<Transaction>) transactionRepository.findAll();
	}

}
