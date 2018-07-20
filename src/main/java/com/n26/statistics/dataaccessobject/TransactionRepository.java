package com.n26.statistics.dataaccessobject;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.n26.statistics.domainobject.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	List<Transaction> findAllByTimestampBetween(ZonedDateTime minusSeconds, ZonedDateTime now);

}
