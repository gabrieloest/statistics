package com.n26.statistics.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

import com.n26.statistics.datatransferobject.StatisticDTO;
import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.exception.ConstraintsViolationException;
import com.n26.statistics.service.TransactionServiceImpl;
import com.n26.statistics.utils.MillisUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MillisUtil.class, Instant.class})
public class StatisticsControllerTest
{

    @Mock
    private TransactionServiceImpl service;

    @InjectMocks
    private StatisticsController controller;

    private List<Transaction> transactions;

    private Transaction transactionNow;

    private Transaction transaction30;

    private Transaction transaction31;

    private Transaction transaction32;

    private Transaction transaction33;

    private Transaction transaction60;

    private Transaction transaction120;


    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.transactions = new ArrayList<>();

        this.transactionNow = new Transaction(1532320037356l, 20);
        this.transaction30 = new Transaction(1532320007356l, 10);
        this.transaction31 = new Transaction(1532320006356l, 15);
        this.transaction32 = new Transaction(1532320005356l, 30);
        this.transaction33 = new Transaction(1532320004356l, 5);
        this.transaction60 = new Transaction(1532319977356l, 25);
        this.transaction120 = new Transaction(1532319917356l, 45);

        this.transactions.add(this.transactionNow);
        this.transactions.add(this.transaction30);
        this.transactions.add(this.transaction31);
        this.transactions.add(this.transaction32);
        this.transactions.add(this.transaction33);
        this.transactions.add(this.transaction60);
        this.transactions.add(this.transaction120);
    }


    @Test
    public void getStatisticsSuccessFully() throws ConstraintsViolationException
    {
        this.transactions.remove(this.transaction120);
        Instant mockTime = Instant.ofEpochMilli(1532320037356l);
        PowerMockito.mockStatic(Instant.class);
        PowerMockito.when(Instant.now()).thenReturn(mockTime);
        Mockito.when(this.service.findAllLast60Seconds()).thenReturn(this.transactions);
        ResponseEntity<?> statistics = this.controller.getStatistics();
        StatisticDTO dto = (StatisticDTO) statistics.getBody();
        Assert.assertEquals(true, dto.getMax() == 30);
        Assert.assertEquals(true, dto.getMin() == 5);
        Assert.assertEquals(true, dto.getAvg() == 17.5);
        Assert.assertEquals(true, dto.getCount() == 6);
        Assert.assertEquals(true, dto.getSum() == 105);
    }
}
