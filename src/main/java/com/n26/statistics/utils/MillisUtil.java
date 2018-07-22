package com.n26.statistics.utils;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class MillisUtil
{
    public static long getMillisNow()
    {
        Instant instant = Instant.now();
        OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);
        return odt.toInstant().toEpochMilli();
    }


    public static long getMillis60SecondsPast()
    {
        Instant instant = Instant.now();
        OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);
        OffsetDateTime odtPast = odt.minusSeconds(60l);
        return odtPast.toInstant().toEpochMilli();
    }


    public static boolean isMillisBetweenNowAndPast60Seconds(long timestamp)
    {
        return timestamp > MillisUtil.getMillis60SecondsPast() && timestamp < MillisUtil.getMillisNow();
    }
}
