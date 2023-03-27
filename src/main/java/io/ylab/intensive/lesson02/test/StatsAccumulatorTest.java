package io.ylab.intensive.lesson02.test;

import io.ylab.intensive.lesson02.statsaccumulator.StatsAccumulatorImpl;
import io.ylab.intensive.lesson02.statsaccumulator.StatsAccumulator;

public class StatsAccumulatorTest {
    public static void main(String[] args) {
        StatsAccumulator statsAccumulator = new StatsAccumulatorImpl();
        statsAccumulator.add(1);
        System.out.println("Добавили 1");
        statsAccumulator.add(2);
        System.out.println("Добавили 2");
        System.out.println("Получаем среднее арифметическое: " + statsAccumulator.getAvg());
        statsAccumulator.add(0);
        System.out.println("Добавили 0");
        System.out.println("Получаем минимальный элемент: " + statsAccumulator.getMin());
        statsAccumulator.add(3);
        System.out.println("Добавили 3");
        statsAccumulator.add(8);
        System.out.println("Добавили 8");
        System.out.println("Получаем максимальный элемент: " + statsAccumulator.getMax());
        System.out.println("Получаем количество элементов: " + statsAccumulator.getCount());
    }
}
