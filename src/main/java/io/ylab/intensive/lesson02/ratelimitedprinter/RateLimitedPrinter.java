package io.ylab.intensive.lesson02.ratelimitedprinter;

public class RateLimitedPrinter {
    private long lastPrintTime;
    private final long interval;

    public RateLimitedPrinter(long interval) {
        this.lastPrintTime = 0;
        this.interval = interval;
    }

    public void print(String message) {
        long currentTime = System.currentTimeMillis();
        if (lastPrintTime == 0) {
            System.out.println(message);
            lastPrintTime = System.currentTimeMillis();
        }
        else if (currentTime - lastPrintTime >= interval) {
            System.out.println(message);
            lastPrintTime = currentTime;
        }

    }
}
