package io.ylab.intensive.lesson02.test;

import io.ylab.intensive.lesson02.ratelimitedprinter.RateLimitedPrinter;

import java.util.Date;

public class RateLimiterTest {
    public static void main(String[] args) {
        RateLimitedPrinter limiter = new RateLimitedPrinter(1000);
        for (int i = 0; i < 1_000_000_000; i++) {
            limiter.print(new Date() + "");
        }
    }
}
