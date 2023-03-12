package homework.second.statsaccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int count;
    private int min;
    private int max;
    private double avg;

    public StatsAccumulatorImpl() {
        this.count = 0;
        this.min = Integer.MAX_VALUE;
        this.max = Integer.MIN_VALUE;
        this.avg = 0;
    }

    @Override
    public void add(int value) {
        this.count++;
        this.min = Math.min(this.min, value);
        this.max = Math.max(this.max, value);
        this.avg = (avg * (count - 1) + value) / count;


    }

    @Override
    public int getMin() {
        if (count == 0) {
            throw new IllegalStateException("Элемент еще не был добавлен, добавьте элемент и попробуйте снова");
        }
        return min;
    }

    @Override
    public int getMax() {
        if (count == 0) {
            throw new IllegalStateException("Элемент еще не был добавлен, добавьте элемент и попробуйте снова");
        }
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        if (count == 0) {
            throw new IllegalStateException("Элемент еще не был добавлен, добавьте элемент и попробуйте снова");
        }
        return avg;
    }
}
