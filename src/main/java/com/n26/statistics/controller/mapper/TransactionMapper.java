package com.n26.statistics.controller.mapper;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.n26.statistics.datatransferobject.TransactionDTO;
import com.n26.statistics.domainobject.Transaction;

public class TransactionMapper {

	public static Transaction makeTransaction(@Valid TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction();
		transaction.setId(transactionDTO.getId());
		transaction.setAmount(transactionDTO.getAmount());
		Instant instant = Instant.ofEpochSecond(transactionDTO.getTimestamp());
		transaction.setTimestamp(ZonedDateTime.ofInstant(instant, ZoneOffset.UTC));
		return transaction;
	}

	public static TransactionDTO makeTransactionDTO(Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setId(transaction.getId());
		transactionDTO.setAmount(transaction.getAmount());
		transactionDTO.setTimestamp(transaction.getTimestamp().toInstant().toEpochMilli());
		return transactionDTO;
	}

	public static List<TransactionDTO> makeTransactionDTOList(Collection<Transaction> transactions) {
		return transactions.stream().map(TransactionMapper::makeTransactionDTO).collect(Collectors.toList());
	}

}
