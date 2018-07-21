package com.n26.statistics.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.n26.statistics.datatransferobject.TransactionDTO;
import com.n26.statistics.domainobject.Transaction;

public class TransactionMapper
{

    public static Transaction makeTransaction(@Valid TransactionDTO transactionDTO)
    {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTimestamp(transactionDTO.getTimestamp());
        return transaction;
    }


    public static TransactionDTO makeTransactionDTO(Transaction transaction)
    {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setTimestamp(transaction.getTimestamp());
        return transactionDTO;
    }


    public static List<TransactionDTO> makeTransactionDTOList(Collection<Transaction> transactions)
    {
        return transactions.stream().map(TransactionMapper::makeTransactionDTO).collect(Collectors.toList());
    }

}
