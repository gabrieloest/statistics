package com.n26.statistics.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.exception.ConstraintsViolationException;
import com.n26.statistics.utils.MillisUtil;

@Service
public class TransactionServiceImpl implements TransactionService
{

    private static List<Transaction> transactions = new ArrayList<>();

    private static long counter = 0;


    @Override
    public Transaction create(Transaction transaction) throws ConstraintsViolationException
    {
        transaction.setId(TransactionServiceImpl.counter++);
        transaction.setDateCreated(ZonedDateTime.now());
        TransactionServiceImpl.transactions.add(transaction);
        return transaction;
    }


    @Override
    public List<Transaction> findAllLast60Seconds()
    {
        return TransactionServiceImpl.transactions
            .stream().filter(it -> MillisUtil.isMillisBetweenNowAndPast60Seconds(it.getTimestamp())).collect(Collectors.toList());
    }


    @Override
    public List<Transaction> findAll()
    {
        return TransactionServiceImpl.transactions;
    }

}
