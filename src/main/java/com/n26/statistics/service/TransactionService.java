package com.n26.statistics.service;

import java.util.List;

import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.exception.ConstraintsViolationException;

public interface TransactionService
{

    Transaction create(Transaction transaction) throws ConstraintsViolationException;


    List<Transaction> findAllLast60Seconds();


    List<Transaction> findAll();

}
