package com.n26.statistics.domainobject;

import java.time.ZonedDateTime;

import javax.validation.constraints.Min;

public class Transaction
{

    private Long id;

    private ZonedDateTime dateCreated = ZonedDateTime.now();

    private double amount;

    @Min(value = 0L, message = "The minimum number for timestamp is 0")
    private long timestamp;


    public Transaction()
    {

    }


    public Transaction(long pastTime, double amount)
    {
        this.amount = amount;
        this.timestamp = pastTime;
    }


    public Long getId()
    {
        return this.id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public ZonedDateTime getDateCreated()
    {
        return this.dateCreated;
    }


    public void setDateCreated(ZonedDateTime dateCreated)
    {
        this.dateCreated = dateCreated;
    }


    public double getAmount()
    {
        return this.amount;
    }


    public void setAmount(double amount)
    {
        this.amount = amount;
    }


    public long getTimestamp()
    {
        return this.timestamp;
    }


    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }


    @Override
    public String toString()
    {
        return "Transaction [id=" + this.id + ", amount=" + this.amount + ", timestamp=" + this.timestamp + "]";
    }

}
