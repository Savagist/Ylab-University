package homework.second.test;

import homework.second.ratelimitedprinter.RateLimitedPrinter;

public class RateLimiterTest {
    public static void main(String[] args) {
        RateLimitedPrinter limiter = new RateLimitedPrinter(1000);
            for (int i = 0; i < 1_000_000_000; i++) {
                limiter.print("Новое сообщение...");
            }
    }
}
