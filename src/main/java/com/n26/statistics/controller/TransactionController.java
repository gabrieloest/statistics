package com.n26.statistics.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.statistics.controller.mapper.TransactionMapper;
import com.n26.statistics.datatransferobject.TransactionDTO;
import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.exception.ConstraintsViolationException;
import com.n26.statistics.service.TransactionService;
import com.n26.statistics.utils.MillisUtil;

/**
 * All operations with a transaction will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController
{

    @Autowired
    private TransactionService transactionService;


    @PostMapping
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO)
    {
        Transaction transactionDO = TransactionMapper.makeTransaction(transactionDTO);

        HttpStatus httpStatus = verifiyTransactionStatus(transactionDO);

        try
        {
            this.transactionService.create(transactionDO);
            return new ResponseEntity<>(httpStatus);
        }
        catch (ConstraintsViolationException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllCars()
    {
        return ResponseEntity.ok(TransactionMapper.makeTransactionDTOList(this.transactionService.findAll()));
    }


    private HttpStatus verifiyTransactionStatus(Transaction transactionDO)
    {
        HttpStatus httpStatus = HttpStatus.CREATED;
        long epochMilli = MillisUtil.getMillis60SecondsPast();

        if (transactionDO.getTimestamp() < epochMilli)
        {
            httpStatus = HttpStatus.NO_CONTENT;
        }
        return httpStatus;
    }

}
