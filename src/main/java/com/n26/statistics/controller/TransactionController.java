package com.n26.statistics.controller;

import java.time.Duration;
import java.time.ZonedDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.statistics.controller.mapper.TransactionMapper;
import com.n26.statistics.datatransferobject.TransactionDTO;
import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.exception.ConstraintsViolationException;
import com.n26.statistics.service.TransactionService;

/**
 * All operations with a transaction will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
		Transaction transactionDO = TransactionMapper.makeTransaction(transactionDTO);

		HttpStatus httpStatus = verifiyTransactionDuration(transactionDO);

		try {
			return new ResponseEntity<>(TransactionMapper.makeTransactionDTO(transactionService.create(transactionDO)),
					httpStatus);
		} catch (ConstraintsViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCars() {
		return ResponseEntity.ok(TransactionMapper.makeTransactionDTOList(transactionService.findAll()));
	}

	private HttpStatus verifiyTransactionDuration(Transaction transactionDO) {
		HttpStatus httpStatus = HttpStatus.CREATED;
		ZonedDateTime now = ZonedDateTime.now();
		Duration d = Duration.between(now, transactionDO.getTimestamp());

		if (d.getSeconds() > 60) {
			httpStatus = HttpStatus.NO_CONTENT;
		}
		return httpStatus;
	}

}
