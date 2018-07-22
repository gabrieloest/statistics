package com.n26.statistics.datatransferobject;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO
{

    @JsonIgnore
    private Long id;

    private double amount;

    @Min(value = 0L, message = "The minimum number for timestamp is 0")
    private long timestamp;


    public Long getId()
    {
        return this.id;
    }


    public void setId(Long id)
    {
        this.id = id;
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

}
