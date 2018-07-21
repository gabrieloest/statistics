package com.n26.statistics.service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.exception.ConstraintsViolationException;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService
{

    private static List<Transaction> transactions = new ArrayList<>();


    @Override
    public Transaction create(Transaction transaction) throws ConstraintsViolationException
    {
        TransactionServiceImpl.transactions.add(transaction);
        return null;
    }


    @Override
    public List<Transaction> findAllLast60Seconds()
    {
        Instant instant = Instant.now();
        OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);
        OffsetDateTime odtPast = odt.minusSeconds(60l);
        return TransactionServiceImpl.transactions
            .stream().filter(it -> isBetween(odt, odtPast, it)).collect(Collectors.toList());
    }


    private boolean isBetween(OffsetDateTime odt, OffsetDateTime odtPast, Transaction it)
    {
        return it.getTimestamp() > odtPast.toInstant().toEpochMilli() && it.getTimestamp() < odt.toInstant().toEpochMilli();
    }


    @Override
    public List<Transaction> findAll()
    {
        return TransactionServiceImpl.transactions;
    }

}
