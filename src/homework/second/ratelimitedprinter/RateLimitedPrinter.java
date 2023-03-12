package homework.second.ratelimitedprinter;

public class RateLimitedPrinter {
    private long lastPrintTime;
    private final long interval;

    public RateLimitedPrinter(long interval) {
        this.lastPrintTime = 0;
        this.interval = interval;
    }

    public void print(String message) {
        long currentTime = System.currentTimeMillis();
        if (this.lastPrintTime == 0) {
            System.out.println(message);
            this.lastPrintTime = System.currentTimeMillis();
        }
        else if (currentTime - this.lastPrintTime >= interval) {
            System.out.println(message);
            this.lastPrintTime = currentTime;
        }

    }
}
