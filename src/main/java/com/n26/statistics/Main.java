package com.n26.statistics;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Main
{

    public static void main(String[] args)
    {
        Main.generateIntervals();

        Instant instant = Instant.now();
        OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);
        for (int i = 0; i < 120; i++)
        {
            System.out.println(odt.minusSeconds(i).toInstant().toEpochMilli());
        }
    }


    private static void generateIntervals()
    {
        Instant instant = Instant.now();
        OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);
        System.out.println("now: " + odt.toInstant().toEpochMilli());

        System.out.println("30sec ago: " + odt.minusSeconds(30l).toInstant().toEpochMilli());
        System.out.println("31sec ago: " + odt.minusSeconds(31l).toInstant().toEpochMilli());
        System.out.println("32sec ago: " + odt.minusSeconds(32l).toInstant().toEpochMilli());
        System.out.println("33sec ago: " + odt.minusSeconds(33l).toInstant().toEpochMilli());

        System.out.println("60sec ago: " + odt.minusSeconds(60l).toInstant().toEpochMilli());

        System.out.println("120sec ago: " + odt.minusSeconds(120l).toInstant().toEpochMilli());
    }
}
