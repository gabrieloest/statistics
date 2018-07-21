package com.n26.statistics;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.n26.statistics.domainobject.Transaction;
import com.n26.statistics.exception.ConstraintsViolationException;
import com.n26.statistics.service.TransactionService;

@Component
public class DataLoader implements ApplicationRunner
{

    @Autowired
    private TransactionService transactionService;


    @Override
    public void run(ApplicationArguments args)
    {
        Instant instant = Instant.now();
        OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);

        double leftLimit = 1D;
        double rightLimit = 100D;

        for (int i = 0; i < 120; i++)
        {
            double amount = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
            OffsetDateTime odtPast = odt.minusSeconds(i);
            try
            {
                this.transactionService.create(new Transaction(odtPast.toInstant().toEpochMilli(), amount));
            }
            catch (ConstraintsViolationException e)
            {
                e.printStackTrace();
            }
        }

    }
}
