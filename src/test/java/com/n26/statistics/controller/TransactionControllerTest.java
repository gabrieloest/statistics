package com.n26.statistics.controller;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.n26.statistics.controller.mapper.TransactionMapper;
import com.n26.statistics.datatransferobject.TransactionDTO;
import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.exception.ConstraintsViolationException;
import com.n26.statistics.service.TransactionServiceImpl;
import com.n26.statistics.utils.MillisUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MillisUtil.class, Instant.class})
public class TransactionControllerTest
{

    @Mock
    private TransactionServiceImpl service;

    @InjectMocks
    private TransactionController controller;

    private Transaction transactionNew;

    private TransactionDTO transactionNewDTO;

    private Transaction transactionPast;

    private TransactionDTO transactionPastDTO;


    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.transactionNew = new Transaction(1532231340439l, 15.3);
        this.transactionNew.setId(1L);
        this.transactionNewDTO = TransactionMapper.makeTransactionDTO(this.transactionNew);

        this.transactionPast = new Transaction(1532231220439l, 15.3);
        this.transactionPast.setId(2L);
        this.transactionPastDTO = TransactionMapper.makeTransactionDTO(this.transactionPast);
    }


    @Test
    public void createTransactionLast60SecondsSuccessFully() throws ConstraintsViolationException
    {
        Instant mockTime = Instant.ofEpochMilli(1532231340439l);
        PowerMockito.mockStatic(Instant.class);
        PowerMockito.when(Instant.now()).thenReturn(mockTime);
        Mockito.when(this.service.create(ArgumentMatchers.any(Transaction.class))).thenReturn(this.transactionNew);
        ResponseEntity<?> controllerTransaction = this.controller.createTransaction(this.transactionNewDTO);
        Assert.assertEquals(HttpStatus.CREATED, controllerTransaction.getStatusCode());
    }


    @Test
    public void createTransactionBefore60SecondsSuccessFully() throws ConstraintsViolationException
    {
        Mockito.when(this.service.create(ArgumentMatchers.any(Transaction.class))).thenReturn(this.transactionPast);
        ResponseEntity<?> controllerTransaction = this.controller.createTransaction(this.transactionPastDTO);
        Assert.assertEquals(HttpStatus.NO_CONTENT, controllerTransaction.getStatusCode());
    }


    @Test
    public void failToCreateCar() throws ConstraintsViolationException
    {
        Mockito.when(this.service.create(ArgumentMatchers.any(Transaction.class))).thenThrow(new ConstraintsViolationException("The minimum number for timestamp is 0"));
        ResponseEntity<?> controllerTransaction = this.controller.createTransaction(this.transactionNewDTO);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, controllerTransaction.getStatusCode());
        Assert.assertTrue(((String) controllerTransaction.getBody()).contains("The minimum number for timestamp is 0"));
    }
}
