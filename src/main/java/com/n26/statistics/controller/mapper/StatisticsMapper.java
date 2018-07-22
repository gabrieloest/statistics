package com.n26.statistics.controller.mapper;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import com.n26.statistics.datatransferobject.StatisticDTO;
import com.n26.statistics.domainobject.Transaction;

public class StatisticsMapper
{

    public static Object makeStatisticDTO(List<Transaction> transactions)
    {
        return StatisticsMapper.generateStatisticsBasics(transactions);
    }


    private static StatisticDTO generateStatistics(List<Transaction> transactions)
    {
        DoubleSummaryStatistics summaryStatistics =
            transactions
                .stream().map(Transaction::getAmount)
                .mapToDouble(v -> v).summaryStatistics();

        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setAvg(summaryStatistics.getAverage());
        statisticDTO.setCount(summaryStatistics.getCount());
        statisticDTO.setMax(summaryStatistics.getMax());
        statisticDTO.setMin(summaryStatistics.getMin());
        statisticDTO.setSum(summaryStatistics.getSum());

        return statisticDTO;
    }


    private static StatisticDTO generateStatisticsBasics(List<Transaction> transactions)
    {
        Double avg =
            transactions
                .stream().map(Transaction::getAmount).mapToDouble(v -> v).average()
                .orElse(0);

        long count = transactions.size();

        Double max =
            transactions
                .stream().map(Transaction::getAmount).mapToDouble(v -> v).max()
                .orElse(0);

        Double min =
            transactions
                .stream().map(Transaction::getAmount).mapToDouble(v -> v).min()
                .orElse(0);

        Double sum = transactions.stream().map(Transaction::getAmount).mapToDouble(v -> v).sum();

        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setAvg(avg);
        statisticDTO.setCount(count);
        statisticDTO.setMax(max);
        statisticDTO.setMin(min);
        statisticDTO.setSum(sum);

        return statisticDTO;
    }
}
