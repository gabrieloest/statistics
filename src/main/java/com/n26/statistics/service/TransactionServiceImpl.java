package com.n26.statistics.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.exception.ConstraintsViolationException;
import com.n26.statistics.utils.MillisUtil;

@Service
public class TransactionServiceImpl implements TransactionService
{
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private static List<Transaction> transactions = new ArrayList<>();

    private static long counter = 0;


    @Override
    public Transaction create(Transaction transaction) throws ConstraintsViolationException
    {
        transaction.setId(TransactionServiceImpl.counter++);
        transaction.setDateCreated(ZonedDateTime.now());
        TransactionServiceImpl.transactions.add(transaction);
        TransactionServiceImpl.logger.info("Create Transaction " + transaction);
        return transaction;
    }


    @Override
    public List<Transaction> findAllLast60Seconds()
    {
        TransactionServiceImpl.logger.info("Find transactions of last 60 seconds " + System.currentTimeMillis());
        return TransactionServiceImpl.transactions
            .stream().filter(it -> MillisUtil.isMillisBetweenNowAndPast60Seconds(it.getTimestamp())).collect(Collectors.toList());
    }


    @Override
    public List<Transaction> findAll()
    {
        return TransactionServiceImpl.transactions;
    }

}
