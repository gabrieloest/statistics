package com.n26.statistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.statistics.controller.mapper.StatisticsMapper;
import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.service.TransactionService;

/**
 * All operations with a transaction will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping()
	public ResponseEntity<?> getStatistics() {

		List<Transaction> findAllLast60Seconds = transactionService.findAllLast60Seconds();
		return new ResponseEntity<>(StatisticsMapper.makeStatisticDTO(findAllLast60Seconds),
				HttpStatus.OK);
	}

}
